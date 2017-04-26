/**
 * 
 */
package gov.shdrc.home.dao.jdbc;

import gov.shdrc.home.dao.IMessageAlertDAO;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageAlertDAOJdbc implements IMessageAlertDAO {	

/*private static final Logger logger = Logger.getLogger(MessageAlertDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
	
	public boolean insertMessageAlert(String[] directorateIds,String sendSMS,String message,String messageType,String userName,String newsHeader){
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_MESSAGE_ALERT);
			
			for(int i=0;i<directorateIds.length;i++){
				Integer directorateId=Integer.parseInt(directorateIds[i]);
			 preparedStatementInsert.setInt(1,directorateId);
			 preparedStatementInsert.setString(2,sendSMS);
			 preparedStatementInsert.setString(3,message);
			 preparedStatementInsert.setString(4,messageType);
			 preparedStatementInsert.setString(5,Util.isNullOrBlank(newsHeader)?null:newsHeader);
			 java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
				preparedStatementInsert.setTimestamp(6,addOnDate);
				preparedStatementInsert.setString(7,userName);
				preparedStatementInsert.addBatch();
			}
			preparedStatementInsert.executeBatch();
			successFlag=true;
	        connection.commit();
	     }catch (SQLException e) {
	    	 e.printStackTrace();
	         try {
	        	 connection.rollback();
	         }catch (SQLException e1) { 
	        	 e1.printStackTrace();
	          }
	      }finally{
	    	  try {
	    		  if(preparedStatementInsert != null) {
	    			  preparedStatementInsert.close();
		          }
		          if(connection != null) {
		        	  connection.close();
	              }
	          }catch (SQLException ex) {
	        	  ex.printStackTrace();
	           }
	       }
	       return successFlag;
	}
}
