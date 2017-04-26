package gov.shdrc.servicemaintenance.dao.jdbc;

import gov.shdrc.servicemaintenance.dao.IIndicatorMasterDashboardDAO;
import gov.shdrc.servicemaintenance.util.ShdrcServiceMaintenanceQueryList;
import gov.shdrc.util.SHDRCException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IndicatorMasterDashboardDAOJdbc implements IIndicatorMasterDashboardDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JSONArray getIndHierarchyList(Integer directorateId){
		JSONArray jsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_THRESHOLD_HIERARCHY_LIST);
			preparedStatement.setInt(1, directorateId);
			
			resultSet = preparedStatement.executeQuery();
			jsonArray = getGridRecordsFromRS(resultSet);
			
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
	    return jsonArray;
	}
	
	public static JSONArray getGridRecordsFromRS(ResultSet resultSet){
		JSONArray jsonArray=null;
		try {
			JSONObject jsonObject =null;
			jsonArray= new JSONArray();			
			String hierarchy="";			
			
			/*jsonArray = Util.getColumnHearders();*/
			while (resultSet.next()) {
	        	jsonObject = new JSONObject();
	        	
	        	hierarchy=resultSet.getString(1);
	        	jsonObject.put("hierarchy",hierarchy);
	        	jsonArray.put(jsonObject);
	        }
			if(jsonArray.length()==0){
				jsonArray=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}
		return jsonArray;
	}
	
	public boolean insertThresholdRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords,String userName)throws SHDRCException{ 
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.INSERT_DASHBOARD_THRESHOLD);
			String hierarchy=null;
			String operator=null;
			String thresholdType=null;
			String thresholdValue=null;
			
			for(int i=0;i<gridRecords.length();i++){
				if(!gridRecords.isNull(i)){
					JSONObject gridRow = gridRecords.getJSONObject(i);
						hierarchy=gridRow.has("hierarchy")?gridRow.getString("hierarchy"):null;
						operator=gridRow.has("operator")?gridRow.getString("operator"):null;
						thresholdType=gridRow.has("thresholdType")?gridRow.getString("thresholdType"):null;
						thresholdValue=gridRow.has("thresholdValue")?gridRow.getString("thresholdValue"):null;
						 preparedStatementInsert.setInt(1,directorateId);
						 preparedStatementInsert.setInt(2,indicatorId);
						 preparedStatementInsert.setString(3,hierarchy);
						 preparedStatementInsert.setString(4,operator);
						 preparedStatementInsert.setString(5,thresholdType);
						 preparedStatementInsert.setString(6,thresholdValue);
						 java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
						 preparedStatementInsert.setTimestamp(7,addOnDate);
						 preparedStatementInsert.setString(8,userName);
						 preparedStatementInsert.addBatch();
				}	
			}	
			preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();

	    } catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}finally{
				try {
	                if (preparedStatementInsert != null) {
	                	preparedStatementInsert.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }
	
			}
		return successFlag;
	}
	
	public JSONArray getThresholdDetails(Integer directorateId,Integer indicatorId){
		JSONArray thresholdJsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcServiceMaintenanceQueryList.INDICATOR_THRESHOLD_DETAILS);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setInt(2, indicatorId);
			
			resultSet = preparedStatement.executeQuery();
			thresholdJsonArray = getThresholdGridRecordsFromRS(resultSet);
			
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
	    return thresholdJsonArray;
	}
	
	public static JSONArray getThresholdGridRecordsFromRS(ResultSet resultSet){
		JSONArray thresholdJsonArray=null;
		try {
			JSONObject jsonObject =null;
			thresholdJsonArray= new JSONArray();			
			String hierarchy="";			
			String operator="";		
			String thresholdType="";		
			String thresholdValue="";		
			
			while (resultSet.next()) {
	        	jsonObject = new JSONObject();
	        	
	        	hierarchy=resultSet.getString(1);
	        	operator=resultSet.getString(2);
	        	thresholdType=resultSet.getString(3);
	        	thresholdValue=resultSet.getString(4);
	        	jsonObject.put("hierarchy",hierarchy);
	        	jsonObject.put("operator",operator);
	        	jsonObject.put("thresholdType",thresholdType);
	        	jsonObject.put("thresholdValue",thresholdValue);
	        	thresholdJsonArray.put(jsonObject);
	        }
			if(thresholdJsonArray.length()==0){
				thresholdJsonArray=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}
		return thresholdJsonArray;
	}
	
	public boolean updateThresholdRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords)throws SHDRCException{ 
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate = connection.prepareStatement
				      (ShdrcServiceMaintenanceQueryList.UPDATE_DASHBOARD_THRESHOLD);
			String hierarchy=null;
			String operator=null;
			String thresholdType=null;
			String thresholdValue=null;
			
			for(int i=0;i<gridRecords.length();i++){
				if(!gridRecords.isNull(i)){
					JSONObject gridRow = gridRecords.getJSONObject(i);
						hierarchy=gridRow.has("hierarchy")?gridRow.getString("hierarchy"):null;
						operator=gridRow.has("operator")?gridRow.getString("operator"):null;
						thresholdType=gridRow.has("thresholdType")?gridRow.getString("thresholdType"):null;
						thresholdValue=gridRow.has("thresholdValue")?gridRow.getString("thresholdValue"):null;
						 preparedStatementUpdate.setString(1,hierarchy);
						 preparedStatementUpdate.setString(2,operator);
						 preparedStatementUpdate.setString(3,thresholdType);
						 preparedStatementUpdate.setString(4,thresholdValue);
						 java.sql.Timestamp updateOnDate = new java.sql.Timestamp(new Date().getTime());
						 preparedStatementUpdate.setTimestamp(5,updateOnDate);
						 preparedStatementUpdate.setInt(6,directorateId);
						 preparedStatementUpdate.setInt(7,indicatorId);
						 preparedStatementUpdate.addBatch();
				}	
			}	
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
		}catch (JSONException e) {
			e.printStackTrace();
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
