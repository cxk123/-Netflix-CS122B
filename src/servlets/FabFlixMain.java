package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/FabFlixMain")
public class FabFlixMain extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/TestDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
      
    public FabFlixMain() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		try 
		{
			if (connection == null || connection.isClosed())
			{
				connection = (Connection) dataSource.getConnection();
			}
			
			ArrayList<String> genres = getGenreNames(connection);
			
			request.setAttribute("genres", genres);
			request.setAttribute("genreSize", genres.size());
			request.getRequestDispatcher("FabFlixMain.jsp").forward(request, response);
			
			if (connection != null && !connection.isClosed())
			{
				connection.close();
			}
		}
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getGenreNames(Connection connection) throws SQLException {
		
		String sqlStatement = "SELECT name FROM genres ORDER BY name ASC";
		
		ResultSet result = connection.createStatement().executeQuery(sqlStatement);
		ArrayList<String> output = new ArrayList<String>();

		while(result.next())
		{
			output.add(result.getString(1));
		}
		
		return output;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String path = "FabFlixMovieList?";
		
		if (request.getParameter("browse_genre_select") != null) 
		{
		    path += "genre=" + request.getParameter("browse_genre_select");
		}
		else if (request.getParameter("browse_title_select") != null) 
		{
			path += "title=" + request.getParameter("browse_title_select");
		}
		
	    response.sendRedirect(path);
	}
}
