package com.imatz.toto.jarvis.base.alert.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.imatz.toto.jarvis.base.JARVISDistributedComponent;
import com.imatz.toto.jarvis.base.alert.JARVISAEIExecutor;
import com.imatz.toto.jarvis.base.alert.JARVISActionsExecutionResult;
import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;
import com.imatz.toto.util.rest.RESTCall;

/**
 * This class is a thread that will be started whenever a
 * {@link JARVISActionExecutionInstruction} is received and has to be handled.
 * <p>
 * This thread uses an App-specific {@link JARVISAEIHandler} to handle the AEI.
 * </p>
 * <p>
 * Note that when an AEI has been executed by a {@link JARVISAEIHandler} (thus
 * receiving from it a positive response, specifying the alerts that have been
 * resolved by that action execution), for every resolved alert this class will
 * delete the alert by requesting its deletion to the JARVIS Alert Microservice
 * </p>
 * 
 * @author nick
 *
 */
public class JARVISAEIExecutionThread implements Runnable {
	
	private static final Logger logger_ = LogManager.getLogger();

	private JARVISActionExecutionInstruction instruction_;

	private JARVISDistributedComponent jarvisDistributedComponent_;

	public JARVISAEIExecutionThread(JARVISActionExecutionInstruction instruction, JARVISDistributedComponent jarvisDistributedComponent) {
		
		instruction_ = instruction;
		
		jarvisDistributedComponent_ = jarvisDistributedComponent;
		
	}

	@Override
	public void run() {
		
		logger_.info("Received AEI for alert \"" + instruction_.getJarvisAlertCode() + "\" (" + instruction_.getJarvisAlertID() + ") related to App \"" + instruction_.getAppCode() + "\"");
		
		JARVISAEIExecutor aeiExecutor = jarvisDistributedComponent_.getAEIExecutorFactory().getActionExecutor(instruction_);
		
		if (aeiExecutor == null) throw new RuntimeException("The JARVIS Distributed Component of App \"" + instruction_.getAppCode() + "\" doesn't provide a JARVISAEIExecutor for alert \"" + instruction_.getJarvisAlertCode() + "\"");

		JARVISActionsExecutionResult executionResult = aeiExecutor.execute(instruction_);

		handleAEIExecutionResult(executionResult);
		
	}

	/**
	 * This method handles the results of the action execution provided by the
	 * {@link JARVISAEIHandler}. <br/>
	 * For every alert that is being signaled as "resolved" by the action
	 * execution instruction execution, this method will request a deletion for
	 * that alert to the JARVIS Alerts Microservice.
	 * 
	 * @param executionResult
	 *            the result of the AEI execution
	 */
	private void handleAEIExecutionResult(JARVISActionsExecutionResult executionResult) {

		if (executionResult == null || executionResult.getResolvedAlertIDs() == null)
			return;

		for (String resolvedAlertID : executionResult.getResolvedAlertIDs()) {
			
			logger_.info("Requesting to JARVIS Alerts Microservice the deletion of alert " + resolvedAlertID);

			RESTCall call = new RESTCall("http://localhost:8080/jarvis-alert/alerts/" + resolvedAlertID);
			
			call.delete();

		}

	}

}
