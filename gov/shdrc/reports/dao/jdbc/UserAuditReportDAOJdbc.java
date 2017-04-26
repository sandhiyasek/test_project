package gov.shdrc.reports.dao.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gov.shdrc.reports.dao.IUserAuditReportDAO;
import gov.shdrc.util.ShdrcReportQueryList;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserAuditReportDAOJdbc implements IUserAuditReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	public JSONArray getUserAuditReport(String directorateId,Integer year,String month,String freqName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
        try {
        	childList=new JSONArray();
			childList.put("User Name");
			childList.put("Directorate Name");
			childList.put("Tier");
			childList.put("Login Time");
			childList.put("IP Address");
			parentList.put(childList);
			
			connection=jdbcTemplate.getDataSource().getConnection();
			if("monthly".equalsIgnoreCase(freqName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.USER_LOGIN_AUDIT_REPORT_MONTHLY);
				Array directorateIdArray=preparedStatement.getConnection().createArrayOf("Integer", directorateId.split(","));
				preparedStatement.setArray(1, directorateIdArray);
				preparedStatement.setInt(2, year);
				preparedStatement.setString(3, month);
			}
			else{
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.USER_LOGIN_AUDIT_REPORT_YEARLY);
				Array directorateIdArray=preparedStatement.getConnection().createArrayOf("Integer", directorateId.split(","));
				preparedStatement.setArray(1, directorateIdArray);
				preparedStatement.setInt(2, year);
			}
			
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String userName=resultSet.getString(2);
				String directorateName=resultSet.getString(3);
				String tier=resultSet.getString(4);
				java.sql.Timestamp loginTime=resultSet.getTimestamp(5);
				String ipaddress=resultSet.getString(6);
				
				childList.put(userName);
				childList.put(directorateName);
				childList.put(tier);
				childList.put(loginTime);
				childList.put(ipaddress);
				parentList.put(childList);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}finally{
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
		return parentList;
	}
}
