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

	@Override
	public synchronized void importData() {

		Connection conn = null;
		try {
			conn = connPull.connectionCheck();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			// TODO Auto-generated catch block
			System.out.println("Deleting Error");
			e.printStackTrace();
		}

		finally {
			safelyClose(stmt);
		}

		PreparedStatement pstmt = null;

		try {

			int i;

			HashMap<String, Movie> moviesList = new HashMap<String, Movie>();
			Iparser yp = new Parser();
			yp.parse();
			moviesList = yp.getMoviesTable();

			
			conn.setAutoCommit(false);
			pstmt = conn
					.prepareStatement("INSERT INTO Movie(idMovie,idLanguage,idDirector,movieName,year,youtube,wiki,duration,plot) VALUES(?,?,?,?,?,?,?,?,?)");

			Statement verifier = null;

			ResultSet x;
			verifier = conn.createStatement();
			for (Movie movie : moviesList.values()) {
				int a;
				int genre = 0;
				
				i=movie.getId().hashCode();
				// putting the movie Id to the movie table, not adding batch yet
				pstmt.setInt(1, i);
				if (movie.getLanguage() != null) {
					x = verifier
							.executeQuery("SELECT * FROM Language WHERE languageName='"
									+ movie.getLanguage() + "'");
					// First we check if the langugae of the film is already
					// exists,
					// otherwise we add it
					if (!x.next()) {
						x = verifier
								.executeQuery("SELECT COUNT(*) FROM Language");
						x.next();

						a = x.getInt(1) + 1; // number of keys so we put the
												// correct
												// Id next to the new language
						System.out
								.println("Language not located....new language is insert");
						verifier.executeUpdate("INSERT INTO Language(idLanguage,languageName) VALUES("
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

					try {

						x = verifier
								.executeQuery("SELECT * FROM Director WHERE directorName=\""
										+ movie.getDirector().getName() + "\"");

						if (!x.next()) {

							x = verifier
									.executeQuery("SELECT COUNT(*) FROM Director");
							x.next();
							a = x.getInt(1) + 1; // number of keys so we put the
													// correct
													// Id next to the new
													// Director
							System.out
									.println("Director not located.... new Director is insert");
							verifier.executeUpdate("INSERT INTO Director(idDirector,directorName) VALUES("
									+ a
									+ ",\""
									+ movie.getDirector().getName()
									+ "\")");

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

					catch (SQLException exp) {

						System.out
								.println("Error with the's name "+movie.getDirector().getName()+", parsing voilating the SQL command");

					
					
					
					}

					finally {

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

					x = verifier
							.executeQuery("SELECT * FROM Genre WHERE genreName='"
									+ currentGenre + "'");
					if (!x.next()) {
						x = verifier.executeQuery("SELECT COUNT(*) FROM Genre");
						x.next();
						genre = x.getInt(1) + 1; // number of keys so we put the
													// correct
													// Id next to the new Genre
						System.out
								.println("Genre not located.... new Genre is insert");

						verifier.executeUpdate("INSERT INTO Genre(idGenre,genreName) VALUES("
								+ genre + ",'" + currentGenre + "')");

						verifier.executeUpdate("INSERT INTO GenreMovie(idGenre,idMovie) VALUES("
								+ genre + ",'" + i + "')");

					}

					else {

						verifier.executeUpdate("INSERT INTO GenreMovie(idGenre,idMovie) VALUES("
								+ x.getInt(1) + ",'" + i + "')");

					}
				}

				List<Person> actors = new ArrayList<Person>();

				actors = movie.getActorsLst();
				int j = 0;

				while (j < actors.size()) {
					try {
						x = verifier
								.executeQuery("SELECT * FROM Actor WHERE actorName=\""
										+ actors.get(j).getName() + "\"");
						if (!x.next()) {
							x = verifier
									.executeQuery("SELECT COUNT(*) FROM Actor");
							x.next();
							a = x.getInt(1) + 1; // number of keys so we put the
													// correct Id next to the
													// new
													// Actor
							System.out
									.println("Actor not located....new Actor is insert");

							verifier.executeUpdate("INSERT INTO Actor(idActor,actorName) VALUES("
									+ a + ",\"" + actors.get(j).getName() + "\")");

							verifier.executeUpdate("INSERT INTO ActorMovie(idActor,idMovie) VALUES("
									+ a + ",\"" + i + "\")");

						}
						
						
						else{
							verifier.executeUpdate("INSERT INTO ActorMovie(idActor,idMovie) VALUES("
									+ x.getInt(1) + ",\"" + i + "\")");
							
						}

					}

					catch (SQLException exp) {

						System.out
								.println("Error with the actor's name, parsing voilating the SQL command");

					}

					finally {
						j++;
					}
				}

			}

			conn.commit();

			safelyClose(pstmt);
			safelyClose(verifier);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Updating the tables

	}

}
