package vn.com.unit.webservice;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import vn.com.unit.fe_credit.config.SystemConfig;

public class PushNotification {

	public static void pushNotification(String accountID, String message, String messRootID) {
		try {

			// DEV
			// URL("http://wltest.sokhambenh.vn:9081/fecredit/invoke?adapter=AccountAdapter&procedure=submitNotification&parameters=["+accountID+",\'"+message+"\',"+messRootID+",2]");

			// UAT
			// URL("http://10.30.135.64:9080/mobilews/invoke?adapter=AccountAdapter&procedure=submitNotification&parameters=["+accountID+",\'"+message+"\',"+messRootID+",2]");

			// PRO
			// URL("http://10.30.135.69/mobilews/invoke?adapter=AccountAdapter&procedure=submitNotification&parameters=["+accountID+",\'"+message+"\',"+messRootID+",2]");

			Integer maxLenghMessage = 100;
			if (StringUtils.isBlank(message)) {
				message = "";
			}

			if (message.length() > maxLenghMessage) {
				message = message.substring(0, maxLenghMessage) + " ...";
			}

			JSONArray jsonArray = new JSONArray();
			jsonArray.put(accountID);
			// jsonArray.put(StringEscapeUtils.escapeXml(message));
			jsonArray.put(message);
			jsonArray.put(messRootID);
			jsonArray.put(2);

			System.out.println("##jsonArray##" + jsonArray);

			String parameters = URLEncoder.encode(jsonArray.toString(), "UTF-8");
			String spec = SystemConfig.PUSH_NOTIFICATION_URL + "invoke?adapter=AccountAdapter&procedure=submitNotification&parameters=" + parameters;

			URL url = new URL(spec);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			// BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			// String output;
			// // System.out.println("Output from Server .... \n");
			// while ((output = br.readLine()) != null) {
			// // System.out.println(output);
			// }

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
