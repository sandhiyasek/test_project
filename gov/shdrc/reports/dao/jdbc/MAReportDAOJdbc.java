package gov.shdrc.reports.dao.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.IMAReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;






import gov.shdrc.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MAReportDAOJdbc implements IMAReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public JSONArray getMAIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray jsonArray=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_state_ma(?,?,?,?,?)");
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
		JSONArray parentList=new JSONArray();
		try {
			/*childList=new JSONArray();			
			childList.put("District");					
			childList.put("Value");	
			childList.put("Zone");	
			parentList.put(childList);*/
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from  shdrc_dwh.dash_hud_ma(?,?,?,?,?,?)");
			preparedStatement.setInt(1, directorateID);
			preparedStatement.setString(2,indicatorCategory);
			preparedStatement.setString(3,indicatorName);
			preparedStatement.setInt(4, year);
			preparedStatement.setString(5,month);
			preparedStatement.setString(6,loggedUser);
			resultSet = preparedStatement.executeQuery();
			 while (resultSet.next()) {
				 	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Region", resultSet.getString(2));
					jsonObject.put("HudName", resultSet.getString(3));					
					String threSholdColor=getThrosholdColor(resultSet.getString(5).charAt(0));
					jsonObject.put("Threshold", threSholdColor);
					jsonObject.put("ThresholdTooltip", resultSet.getString(6));
					jsonObject.put("Value", resultSet.getDouble(4));					
					parentList.put(jsonObject);
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
				childList.put("BlockName");							
				childList.put("Value");	
				childList.put("Threshold");
				parentList.put(childList);
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_inst_ma(?,?,?,?,?,?,?)");		
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
					String blockName=resultSet.getString(2);					
					Double val=resultSet.getDouble(4);
					String institution=resultSet.getString(3);	
					String color=resultSet.getString(5);
					childList.put(institution);
					childList.put(blockName);							
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
	@Override
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indFrequency) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;CommonStringList commonStringList=null;
		
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			if(directorateId==17){//Required because there is separate query for CMCHIS Directorate
				preparedStatement = connection.prepareStatement(ShdrcReportQueryList.CMCHIS_DASHBOARD_MAX_MONTH_YEAR);
			}else{
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DASHBOARD_MAX_MONTH_YEAR);
			}
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2,indFrequency);
			preparedStatement.setInt(3, directorateId);
			preparedStatement.setString(4,indFrequency);			
			resultSet = preparedStatement.executeQuery();
			 while (resultSet.next()) {
				 commonStringList=new CommonStringList();		        	
				 commonStringList.setId( resultSet.getInt(1));
				 commonStringList.setName(resultSet.getString(2));					
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
		        return commonStringList;
	}
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_ma_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_ma_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_ma_ind_cat(?)");
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
				 childList.put("Time_Month_Name");
				 childList.put("Time_Reg_Year");
				 childList.put("Ind_Value");
				 parentList.put(childList);
				 connection=jdbcTemplate.getDataSource().getConnection();				
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_ma_main(?,?,?,?)");
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
						String Time_Month_Name=resultSet.getString(11);
						int Time_Reg_Year=resultSet.getInt(12);
						int Ind_Value=resultSet.getInt(13);
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
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String query=null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
        try {       	
        	childList = getReportTemplateHeader(reportName);
			parentList.put(childList);
			
			connection=jdbcTemplate.getDataSource().getConnection();			
			query = getQueryByReportName(reportName);
			preparedStatement=connection.prepareStatement(query);	
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, reportName);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, fromMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setString(6, toMonth);
			preparedStatement.setString(7, userName);
			resultSet=preparedStatement.executeQuery();	
			
			parentList=getDataByReportName(resultSet,reportName,parentList);	
			
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
	
	private JSONArray getReportTemplateHeader(String reportName){
		JSONArray childList=null;
		try{
			childList=new JSONArray();			
			
			if(("Daily Report - Dis. Surveillance".equalsIgnoreCase(reportName)) || ("CHS & CPMU".equalsIgnoreCase(reportName)) || 
					("Equipments".equalsIgnoreCase(reportName)) || ("Mass Cleaning".equalsIgnoreCase(reportName))
					 || ("Nilavembu and Medical Camps".equalsIgnoreCase(reportName)) || ("Plan 300".equalsIgnoreCase(reportName))
					 || ("Swine Flu".equalsIgnoreCase(reportName)) || ("Registration of Vital Events".equalsIgnoreCase(reportName))
					 || ("Abstract - Civil Works - Corporation".equalsIgnoreCase(reportName)) || ("Abstract - Civil Works - Municipalities".equalsIgnoreCase(reportName))
					 || ("Cattle Menace".equalsIgnoreCase(reportName)) || ("Chemicals".equalsIgnoreCase(reportName)) || ("Health Facilities".equalsIgnoreCase(reportName))
					 || ("Health Facilities - HR".equalsIgnoreCase(reportName)) || ("Health Facilities - HR DPH".equalsIgnoreCase(reportName)) 
					 || ("Health Field - HR".equalsIgnoreCase(reportName)) || ("Indices".equalsIgnoreCase(reportName))){		
				childList.put("Serial No");
				if("Abstract - Civil Works - Corporation".equalsIgnoreCase(reportName)){
					childList.put("Name of the Corporation");
				}
				else if("Abstract - Civil Works - Municipalities".equalsIgnoreCase(reportName)){
					childList.put("Name of the Municipality");
				}
				else{
					childList.put("Name of the ULB");
				}
				childList.put("General Name");
				childList.put("Indicator");				
				childList.put("Value");				
			}
			else if(("Chlorination Input".equalsIgnoreCase(reportName)) || ("ABC".equalsIgnoreCase(reportName)) || ("Inspection-P.H.Standards".equalsIgnoreCase(reportName))
					 || ("Inspections".equalsIgnoreCase(reportName))){
				childList.put("Serial No");
				childList.put("Name of the ULB");
				childList.put("General Name");
				childList.put("General Type");
				childList.put("Indicator");				
				childList.put("Value");		
			}
			else if(("PWS Planning Mapping - Corporation".equalsIgnoreCase(reportName)) || ("PWS Planning Mapping - Municipalities".equalsIgnoreCase(reportName))){
				childList.put("Serial No");
				if("PWS Planning Mapping - Corporation".equalsIgnoreCase(reportName)){
					childList.put("Name of the Corporation");
				}
				else if("PWS Planning Mapping - Municipalities".equalsIgnoreCase(reportName)){
					childList.put("Name of the Municipality");
				}
				childList.put("General Name");
				childList.put("General Type");
				childList.put("Indicator");				
				childList.put("Value");		
			}
			else if(("COTPA".equalsIgnoreCase(reportName)) || ("Pipe Line & Gate Valves".equalsIgnoreCase(reportName)) || ("Water Analysis".equalsIgnoreCase(reportName))
					 || ("Grave Yards".equalsIgnoreCase(reportName))){	
				childList.put("Serial No");
				childList.put("Name of the ULB");
				childList.put("Indicator");				
				childList.put("Value");				
			}		
			else if(("COTPA Abstract".equalsIgnoreCase(reportName))){		
				childList.put("Serial No");
				childList.put("Name of the ULB");
				childList.put("Indicator");
				/*childList.put("Year");*/
				childList.put("Value");				
			}	
			else if(("Dengue Cross Notification".equalsIgnoreCase(reportName))){	
				childList.put("Serial No");
				childList.put("Name of the ULB");
				childList.put("General Name");				
				childList.put("Value");				
			}	
			else if(("Existing  UPHC - Renovation - Corporation".equalsIgnoreCase(reportName)) || ("Existing  UPHC - Renovation - Municipality".equalsIgnoreCase(reportName))
					|| ("Existing UPHC - New Building - Corporation".equalsIgnoreCase(reportName)) || ("Existing UPHC - New Building - Municipalities".equalsIgnoreCase(reportName))){	
				childList.put("Serial No");
				if(("Existing  UPHC - Renovation - Corporation".equalsIgnoreCase(reportName)) || ("Existing UPHC - New Building - Corporation".equalsIgnoreCase(reportName))){
					childList.put("Name of the Corporation");
				}
				else if(("Existing  UPHC - Renovation - Municipality".equalsIgnoreCase(reportName)) || ("Existing UPHC - New Building - Municipalities".equalsIgnoreCase(reportName))){
					childList.put("Name of the Municipality");
				}
				childList.put("Name of the UPHC(existing health centre)");
				childList.put("General Name");
				childList.put("Indicator");				
				childList.put("Value");				
			}	
			else if(("New UPHC - Corporation".equalsIgnoreCase(reportName)) || ("New UPHC - Municipalities".equalsIgnoreCase(reportName))){	
				childList.put("Serial No");
				if("New UPHC - Corporation".equalsIgnoreCase(reportName)){
					childList.put("Name of the Corporation");
				}
				else if("New UPHC - Municipalities".equalsIgnoreCase(reportName)){
					childList.put("Name of the Municipality");
				}
				childList.put("Name of new UPHC");
				childList.put("General Name");	
				childList.put("Indicator");		
				childList.put("General Type");
				childList.put("Value");				
			}	
			else if(("Performance - AllopathyDispensary".equalsIgnoreCase(reportName)) || ("Performance - AyurvedhaDispensary".equalsIgnoreCase(reportName)) || 
					("Performance - HealthCentres".equalsIgnoreCase(reportName)) || ("Performance - SiddhaDispensary".equalsIgnoreCase(reportName))
					 || ("Performance - UnaniDispensary".equalsIgnoreCase(reportName))){		
				childList.put("Serial No");
				childList.put("Name of the ULB");
				childList.put("Name of the Centre");
				childList.put("General Name");
				childList.put("Indicator");				
				childList.put("Value");				
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("Chlorination Input".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_CHLORINATION_INPUT;
		}		
		else if("COTPA".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_COTPA;
		}
		else if("COTPA Abstract".equalsIgnoreCase(reportName)){		
			query=ShdrcReportQueryList.MA_REPORTZONE_COTPA_ABSTRACT;
		}
		else if("Daily Report - Dis. Surveillance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_DAILY_DISEASE_SURVEILLANCE;
		}
		else if("Dengue Cross Notification".equalsIgnoreCase(reportName)){		
			query=ShdrcReportQueryList.MA_REPORTZONE_DENGUE_CROSS_NOTIFICATION;
		}
		else if("CHS & CPMU".equalsIgnoreCase(reportName)){		
			query=ShdrcReportQueryList.MA_REPORTZONE_DENGUE_CHS_CPMU;
		}
		else if("Existing  UPHC - Renovation - Corporation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_EXISTING_UPHC_RENOVATION_CORPORATION;
		}
		else if("Existing  UPHC - Renovation - Municipality".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_EXISTING_UPHC_RENOVATION_MUNICIPALITY;
		}
		else if("Existing UPHC - New Building - Corporation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_EXISTING_UPHC_NEWBUILDING_CORPORATION;
		}
		else if("Existing UPHC - New Building - Municipalities".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_EXISTING_UPHC_NEWBUILDING_MUNICIPALITY;
		}
		else if("Equipments".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_EQUIPMENTS;
		}
		else if("Mass Cleaning".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_MASS_CLEANING;
		 }
		else if(("New UPHC - Corporation".equalsIgnoreCase(reportName))){	
			query=ShdrcReportQueryList.MA_REPORTZONE_NEW_UPHC_CORPORATION;
		}
		else if("New UPHC - Municipalities".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_NEW_UPHC_MUNICIPALITY;
		}
		else if("Nilavembu and Medical Camps".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_NILAVEMBU_MEDICALCAMPS;
		}
		else if("Performance - AllopathyDispensary".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PERF_ALLOPATHY_DISPENSARY;
		}
		else if("Performance - AyurvedhaDispensary".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PERF_AYURVEDA_DISPENSARY;
		}
		else if("Performance - HealthCentres".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PERF_HEALTHCENTRES;
		}
		else if("Performance - SiddhaDispensary".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PERF_SIDDHA_DISPENSARY;
		}
		else if("Performance - UnaniDispensary".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PERF_UNANI_DISPENSARY;
		}
		else if("Pipe Line & Gate Valves".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PIPELINE_GATEVALVES;
		 }
		else if("Water Analysis".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_WATER_ANALYSIS;
		 }
		else if("Plan 300".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PLAN_300;
		}
		else if("PWS Planning Mapping - Corporation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PWS_PLANNING_MAPPING_CORPORATION;
		}
		else if("PWS Planning Mapping - Municipalities".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_PWS_PLANNING_MAPPING_MUNICIPALITY;
		}
		else if("Swine Flu".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_SWINE_FLU;
		}
		else if("Registration of Vital Events".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_REGISTRATION_OF_VITAL_EVENTS;	
		}
		else if("ABC".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_ABC;	
		}
		else if("Abstract - Civil Works - Corporation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_ABSTRACT_CIVIL_WORKS_CORP;	
		}
		else if("Abstract - Civil Works - Municipalities".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_ABSTRACT_CIVIL_WORKS_MUNI;	
		}
		else if("Cattle Menace".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_CATTLE_MENACE;	
		}
		else if("Chemicals".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_CHEMICALS;	
		}
		else if("Grave Yards".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_GRAVEYARDS;	
		}
		else if("Health Facilities".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_HEALTH_FACILITIES;	
		}
		else if("Health Facilities - HR".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_HEALTH_FACILITIES_HR;	
		}
		else if("Health Facilities - HR DPH".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_HEALTH_FACILITIES_HR_DPH;	
		}
		else if("Health Field - HR".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_HEALTH_FIELD_HR;	
		}
		else if("Indices".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_INDICES;	
		}
		else if("Inspection-P.H.Standards".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_INSPECTION_PH_STANDARDS;	
		}
		else if("Inspections".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.MA_REPORTZONE_INSPECTIONS;	
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{
			if(("Daily Report - Dis. Surveillance".equalsIgnoreCase(reportName)) || ("CHS & CPMU".equalsIgnoreCase(reportName)) || 
					("Equipments".equalsIgnoreCase(reportName)) || ("Mass Cleaning".equalsIgnoreCase(reportName))
					 || ("Nilavembu and Medical Camps".equalsIgnoreCase(reportName)) || ("Plan 300".equalsIgnoreCase(reportName))
					 || ("Swine Flu".equalsIgnoreCase(reportName)) || ("Registration of Vital Events".equalsIgnoreCase(reportName))
					 || ("Abstract - Civil Works - Corporation".equalsIgnoreCase(reportName)) || ("Abstract - Civil Works - Municipalities".equalsIgnoreCase(reportName))
					 || ("Cattle Menace".equalsIgnoreCase(reportName)) || ("Chemicals".equalsIgnoreCase(reportName)) || ("Health Facilities".equalsIgnoreCase(reportName))
					 || ("Health Facilities - HR".equalsIgnoreCase(reportName)) || ("Health Facilities - HR DPH".equalsIgnoreCase(reportName)) 
					 || ("Health Field - HR".equalsIgnoreCase(reportName)) || ("Indices".equalsIgnoreCase(reportName))){
				String previousHud=null;
				int serial=1;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String hudName=resultSet.getString(1);					
					String generalName=resultSet.getString(2);			
					String indicator=resultSet.getString(3);
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					serial=Util.generateSerialNo(serial, previousHud, hudName);
					previousHud=hudName;
					childList.put(serial);					
					childList.put(hudName);										
 					childList.put(generalName);	
					childList.put(indicator);					
					childList.put(val);
					parentList.put(childList);
				}
			}	
			else if(("Chlorination Input".equalsIgnoreCase(reportName)) || ("PWS Planning Mapping - Corporation".equalsIgnoreCase(reportName))
					|| ("PWS Planning Mapping - Municipalities".equalsIgnoreCase(reportName)) || ("ABC".equalsIgnoreCase(reportName)) || 
					("Inspection-P.H.Standards".equalsIgnoreCase(reportName)) || ("Inspections".equalsIgnoreCase(reportName))){
				String previousHud=null;
				int serial=1;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String hudName=resultSet.getString(1);					
					String generalName=resultSet.getString(2);			
					String generalType=resultSet.getString(3);			
					String indicator=resultSet.getString(4);
					BigDecimal val=truncateDecimal(resultSet.getDouble(5),2);
					
					serial=Util.generateSerialNo(serial, previousHud, hudName);
					previousHud=hudName;
					childList.put(serial);					
					childList.put(hudName);										
 					childList.put(generalName);	
 					childList.put(generalType);	
					childList.put(indicator);					
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("COTPA".equalsIgnoreCase(reportName)) || ("Pipe Line & Gate Valves".equalsIgnoreCase(reportName)) || ("Water Analysis".equalsIgnoreCase(reportName))
					|| ("Grave Yards".equalsIgnoreCase(reportName))){
				String previousHud=null;
				int serial=1;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();	
					String hudName=resultSet.getString(1);		
					String indicator=resultSet.getString(2);						
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					
					serial=Util.generateSerialNo(serial, previousHud, hudName);
					previousHud=hudName;
					childList.put(serial);		
					childList.put(hudName);	
					childList.put(indicator);					
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("COTPA Abstract".equalsIgnoreCase(reportName))){		
				String previousHud=null;
				int serial=1;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();	
					String hudName=resultSet.getString(1);		
					String indicator=resultSet.getString(2);						
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					
					serial=Util.generateSerialNo(serial, previousHud, hudName);
					previousHud=hudName;
					childList.put(serial);		
					childList.put(hudName);	
					childList.put(indicator);					
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Dengue Cross Notification".equalsIgnoreCase(reportName))){	
				String previousHud=null;
				int serial=1;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();	
					String hudName=resultSet.getString(2);		
					String generalName=resultSet.getString(3);						
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					serial=Util.generateSerialNo(serial, previousHud, hudName);
					previousHud=hudName;
					childList.put(serial);
					childList.put(hudName);	
					childList.put(generalName);					
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Existing  UPHC - Renovation - Corporation".equalsIgnoreCase(reportName)) || ("Existing  UPHC - Renovation - Municipality".equalsIgnoreCase(reportName))
					 || ("Existing UPHC - New Building - Corporation".equalsIgnoreCase(reportName)) || ("Existing UPHC - New Building - Municipalities".equalsIgnoreCase(reportName))
					 || ("Performance - AllopathyDispensary".equalsIgnoreCase(reportName)) || ("Performance - AyurvedhaDispensary".equalsIgnoreCase(reportName)) || 
						("Performance - HealthCentres".equalsIgnoreCase(reportName)) || ("Performance - SiddhaDispensary".equalsIgnoreCase(reportName))
						 || ("Performance - UnaniDispensary".equalsIgnoreCase(reportName))){	
				String previousHud=null;
				int serial=1;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();	
					String hudName=resultSet.getString(2);		
					String institutionName=resultSet.getString(3);	
					String generalName=resultSet.getString(4);	
					String indicator=resultSet.getString(5);		
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					serial=Util.generateSerialNo(serial, previousHud, hudName);
					previousHud=hudName;
					childList.put(serial);
					childList.put(hudName);	
					childList.put(institutionName);	
					childList.put(generalName);
					childList.put(indicator);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("New UPHC - Corporation".equalsIgnoreCase(reportName)) || ("New UPHC - Municipalities".equalsIgnoreCase(reportName))){	
				String previousHud=null;
				int serial=1;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();	
					String hudName=resultSet.getString(2);		
					String institutionName=resultSet.getString(3);	
					String generalName=resultSet.getString(4);	
					String indicator=resultSet.getString(5);	
					String generalType=resultSet.getString(6);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);
					
					serial=Util.generateSerialNo(serial, previousHud, hudName);
					previousHud=hudName;
					childList.put(serial);
					childList.put(hudName);	
					childList.put(institutionName);	
					childList.put(generalName);
					childList.put(indicator);	
					childList.put(generalType);	
					childList.put(val);
					parentList.put(childList);
				}
			}
		}catch(Exception json){
			
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
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName,String regionName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {		
			if("Details of Deaths".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.MA_ANALYSISZONE;
			}
			else if("Details of Vital Events".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.MA_ANALYSISZONE_VITAL_EVENTS;
			}
			else if("Details of Water Analysis".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.MA_ANALYSISZONE_WATER_ANALYSIS;
			}
			else if("Details of Water Supply".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.MA_ANALYSISZONE_WATER_SUPPLY;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, analysisReportName);
			if("Details of Water Supply".equalsIgnoreCase(analysisReportName)){
				preparedStatement.setInt(3, year);
				preparedStatement.setString(4, month);
				preparedStatement.setString(5, userName);
			}
			else{
				preparedStatement.setString(3, regionName);
				preparedStatement.setInt(4, year);
				preparedStatement.setString(5, month);
				preparedStatement.setString(6, userName);
			}
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();	
				String label=null;
				int value=0;
				if("Details of Water Supply".equalsIgnoreCase(analysisReportName)){
					label=resultSet.getString(1);
					value=resultSet.getInt(2);
				}
				else{
					label=resultSet.getString(1);
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
}
