package servlets.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import queries.SearchQuery;

/**
 * Servlet implementation class AddStar
 */
@WebServlet("/AddStar")
public class AddStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		HttpSession session = request.getSession(false);
		
		
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("load Mysql Driver success!<br>");
		 }
		 catch(Exception e)
		 {
			 System.out.println("load Mysql Driver fail!<br>");
			 e.printStackTrace();
		 }
		try {
			if (connection == null || connection.isClosed())
			{
				connection = DriverManager.getConnection("jdbc:mysql://54.213.90.230:3306/moviedb?user=root&password=root&useUnicode=true&characterEncoding=8859_1");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		try 
		{
			int rows_affected = SearchQuery.addStar(first_name, last_name, connection);
			switch (rows_affected) {
	          case 0:
	              session.setAttribute("error", "Invalid Star Information");
	              break;
	          case 1:
	              session.setAttribute("success", "Successfully added " + first_name + " " + last_name);
	              break;
	          default:
	              session.setAttribute("success", "Number of rows were affected: " + rows_affected);
	      }	
			response.sendRedirect("dashboard.jsp");
		} 
		catch (Exception e) 
		{
			e.getMessage();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
