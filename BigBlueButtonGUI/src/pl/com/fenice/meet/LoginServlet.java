package pl.com.fenice.meet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns="/login.do")
public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(false);
		System.out.println((String) session.getAttribute("czyzalogowany")); 
		System.out.println((String) session.getAttribute("login")); 
		System.out.println((String) session.getAttribute("imienazwisko")); 
		
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		
		
		
	}

	

}
