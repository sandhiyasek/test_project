package gov.shdrc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DatabaseConnectionHelper {
	private static DatabaseConnectionHelper databaseConnectionHelper=null;
	private static Properties properties=null;
	private static InitialContext ctx=null;
	private static DataSource dataSource=null;
	private DatabaseConnectionHelper(){
	}
	public static DatabaseConnectionHelper getInstance(){
			try {
				if(databaseConnectionHelper==null){	
					databaseConnectionHelper = new DatabaseConnectionHelper();
					properties = new Properties();
					InputStream inputStream = DatabaseConnectionHelper.class.getClassLoader().getResourceAsStream("scheduler_connection.properties");
					properties.load(inputStream);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return databaseConnectionHelper;
	}
	public Connection getConnection(){
		Connection connection=null;
		try {
			Class.forName(properties.getProperty("shdrc.driverClassName"));
			connection=DriverManager.getConnection( properties.getProperty("shdrc.url"), properties.getProperty("shdrc.username"), properties.getProperty("shdrc.password"));
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		}
	/*public static DatabaseConnectionHelper getInstance(){
		try {
			if(databaseConnectionHelper==null){	
				databaseConnectionHelper = new DatabaseConnectionHelper();
				ctx = new InitialContext();
				dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/shdrc");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return databaseConnectionHelper;
	}
	public Connection getConnection(){
		Connection connection=null;
		try {
			connection=dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}*/
	public static void main(String arg[]){
		Connection connection=DatabaseConnectionHelper.getInstance().getConnection();
		String s=null;		
	}
	
}
