package pl.com.fenice.meet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jdt.internal.compiler.apt.util.Archive;
import org.xml.sax.SAXException;

@WebServlet("/start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Start() {
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
		if ((String) session.getAttribute("czyzalogowany") != "1") {
			if ((String) session.getAttribute("info") == "badpass") {
				request.getRequestDispatcher("/WEB-INF/views/welcome.jsp?info=zlehaslo").forward(request, response);
			}
			if ((String) session.getAttribute("info") == "permissions") {
				request.getRequestDispatcher("/WEB-INF/views/welcome.jsp?info=brakuprawnien").forward(request,
						response);
			}

			request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
		} else {

			String url = "getMeetings";
			String checksumFromUrl = App.checksum(url + secret);
			String urlToSend = homePage + url + "?checksum=" + checksumFromUrl;
			System.out.println(urlToSend);

			String httpQuestionValueXmlString = App.executePost(urlToSend);

			List<String> roomList = new ArrayList<>();

			try {
				String httpQuestionValueXml = App.getValueToXML(httpQuestionValueXmlString, "returncode");
				System.out.println(httpQuestionValueXml);

				String room = "";
				List<Room> listRooms = new ArrayList<Room>();
				
				try {
					room = App.getValueToXML(httpQuestionValueXmlString, "messageKey");
					System.out.println(room);
					listRooms.clear();
				} catch (Exception e) {

				}
				try {
					room = App.getValueToXML(httpQuestionValueXmlString, "meetingName");

					roomList = App.getListFromXML(httpQuestionValueXmlString);

					int iloscPokoi = roomList.size() / 4;
					System.out.println(iloscPokoi);

					System.out.println(roomList.get(0));
	
					
					int r = 1;
					int i = 0;
					while ( r <= iloscPokoi) {
			
						listRooms.add(new Room(roomList.get(i), roomList.get(i+1), roomList.get(i+2), roomList.get(i+3)));
					
						i = i+4;
						r++;
					}

					request.setAttribute("listRooms", listRooms);
					
					
					
					//request.setAttribute("lista", roomList);

					
					

				} catch (Exception e) {

				}


			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("imienazwisko", (String) session.getAttribute("imienazwisko"));
			request.getRequestDispatcher("/WEB-INF/views/zalogowany.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
