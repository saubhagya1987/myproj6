package vn.com.unit.fe_credit.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sun.mail.imap.IMAPFolder;
@Component
public class MailUtil {
	@Autowired
	MessageSource messageSource;
	public static String keyword;
	
	private static final Logger LOG = LoggerFactory.getLogger(MailUtil.class);

	public static void checkInboxMail(String host, String email, String password, String keyword) {
		MailUtil.keyword = keyword;
		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");

			Session session;

			session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect(host, email, password);

			IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message messageTemp[] = folder.search(unseenFlagTerm);

			// creates a search criterion
			SearchTerm searchCondition = new SearchTerm() {
				@Override
				public boolean match(Message message) {
					try {
						if (message.getSubject().toUpperCase().contains(MailUtil.keyword)) {
							return true;
						}
					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
					return false;
				}
			};

			Message message[] = folder.search(searchCondition, messageTemp);

			int j = message.length - 1;
			try {
				for (int i = j; i >= 0; i--) {
					System.out.println("Message " + (i + 1));
					System.out.println("From : " + message[i].getFrom()[0]);
					System.out.println("Subject : " + message[i].getSubject());
					String content = "";
					if (message[i].getContent() instanceof Multipart) {

						Multipart multipart = (Multipart) message[i].getContent();

						for (int bp = 0; bp < multipart.getCount(); bp++) {

							BodyPart bodyPart = multipart.getBodyPart(bp);

							String disposition = bodyPart.getDisposition();

							if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) {
								System.out.println("Mail have some attachment");
								DataHandler handler = bodyPart.getDataHandler();
								System.out.println("file name : " + handler.getName());
							} else {
								content = bodyPart.getContent().toString();
							}
						}
					} else {
						content = message[i].getContent().toString();
					}
					content=getPlainTextFromHtml(content);
					System.out.println("Body : " + content);

				}
			} catch (IOException ex) {
			}
			// System.out.println(newMsg);

			folder.close(false);
			store.close();
		} catch (MessagingException e) {
			System.out.println("Error: " + e);
		}
	}
	
	public static Properties readInput(String keyword1,String keyword2) {
		 Properties props = new Properties();
		 try{
		     String input = "Decision=Approve           Comment=           Test  thôi";
		     int indexkeyword1= input.lastIndexOf(keyword1);
		     int indexkeyword2= input.lastIndexOf(keyword2);
		     props.put(keyword1, input.substring(indexkeyword1+(keyword1.length())+1, indexkeyword2));
		     props.put(keyword2, input.substring(indexkeyword2+(keyword2.length())+1,input.length()));
		 }catch(Exception e){
			 
		 }
	     return props;
	}
	
	
	
	 public static String getPlainTextFromHtml(String htmlString){
		 Document doc = Jsoup.parse(htmlString);
		 return doc.body().text();
	 }
	 
	 @Async  
	 public  void sendEmail(List<String> emails,final Locale locale,String mailMessage, String subject) {
		 System.out.println("Going to send Mail");
			Properties props = new Properties();
			props.put("mail.smtp.auth", messageSource.getMessage("mail.smtp.auth", null, locale));
			props.put("mail.smtp.starttls.enable", messageSource.getMessage("mail.smtp.starttls.enable", null, locale));
			props.put("mail.smtp.host",messageSource.getMessage("mail.smtp.host", null, locale));
			props.put("mail.smtp.port",messageSource.getMessage("mail.smtp.port", null, locale));
			//props.put("mail.smtp.ssl.trust",messageSource.getMessage("mail.smtp.ssl.trust", null, locale));
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					
					return new PasswordAuthentication(messageSource.getMessage("from.mail.id", null, locale), messageSource.getMessage("mail.password", null, locale));
				}
			  });

			try {
					
				   Message message = new MimeMessage(session);				   
				   message.setFrom(new InternetAddress(messageSource.getMessage("from.mail.id", null, locale)));
				   message.setSubject(subject);
				   message.setContent(mailMessage, "text/html");
				   for(Object email : emails){    
					   System.out.println("eMail ...."+email);
				     message.setRecipient(Message.RecipientType.TO, new InternetAddress((String)email));
				     Transport.send(message);
				   }

				   System.out.println("MAIL SENT...............");

				  } catch (Exception e) {
				   LOG.error("Exceeption Occure in Seding Mail ....",e);
				   StringWriter writer = new StringWriter();
				   e.printStackTrace(new PrintWriter(writer));
				   System.out.println(writer.toString());
				}
			
	}

	public static void main(String[] args) {
		readInput("Decision","Comment"); 
//		checkInboxMail("imap.gmail.com", "nguyenductuan1990it@gmail.com", "tuan151090", "");
	}

}