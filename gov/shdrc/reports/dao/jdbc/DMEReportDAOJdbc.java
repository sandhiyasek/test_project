package gov.shdrc.reports.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.shdrc.reports.dao.IDMEReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;
import gov.shdrc.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DMEReportDAOJdbc implements IDMEReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dme_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dme_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dme_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dme_main(?,?,?,?)");
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
/*	public JSONArray getDMEIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray jsonArray=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_state(?,?,?,?,?)");
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
			childList=new JSONArray();			
			childList.put("District");					
			childList.put("Value");	
			childList.put("Zone");	
			parentList.put(childList);
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from  shdrc_dwh.dash_district_zone(?,?,?,?,?,?)");
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
			if(directorateID==2){				
				childList.put("Institution Type");
				childList.put("Institution");	
				childList.put("District");							
				childList.put("Value");	
				childList.put("Threshold");
				parentList.put(childList);
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_inst(?,?,?,?,?,?,?)");
			}else{
				childList.put("Institution");	
				childList.put("District");							
				childList.put("Value");	
				childList.put("Threshold");
				parentList.put(childList);
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_inst(?,?,?,?,?,?,?)");
			}			
			preparedStatement.setInt(1, directorateID);
			preparedStatement.setString(2,indicatorCategory);
			preparedStatement.setString(3,indicatorName);
			preparedStatement.setString(4, districtName);
			preparedStatement.setInt(5, year);
			preparedStatement.setString(6,month);
			preparedStatement.setString(7,loggedUser);
			resultSet = preparedStatement.executeQuery();
			if(directorateID==2){
				while (resultSet.next()) {
				    childList=new JSONArray();				 
					String district=resultSet.getString(3);					
					Double val=resultSet.getDouble(7);
					String institution=resultSet.getString(5);	
					String institutionType=resultSet.getString(6);
					String color=resultSet.getString(8);					
					childList.put(institutionType);
					childList.put(institution);
					childList.put(district);							
					childList.put(val);		
					childList.put(getThrosholdColor(color.charAt(0)));
					parentList.put(childList);
			}
			}else{
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
	}*/
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {	
			if("InPatient Census".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DME_ANALYSISZONE;
			}
			else if("Ceasarian Deliveries Monthwise".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DME_ANALYSISZONE_CEASARIAN_DELIVERIES;
			}
			else if("Major Surgeries Monthwise".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DME_ANALYSISZONE_MAJOR_SURGERIES;
			}
			else if("Minor Surgeries Monthwise".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DME_ANALYSISZONE_MINOR_SURGERIES;
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
				String label=resultSet.getString(1);
				int value=resultSet.getInt(2);
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
	
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,
			String fromMonth,Integer toYear,String toMonth,String institutionName,String postName,String fileName,String frequency,
			String date,String userName){
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
			if("Data Availability Institution Wise".equalsIgnoreCase(reportName)){
				Date newDate=Util.convertToUtilDate(date);
				
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, frequency);
				preparedStatement.setString(3, fileName);
				preparedStatement.setDate(4, new java.sql.Date(newDate.getTime()));
			}
			else{
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, reportName);
				preparedStatement.setInt(3, fromYear);
				preparedStatement.setString(4, fromMonth);
				preparedStatement.setInt(5, toYear);
				preparedStatement.setString(6, toMonth);
				if(("DME_DENGUE REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Date Wise)".equalsIgnoreCase(reportName))
						|| ("DME_DENGUE  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Institution wise)".equalsIgnoreCase(reportName))
						|| ("DME_DENGUE REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Monthwise)".equalsIgnoreCase(reportName))
						|| ("DME_DENGUE  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Year wise)".equalsIgnoreCase(reportName))
						|| ("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Inst_wise Post_wise)".equalsIgnoreCase(reportName)) /*|| ("DME_Cadre Strength(Inst_wise Post_wise)".equalsIgnoreCase(reportName))*/){
					Array institutionArray=preparedStatement.getConnection().createArrayOf("text", institutionName.split(","));
					preparedStatement.setArray(7, institutionArray);
					preparedStatement.setString(8, userName);
				}
				else if(("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Inst_wise)".equalsIgnoreCase(reportName)) || ("DME_Cadre Strength(Post_wise Inst_wise)".equalsIgnoreCase(reportName))){
					Array postArray=preparedStatement.getConnection().createArrayOf("text", postName.split(","));
					preparedStatement.setArray(7, postArray);
					preparedStatement.setString(8, userName);
				}
				else{
					preparedStatement.setString(7, userName);
				}
			}
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
			
			if(("DME_DENGUE REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Date Wise)".equalsIgnoreCase(reportName))
					|| ("DME_EBOLA REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Date Wise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Institution wise)".equalsIgnoreCase(reportName)) 
					|| ("DME_EBOLA  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Institution wise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Monthwise)".equalsIgnoreCase(reportName)) 
					|| ("DME_EBOLA REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Monthwise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Year wise)".equalsIgnoreCase(reportName))
					|| ("DME_EBOLA  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Year wise)".equalsIgnoreCase(reportName))){
				childList.put("Indicator");
				childList.put("General Name");			
				
				if(("DME_DENGUE REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Date Wise)".equalsIgnoreCase(reportName))
						|| ("DME_EBOLA REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Date Wise)".equalsIgnoreCase(reportName))){
					childList.put("Date");
				}
				else if(("DME_DENGUE  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Institution wise)".equalsIgnoreCase(reportName)) 
						|| ("DME_EBOLA  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Institution wise)".equalsIgnoreCase(reportName))){
					childList.put("Institution Name");
				}
				else if(("DME_DENGUE REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Monthwise)".equalsIgnoreCase(reportName)) 
						|| ("DME_EBOLA REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Monthwise)".equalsIgnoreCase(reportName))){
					childList.put("Year");
					childList.put("Month");
				}
				else if(("DME_DENGUE  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Year wise)".equalsIgnoreCase(reportName)) 
						|| ("DME_EBOLA  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Year wise)".equalsIgnoreCase(reportName))){
					childList.put("Year");
				}
				childList.put("Value");
			}
			else if(("DME_ACCIDENT AND EMERGENCY".equalsIgnoreCase(reportName)) || ("DME_BRAIN DEATH DECLARED STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_CLEFT LIP AND PALATE SURGERIES STATISTICS".equalsIgnoreCase(reportName)) || ("DME_CT Scan".equalsIgnoreCase(reportName)) 
					|| ("DME_TOTAL DELIVERIES".equalsIgnoreCase(reportName)) || ("DME_ECG TAKEN".equalsIgnoreCase(reportName)) 
					|| ("DME_IMMUNISATION PERFORMANCE".equalsIgnoreCase(reportName)) || ("DME_STERILISATION PERFORMANCE STATISTICS".equalsIgnoreCase(reportName))){
				childList.put("Institution Name");
				childList.put("Value");
			}	
			else if(("DME_DELIVERIES".equalsIgnoreCase(reportName)) || ("DME_IMAGING STUDIES STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_LITHOTRIPSY STATISTICS".equalsIgnoreCase(reportName)) || ("DME_LIVE BIRTHS".equalsIgnoreCase(reportName)) 
					|| ("DME_MAJOR MINOR TOTAL SURGERIES".equalsIgnoreCase(reportName)) || ("DME_RENAL TRANSPLANTS STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_MASTER HEALTH CHECKUP".equalsIgnoreCase(reportName)) || ("DME_Deliveries Maternal Death".equalsIgnoreCase(reportName))
					|| ("DME_Lab Investigation".equalsIgnoreCase(reportName)) || ("DME_lab investigations statistics".equalsIgnoreCase(reportName)) 
					|| ("DME_Bio Chemistry Report".equalsIgnoreCase(reportName)) || ("DME_Micro Biology Report".equalsIgnoreCase(reportName)) 
					|| ("DME_Pathology Report".equalsIgnoreCase(reportName)) || ("DME_Cancer Control Programme".equalsIgnoreCase(reportName))
					|| ("DME_Post Mortem Report".equalsIgnoreCase(reportName)) || ("DME_Blood Bag and Hospital Diet Report".equalsIgnoreCase(reportName)) 
					|| ("DME_Caesarean Report".equalsIgnoreCase(reportName)) || ("DME_SNAKE BITE CASES STATISTICS".equalsIgnoreCase(reportName))
					|| ("DME_Cadaver".equalsIgnoreCase(reportName))){
				childList.put("Institution Name");
				childList.put("Indicator");
				childList.put("Value");
			}	
			else if(("DME_INPATIENT MONTHWISE".equalsIgnoreCase(reportName)) || ("DME_maternal".equalsIgnoreCase(reportName))){
				childList.put("Institution Name");
				childList.put("Month");
				childList.put("Value");
			}
			else if("DME_Institution wise ip report".equalsIgnoreCase(reportName)){
				childList.put("Department");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if(("DME_DELIVERIES STATISTICS".equalsIgnoreCase(reportName)) || ("DME_IMAGING STUDIES".equalsIgnoreCase(reportName)) 
					|| ("DME_Sterilisation Statistics".equalsIgnoreCase(reportName)) || ("DME_IMMUNISATION_ NEONATAL AND INFANT DEATHS".equalsIgnoreCase(reportName)) 
					|| ("DME_NEONATAL DEATH - Out Born & InBorn".equalsIgnoreCase(reportName)) || ("DME_LAB results".equalsIgnoreCase(reportName))){
				childList.put("Institution Name");
				childList.put("General Name");
				childList.put("Value");
			}
			else if("DME_SNAKE BITE CASES MONTHWISE STATISTICS".equalsIgnoreCase(reportName)){
				childList.put("Indicator");
				childList.put("Month");
				childList.put("Value");
			}
			else if(("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED".equalsIgnoreCase(reportName)) || ("DME_SC ST Representation_Group_A".equalsIgnoreCase(reportName))
					|| ("DME_SC ST Representation_Group_B".equalsIgnoreCase(reportName)) || ("DME_SC ST Representation_Group_C".equalsIgnoreCase(reportName))
					|| ("DME_SC ST Representation_Group_D".equalsIgnoreCase(reportName)) || ("DME_SC ST Representation_Abstract".equalsIgnoreCase(reportName))){
				childList.put("Sl.No");
				if("DME_SC ST Representation_Abstract".equalsIgnoreCase(reportName)){
					childList.put("Group");
				}
				else{
					childList.put("Name of the Post");
				}
				childList.put("Indicator");				
				childList.put("Value");
			}
			else if("DME_DEPT INST MONTHWISE SURGERIES REPORT".equalsIgnoreCase(reportName)){
				childList.put("Department");
				childList.put("Institution Name");
				childList.put("Month");
				childList.put("Indicator");				
				childList.put("Value");
			}
			else if(("DME_Dept wise instwise OP report".equalsIgnoreCase(reportName)) || ("DME_Institution wise Dept wise report".equalsIgnoreCase(reportName))){
				childList.put("Department");
				childList.put("Institution Name");
				childList.put("Indicator");				
				childList.put("Value");
			} 
			else if("DME_INFANT DEATHS, NEONATAL DEATHS MONTHWISE".equalsIgnoreCase(reportName)){
				childList.put("Institution Name");
				childList.put("Month");
				childList.put("Indicator");				
				childList.put("Value");
			}
			else if("DME_Lab Monthwise".equalsIgnoreCase(reportName)){ 
				childList.put("Institution Name");
				childList.put("Month");
				childList.put("General Name");				
				childList.put("Value");
			}
			else if("DME_DOG BITE CASES MONTHWISE STATISTICS".equalsIgnoreCase(reportName)){
				childList.put("Indicator Category");				
				childList.put("Indicator");		
				childList.put("Month");
				childList.put("Value");
			}
			else if("DME_DOG BITE STATISTICS".equalsIgnoreCase(reportName)){ 
				childList.put("Institution Name");
				childList.put("Indicator Category");				
				childList.put("Indicator");						
				childList.put("Value");
			}
			else if("DME_Cancer cases treated and Mortality".equalsIgnoreCase(reportName)){
				childList.put("Institution Name");
				childList.put("Indicator Category");				
				childList.put("General Name");						
				childList.put("Value");
			}
			else if(("DME_monthwise inst wise dog bite cases".equalsIgnoreCase(reportName)) || ("DME_Instwise month wise Dog bites and Rabies Death".equalsIgnoreCase(reportName))){
				childList.put("Institution Name");
				childList.put("Indicator Category");				
				childList.put("Month");						
				childList.put("Value");
			}
			else if("DME_Genderwise OP IP".equalsIgnoreCase(reportName)){
				childList.put("Institution Name");
				childList.put("Indicator");				
				childList.put("Month");						
				childList.put("Value");
			}
			else if(("DME_LINE_LIST_DENGUE".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_H1N1".equalsIgnoreCase(reportName))
					 || ("DME_LINE_LIST_TICK_FEVER".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_EBOLA".equalsIgnoreCase(reportName))
					 ){
				childList.put("Serial No");
				childList.put("Institution Name");
				childList.put("Patient Name");				
				childList.put("Age");						
				childList.put("Sex");
				childList.put("Address");
				childList.put("Referral Hospital Name");
				childList.put("Whether Recovered");				
				childList.put("Death");						
				childList.put("Fever Cases");
				if("DME_LINE_LIST_DENGUE".equalsIgnoreCase(reportName)){
					childList.put("Dengue Cases");
				}
				else if("DME_LINE_LIST_H1N1".equalsIgnoreCase(reportName)){
					childList.put("AH1N1 Cases");
				}
				else if("DME_LINE_LIST_TICK_FEVER".equalsIgnoreCase(reportName)){
					childList.put("Tick Fever Cases");
				}
				else if("DME_LINE_LIST_EBOLA".equalsIgnoreCase(reportName)){
					childList.put("Ebola Cases");
				}
			}
			else if(("DME_LINE_LIST_DEATH_DENGUE".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_DEATH_H1N1".equalsIgnoreCase(reportName))
					 || ("DME_LINE_LIST_DEATH_TICK_FEVER".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_DEATH_EBOLA".equalsIgnoreCase(reportName))){
				childList.put("Serial No");
				childList.put("Institution Name");
				childList.put("Patient Name");				
				childList.put("Age");						
				childList.put("Sex");
				childList.put("Address");
				childList.put("Phone No");
				childList.put("Referral Hospital Name");
				if("DME_LINE_LIST_DEATH_DENGUE".equalsIgnoreCase(reportName)){
					childList.put("Platelet less than 1 lakh");
					childList.put("Igm Plus");
					childList.put("Ns1ag Plus");
				}
			}
			else if("Data Availability Institution Wise".equalsIgnoreCase(reportName)){
				childList.put("File Name");
				childList.put("District");
				childList.put("Institution Name");	
			}
			else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Inst_wise Post_wise)".equalsIgnoreCase(reportName)){
				childList.put("Serial No");
				childList.put("Institution Name");
				childList.put("Name of the Post");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Inst_wise Consolidate)".equalsIgnoreCase(reportName)){
				childList.put("Serial No");
				childList.put("Institution Name");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Consolidate)".equalsIgnoreCase(reportName)){
				childList.put("Serial No");
				childList.put("Name of the Post");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if("DME_Cadre Strength(Inst_wise Post_wise)".equalsIgnoreCase(reportName)){
				childList.put("Serial No");
				childList.put("Institution Name");
				childList.put("Name of the Post");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if("DME_Cadre_Strength(Inst_wise Consolidate)".equalsIgnoreCase(reportName)){
				childList.put("Serial No");
				childList.put("Institution Name");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if("DME_Cadre Strength(Post_wise Consolidate)".equalsIgnoreCase(reportName)){
				childList.put("Serial No");
				childList.put("Name of the Post");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if(("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Inst_wise)".equalsIgnoreCase(reportName)) || ("DME_Cadre Strength(Post_wise Inst_wise)".equalsIgnoreCase(reportName))){
				childList.put("Serial No");
				childList.put("Name of the Post");
				childList.put("Institution Name");
				childList.put("Indicator");
				childList.put("Value");
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("DME_DENGUE REPORT (Date Wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DENGUE_DATEWISE;
		}
		else if("DME_DELIVERIES STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DELIVERIES_STATISTICS;
		}
		else if("DME_IMAGING STUDIES".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_IMAGING_STUDIES;
		}
		else if("DME_Sterilisation Statistics".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_STERILISATION_STATISTICS;
		}
		else if("DME_IMMUNISATION_ NEONATAL AND INFANT DEATHS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_IMMUNISATION_NEONATAL_INFANT_DEATHS;
		}
		else if("DME_NEONATAL DEATH - Out Born & InBorn".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_NEONATAL_DEATH_OUTBORN_INBORN;
		}
		else if("DME_LAB results".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LAB_RESULTS;
		}
		else if("DME_Caesarean Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_CAESAREAN_REPORT;
		}
		else if("DME_Blood Bag and Hospital Diet Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_BLOODBAG_HOSPITALDIET_REPORT;
		}
		else if("DME_Post Mortem Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_POSTMORTEM_REPORT;
		}
		else if("DME_Cancer Control Programme".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_CANCER_CONTROL_PROGRAMME;
		}
		else if("DME_Pathology Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_PATHOLOGY_REPORT;
		}
		else if("DME_Micro Biology Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_MICROBIOLOGY_REPORT;
		}
		else if("DME_Bio Chemistry Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_BIOCHEMISTRY_REPORT;
		}
		else if("DME_lab investigations statistics".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LAB_INVESTIGATION_STATISTICS;
		}
		else if("DME_DELIVERIES".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DELIVERIES;
		}
		else if("DME_IMAGING STUDIES STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_IMAGING_STUDIES_STATISTICS;
		}
		else if("DME_LITHOTRIPSY STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LITHOTRIPSY_STATISTICS;
		}
		else if("DME_LIVE BIRTHS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LIVE_BIRTHS;
		}
		else if("DME_MAJOR MINOR TOTAL SURGERIES".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_MAJOR_MINOR_TOTAL_SURGERIES;
		}
		else if("DME_RENAL TRANSPLANTS STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_RENAL_TRANSPLANTS_STATISTICS;
		}
		else if("DME_MASTER HEALTH CHECKUP".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_MASTER_HEALTH_CHECKUP;
		}
		else if("DME_Deliveries Maternal Death".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DELIVERIES_MATERNAL_DEATH;
		}
		else if("DME_Lab Investigation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DME_LAB_INVESTIGATION;
		}
		else if("DME_DENGUE  REPORT (Institution wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DENGUE_INSTWISE;
		}
		else if("DME_DENGUE REPORT (Monthwise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DENGUE_MONTHWISE;
		}
		else if("DME_DENGUE  REPORT (Year wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DENGUE_YEARWISE;
		}
		else if("DME_AH1N1 REPORT (Date Wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_AH1N1_DATEWISE;
		}
		else if("DME_AH1N1  REPORT (Institution wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_AH1N1_INSTWISE;
		}
		else if("DME_AH1N1 REPORT (Monthwise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_AH1N1_MONTHWISE;
		}
		else if("DME_AH1N1  REPORT (Year wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_AH1N1_YEARWISE;
		}
		else if("DME_EBOLA REPORT (Date Wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_EBOLA_DATEWISE;
		}
		else if("DME_EBOLA  REPORT (Institution wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_EBOLA_INSTWISE;
		}
		else if("DME_EBOLA REPORT (Monthwise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_EBOLA_MONTHWISE;
		}
		else if("DME_EBOLA  REPORT (Year wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_EBOLA_YEARWISE;
		}
		else if("DME_TICK FEVER REPORT (Date Wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_TICK_FEVER_DATEWISE;
		}
		else if("DME_TICK FEVER REPORT (Institution wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_TICK_FEVER_INSTWISE;
		}
		else if("DME_TICK FEVER REPORT (Monthwise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_TICK_FEVER_MONTHWISE;
		}
		else if("DME_TICK FEVER REPORT (Year wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_TICK_FEVER_YEARWISE;
		}
		else if("DME_ACCIDENT AND EMERGENCY".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_ACCIDENT_AND_EMERGENCY;
		}
		else if("DME_BRAIN DEATH DECLARED STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_BRAIN_DEATH_DECLARED_STATISTICS;
		}
		else if("DME_CLEFT LIP AND PALATE SURGERIES STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_CLEFTLIP_PALATE_SURGERIES_STATISTICS;
		}
		else if("DME_CT Scan".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_CT_SCAN;
		}
		else if("DME_TOTAL DELIVERIES".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_TOTAL_DELIVERIES;
		}
		else if("DME_ECG TAKEN".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_ECG_TAKEN;
		}
		else if("DME_IMMUNISATION PERFORMANCE".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_IMMUNISATION_PERFORMANCE;
		}
		else if("DME_STERILISATION PERFORMANCE STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_STERILISATION_PERFORMANCE_STATISTICS;
		}
		else if("DME_INPATIENT MONTHWISE".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INPATIENT_MONTHWISE;
		}
		else if("DME_Institution wise ip report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INSTITUTIONWISE_IP_REPORT;
		}
		else if("DME_SNAKE BITE CASES MONTHWISE STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_SNAKE_BITE_CASES_MONTHWISE_STATISTICS;
		}
		else if("DME_SNAKE BITE CASES STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_SNAKE_BITE_CASES_STATISTICS;
		}
		else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_THREE_PERCENT_RESERVATION_FOR_DIFFERENTLY_ABLED;
		}
		else if("DME_SC ST Representation_Group_A".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_SC_ST_REPRESENTATION_GROUP_A;
		}
		else if("DME_SC ST Representation_Group_B".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_SC_ST_REPRESENTATION_GROUP_B;
		}
		else if("DME_SC ST Representation_Group_C".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_SC_ST_REPRESENTATION_GROUP_C;
		}
		else if("DME_SC ST Representation_Group_D".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_SC_ST_REPRESENTATION_GROUP_D;
		}
		else if("DME_SC ST Representation_Abstract".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_SC_ST_REPRESENTATION_ABSTRACT;
		}
		else if("DME_maternal".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_MATERNAL;
		}
		else if("DME_DEPT INST MONTHWISE SURGERIES REPORT".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DEPT_INST_MONTHWISE_SURGERIES_REPORT;
		}
		else if("DME_Dept wise instwise OP report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DEPTWISE_INSTWISE_OP_REPORT;
		} 
		else if("DME_Institution wise Dept wise report".equalsIgnoreCase(reportName)){ 
			query=ShdrcReportQueryList.DME_REPORTZONE_INSTITUTIONWISE_DEPTWISE_REPORT;
		}
		else if("DME_INFANT DEATHS, NEONATAL DEATHS MONTHWISE".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INFANT_NEONATAL_DEATHS_MONTHWISE;
		}
		else if("DME_Lab Monthwise".equalsIgnoreCase(reportName)){ 
			query=ShdrcReportQueryList.DME_REPORTZONE_LAB_MONTHWISE;
		}
		else if("DME_DOG BITE CASES MONTHWISE STATISTICS".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DOGBITE_CASES_MONTHWISE_STATISTICS;
		}
		else if("DME_DOG BITE STATISTICS".equalsIgnoreCase(reportName)){ 
			query=ShdrcReportQueryList.DME_REPORTZONE_DOGBITE_STATISTICS;
		}
		else if("DME_Cancer cases treated and Mortality".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_CANCER_CASES_TREATED_MORTALITY;
		}
		else if("DME_monthwise inst wise dog bite cases".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_MONTHWISE_INSTWISE_DOGBITE_CASES;
		}
		else if("DME_Instwise month wise Dog bites and Rabies Death".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INSTWISE_MONTHWISE_DOGBITE_RABIESDEATH;
		}
		else if("DME_Genderwise OP IP".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_GENDERWISE_OP_IP;
		}
		else if("DME_Cadaver".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_CADAVER;
		}
		else if("DME_LINE_LIST_DENGUE".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_DENGUE;
		}
		else if("DME_LINE_LIST_H1N1".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_H1N1;
		}
		else if("DME_LINE_LIST_TICK_FEVER".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_TICK_FEVER;
		}
		else if("DME_LINE_LIST_EBOLA".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_EBOLA;
		}
		else if("DME_LINE_LIST_DEATH_DENGUE".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_DEATH_DENGUE;
		}
		else if("DME_LINE_LIST_DEATH_H1N1".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_DEATH_H1N1;
		}
		else if("DME_LINE_LIST_DEATH_TICK_FEVER".equalsIgnoreCase(reportName)) {
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_DEATH_TICK_FEVER;
		}
		else if("DME_LINE_LIST_DEATH_EBOLA".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_LINE_LIST_DEATH_EBOLA;
		}
		else if("Data Availability Institution Wise".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_DATA_AVAILABILITY_INSTWISE;
		}
		else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Inst_wise Post_wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INSTWISE_POSTWISE_RESERVATION;
		}
		else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Inst_wise Consolidate)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INSTWISE_3PERCENT_CONSOLIDATED_REPORT;
		}
		else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Consolidate)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_POSTWISE_3PERCENT_CONSOLIDATED_REPORT;
		}
		else if("DME_Cadre Strength(Inst_wise Post_wise)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INSTWISE_POSTWISE_CADRE_STRENGTH_REPORT;
		}
		else if("DME_Cadre_Strength(Inst_wise Consolidate)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_INSTWISE_CADRE_STRENGTH_REPORT;
		}
		else if("DME_Cadre Strength(Post_wise Consolidate)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DME_REPORTZONE_POSTWISE_CADRE_STRENGTH_REPORT;
		}
		else if(("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Inst_wise)".equalsIgnoreCase(reportName))){
			query=ShdrcReportQueryList.DME_REPORTZONE_3PERCENT_POSTWISE_INSTWISE_REPORT;
		}
		else if(("DME_Cadre Strength(Post_wise Inst_wise)".equalsIgnoreCase(reportName))){
			query=ShdrcReportQueryList.DME_REPORTZONE_POSTWISE__INSTWISE_CADRE_STRENGTH_REPORT;
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;		
		try{						
			if(("DME_DENGUE REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Date Wise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Institution wise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Monthwise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Year wise)".equalsIgnoreCase(reportName))){
				BigDecimal val = null;
				while(resultSet.next()){	
					childList=new JSONArray();			
					String indicator=resultSet.getString(1);
					String generalName=resultSet.getString(2);				
					childList.put(indicator);				
					childList.put(generalName);	
					if(("DME_DENGUE REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Date Wise)".equalsIgnoreCase(reportName))){
						String date= resultSet.getString(3);	
						val=truncateDecimal(resultSet.getDouble(4),2);
						childList.put(date);				
					}
					else if(("DME_DENGUE  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Institution wise)".equalsIgnoreCase(reportName))){
						String institution= resultSet.getString(3);	
						val=truncateDecimal(resultSet.getDouble(4),2);
						childList.put(institution);			
					}
					else if(("DME_DENGUE REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Monthwise)".equalsIgnoreCase(reportName))){
						int year= resultSet.getInt(3);	
						String month= resultSet.getString(4);	
						val=truncateDecimal(resultSet.getDouble(5),2);
						childList.put(year);	
						childList.put(month);	
					}
					else if(("DME_DENGUE  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Year wise)".equalsIgnoreCase(reportName))){
						int year= resultSet.getInt(3);		
						val=truncateDecimal(resultSet.getDouble(4),2);
						childList.put(year);			
					}
					childList.put(val);
					parentList.put(childList);
					}
				}
			else if(("DME_ACCIDENT AND EMERGENCY".equalsIgnoreCase(reportName)) || ("DME_BRAIN DEATH DECLARED STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_CLEFT LIP AND PALATE SURGERIES STATISTICS".equalsIgnoreCase(reportName)) || ("DME_CT Scan".equalsIgnoreCase(reportName)) 
					|| ("DME_TOTAL DELIVERIES".equalsIgnoreCase(reportName)) || ("DME_ECG TAKEN".equalsIgnoreCase(reportName)) 
					|| ("DME_IMMUNISATION PERFORMANCE".equalsIgnoreCase(reportName)) || ("DME_STERILISATION PERFORMANCE STATISTICS".equalsIgnoreCase(reportName))){
				while(resultSet.next()){	
					childList=new JSONArray();	
					String institutionName= resultSet.getString(1);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(2),2);
					childList.put(institutionName);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("DME_DELIVERIES".equalsIgnoreCase(reportName)) || ("DME_IMAGING STUDIES STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_LITHOTRIPSY STATISTICS".equalsIgnoreCase(reportName)) || ("DME_LIVE BIRTHS".equalsIgnoreCase(reportName)) 
					|| ("DME_MAJOR MINOR TOTAL SURGERIES".equalsIgnoreCase(reportName)) || ("DME_RENAL TRANSPLANTS STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_MASTER HEALTH CHECKUP".equalsIgnoreCase(reportName)) || ("DME_Deliveries Maternal Death".equalsIgnoreCase(reportName))
					|| ("DME_Lab Investigation".equalsIgnoreCase(reportName)) || ("DME_lab investigations statistics".equalsIgnoreCase(reportName)) 
					|| ("DME_Bio Chemistry Report".equalsIgnoreCase(reportName)) || ("DME_Micro Biology Report".equalsIgnoreCase(reportName)) 
					|| ("DME_Pathology Report".equalsIgnoreCase(reportName)) || ("DME_Cancer Control Programme".equalsIgnoreCase(reportName))
					|| ("DME_Post Mortem Report".equalsIgnoreCase(reportName)) || ("DME_Blood Bag and Hospital Diet Report".equalsIgnoreCase(reportName)) 
					|| ("DME_Caesarean Report".equalsIgnoreCase(reportName)) || ("DME_INPATIENT MONTHWISE".equalsIgnoreCase(reportName))
					|| ("DME_Institution wise ip report".equalsIgnoreCase(reportName)) || ("DME_DELIVERIES STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_IMAGING STUDIES".equalsIgnoreCase(reportName)) || ("DME_Sterilisation Statistics".equalsIgnoreCase(reportName)) 
					|| ("DME_IMMUNISATION_ NEONATAL AND INFANT DEATHS".equalsIgnoreCase(reportName)) || ("DME_NEONATAL DEATH - Out Born & InBorn".equalsIgnoreCase(reportName)) 
					|| ("DME_LAB results".equalsIgnoreCase(reportName)) || ("DME_SNAKE BITE CASES MONTHWISE STATISTICS".equalsIgnoreCase(reportName))
					|| ("DME_SNAKE BITE CASES STATISTICS".equalsIgnoreCase(reportName)) || ("DME_maternal".equalsIgnoreCase(reportName))
					|| ("DME_Cadaver".equalsIgnoreCase(reportName))){
				while(resultSet.next()){	
					childList=new JSONArray();	
					String firstColData= resultSet.getString(1);	
					String secondColData= resultSet.getString(2);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					childList.put(firstColData);
					childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED".equalsIgnoreCase(reportName)) || ("DME_SC ST Representation_Group_A".equalsIgnoreCase(reportName))
					|| ("DME_SC ST Representation_Group_B".equalsIgnoreCase(reportName)) || ("DME_SC ST Representation_Group_C".equalsIgnoreCase(reportName))
					|| ("DME_SC ST Representation_Group_D".equalsIgnoreCase(reportName)) || ("DME_SC ST Representation_Abstract".equalsIgnoreCase(reportName))){
				String previousPost=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String firstColData= resultSet.getString(1);	
					String secondColData= resultSet.getString(2);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					
					serial=Util.generateSerialNo(serial, previousPost, firstColData);
					previousPost=firstColData;
					childList.put(serial);
					childList.put(firstColData);
					childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("DME_DEPT INST MONTHWISE SURGERIES REPORT".equalsIgnoreCase(reportName)){
				while(resultSet.next()){	
					childList=new JSONArray();	
					String firstColData= resultSet.getString(1);	
					String secondColData= resultSet.getString(2);	
					String thirdColData= resultSet.getString(3);	
					String fourthColData= resultSet.getString(4);
					BigDecimal val=truncateDecimal(resultSet.getDouble(5),2);
					
					childList.put(firstColData);
					childList.put(secondColData);
					childList.put(thirdColData);
					childList.put(fourthColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("DME_Dept wise instwise OP report".equalsIgnoreCase(reportName)) || ("DME_Institution wise Dept wise report".equalsIgnoreCase(reportName)) 
					|| ("DME_INFANT DEATHS, NEONATAL DEATHS MONTHWISE".equalsIgnoreCase(reportName)) || ("DME_Lab Monthwise".equalsIgnoreCase(reportName)) 
					|| ("DME_DOG BITE CASES MONTHWISE STATISTICS".equalsIgnoreCase(reportName)) || ("DME_DOG BITE STATISTICS".equalsIgnoreCase(reportName)) 
					|| ("DME_Cancer cases treated and Mortality".equalsIgnoreCase(reportName)) || ("DME_monthwise inst wise dog bite cases".equalsIgnoreCase(reportName))
					|| ("DME_Instwise month wise Dog bites and Rabies Death".equalsIgnoreCase(reportName)) || ("DME_Genderwise OP IP".equalsIgnoreCase(reportName))){
				while(resultSet.next()){	
					childList=new JSONArray();	
					String firstColData= resultSet.getString(1);	
					String secondColData= resultSet.getString(2);	
					String thirdColData= resultSet.getString(3);
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(firstColData);
					childList.put(secondColData);
					childList.put(thirdColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("DME_LINE_LIST_DENGUE".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_H1N1".equalsIgnoreCase(reportName)) 
					|| ("DME_LINE_LIST_TICK_FEVER".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_EBOLA".equalsIgnoreCase(reportName))
					|| ("DME_LINE_LIST_DEATH_DENGUE".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_DEATH_H1N1".equalsIgnoreCase(reportName))
					 || ("DME_LINE_LIST_DEATH_TICK_FEVER".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_DEATH_EBOLA".equalsIgnoreCase(reportName))){
				while(resultSet.next()){	
					childList=new JSONArray();	
					String firstColData= resultSet.getString(1);	
					String secondColData= resultSet.getString(2);	
					String thirdColData= resultSet.getString(3);
					String fourthColData= resultSet.getString(4);	
					String fifthColData= resultSet.getString(5);	
					String sixthColData= resultSet.getString(6);
					String seventhColData= resultSet.getString(7);	
					String eighthColData= resultSet.getString(8);	
					
					if(firstColData!=null)
						childList.put(firstColData);
					else
						childList.put("");
					if(secondColData!=null)
						childList.put(secondColData);
					else
						childList.put("");
					if(thirdColData!=null)
						childList.put(thirdColData);
					else
						childList.put("");
					if(fourthColData!=null)
						childList.put(fourthColData);
					else
						childList.put("");
					if(fifthColData!=null)
						childList.put(fifthColData);
					else
						childList.put("");
					if(sixthColData!=null)
						childList.put(sixthColData);
					else
						childList.put("");
					if(seventhColData!=null)
						childList.put(seventhColData);
					else
						childList.put("");
					if(eighthColData!=null)
						childList.put(eighthColData);
					else
						childList.put("");
					
					if(("DME_LINE_LIST_DENGUE".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_H1N1".equalsIgnoreCase(reportName)) 
							|| ("DME_LINE_LIST_TICK_FEVER".equalsIgnoreCase(reportName)) || ("DME_LINE_LIST_EBOLA".equalsIgnoreCase(reportName))
							|| ("DME_LINE_LIST_DEATH_DENGUE".equalsIgnoreCase(reportName))){
						String ninthColData= resultSet.getString(9);
						String tenthColData= resultSet.getString(10);	
						String eleventhColData= resultSet.getString(11);	
						
						childList.put(ninthColData);
						childList.put(tenthColData);
						childList.put(eleventhColData);
					}					
					parentList.put(childList);
				}
			}
			else if("Data Availability Institution Wise".equalsIgnoreCase(reportName)){
				while(resultSet.next()){
					childList=new JSONArray();
					childList.put(resultSet.getString(1));
					childList.put(resultSet.getString(2));
					childList.put(resultSet.getString(3));
					parentList.put(childList);
				}
			}
			else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Inst_wise Post_wise)".equalsIgnoreCase(reportName)){
				String previousPost=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String instName= resultSet.getString(2);	
					String firstColData= resultSet.getString(4);	
					String secondColData= resultSet.getString(5);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					serial=Util.generateSerialNo(serial, previousPost, firstColData);
					previousPost=firstColData;
					childList.put(serial);
					childList.put(instName);
					childList.put(firstColData);
					childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Inst_wise Consolidate)".equalsIgnoreCase(reportName)){
				String previousPost=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String instName= resultSet.getString(1);	
					String firstColData= resultSet.getString(3);	
					//String secondColData= resultSet.getString(5);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					serial=Util.generateSerialNo(serial, previousPost, instName);
					previousPost=instName;
					childList.put(serial);
					childList.put(instName);
					childList.put(firstColData);
					//childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Consolidate)".equalsIgnoreCase(reportName)){
				String previousPost=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String postName= resultSet.getString(1);	
					String firstColData= resultSet.getString(3);	
					//String secondColData= resultSet.getString(5);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					serial=Util.generateSerialNo(serial, previousPost, postName);
					previousPost=postName;
					childList.put(serial);
					childList.put(postName);
					childList.put(firstColData);
					//childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("DME_Cadre Strength(Inst_wise Post_wise)".equalsIgnoreCase(reportName)){
				String previousPost=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String instName= resultSet.getString(1);	
					String firstColData= resultSet.getString(3);	
					String secondColData= resultSet.getString(4);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);
					
					serial=Util.generateSerialNo(serial, previousPost, firstColData);
					previousPost=firstColData;
					childList.put(serial);
					childList.put(instName);
					childList.put(firstColData);
					childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("DME_Cadre_Strength(Inst_wise Consolidate)".equalsIgnoreCase(reportName)){
				String previousPost=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String instName= resultSet.getString(1);	
					String firstColData= resultSet.getString(3);	
					//String secondColData= resultSet.getString(5);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					serial=Util.generateSerialNo(serial, previousPost, instName);
					previousPost=instName;
					childList.put(serial);
					childList.put(instName);
					childList.put(firstColData);
					//childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("DME_Cadre Strength(Post_wise Consolidate)".equalsIgnoreCase(reportName)){
				String previousPost=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String postName= resultSet.getString(1);	
					String firstColData= resultSet.getString(3);	
					//String secondColData= resultSet.getString(5);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					serial=Util.generateSerialNo(serial, previousPost, postName);
					previousPost=postName;
					childList.put(serial);
					childList.put(postName);
					childList.put(firstColData);
					//childList.put(secondColData);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Inst_wise)".equalsIgnoreCase(reportName)) || ("DME_Cadre Strength(Post_wise Inst_wise)".equalsIgnoreCase(reportName))){
				String previousInst=null;
				int serial=1;
				while(resultSet.next()){	
					childList=new JSONArray();	
					String postName= resultSet.getString(1);	
					String firstColData= resultSet.getString(2);	
					String secondColData= resultSet.getString(4);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);
					
					serial=Util.generateSerialNo(serial, previousInst, firstColData);
					previousInst=firstColData;
					childList.put(serial);
					childList.put(postName);
					childList.put(firstColData);
					childList.put(secondColData);
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
	
	public List<CommonStringList> getInstitutionTypeList(){
		List<CommonStringList> institutionTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			institutionTypeList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DME_REPORT_INSTITUTION_TYPE);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	institutionTypeList.add(commonStringList);
	        }
		} catch (SQLException e) {
			
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
	
	public List<CommonStringList> getInstitutionList(String institutionType){
		List<CommonStringList> institutionList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			institutionList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DME_REPORT_INSTITUTION);
			preparedStatement.setString(1, institutionType);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	institutionList.add(commonStringList);
	        }
		} catch (SQLException e) {
			
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
	
	public StringBuilder getIdList(String idMethod){
		StringBuilder strBuilderList=new StringBuilder();
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			if("institution".equalsIgnoreCase(idMethod)){
				preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DME_REPORT_INSTITUTION);
			}
			else if("post".equalsIgnoreCase(idMethod)){
				preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DME_REPORT_POST);
			}
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	strBuilderList.append(resultSet.getInt(1));				
				strBuilderList.append(",");	        	
	        }
		} catch (SQLException e) {
			
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
			return strBuilderList;
	}
	
	public List<String> getFileNames(String frequency) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> fileNames=new ArrayList<String>();
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select distinct \"File_Sub_Name\" from shdrc_dwh.\"File_Master_Detailed\" where \"Frequency\"=?");
		preparedStatement.setString(1, frequency);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {	
			String temp=resultSet.getString(1);
			fileNames.add(temp);
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
		return fileNames;
	}
	
	public List<CommonStringList> getPostTypeList(){
		List<CommonStringList> postTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			postTypeList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DME_REPORT_POST_TYPE);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	postTypeList.add(commonStringList);
	        }
		} catch (SQLException e) {
			
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
			return postTypeList;
	}
	
	public List<CommonStringList> getPostList(String postType){
		List<CommonStringList> postList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			postList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DME_REPORT_POST);
			preparedStatement.setString(1, postType);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setId(resultSet.getInt(1));
	        	commonStringList.setName(resultSet.getString(2));
	        	postList.add(commonStringList);
	        }
		} catch (SQLException e) {
			
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
			return postList;
	}
}
