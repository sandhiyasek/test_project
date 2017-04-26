package gov.shdrc.dataentry.dao.jdbc;

import gov.shdrc.util.DatabaseConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.controller.CmchisDataEntryController;
import gov.shdrc.dataentry.dao.ICmchisDataEntryDAO;
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

import gov.shdrc.webservice.reader.NewDataSet;

@Service
public class CmchisDataEntryDAOJdbc implements ICmchisDataEntryDAO {
	private static Logger log=Logger.getLogger(CmchisDataEntryController.class);

/*private static final Logger logger = Logger.getLogger(CmchisDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

	public List<CommonStringList> getInstitutionList(Integer districtId,String userName){
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
			preparedStatement = connection.prepareStatement(ShdrcQueryList.CMCHIS_INSTITUTION);
			preparedStatement.setInt(1,districtId);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	institutionList.add(commonStringList);
	        }
	        log.debug(this.getClass().getName() + "- Exit ");
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
			preparedStatement = connection.prepareStatement(ShdrcQueryList.CMCHIS_INSTITUTION_COUNT);
			preparedStatement.setInt(1,districtId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	totalNoOfInstitution = resultSet.getInt(1);
	        }
			preparedStatement1 = connection.prepareStatement(ShdrcQueryList.ACCESS_RESTRICTED_CMCHIS_INSTITUTION_COUNT);
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
	                if (connection != null) {
	                	connection.close();
	                }
	             
	            } catch (SQLException ex) {
	            }	
			}	        
			return flag;
	}
	
	public JSONArray getCmchisDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
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
					preparedStatement = connection.prepareStatement(ShdrcQueryList.CMCHIS_DAILY_RECORDS);
					preparedStatement.setDate(5, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.CMCHIS_WEEKLY_RECORDS);
					preparedStatement.setString(5, week);
					preparedStatement.setString(6, month);
					preparedStatement.setInt(7, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.CMCHIS_MONTHLY_RECORDS);
					preparedStatement.setString(5, month);
					preparedStatement.setInt(6, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.CMCHIS_QUARTERLY_RECORDS);
					preparedStatement.setString(5, quarter);
					preparedStatement.setInt(6, year);
				}
				preparedStatement.setInt(1, districtId);
				preparedStatement.setInt(2, institutionId);
				preparedStatement.setString(3, classificationName);
				preparedStatement.setString(4, frequency);
								
				resultSet = preparedStatement.executeQuery();
				jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
			}
			log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getCmchisDirectorateRecords "+e.getMessage());
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
	
	public boolean insertCmchisDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_CMCHIS_DIRECTORATE);
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
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.CMCHIS);
						preparedStatementInsert.setInt(2,districtId);
						preparedStatementInsert.setInt(3,institutionId);
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
	    	log.error(this.getClass().getName() + "- insertCmchisDirectorateRecords "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertCmchisDirectorateRecords "+e.getMessage());
			}
	    }catch (JSONException e) {
	    	log.error(this.getClass().getName() + "- insertCmchisDirectorateRecords "+e.getMessage());			
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
	
	public boolean updateCmchisDirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_CMCHIS_DIRECTORATE);
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
	    	log.error(this.getClass().getName() + "- updateCmchisDirectorateRecords "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateCmchisDirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
				log.error(this.getClass().getName() + "- updateCmchisDirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateCmchisDirectorateRecords "+e1.getMessage());
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
	
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		Connection connection = null;
		JSONArray jsonArray= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.CMCHIS_INDICATOR_LIST);
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
	
	public boolean insertWebservicPreAuthData(List<NewDataSet.Shrdcpa> cmchisPreAuthDataList,String columnHeaders){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			//connection=jdbcTemplate.getDataSource().getConnection();
			connection=DatabaseConnectionHelper.getInstance().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.CMCHIS_WEBSERVICE_INSERT_PREAUTH);
			for(NewDataSet.Shrdcpa cmchisPreAuthData:cmchisPreAuthDataList){
				preparedStatementInsert.setString(1,cmchisPreAuthData.getRefno());
				preparedStatementInsert.setString(2,cmchisPreAuthData.getPatientName());
				preparedStatementInsert.setString(3,cmchisPreAuthData.getGender());
				preparedStatementInsert.setString(4,cmchisPreAuthData.getPatientAge());
				preparedStatementInsert.setString(5,cmchisPreAuthData.getPayerZone());
				preparedStatementInsert.setString(6,cmchisPreAuthData.getHospital());
				preparedStatementInsert.setString(7,cmchisPreAuthData.getHospCode());
				preparedStatementInsert.setString(8,cmchisPreAuthData.getCity());
				preparedStatementInsert.setString(9,cmchisPreAuthData.getMemberCaste());
				preparedStatementInsert.setString(10,cmchisPreAuthData.getCardNo());
				preparedStatementInsert.setString(11,cmchisPreAuthData.getPatientTPAID());
				preparedStatementInsert.setString(12,cmchisPreAuthData.getFirstSubmissionTime());
				preparedStatementInsert.setString(13,cmchisPreAuthData.getSubmissionTime());
				preparedStatementInsert.setString(14,cmchisPreAuthData.getAuthorizedTime());
				preparedStatementInsert.setString(15,cmchisPreAuthData.getTAT());
				preparedStatementInsert.setString(16,cmchisPreAuthData.getStatus());
				preparedStatementInsert.setString(17,cmchisPreAuthData.getProcedureName());
				preparedStatementInsert.setFloat(18,cmchisPreAuthData.getAppAmt());
				preparedStatementInsert.setString(19,cmchisPreAuthData.getREMARKS());
				preparedStatementInsert.setString(20,columnHeaders);
				preparedStatementInsert.setString(21,"webservice");
				preparedStatementInsert.addBatch();	
			}
			int rowCount[]=preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- insertWebservicPreAuthData "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertWebservicPreAuthData "+e1.getMessage());
			}
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
	
	public boolean insertWebservicClaimsAuthData(List<NewDataSet.Shrdccl> cmchisClaimsDataList,String columnHeaders){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=DatabaseConnectionHelper.getInstance().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.CMCHIS_WEBSERVICE_INSERT_CLAIMS);
			for(NewDataSet.Shrdccl cmchisClaimsData:cmchisClaimsDataList){
				preparedStatementInsert.setString(1,cmchisClaimsData.getRefno());
				preparedStatementInsert.setString(2,cmchisClaimsData.getPatientName());
				preparedStatementInsert.setString(3,cmchisClaimsData.getGender());
				preparedStatementInsert.setString(4,cmchisClaimsData.getPatientAge());
				preparedStatementInsert.setString(5,cmchisClaimsData.getPayerZone());
				preparedStatementInsert.setString(6,cmchisClaimsData.getHospital());
				preparedStatementInsert.setString(7,cmchisClaimsData.getHospCode());
				preparedStatementInsert.setString(8,cmchisClaimsData.getCity());
				preparedStatementInsert.setString(9,cmchisClaimsData.getMemberCaste());
				preparedStatementInsert.setString(10,cmchisClaimsData.getCardNo());
				preparedStatementInsert.setString(11,cmchisClaimsData.getPatientTPAID());
				preparedStatementInsert.setString(12,null);
				preparedStatementInsert.setString(13,cmchisClaimsData.getSubmissionTime());
				preparedStatementInsert.setString(14,cmchisClaimsData.getAuthorizedTime());
				preparedStatementInsert.setString(15,cmchisClaimsData.getTAT());
				preparedStatementInsert.setString(16,cmchisClaimsData.getStatus());
				preparedStatementInsert.setString(17,cmchisClaimsData.getProcedureName());
				preparedStatementInsert.setFloat(18,cmchisClaimsData.getFinalAppAmt());
				preparedStatementInsert.setString(19,cmchisClaimsData.getREMARKS());
				preparedStatementInsert.setString(20,columnHeaders);
				preparedStatementInsert.setString(21,"webservice");
				preparedStatementInsert.addBatch();	
			}
			int rowCount[]=preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- insertWebservicClaimsAuthData "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertWebservicClaimsAuthData "+e1.getMessage());
			}
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
	
	public boolean insertWebservicPaymentData(List<NewDataSet.Shrdcpayment> cmchisPaymentDataList,String columnHeaders){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=DatabaseConnectionHelper.getInstance().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.CMCHIS_WEBSERVICE_INSERT_PAYMENT);
			for(NewDataSet.Shrdcpayment cmchisPayment:cmchisPaymentDataList){
				preparedStatementInsert.setString(1,cmchisPayment.getRefNo());
				preparedStatementInsert.setString(2,cmchisPayment.getHospCode());
				preparedStatementInsert.setString(3,cmchisPayment.getHospital());
				preparedStatementInsert.setString(4,cmchisPayment.getPayerZone());
				preparedStatementInsert.setLong(5,cmchisPayment.getTDS());
				preparedStatementInsert.setLong(6,cmchisPayment.getOtherDeduction());
				preparedStatementInsert.setLong(7,cmchisPayment.getNetPayable());
				preparedStatementInsert.setString(8,cmchisPayment.getPaymentStatus());
				preparedStatementInsert.setString(9,cmchisPayment.getClaimProcessTime());
				preparedStatementInsert.setString(10,cmchisPayment.getEftCreationTime());
				preparedStatementInsert.setString(11,cmchisPayment.getPaymentConfirmTime());
				preparedStatementInsert.setString(12,cmchisPayment.getTAT());
				preparedStatementInsert.setString(13,"webservice");
				preparedStatementInsert.addBatch();	
			}
			int rowCount[]=preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- insertWebservicPaymentData "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertWebservicPaymentData "+e1.getMessage());
			}
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
	
	public boolean insertWebservicDCData(List<NewDataSet.Shrdcdccl> cmchisDCDataList,String columnHeaders){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=DatabaseConnectionHelper.getInstance().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.CMCHIS_WEBSERVICE_INSERT_DC);
			for(NewDataSet.Shrdcdccl cmchisDCData:cmchisDCDataList){
				preparedStatementInsert.setString(1,cmchisDCData.getRefno());
				preparedStatementInsert.setString(2,cmchisDCData.getPatientName());
				preparedStatementInsert.setString(3,cmchisDCData.getGender());
				preparedStatementInsert.setString(4,cmchisDCData.getPatientAge());
				preparedStatementInsert.setString(5,cmchisDCData.getPayerZone());
				preparedStatementInsert.setString(6,cmchisDCData.getHospCode());
				preparedStatementInsert.setString(7,cmchisDCData.getDiagnosticCenter());
				preparedStatementInsert.setString(8,cmchisDCData.getCity());
				preparedStatementInsert.setString(9,cmchisDCData.getMemberCaste());
				preparedStatementInsert.setString(10,cmchisDCData.getCardNo());
				preparedStatementInsert.setString(11,cmchisDCData.getPatientTPAID());
				preparedStatementInsert.setString(12,cmchisDCData.getSubmissionTime());
				preparedStatementInsert.setString(13,cmchisDCData.getAuthorizedTime());
				preparedStatementInsert.setString(14,cmchisDCData.getTAT());
				preparedStatementInsert.setString(15,cmchisDCData.getStatus());
				preparedStatementInsert.setString(16,cmchisDCData.getProcedureName());
				preparedStatementInsert.setString(17,cmchisDCData.getREMARKS());
				preparedStatementInsert.setString(18,columnHeaders);
				preparedStatementInsert.setString(19,"webservice");
				preparedStatementInsert.setFloat(20,cmchisDCData.getFinalAppAmt());
				preparedStatementInsert.addBatch();	
			}
			int rowCount[]=preparedStatementInsert.executeBatch();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- insertWebservicDCData "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertWebservicDCData "+e1.getMessage());
			}
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
	
	public boolean insertHospitalList(List<NewDataSet.Shrdchosp> hospitalList){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			if(disableHospitalList()){
				connection=DatabaseConnectionHelper.getInstance().getConnection();
				connection.setAutoCommit(false);
				preparedStatementInsert = connection.prepareStatement
					      (ShdrcQueryList.CMCHIS_WEBSERVICE_INSERT_HOSPITAL);
				for(NewDataSet.Shrdchosp hospital:hospitalList){
					preparedStatementInsert.setString(1,hospital.getHOSPCODE());
					preparedStatementInsert.setString(2,hospital.getHOSPNAME());
					preparedStatementInsert.setString(3,hospital.getLOCATION());
					preparedStatementInsert.setString(4,hospital.getOWNERTYPE());
					preparedStatementInsert.setString(5,"webservice");
					preparedStatementInsert.addBatch();	
				}
				int rowCount[]=preparedStatementInsert.executeBatch();
				successFlag=true;
				connection.commit();
			}	
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- insertHospitalList "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertHospitalList "+e1.getMessage());
			}
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
	
	public boolean disableHospitalList(){  
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=DatabaseConnectionHelper.getInstance().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate = connection.prepareStatement
				      (ShdrcQueryList.CMCHIS_WEBSERVICE_DISABLE_HOSPITAL);
			int rowCount=preparedStatementUpdate.executeUpdate();
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- disableHospitalList "+e.getMessage());
			try {
				log.debug(this.getClass().getName() + "- Entering ");
				connection.rollback();
				log.debug(this.getClass().getName() + "- Exit ");
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- disableHospitalList "+e1.getMessage());
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
