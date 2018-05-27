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

public class SendMessSoapServies {

	public static String sendMess(String phone, String mess, String name) {
		String value = null;
		try {

			// if (true) {
			// throw new Exception();
			// }

			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server

			String url = SystemConfig.SEND_MESS_SOAP_SERVIES_URL + "SMS" + "?wsdl";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(phone, mess), url);

			value = getValueSoap(soapResponse, name);
			soapConnection.close();

		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
		}
		return value;
	}

	private static String getValueSoap(SOAPMessage soapResponse, String name) throws Exception {

		soapResponse.writeTo(System.out);
		SOAPBody soapBody = soapResponse.getSOAPBody();
		Iterator<?> iteratorRoot = soapBody.getChildElements();
		Iterator<?> iterator2, iterator3;
		SOAPElement se = null;
		String tagName = null;
		while (iteratorRoot.hasNext()) {
			se = (SOAPElement) iteratorRoot.next();
			iterator2 = se.getChildElements();
			while (iterator2.hasNext()) {
				se = (SOAPElement) iterator2.next();
				tagName = se.getElementName().getLocalName();
				iterator3 = se.getChildElements();
				while (iterator3.hasNext()) {
					se = (SOAPElement) iterator3.next();
					tagName = se.getElementName().getLocalName();
					if (tagName.toString().equals(name)) {
						return se.getValue();
					}
				}

			}
		}
		return null;

	}

	private static SOAPMessage createSOAPRequest(String phone, String mess) throws Exception {
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
		envelope.addNamespaceDeclaration("sms", "http://fecredit.com.vn/SMS");

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("SendSMS", "sms");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("Sys");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("TransID");

		String uniqueID = UUID.randomUUID().toString();

		soapBodyElem2.addTextNode(uniqueID);

		SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("RequestorID");
		soapBodyElem3.addTextNode("MOBILE");

		SOAPElement soapBodyElem4 = soapBodyElem1.addChildElement("DateTime");
		final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
		soapBodyElem4.addTextNode(new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT).format(new Date()));

		SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("request");
		SOAPElement soapBodyElem6 = soapBodyElem5.addChildElement("Phone");
		soapBodyElem6.addTextNode(phone);
		SOAPElement soapBodyElem7 = soapBodyElem5.addChildElement("Message");
		soapBodyElem7.addTextNode(mess);

		soapMessage.saveChanges();

		soapMessage.writeTo(System.out);

		return soapMessage;
	}

}
