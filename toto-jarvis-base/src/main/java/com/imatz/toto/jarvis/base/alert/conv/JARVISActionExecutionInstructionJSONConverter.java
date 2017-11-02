package com.imatz.toto.jarvis.base.alert.conv;

import org.bson.Document;

import com.imatz.toto.jarvis.base.alert.model.JARVISActionExecutionInstruction;

public class JARVISActionExecutionInstructionJSONConverter {

	public static final String FIELD_ALERT_ID = "alertID";
	public static final String FIELD_ACTION_NAME = "actionName";
	public static final String FIELD_ALERT_CODE = "alertCode";
	public static final String FIELD_APP_CODE = "appCode";
	
	public String toJSON(JARVISActionExecutionInstruction instruction) {
		
		Document doc = new Document();
		doc.append(FIELD_ALERT_ID, instruction.getJarvisAlertID());
		doc.append(FIELD_ALERT_CODE, instruction.getJarvisAlertCode());
		doc.append(FIELD_ACTION_NAME, instruction.getJarvisAlertActionName());
		doc.append(FIELD_APP_CODE, instruction.getAppCode());

		return new Document("instruction", doc).toJson();
		
	}
	
	public JARVISActionExecutionInstruction fromJSON(String json) {
		
		Document aeiDocument = (Document) Document.parse(json).get("instruction");
		
		JARVISActionExecutionInstruction aei = new JARVISActionExecutionInstruction(aeiDocument.getString(FIELD_ALERT_ID), aeiDocument.getString(FIELD_ALERT_CODE), aeiDocument.getString(FIELD_ACTION_NAME), aeiDocument.getString(FIELD_APP_CODE));
		
		return aei;
	}

}
