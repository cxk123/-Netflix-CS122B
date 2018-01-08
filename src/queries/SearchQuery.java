package queries;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import session.SessionCart;
import timer.WriteOut;
import app_beans.CartItem;
import data_beans.Customer;
import data_beans.Genre;
import data_beans.Movie;
import data_beans.Star;
import timer.WriteOut;

public class SearchQuery 
{
	public static ArrayList<Movie> fastSearchMovies(ArrayList<String> titleTokens, Connection connection) throws SQLException
	{
		String whereConditions = " WHERE ";
		String orderByCondition = " ORDER BY movies.title ASC";
		//SELECT title FROM movies WHERE MATCH (title) AGAINST ('+revenge star of' IN BOOLEAN MODE);
		for (String token: titleTokens)
		{
			if (!token.equals("") && !token.contains(" "))
			{
				if (titleTokens.indexOf(token) == titleTokens.size() - 1) {
					whereConditions += "MATCH (movies.title) AGAINST ('" + token + "*' IN BOOLEAN MODE)";
				}
				else {
					whereConditions += "MATCH (movies.title) AGAINST ('" + token + "' IN BOOLEAN MODE) AND ";
				}
			}
		}
		
		String sqlStatement = "SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url FROM movies "
				+ whereConditions
				+ orderByCondition;
		Statement searchStatement = connection.createStatement();
		ResultSet resultSet = searchStatement.executeQuery(sqlStatement);
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		while (resultSet.next())
		{
			Movie movie = new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
			movies.add(movie);
		}
		
		return movies;
	}
	
	/*
	public static ArrayList<Movie> fastSearchMovies(ArrayList<String> titleTokens, Connection connection) throws SQLException
	{
		String whereConditions = " WHERE 1=1";
		String orderByCondition = " ORDER BY movies.title ASC";
		
		for (String token: titleTokens)
		{
			if (!token.equals("") && !token.contains(" "))
			{
				if (titleTokens.indexOf(token) == titleTokens.size() - 1)
				{
					whereConditions = whereConditions + " AND movies.title LIKE \"%" + token + "%\"";
				}
				else
				{
					whereConditions = whereConditions + " AND movies.title LIKE \"%" + token + "%\"";
				}
			}
		}
		
		String sqlStatement = "SELECT movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url FROM movies "
				+ whereConditions
				+ orderByCondition;
		
		Statement searchStatement = connection.createStatement();
		ResultSet resultSet = searchStatement.executeQuery(sqlStatement);
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		while (resultSet.next())
		{
			Movie movie = new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
			movies.add(movie);
		}
		
		return movies;
	}
	*/
	
	public static ArrayList<Movie> searchMovies(String movieId, String movieTitle, String movieYear, String movieDirector, String movieGenre,
			String starFirstName, String starLastName, String order, Connection connection, String offset, String limit) throws SQLException
	{
		return SearchQuery.searchMovies(movieId, movieTitle, movieYear, movieDirector, movieGenre, starFirstName, starLastName, order, false, connection);
	}
	
	public static ArrayList<Movie> searchMovies(String movieId, String movieTitle, String movieYear, String movieDirector, String movieGenre,
			String starFirstName, String starLastName, String order, boolean sub_match, Connection connection) throws SQLException
	{	
		String whereConditions = " WHERE 1=1";
		String orderByCondition = " ORDER BY movies.title ASC";
		String prefix = "(1=1";
		String suffix = "1=1)";
		
		if (movieId != null && !movieId.isEmpty())
		{
			whereConditions = whereConditions + " AND movies.id=\"" + movieId + "\""; 
		}
		
		if (movieTitle != null && !movieTitle.isEmpty())
		{
			if (sub_match) 
			{ 
				whereConditions = whereConditions + " AND (movies.title LIKE \"%" + movieTitle + "%\"" + " or " + " ed(movies.title, " + "\'" + movieTitle + "\') <= 2)";
			}
			else 
			{
				whereConditions = whereConditions +" AND movies.title LIKE \"" + movieTitle + "\"";
			}
		}
		
		if (movieYear != null && !movieYear.isEmpty())
		{
			whereConditions = whereConditions + " AND movies.year LIKE \"" + movieYear + "\"";
		}
		
		if (movieDirector != null && !movieDirector.isEmpty())
		{
			if (!sub_match) 
			{ 
				whereConditions = whereConditions + " AND movies.director = \"" + movieDirector + "\"";
			} 
			else 
			{
				whereConditions = whereConditions + " AND (movies.director LIKE \"%" + movieDirector + "%\"" + " or " + " ed(movies.director, " + "\'" + movieDirector + "\') <= 2)";
			}
		}
		
		if (movieGenre != null && !movieGenre.isEmpty())
		{
			if (!sub_match) 
			{
				whereConditions = whereConditions + " AND genres.name = \"" + movieGenre + "\"";
			} 
			else 
			{
				whereConditions = whereConditions + " AND (genres.name LIKE \"%" + movieGenre + "%\"" + " or " + " ed(genres.name, " + "\'" + movieGenre+ "\') <= 2)";
			}
		}
		
		if (starFirstName != null && !starFirstName.isEmpty())
		{
			if (!sub_match) 
			{
				whereConditions = whereConditions + " AND stars.first_name = \"" + starFirstName + "\"";
			} 
			else 
			{
				whereConditions = whereConditions + " AND (stars.first_name LIKE \"%" + starFirstName + "%\"" + " or " + " ed(stars.first_name, " + "\'" +starFirstName+ "\') <= 2)";
			}
		}
		
		if (starLastName != null && !starLastName.isEmpty())
		{
			if (!sub_match) 
			{
				whereConditions = whereConditions + " AND stars.last_name = \"" + starLastName + "\"";
			} 
			else
			{
				whereConditions = whereConditions + " AND (stars.last_name LIKE \"%" + starLastName + "%\"" + " or " + " ed(stars.last_name, " + "\'" +starLastName+ "\') <= 2)";
				//whereConditions = whereConditions + " AND stars.last_name LIKE \"%" + starLastName + "%\"";
			}
		}
		
		if (order != null && !order.isEmpty())
		{
			if (order.equals("titleasc"))
			{
				orderByCondition = " ORDER BY movies.title ASC";
			}
			else if (order.equals("titledsc"))
			{
				orderByCondition = " ORDER BY movies.year DESC";
			}
			else if (order.equals("yearasc"))
			{
				orderByCondition = " ORDER BY movies.year ASC";
			}
			else if (order.equals("yeardsc"))
			{
				orderByCondition = " ORDER BY movies.year DESC";
			}
		}
		
		return executeMoviesFetch(whereConditions, orderByCondition, connection);
	}
	
	public static ArrayList<Movie> browseMovies(String movieTitle, String movieGenre, String order, Connection connection) throws SQLException
	{	
		
		String whereConditions = " WHERE 1=1";
		String orderByCondition = " ORDER BY movies.title ASC";
		
		if (movieTitle != null && !movieTitle.isEmpty())
		{
			whereConditions += " AND movies.title LIKE \"" + movieTitle + "%\"";
		}
		
		if (movieGenre != null && !movieGenre.isEmpty())
		{
			whereConditions += " AND genres.name LIKE \"%" + movieGenre + "%\"";
		}
		
		if (order != null && !order.isEmpty())
		{
			if (order.equals("titleasc"))
			{
				orderByCondition = " ORDER BY movies.title ASC";
			}
			else if (order.equals("titledsc"))
			{
				orderByCondition = " ORDER BY movies.title DESC";
			}
			else if (order.equals("yearasc"))
			{
				orderByCondition = " ORDER BY movies.year ASC";
			}
			else if (order.equals("yeardsc"))
			{
				orderByCondition = " ORDER BY movies.year DESC";
			}
		}
		
		return executeMoviesFetch(whereConditions, orderByCondition, connection);
		
	}
	
	public static ArrayList<Movie> executeMoviesFetch(String whereConditions, String orderByCondition, Connection connection) throws SQLException
	{
		WriteOut W= new WriteOut();
		W.TSstartTime = System.nanoTime();
		String sqlStatement = "SELECT stars.id, stars.first_name, stars.last_name, stars.dob, stars.photo_url, movies.id, movies.title, movies.year, movies.director, movies.banner_url, movies.trailer_url, "
				+ "genres.id, genres.name FROM movies "
				+ "INNER JOIN stars_in_movies ON stars_in_movies.movie_id = movies.id "
				+ "INNER JOIN stars ON stars_in_movies.star_id = stars.id "
				+ "INNER JOIN genres_in_movies ON genres_in_movies.movie_id = movies.id "
				+ "INNER JOIN genres ON genres.id = genres_in_movies.genre_id "
				+ whereConditions
				+ orderByCondition;
		
		Statement searchStatement = connection.createStatement();
		ResultSet resultSet = searchStatement.executeQuery(sqlStatement);
		
		HashMap<Integer, Movie> movieMap = new HashMap<Integer, Movie>(); 
		
		while (resultSet.next()) 
		{
			Star star = new Star(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4), resultSet.getString(5));
			Genre genre = new Genre(resultSet.getInt(12), resultSet.getString(13));
			
			if (movieMap.containsKey(resultSet.getInt(6)))
			{
				Movie movie = movieMap.get(resultSet.getInt(6));
				boolean addStar = true;
				boolean addGenre = true;
				
				for (Star existingStar : movie.getStars())
				{
					if (existingStar.getId() == star.getId())
					{
						addStar = false;
					}
				}
				
				if (addStar)
				{
					movie.addStar(star);
				}
				
				for (Genre existingGenre : movie.getGenres())
				{
					if (existingGenre.getId() == genre.getId())
					{
						addGenre = false;
					}
				}
				
				if (addGenre)
				{
					movie.addGenre(genre);
				}
				
				movieMap.put(movie.getId(), movie);
			} 
			else 
			{
				ArrayList<Genre> genres = new ArrayList<Genre>();
				genres.add(genre);
				
				ArrayList<Star> stars = new ArrayList<Star>();
				stars.add(star);
				
				Movie movie = new Movie(resultSet.getInt(6), resultSet.getString(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), genres, stars);
				movieMap.put(movie.getId(), movie);
			}
		}
		
		ArrayList<Movie> movies = new ArrayList<Movie>(movieMap.values());
		W.TSendTime = System.nanoTime();
		W.writeTofileSearch();
		
		return movies;
	}
	
	public static Star searchStar(String starId, Connection connection) throws SQLException
	{
		String sqlStatement = "SELECT movies.id, movies.title, movies.year, stars.id, stars.first_name, stars.last_name, stars.dob, stars.photo_url FROM stars"
				+ " INNER JOIN stars_in_movies ON stars_in_movies.star_id=stars.id"
				+ " INNER JOIN movies ON stars_in_movies.movie_id=movies.id"
				+ " WHERE stars.id = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
		preparedStatement.setString(1, starId);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		Star star = new Star(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7), resultSet.getString(8));
		star.addMovie(new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), "", "", ""));
		
		while (resultSet.next())
		{
			star.addMovie(new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), "", "", ""));
		}
		
		return star;
	}
	
	public static boolean insertSales(SessionCart cart, Customer customer, Connection connection) 
	{
		for (CartItem item : cart.getCartItems()) 
		{
			try 
			{
				String sql = "INSERT INTO sales(customers_id, movie_id, sale_date) VALUES(?, ?, ?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				
				java.util.Date date = new java.util.Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String today = dateFormat.format(date).toString();
				
				preparedStatement.setInt(1, customer.getId());
				preparedStatement.setInt(2, item.getMovieId());
				preparedStatement.setDate(3, Date.valueOf(today));
				
				for (int i = 0; i < item.getQuantity(); i++) 
				{
					int resultSet = preparedStatement.executeUpdate();
					
					if (resultSet != 1) {
						return false;
					}
				}
			}
			catch (SQLException e) 
			{
				System.out.println(e.getMessage());
			}
		}
		
		return true;
	}
	
	public static Customer verifyLoginAccount(String email, String password, Connection connection) throws SQLException
	{
		String sql = "SELECT id, first_name, last_name, cc_id, address, email, password FROM customers WHERE email = ? AND password = ?";
		
		PreparedStatement verifyStatement = connection.prepareStatement(sql);
		verifyStatement.setString(1, email);
		verifyStatement.setString(2, password);
		
		ResultSet result = verifyStatement.executeQuery();

		Customer validCustomer = null;
		
		if (result.next())
		{
			validCustomer = new Customer(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7));
		}
		
		return validCustomer;
	}
	
	public static boolean verifyLoginAccountEmployee(String email, String password, Connection connection) throws SQLException
	{
		String sql = "SELECT email, password FROM employees WHERE email = ? AND password = ?";
		
		PreparedStatement verifyStatement = connection.prepareStatement(sql);
		verifyStatement.setString(1, email);
		verifyStatement.setString(2, password);
		
		ResultSet result = verifyStatement.executeQuery();
		
		if(result.next() == false)
			return false;
		else 
			return true;
	}
	
	public static int addStar(String first_name, String last_name, Connection connection) throws SQLException, IOException, ServletException{
		
		
		String update = "INSERT INTO stars(first_name,last_name)values(?,?)";
		PreparedStatement verifyStatement = connection.prepareStatement(update);
		verifyStatement.setString(1, first_name);
		verifyStatement.setString(2, last_name);
		int rows_affected = verifyStatement.executeUpdate();
		
		return rows_affected;
		
	}
}
