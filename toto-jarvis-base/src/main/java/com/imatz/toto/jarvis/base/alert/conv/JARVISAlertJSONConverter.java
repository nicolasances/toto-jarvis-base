package com.imatz.toto.jarvis.base.alert.conv;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.imatz.toto.jarvis.base.alert.model.JARVISAction;
import com.imatz.toto.jarvis.base.alert.model.JARVISAlert;
import com.imatz.toto.jarvis.base.alert.model.JARVISSuggestion;

public class JARVISAlertJSONConverter {

	public static final String FIELD_ACTION_TITLE = "title";
	public static final String FIELD_ACTION_NAME = "name";
	public static final String FIELD_SUG_ACTIONS = "actions";
	public static final String FIELD_SUG_MESSAGE = "message";
	public static final String FIELD_SUGGESTIONS = "suggestions";
	public static final String FIELD_MESSAGE = "message";
	public static final String FIELD_ISSUER = "issuer";
	public static final String FIELD_CODE = "code";

	/**
	 * Converts a JSON string containing a JARVIS Alert into a
	 * {@link JARVISAlert}
	 * 
	 * @param json
	 *            the string containing the JARVIS Alert
	 * @return the {@link JARVISAlert}
	 */
	public JARVISAlert fromJSON(String json) {

		Document alertDocument = (Document) Document.parse(json).get("alert");

		return toJARVISAlert(alertDocument);
	}

	/**
	 * Converts a JSON document containing a JARVIS alert into a TO
	 * {@link JARVISAlert}
	 * 
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JARVISAlert toJARVISAlert(Document doc) {

		JARVISAlert alert = new JARVISAlert(doc.getString(FIELD_ISSUER), doc.getString(FIELD_CODE), doc.getString(FIELD_MESSAGE));
		alert.setSuggestions(toJARVISAlertSuggestions(doc.get(FIELD_SUGGESTIONS, List.class)));

		return alert;
	}

	/**
	 * Converts a JSON list of suggestions into the list of
	 * {@link JARVISSuggestion}
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<JARVISSuggestion> toJARVISAlertSuggestions(List<Document> list) {

		if (list == null) return null;

		List<JARVISSuggestion> suggestions = new ArrayList<JARVISSuggestion>();

		for (Document doc : list) {

			JARVISSuggestion sug = new JARVISSuggestion(doc.getString(FIELD_SUG_MESSAGE));
			sug.setActions(toJARVISAlertActions(doc.get(FIELD_SUG_ACTIONS, List.class)));

			suggestions.add(sug);
		}

		return suggestions;
	}

	/**
	 * Converts a JSON list of actions into the list of {@link JARVISAction}
	 * 
	 * @param list
	 * @return
	 */
	private List<JARVISAction> toJARVISAlertActions(List<Document> list) {

		if (list == null) return null;

		List<JARVISAction> actions = new ArrayList<JARVISAction>();

		for (Document doc : list) {

			JARVISAction action = new JARVISAction(doc.getString(FIELD_ACTION_NAME));
			action.setTitle(doc.getString(FIELD_ACTION_TITLE));

			actions.add(action);
		}

		return actions;
	}

	/**
	 * Converts the passed JARVIS Alert into a JSON String representation
	 * 
	 * @param alert
	 *            the alert to convert
	 * @return the JSON String
	 */
	public String toJSON(JARVISAlert alert) {

		Document doc = new Document();
		doc.append(FIELD_ISSUER, alert.getIssuer());
		doc.append(FIELD_CODE, alert.getCode());
		doc.append(FIELD_MESSAGE, alert.getMessage());
		doc.append(FIELD_SUGGESTIONS, toJSONDocument(alert.getSuggestions()));

		return new Document("alert", doc).toJson();
	}

	/**
	 * Converts the past list of {@link JARVISSuggestion} in the equivalent
	 * persistent JSON list
	 * 
	 * @param suggestions
	 * @return
	 */
	private List<Document> toJSONDocument(List<JARVISSuggestion> suggestions) {

		if (suggestions == null) return null;

		List<Document> docs = new ArrayList<Document>();

		for (JARVISSuggestion suggestion : suggestions) {

			Document doc = new Document();
			doc.append(FIELD_SUG_MESSAGE, suggestion.getMessage());
			doc.append(FIELD_SUG_ACTIONS, toActionsJSONDocument(suggestion.getActions()));

			docs.add(doc);

		}

		return docs;
	}

	/**
	 * Converts the past list of {@link JARVISAction} in the equivalent
	 * persistent JSON list
	 * 
	 * @param actions
	 * @return
	 */
	private List<Document> toActionsJSONDocument(List<JARVISAction> actions) {

		if (actions == null) return null;

		List<Document> docs = new ArrayList<Document>();

		for (JARVISAction action : actions) {

			Document doc = new Document();
			doc.append(FIELD_ACTION_NAME, action.getName());
			doc.append(FIELD_ACTION_TITLE, action.getTitle());

			docs.add(doc);
		}

		return docs;
	}

}
