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


@WebServlet("/close")
public class Close extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	
    public Close() {
        super();
        // TODO Auto-generated constructor stub
    }
//end?meetingID=1234567890&password=mp&checksum=1234

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Properties prop = new Properties();
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		prop.load(input);
		String homePage = prop.getProperty("homePage");
		String secret = prop.getProperty("secret");
		
		String idmeeting = request.getParameter("idmeeting");
		String password = request.getParameter("password");
		String url = "meetingID="+idmeeting+"&password="+password;
		String checksumFromUrl = App.checksum("end"+url+secret);
		String urlToSend = homePage+"end?"+url+"&checksum="+checksumFromUrl;
		System.out.println(urlToSend);
		
		String httpQuestionValueXmlString = App.executePost(urlToSend);
		
		//request.getRequestDispatcher("/WEB-INF/views/zalogowany.jsp?info=closeroom").forward(request, response);
		response.sendRedirect("start?info=closeroom");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
