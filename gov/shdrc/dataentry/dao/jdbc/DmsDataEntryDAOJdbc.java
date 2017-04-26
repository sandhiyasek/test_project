package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.dao.IDmsDataEntryDAO;
import gov.shdrc.home.dao.ICommonDAO;
import gov.shdrc.util.CommonStringList;
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
public class DmsDataEntryDAOJdbc implements IDmsDataEntryDAO {
private static Logger log=Logger.getLogger(DmsDataEntryDAOJdbc.class);
/*private static final Logger logger = Logger.getLogger(DmsDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

public List<CommonStringList> getHospitalList(Integer districtId,String userName){
	List<CommonStringList> hospitalList=null;
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	try {
		log.debug(this.getClass().getName() + "- Entering ");
		hospitalList=new ArrayList<CommonStringList>();
		boolean accessFlag = isHospitalNameFUllAccess(districtId,userName);
		if(accessFlag){
			CommonStringList defualtList = new CommonStringList();
			defualtList.setId(ShdrcConstants.SELECTALLID);
			defualtList.setName(ShdrcConstants.SELECTALLVALUE);
			hospitalList.add(defualtList);
		}	
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.DMS_INSTITUTION);
		preparedStatement.setInt(1,districtId);
		preparedStatement.setString(2,userName);
		preparedStatement.setString(3,userName);
		preparedStatement.setString(4,userName);
		resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
        	CommonStringList commonStringList = new CommonStringList();
        	commonStringList.setId(resultSet.getInt(1));
        	commonStringList.setName(resultSet.getString(2));
        	hospitalList.add(commonStringList);
        }log.debug(this.getClass().getName() + "- Exit ");
	} catch (SQLException e) {
		log.error(this.getClass().getName() + "- getHospitalList "+e.getMessage());
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
        
		return hospitalList;
}

public boolean isHospitalNameFUllAccess(Integer districtId,String userName){
	Connection connection = null;
	PreparedStatement preparedStatement1 =null;
	PreparedStatement preparedStatement2 =null;
	ResultSet resultSet1 =null;
	ResultSet resultSet2 =null;
	int totalNoOfUserAccessHospital;
	int totalNoOfHospital;
	boolean flag=false;
	try {
		log.debug(this.getClass().getName() + "- Entering ");
		totalNoOfUserAccessHospital=0;
		totalNoOfHospital=0;
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement1 = connection.prepareStatement(ShdrcQueryList.DMS_INSTITUTION_COUNT);
		preparedStatement1.setInt(1,districtId);
		resultSet1 = preparedStatement1.executeQuery();
        if (resultSet1.next()) {
        	totalNoOfHospital = resultSet1.getInt(1);
        }
		preparedStatement2 = connection.prepareStatement(ShdrcQueryList.ACCESS_RESTRICTED_DMS_INSTITUTION_COUNT);
		preparedStatement2.setInt(1,districtId);
		preparedStatement2.setString(2,userName);
		preparedStatement2.setString(3,userName);
		preparedStatement2.setString(4,userName);
		resultSet2 = preparedStatement2.executeQuery();
        if (resultSet2.next()) {
        	totalNoOfUserAccessHospital = resultSet2.getInt(1);
        }
		flag=(totalNoOfHospital==totalNoOfUserAccessHospital);
		log.debug(this.getClass().getName() + "- Exit ");
	} catch (SQLException e) {
		log.error(this.getClass().getName() + "- isHospitalNameFUllAccess "+e.getMessage());
	}
	catch(Exception json){
		
	}finally{
			try {
                if (resultSet1 != null) {
                    resultSet1.close();
                }
                if (resultSet2 != null) {
                    resultSet2.close();
                }
                if (preparedStatement1 != null) {
                    preparedStatement1.close();
                }
                if (preparedStatement2 != null) {
                    preparedStatement2.close();
                }
                if (connection != null) {
                	connection.close();
                }

            } catch (SQLException ex) {
            }

		}
        
		return flag;
}

public JSONArray getDmsDirectorateRecords(Integer districtId,Integer hospitalId,String classificationName,String frequency,String week,String quarter,
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
				preparedStatement = connection.prepareStatement(ShdrcQueryList.DMS_DAILY_RECORDS);
				preparedStatement.setDate(5, sqlDate);
			}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.DMS_WEEKLY_RECORDS);
				preparedStatement.setString(5, week);
				preparedStatement.setString(6, month);
				preparedStatement.setInt(7, year);
			}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.DMS_MONTHLY_RECORDS);
				preparedStatement.setString(5, month);
				preparedStatement.setInt(6, year);
			}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.DMS_QUARTERLY_RECORDS);
				preparedStatement.setString(5, quarter);
				preparedStatement.setInt(6, year);
			}
			preparedStatement.setInt(1, districtId);
			preparedStatement.setInt(2, hospitalId);
			preparedStatement.setString(3, classificationName);
			preparedStatement.setString(4, frequency);
			resultSet = preparedStatement.executeQuery();
			jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
		}log.debug(this.getClass().getName() + "- Exit ");
	} catch (SQLException e) {
		log.error(this.getClass().getName() + "- getDmsDirectorateRecords "+e.getMessage());
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

public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic){
	Connection connection = null;
	JSONArray jsonArray= null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	try {
		log.debug(this.getClass().getName() + "- Entering ");
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.DMS_INDICATOR_LIST);
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

	public boolean insertDmsDirectorateRecords(Integer districtId,Integer hospitalId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){ 
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_DMS_DIRECTORATE);
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
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.DMS);
						preparedStatementInsert.setInt(2,districtId);
						preparedStatementInsert.setInt(3,hospitalId);
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
	    	log.error(this.getClass().getName() + "- insertDmsDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertDmsDirectorateRecords "+e1.getMessage());
			}
		}catch(Exception json){
			log.error(this.getClass().getName() + "- insertDmsDirectorateRecords "+json.getMessage());
		}
		finally{
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
	
	public boolean updateDmsDirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_DMS_DIRECTORATE);
			
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
			log.error(this.getClass().getName() + "- updateDmsDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDmsDirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
				log.error(this.getClass().getName() + "- updateDmsDirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDmsDirectorateRecords "+e1.getMessage());
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
