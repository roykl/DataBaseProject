package DB;

import com.mysql.jdbc.Connection;

public interface Iimport {

	public void importData(Connection conn,String path);
}
