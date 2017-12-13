package pl.com.fenice.meet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/connect")
public class Join extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	
	
	
    public Join() {
        super();
        // TODO Auto-generated constructor stub
    }
//join?meetingID=abc123&fullName=Dawid&password=111222&checksum=8bc67ac5b55611075cde92fd040b7889a3171078

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Properties prop = new Properties();
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		prop.load(input);
		String homePage = prop.getProperty("homePage");
		String secret = prop.getProperty("secret");
		
		
		HttpSession session = request.getSession();
		if((String) session.getAttribute("czyzalogowany")!="1"){
			request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
		}
		
		String idmeeting = request.getParameter("idmeeting");
		String imieinaziwsko = request.getParameter("imienazwisko");
		String password = request.getParameter("password");
		imieinaziwsko=App.usunPolskieZnaki(imieinaziwsko);
		System.out.println(imieinaziwsko);
		imieinaziwsko = imieinaziwsko.replaceAll(" ", "+");
		System.out.println(imieinaziwsko);
		String url = "meetingID="+idmeeting+"&fullName="+imieinaziwsko+"&password="+password;
		String checksumFromUrl = App.checksum("join"+url+secret);
		String urlToSend = homePage+"join?"+url+"&checksum="+checksumFromUrl;
		System.out.println(urlToSend);
		
		//String httpQuestionValueXmlString = App.executePost(urlToSend);
		
/*		try {
			java.awt.Desktop.getDesktop().browse(new URI(urlToSend));
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}*/

		
		request.setAttribute("idFromXml", idmeeting);
		request.setAttribute("link", urlToSend);
		response.sendRedirect(urlToSend);
		//request.getRequestDispatcher("/WEB-INF/views/open.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
