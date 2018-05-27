package vn.com.unit.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vn.com.unit.fe_credit.config.SystemConfig;

public class NetClientGet {
	// DEV
	// private static String urlData = "http://115.79.47.252:7800/Mobile_getContract/";

	// UAT
	// private static String urlData = "http://uat-app60.deltavn.vn:7800/Mobile_getContract/";

	// PRO
	// private static String urlData = "http://esb-prod.deltavn.vn:7800/Mobile_getContract/";

	public static void syncMessage() {

		try {

			URL url = new URL(SystemConfig.NET_CLIENT_GET_URL + "syncMessage");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"status\":\"1\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			// BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			// System.out.println("##BufferedReader##" + br);

			// String output;
			// System.out.println("Output from Server .... syncMessage\n");
			// while ((output = br.readLine()) != null) {
			// // System.out.println(output);
			// }

			conn.disconnect();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static StringBuffer getContractList(String customerID) {
		try {

			URL url = new URL(SystemConfig.NET_CLIENT_GET_URL + "getContractList");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"customerID\":\"" + customerID + "\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuffer output1 = new StringBuffer();

			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				output1.append(output.toString());
				// System.out.println(output);
			}
			conn.disconnect();
			return output1;
		} catch (MalformedURLException e) {

			e.printStackTrace();
			return null;

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static StringBuffer searchContract(String contractNum, String cmnd, String phone) {
		try {

			URL url = new URL(SystemConfig.NET_CLIENT_GET_URL + "searchContract");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"contractNum\":\"" + contractNum + "\",\"cmnd\":\"" + cmnd + "\",\"phone\":\"" + phone + "\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuffer output1 = new StringBuffer();

			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				output1.append(output.toString());
				// System.out.println(output);
			}
			conn.disconnect();
			return output1;
		} catch (MalformedURLException e) {

			e.printStackTrace();
			return null;

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static StringBuffer getContractDetail(String customerID, String contractNum) {
		try {

			URL url = new URL(SystemConfig.NET_CLIENT_GET_URL + "getContractDetail");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"customerID\":\"" + customerID + "\",\"contractNum\":\"" + contractNum + "\"}";
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuffer output1 = new StringBuffer();
			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				output1.append(output.toString());
				// System.out.println(output);
			}
			conn.disconnect();
			return output1;
		} catch (MalformedURLException e) {

			e.printStackTrace();
			return null;

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static StringBuffer getInstallmentList(String customerID, String contractNum) {
		try {

			URL url = new URL(SystemConfig.NET_CLIENT_GET_URL + "getInstallmentList");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"customerID\":\"" + customerID + "\",\"contractNum\":\"" + contractNum + "\"}";
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuffer output1 = new StringBuffer();
			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				output1.append(output.toString());
				// System.out.println(output);
			}
			conn.disconnect();
			return output1;
		} catch (MalformedURLException e) {

			e.printStackTrace();
			return null;

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

	public static StringBuffer getPaymentHistory(String customerID, String contractNum) {
		try {

			URL url = new URL(SystemConfig.NET_CLIENT_GET_URL + "getPaymentHistory");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"customerID\":\"" + customerID + "\",\"contractNum\":\"" + contractNum + "\"}";
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuffer output1 = new StringBuffer();
			// System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				output1.append(output.toString());
				// System.out.println(output);
			}
			conn.disconnect();
			return output1;
		} catch (MalformedURLException e) {

			e.printStackTrace();
			return null;

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}

	}

}
