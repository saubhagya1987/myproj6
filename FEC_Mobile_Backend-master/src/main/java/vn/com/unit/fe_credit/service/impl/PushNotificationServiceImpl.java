package vn.com.unit.fe_credit.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.service.PushNotificationService;

@Service("PushNotificationService")
public class PushNotificationServiceImpl implements PushNotificationService {

	static final Logger logger = LoggerFactory.getLogger("PushNotificationServiceImpl");

	@Override
	public int pushNotification(String accountID, String message, String messRootID, boolean isParseTextHtml) throws Exception {

		if (isParseTextHtml) {
			message = Jsoup.parse(message).text();
		}

		if (StringUtils.isBlank(message)) {
			message = "";
		} else if (message.length() > 100) {
			message = message.substring(0, 100) + " ...";
		}

		JSONArray jsonArray = new JSONArray();
		jsonArray.put(accountID);
		jsonArray.put(message);
		jsonArray.put(messRootID);
		jsonArray.put(2);
		String parameters = URLEncoder.encode(jsonArray.toString(), "UTF-8");

		String spec = SystemConfig.PUSH_NOTIFICATION_URL + "invoke?adapter=AccountAdapter&procedure=submitNotification&parameters=" + parameters;

		URL url = new URL(spec);
		System.out.println("URL : " + url);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();

		System.out.println("Response Code : " + responseCode);

		String response = "";
		if (200 <= responseCode && responseCode <= 299) {
			response = IOUtils.toString(con.getInputStream());
		} else {
			response = IOUtils.toString(con.getErrorStream());
		}
		System.out.println(response.toString());

		return responseCode;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.com.unit.fe_credit.service.PushNotificationService#sendBroadcastNotification(java.lang.String, long,
	 * java.lang.String[])
	 */
	@Override
	public int sendBroadcastNotification(String msg, long cmsId, String... deviceIds) throws Exception {

		msg = Jsoup.parse(msg).text();

		JSONArray jsonArray = new JSONArray();
		jsonArray.put(msg);
		jsonArray.put(cmsId);
		jsonArray.put(deviceIds);

		System.out.println("##jsonArray##" + jsonArray);

		String parameters = URLEncoder.encode(jsonArray.toString(), "UTF-8");
		String spec = SystemConfig.PUSH_NOTIFICATION_URL + "invoke?adapter=AccountAdapter&procedure=sendBroadcastNotification&parameters="
				+ parameters;

		URL url = new URL(spec);
		System.out.println("URL : " + url);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();

		System.out.println("Response Code : " + responseCode);

		String response = "";
		if (200 <= responseCode && responseCode <= 299) {
			response = IOUtils.toString(con.getInputStream());
		} else {
			response = IOUtils.toString(con.getErrorStream());
		}
		System.out.println(response.toString());

		return responseCode;

	}

}
