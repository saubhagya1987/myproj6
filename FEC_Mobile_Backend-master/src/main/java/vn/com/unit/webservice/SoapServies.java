package vn.com.unit.webservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import vn.com.unit.fe_credit.config.SystemConfig;

public class SoapServies {

	// dev
	// private static String urlSoap = "http://115.79.47.252:7801/";

	// uat
	// private static String urlSoap = "http://uat-app60.deltavn.vn:7800/";

	// pro
	// private static String urlSoap = "http://esb-prod.deltavn.vn:7800/";

	public static String getValueCustomer(String id, String cellPhone, String name) {
		String value = null;
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server

			String url = SystemConfig.SOAP_SERVIES_URL + "Customer" + "?wsdl";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(id, cellPhone), url);

			value = getValueSoap(soapResponse, name);
			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
		return value;
	}

	private static String getValueSoap(SOAPMessage soapResponse, String name) throws Exception {
		// soapResponse.writeTo(System.out);
		// System.out.println();
		SOAPBody soapBody = soapResponse.getSOAPBody();
		Iterator iteratorRoot = soapBody.getChildElements();
		Iterator iterator2;
		SOAPElement se = null;
		String tagName = null;
		while (iteratorRoot.hasNext()) {
			se = (SOAPElement) iteratorRoot.next();
			iterator2 = se.getChildElements();
			while (iterator2.hasNext()) {
				se = (SOAPElement) iterator2.next();
				tagName = se.getElementName().getLocalName();
				if (tagName.toString().equals(name)) {
					return se.getValue();
				}
			}
		}
		return null;
	}

	private static SOAPMessage createSOAPRequest(String id, String celphone) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		/*
		 * Construct SOAP Request Message: <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
		 * xmlns:sam="http://samples.axis2.techdive.in"> <soap:Header/> <soap:Body> <sam:getStudentName>
		 * <!--Optional:--> <sam:rollNumber>3</sam:rollNumber> </sam:getStudentName> </soap:Body> </soap:Envelope>
		 */

		// SOAP Envelope

		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("cus", "http://Customer");

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("GetCustomerID", "cus");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("Sys");

		String uniqueID = UUID.randomUUID().toString();
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("TransID");
		soapBodyElem2.addTextNode(uniqueID);

		SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("RequestorID");
		soapBodyElem3.addTextNode("MOBILE");

		SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("DateTime");
		final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
		soapBodyElem4.addTextNode(new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT).format(new Date()));

		SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("customerIdentifier");
		SOAPElement soapBodyElem6 = soapBodyElem5.addChildElement("PersonalID");
		soapBodyElem6.addTextNode(id);
		SOAPElement soapBodyElem7 = soapBodyElem5.addChildElement("RegistredPhone");
		soapBodyElem7.addTextNode(celphone);

		soapMessage.saveChanges();

		// Check the input
		// System.out.println("Request SOAP Message = ");
		// soapMessage.writeTo(System.out);
		// System.out.println();
		return soapMessage;
	}

	private void SOAPConnection() {

	}

}
