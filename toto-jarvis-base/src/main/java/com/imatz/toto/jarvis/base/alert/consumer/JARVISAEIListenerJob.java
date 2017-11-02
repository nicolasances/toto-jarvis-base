package com.imatz.toto.jarvis.base.alert.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.imatz.toto.jarvis.base.JARVISDistributedComponent;
import com.imatz.toto.jarvis.base.alert.broker.JARVISAlertKafkaBroker;
import com.imatz.toto.jarvis.base.alert.conv.JARVISActionExecutionInstructionJSONConverter;
import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;

@Service("jarvis-base.aei.listener")
@Scope("prototype")
public class JARVISAEIListenerJob implements InitializingBean {

	private static final Logger logger_ = LogManager.getLogger();

	@Value("${kafka.host}")
	private String kafkaHost_;

	@Value("${kafka.port}")
	private String kafkaPort_;

	@Value("${jarvis.aei.kafka.consumer.auto.commit.interval.ms}")
	private String kafkaAutoCommitInterval_;

	@Value("${jarvis.aei.kafka.consumer.poll.timeout}")
	private String kafkaPollTimeout_;

	private String kafkaTopic_;
	private String kafkaConsumerGroup_;
	
	private boolean stoppedPolling_ = true;

	@Autowired
	private JARVISDistributedComponent jarvisDistributedComponent_;
	
	private Consumer<String, String> consumer_;

	public void startJob() {
		
		if (!stoppedPolling_) return;

		getAndExecuteInstructions();
		
	}

	private void getAndExecuteInstructions() {

		List<JARVISActionExecutionInstruction> aeis = new ArrayList<JARVISActionExecutionInstruction>();
		
		logger_.debug(this + " - Starting Kafka consumer polling for AEIs");
		
		stoppedPolling_ = false;

		ConsumerRecords<String, String> records = consumer_.poll(59000L);
		
		logger_.debug(this + " - Kafka consumer polling for AEIs timeout (as expected)");
		
		stoppedPolling_ = true;

		for (ConsumerRecord<String, String> record : records) {

			JARVISActionExecutionInstruction aei = new JARVISActionExecutionInstructionJSONConverter().fromJSON(record.value());

			aeis.add(aei);
		}

		if (aeis == null || aeis.isEmpty())
			return;

		for (JARVISActionExecutionInstruction instruction : aeis) {

			JARVISAEIExecutionThread aeiExecutionThread = new JARVISAEIExecutionThread(instruction, jarvisDistributedComponent_);

			new Thread(aeiExecutionThread).start();

		}
		
	}

	/**
	 * This method initializes the Kafka consumer. This method has to be called
	 * before using this class for doing anything.
	 * 
	 * @param appCode
	 *            the application code that is requesting to use this consumer
	 *            (for example "energy")
	 */
	private void newConsumer(String appCode) {

		kafkaTopic_ = JARVISAlertKafkaBroker.buildAEITopicName(appCode);
		kafkaConsumerGroup_ = JARVISAlertKafkaBroker.KAFKA_TOPIC_JARVIS_ALERTS + "-" + appCode;

		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaHost_ + ":" + kafkaPort_);
		props.put("group.id", kafkaConsumerGroup_);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", kafkaAutoCommitInterval_);
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		consumer_ = new KafkaConsumer<String, String>(props);

		consumer_.subscribe(Arrays.asList(kafkaTopic_));

	}

	@Override
	public void afterPropertiesSet() throws Exception {

		newConsumer(jarvisDistributedComponent_.getAppCode());
		
	}

}
