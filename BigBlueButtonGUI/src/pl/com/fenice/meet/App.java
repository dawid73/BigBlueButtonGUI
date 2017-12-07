package pl.com.fenice.meet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.File;

import java.io.InputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class App {
	
	public static String usunPolskieZnaki(String s) {
	
		String str = s;
		str = str.replace("π", "a").replace("•", "A");
		str = str.replace("Ê", "c").replace("∆", "c");
		str = str.replace("Í", "e").replace(" ", "E");
		str = str.replace("≥", "l").replace("£", "L");
		str = str.replace("Ò", "n").replace("—", "N");
		str = str.replace("Û", "o").replace("”", "O");
		str = str.replace("ú", "s").replace("å", "S");
		str = str.replace("ü", "z").replace("è", "Z");
		str = str.replace("ø", "z").replace("Ø", "Z");
		str = str.replace(".", "-");
		return str;
	}

	// suma kontrolna do utworznia linku dla BBB
	public static String checksum(String s) {
		String checksum = "";
		try {
			checksum = org.apache.commons.codec.digest.DigestUtils.sha1Hex(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checksum;
	}

	/*
	 * Zwraca wartoúÊ z przes≥anego xml w String
	 */
	public static String getValueToXML(String xml, String value)
			throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(xml)));
		NodeList nList = document.getElementsByTagName("response");

		Node nNode = nList.item(0);
		Element eElement = (Element) nNode;
		String outValue = eElement.getElementsByTagName(value).item(0).getTextContent();

		return outValue;
	}

	public static List<String> getListFromXML(String xml)
			throws ParserConfigurationException, SAXException, IOException {

		List<String> listString = new ArrayList<>();
		
		String meetingName;
		String meetingID;
		String attendeePW;
		String moderatorPW;
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(xml)));
		NodeList nList = document.getElementsByTagName("meeting");
		System.out.println("ilosc pokoi: " + nList.getLength());
	
		
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			
			Element eElement = (Element) nNode;
			
			meetingID = eElement.getElementsByTagName("meetingID").item(0).getChildNodes().item(0).getNodeValue();
			meetingName = eElement.getElementsByTagName("meetingName").item(0).getChildNodes().item(0).getNodeValue();
			moderatorPW = eElement.getElementsByTagName("moderatorPW").item(0).getChildNodes().item(0).getNodeValue();
			attendeePW = eElement.getElementsByTagName("attendeePW").item(0).getChildNodes().item(0).getNodeValue();
			
			System.out.println("Spotkanie nr: "+temp+" || " + meetingName +" || " + meetingID +" || " + attendeePW +" || " + moderatorPW);
			
			listString.add(meetingID);
			listString.add(meetingName);
			listString.add(moderatorPW);
			listString.add(attendeePW);
	
			
		}
		
		return listString;
	}

	public static String executePost(String targetURL) {
		HttpURLConnection connection = null;

		try {

			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			// wr.writeBytes(urlParameters);
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public String[] loginUser(String login, String password, String url, String domain, String memeber, String filtr) {

		String[] wynik = new String[3];

		try {

			Properties ldapEnv = new Properties();
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, url);
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, login + "@" + domain);
			ldapEnv.put(Context.SECURITY_CREDENTIALS, password);

			try {
				DirContext context = new InitialDirContext(ldapEnv);
				try {
					String searchFilter = "(&(objectClass=user)(sAMAccountName=" + login + ")(memberOf=" + memeber
							+ "))";
					String[] requiredAttributes = { "sn", "cn", "memberOf", "displayName" };

					SearchControls controls = new SearchControls();
					controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
					controls.setReturningAttributes(requiredAttributes);
					NamingEnumeration users = context.search(filtr, searchFilter, controls);
					SearchResult seachResault = null;
					String commonName = null;
					String surName = null;
					String member = null;
					String displayname = null;

					wynik[1] = login;

					if (users == null || !users.hasMore()) {
						wynik[0] = "permissions";

					} else {
						wynik[0] = "1";
					}

					while (users.hasMore()) {
						seachResault = (SearchResult) users.next();
						Attributes attr = seachResault.getAttributes();

						commonName = attr.get("cn").get(0).toString();
						surName = attr.get("sn").get(0).toString();
						member = attr.get("memberOf").get(0).toString();
						displayname = attr.get("displayName").get(0).toString();

						wynik[2] = displayname;

						return wynik;

					}
				} catch (Exception e) {
					wynik[0] = "permissions";
				}

			} catch (Exception e) {
				wynik[0] = "pass";
				System.out.println(e.toString());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return wynik;
	}

}
