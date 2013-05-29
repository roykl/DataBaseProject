package db;

import java.lang.reflect.Array;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.sql.Connection;

import parsing.*;

public class DBOparations implements IdbOparations {

	static JDBCConnectionPooling connPull;

	// One row operations

	public DBOparations(JDBCConnectionPooling connParam) {
		connPull = connParam;
	}

	@Override
	public synchronized int update(String tableNamr, String columnSet,
			String predicatesSet, String columnWhere, String predicateWhere) {

		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate("UPDATE " + tableNamr + " SET " + columnSet
					+ "=" + "'" + predicatesSet + "'" + " WHERE " + columnWhere
					+ "=" + "'" + predicateWhere + "'");
		}

		catch (SQLException e) {

			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public int update(String tableNamr, String columnSet, String predicatesSet) {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate("UPDATE " + tableNamr + " SET " + columnSet
					+ "=" + "'" + predicatesSet + "'");
		}

		catch (SQLException e) {

			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public synchronized int delete(String tableNamr, String whereCol,
			String pred) {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate("DELETE FROM " + tableNamr + " WHERE "
					+ whereCol + "=" + "'" + pred + "'");
		}

		catch (SQLException e) {

			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public synchronized int insert(String table, String... values) {

		int i;
		String state = "INSERT INTO " + table + " VALUES+ (";

		for (i = 0; i < Array.getLength(values); i++) {
			state = state + "'" + values[i] + "'";

			if (i != Array.getLength(values) - 1)
				state = state + ",";
		}

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
			return 0;
		}

		return 1;

	}

	@Override
	public synchronized ResultSet select(String select, String from,
			String where) {
		// TODO Auto-generated method stub
		return null;
	}

	public void safelyClose(AutoCloseable... resources) {
		for (AutoCloseable resource : resources) {
			try {
				resource.close();
				System.out.println(resources + "was closed safely");
			} catch (Exception e) {
			}
		}
	}
	
	/** get a connection */
	private Connection getConnection(){
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();			
		}
		return conn;
	}

	/** get statement from the conn */
	private Statement getStatement(Connection conn){
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
	
	private void deleteAllTablesContent(Statement stmt){
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
	
	@Override
	public synchronized void importData() {

		// get a connection and statement and delete tables content
			Connection conn = getConnection();
			Statement stmt = getStatement(conn);
			
		try {	
			conn.setAutoCommit(false);

			// Delete all relevant tables
			deleteAllTablesContent(stmt);
			
		} catch (SQLException e1) {		
			e1.printStackTrace();
		}

	try{
		PreparedStatement pstmt = null;
		// create Parser object, start parsing and get all movies details
		Iparser yp = new Parser();
		yp.parse();
		HashMap<String, Movie> moviesList = yp.getMoviesTable();

			
			// prepare statement 
			pstmt = conn
					.prepareStatement("INSERT INTO Movie(idMovie,idLanguage,idDirector,movieName,year,youtube,wiki,duration,plot) VALUES(?,?,?,?,?,?,?,?,?)");		

			ResultSet x;
			int hashValue;
			
			for (Movie movie : moviesList.values()) {
				int a;
				int genre = 0;
				
				hashValue = movie.getId().hashCode();
				// putting the movie Id to the movie table, not adding batch yet
				pstmt.setInt(1, hashValue);
				
				
				if (movie.getLanguage() != null) {
					x = stmt
							.executeQuery("SELECT * FROM Language WHERE languageName='"
									+ movie.getLanguage() + "'");
					// First we check if the langugae of the film is already
					// exists,
					// otherwise we add it
					if (!x.next()) {
						x = stmt
								.executeQuery("SELECT COUNT(*) FROM Language");
						x.next();

						a = x.getInt(1) + 1; // number of keys so we put the
												// correct
												// Id next to the new language
						System.out
								.println("Language not located....new language is insert");
						stmt.executeUpdate("INSERT INTO Language(idLanguage,languageName) VALUES("
								+ a + ",'" + movie.getLanguage() + "')");
						// putting the language Id to the movie table in the
						// correct
						// movie, not adding batch yet
						pstmt.setInt(2, a);

					}

					else { // The language was located but we still need to
							// figure
							// out its ID so we put the correct ID next to the
							// movie

						// now we at the first row of the table
						// "SELECT * FROM Language WHERE languageName='"
						int id = x.getInt(1); // now we have the language Id
						pstmt.setInt(2, id); // we put the correct Id of the
												// language to the current movie

					}
				} else {
					pstmt.setNull(2, 2);
				}
				// Next we check if the Director of the film is already exists,
				// otherwise we add it

				if ((movie.getDirector() != null)
						&& (movie.getDirector().getName() != null)) {

					x=null;
					try {

						x = stmt
								.executeQuery("SELECT * FROM Director WHERE directorName=\""
										+ movie.getDirector().getName() + "\"");

						
					}
					
					
					catch(SQLException expc){
						
						
						
						x = stmt
								.executeQuery("SELECT * FROM Director WHERE directorName='"
										+ movie.getDirector().getName() + "'");
						
					}
						if (!x.next()) {

							x = stmt
									.executeQuery("SELECT COUNT(*) FROM Director");
							x.next();
							a = x.getInt(1) + 1; // number of keys so we put the
													// correct
													// Id next to the new
													// Director
							System.out
									.println("Director not located.... new Director is insert");
							
							
							try{
							stmt.executeUpdate("INSERT INTO Director(idDirector,directorName) VALUES("
									+ a
									+ ",\""
									+ movie.getDirector().getName()
									+ "\")");

							
							}
							
							catch(SQLException exp){
								
								stmt.executeUpdate("INSERT INTO Director(idDirector,directorName) VALUES("
										+ a
										+ ",'"
										+ movie.getDirector().getName()
										+ "')");
								
							

						}
							
							pstmt.setInt(3, a);
							
						}

						else { // The Director was located but we still need to
								// figure
								// out its ID so we put the correct ID next to
								// the movie

							// now we at the first row of the table
							// "SELECT * FROM Director WHERE DirectorName='"
							int id = x.getInt(1); // now we have the language Id
							pstmt.setInt(3, id); // we put the correct Id of the
													// Director to the current
													// movie,
													// not batching yet!

						}

					}

					
				else {

					pstmt.setNull(3, 2);

				}
				// Next we check if the the genre of the movie is already
				// exists,
				// otherwise we add it

				pstmt.setString(4, movie.getName());
				pstmt.setString(8, movie.getDuration());
				pstmt.setString(5, movie.getDateCreated());
				pstmt.setString(6, movie.getYouTubeURL());
				pstmt.setString(7, movie.getWikiURL());
				pstmt.setString(9, movie.getPlot());
				pstmt.addBatch();
				pstmt.executeBatch();

				Set<String> genreList = new LinkedHashSet<String>();
				genreList = movie.getGenre();

				Iterator<String> itr = genreList.iterator();
				while (itr.hasNext()) {

					String currentGenre = itr.next();

					x = stmt
							.executeQuery("SELECT * FROM Genre WHERE genreName='"
									+ currentGenre + "'");
					if (!x.next()) {
						x = stmt.executeQuery("SELECT COUNT(*) FROM Genre");
						x.next();
						genre = x.getInt(1) + 1; // number of keys so we put the
													// correct
													// Id next to the new Genre
						System.out
								.println("Genre not located.... new Genre is insert");

						stmt.executeUpdate("INSERT INTO Genre(idGenre,genreName) VALUES("
								+ genre + ",'" + currentGenre + "')");

						stmt.executeUpdate("INSERT INTO GenreMovie(idGenre,idMovie) VALUES("
								+ genre + ",'" + hashValue + "')");

					}

					else {

						stmt.executeUpdate("INSERT INTO GenreMovie(idGenre,idMovie) VALUES("
								+ x.getInt(1) + ",'" + hashValue + "')");

					}
				}

				List<Person> actors = new ArrayList<Person>();

				actors = movie.getActorsLst();
				int j = 0;

				while (j < actors.size()) {
					try {
						x = stmt
								.executeQuery("SELECT * FROM Actor WHERE actorName=\""
										+ actors.get(j).getName() + "\"");
					}
					
					catch(SQLException sql){
						x = stmt
								.executeQuery("SELECT * FROM Actor WHERE actorName='"
										+ actors.get(j).getName() + "'");
						
						
					}
						if (!x.next()) {
							x = stmt
									.executeQuery("SELECT COUNT(*) FROM Actor");
							x.next();
							a = x.getInt(1) + 1; // number of keys so we put the
													// correct Id next to the
													// new
													// Actor
							System.out
									.println("Actor not located....new Actor is insert");

							
							try{
							stmt.executeUpdate("INSERT INTO Actor(idActor,actorName) VALUES("
									+ a + ",\"" + actors.get(j).getName() + "\")");

							stmt.executeUpdate("INSERT INTO ActorMovie(idActor,idMovie) VALUES("
									+ a + ",\"" + hashValue + "\")");

						}
							
							catch(SQLException exp){
								stmt.executeUpdate("INSERT INTO Actor(idActor,actorName) VALUES("
										+ a + ",'" + actors.get(j).getName() + "')");

								stmt.executeUpdate("INSERT INTO ActorMovie(idActor,idMovie) VALUES("
										+ a + ",'" + hashValue + "')");
							}
							
						}
						
						
						else{
							
							try{
							stmt.executeUpdate("INSERT INTO ActorMovie(idActor,idMovie) VALUES("
									+ x.getInt(1) + ",\"" + hashValue + "\")");
							}
							
							catch (SQLException sql){
								
								stmt.executeUpdate("INSERT INTO ActorMovie(idActor,idMovie) VALUES("
										+ x.getInt(1) + ",'" + hashValue + "')");
								
						}

					}

											j++;
					
				}

			}

			conn.commit();

			safelyClose(pstmt);
			safelyClose(stmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Updating the tables

	}

}
