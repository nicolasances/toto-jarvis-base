package com.imatz.toto.jarvis.base.alert.broker;

/**
 * Factory for the {@link JARVISAlertBroker} implementation. <br/>
 * This factory is a singleton
 * 
 * @author C308961
 *
 */
public class JARVISAlertBrokerFactory {

	private static JARVISAlertBrokerFactory instance_;

	private JARVISAlertBrokerFactory() {
	}

	/**
	 * Returns the instance of this singleton class
	 * 
	 * @return
	 */
	public static JARVISAlertBrokerFactory getInstance() {

		if (instance_ == null) instance_ = new JARVISAlertBrokerFactory();

		return instance_;
	}

	/**
	 * Provides a new instance of the {@link JARVISAlertBroker} that can be used
	 * for sending alerts to JARVIS
	 * 
	 * @return the instance of the JARVIS Alert Broker
	 */
	public JARVISAlertBroker getAlertBroker() {

		return new JARVISAlertKafkaBroker();

	}

}
