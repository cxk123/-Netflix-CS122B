package servlets.employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.*;

/**
 * Servlet implementation class ProvideMeta
 */
@WebServlet("/ProvideMeta")
public class ProvideMeta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvideMeta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		String type = request.getParameter("type");
		
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
				connection = DriverManager.getConnection("jdbc:mysql://localhost/moviedb?user=root&password=root&useUnicode=true&characterEncoding=8859_1");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try{
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
			DatabaseMetaData dbMeta = connection.getMetaData();
			Statement show = connection.createStatement();
			ShowMetaData(type, show, dbMeta,out);
			out.println("<a href = dashboard.jsp >back</a>");
		} catch (Exception e){
			e.printStackTrace();
	    }	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	  public static void ShowMetaData (String  s, Statement show, DatabaseMetaData dbMeta,PrintWriter out) throws Exception{
	 		
	 	  ResultSet result = show.executeQuery("Select * from " + s);
	      ResultSet pkRSet = dbMeta.getPrimaryKeys(null, null, s);
	      ResultSet imPSet = dbMeta.getPrimaryKeys(null, null, s);

	      ResultSetMetaData metadata = result.getMetaData();
	      out.println("Table name is " + metadata.getTableName(1)+"<br>");
	      
		  if( pkRSet.next() ){ 
					out.println("****** Comment ******<br>");
					out.println("Primary key COLUMN_NAME: "+pkRSet.getObject(4)+"<br>");
				    out.println("****** ******* ******"+"<br>");
		  }else{
			  		out.println("There is no Primary key"+"<br>");  
		  }
		  if (imPSet.next()){ 			
					out.println("IMPORTED FORENGN KEY: "+imPSet.getObject(4)+"<br>");
					out.println("****** ******* ******"+"<br>");
		  }else{
			  		out.println("There is no Foreign key"+"<br>");
		  }
	
	      out.println("There are " + metadata.getColumnCount() + " columns:"+"<br>");
	      
	      for (int i = 1; i <= metadata.getColumnCount(); i++){	
	    	  out.print("Column " +i +" is "+ metadata.getColumnLabel(i) + ".   " + "Type of column " + metadata.getColumnTypeName(i) + "<br>");
	      }

	 }

}
