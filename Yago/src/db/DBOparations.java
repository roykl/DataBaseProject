package db;

import java.sql.ResultSet;

public class DBOparations implements IdbOparations {

//One row oparations
	
public DBOparations(){
	
}

@Override
public int update(String tableNamr, String column, String predicates) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int delete(String tableNamr, String whereStatment) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int insert(String statment) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public ResultSet select(String select, String from, String where) {
	// TODO Auto-generated method stub
	return null;
}


}
