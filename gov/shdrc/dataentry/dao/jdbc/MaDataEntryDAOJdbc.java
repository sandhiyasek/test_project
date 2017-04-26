package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.dao.IMaDataEntryDAO;
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
public class MaDataEntryDAOJdbc implements IMaDataEntryDAO {
private static Logger log=Logger.getLogger(MaDataEntryDAOJdbc.class);
/*private static final Logger logger = Logger.getLogger(MaDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

	public JSONArray getMaDirectorateRecords(String regionName,Integer corporationId, Integer regionOrCorporationId,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year){
		JSONArray jsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			if(ShdrcConstants.DAILY.equalsIgnoreCase(frequency)){
				java.sql.Date  sqlDate = new java.sql.Date(Util.getUtilDate(searchDate).getTime());
				preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_DAILY_RECORDS);
				preparedStatement.setDate(7, sqlDate);
			}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_WEEKLY_RECORDS);
				preparedStatement.setString(7, week);
				preparedStatement.setString(8, month);
				preparedStatement.setInt(9, year);
			}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_MONTHLY_RECORDS);
				preparedStatement.setString(7, month);
				preparedStatement.setInt(8, year);
			}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_QUARTERLY_RECORDS);
				preparedStatement.setString(7, quarter);
				preparedStatement.setInt(8, year);
			}
			else if(ShdrcConstants.YEARLY.equalsIgnoreCase(frequency)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_YEARLY_RECORDS);
				preparedStatement.setInt(7, year);
			}
			preparedStatement.setString(1, regionName);
			preparedStatement.setInt(2, regionOrCorporationId);
			preparedStatement.setInt(3, corporationId);
			preparedStatement.setInt(4, institutionId);
			preparedStatement.setString(5, classificationName);
			preparedStatement.setString(6, frequency);
			
			resultSet = preparedStatement.executeQuery();
			jsonArray = commonDAO.getGridRecordsFromRS(resultSet);			
			log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getMaDirectorateRecords "+e.getMessage());
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
	
	public boolean isMADirectorateRecordsExists(String regionName,Integer districtId,Integer corporationId,Integer municipalityId,Integer institutionId,
			String classificationName,Integer demographicId,Integer generalId,String frequency,Integer week,Integer quarter,
			String searchDate,String month,Integer year){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		boolean isRecordExists=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			if(Util.isNotNull(districtId)){
				if(ShdrcConstants.DAILY.equalsIgnoreCase(frequency)){
					java.sql.Date  sqlDate = new java.sql.Date(Util.getUtilDate(searchDate).getTime());
					preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_DAILY_RECORDS_COUNT);
					preparedStatement.setDate(9, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_WEEKLY_RECORDS_COUNT);
					preparedStatement.setInt(9, week);
					preparedStatement.setString(10, month);
					preparedStatement.setInt(11, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_MONTHLY_RECORDS_COUNT);
					preparedStatement.setString(9, month);
					preparedStatement.setInt(10, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_QUARTERLY_RECORDS_COUNT);
					preparedStatement.setInt(9, quarter);
					preparedStatement.setInt(10, year);
				}
				preparedStatement.setString(1, regionName);
				preparedStatement.setInt(2, districtId);
				preparedStatement.setInt(3, corporationId);
				preparedStatement.setInt(4, institutionId);
				preparedStatement.setString(5, classificationName);
				preparedStatement.setInt(6, ShdrcConstants.NAID);
				preparedStatement.setString(7, frequency);
				preparedStatement.setInt(8, generalId);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					int recordCount = resultSet.getInt(1);
					if(recordCount>0)
						isRecordExists=true;
				}
			}	log.debug(this.getClass().getName() + "- Exit ");        
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- isMADirectorateRecordsExists "+e.getMessage());
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
			return isRecordExists;
		}
	
	public boolean insertMaDirectorateRecords(String regionName,Integer regionOrCorporationId,Integer corporationId, Integer districtId,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year,JSONArray gridRecords,String dataEntryLevel){
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_MA_DIRECTORATE);
			java.sql.Date  sqlDate=null;
			if(Util.isNotNull(searchDate))
				sqlDate=new java.sql.Date(Util.getUtilDate(searchDate).getTime());
		
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
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.MA);
						preparedStatementInsert.setString(2,regionName);
						preparedStatementInsert.setString(3,dataEntryLevel);
						preparedStatementInsert.setInt(4,corporationId);
						preparedStatementInsert.setInt(5,institutionId);
						preparedStatementInsert.setInt(6,generalId);
						preparedStatementInsert.setString(7,generalType);
						preparedStatementInsert.setString(8,generalName);
						preparedStatementInsert.setString(9,subCategory);
						preparedStatementInsert.setInt(10,indicatorId);
						preparedStatementInsert.setString(11,indicatorName);
						preparedStatementInsert.setString(12,isCharacter);
						if(indicatorValue==null)
							preparedStatementInsert.setNull(13,java.sql.Types.DOUBLE);
						else
							preparedStatementInsert.setDouble(13,indicatorValue);
						preparedStatementInsert.setString(14,indicatorValue1);
						preparedStatementInsert.setString(15,frequency);
						preparedStatementInsert.setDate(16,sqlDate);
						preparedStatementInsert.setString(17,Util.isNullOrBlank(week)?null:week);
						preparedStatementInsert.setString(18,Util.isNullOrBlank(quarter)?null:quarter);
						month = Util.isNullOrBlank(month)?null:month;
						preparedStatementInsert.setString(19,month);
						if(year==null)
							preparedStatementInsert.setNull(20,java.sql.Types.INTEGER);
						else
							preparedStatementInsert.setInt(20,year);
						java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
						preparedStatementInsert.setTimestamp(21,addOnDate);
						preparedStatementInsert.setString(22, UserInfo.getLoggedInUserName());
						preparedStatementInsert.setInt(23,regionOrCorporationId);
						preparedStatementInsert.addBatch();
					}
				}	
			}	
			preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
			log.error(this.getClass().getName() + "- insertMaDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertMaDirectorateRecords "+e1.getMessage());
			}
		}catch (JSONException e) {
			log.error(this.getClass().getName() + "- insertMaDirectorateRecords "+e.getMessage());
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
	
	
	public boolean updateMADirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_MA_DIRECTORATE);
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
			log.error(this.getClass().getName() + "- updateMADirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateMADirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
				log.error(this.getClass().getName() + "- updateMADirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateMADirectorateRecords "+e1.getMessage());
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
	
	public List<CommonStringList> getCorporationAndMunicipalityList(Integer districtId,String userName){
		List<CommonStringList> corporationAndMunicipalityList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			corporationAndMunicipalityList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_CORPORATION_MUNICIPALITY);
			preparedStatement.setInt(1,districtId);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setId(resultSet.getInt(1));
	        	CommonStringList.setName(resultSet.getString(2));
	        	corporationAndMunicipalityList.add(CommonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getCorporationAndMunicipalityList "+e.getMessage());
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
			return corporationAndMunicipalityList;
	}

	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		Connection connection = null;
		JSONArray jsonArray= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_INDICATOR_LIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, classificationName);
			preparedStatement.setString(3,Util.getFrequencyShortName(frequency));
			preparedStatement.setString(4,isDemographic);
			preparedStatement.setString(5,dataEntryLevel);
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
		
	public List<CommonStringList> getClassificationList(Integer directorateId){
		List<CommonStringList> classificationList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			classificationList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_MA_CLASSIFICATION);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setName(resultSet.getString(1));
	        	classificationList.add(CommonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getClassificationList "+e.getMessage());
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
			return classificationList;
	}
	
	public List<CommonStringList> getRegions(String userName){
		List<CommonStringList> regionsList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			regionsList=new ArrayList<CommonStringList>();
		
			boolean accessFlag = commonDAO.isDistrictsFUllAccess(userName);
			if(accessFlag){
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
				regionsList.add(commonStringList);
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_REGION_NAME);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setId(resultSet.getInt(1));
	        	CommonStringList.setName(resultSet.getString(2));
	        	regionsList.add(CommonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getRegions "+e.getMessage());
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
			return regionsList;
	}
	
	public boolean isRegionsFUllAccess(String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		PreparedStatement preparedStatement1 =null;
		ResultSet resultSet1 =null;
		int totalNoOfUserAccessRegions;
		int totalNoOfregions;
		boolean flag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			totalNoOfUserAccessRegions=0;
			totalNoOfregions=0;
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_REGION_NAME_COUNT);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	totalNoOfregions = resultSet.getInt(1);
	        }
			preparedStatement1 = connection.prepareStatement(ShdrcQueryList.ACCESS_RESTRICTED_MA_REGION_NAME_COUNT);
			preparedStatement1.setString(1,userName);
			preparedStatement1.setString(2,userName);
			preparedStatement1.setString(3,userName);
			resultSet1 = preparedStatement1.executeQuery();
	        if (resultSet1.next()) {
	        	totalNoOfUserAccessRegions = resultSet1.getInt(1);
	        }
			flag=(totalNoOfregions==totalNoOfUserAccessRegions);
			log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- isRegionsFUllAccess "+e.getMessage());
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
	
	public List<CommonStringList> getDistricts(String userName,String region){
		List<CommonStringList> districtsList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			districtsList=new ArrayList<CommonStringList>();
			
			boolean accessFlag = commonDAO.isDistrictsFUllAccess(userName);
			if(accessFlag){
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
				districtsList.add(commonStringList);
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_DISTRICT_NAME);
			preparedStatement.setString(1,region);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setId(resultSet.getInt(1));
	        	CommonStringList.setName(resultSet.getString(2));
	        	districtsList.add(CommonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getDistricts "+e.getMessage());
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
	
	public List<CommonStringList> getInstitutionList(Integer paramId,String userName,String isCorporation){
		List<CommonStringList> institutionList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			institutionList=new ArrayList<CommonStringList>();			
			CommonStringList defualtList = new CommonStringList();
			defualtList.setId(ShdrcConstants.SELECTALLID);
			defualtList.setName(ShdrcConstants.SELECTALLVALUE);
			institutionList.add(defualtList);
			
			connection=jdbcTemplate.getDataSource().getConnection();
			if("Y".equalsIgnoreCase(isCorporation)){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_CORPORATION_INSTITUTION);
			}else{
				preparedStatement = connection.prepareStatement(ShdrcQueryList.MA_REGION_INSTITUTION);				
			}
			preparedStatement.setInt(1,paramId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	institutionList.add(commonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getInstitutionList "+e.getMessage());
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

}
