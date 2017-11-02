package com.imatz.toto.jarvis.base.alert.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a generic JARVIS Alert and its structure.
 * <p>
 * Every Alert can have Tasks associated to it. Tasks are the activities that
 * can be executed as a result of the alert.
 * </p>
 * 
 * @author nicolas
 *
 */
public class JARVISAlert {

	/**
	 * Unique identifier (hash code) assigned to this alert. This identifier
	 * corresponds to the one assigned by the persistent storage (MongoDB).
	 */
	protected String id_;

	/**
	 * Code that identifies the type of alert. An example could be
	 * "cleaning.day.not.pinned". This code is the one that's going to be used
	 * by the issuer to later determine what task to execute upon receiving an
	 * action execution instruction for this alert.
	 */
	protected String code_;

	/**
	 * Name of the application that issued this alert (for example "energy")
	 */
	protected String issuer_;

	/**
	 * This is the user-friendly message that will be displayed to the user as a
	 * detailed description of the alert.
	 */
	protected String message_;

	/**
	 * This is the list of suggestions that can (and typically are) associated
	 * to an alert.
	 * <p>
	 * For example if the alert is "You haven't payed bill #39482983.", a
	 * possible suggestion could be "Do you want me (JARVIS) to pay the bill for
	 * you?".
	 * </p>
	 */
	protected List<JARVISSuggestion> suggestions_;

	public JARVISAlert() {
	}

	/**
	 * This constructor requires in input the issuer information, as well as the
	 * message since both are mandatory information bound to the alert
	 * 
	 * @param issuer
	 *            who (the app) issued this alert
	 * @param message
	 *            the message of this alert
	 */
	public JARVISAlert(String issuer, String code, String message) {
		setIssuer(issuer);
		setCode(code);
		setMessage(message);
	}

	public void setCode(String code) {
		code_ = code;
	}

	public String getCode() {
		return code_;
	}

	public String getId() {
		return id_;
	}

	public void setId(String id) {
		id_ = id;
	}

	public String getIssuer() {
		return issuer_;
	}

	protected void setIssuer(String issuer) {
		issuer_ = issuer;
	}

	public String getMessage() {
		return message_;
	}

	public void setMessage(String message) {
		message_ = message;
	}

	public List<JARVISSuggestion> getSuggestions() {
		return suggestions_;
	}

	public void setSuggestions(List<JARVISSuggestion> suggestions) {
		suggestions_ = suggestions;
	}

	/**
	 * Adds a suggestion to the list of suggestions and returns the current
	 * alert
	 * 
	 * @param suggestion
	 *            the suuggestion to be added
	 * @return this alert
	 */
	public JARVISAlert addSuggestion(JARVISSuggestion suggestion) {
		if (suggestions_ == null) suggestions_ = new ArrayList<JARVISSuggestion>();

		suggestions_.add(suggestion);

		return this;
	}
}
