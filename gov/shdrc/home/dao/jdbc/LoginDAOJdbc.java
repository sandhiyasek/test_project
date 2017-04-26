/**
 * 
 */
package gov.shdrc.home.dao.jdbc;

import gov.shdrc.home.dao.IHomeDAO;
import gov.shdrc.home.dao.ILoginDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.News;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.User;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginDAOJdbc implements ILoginDAO {	


	@Autowired
	JdbcTemplate jdbcTemplate;	
	public void insertLoginAudit(String clientIpAddress,String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.INSERT_USER_AUDIT_LOG);
			preparedStatement.setString(1,userName);
			preparedStatement.setTimestamp(2,new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setString(3,clientIpAddress);
			boolean flag = preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
	                if (preparedStatement != null) {
	                    preparedStatement.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }
	
			}
	}
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

}
