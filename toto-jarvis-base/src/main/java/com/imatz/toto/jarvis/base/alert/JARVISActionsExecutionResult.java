package com.imatz.toto.jarvis.base.alert;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by the {@link JARVISAEIHandler} to communicate to the
 * JARVIS Base the results of the execution of the provided actions.
 * 
 * @author nick
 *
 */
public class JARVISActionsExecutionResult {

	/**
	 * This is the list of JARVIS Alerts that have been resolved as a result of
	 * the {@link JARVISAEIHandler#handleActionExecutionInstructions(List)}
	 * execution.
	 */
	private List<String> resolvedAlertIDs_;

	/**
	 * Returns the list of alerts that have been resolved by the
	 * {@link JARVISAEIHandler}
	 * 
	 * @return the list of alerts' IDs
	 */
	public List<String> getResolvedAlertIDs() {
		return resolvedAlertIDs_;
	}

	public void setResolvedAlertIDs(List<String> resolvedAlertIDs) {
		resolvedAlertIDs_ = resolvedAlertIDs;
	}

	public void addResolvedAlert(String alertID) {

		if (resolvedAlertIDs_ == null)
			resolvedAlertIDs_ = new ArrayList<String>();

		resolvedAlertIDs_.add(alertID);
	}

}
