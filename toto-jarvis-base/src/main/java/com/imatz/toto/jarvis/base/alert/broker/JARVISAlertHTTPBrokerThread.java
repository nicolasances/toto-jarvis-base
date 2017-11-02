package com.imatz.toto.jarvis.base.alert.broker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.imatz.toto.jarvis.base.alert.conv.JARVISAlertJSONConverter;
import com.imatz.toto.jarvis.base.alert.model.JARVISAlert;

/**
 * This class is the separate thread that's being activated by the
 * {@link JARVISAlertHTTPBroker} when an alert has to be sent.
 * 
 * @author nicolas
 *
 */
public class JARVISAlertHTTPBrokerThread implements Runnable {

	private static final Logger logger_ = LogManager.getLogger();

	private JARVISAlert alert_;

	/**
	 * This constructor takes the alert that has to be sent
	 * 
	 * @param alert
	 */
	public JARVISAlertHTTPBrokerThread(JARVISAlert alert) {

		alert_ = alert;

	}

	public void run() {

		logger_.info("I'm sending the following alert: " + alert_.getMessage());

		// TODO Architect a better solution for calling the REST Service
		HttpURLConnection connection = null;
		try {
			URL url = new URL("http://localhost:8080/jarvis-alert/alerts");
			connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			String data = new JARVISAlertJSONConverter().toJSON(alert_);

			OutputStream os = connection.getOutputStream();
			os.write(data.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

			String output = "";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e);
		}
		finally {
			if (connection != null) connection.disconnect();
		}

	}

}
