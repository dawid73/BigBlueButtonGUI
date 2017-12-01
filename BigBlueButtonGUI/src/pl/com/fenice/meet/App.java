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

	public String[] loginUser(String login, String password, String url, String domain, String memeber, String filtr) {

		String[] wynik = new String[3];

		try {

			Properties ldapEnv = new Properties();
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, url);
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, login + "@" + domain);
			ldapEnv.put(Context.SECURITY_CREDENTIALS, password);

			DirContext context = new InitialDirContext(ldapEnv);

			String searchFilter = "(&(objectClass=user)(sAMAccountName=" + login + ")(memberOf=" + memeber + "))";
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
				wynik[0] = "0";

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
			System.out.println(e.toString());
		}

		return wynik;
	}

}
