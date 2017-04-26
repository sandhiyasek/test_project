package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import gov.shdrc.dataentry.dao.IPrivateSectorDataEntryDAO;
import gov.shdrc.home.dao.ICommonDAO;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrivateSectorDataEntryDAOJdbc implements IPrivateSectorDataEntryDAO {
private static Logger log=Logger.getLogger(PrivateSectorDataEntryDAOJdbc.class);
/*private static final Logger logger = Logger.getLogger(PrivateSectorDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

	public boolean updatePvtSecDirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_PVTSEC_DIRECTORATE);
		
			String isCharacter=null;
			String indicatorValue1=null;
			Double indicatorValue=null;
			java.sql.Timestamp updatedOnDate = new java.sql.Timestamp(new Date().getTime());
			for(int i=1;i<jsonArray.length()-1;i++){
				if(!jsonArray.isNull(i)){
					JSONObject gridRow =jsonArray.getJSONObject(i);
					String strIndicatorValue = gridRow.isNull("indicatorvalue")?null:gridRow.getString("indicatorvalue");
					isCharacter=gridRow.has("ischaracter")?gridRow.getString("ischaracter"):null;
					if(isCharacter==null || "N".equalsIgnoreCase(isCharacter)){
						 indicatorValue=Util.isNotNull(strIndicatorValue)?Double.parseDouble(strIndicatorValue):null;
						 indicatorValue1=null;
					 }else{
						 indicatorValue1=strIndicatorValue;
						 indicatorValue=null;
					 }
					
					if(indicatorValue==null)
						preparedStatementUpdate.setNull(1,java.sql.Types.DOUBLE);
					else
						preparedStatementUpdate.setDouble(1, indicatorValue);
					preparedStatementUpdate.setString(2, indicatorValue1);
					preparedStatementUpdate.setTimestamp(3, updatedOnDate);
					preparedStatementUpdate.setLong(4, gridRow.getLong("transactionkey"));
					preparedStatementUpdate.addBatch();
				}
			}
			preparedStatementUpdate.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- updatePvtSecDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
		    	log.error(this.getClass().getName() + "- updatePvtSecDirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
		    	log.error(this.getClass().getName() + "- updatePvtSecDirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
		    	log.error(this.getClass().getName() + "- updatePvtSecDirectorateRecords "+e1.getMessage());
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
	
	public JSONArray getPvtSecDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		JSONArray jsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			
			connection=jdbcTemplate.getDataSource().getConnection();
			if(Util.isNotNull(districtId)){
				if(ShdrcConstants.DAILY.equalsIgnoreCase(frequency)){
					java.sql.Date  sqlDate = new java.sql.Date(Util.getUtilDate(searchDate).getTime());
					preparedStatement = connection.prepareStatement(ShdrcQueryList.PVTSEC_DAILY_RECORDS);
					preparedStatement.setDate(5, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.PVTSEC_WEEKLY_RECORDS);
					preparedStatement.setString(5, week);
					preparedStatement.setString(6, month);
					preparedStatement.setInt(7, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.PVTSEC_MONTHLY_RECORDS);
					preparedStatement.setString(5, month);
					preparedStatement.setInt(6, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.PVTSEC_QUARTERLY_RECORDS);
					preparedStatement.setString(5, quarter);
					preparedStatement.setInt(6, year);
				}else if(ShdrcConstants.YEARLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.PVTSEC_YEARLY_RECORDS);
					preparedStatement.setInt(5, year);
				}
				preparedStatement.setInt(1, districtId);
				preparedStatement.setInt(2, ShdrcConstants.NAID);
				preparedStatement.setString(3, classificationName);
				preparedStatement.setString(4, frequency);
								
				resultSet = preparedStatement.executeQuery();
				jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
				}log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e) {
		    	log.error(this.getClass().getName() + "- getPvtSecDirectorateRecords "+e.getMessage());
				
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
	        
			return jsonArray;
		}
	
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		Connection connection = null;
		JSONArray jsonArray= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.PVTSEC_INDICATOR_LIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, classificationName);
			preparedStatement.setString(3,Util.getFrequencyShortName(frequency));
			preparedStatement.setString(4,isDemographic);
			resultSet = preparedStatement.executeQuery();
			jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
			log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- getIndicatorList "+e.getMessage());
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
	        
			return jsonArray;
	}
	
	public boolean insertPvtSecDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_PVTSEC_DIRECTORATE);
			java.sql.Date  sqlDate=null;
			if(Util.isNotNull(date))
				sqlDate=new java.sql.Date(Util.getUtilDate(date).getTime());
		
			Integer indicatorId=null;
			String indicatorName=null;
			Double indicatorValue=null;
			String indicatorValue1=null;
			Integer generalId = null;
			String generalType=null;
			String generalName=null;
			String subCategory=null;
			String isCharacter=null;
	
			for(int i=1;i<gridRecords.length()-1;i++){
				if(!gridRecords.isNull(i)){
					JSONObject gridRow = gridRecords.getJSONObject(i);
					if(!gridRow.getBoolean("issucategorylabel")){
						 indicatorId=gridRow.getInt("indicatorid");
						
						 isCharacter=gridRow.has("ischaracter")?gridRow.getString("ischaracter"):null;
						 indicatorName=gridRow.has("indicatorname1")?gridRow.getString("indicatorname1"):null;
						 String strIndicatorValue = gridRow.isNull("indicatorvalue")?null:gridRow.getString("indicatorvalue");
						 
						 if(isCharacter==null || "N".equalsIgnoreCase(isCharacter)){
							 indicatorValue=Util.isNotNull(strIndicatorValue)?Double.parseDouble(strIndicatorValue):null;
							 indicatorValue1=null;
						 }else{
							 indicatorValue1=strIndicatorValue;
							 indicatorValue=null;
						 }
						 generalId = gridRow.getInt("generalid");
						 generalType=gridRow.has("generaltype1")?gridRow.getString("generaltype1"):null;
						 generalName=gridRow.has("generalname1")?gridRow.getString("generalname1"):null;
						 subCategory=gridRow.has("subcategory")?gridRow.getString("subcategory"):ShdrcConstants.NAVALUE;
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.PVTSEC);
						preparedStatementInsert.setInt(2,districtId);
						preparedStatementInsert.setInt(3, ShdrcConstants.NAID);
						preparedStatementInsert.setInt(4,ShdrcConstants.NAID);
						preparedStatementInsert.setInt(5,generalId);
						preparedStatementInsert.setString(6,generalType);
						preparedStatementInsert.setString(7,generalName);
						preparedStatementInsert.setString(8,subCategory);
						preparedStatementInsert.setInt(9,indicatorId);
						preparedStatementInsert.setString(10,indicatorName);
						preparedStatementInsert.setString(11,isCharacter);
						if(indicatorValue==null)
							preparedStatementInsert.setNull(12,java.sql.Types.DOUBLE);
						else
							preparedStatementInsert.setDouble(12,indicatorValue);
						preparedStatementInsert.setString(13,indicatorValue1);
						preparedStatementInsert.setString(14,frequency);
						preparedStatementInsert.setDate(15,sqlDate);
						preparedStatementInsert.setString(16,Util.isNullOrBlank(week)?null:week);
						preparedStatementInsert.setString(17,Util.isNullOrBlank(quarter)?null:quarter);
						month = Util.isNullOrBlank(month)?null:month;
						preparedStatementInsert.setString(18,month);
						if(year==null)
							preparedStatementInsert.setNull(19,java.sql.Types.INTEGER);
						else
							preparedStatementInsert.setInt(19,year);
						java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
						preparedStatementInsert.setTimestamp(20,addOnDate);
						preparedStatementInsert.setString(21, UserInfo.getLoggedInUserName());
						preparedStatementInsert.addBatch();
					}
				}	
			}	
			preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- insertPvtSecDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
		    	log.error(this.getClass().getName() + "- insertPvtSecDirectorateRecords "+e1.getMessage());
			}
		}catch (JSONException e) {
	    	log.error(this.getClass().getName() + "- insertPvtSecDirectorateRecords "+e.getMessage());
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
}
