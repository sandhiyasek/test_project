package gov.shdrc.dataentry.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.controller.CmchisDataEntryController;
import gov.shdrc.dataentry.dao.IDfwDataEntryDAO;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.home.dao.ICommonDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.DFWEmployeeForm;
import gov.shdrc.util.Promotion;
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
public class DfwDataEntryDAOJdbc implements IDfwDataEntryDAO {
private static Logger log=Logger.getLogger(DfwDataEntryDAOJdbc.class);

/*private static final Logger logger = Logger.getLogger(DfwDataEntryDAOJdbc.class);*/
@Autowired
JdbcTemplate jdbcTemplate;
@Autowired
ICommonDAO commonDAO;

	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName){
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
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_INSTITUTION);
			preparedStatement.setString(1,institutionType);
			preparedStatement.setInt(2,districtId);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			preparedStatement.setString(5,userName);
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
	
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName){
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
				defualtList.setName("DFWB");
				institutionTypeList.add(defualtList);
			}	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_INSTITUTION_TYPE);
			preparedStatement.setInt(1,districtId);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
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
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_INSTITUTION_COUNT);
			preparedStatement.setInt(1,districtId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	totalNoOfInstitution = resultSet.getInt(1);
	        }
			preparedStatement1 = connection.prepareStatement(ShdrcQueryList.ACCESS_RESTRICTED_DFW_INSTITUTION_COUNT);
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
	
	public JSONArray getDfwDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year){
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
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_DAILY_RECORDS);
					preparedStatement.setDate(6, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_WEEKLY_RECORDS);
					preparedStatement.setString(6, week);
					preparedStatement.setString(7, month);
					preparedStatement.setInt(8, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_MONTHLY_RECORDS);
					preparedStatement.setString(6, month);
					preparedStatement.setInt(7, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_QUARTERLY_RECORDS);
					preparedStatement.setString(6, quarter);
					preparedStatement.setInt(7, year);
				}
				preparedStatement.setInt(1, districtId);
				preparedStatement.setString(2, institutionType);
				preparedStatement.setInt(3, institutionId);
				preparedStatement.setString(4, classificationName);
				preparedStatement.setString(5, frequency);
				
				resultSet = preparedStatement.executeQuery();
				jsonArray = commonDAO.getGridRecordsFromRS(resultSet);
			}log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getDfwDirectorateRecords "+e.getMessage());
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
	
	public boolean insertDfwDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String dataEntryLevel,String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){  
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementInsert = connection.prepareStatement
				      (ShdrcQueryList.INSERT_DFW_DIRECTORATE);
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
						preparedStatementInsert.setInt(1,ShdrcConstants.DirectorateId.DFW);
						preparedStatementInsert.setString(2,dataEntryLevel);
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
	    	log.error(this.getClass().getName() + "- insertDfwDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- insertDfwDirectorateRecords "+e1.getMessage());
			}
		}catch(Exception json){
			
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
	
	public boolean updateDfwDirectorateRecords(JSONArray jsonArray){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_DFW_DIRECTORATE);
		
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
	    	log.error(this.getClass().getName() + "- updateDfwDirectorateRecords "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDfwDirectorateRecords "+e1.getMessage());
			}
		}catch(JSONException json){
			try {
				connection.rollback();
				log.error(this.getClass().getName() + "- updateDfwDirectorateRecords "+json.getMessage());
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- updateDfwDirectorateRecords "+e1.getMessage());
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
	
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel,
			String dataEntryLevel,String month){
		Connection connection = null;
		JSONArray jsonArray= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String indicatorLevel=null;
		if("April".equalsIgnoreCase(month)){
			indicatorLevel="Month";
		}else{
			indicatorLevel="None";
		}
		String query=null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			if(ShdrcConstants.DirectorateId.DFW==directorateId && ("HR".equalsIgnoreCase(classificationName) || "Grants".equalsIgnoreCase(classificationName))){
				query="select \"Indicator_Id\",\"General_Id\",\"Indicator_Name\",\"General_Type\",\"General_Name\",\"General_SubCategory\",null,\"Is_Demography\",null,null,\"Is_Character\",null " +
						"from shdrc_dwh.\"Vw_UDSM_DFW\" where \"Directorate_Id\"=? and \"General_Category\"=? and \"Indicator_Frequency\"=? and \"Is_Demography\"=? and \"Ind_Hierarchy\" like ? and \"General_Level\"in ("+generalNameLevel+")"+
						" and (\"Indicator_Level\"=? or \"Indicator_Level\"='None') order by \"Ind_Order\",\"Gen_Order\" asc";
			}else{
				query=ShdrcQueryList.DFW_INDICATOR_LIST;
			}	
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, classificationName);
			preparedStatement.setString(3,Util.getFrequencyShortName(frequency));
			preparedStatement.setString(4,isDemographic);
			preparedStatement.setString(5,SplCharsConstants.PERCENTAGE+dataEntryLevel+SplCharsConstants.PERCENTAGE);
			preparedStatement.setString(6,indicatorLevel);
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
	
	public List<DFWEmployeeForm> getEmployeeDetailsList(Integer districtId,Integer institutionId){
		DFWEmployeeForm employeeDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<DFWEmployeeForm> employeeDetailsList =null;
	try {
		log.debug(this.getClass().getName() + "- Entering ");
		employeeDetailsList = new ArrayList<DFWEmployeeForm>();
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_LIST);
		preparedStatement.setInt(1,districtId);
		preparedStatement.setInt(2,institutionId);
		resultSet = preparedStatement.executeQuery();
		String strDobDate=null;
		String strFirstAppointmentDOJ=null;
		String strDateOfRegularisation=null;
		String strDateOfRetirement=null;
		String strDateOfProbationDecleration=null;
		String strSlsDueDate=null;
        while (resultSet.next()) {
        	employeeDetails =new DFWEmployeeForm();
        	employeeDetails.setEmployeeId(resultSet.getLong(1));
        	employeeDetails.setEmployeeName(resultSet.getString(2));
        	employeeDetails.setGpfOrCpsNo(resultSet.getString(3));
        	employeeDetails.setGender(resultSet.getString(4));
        	employeeDetails.setDesignation(resultSet.getString(5));
        	employeeDetails.setPayBand(resultSet.getString(6));
        	employeeDetails.setGroupCategory(resultSet.getString(7));
        	employeeDetails.setDutyPay(resultSet.getString(8));
        	employeeDetails.setRecruitedBy(resultSet.getString(9));
        	employeeDetails.setGradePay(resultSet.getString(10));
        	employeeDetails.setCommunity(resultSet.getString(11));
        	strDobDate=resultSet.getString(12);
        	employeeDetails.setDobDate(strDobDate!=null?Util.convertSqlDateToStrDate(strDobDate):null);
        	employeeDetails.setFirstAppointmentPost(resultSet.getString(13));
        	strFirstAppointmentDOJ=resultSet.getString(14);
        	employeeDetails.setFirstAppointmentDOJ(strFirstAppointmentDOJ!=null?Util.convertSqlDateToStrDate(strFirstAppointmentDOJ):null);
        	strDateOfRegularisation=resultSet.getString(15);
        	employeeDetails.setDateOfRegularisation(strDateOfRegularisation!=null?Util.convertSqlDateToStrDate(strDateOfRegularisation):null);
        	strDateOfProbationDecleration=resultSet.getString(16);
        	employeeDetails.setDateOfProbationDecleration(strDateOfProbationDecleration!=null?Util.convertSqlDateToStrDate(strDateOfProbationDecleration):null);
        	strDateOfRetirement=resultSet.getString(17);
        	employeeDetails.setDateOfRetirement(strDateOfRetirement!=null?Util.convertSqlDateToStrDate(strDateOfRetirement):null);
        	employeeDetails.setIncrementDueMonth(resultSet.getString(18));
        	strSlsDueDate=resultSet.getString(19);
        	employeeDetails.setSlsDueDate(strSlsDueDate!=null?Util.convertSqlDateToStrDate(strSlsDueDate):null);
        	employeeDetails.setPhysicalStatus(resultSet.getString(20));
        	employeeDetails.setPunishments(resultSet.getString(21));
        	employeeDetails.setNomineeDetails(resultSet.getString(22));
        	employeeDetailsList.add(employeeDetails);
        }log.debug(this.getClass().getName() + "- Exit ");
	} catch (SQLException e) {
		log.error(this.getClass().getName() + "- getEmployeeDetailsList "+e.getMessage());
	}
	finally{
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
	return employeeDetailsList;
	}
	
	public List<CommonStringList> getPostList(Integer directorateId){
		List<CommonStringList> postList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			postList = new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_POST_LIST);
			preparedStatement.setInt(1,directorateId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	postList.add(commonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		}catch (SQLException e) {
			log.error(this.getClass().getName() + "- getPostList "+e.getMessage());
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
	            }catch (SQLException ex) {
	            }	
			}	        
			return postList;
	}	
	
	public DFWEmployeeForm getEmployeeDetails(Long employeeId){
		DFWEmployeeForm employeeDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	try {		
		log.debug(this.getClass().getName() + "- Entering ");
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_EMPLOYEE_DETAILS);
		preparedStatement.setLong(1,employeeId);
		resultSet = preparedStatement.executeQuery();
		String strDobDate=null;
		String strFirstAppointmentDOJ=null;
		String strDateOfRegularisation=null;
		String strDateOfRetirement=null;
		String strDateOfProbationDecleration=null;
		String strSlsDueDate=null;
		
        while (resultSet.next()) {
        	employeeDetails =new DFWEmployeeForm();
        	employeeDetails.setEmployeeId(resultSet.getLong(1));
        	employeeDetails.setEmployeeName(resultSet.getString(2));
        	employeeDetails.setGpfOrCpsNo(resultSet.getString(3));
        	employeeDetails.setGender(resultSet.getString(4));
        	employeeDetails.setDesignation(resultSet.getString(5));
        	employeeDetails.setPayBand(resultSet.getString(6));
        	employeeDetails.setGroupCategory(resultSet.getString(7));
        	employeeDetails.setDutyPay(resultSet.getString(8));
        	employeeDetails.setRecruitedBy(resultSet.getString(9));
        	employeeDetails.setGradePay(resultSet.getString(10));
        	employeeDetails.setCommunity(resultSet.getString(11));
        	strDobDate=resultSet.getString(12);
        	employeeDetails.setDobDate(strDobDate!=null?Util.convertSqlDateToStrDate(strDobDate):null);
        	employeeDetails.setFirstAppointmentPost(resultSet.getString(13));
        	strFirstAppointmentDOJ=resultSet.getString(14);
        	employeeDetails.setFirstAppointmentDOJ(strFirstAppointmentDOJ!=null?Util.convertSqlDateToStrDate(strFirstAppointmentDOJ):null);
        	strDateOfRegularisation=resultSet.getString(15);
        	employeeDetails.setDateOfRegularisation(strDateOfRegularisation!=null?Util.convertSqlDateToStrDate(strDateOfRegularisation):null);
        	strDateOfProbationDecleration=resultSet.getString(16);
        	employeeDetails.setDateOfProbationDecleration(strDateOfProbationDecleration!=null?Util.convertSqlDateToStrDate(strDateOfProbationDecleration):null);
        	strDateOfRetirement=resultSet.getString(17);
        	employeeDetails.setDateOfRetirement(strDateOfRetirement!=null?Util.convertSqlDateToStrDate(strDateOfRetirement):null);
        	employeeDetails.setIncrementDueMonth(resultSet.getString(18));
        	strSlsDueDate=resultSet.getString(19);
        	employeeDetails.setSlsDueDate(strSlsDueDate!=null?Util.convertSqlDateToStrDate(strSlsDueDate):null);
        	employeeDetails.setPhysicalStatus(resultSet.getString(20));
        	employeeDetails.setPunishments(resultSet.getString(21));
        	employeeDetails.setNomineeDetails(resultSet.getString(22));
        	employeeDetails.setDistrictId(resultSet.getInt(23));
        	employeeDetails.setInstitutionId(resultSet.getInt(24));
        	employeeDetails.setRetirementType(resultSet.getString(25));
        	employeeDetails.setTransferStatus(resultSet.getString(26));
        	employeeDetails.setTransferType(resultSet.getString(27));
        	employeeDetails.setWorkingLocation(resultSet.getString(28));
        	employeeDetails.setMaritalStatus(resultSet.getString(29));
        }log.debug(this.getClass().getName() + "- Exit ");
	} catch (SQLException e) {
		log.error(this.getClass().getName() + "- getEmployeeDetails "+e.getMessage());
	}
	finally{
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
	return employeeDetails;
	}
	
	public DFWEmployeeForm getQualificationDetails(Long employeeId){
		DFWEmployeeForm qualificationDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {	
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_EMPLOYEE_QUALIFICATION_DETAILS);
			preparedStatement.setLong(1,employeeId);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	qualificationDetails =new DFWEmployeeForm();
	        	qualificationDetails.setQualification(resultSet.getString(1));
	        	qualificationDetails.setUgMajor(resultSet.getString(2));
	        	qualificationDetails.setPgMajor(resultSet.getString(3));
	        	qualificationDetails.setOthers(resultSet.getString(4));
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getQualificationDetails "+e.getMessage());
		}
		finally{
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
		return qualificationDetails;

	}
	
	public Promotion getPromotionDetails(Long employeeId){
		Promotion promotionDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {	
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_EMPLOYEE_PROMOTION_DETAILS);
			preparedStatement.setLong(1,employeeId);
			resultSet = preparedStatement.executeQuery();
			String strPromotionDOJ=null;
	       if(resultSet.next()) {
	        	promotionDetails =new Promotion();
	        	promotionDetails.setPromotionType(resultSet.getString(1));
	        	promotionDetails.setPromotion(resultSet.getString(2));
	        	strPromotionDOJ=resultSet.getString(3);
	        	promotionDetails.setPromotionDOJ(strPromotionDOJ!=null?Util.convertSqlDateToStrDate(strPromotionDOJ):null);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getPromotionDetails "+e.getMessage());
		}
		finally{
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
		return promotionDetails;
	} 
	
	public DFWEmployeeForm getLeaveDetails(Long employeeId){
		DFWEmployeeForm leaveDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
		try {	
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_EMPLOYEE_LEAVE_DETAILS);
			preparedStatement.setLong(1,employeeId);
			resultSet = preparedStatement.executeQuery();
			String strELTakenFrom=null;			
			String strELTakenTo=null;
			String strMLTakenFrom=null;
			String strMLTakenTo=null;
			String strUELTakenFrom=null;
			String strUELTakenTo=null;
	        if (resultSet.next()) {
	        	leaveDetails =new DFWEmployeeForm();
	        	strELTakenFrom=resultSet.getString(1);
	        	leaveDetails.setELTakenFrom(strELTakenFrom!=null?Util.convertSqlDateToStrDate(strELTakenFrom):null);
	        	strELTakenTo=resultSet.getString(2);
	        	leaveDetails.setELTakenTo(strELTakenTo!=null?Util.convertSqlDateToStrDate(strELTakenTo):null);
	        	leaveDetails.setELTakenDays(resultSet.getInt(3));
	        	leaveDetails.setBalanceEL(resultSet.getInt(4));
	        	strMLTakenFrom=resultSet.getString(5);
	        	leaveDetails.setMLTakenFrom(strMLTakenFrom!=null?Util.convertSqlDateToStrDate(strMLTakenFrom):null);
	        	strMLTakenTo=resultSet.getString(6);
	        	leaveDetails.setMLTakenTo(strMLTakenTo!=null?Util.convertSqlDateToStrDate(strMLTakenTo):null);
	        	leaveDetails.setMLTakenDays(resultSet.getInt(7));
	        	leaveDetails.setBalanceML(resultSet.getInt(8));
	        	strUELTakenFrom=resultSet.getString(9);
	        	leaveDetails.setUELTakenFrom(strUELTakenFrom!=null?Util.convertSqlDateToStrDate(strUELTakenFrom):null);
	        	strUELTakenTo=resultSet.getString(10);
	        	leaveDetails.setUELTakenTo(strUELTakenTo!=null?Util.convertSqlDateToStrDate(strUELTakenTo):null);
	        	leaveDetails.setUELTakenDays(resultSet.getInt(11));
	        	leaveDetails.setBalanceUEL(resultSet.getInt(12));	        	
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getLeaveDetails "+e.getMessage());
		  }
	    finally{
			try {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (connection != null) {
	            	connection.close();
	            }log.debug(this.getClass().getName() + "- Exit ");
	
	        } catch (SQLException ex) {
	          }	
		}
		return leaveDetails;	
	}
	
	public List<CommonStringList> getInstitutionList(Integer districtId){
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
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_TRANSFER_INSTITUTION);
			preparedStatement.setInt(1,districtId);
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
	
	public List<DFWEmployeeForm> getEmployeeTransferDetailsList(){
		DFWEmployeeForm employeeTransferDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	    List<DFWEmployeeForm> employeeTransferDetailsList =null;
	try {
		log.debug(this.getClass().getName() + "- Entering ");
		employeeTransferDetailsList = new ArrayList<DFWEmployeeForm>();
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_TRANSFER_LIST);
		resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
        	employeeTransferDetails =new DFWEmployeeForm();
        	employeeTransferDetails.setEmployeeId(resultSet.getLong(1));
        	employeeTransferDetails.setEmployeeName(resultSet.getString(2));
        	employeeTransferDetails.setGpfOrCpsNo(resultSet.getString(3));
        	employeeTransferDetails.setDesignation(resultSet.getString(4));
        	employeeTransferDetails.setDistrictId(resultSet.getInt(5));
        	employeeTransferDetails.setDistrictName(resultSet.getString(6));
        	employeeTransferDetails.setInstitutionId(resultSet.getInt(7));
        	employeeTransferDetails.setInstitutionName(resultSet.getString(8));
        	employeeTransferDetailsList.add(employeeTransferDetails);
        }log.debug(this.getClass().getName() + "- Exit ");
	} catch (SQLException e) {
		log.error(this.getClass().getName() + "- getEmployeeTransferDetailsList "+e.getMessage());
	}
	finally{
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
	return employeeTransferDetailsList;
	}
	
	public boolean insertEmployeeDetails(Integer districtId,Integer institutionId,String employeeName,String gpfOrCpsNo,String gender,String designation,String payBand,String groupCategory,
			 Integer dutyPay,String workingLocation, String recruitedBy,Integer gradePay,String community,String dobDate,String qualification,String ugMajor,String pgMajor,String others,
			 String firstAppointmentPost,String firstAppointmentDOJ,String dateOfRegularisation,String dateOfProbationDecleration,String dateOfRetirement,String incrementDueMonth,
			 String slsDueDate,String physicalStatus,String punishments,String maritalStatus,String nomineeDetails,String ELTakenFrom,String ELTakenTo,Integer ELTakenDays,Integer balanceEL,
			 String MLTakenFrom,String MLTakenTo,Integer MLTakenDays,Integer balanceML,String UELTakenFrom,String UELTakenTo,Integer UELTakenDays,Integer balanceUEL,
			 List<Promotion> promotionList){ 
			 Connection connection = null;
			 PreparedStatement preparedStatementInsert =null;
			 PreparedStatement preparedStatementInsert1 =null;
			 Long employeeId=null;
			 boolean successFlag=false;
			 try {
				 log.debug(this.getClass().getName() + "- Entering ");
				 boolean isEmployeeExists =isEmployeeExists(gpfOrCpsNo);
				 if(isEmployeeExists){
					 throw new SHDRCException("The employee record already exists");
				 }
				 
				connection=jdbcTemplate.getDataSource().getConnection();
				connection.setAutoCommit(false);
				preparedStatementInsert = connection.prepareStatement(ShdrcQueryList.INSERT_EMPLOYEE_DETAILS);
				java.sql.Date  dateOfRegularise=null;
				if(Util.isNotNull(dateOfRegularisation))
					dateOfRegularise=new java.sql.Date(Util.getUtilDate(dateOfRegularisation).getTime());
				java.sql.Date  dateOfProbationDeclare=null;
				if(Util.isNotNull(dateOfProbationDecleration))
					dateOfProbationDeclare=new java.sql.Date(Util.getUtilDate(dateOfProbationDecleration).getTime());
				java.sql.Date  dateOfRetire=null;
				if(Util.isNotNull(dateOfRetirement))
					dateOfRetire=new java.sql.Date(Util.getUtilDate(dateOfRetirement).getTime());
				java.sql.Date  slsDue=null;
				if(Util.isNotNull(slsDueDate))
					slsDue=new java.sql.Date(Util.getUtilDate(slsDueDate).getTime());			
				java.sql.Date  ELFrom=null;
				if(Util.isNotNull(ELTakenFrom))
					ELFrom=new java.sql.Date(Util.getUtilDate(ELTakenFrom).getTime());			
				java.sql.Date  ELTo=null;
				if(Util.isNotNull(ELTakenTo))
					ELTo=new java.sql.Date(Util.getUtilDate(ELTakenTo).getTime());			
				java.sql.Date  MLFrom=null;
				if(Util.isNotNull(MLTakenFrom))
					MLFrom=new java.sql.Date(Util.getUtilDate(MLTakenFrom).getTime());			
				java.sql.Date  MLTo=null;
				if(Util.isNotNull(MLTakenTo))
					MLTo=new java.sql.Date(Util.getUtilDate(MLTakenTo).getTime());			
				java.sql.Date  UELFrom=null;
				if(Util.isNotNull(UELTakenFrom))
					UELFrom=new java.sql.Date(Util.getUtilDate(UELTakenFrom).getTime());			
				java.sql.Date  UELTo=null;
				if(Util.isNotNull(UELTakenTo))
					UELTo=new java.sql.Date(Util.getUtilDate(UELTakenTo).getTime());
				
				java.sql.Date promotionDOJ =null;
				
				preparedStatementInsert.setInt(1,districtId);
				preparedStatementInsert.setString(2,employeeName);
				preparedStatementInsert.setString(3,gpfOrCpsNo);
				preparedStatementInsert.setString(4,gender);
				preparedStatementInsert.setString(5,designation);
				preparedStatementInsert.setString(6,payBand);
				preparedStatementInsert.setString(7,groupCategory);
				preparedStatementInsert.setInt(8,dutyPay);
				preparedStatementInsert.setString(9,recruitedBy);
				preparedStatementInsert.setInt(10,gradePay);
				preparedStatementInsert.setString(11,community);
				preparedStatementInsert.setDate(12,new java.sql.Date (Util.getUtilDate(dobDate).getTime()));
				preparedStatementInsert.setString(13,firstAppointmentPost);
				preparedStatementInsert.setDate(14,new java.sql.Date (Util.getUtilDate(firstAppointmentDOJ).getTime()));
				preparedStatementInsert.setDate(15,dateOfRegularise);
				preparedStatementInsert.setDate(16,dateOfProbationDeclare);
				preparedStatementInsert.setDate(17,dateOfRetire);
				preparedStatementInsert.setString(18,incrementDueMonth);
				preparedStatementInsert.setDate(19,slsDue);
				preparedStatementInsert.setString(20,physicalStatus);
				preparedStatementInsert.setString(21,punishments);			
				preparedStatementInsert.setString(22,nomineeDetails);
				preparedStatementInsert.setInt(23,institutionId);
				preparedStatementInsert.setString(24,maritalStatus);
				preparedStatementInsert.setString(25,workingLocation); 
				java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
				preparedStatementInsert.setTimestamp(26,addOnDate);
				preparedStatementInsert.setString(27,UserInfo.getLoggedInUserName());
				ResultSet rs =preparedStatementInsert.executeQuery();
				if(rs.next())
					employeeId=rs.getLong(1);
				
				preparedStatementInsert1 = connection.prepareStatement(ShdrcQueryList.INSERT_DFW_EMPLOYEE_QUALIFICATION);
				preparedStatementInsert1.setLong(1,employeeId);
				preparedStatementInsert1.setString(2,qualification);
				preparedStatementInsert1.setString(3,ugMajor);
				preparedStatementInsert1.setString(4,pgMajor);
				preparedStatementInsert1.setString(5,others);
				preparedStatementInsert1.setTimestamp(6,addOnDate);
				preparedStatementInsert1.setString(7,UserInfo.getLoggedInUserName());
				preparedStatementInsert1.execute();
				 
				if(ELFrom!=null || ELTo!=null || balanceEL!=null || MLFrom!=null || MLTo!=null || 
						balanceML!=null || UELFrom!=null || UELTo!=null || balanceUEL!=null){
					successFlag=insertEmployeeLeaveDetails(preparedStatementInsert,connection,
							 employeeId,ELFrom, ELTo, ELTakenDays,balanceEL,
							 MLFrom,MLTo,MLTakenDays,balanceML,
							UELFrom,UELTo,UELTakenDays,balanceUEL,addOnDate);					
				}
					preparedStatementInsert = connection.prepareStatement(ShdrcQueryList.INSERT_DFW_EMPLOYEE_PROMOTION);
					for(Promotion promotion:promotionList){
						preparedStatementInsert.setLong(1,employeeId);
						preparedStatementInsert.setString(2,promotion.getPromotionType());
						preparedStatementInsert.setString(3,promotion.getPromotion());
						if(Util.isNotNull(promotion.getPromotionDOJ()))
							promotionDOJ=new java.sql.Date(Util.getUtilDate(promotion.getPromotionDOJ()).getTime());
						preparedStatementInsert.setDate(4,promotionDOJ);
						preparedStatementInsert.setTimestamp(5,addOnDate);
						preparedStatementInsert.setString(6,UserInfo.getLoggedInUserName());
						preparedStatementInsert.execute();
					}
					
		            successFlag=true;
		            connection.commit();
		            log.debug(this.getClass().getName() + "- Exit ");
	         }catch (SQLException | SHDRCException e) {
	        	 log.error(this.getClass().getName() + "- insertEmployeeDetails "+e.getMessage());
	             try {
	            	 connection.rollback();
	             }catch (SQLException e1) { 
	            	 log.error(this.getClass().getName() + "- insertEmployeeDetails "+e1.getMessage());
	              }
	          }finally{
	        	  try {
	        		  if(preparedStatementInsert != null) {
	        			  preparedStatementInsert.close();
			          }
	        		  if(preparedStatementInsert1 != null) {
	        			  preparedStatementInsert1.close();
			          }
			          if(connection != null) {
			        	  connection.close();
	                  }
	              }catch (SQLException ex) {
	            	  log.error(this.getClass().getName() + "- insertEmployeeDetails "+ex.getMessage());
	               }
	           }
	           return successFlag;
		}
	
	public boolean isEmployeeExists(String gpfOrCpsNo){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		boolean isEmployeeExists=false;
		  ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_EXISTS);
			preparedStatement.setString(1,gpfOrCpsNo);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	long employeeCount=resultSet.getLong(1);
	        	isEmployeeExists =(employeeCount>0);
	        }log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	 log.error(this.getClass().getName() + "- isEmployeeExists "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				 log.error(this.getClass().getName() + "- isEmployeeExists "+e1.getMessage());
			}
		}finally{
				try {
	                if (preparedStatement != null) {
	                	preparedStatement.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }
			}
		return isEmployeeExists;		
	}

	private boolean insertEmployeeLeaveDetails(PreparedStatement preparedStatementInsert,Connection connection,
			Long employeeId,java.sql.Date ELFrom,java.sql.Date ELTo,Integer ELTakenDays,Integer balanceEL,
			java.sql.Date MLFrom,java.sql.Date MLTo,Integer MLTakenDays,Integer balanceML,
			java.sql.Date UELFrom,java.sql.Date UELTo,Integer UELTakenDays,Integer balanceUEL,java.sql.Timestamp addOnDate) throws SQLException{
		boolean successFlag =false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			preparedStatementInsert = connection.prepareStatement(ShdrcQueryList.INSERT_DFW_EMPLOYEE_LEAVE);
			preparedStatementInsert.setLong(1,employeeId);
			preparedStatementInsert.setDate(2,ELFrom);
			preparedStatementInsert.setDate(3,ELTo);
			if(ELTakenDays==null)
				preparedStatementInsert.setNull(4,java.sql.Types.INTEGER);
			else
			preparedStatementInsert.setInt(4,ELTakenDays);
			if(balanceEL==null)
				preparedStatementInsert.setNull(5,java.sql.Types.INTEGER);
			else
			preparedStatementInsert.setInt(5,balanceEL);
			preparedStatementInsert.setDate(6,MLFrom);
			preparedStatementInsert.setDate(7,MLTo);
			if(MLTakenDays==null)
				preparedStatementInsert.setNull(8,java.sql.Types.INTEGER);
			else
			preparedStatementInsert.setInt(8,MLTakenDays);
			if(balanceML==null)
				preparedStatementInsert.setNull(9,java.sql.Types.INTEGER);
			else
			preparedStatementInsert.setInt(9,balanceML);
			preparedStatementInsert.setDate(10,UELFrom);
			preparedStatementInsert.setDate(11,UELTo);
			if(UELTakenDays==null)
				preparedStatementInsert.setNull(12,java.sql.Types.INTEGER);
			else
			preparedStatementInsert.setInt(12,UELTakenDays);
			if(balanceUEL==null)
				preparedStatementInsert.setNull(13,java.sql.Types.INTEGER);
			else
			preparedStatementInsert.setInt(13,balanceUEL);
			preparedStatementInsert.setTimestamp(14,addOnDate);
			preparedStatementInsert.setString(15,UserInfo.getLoggedInUserName());
			preparedStatementInsert.execute();
			successFlag=true;
			log.debug(this.getClass().getName() + "- Exit ");
		}
			catch (SQLException e) {
				log.error(this.getClass().getName() + "- getInstitutionList "+e.getMessage());
			}
		finally{
			}
		return successFlag;		
		
	}

	public boolean updateEmployeeDetails(String gpfOrCpsNo,Long employeeId,Integer districtId,Integer institutionId,String designation,String payBand,String groupCategory,Integer dutyPay,String workingLocation,String recruitedBy,
			 Integer gradePay,String community,String dobDate,String qualification,String ugMajor,String pgMajor,String others,String firstAppointmentPost,String firstAppointmentDOJ,
			 String dateOfRegularisation,String dateOfProbationDecleration,String dateOfRetirement,String incrementDueMonth,String slsDueDate,String physicalStatus,
			 String promotionType,String promotion1,String promotion1DOJ,String punishments,String retirementType,String maritalStatus,String nomineeDetails,String ELTakenFrom,String ELTakenTo,
			 Integer ELTakenDays,Integer balanceEL,String MLTakenFrom,String MLTakenTo,Integer MLTakenDays,Integer balanceML,String UELTakenFrom,
			 String UELTakenTo,Integer UELTakenDays,Integer balanceUEL,String previousPromotionType){ 
			 Connection connection = null;
			 PreparedStatement preparedStatementUpdate =null;
			 PreparedStatement preparedStatementUpdate1 =null;
			 PreparedStatement preparedStatementUpdate2 =null;
			 PreparedStatement preparedStatementInsert =null;
			 boolean successFlag=false;
			 try {		
				 log.debug(this.getClass().getName() + "- Entering ");
				 connection=jdbcTemplate.getDataSource().getConnection();
				connection.setAutoCommit(false);
				preparedStatementUpdate = connection.prepareStatement(ShdrcQueryList.UPDATE_EMPLOYEE_DETAILS);
				java.sql.Date  dateOfRegularise=null;
				if(Util.isNotNull(dateOfRegularisation))
					dateOfRegularise=new java.sql.Date(Util.getUtilDate(dateOfRegularisation).getTime());
				java.sql.Date  dateOfProbationDeclare=null;
				if(Util.isNotNull(dateOfProbationDecleration))
					dateOfProbationDeclare=new java.sql.Date(Util.getUtilDate(dateOfProbationDecleration).getTime());
				java.sql.Date  dateOfRetire=null;
				if(Util.isNotNull(dateOfRetirement))
					dateOfRetire=new java.sql.Date(Util.getUtilDate(dateOfRetirement).getTime());
				java.sql.Date  slsDue=null;
				if(Util.isNotNull(slsDueDate))
					slsDue=new java.sql.Date(Util.getUtilDate(slsDueDate).getTime());
				java.sql.Date  ELFrom=null;
				if(Util.isNotNull(ELTakenFrom))
					ELFrom=new java.sql.Date(Util.getUtilDate(ELTakenFrom).getTime());			
				java.sql.Date  ELTo=null;
				if(Util.isNotNull(ELTakenTo))
					ELTo=new java.sql.Date(Util.getUtilDate(ELTakenTo).getTime());			
				java.sql.Date  MLFrom=null;
				if(Util.isNotNull(MLTakenFrom))
					MLFrom=new java.sql.Date(Util.getUtilDate(MLTakenFrom).getTime());			
				java.sql.Date  MLTo=null;
				if(Util.isNotNull(MLTakenTo))
					MLTo=new java.sql.Date(Util.getUtilDate(MLTakenTo).getTime());			
				java.sql.Date  UELFrom=null;
				if(Util.isNotNull(UELTakenFrom))
					UELFrom=new java.sql.Date(Util.getUtilDate(UELTakenFrom).getTime());			
				java.sql.Date  UELTo=null;
				if(Util.isNotNull(UELTakenTo))
					UELTo=new java.sql.Date(Util.getUtilDate(UELTakenTo).getTime());
				java.sql.Date  promotionDOJ=null;
				if(Util.isNotNull(promotion1DOJ))
					promotionDOJ=new java.sql.Date(Util.getUtilDate(promotion1DOJ).getTime());
				
				preparedStatementUpdate.setInt(1,districtId);
				preparedStatementUpdate.setString(2,designation);
				preparedStatementUpdate.setString(3,payBand);
				preparedStatementUpdate.setString(4,groupCategory);
				preparedStatementUpdate.setInt(5,dutyPay);
				preparedStatementUpdate.setString(6,recruitedBy);
				preparedStatementUpdate.setInt(7,gradePay);
				preparedStatementUpdate.setString(8,community);
				preparedStatementUpdate.setDate(9,new java.sql.Date (Util.getUtilDate(dobDate).getTime()));
				preparedStatementUpdate.setString(10,firstAppointmentPost);
				preparedStatementUpdate.setDate(11,new java.sql.Date (Util.getUtilDate(firstAppointmentDOJ).getTime()));
				preparedStatementUpdate.setDate(12,dateOfRegularise);
				preparedStatementUpdate.setDate(13,dateOfProbationDeclare);
				preparedStatementUpdate.setDate(14,dateOfRetire);
				preparedStatementUpdate.setString(15,incrementDueMonth);
				preparedStatementUpdate.setDate(16,slsDue);
				preparedStatementUpdate.setString(17,physicalStatus);
				preparedStatementUpdate.setString(18,punishments);			
				preparedStatementUpdate.setString(19,nomineeDetails);
				preparedStatementUpdate.setInt(20,institutionId);
				preparedStatementUpdate.setString(21,retirementType);
				preparedStatementUpdate.setString(22,workingLocation);
				preparedStatementUpdate.setString(23,maritalStatus);
				java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
				preparedStatementUpdate.setTimestamp(24,addOnDate);
				preparedStatementUpdate.setString(25,UserInfo.getLoggedInUserName());
				preparedStatementUpdate.setLong(26,employeeId);
				preparedStatementUpdate.executeUpdate();
				
				preparedStatementUpdate1 = connection.prepareStatement(ShdrcQueryList.UPDATE_DFW_EMPLOYEE_QUALIFICATION);
				preparedStatementUpdate1.setString(1,qualification);
				preparedStatementUpdate1.setString(2,ugMajor);
				preparedStatementUpdate1.setString(3,pgMajor);
				preparedStatementUpdate1.setString(4,others);
				preparedStatementUpdate1.setTimestamp(5,addOnDate);
				preparedStatementUpdate1.setString(6,UserInfo.getLoggedInUserName());
				preparedStatementUpdate1.setLong(7,employeeId);				
				preparedStatementUpdate1.execute();
				 
				if(ELFrom!=null || ELTo!=null || balanceEL!=null || MLFrom!=null || MLTo!=null || 
						balanceML!=null || UELFrom!=null || UELTo!=null || balanceUEL!=null){
					boolean isEmployeeLeaveExists=isEmployeeLeaveExists(employeeId);
					
					if(!isEmployeeLeaveExists){
						insertEmployeeLeaveDetails(preparedStatementInsert,connection,
								 employeeId,ELFrom, ELTo, ELTakenDays,balanceEL,
								 MLFrom,MLTo,MLTakenDays,balanceML,
								UELFrom,UELTo,UELTakenDays,balanceUEL,addOnDate);
					}else{
						preparedStatementUpdate2 = connection.prepareStatement(ShdrcQueryList.UPDATE_DFW_EMPLOYEE_LEAVE);
						preparedStatementUpdate2.setDate(1,ELFrom);
						preparedStatementUpdate2.setDate(2,ELTo);
						if(ELTakenDays==null)
							preparedStatementUpdate2.setNull(3,java.sql.Types.INTEGER);
						else
						preparedStatementUpdate2.setInt(3,ELTakenDays);
						if(balanceEL==null)
							preparedStatementUpdate2.setNull(4,java.sql.Types.INTEGER);
						else
						preparedStatementUpdate2.setInt(4,balanceEL);
						preparedStatementUpdate2.setDate(5,MLFrom);
						preparedStatementUpdate2.setDate(6,MLTo);
						if(MLTakenDays==null)
							preparedStatementUpdate2.setNull(7,java.sql.Types.INTEGER);
						else
						preparedStatementUpdate2.setInt(7,MLTakenDays);
						if(balanceML==null)
							preparedStatementUpdate2.setNull(8,java.sql.Types.INTEGER);
						else
						preparedStatementUpdate2.setInt(8,balanceML);
						preparedStatementUpdate2.setDate(9,UELFrom);
						preparedStatementUpdate2.setDate(10,UELTo);
						if(UELTakenDays==null)
							preparedStatementUpdate2.setNull(11,java.sql.Types.INTEGER);
						else
						preparedStatementUpdate2.setInt(11,UELTakenDays);
						if(balanceUEL==null)
							preparedStatementUpdate2.setNull(12,java.sql.Types.INTEGER);
						else
						preparedStatementUpdate2.setInt(12,balanceUEL);
						preparedStatementUpdate2.setTimestamp(13,addOnDate);
						preparedStatementUpdate2.setString(14,UserInfo.getLoggedInUserName());
						preparedStatementUpdate2.setLong(15,employeeId);
						preparedStatementUpdate2.execute();
					}
				}
				
				if(Util.isNotNull(promotionType)){
					if(!promotionType.equals(previousPromotionType)){
					preparedStatementInsert = connection.prepareStatement(ShdrcQueryList.INSERT_DFW_EMPLOYEE_PROMOTION);
					preparedStatementInsert.setLong(1,employeeId);
					preparedStatementInsert.setString(2,promotionType);
					preparedStatementInsert.setString(3,promotion1);
					preparedStatementInsert.setDate(4,promotionDOJ);
					preparedStatementInsert.setTimestamp(5,addOnDate);
					preparedStatementInsert.setString(6,UserInfo.getLoggedInUserName());					
					preparedStatementInsert.execute();
					}
				}				
				successFlag =updateEmployeeHist(preparedStatementInsert,connection,gpfOrCpsNo,employeeId,"C");
				if(successFlag)
					connection.commit();
				log.debug(this.getClass().getName() + "- Exit ");
	         }catch (SQLException e) {
	        	 log.error(this.getClass().getName() + "- updateEmployeeDetails "+e.getMessage());
	             try {
	            	 connection.rollback();
	             }catch (SQLException e1) { 
	            	 log.error(this.getClass().getName() + "- updateEmployeeDetails "+e1.getMessage());
	              }
	          }finally{
	        	  try {
	        		  if(preparedStatementUpdate != null) {
	        			  preparedStatementUpdate.close();
			          }
	        		  if(preparedStatementUpdate1 != null) {
	        			  preparedStatementUpdate1.close();
			          }
	        		  if(preparedStatementUpdate2 != null) {
	        			  preparedStatementUpdate2.close();
			          }
	        		  if(preparedStatementInsert != null) {
	        			  preparedStatementInsert.close();
			          }
			          if(connection != null) {
			        	  connection.close();
	                  }
	              }catch (SQLException ex) {
	            	  log.error(this.getClass().getName() + "- updateEmployeeDetails "+ex.getMessage());
	               }
	           }
	           return successFlag;
		}
	
	public boolean updateEmployeeHist( PreparedStatement preparedStatementInsert,Connection connection,String gpfOrCpsNo,Long employeeId,String mode){
		boolean successFlag =false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			DFWEmployeeForm employeeDetails=getEmployeeDetails(employeeId);
			DFWEmployeeForm qualificationDetails=getQualificationDetails(employeeId);
			Promotion promotionDetails=getPromotionDetails(employeeId);
			DFWEmployeeForm leaveDetails=getLeaveDetails(employeeId);
			preparedStatementInsert = connection.prepareStatement(ShdrcQueryList.INSERT_DFW_EMPLOYEE_HISTORY);
			
			java.sql.Date  dateOfRegularise=null;
			if(Util.isNotNull(employeeDetails.getDateOfRegularisation()))
				dateOfRegularise=new java.sql.Date(Util.getUtilDate(employeeDetails.getDateOfRegularisation()).getTime());
			java.sql.Date  dateOfProbationDeclare=null;
			if(Util.isNotNull(employeeDetails.getDateOfProbationDecleration()))
				dateOfProbationDeclare=new java.sql.Date(Util.getUtilDate(employeeDetails.getDateOfProbationDecleration()).getTime());
			java.sql.Date  dateOfRetire=null;
			if(Util.isNotNull(employeeDetails.getDateOfRetirement()))
				dateOfRetire=new java.sql.Date(Util.getUtilDate(employeeDetails.getDateOfRetirement()).getTime());
			java.sql.Date  slsDue=null;
			if(Util.isNotNull(employeeDetails.getSlsDueDate()))
				slsDue=new java.sql.Date(Util.getUtilDate(employeeDetails.getSlsDueDate()).getTime());
			
			java.sql.Date  ELFrom=null;
			java.sql.Date  ELTo=null;
			java.sql.Date  MLFrom=null;
			java.sql.Date  MLTo=null;
			java.sql.Date  UELFrom=null;
			java.sql.Date  UELTo=null;
			Integer balanceEL=null;
			Integer balanceML=null;
			Integer balanceUEL=null;
			Integer elTakenDays=null;
			Integer mlTakenDays=null;
			Integer uelTakenDays=null;
			
			if(leaveDetails!=null){
				if(Util.isNotNull(leaveDetails.getELTakenFrom()))
					ELFrom=new java.sql.Date(Util.getUtilDate(leaveDetails.getELTakenFrom()).getTime());			
				
				if(Util.isNotNull(leaveDetails.getELTakenTo()))
					ELTo=new java.sql.Date(Util.getUtilDate(leaveDetails.getELTakenTo()).getTime());			
				
				if(Util.isNotNull(leaveDetails.getMLTakenFrom()))
					MLFrom=new java.sql.Date(Util.getUtilDate(leaveDetails.getMLTakenFrom()).getTime());			
				
				if(Util.isNotNull(leaveDetails.getMLTakenTo()))
					MLTo=new java.sql.Date(Util.getUtilDate(leaveDetails.getMLTakenTo()).getTime());			
				
				if(Util.isNotNull(leaveDetails.getUELTakenFrom()))
					UELFrom=new java.sql.Date(Util.getUtilDate(leaveDetails.getUELTakenFrom()).getTime());			
				
				if(Util.isNotNull(leaveDetails.getUELTakenTo()))
					UELTo=new java.sql.Date(Util.getUtilDate(leaveDetails.getUELTakenTo()).getTime());
				
				elTakenDays =leaveDetails.getELTakenDays();
				mlTakenDays =leaveDetails.getMLTakenDays();
				uelTakenDays =leaveDetails.getUELTakenDays();
				balanceEL=leaveDetails.getBalanceEL();
				balanceML=leaveDetails.getBalanceML();
				balanceUEL=leaveDetails.getBalanceUEL();
			}	
			
			java.sql.Date promotionDOJ =null;
			String promotionType=null;
			String promotion=null;
			
			if(promotionDetails!=null){
				promotionType=promotionDetails.getPromotionType();
				promotion=promotionDetails.getPromotion();
				if(Util.isNotNull(promotionDetails.getPromotionDOJ()))
					promotionDOJ=new java.sql.Date(Util.getUtilDate(promotionDetails.getPromotionDOJ()).getTime());
			}
			
			preparedStatementInsert.setLong(1,employeeId);
			preparedStatementInsert.setString(2,employeeDetails.getEmployeeName());
			preparedStatementInsert.setInt(3,employeeDetails.getDistrictId());
			preparedStatementInsert.setString(4,employeeDetails.getGpfOrCpsNo());
			preparedStatementInsert.setString(5,employeeDetails.getGender());
			preparedStatementInsert.setString(6,employeeDetails.getDesignation());
			preparedStatementInsert.setString(7,employeeDetails.getPayBand());
			preparedStatementInsert.setString(8,employeeDetails.getGroupCategory());
			preparedStatementInsert.setInt(9,Integer.valueOf(employeeDetails.getDutyPay()));
			preparedStatementInsert.setString(10,employeeDetails.getRecruitedBy());
			preparedStatementInsert.setInt(11,Integer.valueOf(employeeDetails.getGradePay()));
			preparedStatementInsert.setString(12,employeeDetails.getCommunity());
			preparedStatementInsert.setDate(13,new java.sql.Date (Util.getUtilDate(employeeDetails.getDobDate()).getTime()));
			preparedStatementInsert.setString(14,employeeDetails.getFirstAppointmentPost());
			preparedStatementInsert.setDate(15,new java.sql.Date (Util.getUtilDate(employeeDetails.getFirstAppointmentDOJ()).getTime()));
			preparedStatementInsert.setDate(16,dateOfRegularise);
			preparedStatementInsert.setDate(17,dateOfProbationDeclare);
			preparedStatementInsert.setDate(18,dateOfRetire);
			preparedStatementInsert.setString(19,employeeDetails.getIncrementDueMonth());
			preparedStatementInsert.setDate(20,slsDue);
			preparedStatementInsert.setString(21,employeeDetails.getPhysicalStatus());
			preparedStatementInsert.setString(22,employeeDetails.getPunishments());			
			preparedStatementInsert.setString(23,employeeDetails.getNomineeDetails());
			preparedStatementInsert.setString(24,qualificationDetails.getQualification());
			preparedStatementInsert.setString(25,qualificationDetails.getUgMajor());
			preparedStatementInsert.setString(26,qualificationDetails.getPgMajor());
			preparedStatementInsert.setString(27,qualificationDetails.getOthers());
			
				preparedStatementInsert.setDate(28,ELFrom);
				preparedStatementInsert.setDate(29,ELTo);
				if(elTakenDays==null)
					preparedStatementInsert.setNull(30,java.sql.Types.INTEGER);
				else
					preparedStatementInsert.setInt(30,elTakenDays);
				if(balanceEL==null)
					preparedStatementInsert.setNull(31,java.sql.Types.INTEGER);
				else
					preparedStatementInsert.setInt(31,balanceEL);
				preparedStatementInsert.setDate(32,MLFrom);
				preparedStatementInsert.setDate(33,MLTo);
				if(mlTakenDays==null)
					preparedStatementInsert.setNull(34,java.sql.Types.INTEGER);
				else
				preparedStatementInsert.setInt(34,mlTakenDays);
				if(balanceML==null)
					preparedStatementInsert.setNull(35,java.sql.Types.INTEGER);
				else
				preparedStatementInsert.setInt(35,balanceML);
				preparedStatementInsert.setDate(36,UELFrom);
				preparedStatementInsert.setDate(37,UELTo);
				if(uelTakenDays==null)
					preparedStatementInsert.setNull(38,java.sql.Types.INTEGER);
				else
				preparedStatementInsert.setInt(38,uelTakenDays);
				if(balanceUEL==null)
					preparedStatementInsert.setNull(39,java.sql.Types.INTEGER);
				else
				preparedStatementInsert.setInt(39,balanceUEL);	
				
				
			preparedStatementInsert.setString(40,promotionType);
			preparedStatementInsert.setString(41,promotion);
			preparedStatementInsert.setDate(42,promotionDOJ);
			preparedStatementInsert.setString(43,mode);		
			preparedStatementInsert.setInt(44,employeeDetails.getInstitutionId());
			preparedStatementInsert.setString(45,employeeDetails.getTransferStatus());
			preparedStatementInsert.setString(46,employeeDetails.getRetirementType());
			preparedStatementInsert.setString(47,employeeDetails.getTransferType());
			preparedStatementInsert.setString(48,employeeDetails.getWorkingLocation());
			preparedStatementInsert.setString(49,employeeDetails.getMaritalStatus());
			java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
			preparedStatementInsert.setTimestamp(50,addOnDate);
			preparedStatementInsert.setString(51,UserInfo.getLoggedInUserName());			
			preparedStatementInsert.execute();
			
			successFlag=true;
			log.debug(this.getClass().getName() + "- Exit ");
		}catch(SQLException e){
			log.error(this.getClass().getName() + "- updateEmployeeHist "+e.getMessage());
		}		
		
		return successFlag;
	}
	
	public boolean isEmployeeLeaveExists(Long employeeId){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		boolean isEmployeeLeaveExists=false;
		  ResultSet resultSet =null;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_LEAVE_EXISTS);
			preparedStatement.setLong(1,employeeId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	long employeeCount=resultSet.getLong(1);
	        	isEmployeeLeaveExists =(employeeCount>0);
	        }log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- isEmployeeLeaveExists "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- isEmployeeLeaveExists "+e.getMessage());
			}
		}finally{
				try {
	                if (preparedStatement != null) {
	                	preparedStatement.close();
	                }
	                if (connection != null) {
	                	connection.close();
	                }
	
	            } catch (SQLException ex) {
	            }
			}
		return isEmployeeLeaveExists;		
	}

	public boolean transferEmployee(String gpfOrCpsNo,Integer districtId,Long employeeId,Integer institutionId,String transferType,String PromotionName){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		PreparedStatement preparedStatementInsert =null;
		PreparedStatement preparedStatement =null;
		 ResultSet resultSet =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			java.sql.Timestamp updatedOnDate = new java.sql.Timestamp(new Date().getTime());
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_TRANSFER);
			preparedStatementUpdate.setInt(1, districtId);
			preparedStatementUpdate.setInt(2, institutionId);
			preparedStatementUpdate.setString(3, transferType );
			preparedStatementUpdate.setString(4, "B");
			preparedStatementUpdate.setTimestamp(5, updatedOnDate);
			preparedStatementUpdate.setString(6, gpfOrCpsNo);	
			preparedStatementUpdate.executeUpdate();
			
			if(transferType.equalsIgnoreCase("P")){
				preparedStatement=connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_PROMOTION_TYPE);
				String promotionType=null;	
				String PromotionTypeLevel=null;	
				preparedStatement.setLong(1,employeeId);
				resultSet = preparedStatement.executeQuery();
				 
				while (resultSet.next()) {
			        promotionType=resultSet.getString(1);
					if(promotionType!=null){
						PromotionTypeLevel =promotionType.substring(promotionType.length()-1,promotionType.length());
						promotionType="Promotion"+(PromotionTypeLevel+1);
					}
				}
				
				promotionType="Promotion1";		
				
				preparedStatementInsert=connection.prepareStatement(ShdrcQueryList.INSERT_DFW_EMPLOYEE_PROMOTION);
				preparedStatementInsert.setLong(1,employeeId);
				preparedStatementInsert.setString(2,promotionType);
				preparedStatementInsert.setString(3,PromotionName);
				preparedStatementInsert.setDate(4,null);
				java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
				preparedStatementInsert.setTimestamp(5,addOnDate);
				preparedStatementInsert.setString(6,UserInfo.getLoggedInUserName());					
				preparedStatementInsert.execute();
			}			
			successFlag =updateEmployeeHist(preparedStatementUpdate,connection,gpfOrCpsNo,employeeId,"T");
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- transferEmployee "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- transferEmployee "+e1.getMessage());
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

	public List<CommonStringList> getTransferInstitutionList(Integer districtId){
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
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_TRANSFER_INSTITUTION);
			preparedStatement.setInt(1,districtId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	institutionList.add(commonStringList);
	        }log.debug(this.getClass().getName() + "- Exit ");
		} catch (SQLException e) {
			log.error(this.getClass().getName() + "- getTransferInstitutionList "+e.getMessage());
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

	public DFWEmployeeForm getTransferEmployeeDetails(Long employeeId,Integer districtId,Integer institutionId){
		DFWEmployeeForm transferEmployeeDetails=null;
		Connection connection = null;
	    PreparedStatement preparedStatement =null;
	    ResultSet resultSet =null;
	try {
		log.debug(this.getClass().getName() + "- Entering ");
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.DFW_TRANSFER_EMPLOYEE_DETAILS);
		preparedStatement.setLong(1,employeeId);
		preparedStatement.setInt(2,districtId);
		preparedStatement.setInt(3,institutionId);
		preparedStatement.setString(4,"B");
		resultSet = preparedStatement.executeQuery();
       
		 while (resultSet.next()) {
    	transferEmployeeDetails =new DFWEmployeeForm();
    	transferEmployeeDetails.setEmployeeName(resultSet.getString(1));
    	transferEmployeeDetails.setGpfOrCpsNo(resultSet.getString(2));
    	transferEmployeeDetails.setDesignation(resultSet.getString(3));
    	String transferType=resultSet.getString(4);
    	transferEmployeeDetails.setTransferType(transferType);
    	transferEmployeeDetails.setDistrictName(resultSet.getString(5));
    	transferEmployeeDetails.setInstitutionName(resultSet.getString(6));
    	if(transferType.equalsIgnoreCase("P")){
        	transferEmployeeDetails.setPromotion1(resultSet.getString(7));
    	}
    	transferEmployeeDetails.setPayBand((resultSet.getString(8)));
    	transferEmployeeDetails.setGroupCategory(resultSet.getString(9));
    	transferEmployeeDetails.setGradePay(resultSet.getString(10));
    	transferEmployeeDetails.setWorkingLocation(resultSet.getString(11));
		 }
		 log.debug(this.getClass().getName() + "- Exit ");
	} catch (SQLException e) {
		log.error(this.getClass().getName() + "- getTransferEmployeeDetails "+e.getMessage());
	}
	finally{
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
	return transferEmployeeDetails;
	}
	
	public boolean acceptEmployee(String gpfOrCpsNo,Integer districtId,Long employeeId,Integer institutionId,String transferType,
			String previousTransferType,String PromotionName,String promotionDate,String payBand,String groupCategory,Integer gradePay,String workingLocation){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		PreparedStatement preparedStatement =null;
		PreparedStatement preparedStatementInsert =null;
		 ResultSet resultSet =null;
		boolean successFlag=false;
		try {
			log.debug(this.getClass().getName() + "- Entering ");
			connection=jdbcTemplate.getDataSource().getConnection();
			java.sql.Timestamp updatedOnDate = new java.sql.Timestamp(new Date().getTime());
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_TRANSFER_ACCEPT);
			
			preparedStatementUpdate.setInt(1, districtId);
			preparedStatementUpdate.setInt(2, institutionId);
			preparedStatementUpdate.setString(3, transferType );
			preparedStatementUpdate.setString(4, "A");
			preparedStatementUpdate.setTimestamp(5, updatedOnDate);
			preparedStatementUpdate.setString(6, payBand);
			preparedStatementUpdate.setString(7, groupCategory);
			preparedStatementUpdate.setInt(8, gradePay);
			preparedStatementUpdate.setString(9, workingLocation);
			preparedStatementUpdate.setString(10, gpfOrCpsNo);
			preparedStatementUpdate.executeUpdate();
			
			if((transferType.equalsIgnoreCase("P")) && (previousTransferType.equalsIgnoreCase("P")) || (transferType.equalsIgnoreCase("T")) && (previousTransferType.equalsIgnoreCase("P"))){
				preparedStatement=connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_PROMOTION_TYPE);
				String promotionType=null;	
				preparedStatement.setLong(1,employeeId);
				resultSet = preparedStatement.executeQuery();
				 while (resultSet.next()) {
			        promotionType=resultSet.getString(1);
			        if(promotionType!=null){
			        	preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_DFW_EMPLOYEE_PROMOTION);
			        	preparedStatementUpdate.setString(1,PromotionName);
			        	preparedStatementUpdate.setDate(2,new java.sql.Date (Util.getUtilDate(promotionDate).getTime()));
			        	preparedStatementUpdate.setLong(3,employeeId);
			        	preparedStatementUpdate.setString(4,promotionType);	
			        	preparedStatementUpdate.executeUpdate();			        
			        }
				 }
			}
		        else if((transferType.equalsIgnoreCase("P")) && (previousTransferType.equalsIgnoreCase("T"))){		
		        	preparedStatement=connection.prepareStatement(ShdrcQueryList.DFW_EMPLOYEE_PROMOTION_TYPE);
					String promotionType=null;	
					String PromotionTypeLevel=null;	
					preparedStatement.setLong(1,employeeId);
					resultSet = preparedStatement.executeQuery();
					 
					while (resultSet.next()) {
				        promotionType=resultSet.getString(1);
						if(promotionType!=null){
							PromotionTypeLevel =promotionType.substring(promotionType.length()-1,promotionType.length());
							promotionType="Promotion"+(PromotionTypeLevel+1);
						}
					}
					
					promotionType="Promotion1";		
					
					preparedStatementInsert=connection.prepareStatement(ShdrcQueryList.INSERT_DFW_EMPLOYEE_PROMOTION);
					preparedStatementInsert.setLong(1,employeeId);
					preparedStatementInsert.setString(2,promotionType);
					preparedStatementInsert.setString(3,PromotionName);
					preparedStatementInsert.setDate(4,new java.sql.Date (Util.getUtilDate(promotionDate).getTime()));
					java.sql.Timestamp addOnDate = new java.sql.Timestamp(new Date().getTime());
					preparedStatementInsert.setTimestamp(5,addOnDate);
					preparedStatementInsert.setString(6,UserInfo.getLoggedInUserName());					
					preparedStatementInsert.execute();
				}
			
			successFlag =updateEmployeeHist(preparedStatementUpdate,connection,gpfOrCpsNo,employeeId,"A");
			successFlag=true;
			connection.commit();
			log.debug(this.getClass().getName() + "- Exit ");
	    } catch (SQLException e) {
	    	log.error(this.getClass().getName() + "- acceptEmployee "+e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(this.getClass().getName() + "- acceptEmployee "+e1.getMessage());
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
