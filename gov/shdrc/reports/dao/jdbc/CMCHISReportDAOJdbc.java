/**
 * 
 */
package gov.shdrc.reports.dao.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import gov.shdrc.reports.dao.ICMCHISReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;

/**
 * @author Upendra G
 *
 * 
 */
@Service
public class CMCHISReportDAOJdbc implements ICMCHISReportDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_cmchis_year(?,?,?)");
		Array array=preparedStatement.getConnection().createArrayOf("text", indName.split(","));
		preparedStatement.setInt(1, directorateId);
		preparedStatement.setString(2, indCategory);
		preparedStatement.setArray(3, array);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {	
			resultList.add(resultSet.getInt(1));
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
		return resultList;
	}
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_cmchis_ind_name(?,?)");	
		preparedStatement.setInt(1, directorateId);
		preparedStatement.setString(2, category);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			CommonStringList listCommon=new CommonStringList();
			listCommon.setName(resultSet.getString(1));
			listCommon.setId(resultSet.getInt(2));
			resultList.add(listCommon);
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
		return resultList;
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_cmchis_ind_cat(?)");
		preparedStatement.setInt(1, directorateId);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {	
			resultList.add(resultSet.getString(1));
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
		return resultList;
	}
	public JSONArray getFreeHandZoneData(int directorateId,String category,String indName,int year){		
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray childList=null;			
			JSONArray parentList=new JSONArray();
			try {
				 childList=new JSONArray();
				 childList.put("State_Name"); 
				 childList.put("District_Name"); 
				 childList.put("HUD_Name"); 
				 childList.put("Institution_Name"); 
				 childList.put("Institution_Type");
				 childList.put("Indicator_Name");
				 childList.put("Indicator_Category");
				 childList.put("General_Type");
				 childList.put("General_Name");
				 childList.put("General_Category");
				 childList.put("File_Type");
				 childList.put("Actual_Status");
				 childList.put("Groupby_Status");
				 childList.put("Time_Month_Name");
				 childList.put("Time_Reg_Year");
				 childList.put("Ind_Value");
				 parentList.put(childList);
				 connection=jdbcTemplate.getDataSource().getConnection();				
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_cmchis_main(?,?,?,?)");
				 Array array=preparedStatement.getConnection().createArrayOf("text", indName.split(","));
				 preparedStatement.setInt(1, directorateId);
				 preparedStatement.setString(2, category);
				 preparedStatement.setArray(3, array);
				 preparedStatement.setInt(4, year);				
				 resultSet = preparedStatement.executeQuery();
				  while (resultSet.next()) {
					   childList=new JSONArray();
					    String State_Name=resultSet.getString(1);
					    String District_Name=resultSet.getString(2);
					    String HUD_Name=resultSet.getString(3);
					    String Institution_Name=resultSet.getString(4);
					    String Institution_Type=resultSet.getString(5);
						String Indicator_Name=resultSet.getString(6);
						String Indicator_Category=resultSet.getString(7);
						String General_Type=resultSet.getString(8);
						String General_Name=resultSet.getString(9);
						String General_Category=resultSet.getString(10);
						String File_Type=resultSet.getString(11);
						String Actual_Status=resultSet.getString(12);
						String Groupby_Status=resultSet.getString(13);
						String Time_Month_Name=resultSet.getString(14);
						int Time_Reg_Year=resultSet.getInt(15);
						int Ind_Value=resultSet.getInt(16);
						childList.put(State_Name);
						childList.put(District_Name);
						childList.put(HUD_Name);
						childList.put(Institution_Name);
						childList.put(Institution_Type);					    
						childList.put(Indicator_Name);					
						childList.put(Indicator_Category);						
						childList.put(General_Type);
						childList.put(General_Name);
						childList.put(General_Category);
						childList.put(File_Type);
						childList.put(Actual_Status);
						childList.put(Groupby_Status);
						childList.put(Time_Month_Name);
						childList.put(Time_Reg_Year);
						childList.put(Ind_Value);
						parentList.put(childList);
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
			        return parentList;
	}
	//Reports Zone
			public JSONArray getReportZoneData(Integer directorateId,String reportName,/*Integer fromYear,String fromMonth,Integer toYear,String toMonth,*/String userName){
				Connection connection = null;
				PreparedStatement preparedStatement =null;
				ResultSet resultSet =null;
				JSONArray childList=null;
				JSONArray parentList=new JSONArray();
		        try {
		        	childList=new JSONArray();
		        	
			        if("CMCHIS_Dengue".equalsIgnoreCase(reportName)){				        	
						childList.put("District_Name");
						childList.put("totalcount");
						childList.put("totalsum");
						childList.put("tn0407count");
						childList.put("tn0407amt");
						childList.put("tn0422count");
						childList.put("tn0422amt");
						childList.put("tn0644count");
						childList.put("tn0644amt");
						childList.put("tn0650count");
						childList.put("tn0650amt");
						childList.put("tn0669count");
						childList.put("tn0669amt");					
					}
					else if(("CMCHIS-Claims-Zone Wise".equalsIgnoreCase(reportName)) || ("CMCHIS-DC-Zone Wise".equalsIgnoreCase(reportName))){				
						childList.put("District_Name");
						childList.put("Amt");
						childList.put("Approved");
						childList.put("Approved_Claims_Head");
						childList.put("Claims_Head_Amt");
						childList.put("Total_Approved");
						childList.put("Total_Amt_Approved");
					}
					else if(("CMCHIS-Diagnos-Centres-Claims".equalsIgnoreCase(reportName)) || ("CMCHIS-Diagnos-Centres-DC".equalsIgnoreCase(reportName))){				
						childList.put("Diag_Centre");
						childList.put("District");
						childList.put("Approved_Amt");
						childList.put("Claims_Approved");
					}
					else if(("CMCHIS-Medical-College-Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Hosp Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Hosp Wise - Pvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Tie-Up-Pvt".equalsIgnoreCase(reportName))){	
						childList.put("Instituion_Name");
						childList.put("District_Name");
						childList.put("Approved");
						childList.put("Cancelled");
						childList.put("Denied");
						childList.put("Process");
					}
					else if(("CMCHIS-Preauth-Beneficiary-Govt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Beneficiary-Pvt".equalsIgnoreCase(reportName))){
						childList.put("Activity");
						childList.put("Nos");
						childList.put("Amt");
					}
					else if(("CMCHIS -Preauth-Claims Status-Govt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Claims  Status-Pvt".equalsIgnoreCase(reportName))){		
						childList.put("Module_Flag");
						childList.put("Status");
						childList.put("Amt");
						childList.put("Count_By_Status");
					}
					else if(("CMCHIS-Preauth Disease Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-PreauthDisease Wise - Pvt".equalsIgnoreCase(reportName))){				
						childList.put("General_Type");
						childList.put("Preauth_sought");
						childList.put("Approved");
						childList.put("Cancelled");
						childList.put("Need_More_Info");
						childList.put("Denied");
						childList.put("Process");
					}
					else if(("CMCHIS-Preauth District-Hosp Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth District-Hosp Wise - Pvt".equalsIgnoreCase(reportName))){				
						childList.put("District_Name");
						childList.put("Preauth");
						childList.put("Amt");
					}
					else if("CMCHIS-Preauth Hospital Wise Approved-Pvt".equalsIgnoreCase(reportName)){				
						childList.put("Instituion_Name");
						childList.put("District_Name");
						childList.put("Total");
						childList.put("Count");	
					}
					else if(("CMCHIS-Preauth Patient-District Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth Patient-District Wise - Pvt".equalsIgnoreCase(reportName))){				
						childList.put("District_Name");
						childList.put("Approved");
						childList.put("Cancelled");
						childList.put("Denied");
						childList.put("Process");
					}
					else if("CMCHIS-Preauth Procedure Wise Approved-Pvt".equalsIgnoreCase(reportName)){				
						childList.put("Package_or_Procedure");
						childList.put("District_Name");
						childList.put("Total");
						childList.put("Count");	
					}
					else if(("CMCHIS-Preauth Procedure Wise-Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth Procedure Wise-Pvt".equalsIgnoreCase(reportName))){				
						childList.put("Package_or_Procedure");
						childList.put("Preauth_sought");
						childList.put("Approved");
						childList.put("Cancelled");
						childList.put("Need_More_Info");
						childList.put("Denied");
						childList.put("Process");
						childList.put("Amt");
					}
					else if(("CMCHIS-Preauth-Spl & Pro Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Spl & Pro Wise - Pvt".equalsIgnoreCase(reportName))){				
						childList.put("Category");
						childList.put("Package_or_Procedure");
						childList.put("Amt");
					}
					else if(("CMCHIS-Procedure-Wise-Claims".equalsIgnoreCase(reportName)) || ("CMCHIS-Procedure-Wise-DC".equalsIgnoreCase(reportName))){				
						childList.put("Package_or_Procedure");
						childList.put("Claims_sought");
						childList.put("Approved");
						childList.put("Cancelled");
						childList.put("Need_More_Info");
						childList.put("Denied");
						childList.put("Process");
						childList.put("Amt");
					}
					else if(("CMCHIS-Status-Claims".equalsIgnoreCase(reportName)) || ("CMCHIS-Status-DC".equalsIgnoreCase(reportName))){				
						childList.put("Status");
						childList.put("Amt");
						childList.put("Count");
					}
					else if("CMCHIS-Status-DC".equalsIgnoreCase(reportName)){				
						
					}
			        parentList.put(childList);
		        			
					connection=jdbcTemplate.getDataSource().getConnection();		
					if("CMCHIS_Dengue".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_DENGUE);		
					}
					else if("CMCHIS-Claims-Zone Wise".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_CLAIMS_ZONEWISE);		
					}
					else if("CMCHIS-DC-Zone Wise".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_DC_ZONEWISE);		
					}
					else if("CMCHIS-Diagnos-Centres-Claims".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_DIAGNOS_CENTRES_CLAIMS);		
					}
					else if("CMCHIS-Diagnos-Centres-DC".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_DIAGNOS_CENTRES_DC);		
					}
					else if("CMCHIS-Medical-College-Gvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_MEDICAL_COLLEGE_GOVT);		
					}
					else if("CMCHIS-Preauth-Beneficiary-Govt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_BENEFICIARY_GOVT);		
					}
					else if("CMCHIS-Preauth-Beneficiary-Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_BENEFICIARY_PVT);		
					}
					else if("CMCHIS -Preauth-Claims Status-Govt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_CLAIMS_STATUS_GOVT);		
					}
					else if("CMCHIS-Preauth-Claims  Status-Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_CLAIMS_STATUS_PVT);		
					}
					else if("CMCHIS-Preauth Disease Wise - Gvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_DISEASEWISE_GOVT);		
					}
					else if("CMCHIS-PreauthDisease Wise - Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_DISEASEWISE_PVT);		
					}
					else if("CMCHIS-Preauth District-Hosp Wise - Gvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_DISTHOSPWISE_GOVT);		
					}
					else if("CMCHIS-Preauth District-Hosp Wise - Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_DISTHOSPWISE_PVT);		
					}
					else if("CMCHIS-Preauth Hospital Wise Approved-Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_HOSPWISE_APPROVED_PVT);		
					}
					else if("CMCHIS-Preauth-Hosp Wise - Gvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_HOSPWISE_GOVT);		
					}
					else if("CMCHIS-Preauth-Hosp Wise - Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_HOSPWISE_PVT);		
					}
					else if("CMCHIS-Preauth Patient-District Wise - Gvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_PATIENT_DISTWISE_GOVT);		
					}
					else if("CMCHIS-Preauth Patient-District Wise - Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_PATIENT_DISTWISE_PVT);		
					}
					else if("CMCHIS-Preauth Procedure Wise Approved-Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_PROCEDUREWISE_APPROVED_PVT);		
					}
					else if("CMCHIS-Preauth Procedure Wise-Gvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_PROCEDUREWISE_GOVT);		
					}
					else if("CMCHIS-Preauth Procedure Wise-Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_PROCEDUREWISE_PVT);		
					}
					else if("CMCHIS-Preauth-Spl & Pro Wise - Gvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_SPL_PROWISE_GOVT);		
					}
					else if("CMCHIS-Preauth-Spl & Pro Wise - Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PREAUTH_SPL_PROWISE_PVT);		
					}
					else if("CMCHIS-Procedure-Wise-Claims".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PROCEDUREWISE_CLAIMS);		
					}
					else if("CMCHIS-Procedure-Wise-DC".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_PROCEDUREWISE_DC);		
					}
					else if("CMCHIS-Status-Claims".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_STATUS_CLAIMS);		
					}
					else if("CMCHIS-Status-DC".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_STATUS_DC);		
					}
					else if("CMCHIS-Tie-Up-Pvt".equalsIgnoreCase(reportName)){				
						preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CMCHIS_REPORTZONE_TIE_UP_PVT);		
					}
					preparedStatement.setInt(1, directorateId);
					preparedStatement.setString(2, reportName);
					preparedStatement.setString(3, userName);
					/*preparedStatement.setInt(3, fromYear);
					preparedStatement.setString(4, fromMonth);
					preparedStatement.setInt(5, toYear);
					preparedStatement.setString(6, toMonth);
					preparedStatement.setString(7, userName);*/
					resultSet=preparedStatement.executeQuery();	
				    		
					while(resultSet.next())
					{ 	
						childList=new JSONArray();
						 if("CMCHIS_Dengue".equalsIgnoreCase(reportName)){				
							 String district=resultSet.getString(1);
							 long totalcount=resultSet.getLong(2);
							 long totalsum=resultSet.getLong(3);
							 long tn0407count=resultSet.getLong(4);
							 long tn0407amt=resultSet.getLong(5);
							 long tn0422count=resultSet.getLong(6);
							 long tn0422amt=resultSet.getLong(7);
							 long tn0644count=resultSet.getLong(8);
							 long tn0644amt=resultSet.getLong(9);
							 long tn0650count=resultSet.getLong(10);
							 long tn0650amt=resultSet.getLong(11);
							 long tn0669count=resultSet.getLong(12);
							 long tn0669amt=resultSet.getLong(13);
							
							 childList.put(district);				
							 childList.put(totalcount);	
							 childList.put(totalsum);
							 childList.put(tn0407count);	
							 childList.put(tn0407amt);
							 childList.put(tn0422count);	
							 childList.put(tn0422amt);
							 childList.put(tn0644count);	
							 childList.put(tn0644amt);
							 childList.put(tn0650count);	
							 childList.put(tn0650amt);
							 childList.put(tn0669count);	
							 childList.put(tn0669amt);
						 }
						else if(("CMCHIS-Claims-Zone Wise".equalsIgnoreCase(reportName)) || ("CMCHIS-DC-Zone Wise".equalsIgnoreCase(reportName))){				
							 String district=resultSet.getString(1);
							 long secondColData=resultSet.getLong(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);
							 long fifthColData=resultSet.getLong(5);
							 long sixthColData=resultSet.getLong(6);
							 long seventhColData=resultSet.getLong(7);
							 
							 childList.put(district);				
							 childList.put(secondColData);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);	
							 childList.put(fifthColData);
							 childList.put(sixthColData);	
							 childList.put(seventhColData);
						}
						else if(("CMCHIS-Diagnos-Centres-Claims".equalsIgnoreCase(reportName)) || ("CMCHIS-Diagnos-Centres-DC".equalsIgnoreCase(reportName))){				
							 String diagnosCentre=resultSet.getString(1);
							 String district=resultSet.getString(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);
							 
							 childList.put(diagnosCentre);				
							 childList.put(district);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);	
						}
						else if(("CMCHIS-Medical-College-Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Hosp Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Hosp Wise - Pvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Tie-Up-Pvt".equalsIgnoreCase(reportName))){				
							 String institutionName=resultSet.getString(1);
							 String districtName=resultSet.getString(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);
							 long fifthColData=resultSet.getLong(5);
							 long sixthColData=resultSet.getLong(6);
							 
							 childList.put(institutionName);				
							 childList.put(districtName);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);	
							 childList.put(fifthColData);
							 childList.put(sixthColData);	
						}
						else if(("CMCHIS-Preauth-Beneficiary-Govt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Beneficiary-Pvt".equalsIgnoreCase(reportName))){	
							String activity=resultSet.getString(1);
							long secondColData=resultSet.getLong(2);
							long thirdColData=resultSet.getLong(3);
							 
							childList.put(activity);
							childList.put(secondColData);	
							childList.put(thirdColData);					
						}
						else if(("CMCHIS -Preauth-Claims Status-Govt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Claims  Status-Pvt".equalsIgnoreCase(reportName))){				
							 String flag=resultSet.getString(1);
							 String status=resultSet.getString(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);	
							 
							 childList.put(flag);				
							 childList.put(status);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);	
						}
						else if(("CMCHIS-Preauth Disease Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-PreauthDisease Wise - Pvt".equalsIgnoreCase(reportName))){				
							 String generalType=resultSet.getString(1);
							 long secondColData=resultSet.getLong(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);
							 long fifthColData=resultSet.getLong(5);
							 long sixthColData=resultSet.getLong(6);
							 long seventhColData=resultSet.getLong(7);
							 
							 childList.put(generalType);				
							 childList.put(secondColData);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);	
							 childList.put(fifthColData);
							 childList.put(sixthColData);	
							 childList.put(seventhColData);
						}
						else if(("CMCHIS-Preauth District-Hosp Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth District-Hosp Wise - Pvt".equalsIgnoreCase(reportName))){				
							 String district=resultSet.getString(1);
							 long secondColData=resultSet.getLong(2);
							 long thirdColData=resultSet.getLong(3);
							 
							 childList.put(district);				
							 childList.put(secondColData);	
							 childList.put(thirdColData);
						}
						else if(("CMCHIS-Preauth Hospital Wise Approved-Pvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth Procedure Wise Approved-Pvt".equalsIgnoreCase(reportName))){				
							 String firstColData=resultSet.getString(1);
							 String districtName=resultSet.getString(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);
							 
							 childList.put(firstColData);				
							 childList.put(districtName);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);			
						}
						else if(("CMCHIS-Preauth Patient-District Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth Patient-District Wise - Pvt".equalsIgnoreCase(reportName))){				
							 String district=resultSet.getString(1);
							 long secondColData=resultSet.getLong(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);
							 long fifthColData=resultSet.getLong(5);
							 
							 childList.put(district);				
							 childList.put(secondColData);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);	
							 childList.put(fifthColData);
						}
						else if(("CMCHIS-Preauth Procedure Wise-Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth Procedure Wise-Pvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Procedure-Wise-Claims".equalsIgnoreCase(reportName)) || ("CMCHIS-Procedure-Wise-DC".equalsIgnoreCase(reportName))){				
							 String firstColData=resultSet.getString(1);
							 long secondColData=resultSet.getLong(2);
							 long thirdColData=resultSet.getLong(3);
							 long fourthColData=resultSet.getLong(4);
							 long fifthColData=resultSet.getLong(5);
							 long sixthColData=resultSet.getLong(6);
							 long seventhColData=resultSet.getLong(7);
							 long eighthColData=resultSet.getLong(8);
							 
							 childList.put(firstColData);				
							 childList.put(secondColData);	
							 childList.put(thirdColData);
							 childList.put(fourthColData);	
							 childList.put(fifthColData);
							 childList.put(sixthColData);	
							 childList.put(seventhColData);
							 childList.put(eighthColData);
						}
						else if(("CMCHIS-Preauth-Spl & Pro Wise - Gvt".equalsIgnoreCase(reportName)) || ("CMCHIS-Preauth-Spl & Pro Wise - Pvt".equalsIgnoreCase(reportName))){				
							 String category=resultSet.getString(1);
							 String secondColData=resultSet.getString(2);
							 long thirdColData=resultSet.getLong(3);	
							 
							 childList.put(category);				
							 childList.put(secondColData);	
							 childList.put(thirdColData);
						}
						else if(("CMCHIS-Status-Claims".equalsIgnoreCase(reportName)) || ("CMCHIS-Status-DC".equalsIgnoreCase(reportName))){				
							 String status=resultSet.getString(1);
							 long secondColData=resultSet.getLong(2);
							 long thirdColData=resultSet.getLong(3);
							 
							 childList.put(status);				
							 childList.put(secondColData);	
							 childList.put(thirdColData);
						}
						parentList.put(childList);
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
				return parentList;
			}
			
		private static BigDecimal truncateDecimal(double x,int numberofDecimals)
		{
		    if ( x > 0) {
		        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
		    } else {
		        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
		    }
		}
		
		//Analysis Zone
		public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
			String sr=null;
			String label=null;
			int value=0;
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray analysisZoneData=new JSONArray();
			try {		
				if("Trend Analysis - Patient Died".equalsIgnoreCase(analysisReportName)){
					sr = ShdrcReportQueryList.CMCHIS_ANALYSISZONE;					
				}
				else if("Trend Analysis - Preauth (Status_wise Comparison)".equalsIgnoreCase(analysisReportName)){
					sr = ShdrcReportQueryList.CMCHIS_ANALYSISZONE_PREAUTH_STATUS;					
				}
				else if("Trend Analysis - Rejection due to want of document".equalsIgnoreCase(analysisReportName)){
					sr = ShdrcReportQueryList.CMCHIS_ANALYSISZONE_REJECTION;					
				}
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, analysisReportName);
				preparedStatement.setInt(3, year);
				preparedStatement.setString(4, month);
				preparedStatement.setString(5, userName);
				resultSet=preparedStatement.executeQuery();	
			    		
				while(resultSet.next())
				{ 						
					JSONObject jsonObject=new JSONObject();	
					if("Trend Analysis - Preauth (Status_wise Comparison)".equalsIgnoreCase(analysisReportName)){
						label=resultSet.getString(1);
						value=resultSet.getInt(2);
					}	
					else if("Trend Analysis - Rejection due to want of document".equalsIgnoreCase(analysisReportName)){
						label=resultSet.getString(2);
						value=resultSet.getInt(3);
						String seriesName=resultSet.getString(1);
						jsonObject.put("seriesName",seriesName);		
					}
					else{
						label=resultSet.getString(2);
						value=resultSet.getInt(3);
					}
					jsonObject.put("label",label);
					jsonObject.put("value",value);		
					analysisZoneData.put(jsonObject);
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
			return analysisZoneData;
		}
		
		public JSONObject getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
			String sr=null;
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray analysisZoneFirstSeriesData=new JSONArray();
			JSONArray analysisZoneSecondSeriesData=new JSONArray();
			JSONObject finalList=new JSONObject();			
			try {		
				sr = ShdrcReportQueryList.CMCHIS_ANALYSISZONE_MULTISERIES;				
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, analysisReportName);
				preparedStatement.setInt(3, year);
				preparedStatement.setString(4, month);
				preparedStatement.setString(5, userName);
				resultSet=preparedStatement.executeQuery();	
			    		
				while(resultSet.next())
				{ 					
					JSONObject jsonObject1=new JSONObject();			
					JSONObject jsonObject2=new JSONObject();			
					String label=resultSet.getString(1);
					int value=resultSet.getInt(2);
					jsonObject1.put("label",label);
					jsonObject1.put("value",value);
					jsonObject1.put("seriesName","Value");
					analysisZoneFirstSeriesData.put(jsonObject1);
					
					int count=resultSet.getInt(3);
					jsonObject2.put("label",label);
					jsonObject2.put("value",count);	
					jsonObject2.put("seriesName","Count");
					analysisZoneSecondSeriesData.put(jsonObject2);
					
					finalList.put("analysisZoneFirstSeriesData", analysisZoneFirstSeriesData);
					finalList.put("analysisZoneSecondSeriesData", analysisZoneSecondSeriesData);
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
			return finalList;
		}
		
		public JSONArray getIndicatorPvtStateData(int directorateId,String indicatorCategory, int year, String month,
				String userName) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray IndicatorPvtStateData=null;
			String query=null;
			try{			
				IndicatorPvtStateData=new JSONArray();			
				connection=jdbcTemplate.getDataSource().getConnection();
				if(indicatorCategory.equalsIgnoreCase("Keyword Based - Normal Indicator")){
					query="select * from shdrc_cmchis.indzone_keyword_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Call Centre")){
					query="select * from shdrc_cmchis.indzone_call_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Number of belated Claim Submissions")){
					query="select * from shdrc_cmchis.indzone_belatedclaim_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Claim Amount disposed during the Month")){
					query="select * from shdrc_cmchis.indzone_claimdispos_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Number Of Claims")){
					query="select * from shdrc_cmchis.indzone_totclaims_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Benifites")){
					query="select * from shdrc_cmchis.indzone_benefits_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Claims")){
					query="select * from shdrc_cmchis.indzone_claims_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Corpus fund")){
					query="select * from shdrc_cmchis.indzone_corp_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Empannelment")){
					query="select * from shdrc_cmchis.indzone_emp_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Enrollment")){
					query="select * from shdrc_cmchis.indzone_enroll_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Finance")){
					query="select * from shdrc_cmchis.indzone_finance_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Govt Hospital")){
					query="select * from shdrc_cmchis.indzone_govhos_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-HR")){
					query="select * from shdrc_cmchis.indzone_hr_state_cmchis(?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Suspension")){
					query="select * from shdrc_cmchis.indzone_suspen_state_cmchis(?,?,?,?,?)";
				}
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, indicatorCategory);
				preparedStatement.setInt(3, year);
				preparedStatement.setString(4, month);
				preparedStatement.setString(5, userName);
				resultSet=preparedStatement.executeQuery();	
			while(resultSet.next())
			{ 	
				JSONObject obj=new JSONObject();
				obj.put("Indicator",resultSet.getString(1));
				obj.put("State",resultSet.getString(2));
				obj.put("Value",resultSet.getInt(3));
				IndicatorPvtStateData.put(obj);
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (JSONException exp) {
				exp.printStackTrace();
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
			return IndicatorPvtStateData;
		}
		public JSONArray getIndicatorPvtDistrictData(int directorateId,	String indicatorCategory, String indicators, int year,
				String month, String userName) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray IndicatorPvtDistrictData=null;
			String query=null;
			try{
				IndicatorPvtDistrictData=new JSONArray();
				connection=jdbcTemplate.getDataSource().getConnection();
				
				if(indicatorCategory.equalsIgnoreCase("Keyword Based - Normal Indicator")){
					query="select * from shdrc_cmchis.indzone_keyword_dis_cmchis(?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Call Centre")){
					query="select * from shdrc_cmchis.indzone_call_dist_cmchis(?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Number of belated Claim Submissions")){
					query="select * from shdrc_cmchis.indzone_belatedclaim_district_cmchis(?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Claim Amount disposed during the Month")){
					query="select * from shdrc_cmchis.indzone_claimdispos_dist_cmchis(?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Number Of Claims")){
					query="select * from shdrc_cmchis.indzone_totclaims_dist_cmchis(?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Benifites")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Claims")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Corpus fund")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Empannelment")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Enrollment")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Finance")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Govt Hospital")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-HR")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Suspension")){
					query="select * from shdrc_cmchis.indzone_suspen_dis_cmchis(?,?,?,?,?,?)";
				}
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, indicatorCategory);
				preparedStatement.setString(3, indicators);
				preparedStatement.setInt(4, year);
				preparedStatement.setString(5, month);
				preparedStatement.setString(6, userName);
				resultSet=preparedStatement.executeQuery();	
			while(resultSet.next())
			{ 	
				JSONObject obj = new JSONObject();
				obj.put("District", resultSet.getString(3));
				obj.put("Value", resultSet.getInt(4));
				IndicatorPvtDistrictData.put(obj);
			}	
			} catch (SQLException | JSONException e) {
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
			return IndicatorPvtDistrictData;
		}
		public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory, String indicators, String district,
				int year, String month, String userName) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray IndicatorPvtInstitutionData=null;
			String query=null;
			try{
				IndicatorPvtInstitutionData=new JSONArray();
				connection=jdbcTemplate.getDataSource().getConnection();
				
				if(indicatorCategory.equalsIgnoreCase("Keyword Based - Normal Indicator")){
					query="select * from shdrc_cmchis.indzone_keyword_ins_cmchis(?,?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Call Centre")){
					query="select * from shdrc_cmchis.indzone_call_inst_cmchis(?,?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Number of belated Claim Submissions")){
					query="select * from shdrc_cmchis.indzone_belatedclaim_inst_cmchis(?,?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Claim Amount disposed during the Month")){
					query="select * from shdrc_cmchis.indzone_claimdispos_inst_cmchis(?,?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("Total Number Of Claims")){
					query="select * from shdrc_cmchis.indzone_totclaims_inst_cmchis(?,?,?,?,?,?,?)";
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Benifites")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Claims")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Corpus fund")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Empannelment")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Enrollment")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Finance")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Govt Hospital")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-HR")){
					query=null;
				}
				if(indicatorCategory.equalsIgnoreCase("CMCHIS_Indicator_Zone-Suspension")){
					query="select * from shdrc_cmchis.indzone_suspen_ins_cmchis(?,?,?,?,?,?,?)";
				}
				preparedStatement=connection.prepareStatement(query);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, indicatorCategory);
				preparedStatement.setString(3, indicators);		
				preparedStatement.setString(4, district);
				preparedStatement.setInt(5, year);
				preparedStatement.setString(6, month);
				preparedStatement.setString(7, userName);
				resultSet=preparedStatement.executeQuery();	
				while(resultSet.next())
					{ 	
						JSONObject obj = new JSONObject();
						obj.put("Institution",resultSet.getString(5));
						obj.put("Institution_Type",resultSet.getString(3));
						obj.put("Value",resultSet.getInt(6));
						IndicatorPvtInstitutionData.put(obj);	
				}
		} catch (SQLException | JSONException e) {
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
			return IndicatorPvtInstitutionData;
		}
		public JSONArray getGovtHospitalData(int directorateId,String indicatorCategory, int year, String month,
				String userName) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray govtHospitalData=null;
			try{
				govtHospitalData=new JSONArray();
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement("select * from shdrc_cmchis.indzone_govhosp_normal_cmchis(?,?,?,?,?)");
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, indicatorCategory);
				preparedStatement.setInt(3, year);
				preparedStatement.setString(4, month);
				preparedStatement.setString(5, userName);
				resultSet=preparedStatement.executeQuery();	
			while(resultSet.next())
			{ 	
				JSONObject obj = new JSONObject();
				obj.put("IndicatorName", resultSet.getString(1));
				obj.put("Social", resultSet.getString(2));
				obj.put("AgeGroup", resultSet.getString(3));
				obj.put("Gender", resultSet.getString(4));
				obj.put("IndValue", resultSet.getInt(5));
				govtHospitalData.put(obj);
			}	
			} catch (SQLException | JSONException e) {
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
			return govtHospitalData;
		}
		
		@Override
		public JSONArray getIndicatorList(int directorateId,
				String indicatorCategory, int year, String month, String loggedUser) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray jsonArray=new JSONArray();
			try {
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_state_cmchis(?,?,?,?,?)");
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2,indicatorCategory);
				preparedStatement.setInt(3, year);
				preparedStatement.setString(4,month);
				preparedStatement.setString(5,loggedUser);
				resultSet = preparedStatement.executeQuery();
				 while (resultSet.next()) {
			        	JSONObject jsonObject=new JSONObject();		        	
						jsonObject.put("Indicator", resultSet.getString(1));
						jsonObject.put("State", resultSet.getString(2));					
						String threSholdColor=getThrosholdColor(resultSet.getString(4).charAt(0));
						jsonObject.put("Threshold", threSholdColor);
						jsonObject.put("ThresholdTooltip", resultSet.getString(5));
						jsonObject.put("Value", resultSet.getDouble(3));					
						jsonArray.put(jsonObject);
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
			        return jsonArray;
		}

		@Override
		public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,
				String indicatorCategory, String indicatorName, int year,
				String month, String loggedUser) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray childList=null;
			JSONArray parentList=new JSONArray();
			try {
				/*childList=new JSONArray();			
				childList.put("District");					
				childList.put("Value");	
				childList.put("Zone");	
				parentList.put(childList);*/
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_district_cmchis_zone(?,?,?,?,?,?)");
				preparedStatement.setInt(1, directorateID);
				preparedStatement.setString(2,indicatorCategory);
				preparedStatement.setString(3,indicatorName);
				preparedStatement.setInt(4, year);
				preparedStatement.setString(5,month);
				preparedStatement.setString(6,loggedUser);
				resultSet = preparedStatement.executeQuery();
				 while (resultSet.next()) {
					    childList=new JSONArray();				    			   
						String district=resultSet.getString(3);
						//String Indicator=resultSet.getString(1);	
						//String state=resultSet.getString(2);
						String zone=resultSet.getString(7);
						String color=resultSet.getString(5);
						Double val=resultSet.getDouble(4);
						childList.put(district);					
						//childList.put(Indicator);					
						childList.put(val);
						childList.put(zone);
						childList.put(getThrosholdColor(color.charAt(0)));
						parentList.put(childList);
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
			        return parentList;	
		}

		@Override
		public JSONArray getInstitutionwiseIndicaotrDetails(int directorateID,
				String indicatorCategory, String indicatorName,
				String districtName, int year, String month, String loggedUser) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray childList=null;
			JSONArray parentList=new JSONArray();
			try {
				childList=new JSONArray();
				connection=jdbcTemplate.getDataSource().getConnection();
				childList.put("Institution");	
				childList.put("District");							
				childList.put("Value");	
				childList.put("Threshold");
				parentList.put(childList);
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_inst_cmchis(?,?,?,?,?,?,?)");		
				preparedStatement.setInt(1, directorateID);
				preparedStatement.setString(2,indicatorCategory);
				preparedStatement.setString(3,indicatorName);
				preparedStatement.setString(4, districtName);
				preparedStatement.setInt(5, year);
				preparedStatement.setString(6,month);
				preparedStatement.setString(7,loggedUser);
				resultSet = preparedStatement.executeQuery();
		        while (resultSet.next()) {
					    childList=new JSONArray();				 
						String district=resultSet.getString(3);					
						Double val=resultSet.getDouble(5);
						String institution=resultSet.getString(4);	
						String color=resultSet.getString(6);
						childList.put(institution);
						childList.put(district);							
						childList.put(val);		
						childList.put(getThrosholdColor(color.charAt(0)));
						parentList.put(childList);
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
			        return parentList;	
		}

		private String getThrosholdColor(char c){
			if(c=='Y'){
				return "#F36A6A";//"#ff4d4d";//red
			}
			else if(c=='N'){
				return "#66A65C";//"#2e9e66";//"#00994d";//green
			}else{
				return "";
			}
			
		}
}
