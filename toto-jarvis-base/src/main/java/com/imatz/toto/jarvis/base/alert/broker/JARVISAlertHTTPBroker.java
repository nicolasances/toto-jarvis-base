package com.imatz.toto.jarvis.base.alert.broker;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;
import com.imatz.toto.jarvis.base.alert.model.JARVISAlert;

/**
 * This class is a facade that provides the functionalities to store an alert.
 * <p>
 * This alert will be dealt with asynchronously.<br/>
 * This class will just asynchronously call the HTTP (REST) API that will
 * actually store the Alert on DB.
 * </p>
 * <p>
 * Use the {@link JARVISAlertBrokerFactory} to get an instance of the
 * {@link JARVISAlertBroker} interface
 * </p>
 * 
 * @author nicolas
 * 
 */
public class JARVISAlertHTTPBroker implements JARVISAlertBroker {

	/**
	 * Constructor is only package protected because this class can only be
	 * instantiated through the {@link JARVISAlertBrokerFactory}
	 */
	JARVISAlertHTTPBroker() {
	}

	/**
	 * This method does the actual alert sending. It does that asynchronously.
	 * 
	 * @param alert
	 *            the {@link JARVISAlert} to send
	 */
	public void sendAlert(JARVISAlert alert) {

		new Thread(new JARVISAlertHTTPBrokerThread(alert)).start();

	}

	@Override
	public void sendActionExecutionInstruction(JARVISActionExecutionInstruction instruction) {

		throw new NotImplementedException();
		
	}

}
