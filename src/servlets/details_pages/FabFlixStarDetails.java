package servlets.details_pages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import queries.SearchQuery;
import data_beans.Star;

@WebServlet("/FabFlixStarDetails")
public class FabFlixStarDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/TestDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
	
    public FabFlixStarDetails() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = dataSource.getConnection();
			}
			
			// Find star based on id
			String starId = request.getParameter("starid");
			Star star = SearchQuery.searchStar(starId, connection);
			
			request.setAttribute("star", star);
			request.setAttribute("movies", star.getMovies());
			request.getRequestDispatcher("FabFlixStarDetails.jsp").forward(request, response);
			
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
