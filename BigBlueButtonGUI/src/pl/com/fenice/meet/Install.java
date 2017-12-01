package pl.com.fenice.meet;

import java.io.FileInputStream;
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

@WebServlet("/install")
public class Install extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Install() {
		super();

	}

	private void createConfigFile() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("config3.properties");

			// set the properties value
			prop.setProperty("database", "localhost");
			prop.setProperty("dbuser", "mkyong");
			prop.setProperty("dbpassword", "password");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Install install = new Install();
		//install.createConfigFile();
		
		
		Properties prop = new Properties();
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/config77.properties");

		//input = new FileInputStream("config3.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		String database = prop.getProperty("database");
		System.out.println(prop.getProperty("database"));
		System.out.println(prop.getProperty("dbuser"));
		System.out.println(prop.getProperty("dbpassword"));

		input.close();

		response.getWriter().append("Database is: ").append(database);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
