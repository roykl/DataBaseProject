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

public class DBOparations implements IdbOparations {

	static JDBCConnectionPooling connPull;

	// One row operations

	public DBOparations(JDBCConnectionPooling connParam) {
		connPull = connParam;
	}

//	@Override
//	public  int update(String tableNamr, String columnSet,
//			String predicatesSet, String columnWhere, String predicateWhere) {
//
//		Connection conn = null;
//		try {
//			conn = connPull.connectionCheck();
//
//			Statement stmt = null;
//
//			stmt = conn.createStatement();
//
//			stmt.executeUpdate("UPDATE " + tableNamr + " SET " + columnSet
//					+ "=" + "'" + predicatesSet + "'" + " WHERE " + columnWhere
//					+ "=" + "'" + predicateWhere + "'");
//		}
//
//		catch (SQLException e) {
//
//			e.printStackTrace();
//			return 0;
//		}
//
//		return 1;
//	}
//
	private void addSingleFacts(Movie movie, PreparedStatement pstmt) {
		try {
			pstmt.setString(4, movie.getName());
			pstmt.setString(8, movie.getDuration());
			pstmt.setString(5, movie.getDateCreated());
			pstmt.setString(6, movie.getYouTubeURL());
			pstmt.setString(7, movie.getWikiURL());
			pstmt.setString(9, movie.getPlot());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public int update(String tableNamr, String columnSet, String predicatesSet) {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate("UPDATE " + tableNamr + " SET " + columnSet
				+ " WHERE "	+ predicatesSet);
		}

		catch (SQLException e) {

			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public  int delete(String tableNamr, String whereCol)
			 {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();

			stmt.executeUpdate("DELETE FROM " + tableNamr + " WHERE "
					+ whereCol );
		}

		catch (SQLException e) {

			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	@Override
	public  int insert(String table, String... values) {

		int i;
		String state = "INSERT INTO " + table + " VALUES (";

		for (i = 0; i < Array.getLength(values); i++) {
			state = state + "'" + values[i] + "'";

			if (i != Array.getLength(values) - 1)
				state = state + ",";
		}

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
			return 0;
		}

		return 1;

	}

	public  void insertidName(String table, String name,
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

	
	//if not working return null
	@Override
	public  ResultSet select(String select, String from,
			String where) {
		Connection conn = null;
		try {
			conn = connPull.connectionCheck();

			Statement stmt = null;

			stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT " + select + " FROM " + from
					+" WHERE "+ where);
			return result;
		}

		
		catch (SQLException e) {

			e.printStackTrace();
			
		}
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
	private Connection getConnection() {
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
	private Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}

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

	@Override
	public  void importData() {
		HashMap<String, Movie> moviesList = new HashMap<String, Movie>();
		try {
			moviesList = (HashMap<String, Movie>) TestConsole
					.getObjFromFile("C:\\Users\\Nir\\Dropbox\\DB Project\\object");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Connection conn = getConnection();
		Statement stmt = getStatement(conn);

		try {
			conn.setAutoCommit(false);
			deleteAllTablesContent(stmt);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		long start = 0;
		try {
			PreparedStatement pstmt = null;
			pstmt = conn
					.prepareStatement("INSERT INTO Movie(idMovie,idLanguage,idDirector,movieName,year,youtube,wiki,duration,plot) VALUES(?,?,?,?,?,?,?,?,?)");

			int movieHashValue;
			int count = 0;

			HashSet<Integer> actorsSet = new HashSet<Integer>();
			HashSet<Integer> directors = new HashSet<Integer>();
			HashSet<Integer> genres = new HashSet<Integer>();
			HashSet<Integer> languages = new HashSet<Integer>();

			start = System.currentTimeMillis();

			for (Movie movie : moviesList.values()) {
				
				
				movieHashValue = movie.getId().hashCode();
				pstmt.setInt(1, movieHashValue);
				addLanguage(movie, stmt, pstmt, languages);
				addDirector(movie, stmt, directors, pstmt);
				addSingleFacts(movie, pstmt);
				pstmt.addBatch();
				pstmt.executeBatch();
				addGenres(movie, stmt, genres);
				addActors(movie, stmt, actorsSet);
			}

			try {
				conn.commit();
			} catch (SQLException exp) {
				System.out.println("I failed to commit");
			}

			try {
				safelyClose(pstmt);
				safelyClose(stmt);
			} catch (Exception e) {
				System.out.println("Failed - problem closing statements");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Time in sec= "
				+ (System.currentTimeMillis() - start) / 1000F);
	}

	private void addLanguage(Movie movie, Statement stmt,
			PreparedStatement pstmt, HashSet<Integer> languages) {
		if (movie.getLanguage() != null) {
			int hashValue = movie.getLanguage().hashCode();
			try {
				if (!languages.contains(hashValue)) {
					insertidName("Language", movie.getLanguage(), hashValue,
							stmt);
					languages.add(hashValue);
				}
			} catch (SQLException exp) {
				System.out.println("Language already in the table");
			}

			try {
				pstmt.setInt(2, hashValue);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				pstmt.setNull(2, 2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void addDirector(Movie movie, Statement stmt,
			HashSet<Integer> directors, PreparedStatement pstmt) {

		if ((movie.getDirector() != null)
				&& (movie.getDirector().getName() != null)) {

			int hashValue = movie.getDirector().getId().hashCode();
			try {
				if (!directors.contains(hashValue)) {
					insertidName("Director", movie.getDirector().getName(),
							hashValue, stmt);
					directors.add(hashValue);
				}
			} catch (SQLException exp) {

				System.out.println("Director's already in table");
			}
			try {
				pstmt.setInt(3, hashValue);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				pstmt.setNull(3, 2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void addActors(Movie movie, Statement stmt,
			HashSet<Integer> actorsSet) {
		List<Person> actors = new ArrayList<Person>();

		actors = movie.getActorsLst();
		int j = 0;

		while (j < actors.size()) {

			int hashValue = actors.get(j).getId().hashCode();
			try {
				if (!actorsSet.contains(hashValue)) {
					insertidName("Actor", actors.get(j).getName(), hashValue,
							stmt);
					actorsSet.add(hashValue);
				}

			}

			catch (SQLException exp) {
				System.out.println("Actor already in tables");
			}

			try {
				stmt.executeUpdate("INSERT INTO ActorMovie(idActor,idMovie) VALUES("
						+ hashValue + ",'" + movie.getId().hashCode() + "')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			j++;
		}

	}

	private void addGenres(Movie movie, Statement stmt, HashSet<Integer> genres) {

		Set<String> genreList = new LinkedHashSet<String>();
		genreList = movie.getGenre();

		Iterator<String> itr = genreList.iterator();
		while (itr.hasNext()) {

			String currentGenre = itr.next();
			int hashValue = currentGenre.hashCode();
			try {
				if (!genres.contains(hashValue)) {
					insertidName("Genre", currentGenre, hashValue, stmt);
					genres.add(hashValue);
				}

			} catch (SQLException exp) {
				System.out.println("Genre already in tables");

			}

			try {
				stmt.executeUpdate("INSERT INTO GenreMovie(idGenre,idMovie) VALUES("
						+ hashValue + ",'" + movie.getId().hashCode() + "')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
