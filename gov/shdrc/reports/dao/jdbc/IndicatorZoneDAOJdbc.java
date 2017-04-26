package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IIndicatorZoneDAO;
import gov.shdrc.util.ShdrcQueryList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IndicatorZoneDAOJdbc implements IIndicatorZoneDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JSONArray indzone(String ind1, int year,String month){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
        try {
			childList=new JSONArray();
			childList.put("Category");
			childList.put("District");
			childList.put("Institution");
			childList.put("Year");
			childList.put("Month");
			childList.put("Value");
			parentList.put(childList);
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.IND_DIST);
			preparedStatement.setString(1, ind1);
			preparedStatement.setLong(2, year);
			preparedStatement.setString(3, month);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String category=resultSet.getString(2);
				String district=resultSet.getString(3);
				String institution=resultSet.getString(4);
				int yr=resultSet.getInt(5);
				String mon=resultSet.getString(6);	
				
				BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);
				childList.put(category);
				childList.put(district);
				childList.put(institution);
				childList.put(yr);
				childList.put(mon);
				childList.put(val);
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
	private static BigDecimal truncateDecimal(double x,int numberofDecimals)
	{
	    if ( x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
	    } else {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
	    }
	}
}
