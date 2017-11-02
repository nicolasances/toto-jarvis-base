package com.imatz.toto.jarvis.base.alert.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class model a generic suggestion that is associated to the
 * {@link JARVISAlert}
 * 
 * @author nicolas
 *
 */
public class JARVISSuggestion {

	/**
	 * This is the user-friendly message associated with the suggestion and that
	 * is going to be shown to the user.
	 */
	protected String message_;

	/**
	 * This is a list of actions that can be associated to this suggestion.
	 * <p>
	 * For example, to the suggestion
	 * "Do you want me (JARVIS) to pay your bill?", possible actions could be
	 * "YES" or "NO".
	 * </p>
	 */
	protected List<JARVISAction> actions_;
	
	public JARVISSuggestion () {}

	/**
	 * This Constructor requires a message as this is a mandatory information
	 * for the suggestion
	 * 
	 * @param message
	 */
	public JARVISSuggestion(String message) {
		setMessage(message);
	}

	public List<JARVISAction> getActions() {
		return actions_;
	}

	public void setActions(List<JARVISAction> actions) {
		actions_ = actions;
	}
	
	public JARVISSuggestion addAction(JARVISAction action) {
		if (actions_ == null) actions_ = new ArrayList<JARVISAction>();
		
		actions_.add(action);
		
		return this;
	}

	public String getMessage() {
		return message_;
	}

	public void setMessage(String message) {
		message_ = message;
	}

}
