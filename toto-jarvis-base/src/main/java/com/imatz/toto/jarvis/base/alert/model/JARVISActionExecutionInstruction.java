package com.imatz.toto.jarvis.base.alert.model;

/**
 * Modella un'istruzione di esecuzione di un'azione a risposta di un alert
 * ricevuto da JARVIS<br/>
 * Note that the {@link #appCode_} is the code of the app that the alert
 * functionally belongs to
 * 
 * @author C308961
 *
 */
public class JARVISActionExecutionInstruction {

	private String jarvisAlertID_;
	private String jarvisAlertCode_;
	private String jarvisAlertActionName_;
	private String appCode_;

	/**
	 * Costruttore che permette di definire un'istruzione di esecuzione di una
	 * specifica {@link JARVISAction} per uno specifico {@link JARVISAlert}
	 * 
	 * @param jarvisAlertID
	 *            l'ID dell'alert a cui si riferisce questa azione
	 * @param jarvisAlertCode
	 *            the code of the alert. See {@link JARVISAlert#code_}
	 * @param jarvisAlertActionName
	 *            il name dell'action da eseguire. Vedi
	 *            {@link JARVISAction#name_}
	 * @param appCode
	 *            the code of the app that the alert functionally belongs to
	 */
	public JARVISActionExecutionInstruction(String jarvisAlertID, String jarvisAlertCode, String jarvisAlertActionName, String appCode) {
		setJarvisAlertActionName(jarvisAlertActionName);
		setJarvisAlertCode(jarvisAlertCode);
		setJarvisAlertID(jarvisAlertID);
		setAppCode(appCode);
	}

	public String getAppCode() {
		return appCode_;
	}

	public void setAppCode(String appCode) {
		appCode_ = appCode;
	}

	public String getJarvisAlertCode() {
		return jarvisAlertCode_;
	}

	public void setJarvisAlertCode(String jarvisAlertCode) {
		jarvisAlertCode_ = jarvisAlertCode;
	}

	public String getJarvisAlertActionName() {
		return jarvisAlertActionName_;
	}

	public void setJarvisAlertActionName(String jarvisAlertActionName) {
		jarvisAlertActionName_ = jarvisAlertActionName;
	}

	public String getJarvisAlertID() {
		return jarvisAlertID_;
	}

	public void setJarvisAlertID(String jarvisAlertID) {
		jarvisAlertID_ = jarvisAlertID;
	}

}
