package servlets.auto_popups;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import queries.SearchQuery;
import custom_http.CustomHttpServletResponseWrapper;
import data_beans.Movie;

@WebServlet("/FabFlixMovieCard")
public class FabFlixMovieCard extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/TestDB", type=javax.sql.DataSource.class)
	private DataSource dataSource;
	
	private Connection connection;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			if (connection == null || connection.isClosed())
			{
				connection = dataSource.getConnection();
			}
			
			String movieId = request.getParameter("movieid");
			
			ArrayList<Movie> movies = SearchQuery.searchMovies(movieId, "", "", "", "", "", "", "", connection, "", "");
			
			if (movies.size() == 1)
			{
				Movie movie = movies.get(0);
				
				request.setAttribute("movie", movie);
				request.setAttribute("stars", movie.getStars());
				
				CustomHttpServletResponseWrapper customResponse = new CustomHttpServletResponseWrapper(response);
			    request.getRequestDispatcher("FabFlixMovieCard.jsp").forward(request, customResponse);
				
			    response.setContentType("text/plain");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(customResponse.toString()); 
			}
			else
			{
				response.getWriter().write("<link href=\"Style/movie_card.css\" rel=\"stylesheet\" /><p>No details found</p>");
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
