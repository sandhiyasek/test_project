package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.dao.IShtdDataEntryDAO;
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
public class ShtdDataEntryDAOJdbc implements IShtdDataEntryDAO {
private static Logger log=Logger.getLogger(ShtdDataEntryDAOJdbc.class);
/*private static final Logger logger = Logger.getLogger(ShtdDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

	public JSONArray getShtoDirectorateRecords(Integer workshopId,Integer mobileId,String classificationName,String frequency,String week,
			String quarter,String searchDate,String month,Integer year){
		JSONArray jsonArray= null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {	
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			if(Util.isNotNull(workshopId)){
				if(ShdrcConstants.DAILY.equalsIgnoreCase(frequency)){
					java.sql.Date  sqlDate = new java.sql.Date(Util.getUtilDate(searchDate).getTime());
					preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_DAILY_RECORDS);
					preparedStatement.setDate(6, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_WEEKLY_RECORDS);
					preparedStatement.setString(6, week);
					preparedStatement.setString(7, month);
					preparedStatement.setInt(8, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_MONTHLY_RECORDS);
					preparedStatement.setString(6, month);
					preparedStatement.setInt(7, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_QUARTERLY_RECORDS);
					preparedStatement.setString(6, quarter);
					preparedStatement.setInt(7, year);
				}
				preparedStatement.setInt(1, workshopId);
				preparedStatement.setInt(2, -99);
				preparedStatement.setInt(3, mobileId);
				preparedStatement.setString(4, classificationName);
				preparedStatement.setString(5, frequency);			
				
				resultSet = preparedStatement.executeQuery();
				jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
			}log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getShtoDirectorateRecords "+e.getMessage());
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
	
	public boolean insertShtoDirectorateRecords(Integer workshopId,Integer mobileId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){ 
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_SHTO_DIRECTORATE);
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
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.SHTO);
						preparedStatementInsert.setInt(2,workshopId);
					    preparedStatementInsert.setInt(3,ShdrcConstants.NAID);
						preparedStatementInsert.setInt(4,mobileId);
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
			log.error(this.getClass().getName() + "- insertShtoDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertShtoDirectorateRecords "+e1.getMessage());
			}
		}catch (JSONException e) {
			log.error(this.getClass().getName() + "- insertShtoDirectorateRecords "+e.getMessage());
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
	
	public boolean updateShtoDirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_SHTO_DIRECTORATE);
		
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
			log.error(this.getClass().getName() + "- updateShtoDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateShtoDirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
				log.error(this.getClass().getName() + "- updateShtoDirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateShtoDirectorateRecords "+e1.getMessage());
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
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_INDICATOR_LIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, classificationName);
			preparedStatement.setString(3,Util.getFrequencyShortName(frequency));
			preparedStatement.setString(4,isDemographic);
			preparedStatement.setString(5,SplCharsConstants.PERCENTAGE+dataEntryLevel+SplCharsConstants.PERCENTAGE);
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
	
	public List<CommonStringList> getMobileList(Integer districtId,String userName){
		List<CommonStringList> mobileList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			mobileList=new ArrayList<CommonStringList>();
			/*boolean accessFlag = isMobileWorkShopFUllAccess(districtId,userName);
			if(accessFlag){*/
				CommonStringList defualtList = new CommonStringList();
				defualtList.setId(ShdrcConstants.SELECTALLID);
				defualtList.setName(ShdrcConstants.SELECTALLVALUE);
				mobileList.add(defualtList);
			//}	
				connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_MOBILE_WORKSHOP);
			preparedStatement.setInt(1,districtId);
		/*	preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName); */
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	mobileList.add(commonStringList);
	        }
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getMobileList "+e.getMessage());
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
			return mobileList;
	}
	
	public boolean isMobileWorkShopFUllAccess(Integer districtId,String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		PreparedStatement preparedStatement1 =null;
		ResultSet resultSet1 =null;
		int totalNoOfUserAccessMobileWorkShop;
		int totalNoOfMobileWorkShop;
		boolean flag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			totalNoOfUserAccessMobileWorkShop=0;
			totalNoOfMobileWorkShop=0;
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_MOBILE_WORKSHOP_COUNT);
			preparedStatement.setInt(1,districtId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	totalNoOfMobileWorkShop = resultSet.getInt(1);
	        }
			preparedStatement1 = connection.prepareStatement(ShdrcQueryList.ACCESS_RESTRICTED_SHTO_MOBILE_WORKSHOP_COUNT);
			preparedStatement1.setInt(1,districtId);
			preparedStatement1.setString(2,userName);
			preparedStatement1.setString(3,userName);
			preparedStatement1.setString(4,userName); 
			resultSet1 = preparedStatement1.executeQuery();
	        if (resultSet1.next()) {
	        	totalNoOfUserAccessMobileWorkShop = resultSet1.getInt(1);
	        }
			flag=(totalNoOfMobileWorkShop==totalNoOfUserAccessMobileWorkShop);
			log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- isMobileWorkShopFUllAccess "+e.getMessage());
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
	
	public List<CommonStringList> getWorkshopList(String userName){
		List<CommonStringList> workshopList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			workshopList=new ArrayList<CommonStringList>();
			boolean accessFlag = isWorkshopFUllAccess(userName);
			if(accessFlag){
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.STATELEVEL);
				workshopList.add(commonStringList);
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_WORKSHOP_NAME);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	CommonStringList CommonStringList = new CommonStringList();
		        	CommonStringList.setId(resultSet.getInt(1));
		        	CommonStringList.setName(resultSet.getString(2));
		        	workshopList.add(CommonStringList);
	        	}	
	        }
	        if(workshopList.size()==0)
	        	workshopList=null;
	        log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getWorkshopList "+e.getMessage());
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
			return workshopList;
	}
	
	public boolean isWorkshopFUllAccess(String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		PreparedStatement preparedStatement1 =null;
		ResultSet resultSet1 =null;
		int totalNoOfUserAccessWorkshop;
		int totalNoOfWorkshop;
		boolean flag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			totalNoOfUserAccessWorkshop=0;
			totalNoOfWorkshop=0;
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SHTO_WORKSHOP_NAME_COUNT);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	totalNoOfWorkshop = resultSet.getInt(1);
	        }
			preparedStatement1 = connection.prepareStatement(ShdrcQueryList.SHTO_ACCESS_RESTRICTED_WORKSHOP_NAME_COUNT);
			preparedStatement1.setString(1,userName);
			preparedStatement1.setString(2,userName);
			resultSet1 = preparedStatement1.executeQuery();
	        if (resultSet1.next()) {
	        	totalNoOfUserAccessWorkshop = resultSet1.getInt(1);
	        }
			flag=(totalNoOfWorkshop==totalNoOfUserAccessWorkshop);
			log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- isWorkshopFUllAccess "+e.getMessage());
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

}
