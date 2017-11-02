
package com.imatz.toto.jarvis.base.alert.broker;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.imatz.toto.jarvis.base.alert.conv.JARVISActionExecutionInstructionJSONConverter;
import com.imatz.toto.jarvis.base.alert.conv.JARVISAlertJSONConverter;
import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;
import com.imatz.toto.jarvis.base.alert.model.JARVISAlert;

/**
 * Implementation of the {@link JARVISAlertBroker} interface using KAFKA
 * 
 * @author C308961
 *
 */
public class JARVISAlertKafkaBroker implements JARVISAlertBroker {
	
	private static final Logger logger_ = LogManager.getLogger();

	public static final String KAFKA_TOPIC_JARVIS_ALERTS = "jarvis-alerts";
	public static final String KAFKA_TOPIC_JARVIS_ACTION_EXEC_INSTRUCTION = "jarvis-alerts-aei";

	@Value("${kafka.host}")
	private String kafkaHost_;

	@Value("${kafka.port}")
	private String kafkaPort_;

	private Producer<String, String> producer_;

	/**
	 * Package protected constructor. This class has to be instantiated through
	 * {@link JARVISAlertBrokerFactory}
	 */
	JARVISAlertKafkaBroker() {

		// TODO Remove this and put it as spring properties (make this bean implement the InizializingBean
		
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaHost_ + ":" + kafkaPort_);
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		producer_ = new KafkaProducer<String, String>(props);

	}
	
	@Override
	public void sendAlert(JARVISAlert alert) {

		String data = new JARVISAlertJSONConverter().toJSON(alert);
		
		logger_.info("Sending Alert " + alert.getCode() + " from app \"" + alert.getIssuer() + "\"");

		// TODO Capire come gestire meglio (in quale punto) i nomi dei topic

		producer_.send(new ProducerRecord<String, String>(KAFKA_TOPIC_JARVIS_ALERTS, data));

		producer_.close();
	}

	@Override
	public void sendActionExecutionInstruction(JARVISActionExecutionInstruction instruction) {

		String data = new JARVISActionExecutionInstructionJSONConverter().toJSON(instruction);
		
		// TODO Capire come gestire meglio (in quale punto) i nomi dei topic

		String topicName = buildAEITopicName(instruction.getAppCode());
		
		logger_.info("Sending an AEI for alert " + instruction.getJarvisAlertID() + " on topic \"" + topicName + "\"");

		producer_.send(new ProducerRecord<String, String>(topicName, data));

		producer_.close();

	}

	/**
	 * This method returns the name of the topic on which to listen to AEI for a
	 * specific app
	 * 
	 * @param appCode
	 *            the code of the app that the alert functionally belogns to
	 * @return the name of the Kafka topic
	 */
	public static String buildAEITopicName(String appCode) {

		return KAFKA_TOPIC_JARVIS_ACTION_EXEC_INSTRUCTION + "-" + appCode;

	}

}
