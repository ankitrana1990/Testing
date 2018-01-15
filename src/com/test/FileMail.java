package com.test;

some changes are done

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class FileMail {

	
	public static void main(String[] args) {
		
		Set<String> ccRecipients = new TreeSet<String>();
		Set<String> toRecipients = new TreeSet<String>();
		Map<String, String> fileDetails = new HashMap<String, String>();
		
		ccRecipients.add("rana.goldmine@gmail.com");
		toRecipients.add("ankit.rana@nvish.com");
		String from = "suraj.verma@nvish.com";
		
		
		String message= "Please find attachment for details";
		String subject= "This is a test mail subject";
		
		
		String fileName = "This is a text file .txt";
		String filePath = "D://temp//";
		
		
		fileDetails.put(fileName, filePath);
		
		
		
		
		
		
		String mail = postMail(toRecipients, subject, message, from, ccRecipients, fileDetails);
		
		if(mail.equals("true"))
		{
			System.out.println("Mail sent");
		}
		else{
			System.out.println("Mail not sent");
		}

	}



	public static String postMail(Set<String> recipients, String subject, String message, String from,
	        Set<String> ccManagerEmailIds, Map<String, String> fileDetails)
		{
		try
			{
			Properties props = new Properties();
			props.put("mail.smtp.host", "mail.corp.hds.com");
			props.put("mail.smtp.auth", "false");
	

			 Session session = Session.getDefaultInstance(props,   new javax.mail.Authenticator() {  
			      protected PasswordAuthentication getPasswordAuthentication() {  
			    return new PasswordAuthentication("","");  
			      }  
			    }); 
			
			
			MimeBodyPart contentBodyPart = new MimeBodyPart();
			contentBodyPart.setContent(message,"text/html;charset=utf-8");
			InternetAddress iaSender = new InternetAddress(from);
			int toMailSize = getFinalRecipientsList(recipients).size();
			int ccMailSize = getFinalRecipientsList(ccManagerEmailIds).size();
		
			int indexTo = 0;
			int indexCC = 0;
			
			InternetAddress[] addressTo = new InternetAddress[toMailSize];
			InternetAddress[] addressToCC = new InternetAddress[ccMailSize];
		
			
			for (String recipient : recipients)
				{
				
					addressTo[indexTo] = new InternetAddress(recipient);
					indexTo++;
					
				}
			
			for (String ccManagerEmailId : ccManagerEmailIds)
				{
				
					addressToCC[indexCC] = new InternetAddress(ccManagerEmailId);
					indexCC++;
				
				}
			
			
			MimeMessage mimeMessage = new MimeMessage(session);
			
			mimeMessage.setFrom(iaSender);
			mimeMessage.setSubject(subject);
			
			mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);
			
			
			if ( addressToCC != null&&addressToCC.length > 0 )
			{
				mimeMessage.addRecipients(Message.RecipientType.CC, addressToCC);
			}
			
			
			mimeMessage.setContent(addAttachment(fileDetails, contentBodyPart));
			Transport.send(mimeMessage);
			
			return "true";
			}
		
		catch (Exception e)
			{
				e.printStackTrace();
			
			}
		return "true";
		}

	private static MimeMultipart addAttachment(Map<String, String> fileDetails,
	        MimeBodyPart contentBodyPart) throws MessagingException, IOException
		{
		MimeMultipart mimeMultipart = new MimeMultipart();
		ByteArrayOutputStream outputStream = null;
		FileInputStream foStream = null;
		try
			{
			mimeMultipart.addBodyPart(contentBodyPart);
			for (String fileName : fileDetails.keySet())
				{
				
				File file = new File(fileDetails.get(fileName) + fileName);
				foStream = new FileInputStream(file);

				String fileExtension =
				        fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				outputStream = new ByteArrayOutputStream();
				int read;
				byte[] buff = new byte[256];
				while ((read = foStream.read(buff)) != -1)
					{
					outputStream.write(buff, 0, read);
					}
				byte[] bytes = outputStream.toByteArray();
				DataSource dataSource =
				        new ByteArrayDataSource(bytes, "application/" + fileExtension);
				contentBodyPart = new MimeBodyPart();
				contentBodyPart.setDataHandler(new DataHandler(dataSource));
				if (!isEmpty(fileName))
				{
					contentBodyPart.setFileName(fileName);
				
				}
				
				mimeMultipart.addBodyPart(contentBodyPart);
				}
			}
		finally
			{
			if (null != outputStream)
				{
				try
					{
					outputStream.close();
					outputStream = null;
					foStream.close();
					}
				catch (Exception e)
					{
					
					e.printStackTrace();
					}
				}
			}
		return mimeMultipart;
		}
	
	public static boolean isEmpty(String appPropertiesDir)
	{
	
	if (appPropertiesDir == null || appPropertiesDir.trim().length() == 0)
		return true;
	return false;
	}

	public static List<String> getSplitList(String value)
	{
	List<String> splitList = new ArrayList<String>();

	if (!isEmpty(value) && !value.equalsIgnoreCase(null) && value != null)
		{
		String[] emailUserCC = value.split(",");
		for (String emailCC : emailUserCC)
			{
			splitList.add(emailCC);
			}
		}
	return splitList;
	}
	private static Set<String> getFinalRecipientsList(Set<String> recipentsList)
		{
		Set<String> finalRecipientsList = new TreeSet<String>();
		for (String emailId : recipentsList)
			{
				finalRecipientsList.add(emailId);
			}
		return finalRecipientsList;
		}     

	private class SMTPAuthenticator extends javax.mail.Authenticator
	{
		public PasswordAuthentication getPasswordAuthentication()
			{
			String username = "";
			String password = "";
			return new PasswordAuthentication(username, password);
			}
	}


	
}
