import java.util.Properties;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.MessagingException;
import javax.mail.Transport;

class SendEmail 
{
	private String emailId;
	private String emailPassword;

	// We need to configure the properties for the mail
	private Properties props;
	private Session session;

	SendEmail(String emailId,String emailPassword) 
	{
		this.emailId=emailId;
		this.emailPassword=emailPassword;
		props = new Properties();
		addProperties();
	}

	private void addProperties()
	{
		// We need to configure the following fields
		// mail.smtp.auth for authentication - required in case of gmail
		// mail.smtp.starttls.enable for TLS encryption
		// mail.smtp.host for host - smtp.gmail.com for gmail server
		// mail.smtp.port for port - 587 for gmail

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}

	// We need to login in the gmail account
	private void login() 
	{
		session = Session.getInstance(props,
			new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {  
		    	   return new PasswordAuthentication(emailId,emailPassword);  
			   }  
			});
		System.out.println("Login to account successful");
	}

	private Message prepareMessage(String recipientMailId,String subject,String messageText)
	{
		try {
			login();
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailId));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientMailId));

			// If we need a CC or BCC simply use
			// message.setRecipient(Message.RecipientType.CC, new InternetAddress(CCMailId));
			// message.setRecipient(Message.RecipientType.BCC, new InternetAddress(BCCMailId));

			message.setSubject(subject);
			message.setText(messageText);

			return message;
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
		return null;	
	}

	public void sendMessage(String recipientMailId,String subject,String messageText) throws Exception
	{
		System.out.println("Preparing message to send");
		Message message = prepareMessage(recipientMailId,subject,messageText);
		System.out.println("Message prepared");

		Transport transport = session.getTransport();
		// Transport message 
		transport.connect();
		Transport.send(message);
		transport.close();
		System.out.println("Message sent successfully");
	}
}


public class Main
{
	public static void main(String args[]) throws Exception
	{
		SendEmail se = new SendEmail("digielector@gmail.com","mvsr7902@lnm");
		se.sendMessage("19ucs035@lnmiit.ac.in","Hello subject","Hello body");
	}
}
