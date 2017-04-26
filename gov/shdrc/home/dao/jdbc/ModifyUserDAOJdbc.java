/**
 * 
 */
package gov.shdrc.home.dao.jdbc;

import gov.shdrc.home.dao.IModifyUserDAO;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ModifyUserDAOJdbc implements IModifyUserDAO {	

/*private static final Logger logger = Logger.getLogger(ModifyUserDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
	
	public User getUserDetails(String userName){
		User user=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    
		try {		
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_USER_DETAILS);
			preparedStatement.setString(1,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	user =new User();
	        	user.setUfname(resultSet.getString(1));
	        	user.setUlname(resultSet.getString(2));
	        	user.setUpass(resultSet.getString(3));
	        	user.setEmail(resultSet.getString(4));
	        	user.setMobile(resultSet.getLong(5));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		  }
		finally{
			try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	            	connection.close();
	            }	
	        } catch (SQLException ex) {
	        	ex.printStackTrace();
	          }	
		}
		return user;
	}
	
	public boolean updateUserDetails(String firstName,String lastName,String email,Long mobile,String userName){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_USER_DETAILS);
			preparedStatementUpdate.setString(1,firstName);
			preparedStatementUpdate.setString(2,lastName);
			preparedStatementUpdate.setString(3,email);
			preparedStatementUpdate.setLong(4,mobile);
			java.sql.Timestamp updatedOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementUpdate.setTimestamp(5,updatedOnDate);
			preparedStatementUpdate.setString(6,userName);
			preparedStatementUpdate.execute();
			successFlag=true;
			connection.commit();
	    } catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		  }finally{
				try {
	                if (preparedStatementUpdate != null) {
	                	preparedStatementUpdate.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }	
			}
		return successFlag;
	}
}
