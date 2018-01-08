package servlets.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.mysql.jdbc.CallableStatement;
/**
 * Servlet implementation class AddMovie
 */
@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		String [] a = new String[2];
		Connection dbcon = connection;
		PrintWriter out = response.getWriter();
		try
	    {
			PreparedStatement addMovies = connection.prepareStatement("call add_movie(?,?,?,?,?,?,?,?,?,?)");
			String name = request.getParameter("star");
			String dob = request.getParameter("dob");
			String photo = request.getParameter("photo");
		    String movie = request.getParameter("name");
		    String year = request.getParameter("year");
		    String director = request.getParameter("director");
		    String banner = request.getParameter("banner");
		    String trailer = request.getParameter("trailer");
		    String genre = request.getParameter("genre");
		    
		    if (name.contains(" ")){
		    	a = name.split(" ");
		    }
			else {
				a[0] = "";
				a[1] = name;
			}
		    if(year.equals("")){
		    	year = "9999";
		    }
		    	
		    int y = Integer.parseInt(year);
		    response.setContentType("text/html");
		    
		    String title = "Employee Operation";
		    String docType =
		      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
		      "Transitional//EN\">\n";
		    out.println(docType +
		                "<HTML>\n" +
		                "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
		                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
		                "<H1>" + title + "</H1>");
		    if (name.equals("")||movie.equals("")||genre.equals(""))
		    	out.write("<script language='javascript'>alert('Please input valid information!');window.location='dashboard.jsp';</script>");
		    addMovies.setString(1, movie);
		    addMovies.setInt(2, y);
		    addMovies.setString(3, director);
		   	addMovies.setString(4, banner);
		   	addMovies.setString(5, trailer);
		   	addMovies.setString(6, a[0]);
		   	addMovies.setString(7, a[1]);
		   	if (!dob.contains("-")&&!dob.equals("")){
		   	   	out.write("<script language='javascript'>alert('wrong date of birth format');window.location='dashboard.jsp';</script>");

		   	}
		   	else if (dob.equals("")	)
		   			addMovies.setString(8, "1999-09-09");	

		   	else
		   	addMovies.setString(8, dob);
		   	addMovies.setString(9, photo);
		   	addMovies.setString(10, genre);
		   	
		   	
		   	
		   	int retID = addMovies.executeUpdate();
		   	if (retID ==0 )
		   		out.write("<script language='javascript'>alert('the operation has been done');window.location='dashboard.jsp';</script>");
		   	else
		   		out.write("<script language='javascript'>alert('add movie success');window.location='dashboard.jsp';</script>");
		   	out.println("</BODY></HTML>");
	    }
	    catch (Exception e){
	    	//out.write("<script language='javascript'>alert('add movie failed');window.location='Operation.html';</script>");
	    	e.printStackTrace() ;
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
