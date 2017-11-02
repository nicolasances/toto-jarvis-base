package com.imatz.toto.jarvis.base.alert.broker;

import com.imatz.toto.jarvis.base.alert.model.JARVISAction;
import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;
import com.imatz.toto.jarvis.base.alert.model.JARVISAlert;

/**
 * This interface is a facade that provides the functionalities to store an
 * alert. It's a utility bean provided to all classes that want to post an
 * alert.
 * <p>
 * This alert will be dealt with asynchronously.<br/>
 * </p>
 * 
 * @author C308961
 *
 */
public interface JARVISAlertBroker {

	/**
	 * This method does the actual alert sending. It does that asynchronously.
	 * 
	 * @param alert
	 *            the {@link JARVISAlert} to send
	 */
	public void sendAlert(JARVISAlert alert);

	/**
	 * This method sends (asynchronously) a request for the execution of an
	 * action {@link JARVISAction} due to a specific alert {@link JARVISAlert}
	 * 
	 * @param instruction
	 *            the instrcution information
	 */
	public void sendActionExecutionInstruction(JARVISActionExecutionInstruction instruction);

}
