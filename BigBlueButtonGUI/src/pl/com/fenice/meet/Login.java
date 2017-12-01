package pl.com.fenice.meet;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	App app = new App();
	
	
	public Login() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if((String) session.getAttribute("czyzalogowany")!="1") {
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}else {
			request.setAttribute("imienazwisko", (String) session.getAttribute("imienazwisko"));
			request.getRequestDispatcher("/WEB-INF/zalogowany.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println(username + " || " + password );
		
		Properties prop = new Properties();
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		prop.load(input);
		
		String url = prop.getProperty("url");
		String domain = prop.getProperty("domain");
		String memberOf = prop.getProperty("memberOf");
		String filtr = prop.getProperty("filtr");
		System.out.println(memberOf);
		
		String[] tab = app.loginUser(username, password, url, domain, memberOf, filtr);
		
		System.out.println(tab[2]);

		HttpSession session = request.getSession();
		session.setAttribute("czyzalogowany", tab[0]);
		session.setAttribute("login", tab[1]);
		session.setAttribute("imienazwisko", tab[2]);
	
		if(tab[0]=="pass") {
			System.out.println("Bledne haslo");
		}
		if(tab[0]=="permissions") {
			System.out.println("Brak uprawnien");
		}
			
		doGet(request, response);
	
		
	}

}
