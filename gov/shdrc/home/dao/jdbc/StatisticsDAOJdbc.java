/**
 * 
 */
package gov.shdrc.home.dao.jdbc;

import gov.shdrc.home.dao.IStatisticsDAO;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatisticsDAOJdbc implements IStatisticsDAO {	

/*private static final Logger logger = Logger.getLogger(StatisticsDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
	
	public List<String> getindicatorList(Integer directorateId){
		List<String> indicatorList= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;	
		try {
			indicatorList = new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.INDICATORS_LIST);
			preparedStatement.setInt(1,directorateId);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				String indicator=resultSet.getString(1);
				indicatorList.add(indicator);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception json){
			
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
	
	        }catch (SQLException ex) {
	         }	
		}
	    return indicatorList;
	}	
	
	public JSONArray getIndStatisticsData(Integer directorateId,String indName){
		JSONArray indStatisticsData= null;
		JSONObject jsonObject=new JSONObject();
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;	
		PreparedStatement preparedStatement1 =null;
		ResultSet resultSet1 =null;	
		try {
			indStatisticsData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.INDICATOR_STATISTICS_LIST);
			preparedStatement.setInt(1,directorateId);
			preparedStatement.setString(2,indName);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				String indicatorName=resultSet.getString(1);
				Integer minValue=resultSet.getInt(2);
				Integer maxValue=resultSet.getInt(3);
				Double mean=resultSet.getDouble(4);
				Double standardDeviation=resultSet.getDouble(5);
				Double variance=resultSet.getDouble(6);
				jsonObject.put("indicatorName",indicatorName);
				jsonObject.put("minValue",minValue);
				jsonObject.put("maxValue",maxValue);
				jsonObject.put("mean",Util.formatTwoDecimal(mean));
				jsonObject.put("standardDeviation",Util.formatTwoDecimal(standardDeviation));
				jsonObject.put("variance",Util.formatTwoDecimal(variance));
				indStatisticsData.put(jsonObject);
			}
			preparedStatement1 = connection.prepareStatement(ShdrcQueryList.INDICATOR_MAX_DATA_AVAILABLE);
			preparedStatement1.setInt(1,directorateId);
			preparedStatement1.setString(2,indName);
			resultSet1=preparedStatement1.executeQuery();
			while(resultSet1.next())
			{
				Integer year=resultSet1.getInt(1);
				String month=resultSet1.getString(2);
				String date=resultSet1.getString(3);
				jsonObject.put("year",year);
				jsonObject.put("month",month);
				jsonObject.put("date",date);
				indStatisticsData.put(jsonObject);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception json){
			
		}finally{
			try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (resultSet1 != null) {
	                resultSet1.close();
	            }
	            if (preparedStatement1 != null) {
	                preparedStatement1.close();
	            }
	            if (connection != null) {
	            	connection.close();
	            }
	
	        }catch (SQLException ex) {
	         }	
		}
	    return indStatisticsData;
	}	
	
	public JSONArray getIndDataAvailability(Integer directorateId,String indName){
		JSONArray indDataAvailabity= null;
		
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;	
		try {
			indDataAvailabity = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.INDICATOR_DATA_AVAILABILITY);
				preparedStatement.setInt(1,directorateId);
				preparedStatement.setString(2,indName);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())
			{
				JSONObject jsonObject=new JSONObject();
				String districtName=resultSet.getString(1);
				String janValue=resultSet.getString(2);
				String febValue=resultSet.getString(3);
				String marValue=resultSet.getString(4);
				String aprValue=resultSet.getString(5);
				String mayValue=resultSet.getString(6);
				String junValue=resultSet.getString(7);
				String julValue=resultSet.getString(8);
				String augValue=resultSet.getString(9);
				String sepValue=resultSet.getString(10);
				String octValue=resultSet.getString(11);
				String novValue=resultSet.getString(12);
				String decValue=resultSet.getString(13);
				
				jsonObject.put("districtName",districtName);
				jsonObject.put("janValue",janValue);
				jsonObject.put("febValue",febValue);
				jsonObject.put("marValue",marValue);
				jsonObject.put("aprValue",aprValue);
				jsonObject.put("mayValue",mayValue);
				jsonObject.put("junValue",junValue);
				jsonObject.put("julValue",julValue);
				jsonObject.put("augValue",augValue);
				jsonObject.put("sepValue",sepValue);
				jsonObject.put("octValue",octValue);
				jsonObject.put("novValue",novValue);
				jsonObject.put("decValue",decValue);
	
				indDataAvailabity.put(jsonObject);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(Exception json){
			
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
	
	        }catch (SQLException ex) {
	         }
	
		}
	    return indDataAvailabity;
	}
}
