package com.imatz.toto.jarvis.base.alert;

import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;

/**
 * This factory creates {@link JARVISAEIHandler} based on the type of AEI that
 * has been received (actually, based on the Alert Code, that defines the
 * different types of alerts).
 * 
 * @author nick
 *
 */
public interface JARVISAEIExecutorFactory {

	/**
	 * Instantiates the {@link JARVISAEIExecutor} that can handle the specific
	 * AEI received (actually, the type of alert related to the received AEI)
	 * 
	 * @param aei
	 *            the received AEI
	 * @return the {@link JARVISAEIExecutor} that can handle the received AEI
	 */
	public JARVISAEIExecutor getActionExecutor(JARVISActionExecutionInstruction aei);

}
