package servlets.login_logout;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.sql.*;

import timer.WriteOut;
import queries.SearchQuery;
import session.SessionCart;
import data_beans.Customer;


@WebServlet("/Login")
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }
	
	//@Resource(name="jdbc/TestDB", type=javax.sql.DataSource.class)
	//private DataSource dataSource;
	
	//private Connection connection;
	private HttpSession session;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		
		try 
		{
			login(email,password, request, response);
		} 
		catch (Exception e) 
		{
			e.getMessage();
		}
	}
	
	private synchronized void login(String email, String password, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, NamingException 
	{	
		
		//try{
		//	Class.forName("org.gjt.mm.mysql.Driver");
		//	System.out.println("load Mysql Driver success!<br>");
		//}
		// catch(Exception e)
		// {
		//	 System.out.println("load Mysql Driver fail!<br>");
			// e.printStackTrace();
		// }
	

		//Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/moviedb?user=root&password=root&useUnicode=true&characterEncoding=8859_1");
		
		
		//if (connection == null || connection.isClosed())
		//{
		//	connection = DriverManager.getConnection("jdbc:mysql://localhost/moviedb?user=root&password=root&useUnicode=true&characterEncoding=8859_1");
		//}
		
		
		WriteOut W= new WriteOut();
	    W.TJstartTime= System.nanoTime();
		
		PrintWriter out = response.getWriter();

	    Context initCtx = new InitialContext();
        if (initCtx == null)
            out.println("initCtx is NULL");

        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        if (envCtx == null)
            out.println("envCtx is NULL");

       
        DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
        if (ds == null)
        	out.println("ds is null.");

        Connection connection = ds.getConnection();
        if (connection == null)
        	out.println("dbcon is null.");
        W.TJendTime= System.nanoTime();
        W.writeTofileJdbc();
        
        Customer validCustomer = null;
		String type = request.getParameter("type");
		
		if("2".equals(type)){
			boolean flag;
			flag = SearchQuery.verifyLoginAccountEmployee(email, password, connection);
			if(flag == true){
				request.getRequestDispatcher("/dashboard.jsp").forward(request,response);
			}
			else
			{
				request.setAttribute("login_invalid", "login_invalid");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
		else if("1".equals(type)){
				try 
				{	
					validCustomer = SearchQuery.verifyLoginAccount(email, password, connection);
				} 
				catch (SQLException e) 
				{
					System.out.println(e.getMessage());
				}
	
				if (validCustomer != null)
				{    
					session = request.getSession(true);
					session.setAttribute("customer_loggedin", validCustomer);
					session.setAttribute("connection", connection);
					session.setAttribute("session_cart", new SessionCart());
					request.setAttribute("login_invalid", "");
					response.sendRedirect("FabFlixMain");
				}
				else if (email != null && password != null)
				{
					request.setAttribute("login_invalid", "Invalid login details");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				else
				{
					request.setAttribute("login_invalid", "Not all login details were provided");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				
				if (connection != null && !connection.isClosed())
				{
					connection.close();
				}
			}
		}

}
