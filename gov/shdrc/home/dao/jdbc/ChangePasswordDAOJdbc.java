/**
 * 
 */
package gov.shdrc.home.dao.jdbc;

import gov.shdrc.home.dao.IChangePasswordDAO;
import gov.shdrc.util.SecurityUtil;
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
public class ChangePasswordDAOJdbc implements IChangePasswordDAO {	

/*private static final Logger logger = Logger.getLogger(ChangePasswordDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
	
	public User getuserList(String userName){
		User user=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {		
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_USER_PWD);
			preparedStatement.setString(1,userName);
			resultSet = preparedStatement.executeQuery();
		    while (resultSet.next()) {
		    	user =new User();
		    	user.setUserid(resultSet.getInt(1));
		    	user.setUpass(resultSet.getString(2));
		    	user.setOrganisation(resultSet.getString(3));
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
		    }	
		}
		return user;	
	}
	
	public boolean updatePassword(String encryptedCurrentPassword,String newPwd,String userName,String userOrganisation){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_USER_PWD);
			java.sql.Timestamp updatedOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementUpdate.setString(1,encryptedCurrentPassword);
			preparedStatementUpdate.setString(2,newPwd);
			preparedStatementUpdate.setTimestamp(3, updatedOnDate);
			preparedStatementUpdate.setString(4,userName);
			preparedStatementUpdate.execute();
			successFlag=SecurityUtil.updateUserPassword(newPwd,userName,userOrganisation);
			if(successFlag)
				connection.commit();
			else
				connection.rollback();
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
