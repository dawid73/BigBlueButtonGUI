package pl.com.fenice.meet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
	 * Zwraca wartoœæ z przes³anego xml w String
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
	
	
	public int loginUser() {
		
		try {
			Properties ldapEnv = new Properties();
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, "ldap://tyr:389");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, "tester@seberus.local");
			ldapEnv.put(Context.SECURITY_CREDENTIALS, "tester123");
			
			DirContext context = new InitialDirContext(ldapEnv);
			
			String searchFilter = "(&(objectClass=user)(sAMAccountName=tester)(memberOf=CN=GPO_7nie_wygaszaczBlokada,OU=Fenice,DC=seberus,DC=local))";
			String[] requiredAttributes = {"sn", "cn", "memberOf"};
			
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			controls.setReturningAttributes(requiredAttributes);
			NamingEnumeration users = context.search("ou=TEST,dc=seberus,dc=local", searchFilter, controls);
			SearchResult seachResault = null;
			String commonName = null;
			String surName = null;
			String member = null;

			if(users == null) {
				System.out.println("nie nalezy do grupy");
			}else {
				System.out.println("nalezy do grupy");
			}
			
			while(users.hasMore()) {
				seachResault = (SearchResult) users.next();
				Attributes attr = seachResault.getAttributes();
				
				commonName=attr.get("cn").get(0).toString();
				surName=attr.get("sn").get(0).toString();
				member=attr.get("memberOf").get(0).toString();
				
				System.out.println("Name: " + commonName);
				System.out.println("Surname: " + surName);
				System.out.println("Member: " + member);
			}
			
			
			
		} catch (Exception e) {
			System.out.println("..........blad...............");
			System.out.println(e.toString());
		}
		
		
		return 1;
	}

}
