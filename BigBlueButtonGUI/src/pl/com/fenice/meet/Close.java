package pl.com.fenice.meet;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/close")
public class Close extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	String homePage = "http://10.56.1.248/bigbluebutton/api/";
	String secret = "0a82ee4cb1b20f731e98b47dbf329f0a";
	
    public Close() {
        super();
        // TODO Auto-generated constructor stub
    }
//end?meetingID=1234567890&password=mp&checksum=1234

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idmeeting = request.getParameter("idmeeting");
		
		String url = "meetingID="+idmeeting+"&password=333444";
		String checksumFromUrl = App.checksum("end"+url+secret);
		String urlToSend = homePage+"end?"+url+"&checksum="+checksumFromUrl;
		System.out.println(urlToSend);
		
		String httpQuestionValueXmlString = App.executePost(urlToSend);
		
	

		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
