package pl.com.fenice.meet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


@WebServlet("/start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    public Start() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Properties prop = new Properties();
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		prop.load(input);
		String homePage = prop.getProperty("homePage");
		String secret = prop.getProperty("secret");
		
		HttpSession session = request.getSession();
		if((String) session.getAttribute("czyzalogowany")!="1"){
			if((String) session.getAttribute("info")=="badpass"){
				request.getRequestDispatcher("/WEB-INF/views/welcome.jsp?info=zlehaslo").forward(request, response);
			}
			if((String) session.getAttribute("info")=="permissions"){
				request.getRequestDispatcher("/WEB-INF/views/welcome.jsp?info=brakuprawnien").forward(request, response);
			}
			
			request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
		}else {
			
			String url = "getMeetings";
			String checksumFromUrl = App.checksum(url+secret);
			String urlToSend = homePage+url+"?checksum="+checksumFromUrl;
			System.out.println(urlToSend);
			
/*			String httpQuestionValueXmlString = App.executePost(urlToSend);
			
			try {
				String httpQuestionValueXml = App.getValueToXML(httpQuestionValueXmlString, "returncode");
				System.out.println(httpQuestionValueXml);
				
				String nameRoom = App.getValueToXML(httpQuestionValueXmlString, "meetingName");
				System.out.println(nameRoom);
				
				String idFromXml = App.getValueToXML(httpQuestionValueXmlString, "meetingID");
				System.out.println(idFromXml);
				

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			
			
			request.setAttribute("imienazwisko", (String) session.getAttribute("imienazwisko"));
			request.getRequestDispatcher("/WEB-INF/views/zalogowany.jsp").forward(request, response);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
