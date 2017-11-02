package com.imatz.toto.jarvis.base.alert.model;


/**
 * An Action is something that can be done and is always associated to a
 * {@link JARVISSuggestion}.
 * <p>
 * For example, to the suggestion "Do you want me (JARVIS) to pay your bill?",
 * possible actions could be "YES" or "NO".
 * </p>
 * 
 * @author nicolas
 *
 */
public class JARVISAction {

	/**
	 * This is the "code" that's associated to this action. For example "YES" or
	 * "NO".
	 */
	protected String name_;

	/**
	 * This is the user-friendly string associated to this action and meant to
	 * be read by the user.
	 * <p>
	 * For example, if the name is "YES", the title could be
	 * "Yes, please do it!".
	 * </p>
	 */
	protected String title_;
	
	public JARVISAction() {}

	/**
	 * This constructor requires the name of the action as this information is
	 * mandatory. <br/>
	 * By default the title is set to the name (and can be overwritten by using
	 * the setter method).
	 * 
	 * @param name
	 */
	public JARVISAction(String name) {
		setName(name);
		setTitle(name);
	}
	
	public JARVISAction(String name, String title) {
		setName(name);
		setTitle(title);
	}

	public String getName() {
		return name_;
	}

	public void setName(String name) {
		name_ = name;
	}

	public String getTitle() {
		return title_;
	}

	public void setTitle(String title) {
		title_ = title;
	}
}
