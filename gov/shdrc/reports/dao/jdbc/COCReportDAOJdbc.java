package gov.shdrc.reports.dao.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.ICOCReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class COCReportDAOJdbc implements ICOCReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_coc_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_coc_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_coc_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_coc_main(?,?,?,?)");
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
			if(("Control of Communicable Diseases".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Zone");
				childList.put("Institution Name");
				childList.put("Indicator");
				childList.put("Year");
				childList.put("Month");
				childList.put("Value");
			}
			else if(("Drinking water quality".equalsIgnoreCase(reportName)) || ("UPHC-Treatment Services".equalsIgnoreCase(reportName))
					 || ("Vector Control Activities".equalsIgnoreCase(reportName)) || ("Regulatory functions".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Zone");
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Value");
			}
			else if(("Performance of Diagnostic Centre".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Zone");
				childList.put("General Name");
				childList.put("Indicator");
				childList.put("Year");
				childList.put("Month");
				childList.put("Value");
			}
			else if(("Veterinary Section".equalsIgnoreCase(reportName)) || ("Vital Statistics".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Zone");
				childList.put("Indicator");
				childList.put("Value");
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("Control of Communicable Diseases".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_CONTROL_OF_COMMUNICABLE_DISEASES;
		}
		else if("Drinking water quality".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_DRINKING_WATER_QUALITY;
		}
		else if("Performance of Diagnostic Centre".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_DIAGNOSTIC_CENTRE_PERFORMANCE;
		}
		else if("UPHC-Treatment Services".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_UPHC_TREATMENT_SERVICES;
		}
		else if("Veterinary Section".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_VETERIINARY_SECTION;
		}
		else if("Vital Statistics".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_VITAL_STATISTICS;
		}
		else if("Vector Control Activities".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_VECTOR_CONTROL_ACTIVITIES;
		}
		else if("Regulatory functions".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.COC_REPORTZONE_REGULATORY_FUNCTIONS;
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{	
			if(("Control of Communicable Diseases".equalsIgnoreCase(reportName))){
		 		while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String zoneName=resultSet.getString(2);
					String institutionName=resultSet.getString(3);		
					int year=resultSet.getInt(4);		
					String month=resultSet.getString(5);					
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					childList.put(zoneName);
					childList.put(institutionName);
					childList.put(indicator);
					childList.put(year);
					childList.put(month);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Drinking water quality".equalsIgnoreCase(reportName)) || ("UPHC-Treatment Services".equalsIgnoreCase(reportName))
					 || ("Vector Control Activities".equalsIgnoreCase(reportName)) || ("Regulatory functions".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String zoneName=resultSet.getString(1);
					String indicator=resultSet.getString(2);
					String generalName=resultSet.getString(3);				
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(zoneName);	
					childList.put(indicator);	
					childList.put(generalName);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("Performance of Diagnostic Centre".equalsIgnoreCase(reportName)){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String zoneName=resultSet.getString(1);
					String indicator=resultSet.getString(2);					
					String generalName=resultSet.getString(3);		
					int year=resultSet.getInt(4);		
					String month=resultSet.getString(5);					
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					childList.put(zoneName);
					childList.put(generalName);
					childList.put(indicator);
					childList.put(year);
					childList.put(month);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Veterinary Section".equalsIgnoreCase(reportName)) || ("Vital Statistics".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String zoneName=resultSet.getString(1);
					String indicator=resultSet.getString(2);				
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					
					childList.put(zoneName);	
					childList.put(indicator);
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
			if("Birth & Death (Yearly comparison)".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.COC_ANALYSISZONE;				
			}
			else if("Death Report".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.COC_ANALYSISZONE_DEATH_REPORT;				
			}
			else if("Cases Confirmed (Yearly Comparison)".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.COC_ANALYSISZONE_CASES_CONFIRMED;				
			}
			else if("Delivery Report  (Yearly)".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.COC_ANALYSISZONE_DELIVERY_REPORT;				
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
				String label=null;
				int value=0;
				JSONObject jsonObject=new JSONObject();		
				if("Birth & Death (Yearly comparison)".equalsIgnoreCase(analysisReportName)){
					String seriesName=resultSet.getString(1);
					label=resultSet.getString(2);
					value=resultSet.getInt(3);
					jsonObject.put("seriesName",seriesName);
				}
				else{
					label=resultSet.getString(1);
					value=resultSet.getInt(2);
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
