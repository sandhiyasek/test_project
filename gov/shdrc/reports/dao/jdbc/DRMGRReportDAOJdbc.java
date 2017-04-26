package gov.shdrc.reports.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;







import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.IDRMGRReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;
import gov.shdrc.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DRMGRReportDAOJdbc implements IDRMGRReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_mgr_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_mgr_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_mgr_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_mgr_main(?,?,?,?)");
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
	@Override
	public JSONArray getDRMGRIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray jsonArray=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_dept_mgr(?,?,?,?,?,?)");
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2,indicatorCategory);
			preparedStatement.setString(3,"");
			preparedStatement.setInt(4, year);
			preparedStatement.setString(5,month);
			preparedStatement.setString(6,loggedUser);
			resultSet = preparedStatement.executeQuery();
			 while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator", resultSet.getString(1));
					jsonObject.put("State", resultSet.getString(2));	
					jsonObject.put("Department", resultSet.getString(3));
					String threSholdColor=getThrosholdColor(resultSet.getString(5).charAt(0));
					jsonObject.put("Threshold", threSholdColor);
					jsonObject.put("ThresholdTooltip", resultSet.getString(6));
					jsonObject.put("Value", resultSet.getDouble(4));					
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
			String indicatorCategory,String indicatorName, String departmentName, int year,
			String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from  shdrc_dwh.dash_inst_mgr(?,?,?,?,?,?,?)");
			preparedStatement.setInt(1, directorateID);
			preparedStatement.setString(2,indicatorCategory);
			preparedStatement.setString(3,indicatorName);
			preparedStatement.setString(4,departmentName);
			preparedStatement.setInt(5, year);
			preparedStatement.setString(6,month);
			preparedStatement.setString(7,loggedUser);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				    /*childList=new JSONArray();				    			   
					String department=resultSet.getString(3);
					String institution=resultSet.getString(4);
					Double val=resultSet.getDouble(5);
					String color=resultSet.getString(6);
					String zone=resultSet.getString(7);
					childList.put(department);
					childList.put(institution);				
					childList.put(val);
					childList.put(zone);
					childList.put(getThrosholdColor(color.charAt(0)));
					parentList.put(childList);*/
					JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Institution", resultSet.getString(4));
					jsonObject.put("Department", resultSet.getString(3));
					jsonObject.put("Value", resultSet.getDouble(5));
					String threSholdColor=getThrosholdColor(resultSet.getString(6).charAt(0));
					jsonObject.put("Threshold", threSholdColor);
					jsonObject.put("ThresholdTooltip", resultSet.getString(7));
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
	public JSONArray getFreeHandZoneData(int year){		
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
			 preparedStatement = connection.prepareStatement(ShdrcReportQueryList.ESI_FREEHANDZONE_FULL_INFO);
			 preparedStatement.setInt(1, year);				
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
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,
			String userName){
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
			
			if("Degrees Awarded in the Convocation".equalsIgnoreCase(reportName)){
				childList.put("Course Category");
				childList.put("Course Name");
				childList.put("Branch Name");	
				childList.put("Indicator");
				childList.put("Value");	
			}
			else if(("PHD Awarded".equalsIgnoreCase(reportName))){		
				childList.put("Indicator");
				childList.put("Branch");
				childList.put("General Code");
				childList.put("Value");		
			}
			else if(("Students Admission details (Institution wise)".equalsIgnoreCase(reportName))){
				childList.put("Institution Name");
				childList.put("Indicator");
				childList.put("Course Speciality");
				childList.put("Course Name");
				childList.put("Value");			
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("PHD Awarded".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DRMGR_REPORTZONE_PHD_AWARDED;
		}		
		else if("Degrees Awarded in the Convocation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DRMGR_REPORTZONE_DEGREES_AWARDED_IN_CONVOCATION;
		}
		else if("Students Admission details (Institution wise)".equalsIgnoreCase(reportName)){		
			query=ShdrcReportQueryList.DRMGR_REPORTZONE_STUDENTS_ADMITTED;
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{
			if("Degrees Awarded in the Convocation".equalsIgnoreCase(reportName)){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String course=resultSet.getString(2);		
					String coursecategory=resultSet.getString(3);		
					String generalType=resultSet.getString(4);		
					BigDecimal val=truncateDecimal(resultSet.getDouble(5),2);
					
					childList.put(coursecategory);		
					childList.put(course);		
					childList.put(generalType);				
					childList.put(indicator);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("PHD Awarded".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String generalType=resultSet.getString(2);		
					String branchCode=resultSet.getString(3);		
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(indicator);				
					childList.put(generalType);	
					childList.put(branchCode);	
					childList.put(val);
					parentList.put(childList);
				}
			}	
			else if(("Students Admission details (Institution wise)".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String institutionName=resultSet.getString(1);
					String indicator=resultSet.getString(2);
					String courseSpeciality=resultSet.getString(3);				
					String generalType=resultSet.getString(4);				
					BigDecimal val=truncateDecimal(resultSet.getDouble(5),2);
					
					childList.put(institutionName);	
					childList.put(indicator);	
					childList.put(courseSpeciality);	
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
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {	
			if("Trend of Phd Awarded".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DRMGR_ANALYSISZONE_PHD_AWARDED;		
			}
			else if("Students Admitted in Top 10 Institutions-Year wise".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DRMGR_ANALYSISZONE_STUDENTS_ADMITTED;		
				}
			else if("Number of Degrees Awarded - Across Medical Colleges".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DRMGR_ANALYSISZONE_DEGREES_AWARDED;		
				}
			else if("Comparison of various institutions -Year wise".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DRMGR_ANALYSISZONE_INSTITUTIONS_COMPARISON;		
				}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, analysisReportName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next()){ 	
				String label=null;	
				int value;
				JSONObject jsonObject=new JSONObject();	
				if("Comparison of various institutions -Year wise".equalsIgnoreCase(analysisReportName)){
					label= resultSet.getString(1);
					value=resultSet.getInt(2);
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
}
