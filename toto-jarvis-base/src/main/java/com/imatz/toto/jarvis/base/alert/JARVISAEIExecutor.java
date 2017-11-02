package com.imatz.toto.jarvis.base.alert;

import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;

/**
 * This interface provides the methods for executing an Action Execution
 * Instruction {@link JARVISActionExecutionInstruction}.
 * 
 * @author C308961
 *
 */
public interface JARVISAEIExecutor {

	/**
	 * Executes an instruction related to an alert's action
	 * 
	 * @param inst
	 *            the instruction to execute
	 * 
	 * @return the result of the AEI execution
	 */
	public JARVISActionsExecutionResult execute(JARVISActionExecutionInstruction inst);

}
