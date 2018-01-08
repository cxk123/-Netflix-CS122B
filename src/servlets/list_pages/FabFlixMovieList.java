package servlets.list_pages;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import queries.SearchQuery;

import data_beans.Movie;

@WebServlet("/FabFlixMovieList")
public class FabFlixMovieList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<String> validSearchParams = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
	{
	    add("title");
	    add("year");
	    add("director");
	    add("first_name");
	    add("last_name");
	    add("genre");
	    add("from_search");
	    add("substring_match");
	}};

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try 
		{
			try{
				Class.forName("org.gjt.mm.mysql.Driver");
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
			
			
			
			Connection connection = null;
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost/moviedb?user=root&password=root&useUnicode=true&characterEncoding=8859_1");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		
			String title, year, director, first_name, last_name, genre;
			title = year = director = first_name = last_name = genre = "";
			
			Boolean sub_match = false;
			Boolean from_search = false;
			
			int page = 0;
			int limit = 5;
			String order = "titleasc";
			
			if (request.getParameter("lim") != null)
			{
				limit = Integer.parseInt((String) request.getParameter("lim"));
			}
			
			if (request.getParameter("page") != null)
			{
				page = Integer.parseInt((String) request.getParameter("page"));
			}
			
			if (request.getParameter("sort") != null)
			{
				order = request.getParameter("sort");
			}
			
			if (request.getParameter("title") != null) 
			{
				title = request.getParameter("title");
			}
			
			if (request.getParameter("year") != null)
			{
				year = request.getParameter("year");
			}
			
			if (request.getParameter("director") != null)
			{
				director = request.getParameter("director");
			}
			
			if (request.getParameter("first_name") != null)
			{
				first_name = request.getParameter("first_name");
			}
			
			if (request.getParameter("last_name") != null)
			{
				last_name = request.getParameter("last_name");
			}
			
			if (request.getParameter("genre") != null) 
			{
				genre = request.getParameter("genre");
			}
			
			if (request.getParameter("from_search") != null) 
			{
				from_search = true;
				
				if (request.getParameter("substring_match") != null) 
				{
					sub_match = true;
				}
			}
			
			ArrayList<Movie> movies;
			
			if (from_search) 
			{
				
				movies = SearchQuery.searchMovies("", title, year, director, genre, first_name, last_name, order, sub_match, connection);
				
			}
			else 
			{
				movies = SearchQuery.browseMovies(title, genre, order, connection);
			}
			
			if (order != null && !order.isEmpty())
			{
				if (order.equals("titleasc"))
				{
					Collections.sort(movies, new Comparator<Movie>() {
						
						public int compare(Movie o1, Movie o2) 
						{
							return (o1.getTitle().compareTo(o2.getTitle()));
						}
					});
				}
				else if (order.equals("titledsc"))
				{
					Collections.sort(movies, new Comparator<Movie>() {
						
						public int compare(Movie o1, Movie o2) 
						{
							return (o1.getTitle().compareTo(o2.getTitle()));
						}
					});
					Collections.reverse(movies);
				}
				else if (order.equals("yearasc"))
				{
					Collections.sort(movies, new Comparator<Movie>() {
						
						public int compare(Movie o1, Movie o2) 
						{
							if (o1.getYear() == o2.getYear()) {
								return 0;
							}
							return o1.getYear() < o2.getYear() ? -1 : 1;
						}
					});
				}
				else if (order.equals("yeardsc"))
				{
					Collections.sort(movies, new Comparator<Movie>() {
						
						public int compare(Movie o1, Movie o2) 
						{
							if (o1.getYear() == o2.getYear()) {
								return 0;
							}
							return o1.getYear() < o2.getYear() ? 1 : -1;
						}
					});
				}
			}
			
			ArrayList<Movie> finalMovies = new ArrayList<Movie>();
			int offset = (page * limit);
			
			for (int increment = offset; increment < (offset + limit); increment++)
			{
				if (increment <= movies.size() - 1) 
				{
					finalMovies.add(movies.get(increment));
				}
			}
			
			ArrayList<String> parameterArray = new ArrayList<String>();
			
			for (String retval: request.getQueryString().split("&"))
			{
				for (String param : validSearchParams)
				{
					if (retval.startsWith(param))
					{
						parameterArray.add(retval);
					}
				}
		    }
			
			String queries = "";
			
			for (String param : parameterArray)
			{
				queries += param + "&";
			}
			
			request.setAttribute("queries", queries);
			request.setAttribute("movies", finalMovies);
			request.setAttribute("lim", limit);
			request.setAttribute("page", page);
			request.setAttribute("sort", order);
			
			request.setAttribute("maxPage", (int)Math.ceil(movies.size() / limit));
			
			request.getRequestDispatcher("FabFlixMovieList.jsp").forward(request, response);
			
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
		doGet(request, response);
	}

}
