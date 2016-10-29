package com.IgniteEvents.automation.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;


public class ConnectDatabase extends SeleneseTestCase{

	private String driver = "com.mysql.jdbc.Driver";//"oracle.jdbc.driver.OracleDriver";//"org.gjt.mm.mysql.Driver";//"com.mysql.jdbc.Driver";
	
	private String url = "jdbc:mysql://ignite-big5.ignite.net:3306/igniteoltp";
	
	String sid = "xe";
	
	private String user = "app_hq_rw";
	
	private String pssword = "app_hq_rw";
	
	public IDatabaseConnection getConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
		logger.info("Try connect to DB on the - " + url.substring(url.indexOf('/') + 2));
		Class.forName(driver);
		Connection jdbcConnection = DriverManager.getConnection(url, user, pssword);
		IDatabaseConnection i = new DatabaseConnection(jdbcConnection);
		logger.info("Connection is established ");
		return i;
	}
	 
}
