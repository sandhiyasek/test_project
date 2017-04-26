package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.dao.IDimDataEntryDAO;
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
public class DimDataEntryDAOJdbc implements IDimDataEntryDAO {
private static Logger log=Logger.getLogger(DimDataEntryDAOJdbc.class);
/*private static final Logger logger = Logger.getLogger(DimDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String system,String userName){
		List<CommonStringList> institutionTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			institutionTypeList=new ArrayList<CommonStringList>();
			boolean accessFlag = isInstitutionFUllAccess(districtId,userName);
			if(accessFlag){
				CommonStringList defualtList = new CommonStringList();
				defualtList.setId(ShdrcConstants.SELECTALLID);
				defualtList.setName(ShdrcConstants.SELECTALLVALUE);
				institutionTypeList.add(defualtList);
			}	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_INSTITUTION_TYPE);
			preparedStatement.setString(1,system);
			preparedStatement.setInt(2,districtId);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			preparedStatement.setString(5,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	institutionTypeList.add(commonStringList);
	        }
	        log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getInstitutionTypeList "+e.getMessage());
		}
		catch(Exception json){
			log.error(this.getClass().getName() + "- getInstitutionTypeList "+json.getMessage());
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
			return institutionTypeList;
	}
	
	
	public List<CommonStringList> getSytem(){
		List<CommonStringList> systemList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			systemList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_SYSTEM);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	systemList.add(commonStringList);
	        } log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getSytem "+e.getMessage());
		}
		catch(Exception json){
			log.error(this.getClass().getName() + "- getSytem "+json.getMessage());
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
			return systemList;
	}
	
	
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String system,String userName){
		List<CommonStringList> institutionList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			institutionList=new ArrayList<CommonStringList>();
			boolean accessFlag = isInstitutionFUllAccess(districtId,userName);
			if(accessFlag){
				CommonStringList defualtList = new CommonStringList();
				defualtList.setId(ShdrcConstants.SELECTALLID);
				defualtList.setName(ShdrcConstants.SELECTALLVALUE);
				institutionList.add(defualtList);
			}	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_INSTITUTION);
			preparedStatement.setString(1,system);
			preparedStatement.setInt(2,districtId);
			preparedStatement.setString(3,institutionType);
			preparedStatement.setString(4,userName);
			preparedStatement.setString(5,userName);
			preparedStatement.setString(6,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	institutionList.add(commonStringList);
	        } log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getDataSource "+e.getMessage());
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
			return institutionList;
	}
	
	public boolean isInstitutionFUllAccess(Integer districtId,String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		PreparedStatement preparedStatement1 =null;
		ResultSet resultSet1 =null;
		int totalNoOfUserAccessInstitution;
		int totalNoOfInstitution;
		boolean flag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			totalNoOfUserAccessInstitution=0;
			totalNoOfInstitution=0;
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_INSTITUTION_COUNT);
			preparedStatement.setInt(1,districtId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	totalNoOfInstitution = resultSet.getInt(1);
	        }
			preparedStatement1 = connection.prepareStatement(ShdrcQueryList.ACCESS_RESTRICTED_DIM_INSTITUTION_COUNT);
			preparedStatement1.setInt(1,districtId);
			preparedStatement1.setString(2,userName);
			preparedStatement1.setString(3,userName);
			preparedStatement1.setString(4,userName);
			resultSet1 = preparedStatement1.executeQuery();
	        if (resultSet1.next()) {
	        	totalNoOfUserAccessInstitution = resultSet1.getInt(1);
	        }
			flag=(totalNoOfInstitution==totalNoOfUserAccessInstitution);
			 log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- isInstitutionFUllAccess "+e.getMessage());
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
	                if (resultSet1 != null) {
	                    resultSet1.close();
	                }
	                if (preparedStatement1 != null) {
	                    preparedStatement1.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }	
			}	        
			return flag;
	}
	
	public JSONArray getDimDirectorateRecords(Integer districtId,String institutionType,String system,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String searchDate,String month,Integer year){
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
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_DAILY_RECORDS);
					preparedStatement.setDate(7, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_WEEKLY_RECORDS);
					preparedStatement.setString(7, week);
					preparedStatement.setString(8, month);
					preparedStatement.setInt(9, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_MONTHLY_RECORDS);
					preparedStatement.setString(7, month);
					preparedStatement.setInt(8, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_QUARTERLY_RECORDS);
					preparedStatement.setString(7, quarter);
					preparedStatement.setInt(8, year);
				}else if(ShdrcConstants.YEARLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_YEARLY_RECORDS);
					preparedStatement.setInt(7, year);
				}
				preparedStatement.setString(1, system);
				preparedStatement.setInt(2, districtId);
				preparedStatement.setString(3, institutionType);
				preparedStatement.setInt(4, institutionId);
				preparedStatement.setString(5, classificationName);
				preparedStatement.setString(6, frequency);
								
				resultSet = preparedStatement.executeQuery();
				jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
			} log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getDimDirectorateRecords "+e.getMessage());
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
	
	public boolean insertDimDirectorateRecords(Integer districtId,String institutionType,String system,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){ 
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_DIM_DIRECTORATE);
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
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.DIM);
						preparedStatementInsert.setString(2,system);
						preparedStatementInsert.setInt(3,districtId);
						preparedStatementInsert.setString(4,institutionType);
						preparedStatementInsert.setInt(5,institutionId);
						preparedStatementInsert.setInt(6,ShdrcConstants.NAID);
						preparedStatementInsert.setInt(7,generalId);
						preparedStatementInsert.setString(8,generalType);
						preparedStatementInsert.setString(9,generalName);
						preparedStatementInsert.setString(10,subCategory);
						preparedStatementInsert.setInt(11,indicatorId);
						preparedStatementInsert.setString(12,indicatorName);
						preparedStatementInsert.setString(13,isCharacter);
						if(indicatorValue==null)
							preparedStatementInsert.setNull(14,java.sql.Types.DOUBLE);
						else
							preparedStatementInsert.setDouble(14,indicatorValue);
						preparedStatementInsert.setString(15,indicatorValue1);
						preparedStatementInsert.setString(16,frequency);
						preparedStatementInsert.setDate(17,sqlDate);
						preparedStatementInsert.setString(18,Util.isNullOrBlank(week)?null:week);
						preparedStatementInsert.setString(19,Util.isNullOrBlank(quarter)?null:quarter);
						month = Util.isNullOrBlank(month)?null:month;
						preparedStatementInsert.setString(20,month);
						if(year==null)
							preparedStatementInsert.setNull(21,java.sql.Types.INTEGER);
						else
							preparedStatementInsert.setInt(21,year);
						java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
						preparedStatementInsert.setTimestamp(22,addOnDate);
						preparedStatementInsert.setString(23, UserInfo.getLoggedInUserName());
						preparedStatementInsert.addBatch();
					}
				}	
			}	
			preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			 log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
			log.error(this.getClass().getName() + "- insertDimDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertDimDirectorateRecords "+e1.getMessage());
			}
		}catch (JSONException e) {
			log.error(this.getClass().getName() + "- insertDimDirectorateRecords "+e.getMessage());
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
	
	public boolean updateDimDirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_DIM_DIRECTORATE);
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
			log.error(this.getClass().getName() + "- updateDimDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDimDirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
				log.error(this.getClass().getName() + "- updateDimDirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDimDirectorateRecords "+e1.getMessage());
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
	
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel,String system){
		Connection connection = null;
		JSONArray jsonArray= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			if("Medical Personnel".equalsIgnoreCase(classificationName) 
					|| "OP".equalsIgnoreCase(classificationName)
					|| "Pharmacist / Chief Pharmacist / Pharmacy Supervisor".equalsIgnoreCase(classificationName)
					|| "Under NRHM".equalsIgnoreCase(classificationName)
					|| "IP".equalsIgnoreCase(classificationName)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_INDICATOR_LIST1);
				preparedStatement.setString(5, system);
			}
			else{
				preparedStatement = connection.prepareStatement(ShdrcQueryList.DIM_INDICATOR_LIST);
			}
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2,classificationName);
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

}
