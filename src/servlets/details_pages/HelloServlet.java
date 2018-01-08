package servlets.details_pages;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Readme</title></head>");
		out.println("<body>");
		out.println("<h1>Instruction</h1>");
		out.println("<h3>To compile and run, what you need to do is import it into eclipse and click login.jsp to run it on tomcat</h3>");
		out.println("<h3>Use the password and email in Customer table!</h3>");
		out.println("<h3>Goodluck!</h3>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}


}
