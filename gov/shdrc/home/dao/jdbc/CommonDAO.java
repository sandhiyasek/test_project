package gov.shdrc.home.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import gov.shdrc.dataentry.fileupload.File;
import gov.shdrc.home.dao.ICommonDAO;
import gov.shdrc.sms.SmsAlert;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.SplCharsConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;


@Service
public class CommonDAO implements ICommonDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<CommonStringList> getDirecorateList(boolean isAllDirectorate,String role){
		List<CommonStringList> direcorateList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			direcorateList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			//preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_DIRECTORATE_MASTER);
			String sqlQuery=null;
			if(isAllDirectorate)
				//sqlQuery="select \"Directorate_Id\",\"Directorate_Name\" from shdrc_dwh.\"Directorate_Master_Dim\" where \"Directorate_Id\" in (1,2,3,4,5,6,7,10,13,17) order by \"Directorate_Name\" asc";
				sqlQuery="select \"Directorate_Id\",\"Directorate_Name\" from shdrc_dwh.\"Directorate_Master_Dim\" order by \"Directorate_Name\" asc";
			else
				sqlQuery="select \"Directorate_Id\",\"Directorate_Name\" from shdrc_dwh.\"Directorate_Master_Dim\" where \"Directorate_Name\" in ("+role+")  order by \"Directorate_Name\" asc";
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	CommonStringList commonStringList = new CommonStringList();
		        	commonStringList.setId(resultSet.getInt(1));
		        	commonStringList.setName(resultSet.getString(2));
		        	direcorateList.add(commonStringList);
	        	}	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return direcorateList;
	}
	
	public JSONArray getDirecorateServletList(){
		JSONArray direcorateServletList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			direcorateServletList=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_DIRECTORATE_SERVLET);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	direcorateServletList.put(resultSet.getInt(1),resultSet.getString(2));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return direcorateServletList;
	}
	
	public List<CommonStringList> getDemographicList(){
		List<CommonStringList> demographicList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			demographicList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.SELECT_DEMOGRAPHIC_MASTER);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	String gender=resultSet.getString(2);
	        	String ageGroup=resultSet.getString(3);
	        	String social=resultSet.getString(4);
	        	if(Util.isNotNull(gender)&&Util.isNotNull(ageGroup)&&Util.isNotNull(social)){
	        		commonStringList.setId(resultSet.getInt(1));
	        		commonStringList.setName(gender+"-"+ageGroup+"-"+social);
	        		demographicList.add(commonStringList);
	        	}else if(Util.isNotNull(gender)&&Util.isNotNull(ageGroup)){
	        		commonStringList.setId(resultSet.getInt(1));
	        		commonStringList.setName(gender+"-"+ageGroup);
	        		demographicList.add(commonStringList);
	        	}else if(Util.isNotNull(ageGroup)&&Util.isNotNull(social)){
	        		commonStringList.setId(resultSet.getInt(1));
	        		commonStringList.setName(ageGroup+"-"+social);
	        		demographicList.add(commonStringList);
	        	}
	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return demographicList;
	}
	
	public List<CommonStringList> getDistricts(String userName){
		List<CommonStringList> districtsList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			districtsList=new ArrayList<CommonStringList>();
			boolean accessFlag = isDistrictsFUllAccess(userName);
			int isUserIndex=userName.indexOf("_");
			String districtQuery=null;
			if(isUserIndex!=-1){
				String dirShortName=userName.substring(0,isUserIndex);
				if("dms".equalsIgnoreCase(dirShortName)){
					districtQuery=ShdrcQueryList.DMS_DISTRICT_NAME;
				}else if("dme".equalsIgnoreCase(dirShortName)){
					districtQuery=ShdrcQueryList.DME_DISTRICT_NAME;
				}else{
					districtQuery=ShdrcQueryList.DISTRICT_NAME;
				}
			}else{
				districtQuery=ShdrcQueryList.DISTRICT_NAME;
			}
			
			if(accessFlag){
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.STATELEVEL);
				districtsList.add(commonStringList);
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(districtQuery);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	CommonStringList CommonStringList = new CommonStringList();
		        	CommonStringList.setId(resultSet.getInt(1));
		        	CommonStringList.setName(resultSet.getString(2));
		        	districtsList.add(CommonStringList);
	        	}	
	        }
	        if(districtsList.size()==0)
	        	districtsList=null;
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public List<CommonStringList> getGeneralTypeList(String query,String catogory,boolean isRequired){
		List<CommonStringList> generalTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			generalTypeList=new ArrayList<CommonStringList>();
			CommonStringList naObj = new CommonStringList();
			naObj.setId(ShdrcConstants.NAID);
			naObj.setName(ShdrcConstants.NAVALUE);
			generalTypeList.add(naObj);
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(query);
			if(isRequired)
				preparedStatement.setString(1,catogory);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList =new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	generalTypeList.add(commonStringList);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return generalTypeList;
	}
	
	public boolean isDistrictsFUllAccess(String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		PreparedStatement preparedStatement1 =null;
		ResultSet resultSet =null;
		ResultSet resultSet1 =null;
		int totalNoOfUserAccessDistricts;
		int totalNoOfDistricts;
		boolean flag=false;
		try {
			totalNoOfUserAccessDistricts=0;
			totalNoOfDistricts=0;
			connection=jdbcTemplate.getDataSource().getConnection();
			int isUserIndex=userName.indexOf("_");
			String districtRestrictedtQuery=null;
			if(isUserIndex!=-1){
				String dirShortName=userName.substring(0,isUserIndex);
				if("dms".equalsIgnoreCase(dirShortName)){
					districtRestrictedtQuery=ShdrcQueryList.DMS_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT;
				}else if("dme".equalsIgnoreCase(dirShortName)){
					districtRestrictedtQuery=ShdrcQueryList.DME_ACCESS_RESTRICTED_DISTRICT_NAME_COUNT;
				}else{
					districtRestrictedtQuery=ShdrcQueryList.ACCESS_RESTRICTED_DISTRICT_NAME_COUNT;;
				}
			}else{
				districtRestrictedtQuery=ShdrcQueryList.ACCESS_RESTRICTED_DISTRICT_NAME_COUNT;
			}
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DISTRICT_NAME_COUNT);
			resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	        	totalNoOfDistricts = resultSet.getInt(1);
	        }
			preparedStatement1 = connection.prepareStatement(districtRestrictedtQuery);
			preparedStatement1.setString(1,userName);
			preparedStatement1.setString(2,userName);
			preparedStatement1.setString(3,userName);
			preparedStatement1.setString(4,userName);
			resultSet1 = preparedStatement1.executeQuery();
	        if (resultSet1.next()) {
	        	totalNoOfUserAccessDistricts = resultSet1.getInt(1);
	        }
			flag=(totalNoOfDistricts==totalNoOfUserAccessDistricts);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}finally{
				try {
	                if (resultSet != null) {
	                    resultSet.close();
	                }
	                if (resultSet1 != null) {
	                    resultSet1.close();
	                }
	                if (preparedStatement != null) {
	                    preparedStatement.close();
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
	
	public String getMessageAlertMobileNo(String directorateIds,boolean isAllDirectorate){
		StringBuilder mobileNoList=null;
		String mobileNo=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			mobileNoList=new StringBuilder();
			String sqlQuery=null;
			if(isAllDirectorate)
				//sqlQuery="select \"Directorate_Id\",\"Directorate_Name\" from shdrc_dwh.\"Directorate_Master_Dim\" where \"Directorate_Id\" in (1,2,3,4,5,6,7,10,13,17) order by \"Directorate_Name\" asc";
				sqlQuery="select userMast.\"Mobile\" from shdrc_dwh.\"User_Master\" userMast JOIN shdrc_dwh.\"User_Tier_Master\" tier ON userMast.\"Tier\"=tier.\"Tier_Id\" JOIN shdrc_dwh.\"User_Org_Group\" userGrp ON tier.\"OG_Id\"=userGrp.\"OG_Id\" where tier.\"Tier_Cd\" IN ('T1','T2','T3')";
			else
				sqlQuery="select userMast.\"Mobile\" from shdrc_dwh.\"User_Master\" userMast JOIN shdrc_dwh.\"User_Tier_Master\" tier ON userMast.\"Tier\"=tier.\"Tier_Id\" JOIN shdrc_dwh.\"User_Org_Group\" userGrp ON tier.\"OG_Id\"=userGrp.\"OG_Id\" where tier.\"Tier_Cd\" IN ('T1','T2','T3') and userMast.\"Directorate_Id\" IN ('"+directorateIds+"')";
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	mobileNoList.append(resultSet.getLong(1));
	        	mobileNoList.append(SplCharsConstants.COMMA);
	        }
	        
	        if(mobileNoList.length()>0){
	        	mobileNo=mobileNoList.substring(0,mobileNoList.length()-1);
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return mobileNo;
	}
	public List<SmsAlert> getSmsAlertList(Integer directorateId){
		List<SmsAlert> smsAlertList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			smsAlertList=new ArrayList<SmsAlert>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.GET_SMS_INFO);
			preparedStatement.setInt(1,directorateId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	SmsAlert smsAlert = new SmsAlert();
	        	smsAlert.setSmsId(resultSet.getLong(1));
	        	smsAlert.setDirectorateName(resultSet.getString(2));
	        	smsAlert.setDrillDownLevlName(resultSet.getString(3));
	        	smsAlert.setIndicatorName(resultSet.getString(4));
	        	smsAlert.setIndicatorValue(resultSet.getLong(5));
	        	smsAlert.setTargetValue(resultSet.getLong(6));
	        	smsAlert.setThresholdComparator(resultSet.getString(7));
	        	smsAlert.setMobileNo(resultSet.getLong(8));
	        	smsAlertList.add(smsAlert);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return smsAlertList;
	}
	
	public List<SmsAlert> getSmsAlertList(){
		List<SmsAlert> smsAlertList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			smsAlertList=new ArrayList<SmsAlert>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.GET_CRON_TRIGER_SMS_INFO);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	SmsAlert smsAlert = new SmsAlert();
	        	smsAlert.setSmsId(resultSet.getLong(1));
	        	smsAlert.setDirectorateName(resultSet.getString(2));
	        	smsAlert.setDrillDownLevlName(resultSet.getString(3));
	        	smsAlert.setIndicatorName(resultSet.getString(4));
	        	smsAlert.setIndicatorValue(resultSet.getLong(5));
	        	smsAlert.setTargetValue(resultSet.getLong(6));
	        	smsAlert.setThresholdComparator(resultSet.getString(7));
	        	smsAlert.setMobileNo(resultSet.getLong(8));
	        	smsAlert.setHierarchyName(resultSet.getString(9));
	        	smsAlert.setMonth(resultSet.getString(10));
	        	smsAlert.setYear(resultSet.getInt(11));
	        	smsAlertList.add(smsAlert);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return smsAlertList;
	}
	
	public boolean updateSmsAlert(Long smsId){
		Connection connection = null;
		PreparedStatement preparedStatementUpdate =null;
		boolean successFlag=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			connection.setAutoCommit(false);
			preparedStatementUpdate=connection.prepareStatement(ShdrcQueryList.UPDATE_SMS_ALERT);
			preparedStatementUpdate.setLong(1,smsId);
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
	
	public boolean addFileUploadAuditLog(Integer directorateId,String hierarchyName1,String hierarchyName2,String fileName,
			String frequency,String userName,String searchDate,String week,String month,String quarter,Integer year){
		Connection connection = null;
		PreparedStatement preparedStatementInsert =null;
		boolean successFlag=false;
		try {
			if(ShdrcConstants.DAILY.equalsIgnoreCase(frequency) || ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
				week=null;
				quarter=null;
			}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
				quarter=null;
			}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
				week=null;
				month=null;
			}else if(ShdrcConstants.YEARLY.equalsIgnoreCase(frequency)){
				week=null;
				quarter=null;
				month=null;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			//To add HUD and Location Id in File Upload Audit table 
			if(directorateId==16){
				preparedStatementInsert=connection.prepareStatement(ShdrcQueryList.ADD_FILE_UPLOAD_AUDIT_DFS);
			}else if(directorateId==7){
				preparedStatementInsert=connection.prepareStatement(ShdrcQueryList.ADD_FILE_UPLOAD_AUDIT_MA);
			}
			//To add District and Institution Id in File Upload Audit table
			else{
				preparedStatementInsert=connection.prepareStatement(ShdrcQueryList.ADD_FILE_UPLOAD_AUDIT);
			}
			preparedStatementInsert.setInt(1,directorateId);
			preparedStatementInsert.setInt(2,(Util.isNotNull(hierarchyName1)?Integer.parseInt(hierarchyName1):-99));
			preparedStatementInsert.setInt(3,(Util.isNotNull(hierarchyName2)?Integer.parseInt(hierarchyName2):-99));
			preparedStatementInsert.setString(4,fileName);
			preparedStatementInsert.setString(5,frequency);
			java.sql.Date  sqlDate=null;
			if(Util.isNotNull(searchDate))
				sqlDate=new java.sql.Date(Util.getUtilDate(searchDate).getTime());
			preparedStatementInsert.setDate(6,sqlDate);
			preparedStatementInsert.setString(7,week);
			preparedStatementInsert.setString(8,quarter);
			month = Util.isNullOrBlank(month)?null:month;
			preparedStatementInsert.setString(9,month);
			if(year==null)
				preparedStatementInsert.setNull(10,java.sql.Types.INTEGER);
			else
				preparedStatementInsert.setInt(10,year);
			preparedStatementInsert.setString(11,userName);
			preparedStatementInsert.execute();
			successFlag=true;
	    } catch (SQLException e) {
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
	
	public List<CommonStringList> getClassificationList(Integer directorateId,String query){
		List<CommonStringList> classificationList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			classificationList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, directorateId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setName(resultSet.getString(1));
	        	classificationList.add(CommonStringList);
	        }
	        if(classificationList.size()==0)
	        	classificationList=null;
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<CommonStringList> getGeneralCategoryListByFrequency(int directorateId,String frequency,String dataEntryLevel,String query){
		List<CommonStringList> generalCategoryList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			generalCategoryList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2,Util.getFrequencyShortName(frequency));
			if(directorateId==ShdrcConstants.DirectorateId.DFW 
					|| directorateId==ShdrcConstants.DirectorateId.RNTCP 
					|| directorateId==ShdrcConstants.DirectorateId.SHTO
					|| directorateId==ShdrcConstants.DirectorateId.TANSACS
					|| directorateId==ShdrcConstants.DirectorateId.DFS
					|| directorateId==ShdrcConstants.DirectorateId.DCA
					|| directorateId==ShdrcConstants.DirectorateId.DRMGR){
				preparedStatement.setString(3,SplCharsConstants.PERCENTAGE+dataEntryLevel+SplCharsConstants.PERCENTAGE);
			}
			
			if(directorateId==ShdrcConstants.DirectorateId.MA)
				preparedStatement.setString(3,dataEntryLevel);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList CommonStringList = new CommonStringList();
	        	CommonStringList.setName(resultSet.getString(1));
	        	generalCategoryList.add(CommonStringList);
	        }
	        if(generalCategoryList.size()==0)
	        	generalCategoryList=null;
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return generalCategoryList;
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel,String query){
		Connection connection = null;
		JSONArray jsonArray= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			if(ShdrcConstants.DirectorateId.DFW==directorateId && ("HR".equalsIgnoreCase(classificationName) || "Grants".equalsIgnoreCase(classificationName)))
				query="select \"Indicator_Id\",\"General_Id\",\"Indicator_Name\",\"General_Type\",\"General_Name\",\"General_SubCategory\",null,\"Is_Demography\",null,null " +
						"from shdrc_dwh.\"Vw_UDSM_DFW\" where \"Directorate_Id\"=? and \"General_Category\"=? and \"Indicator_Frequency\"=? and \"Is_Demography\"=? and \"General_Level\"in ("+generalNameLevel+")"+
						" order by \"General_SubCategory\",\"Indicator_Name\",\"General_Type\",\"General_Name\" asc";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, classificationName);
			preparedStatement.setString(3,Util.getFrequencyShortName(frequency));
			preparedStatement.setString(4,isDemographic);
			resultSet = preparedStatement.executeQuery();
			jsonArray = getGridRecordsFromRS(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public boolean isDemographicExists(int directorateId,String classificationName,String frequency,String query){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		boolean isDemographicExists=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.IS_DEMOGRAPHIC);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, classificationName);
			preparedStatement.setString(3,Util.getFrequencyShortName(frequency));
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if("Y".equalsIgnoreCase(resultSet.getString(1))){
	        		isDemographicExists=true;
	        	}
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return isDemographicExists;
	}
	
	public JSONArray getGridRecordsFromRS(ResultSet resultSet){
		JSONArray jsonArray=null;
		try {
			JSONObject jsonObject =null;
			jsonArray= new JSONArray();
			int previousIndicatorId=0;
			String previousGeneralType="";
			String previousGeneralName="";
			String previousSubCategory="";
			int indicatorId=0;
			int generalId=0;
			int transKey;
			String isCharacter=null;
			String etlProcessStatus=null;
			String indicatorName="";
			String indicatorValue="";
			String generalType="";
			String generalName="";
			String subCategory="";
			String isDemographic=null;
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			else
				role = ShdrcConstants.ALLDIRECTORATE;
			
			jsonArray = Util.getColumnHearders();
			while (resultSet.next()) {
	        	jsonObject = new JSONObject();
	        	indicatorId=resultSet.getInt(1);
	        	generalId=resultSet.getInt(2);
	        	indicatorName=resultSet.getString(3);
				generalType=resultSet.getString(4);
				generalName=resultSet.getString(5);
				subCategory=resultSet.getString(6);
				isDemographic = resultSet.getString(8);
				etlProcessStatus=resultSet.getString(9);
				transKey=resultSet.getInt(10);
				isCharacter=resultSet.getString(11);
				if(isCharacter==null || "N".equalsIgnoreCase(isCharacter)){
					indicatorValue=resultSet.getString(7);
				}else{
					indicatorValue=resultSet.getString(12);
				}
				jsonObject.put("userrole",role);
	        	jsonObject.put("indicatorid",indicatorId);
	        	jsonObject.put("generalid",generalId);
	        	jsonObject.put("subcategory",subCategory);
	        	jsonObject.put("indicatorvalue",indicatorValue);
	        	jsonObject.put("isdemographic",isDemographic);
	        	jsonObject.put("etlprocessstatus",etlProcessStatus);
	        	jsonObject.put("transactionkey",transKey);
	        	jsonObject.put("ischaracter",isCharacter);
	        	
	        	jsonObject.put("indicatorname1",indicatorName);
	        	jsonObject.put("generaltype1",generalType);
	        	jsonObject.put("generalname1",generalName);
	        	if(!previousSubCategory.equalsIgnoreCase(subCategory)&&!ShdrcConstants.NAVALUE.equalsIgnoreCase(subCategory)){
	        		jsonObject.put("indicatorname",subCategory);
	        		jsonObject.put("issucategorylabel",true);
	        		jsonObject.put("indicatorvalue","");
	        		jsonArray.put(jsonObject);
	        		
	        		jsonObject = new JSONObject();
	        		jsonObject.put("indicatorid",indicatorId);
		        	jsonObject.put("generalid",generalId);
		        	jsonObject.put("subcategory",subCategory);
		        	jsonObject.put("indicatorvalue",indicatorValue);
	        		jsonObject.put("indicatorname",indicatorName);
	        		if(!ShdrcConstants.NAVALUE.equalsIgnoreCase(generalType))
	        			jsonObject.put("generaltype",generalType);
	        		if(!ShdrcConstants.NAVALUE.equalsIgnoreCase(generalName))
	        			jsonObject.put("generalname",generalName);
		        	jsonObject.put("issucategorylabel",false);
		        	jsonObject.put("isdemographic",isDemographic);
		        	jsonObject.put("etlprocessstatus",etlProcessStatus);
		        	jsonObject.put("transactionkey",transKey);
		        	jsonObject.put("ischaracter",isCharacter);
		        	
		        	jsonObject.put("indicatorname1",indicatorName);
		        	jsonObject.put("generaltype1",generalType);
		        	jsonObject.put("generalname1",generalName);
		        	
		        	previousSubCategory=subCategory;
		        	previousIndicatorId=indicatorId;
		        	previousGeneralType=generalType;
		        	previousGeneralName=generalName;
	        	}else if(previousIndicatorId!=indicatorId && ShdrcConstants.NAID!=indicatorId){
	        		jsonObject.put("indicatorname",indicatorName);
	        		if(!ShdrcConstants.NAVALUE.equalsIgnoreCase(generalType)){
	        			jsonObject.put("generaltype",generalType);
	        			previousGeneralType=generalType;
	        		}	
	        		if(!ShdrcConstants.NAVALUE.equalsIgnoreCase(generalName)){
	        			jsonObject.put("generalname",generalName);
	        			previousGeneralName=generalName;
	        		}	
		        	previousIndicatorId=indicatorId;
		        	jsonObject.put("issucategorylabel",false);
	        	}else if(!previousGeneralType.equalsIgnoreCase(generalType)&&!ShdrcConstants.NAVALUE.equalsIgnoreCase(generalType)){
	        		jsonObject.put("generaltype",generalType);
		        	jsonObject.put("generalname",generalName);
		        	previousGeneralType=generalType;
		        	previousGeneralName=generalName;
		        	jsonObject.put("issucategorylabel",false);
	    		}else if(!previousGeneralName.equalsIgnoreCase(generalName)&&!ShdrcConstants.NAVALUE.equalsIgnoreCase(generalName)){
		        	jsonObject.put("generalname",generalName);
		        	previousGeneralName=generalName;
		        	jsonObject.put("issucategorylabel",false);
	    		}
	        	//jsonObject.put("indicatorvalue",resultSet.getString(7));
	        	jsonArray.put(jsonObject);
	        }
			if(jsonArray.length()==1){
				jsonArray=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception json){
			
		}
		return jsonArray;
	}
	public String getUserTier(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String userTier=null;
		String userName = UserInfo.getLoggedInUserName();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.GET_USER_TIER);
			preparedStatement.setString(1,userName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userTier = resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return userTier;
	}

	public String getUserAccessLevel(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String userAccessLevel=null;
		String userName = UserInfo.getLoggedInUserName();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.GET_USER_ACCESS_LEVEL);
			preparedStatement.setString(1,userName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userAccessLevel = resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
			return userAccessLevel;
	}
	
	public List<File> getFileList(boolean isAllDirectorate,String role,String userName){
		List<File> fileList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			fileList=new ArrayList<File>();
			connection=jdbcTemplate.getDataSource().getConnection();
			String sqlQuery=null;
			 if(Util.isNotNull(userName)){
				 StringBuilder struserName=new StringBuilder();
				 struserName.append(SplCharsConstants.SINGLEQUOTE+userName+SplCharsConstants.SINGLEQUOTE+SplCharsConstants.COMMA);
				 if(struserName.length()>0){
					 userName = struserName.substring(0, struserName.length() - 1) ;
					}
			  }
			if(isAllDirectorate)
				sqlQuery="SELECT dir.\"Directorate_Id\",\"File_Name\", \"File_Extension\", \"Frequency\" FROM shdrc_dwh.\"File_Master\" filemaster join shdrc_dwh.\"Directorate_Master_Dim\" dir on filemaster.\"Directorate_Id\"=dir.\"Directorate_Id\" where \"Is_Upload_Required\"='Y' ";
			
			else if(userName.contains("dme_de") || userName.contains("ma_de")|| userName.contains("tnmsc_de"))
				sqlQuery="SELECT dir.\"Directorate_Id\",\"File_Name\", \"File_Extension\", \"Frequency\" FROM shdrc_dwh.\"File_Master\" filemaster join shdrc_dwh.\"Directorate_Master_Dim\" dir on filemaster.\"Directorate_Id\"=dir.\"Directorate_Id\"  WHERE \"Directorate_Name\" in ("+role+") and \"Is_Upload_Required\"='Y' order by \"File_Name\" asc";
			else
				sqlQuery="SELECT dir.\"Directorate_Id\",\"File_Name\", \"File_Extension\", \"Frequency\" FROM shdrc_dwh.\"File_Master\" filemaster join shdrc_dwh.\"Directorate_Master_Dim\" dir on filemaster.\"Directorate_Id\"=dir.\"Directorate_Id\" LEFT JOIN (select UR.\"Role_Id\" from shdrc_dwh.\"User_Roles\" UR join shdrc_dwh.\"User_Role_Assoc\" URA on URA.\"Role_Id\" = UR.\"Role_Id\" join shdrc_dwh.\"User_Master\" UM on UM.\"User_Id\" = URA.\"User_Id\" and UM.\"User_Name\" =  ("+userName+")) \"User_Restrict_Zone\" ON filemaster.\"Role_Id\" = \"User_Restrict_Zone\".\"Role_Id\" WHERE \"Directorate_Name\" in ("+role+") and \"Is_Upload_Required\"='Y'and (\"User_Restrict_Zone\".\"Role_Id\" IS NOT NULL OR NOT EXISTS ( select UR.\"Role_Id\" from shdrc_dwh.\"User_Roles\" UR join shdrc_dwh.\"User_Role_Assoc\" URA on URA.\"Role_Id\" = UR.\"Role_Id\" join shdrc_dwh.\"User_Master\" UM on UM.\"User_Id\" = URA.\"User_Id\" and UM.\"User_Name\" = ("+userName+")))order by \"File_Name\" asc";
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	File file = new File();
		        	file.setDirectorateId(resultSet.getInt(1));
		        	file.setFileName(resultSet.getString(2)+SplCharsConstants.DOT+resultSet.getString(3));
		        	file.setFrequency(resultSet.getString(4));
		        	fileList.add(file);
	        	}	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
			return fileList;
	}
	
	public List<CommonStringList> getFileListBasedOnFrequency(Integer directorateId,String frequency,String userName){
		List<CommonStringList> fileList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			fileList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			if(directorateId==ShdrcConstants.DirectorateId.DME
					||directorateId==ShdrcConstants.DirectorateId.MA
					||directorateId==ShdrcConstants.DirectorateId.TNMSC){
				preparedStatement = connection.prepareStatement(ShdrcQueryList.DME_FILE_LIST_BASED_ON_FREQUENCY);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, frequency);
			}else{
				preparedStatement = connection.prepareStatement(ShdrcQueryList.FILE_LIST_BASED_ON_FREQUENCY);
				preparedStatement.setString(1, userName);
				preparedStatement.setInt(2, directorateId);
				preparedStatement.setString(3, frequency);
				preparedStatement.setString(4, userName);
			}	
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	CommonStringList commonStringList = new CommonStringList();
		        	commonStringList.setId(resultSet.getInt(1));
		        	commonStringList.setName(resultSet.getString(2)+SplCharsConstants.DOT+resultSet.getString(3));
		        	fileList.add(commonStringList);
	        	}	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
			return fileList;
	}
	
	public boolean isFileExists(Integer directorateId,Integer hierarchyId1,Integer hierarchyId2,String fileName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		boolean isFileExists=false;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
				if(ShdrcConstants.DAILY.equalsIgnoreCase(frequency)){
					java.sql.Date  sqlDate = new java.sql.Date(Util.getUtilDate(searchDate).getTime());
					preparedStatement = connection.prepareStatement(ShdrcQueryList.FILE_AUDIT_DAILY_RECORDS_COUNT);
					preparedStatement.setDate(6, sqlDate);
				}else if(ShdrcConstants.WEEKLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.FILE_AUDIT_WEEKLY_RECORDS_COUNT);
					preparedStatement.setString(6, week);
					preparedStatement.setString(7, month);
					preparedStatement.setInt(8, year);
				}else if(ShdrcConstants.MONTHLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.FILE_AUDIT_MONTHLY_RECORDS_COUNT);
					preparedStatement.setString(6, month);
					preparedStatement.setInt(7, year);
				}else if(ShdrcConstants.QUARTERLY.equalsIgnoreCase(frequency)){
					if(directorateId==7){
						preparedStatement = connection.prepareStatement(ShdrcQueryList.FILE_AUDIT_QUARTERLY_HUD_RECORDS_COUNT);
					}else{
						preparedStatement = connection.prepareStatement(ShdrcQueryList.FILE_AUDIT_QUARTERLY_RECORDS_COUNT);
					}	
					preparedStatement.setString(6, quarter);
					preparedStatement.setInt(7, year);
				}else if(ShdrcConstants.YEARLY.equalsIgnoreCase(frequency)){
					preparedStatement = connection.prepareStatement(ShdrcQueryList.FILE_AUDIT_YEARLY_RECORDS_COUNT);
					preparedStatement.setInt(6, year);
				}
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setInt(2,(Util.isNotNull(hierarchyId1)?hierarchyId1:-99));
				preparedStatement.setInt(3,(Util.isNotNull(hierarchyId2)?hierarchyId2:-99));
				preparedStatement.setString(4, frequency);
				preparedStatement.setString(5, SplCharsConstants.PERCENTAGE+fileName+SplCharsConstants.PERCENTAGE);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					int recordCount = resultSet.getInt(1);
					if(recordCount>0)
						isFileExists=true;
				}
			
	        
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return isFileExists;
		}
	
	public List<CommonStringList> getDirectorateListById(Integer directorateId){
		List<CommonStringList> directorateList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			directorateList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DIRECTORATE_LIST_BY_ID);
			preparedStatement.setInt(1, directorateId);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	CommonStringList commonStringList = new CommonStringList();
		        	commonStringList.setId(resultSet.getInt(1));
		        	commonStringList.setName(resultSet.getString(2));
		        	directorateList.add(commonStringList);
	        	}	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
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
	        
			return directorateList;
	}
	
}

