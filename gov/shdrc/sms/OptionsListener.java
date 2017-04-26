package gov.shdrc.sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class OptionsListener extends Thread {
	@Autowired
	JdbcTemplate jdbcTemplate;	
    private int threadMills = 1000;
    private Connection conn;
    private org.postgresql.PGConnection pgconn;
    //private Options optionsInstance;
   /* private static final String DB_URL;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;*/

   /* static {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DB_URL = (String) envContext.lookup("jdbc/shdrc");
            DB_USERNAME = (String) envContext.lookup("jdbc/shdrc/username");
            DB_PASSWORD = (String) envContext.lookup("jdbc/shdrc/password");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }*/

    //OptionsListener(Options instance, int threadMillis) {
    OptionsListener(int threadMillis) {
       // optionsInstance = instance;
        this.threadMills = threadMillis;

        try {
            /*Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);*/
        	
            conn=jdbcTemplate.getDataSource().getConnection();
            Statement stmt = conn.createStatement();
           // stmt.executeUpdate("NOTIFY mynotification, 'message'");
            stmt.execute("LISTEN object_updated");
            stmt.close();
        } catch (SQLException e) {
           // Log.addItem(getClass().getName() + " sql error:" + e.getMessage());
        	System.out.println(e);
        } 
    }

    @Override
    public void run() {
/*    	Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute("LISTEN object_updated");
	    	stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	while (true) {
            try {
            	PGConnection pgconn=(PGConnection) conn;
                PGNotification notifications[] = pgconn.getNotifications();
                if (notifications != null) {
                    //optionsInstance.loadDbData();
                	for (int i=0; i<notifications.length; i++) {
						System.out.println("Got notification: " + notifications[i].getName());
						System.out.println("Got notification Parameter: " + notifications[i].getParameter());
					}
                }

                Thread.sleep(threadMills); 
            } catch (SQLException sqle) {
               // Log.addItem(getClass().getName() + " sql error:" + sqle.getMessage());
            	sqle.printStackTrace();
            } catch (InterruptedException ie) {
               // Log.addItem(getClass().getName() + " thread error: " + ie.getMessage());
            } 
        }
    }
    public static void  main(String[] main){
    	Connection conn=null;
    	//Connection conn=DatabaseConnectionHelper.getInstance().getConnection();
    	Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("LISTEN mynotification");
			stmt.executeUpdate("NOTIFY mynotification, 'message'");
			stmt.close();
			PGConnection pgconn=(PGConnection) conn;
            PGNotification notifications[] = pgconn.getNotifications();
	        if (notifications != null) {
	            //optionsInstance.loadDbData();
	        	System.out.println("inside notifications");
	        }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*OptionsListener op = new OptionsListener(100);
    	op.start();*/
    }
}
