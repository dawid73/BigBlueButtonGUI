package pl.com.fenice.meet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/connect")
public class Join extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	String homePage = "http://10.56.1.248/bigbluebutton/api/";
	String secret = "0a82ee4cb1b20f731e98b47dbf329f0a";
	
    public Join() {
        super();
        // TODO Auto-generated constructor stub
    }
//join?meetingID=abc123&fullName=Dawid&password=111222&checksum=8bc67ac5b55611075cde92fd040b7889a3171078

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idmeeting = request.getParameter("idmeeting");
		
		String url = "meetingID="+idmeeting+"&fullName=Dawid&password=333444";
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
		request.getRequestDispatcher("/WEB-INF/views/open.jsp").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
