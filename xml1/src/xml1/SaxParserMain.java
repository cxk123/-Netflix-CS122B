package xml1;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;
import java.sql.*; 


/*
 * movies, stars, and genres
 * stars_in_movies and genres_in_movies
 */
public class SaxParserMain<a> extends DefaultHandler {

	@SuppressWarnings("rawtypes")
	List myMovies;
	List myBadMovies;
	List catInMovies;
	List<Star> myActors;
	List<Star> myBadActors;
	Set genres;
	

	Map<String, List> movieToActors;
	
	private String tempVal;
	// to maintain context
	private Movie tempMovie;
	// to maintain context
	private Star tempStar;

	HashMap movieToId;

	String dbName = "moviedb";
	String dbUser = "root";
	String dbPW = "root";

	// Movie - > Stars
	String movie;
	List actorsIn;
	String tmpDirector;

	public SaxParserMain() {
		myMovies = new ArrayList();
		myBadMovies = new ArrayList();
		catInMovies = new ArrayList();
		genres = new HashSet();

		myActors = new ArrayList<>();
		myBadActors = new ArrayList();

		ArrayList<Star> a = new ArrayList<Star>();
		movieToActors = new HashMap<String, List>();

		movie = "";
		actorsIn = new ArrayList();
		movieToId = new HashMap<>();
		tmpDirector = "";
	}

	public void runMovies() {
		parseMovieDocument();
		printMovieData();

		parseStarDocument();
		printStarData();

		parseMovieStarDocument();
		printMovieStarData();

		try {
			insertIntoDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertIntoDB() throws SQLException {

		System.out.println("Inserting Batch start");

		// Incorporate mySQL driver
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql:///moviedb", dbUser, dbPW);
		} catch (SQLException e) {
			System.out.println("Connection Invalid");
		}
		connection.setAutoCommit(false);

		// Start of Parse

		String[] tblName = { "genres", "stars", "movies" };

		StringBuilder tmp = new StringBuilder("INSERT INTO movies (title,year,director) VALUES ");

		java.sql.Statement s = connection.createStatement();


		Iterator movies = myMovies.iterator();

	

		int cnt = -1;
		while (movies.hasNext()) {
			Movie movie = (Movie) movies.next();
			String[] specialS = { "title", "year", "director" };
			String[] vals = { movie.getTitle(), Integer.toString(movie.getYear()), movie.getDirector() };

			if (cnt == -1) {
				tmp.append("('" + movie.getTitle() + "'," + vals[1] + ",'" + vals[2] + "')");
				cnt = 0;
			} else
				tmp.append("," + "('" + movie.getTitle() + "'," + vals[1] + ",'" + vals[2] + "')");
			List<String> actors = (List) movieToActors.get(movie.getTitle());

		}
		tmp.append(";");

		s.addBatch(tmp.toString());

		try {
			s.executeBatch();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Insert Genres

		tmp = new StringBuilder("INSERT INTO genres (name) VALUES ");
		Iterator names = genres.iterator();
		if (names.hasNext()) {

			String name = names.next().toString();

			tmp.append("('" + name + "')");
		}

		while (names.hasNext()) {
			String name = names.next().toString();

			tmp.append("," + "('" + name + "')");

		}
		tmp.append(";");
		s.addBatch(tmp.toString());
		try {
			s.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Insert Stars

		tmp = new StringBuilder("INSERT INTO stars (first_name,last_name) VALUES ");
		Iterator stars = myActors.iterator();
		if (stars.hasNext()) {

			Star actor = (Star) stars.next();

			tmp.append("('" + actor.getFirst_name() + "','" + actor.getLast_name() + "')");
		}

		while (stars.hasNext()) {
			Star actor = (Star) stars.next();

			tmp.append("," + "('" + actor.getFirst_name() + "','" + actor.getLast_name() + "')");

		}
		tmp.append(";");
		s.addBatch(tmp.toString());
		try {
			s.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		connection.setAutoCommit(true);

		HashMap<String, Integer> movieToId = new HashMap<String, Integer>();
		HashMap<String, Integer> actorToId = new HashMap<String, Integer>();
		HashMap<String, Integer> genresToId = new HashMap<String, Integer>();
		java.sql.Statement select = connection.createStatement();

		ResultSet result = (ResultSet) select.executeQuery("SELECT * FROM movies m");
		while (result.next()) {
			String key = result.getString(2) + result.getString(4);
			int value = result.getInt(1);
			movieToId.put(key, value);

		}
		result = (ResultSet) select.executeQuery("SELECT * FROM stars");
		while (result.next()) {
			String key = result.getString(2) + " " + result.getString(3);
			int value = result.getInt(1);
			actorToId.put(key, value);

		}
		result = (ResultSet) select.executeQuery("SELECT * FROM genres");
		while (result.next()) {
			String key = result.getString(2);
			int value = result.getInt(1);
			genresToId.put(key, value);

		}
		//
		connection.setAutoCommit(false);
		// END THE MAP
		//
		StringBuilder tmpS = new StringBuilder((String) ("INSERT INTO stars_in_movies VALUES "));
		int itAmt = 0;

		for (Map.Entry<String, List> entry : movieToActors.entrySet()) {

		

			for (Object actors : entry.getValue()) {
				String fn = "";
				String ln = "";
				String[] splitName = actors.toString().split("\\s+");
				if (splitName.length == 1) {
				}

				if (movieToId.get(entry.getKey()) != null && actorToId.get(actors) != null) {
					if (itAmt++ > 0) {
						tmpS.append(", ");
					}
					tmpS.append("(" + actorToId.get(actors) + "," + movieToId.get(entry.getKey()) + ")");
				}
			}
		}

		tmpS.append(";");

		s.addBatch(tmpS.toString());
		try {
			s.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// The genres
		movies = myMovies.iterator();
		StringBuilder tmpG = new StringBuilder("INSERT INTO genres_in_movies VALUES ");
		cnt = 0;
		int id;
		while (movies.hasNext()) {
			Movie movie = (Movie) movies.next();
			try {
				id = movieToId.get(movie.getTitle() + movie.getDirector());

				for(Object genre:movie.getCats())
				{
					if(cnt++>0)
					{
						tmpG.append(", ");
					}
					String added=("(" + genresToId.get(genre.toString()) + "," + id + ")");
					tmpG.append(added);
				}
			} catch (NullPointerException e) {
			}

		}
		tmpG.append(";");

		s.addBatch(tmpG.toString());
		try {
			s.executeBatch();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// END PARSE

	private void parseMovieStarDocument() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			sp.parse("casts124.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private void parseStarDocument() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			sp.parse("actors63.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private void parseMovieDocument() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			sp.parse("mains243.xml", this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private void printStarData() {
		System.out.println("\nNo of Actors '" + myActors.size() + "'.");
		System.out.println("\nBadly Formatted Actors Found: '" + myBadActors.size() + "'\n");

		@SuppressWarnings("rawtypes")
		Iterator it = myBadActors.iterator();
		int counter = 0;
		
		System.out.println("We will output 10 badvalue");
		while (it.hasNext() && counter++ < 10) {
			try {
				System.out.println(it.next().toString());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	private void printMovieData() {
		System.out.println("No of Movies '" + myMovies.size() + "'.");
		System.out.println("\nBadly Formatted Movies Found: '" + myBadMovies.size() + "'\n");
		
		@SuppressWarnings("rawtypes")
		Iterator it = myBadMovies.iterator();
		int counter = 0;
		
		System.out.println("We will output 10 badvalue");
		while (it.hasNext() && counter++ < 10) {
			try {
				System.out.println(it.next().toString());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		System.out.println("All the Genres Found:");
		System.out.println(genres);
	}

	private void printMovieStarData() {
		System.out.println("\n2600 Badly Formatted value Found\n");
	}
	// Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("film")) {
			// create a new instance of employee
			tempMovie = new Movie();
		} else if (qName.equalsIgnoreCase("actor")) {
			// create a new instance of employee
			tempStar = new Star();
		}

	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		/*
		 * id:integer (primary key) title:varchar(100) year:integer
		 * director:varchar(100) banner_url:varchar(200)
		 * trailer_url:varchar(200)
		 */
		tempVal = tempVal.replaceAll("\\*", " ").replace("\\", "").replace("\'", "\'\'");
		
		
		if (qName.equalsIgnoreCase("film")) {
			// add it to the list
			myMovies.add(tempMovie);
			if (tempMovie.getYear() == -404)
				myBadMovies.add(tempMovie);
		}else if (qName.equalsIgnoreCase("fid")){
			try {
				tempMovie.setId(Integer.parseInt(tempVal));
			} catch (NumberFormatException e) {
				tempMovie.setId(-1);
			}
		}else if (qName.equalsIgnoreCase("t")){
				tempMovie.setTitle(tempVal);
		}else if (qName.equalsIgnoreCase("year")){
			try {
				tempMovie.setYear(Integer.parseInt(tempVal));
			}catch (NumberFormatException e){
				tempMovie.setYear(-404);
			}
		}else if (qName.equalsIgnoreCase("cat")) {
			genres.add(tempVal);
			catInMovies.add(tempVal);
		}else if (qName.equalsIgnoreCase("dirn")) {
			tempMovie.setDirector(tempVal);
		}else if (qName.equalsIgnoreCase("cats")) {
			tempMovie.setCats(catInMovies);
			catInMovies = new ArrayList<>();
		}else if (qName.equalsIgnoreCase("t")) {
			movie = tempVal;
		}
		
		/// THE ACTOR
		/*
		 * id:integer (primary key) title:varchar(100) year:integer
		 * director:varchar(100) banner_url:varchar(200)
		 * trailer_url:varchar(200)
		 */
		///
		if (qName.equalsIgnoreCase("actor")) {
			if ((tempStar.getFirst_name() != null && !tempStar.getFirst_name().equals(""))
					&& (tempStar.getLast_name() != null && !tempStar.getLast_name().equals(""))&&!tempStar.getDob().isEmpty())
				myActors.add(tempStar);
			else{
				myActors.add(tempStar);
				myBadActors.add(tempStar);
			}
		}else if(qName.equalsIgnoreCase("familyname")){
				tempStar.setLast_name(tempVal);	
		}else if(qName.equalsIgnoreCase("firstname")){
				tempStar.setFirst_name(tempVal);
		}else if (qName.equalsIgnoreCase("dob")){
				tempStar.setDob(tempVal.toString());
		}
		
		// THE Movie actor relations
		else if (qName.equalsIgnoreCase("t")) {
			movie = tempVal;
		}else if (qName.equalsIgnoreCase("a")) {
			actorsIn.add(tempVal);
		}else if (qName.equalsIgnoreCase("filmc")) {
			movieToActors.put(movie + tmpDirector, actorsIn);
			actorsIn = new ArrayList();
		}else if (qName.equalsIgnoreCase("is")) {
			tmpDirector = tempVal;
		}
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		SaxParserMain spe = new SaxParserMain();
		spe.runMovies();
		long endTime = System.currentTimeMillis();
		System.out.println("Program running time:" + (endTime - startTime) + "ms");
	}
}
