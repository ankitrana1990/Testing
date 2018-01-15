package com.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;



public class LdapTwo {

	public static void main(String[] args) {


		
		String payload = generateEmailReqData("Ankit.Rana@Hitachivantara.Com");
	    String result = validateLDAPUser(payload, "http://vwwwapp02d:8888/auth/services/ldap/emailvalidate");
		if (result == null) {
			
			
		System.out.println("null");	
		}
		Map<String, String> resultMap = parseResult(result);
	
		
		
		if (resultMap != null && "true".equalsIgnoreCase(resultMap.get("status")))
			{
		System.out.println("true");
			}
		
		else{
			
			System.out.println("false");
		}
	}
	
	
	public static String generateEmailReqData(String email)
	{
	return "<userdetail><email>" + email + "</email></userdetail>";
	}

	
	public static String validateLDAPUser(String payload, String serviceURL)
	{
	
	try
		{
		StringBuffer sb = new StringBuffer();
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost postReq = new HttpPost(serviceURL);
		StringEntity input = new StringEntity(payload, "UTF-8");
		input.setContentType("application/xml");
		postReq.setEntity(input);
		HttpResponse response = httpClient.execute(postReq);
		if (response.getStatusLine().getStatusCode() != 200)
			return null;
		BufferedReader br =
		        new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
		String output;
		while ((output = br.readLine()) != null)
			{
			sb.append(output);
			}
		httpClient.getConnectionManager().shutdown();
		return sb.toString();
		}
	catch (Exception e)
		{
		e.printStackTrace();
		}
	return null;
	}
	
	public static Map<String, String> parseResult(String result)
	{
	
	Map<String, String> userDataMap = null;
	try
		{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(result));
		Document doc = builder.parse(is);
		NodeList rootElementList = doc.getChildNodes();
		for (int i = 0; i < rootElementList.getLength(); i++)
			{
			Node rootNode = rootElementList.item(i);
			if (rootNode.getNodeType() == Node.ELEMENT_NODE)
				{
				Element rootElement = (Element) rootNode;
				NodeList childrenList = rootElement.getChildNodes();
				for (int k = 0; k < childrenList.getLength(); k++)
					{
					Node childrenNode = childrenList.item(k);
					if (childrenNode.getNodeType() == Node.ELEMENT_NODE)
						{
						if (userDataMap == null)
							userDataMap = new HashMap<String, String>();
						Element childrenElement = (Element) childrenNode;
						if (childrenElement.getFirstChild() != null)
							{
							userDataMap.put(childrenElement.getNodeName(), childrenElement
							        .getFirstChild().getNodeValue());
							}
						}
					}
				}
			}
		}
	catch (SAXParseException err)
		{
			err.printStackTrace();
		}
	catch (Exception ex)
		{
			ex.printStackTrace();
		}
	return userDataMap;
	}
	
	
}
