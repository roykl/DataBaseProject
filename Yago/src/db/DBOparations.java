package db;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.sql.Connection;

import parsing.*;

/**
 * The communication with the db is being made
 * With this class methods. All the operation against the DB:
 */
public class DBOparations implements IdbOparations {

	static JDBCConnectionPooling connPull;
	private static final int IdMovie = 1;
	private static final int IdLanguage = 2;
	private static final int IdDirector = 3;
	private static final int MovieName = 4;
	private static final int Year = 5;
	private static final int Youtube = 6;
	private static final int Wiki = 7;
	private static final int Duration = 8;
	private static final int Plot = 9;
	private static final int OK = 1;
	private static final int ERR = 0;
	// constructor //

	public DBOparations(JDBCConnectionPooling connParam) {
		connPull = connParam;
	}


	//*** db operations- select, insert, update, delete ***//

	/** insert tuple
	 * @param table - the table name we want to insert to
	 * @param values - the values to insert in the correct order
	 */
	@Override
	public int insert(String table, String... values) {

		int i;

		String state = "INSERT INTO " + table + " VALUES (";

		for (i = 0; i < Array.getLength(values); i++) {
			state = state+values[i];

			if (i != Array.getLength(values) - 1)
				state = state + ",";
		}
		state += ")";

		System.out.println(state);
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate(state);
		}

		catch (SQLException e) {
			System.out
			.println("Check that the number of values matches the number of coulmns in the table+ "
					+ table);

			e.printStackTrace();
			return ERR;
		}
		connPull.close(conn);
		return OK;

	}

	/** select query 
	 * @param select- the string that should come after "SELECT"
	 * @param from- the string that should come after "FROM"
	 * @param where- the string that should come after "WHER"
	 * @return ResultSet with the result to that query
	 */
	@Override
	public ResultSet select(String select, String from, String where) {

		ResultSet result = null;
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = connPull.connectionCheck();
			stmt = conn.createStatement();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		try {
			if (where == "")
				result = stmt.executeQuery("SELECT " + select + " FROM " + from);
			else
				result = stmt.executeQuery("SELECT " + select + " FROM " + from
						+ " WHERE " + where);
		} catch (SQLException e) {
			System.out.println("Select Error");
			e.printStackTrace();
		}
		connPull.close(conn);
		return result;
	}

	/** update tuple
	 * @param tablrNamr- the table name as appear in the db. Comes after "UODATE"
	 * @param columnSet- string that should come after "SET"
	 * @param predicatesSet - string that should come after "WHERE"
	 */
	@Override
	public int update(String tableName, String columnSet, String predicatesSet) {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate("UPDATE " + tableName + " SET " + columnSet
					+" WHERE "+ predicatesSet);
		}

		catch (SQLException e) {

			e.printStackTrace();
			return ERR;
		}
		connPull.close(conn);
		return OK;

	}

	/** delete tuple
	 * @param tableName- the name of the table we want to delete from
	 * @param whereCol- string that comes after "WHERE"- tells which tuple to delete
	 */
	@Override
	public int delete(String tableName, String whereCol) {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate("DELETE FROM " + tableName + " WHERE "
					+ whereCol );
		}

		catch (SQLException e) {

			e.printStackTrace();
			return ERR;
		}

		return OK;
	}



	//*** import data from yago - when pressing "Update" ***//   

	/**
	 * Big update- parse yago files and Imdb files, Delete the content from the DB tables
	 * (not tables that "belong" to users) and insert all the the new parsed information instead.
	 * Check for updates made by users, in the tables that weren't deleted, and update
	 * the "yago tables" if needed
	 */
	@Override
	public int importData() {

		// get the movies list (with all the information about the movies)
		HashMap<String, Movie> moviesList = new HashMap<String, Movie>();

		// parse yago and imdb files 
		//		Iparser parser = new Parser();
		//		parser.parse();
		//		moviesList = parser.getMoviesTable();

		try {
			moviesList = (HashMap<String, Movie>) TestConsole
					.getObjFromFile("C:\\Users\\Roy\\Dropbox\\DB Project\\object");
		} catch (ClassNotFoundException e2) {		
			e2.printStackTrace();
		} catch (IOException e2) {		
			e2.printStackTrace();
		}

		// get the connection and statement
		Connection conn = getConnection();
		Statement stmt = getStatement(conn);
		//create prepared statements to populate the db
		PreparedStatement pstmt = null;
		PreparedStatement genreMovieStmt = null;
		PreparedStatement actorMovieStmt = null;
		PreparedStatement directorStmt = null;
		PreparedStatement actorStmt = null;
		PreparedStatement genreStmt = null;
		PreparedStatement languagStmt = null;

		long start = 0;// delete later

		try {
			//auto commit = false
			conn.setAutoCommit(false);
			// delete all the content from the "yago" tables
			deleteAllTablesContent(stmt);
			
			safelyClose(stmt);
			
			// create the prepared statements
			pstmt = conn.prepareStatement("INSERT INTO Movie(idMovie,idLanguage,idDirector,movieName,year,youtube,wiki,duration,plot) VALUES(?,?,?,?,?,?,?,?,?)");
			genreMovieStmt = conn.prepareStatement("INSERT INTO GenreMovie(idMovie, idGenre) VALUES(?,?)");
			actorMovieStmt = conn.prepareStatement("INSERT INTO ActorMovie(idMovie, idActor) VALUES(?,?)");
			directorStmt = conn.prepareStatement("Insert INTO Director(idDirector, directorName) VALUES (?,?)");
			actorStmt = conn.prepareStatement("Insert INTO Actor(idActor, actorName) VALUES (?,?)");
			languagStmt = conn.prepareStatement("INSERT INTO Language(idLanguage, LanguageName) VALUES (?,?)");
			genreStmt = conn.prepareStatement("INSERT INTO Genre(idGenre, genreName) VALUES (?,?)");
			
			// create HashSet to contains hashValues in order to verify if we already inserted this value to the db
			HashSet<Integer> actorsSet = new HashSet<Integer>();
			HashSet<Integer> directors = new HashSet<Integer>();
			HashSet<Integer> genres = new HashSet<Integer>();
			HashSet<Integer> languages = new HashSet<Integer>();

			start = System.currentTimeMillis();
			int movieHashValue;		
			int  count =0;
			//update the tables 
			for (Movie movie : moviesList.values()) {
								if(count>1000)
									break;
				count++;

				// calculate movieHashValue to be the idMovie and insert it to the table
				movieHashValue = movie.getId().hashCode();

				pstmt.setInt(IdMovie, movieHashValue);
				// add language to Movie and Language tables
				addLanguage(movie, languagStmt, pstmt, languages);
				// add director to Movie and Director tables
				addDirector(movie, directors, pstmt, directorStmt);
				// add facts that appear only in the Movie table and nowhere else
				addSingleFacts(movie, pstmt);
				//add and execute batch
				pstmt.addBatch();

				//add many to many facts- genres and actors
				addGenres(movie, genreStmt, genreMovieStmt, genres);
				addActors(movie, actorMovieStmt, actorStmt, actorsSet);

				if (count % 1000 ==0){
					directorStmt.executeBatch();
					actorStmt.executeBatch();
					genreStmt.executeBatch();
					languagStmt.executeBatch();
					pstmt.executeBatch();
					genreMovieStmt.executeBatch();
					actorMovieStmt.executeBatch();
					System.out.println(count);
				}
			}
			directorStmt.executeBatch();
			actorStmt.executeBatch();
			genreStmt.executeBatch();
			languagStmt.executeBatch();
			pstmt.executeBatch();
			genreMovieStmt.executeBatch();
			actorMovieStmt.executeBatch();

			// check for updates made by users and update the tables accordingly
			//commitUpdates(stmt);

			try {
				conn.commit();
			} catch (SQLException exp) {
				return ERR;				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return ERR;
		}
		finally {
			safelyClose(pstmt, stmt, genreStmt, languagStmt, directorStmt, actorStmt, genreMovieStmt, actorMovieStmt);
			connPull.close(conn);			
		}
		
		System.out.println("Time in sec= "
				+ (System.currentTimeMillis() - start) / 1000F);
		return OK;
	}


	//*** helper function - used by importData() ***//

	/** get a connection */
	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();
		} catch (SQLException e1) {			
			e1.printStackTrace();
		}
		return conn;
	}

	/** get statement from the conn */
	private Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return stmt;
	}

	/** delete content from the tables */
	private void deleteAllTablesContent(Statement stmt) {
		// Delete all relevant tables
		try {
			stmt.executeUpdate("DELETE FROM ActorMovie");
			stmt.executeUpdate("DELETE FROM GenreMovie");
			stmt.executeUpdate("DELETE FROM Movie");
			stmt.executeUpdate("DELETE FROM Actor");
			stmt.executeUpdate("DELETE FROM Director");
			stmt.executeUpdate("DELETE FROM Language");
			stmt.executeUpdate("DELETE FROM Genre");
		} catch (SQLException e) {
			System.out.println("Deleting Error");
			e.printStackTrace();
		}
	}

	/** add the language to the db- to Movie and Language tables */
	private void addLanguage(Movie movie, PreparedStatement languageStmt, PreparedStatement pstmt, HashSet<Integer> languages) {
		if (movie.getLanguage() != null) {
			// calc hashValue to be the language key
			int hashValue = movie.getLanguage().hashCode();
			try {
				//check if we didn't already inserted that language to the table
				if (!languages.contains(hashValue)) {
					//insert the language to the "Language" table
					//insertidName("Language", movie.getLanguage(), hashValue, stmt);
					languageStmt.setInt(1, hashValue);
					languageStmt.setString(2, movie.getLanguage());
					languageStmt.addBatch();
					// add the hashValue to languages to know that we insert it
					languages.add(hashValue);
				}
			} catch (SQLException exp) {
				System.out.println("Language already in the table");
			}

			// insert to Movie table
			try {
				pstmt.setInt(IdLanguage, hashValue);
			} catch (SQLException e) {
				System.out.println("Problem adding Idlanguage to Movie table");
				e.printStackTrace();
			}
		}
		else {
			try {
				pstmt.setNull(IdLanguage, 2);
			} catch (SQLException e) {
				System.out.println("Problem with null language");
				e.printStackTrace();
			}
		}
	}

	/** add the director to the db- to the Movie and Director table */
	private void addDirector(Movie movie, HashSet<Integer> directors, PreparedStatement pstmt, PreparedStatement directorStmt) {
		if ((movie.getDirector() != null)
				&& (movie.getDirector().getName() != null)) {
			// calc hashValue to be the director key
			int hashValue = movie.getDirector().getId().hashCode();
			try {
				if (!directors.contains(hashValue)) {
					//insertidName("Director", movie.getDirector().getName(),hashValue, stmt);
					directorStmt.setInt(1, hashValue);
					directorStmt.setString(2, movie.getDirector().getName());
					directorStmt.addBatch();
					directors.add(hashValue);
				}
			}
			catch (SQLException exp) {
				System.out.println("Director's already in table");
			}
			
			// adding to information the Movie table
			try {
				pstmt.setInt(IdDirector, hashValue);
			}
			catch (SQLException e) {
				System.out.println("Problem adding IdDirector");
				e.printStackTrace();
			}
		} 
		else {
			try {
				pstmt.setNull(IdDirector, 2);
			} catch (SQLException e) {
				System.out.println("Problem with null director");
				e.printStackTrace();
			}
		}

	}

	/** add facts that appear only in the Movie table */
	private void addSingleFacts(Movie movie, PreparedStatement pstmt) {
		try {
			pstmt.setString(MovieName, movie.getName());
			pstmt.setString(Duration, movie.getDuration());
			pstmt.setString(Year, movie.getDateCreated());
			pstmt.setString(Youtube, movie.getYouTubeURL());
			pstmt.setString(Wiki, movie.getWikiURL());
			pstmt.setString(Plot, movie.getPlot());
		} catch (SQLException e) {
			System.out.println("Problem adding single fact");
			e.printStackTrace();
		}

	}

	/** add actors to the Actor and ActorMovie tables */
	private void addActors(Movie movie, PreparedStatement actorMovieStmt, PreparedStatement actorStmt, HashSet<Integer> actorsSet) {
		List<Person> actors = new ArrayList<Person>();

		actors = movie.getActorsLst();
		int j = 0;

		while (j < actors.size()) {
			int hashValue = actors.get(j).getId().hashCode();
			try {
				if (!actorsSet.contains(hashValue)) {
					//insertidName("Actor", actors.get(j).getName(), hashValue,stmt);
					actorStmt.setInt(1, hashValue);
					actorStmt.setString(2, actors.get(j).getName());
					actorStmt.addBatch();

					actorsSet.add(hashValue);
				}
			}
			catch (SQLException exp) {
				System.out.println("Actor already in tables");
			}
			try {
				actorMovieStmt.setInt(1, movie.getId().hashCode());
				actorMovieStmt.setInt(2, hashValue);
				actorMovieStmt.addBatch();

			} catch (SQLException e) {
				System.out.println("problem inseting to actorMovie");
				e.printStackTrace();
			}
			j++;
		}

	}

	/** add genres to the Genre and GenreMovie tables */
	private void addGenres(Movie movie, PreparedStatement genreStmt, PreparedStatement genreMovieStmt, HashSet<Integer> genres) {

		Set<String> genreList = new LinkedHashSet<String>();
		genreList = movie.getGenre();

		Iterator<String> itr = genreList.iterator();
		while (itr.hasNext()) {

			String currentGenre = itr.next();
			int hashValue = currentGenre.hashCode();
			try {
				if (!genres.contains(hashValue)) {
					//insertidName("Genre", currentGenre, hashValue, stmt);
					genreStmt.setInt(1, hashValue);
					genreStmt.setString(2, currentGenre);
					genreStmt.addBatch();
					genres.add(hashValue);
				}

			} catch (SQLException exp) {
				System.out.println("Genre already in tables");
			}
			// insert t
			try {
				genreMovieStmt.setInt(1, movie.getId().hashCode());
				genreMovieStmt.setInt(2, hashValue);
				genreMovieStmt.addBatch();
				
			} catch (SQLException e) {
				System.out.println("Problem with insert to genreMovie");
				e.printStackTrace();
			}
		}

	}

	/** insert to table <table> the tuple <hashId> , <name> */
	private void insertidName(String table, String name,
			int HashId, Statement sta) throws SQLException {

		try {
			String state = "INSERT INTO " + table + " VALUES (" + HashId + ",'"
					+ name + "')";

			sta.executeUpdate(state);
		}

		catch (SQLException exp) {

			String state = "INSERT INTO " + table + " VALUES (" + HashId
					+ ",\"" + name + "\")";
			sta.executeUpdate(state);
		}
	}

	/** close safely the resources */
	private void safelyClose(AutoCloseable... resources) {
		for (AutoCloseable resource : resources) {
			try {
				resource.close();
				System.out.println(resources + "was closed safely");
			}
			catch (Exception e) {
			}
		}
	}

	/** check for updates made by users and update the tables accordingly */
	public void commitUpdates(Statement stmt) {

		ResultSet updateSet = null;
		// get All the tuples from the Update table
		updateSet = select("*", "Updates", "");
		String tableName=null;
		String columnValue=null;
		int newVal=0;
		int key1=0;
		int key2=0;
		String str = null, str2=null;

		try{
			while (updateSet.next()) {

				tableName=updateSet.getString(1);
				columnValue=updateSet.getString(2);
				newVal=updateSet.getInt(3);
				key1=updateSet.getInt(4);
				key2=updateSet.getInt(5);


				if ((newVal==0) && !(tableName.equals("Movie")) ){ // Delete operation- Table
					// name, key1, key2 - only
					// for ActorMovie,
					// GenreMovie
					if (tableName.equals("ActorMovie"))
						str= "idActor="+key2+" AND idMovie="+key1;
					else str= "idGenre="+key2+" AND idMovie="+key1;
					delete(tableName,str);


				}
				else if ((key2==0) && !(tableName.equals("Movie")))  // Insert operation -
					// Table name, key1,
					// key2- only for
					// ActorMovie,GenreMovie
					insert(tableName, Integer.toString(key1), Integer.toString(newVal));

				else{
					str=columnValue+"="+newVal;

					if (tableName.equals("Movie"))
						str2="idMovie="+key1;


					else if (tableName.equals("ActorMovie"))
						str2="idMovie="+key1+" AND "+ "idActor="+key2;
					else
						str2="idMovie="+key1+" AND "+ "idGenre="+key2;
					update(tableName, str,
							str2);
				}
			}

		}
		catch (SQLException e) {
			System.out.println("Updates Failed");
			e.printStackTrace();
		}
	}


}

