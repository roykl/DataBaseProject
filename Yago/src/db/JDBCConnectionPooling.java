package db;
import java.util.*;
import java.sql.*;

public class JDBCConnectionPooling implements Runnable {
        int initialConnections = 5;
        Vector connectionsAvailable = new Vector();
        Vector connectionsUsed = new Vector();

        String connectionUrl ;
        String userName;
        String userPassword ;
        
        
        public JDBCConnectionPooling(String url,String userName, String userPass) throws SQLException {
                try {
                        this.connectionUrl = url;
                        this.userName = userName;
                        this.userPassword = userPass;
                        Class.forName("com.mysql.jdbc.Driver");
                        for (int count = 0; count < initialConnections; count++) {
                                connectionsAvailable.addElement(getConnection());
                        }
                } catch (ClassNotFoundException e) {
                        System.out.println(e.toString());
                }
        }
        private Connection getConnection() throws SQLException {
                return DriverManager.getConnection(connectionUrl, userName,
                                userPassword);
        }
        
        public synchronized void close(Connection conn){
        	
        	Connection connToRemove = null;
        	int indexToRemove;
        	
        	indexToRemove = connectionsUsed.indexOf(conn);
        	connToRemove = (Connection) connectionsUsed.remove(indexToRemove);
        	connectionsAvailable.add(connToRemove);
        }
        
        public synchronized Connection connectionCheck() throws SQLException {
                Connection newConnection = null;
                if (connectionsAvailable.size() == 0) {
                        // creating a new Connection
                        newConnection = getConnection();
                        // adding Connection to used list
                        connectionsUsed.addElement(newConnection);
                } else {
                        newConnection = (Connection) connectionsAvailable.lastElement();

                        connectionsAvailable.removeElement(newConnection);

                        connectionsUsed.addElement(newConnection);
                }
                return newConnection;
        }

        public int availableCount() {
                return connectionsAvailable.size();
        }
        public void run() {
                try {
                        while (true) {
                                synchronized (this) {
                                        while ((connectionsAvailable.size() > 0) &&(connectionsAvailable.size() + connectionsUsed.size() > initialConnections)) {
                                                Connection connection = (Connection) connectionsAvailable
                                                                .lastElement();
                                                connectionsAvailable.removeElement(connection);

                                                connection.close();
                                        }

                                }
                        }
                } catch (SQLException sqle) {
                        sqle.printStackTrace();
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}