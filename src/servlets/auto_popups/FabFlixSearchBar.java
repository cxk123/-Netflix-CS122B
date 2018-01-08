package servlets.auto_popups;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import custom_http.CustomHttpServletResponseWrapper;
import queries.SearchQuery;
import data_beans.Movie;
import java.sql.*;
import net.sf.json.JSONArray;

@WebServlet("/FabFlixSearchBar")
public class FabFlixSearchBar extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/TestDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String keyword=request.getParameter("keyword");
		String[] arrays=keyword.split("\\s+");
		int size=arrays.length;
		
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = dataSource.getConnection();
			}
			
			StringBuffer sqlQuery=new StringBuffer("select title from movies where match (title) against ('+"+arrays[0]);
			for(int i=1;i<size;i++){
				sqlQuery.append(" +");
				sqlQuery.append(arrays[i]);
			}
			
			sqlQuery.append("*' IN BOOLEAN MODE) limit 8");
			System.out.println(sqlQuery);
			PreparedStatement ps = connection.prepareStatement(sqlQuery.toString());
			ResultSet rs = ps.executeQuery();
			
			List<String> list=new ArrayList<String>();
			while(rs.next()){
				list.add(rs.getString("title"));
				//pw.print("<li>"+rs.getString("name")+"</li>");
			}
			response.getWriter().write(JSONArray.fromObject(list).toString());
			
		    
		    if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}