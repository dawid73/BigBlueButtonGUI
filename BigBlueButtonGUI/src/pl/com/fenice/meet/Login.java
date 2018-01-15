package pl.com.fenice.meet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

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
		// session.setAttribute("czyzalogowany", "0");
		System.out.println("czy zalogowany: " + session.getAttribute("czyzalogowany"));
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
			request.setAttribute("imienazwisko", (String) session.getAttribute("imienazwisko"));

			 response.sendRedirect("start");

			//request.getRequestDispatcher("/WEB-INF/views/zalogowany.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// System.out.println(username + " || " + password );

		Properties prop = new Properties();
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		prop.load(input);

		String url = prop.getProperty("url");
		String domain = prop.getProperty("domain");
		String memberOf = prop.getProperty("memberOf");
		String filtr = prop.getProperty("filtr");

		String adminLogin = prop.getProperty("adminLogin");
		String adminPass = prop.getProperty("adminPass");
		// System.out.println(memberOf);

		// LOGOWANIE ADMINA oraz WIDZA //

		//List<Room> listRooms = (List<Room>) session.getAttribute("listapokoi");
		//System.out.println(listRooms.toString());

		if (username.equals(adminLogin) && (password.equals(adminPass))) {
			session.setAttribute("czyzalogowany", "1");
			session.setAttribute("login", "Administrator");
			session.setAttribute("imienazwisko", "Administrator");

			doGet(request, response);

		}
		/*if (username.equals("user") && listRooms.toString().trim().toLowerCase().contains(password)) {

			session.setAttribute("czyzalogowany", "1");
			session.setAttribute("login", "Gosc");
			session.setAttribute("imienazwisko", "Gosc");

			doGet(request, response);

		} */else {

			String[] tab = app.loginUser(username, password, url, domain, memberOf, filtr);

			System.out.println(tab[2]);

			session.setAttribute("czyzalogowany", tab[0]);
			session.setAttribute("login", tab[1]);
			session.setAttribute("imienazwisko", tab[2]);

			if (tab[0] == "pass") {
				session.setAttribute("info", "badpass");
				System.out.println("Bledne haslo");
			}
			if (tab[0] == "permissions") {
				session.setAttribute("info", "permissions");
				System.out.println("Brak uprawnien");
			}

			doGet(request, response);

		}

	}

}
