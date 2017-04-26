package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.controller.CmchisDataEntryController;
import gov.shdrc.dataentry.dao.IDfsDataEntryDAO;
import gov.shdrc.home.dao.ICommonDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.SplCharsConstants;
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
public class DfsDataEntryDAOJdbc implements IDfsDataEntryDAO {
	private static Logger log=Logger.getLogger(DfsDataEntryDAOJdbc.class);
/*private static final Logger logger = Logger.getLogger(DfsDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

	public JSONArray getDfsDirectorateRecords(Integer districtId,Integer labId,Integer labDistrictId,String areaType,String area,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year,String dataEntryLevel){	
		JSONArray jsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			if(Util.isNotNull(districtId) || Util.isNotNull(labId)){
				if(ShdrcConstants.DAILY.equalsIgnoreCase(frequency)){
					java.sql.Date  sqlDate = new java.sql.Date(Util.getUtilDate(searchDate).getTime());
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_DAILY_RECORDS);
					preparedStatement.setDate(9, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_WEEKLY_RECORDS);
					preparedStatement.setString(9, week);
					preparedStatement.setString(10, month);
					preparedStatement.setInt(11, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_MONTHLY_RECORDS);
					preparedStatement.setString(9, month);
					preparedStatement.setInt(10, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_QUARTERLY_RECORDS);
					preparedStatement.setString(9, quarter);
					preparedStatement.setInt(10, year);
				}
				if(dataEntryLevel=="D"){
				preparedStatement.setInt(1, districtId);
				preparedStatement.setInt(2, ShdrcConstants.NAID);
				preparedStatement.setInt(3, ShdrcConstants.NAID);
				preparedStatement.setString(4, ShdrcConstants.NAVALUE);
				preparedStatement.setString(5, ShdrcConstants.NAVALUE);
				preparedStatement.setInt(6, ShdrcConstants.NAID);
			}
			else{
				preparedStatement.setInt(1,ShdrcConstants.NAID);
				preparedStatement.setInt(2,labId);
				preparedStatement.setInt(3,labDistrictId);
				preparedStatement.setString(4,areaType);
				preparedStatement.setString(5,area);
				preparedStatement.setInt(6, institutionId);
			}
				preparedStatement.setString(7, classificationName);
				preparedStatement.setString(8, frequency);
				
				resultSet = preparedStatement.executeQuery();
				jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
			}
			log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getDfsDirectorateRecords "+e.getMessage());
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
	
	public boolean insertDfsDirectorateRecords(Integer districtId,Integer labId,Integer labDistrictId,String areaType,String area,
			Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,String dataEntryLevel,JSONArray gridRecords){ 
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_DFS_DIRECTORATE);
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
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.DFS);
						if(dataEntryLevel=="D"){
							preparedStatementInsert.setInt(2,districtId);
							preparedStatementInsert.setInt(3, ShdrcConstants.NAID);
							preparedStatementInsert.setInt(4, ShdrcConstants.NAID);
							preparedStatementInsert.setString(5, ShdrcConstants.NAVALUE);
							preparedStatementInsert.setString(6, ShdrcConstants.NAVALUE);
							preparedStatementInsert.setInt(7, ShdrcConstants.NAID);
						}
						else{
							preparedStatementInsert.setInt(2,ShdrcConstants.NAID);
							preparedStatementInsert.setInt(3,labId);
							preparedStatementInsert.setInt(4,labDistrictId);
							preparedStatementInsert.setString(5,areaType);
							preparedStatementInsert.setString(6,area);
							preparedStatementInsert.setInt(7, institutionId);
						}
						preparedStatementInsert.setInt(8,ShdrcConstants.NAID);
						preparedStatementInsert.setString(9, dataEntryLevel);
						preparedStatementInsert.setInt(10,generalId);
						preparedStatementInsert.setString(11,generalType);
						preparedStatementInsert.setString(12,generalName);
						preparedStatementInsert.setString(13,subCategory);
						preparedStatementInsert.setInt(14,indicatorId);
						preparedStatementInsert.setString(15,indicatorName);
						preparedStatementInsert.setString(16,isCharacter);
						if(indicatorValue==null)
							preparedStatementInsert.setNull(17,java.sql.Types.DOUBLE);
						else
							preparedStatementInsert.setDouble(17,indicatorValue);
						preparedStatementInsert.setString(18,indicatorValue1);
						preparedStatementInsert.setString(19,frequency);
						preparedStatementInsert.setDate(20,sqlDate);
						preparedStatementInsert.setString(21,Util.isNullOrBlank(week)?null:week);
						preparedStatementInsert.setString(22,Util.isNullOrBlank(quarter)?null:quarter);
						month = Util.isNullOrBlank(month)?null:month;
						preparedStatementInsert.setString(23,month);
						if(year==null)
							preparedStatementInsert.setNull(24,java.sql.Types.INTEGER);
						else
							preparedStatementInsert.setInt(24,year);
						java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
						preparedStatementInsert.setTimestamp(25,addOnDate);
						preparedStatementInsert.setString(26, UserInfo.getLoggedInUserName());
						preparedStatementInsert.addBatch();
					}
				}	
			}	
			preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- insertDfsDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertDfsDirectorateRecords "+e1.getMessage());
			}
		}catch (JSONException e) {
			log.error(this.getClass().getName() + "- insertDfsDirectorateRecords "+e.getMessage());
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
	
	public boolean updateDfsDirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_DFS_DIRECTORATE);
		
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
	    	log.error(this.getClass().getName() + "- updateDfsDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDfsDirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
				log.error(this.getClass().getName() + "- updateDfsDirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDfsDirectorateRecords "+e1.getMessage());
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
	
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		Connection connection = null;
		JSONArray jsonArray= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_INDICATOR_LIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, classificationName);
			preparedStatement.setString(3,Util.getFrequencyShortName(frequency));
			preparedStatement.setString(4,isDemographic);
			preparedStatement.setString(5,SplCharsConstants.PERCENTAGE+dataEntryLevel+SplCharsConstants.PERCENTAGE);
			resultSet = preparedStatement.executeQuery();
			jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
			log.debug(this.getClass().getName() + "- Exit ");
		}
		catch (SQLException e) {
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
	
	public List<CommonStringList> getLabList(String userName){
		List<CommonStringList> labList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			labList=new ArrayList<CommonStringList>();
			/*boolean accessFlag = isLabFUllAccess(userName);
			if(accessFlag){
				CommonStringList defualtList = new CommonStringList();
				defualtList.setId(ShdrcConstants.SELECTALLID);
				defualtList.setName(ShdrcConstants.SELECTALLVALUE);
				labList.add(defualtList);
			}	*/
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_LAB);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,userName);
			/*preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);*/
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	labList.add(commonStringList);
	        }
        	log.debug(this.getClass().getName() + "- Exit ");

		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getLabList "+e.getMessage());
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
	        
			return labList;
	}
	
	public List<CommonStringList> getDistrictList(Integer labId,String userName){
		List<CommonStringList> districtsList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			districtsList=new ArrayList<CommonStringList>();
			/*boolean accessFlag = isFUllAccess(labId,userName);
			if(accessFlag){
				CommonStringList defualtList = new CommonStringList();
				defualtList.setId(ShdrcConstants.SELECTALLID);
				defualtList.setName(ShdrcConstants.SELECTALLVALUE);
				labList.add(defualtList);
			}	*/
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_LAB_DISTRICT);
			preparedStatement.setInt(1,labId);
			/*preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);*/
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	districtsList.add(commonStringList);
	        }
	        log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getDistrictList "+e.getMessage());
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
	        
			return districtsList;
	}
	
	public List<CommonStringList> getInstitutionTypeList(Integer labId,Integer districtId,String userName){
		List<CommonStringList> institutionTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			institutionTypeList=new ArrayList<CommonStringList>();	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_AREA_TYPE);
			preparedStatement.setInt(1,labId);
			preparedStatement.setInt(2,districtId);
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
	
	public List<CommonStringList> getInstitutionSpecialityList(Integer labId,Integer districtId,String areaType,String userName){
		List<CommonStringList> institutionSpecialityList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			institutionSpecialityList=new ArrayList<CommonStringList>();	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_AREA);
			preparedStatement.setInt(1,labId);
			preparedStatement.setInt(2,districtId);
			preparedStatement.setString(3,areaType);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	institutionSpecialityList.add(commonStringList);
	        }
        	log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getInstitutionSpecialityList "+e.getMessage());
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
	        
			return institutionSpecialityList;
	}
	
	public List<CommonStringList> getAreaCodeList(Integer labId,Integer districtId,String areaType,String area,String userName){
		List<CommonStringList> areaCodeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			areaCodeList=new ArrayList<CommonStringList>();	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFS_AREACODE);
			preparedStatement.setInt(1,labId);
			preparedStatement.setInt(2,districtId);
			preparedStatement.setString(3,areaType);
			preparedStatement.setString(4,area);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	areaCodeList.add(commonStringList);
	        }
        	log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getAreaCodeList "+e.getMessage());
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
	        
			return areaCodeList;
	}

}
