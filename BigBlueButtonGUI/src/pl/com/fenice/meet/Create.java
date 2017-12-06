package pl.com.fenice.meet;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@WebServlet("/create")
public class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;


	
	public Create() {
		super();

	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Properties prop = new Properties();
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		prop.load(input);
		String homePage = prop.getProperty("homePage");
		String secret = prop.getProperty("secret");
		
		HttpSession session = request.getSession();
		if((String) session.getAttribute("czyzalogowany")!="1"){
			request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
		}
		
		String nameRoom = request.getParameter("nameRoom");
		

		session.setAttribute("nameroom", nameRoom);
		
		request.setAttribute("nameRoom", nameRoom);
		nameRoom = nameRoom.replaceAll(" ", "+");
		Random rand = new Random(); 
		int meetingID = rand.nextInt(4000); 
		
		String adminName = request.getParameter("adminName");

		String url = "name="+nameRoom+"&meetingID="+meetingID+"&attendeePW=111222&moderatorPW=333444";
		String checksumFromUrl = App.checksum("create"+url+secret);
		String urlToSend = homePage+"create?"+url+"&checksum="+checksumFromUrl;
		System.out.println(urlToSend);
		
		String httpQuestionValueXmlString = App.executePost(urlToSend);
		
		try {
			String httpQuestionValueXml = App.getValueToXML(httpQuestionValueXmlString, "returncode");
			System.out.println(httpQuestionValueXml);
			
			
			String idFromXml = App.getValueToXML(httpQuestionValueXmlString, "meetingID");
			System.out.println(idFromXml);
			
			
			
			request.setAttribute("idFromXml", idFromXml);
			if(httpQuestionValueXml.equals("SUCCESS")) {
				request.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(request, response);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
