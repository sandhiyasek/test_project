package gov.shdrc.reports.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.ISBCSReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SBCSReportDAOJdbc implements ISBCSReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		String indicator=null;
		int value;
		try {		
			if("Details of Eye Donation".equalsIgnoreCase(analysisReportName)|| "Details of Other Eye Diseases Yearly".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.SBCS_ANALYSISZONE_MULTISERIES;				
			}
			else{
				sr = ShdrcReportQueryList.SBCS_ANALYSISZONE;		
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
				if("Details of Eye Donation".equalsIgnoreCase(analysisReportName)|| "Details of Other Eye Diseases Yearly".equalsIgnoreCase(analysisReportName)){
					indicator=resultSet.getString(1);
					String category = resultSet.getString(2);
					value=resultSet.getInt(3);
				}
				else{				
					indicator=resultSet.getString(1);
					value=resultSet.getInt(2);
				}				
				jsonObject.put("label",indicator);
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

	//Indicator Zone	
	public List<String> getIndicatorCategories(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> categoryList=new ArrayList<String>();
		try{
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.SBCS_GETGENERALCATEGORY);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			String cat=resultSet.getString(1);						
			categoryList.add(cat);
		}			
		}catch (SQLException e) {
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
		return categoryList;
	}
				
	public JSONArray getIndicatorPvtStateData(int directorateId,String category,String fromMonth,int fromYear,String toMonth,int toYear,String username){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray IndicatorPvtStateData=null;
		try{
			IndicatorPvtStateData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.SBCS_GETSTATELIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, category);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, fromMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setString(6, toMonth);
			preparedStatement.setString(7, username);
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

	public JSONArray getIndicatorPvtDistrictData(int directorateId,String category,String indicator, String generalCategory,
			String fromMonth,int fromYear,String toMonth,int toYear,String username){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray IndicatorPvtDistrictData=null;
		try{
			IndicatorPvtDistrictData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.SBCS_GETDISTRICTLIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, category);
			preparedStatement.setString(3, indicator);
			preparedStatement.setString(4, generalCategory);
			preparedStatement.setInt(5, fromYear);
			preparedStatement.setString(6, fromMonth);
			preparedStatement.setInt(7, toYear);
			preparedStatement.setString(8, toMonth);
			preparedStatement.setString(9, username);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 	
			JSONObject obj = new JSONObject();
			obj.put("District", resultSet.getString(3));
			obj.put("GeneralName", resultSet.getString(4));
			obj.put("Value", resultSet.getInt(5));
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
	public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory,String indicator,String generalCategory,
			String district, String fromMonth,int fromYear,String toMonth,int toYear,String username){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray IndicatorPvtInstitutionData=null;
		try{
			IndicatorPvtInstitutionData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.SBCS_GETINSTITUTIONLIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, indicatorCategory);
			preparedStatement.setString(3, indicator);			
			preparedStatement.setString(4, generalCategory);
			preparedStatement.setString(5, district);
			preparedStatement.setInt(6, fromYear);
			preparedStatement.setString(7, fromMonth);
			preparedStatement.setInt(8, toYear);
			preparedStatement.setString(9, toMonth);
			preparedStatement.setString(10, username);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 	
			JSONObject obj = new JSONObject();
			obj.put("Institution",resultSet.getString(4));
			obj.put("GeneralName",resultSet.getString(5));
			obj.put("Value",resultSet.getString(6));
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
		
	public List<String> getGeneralCategory(String indicator){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> categoryList=null;
		try{
			categoryList=new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.SBCS_GETINDICATORCATEGORY);
			preparedStatement.setString(1, indicator);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 									
			categoryList.add(resultSet.getString(2));
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
		return categoryList;
	}
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_sbcs_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_sbcs_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_sbcs_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_sbcs_main(?,?,?,?)");
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
			if(("Abstract of Cataract Surgeries performed".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Indicator");
				childList.put("General Type");
				childList.put("General Name");
				childList.put("Institution");
				childList.put("Value");	
			}
			else if(("Doctors Performance".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Indicator");
				childList.put("Institution");
				childList.put("Value");	
			}
			else if(("Eye Donation".equalsIgnoreCase(reportName)) || ("Financial Management Report".equalsIgnoreCase(reportName))
					 || ("Receipts and Expenditure for the Months".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Value");	
			}
			else if(("Liability Performance Report for the Period".equalsIgnoreCase(reportName)) || ("Physical Performance Report for the Period".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Name of the NGO");
				childList.put("Year");
				childList.put("Month");
				childList.put("Value");	
			}
			else if(("Performance of Cataract Surgery - Govt Sector".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Name of the Hospital");
				childList.put("Indicator");
				childList.put("Year");
				childList.put("Month");
				childList.put("Value");	
			}
			else if(("Performance of Cataract Surgery - NGO Sector".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Name of the NGO");
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Value");	
			}
			else if(("Receipts and Expenditure for the Months-Overall".equalsIgnoreCase(reportName)) || ("School Eye Screening".equalsIgnoreCase(reportName))
					 || ("Other Eye Disease(Yearly)".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Indicator");
				childList.put("Value");
			}
			else if(("Cornea Collection During the Year".equalsIgnoreCase(reportName)) || ("Monthwise Performance of Cataract Surgery During Year".equalsIgnoreCase(reportName))
					 || ("Cataract Performance for the Year".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("State Name");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if("School Eye Screening(Yearly)".equalsIgnoreCase(reportName)){
				childList=new JSONArray();	
				childList.put("State Name");
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Value");
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("Abstract of Cataract Surgeries performed".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_ABSTRACT_CATARACT_SURGERIES_PERFORMED;
		}
		else if("Doctors Performance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_DOCTORS_PERFORMANCE;
		}
		else if("Eye Donation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_EYE_DONATION;
		}
		else if("Financial Management Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_FINANCIAL_MANAGEMENT_REPORT;
		}
		else if("Liability Performance Report for the Period".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_LIABILITY_PERFORMANCE_REPORT;
		}
		else if("Performance of Cataract Surgery - Govt Sector".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_CATARACT_SURGERY_PERFORMANCE_GOVT;
		}
		else if("Performance of Cataract Surgery - NGO Sector".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_CATARACT_SURGERY_PERFORMANCE_NGO;
		}
		else if("Physical Performance Report for the Period".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_PHYSICAL_PERFORMANCE_REPORT;
		}
		else if("Receipts and Expenditure for the Months".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_MONTHS_RECEIPTS_EXPENDITURE;
		}
		else if("Receipts and Expenditure for the Months-Overall".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_MONTHS_RECEIPTS_EXPENDITURE_OVERALL;
		}
		else if("School Eye Screening".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_SCHOOL_EYE_SCREENING;
		}
		else if("Cornea Collection During the Year".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_CORNEA_COLLECTION_DURING_THE_YEAR;
		}
		else if("Monthwise Performance of Cataract Surgery During Year".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_MONTHWISE_CATARACT_SURGERY_PERFORMANCE_FOR_YEAR;
		}
		else if("Other Eye Disease(Yearly)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_OTHER_EYE_DISEASE_YEARLY;
		}
		else if("Cataract Performance for the Year".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_CATARACT_PERFORMANCE_FOR_YEAR;
		}
		else if("School Eye Screening(Yearly)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.SBCS_REPORTZONE_SCHOOL_EYE_SCREENING_YEARLY;
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{	
			if(("Abstract of Cataract Surgeries performed".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();		
					String institutionName=resultSet.getString(1);	
					String indicator=resultSet.getString(2);	
					String generalType=resultSet.getString(3);	
					String generalName=resultSet.getString(4);						
					BigDecimal val=truncateDecimal(resultSet.getDouble(5),2);
					
					childList.put(indicator);		
					childList.put(generalType);		
					childList.put(generalName);		
					childList.put(institutionName);			
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Doctors Performance".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();		
					String institutionName=resultSet.getString(1);	
					String indicator=resultSet.getString(2);					
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					
					childList.put(indicator);	
					childList.put(institutionName);			
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Eye Donation".equalsIgnoreCase(reportName)) || ("Financial Management Report".equalsIgnoreCase(reportName))
					 || ("Receipts and Expenditure for the Months".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();		
					String indicator=resultSet.getString(1);	
					String generalName=resultSet.getString(2);										
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					
					childList.put(indicator);	
					childList.put(generalName);			
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Liability Performance Report for the Period".equalsIgnoreCase(reportName)) || ("Physical Performance Report for the Period".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();		
					String institutionName=resultSet.getString(1);	
					int year=resultSet.getInt(2);	
					String month=resultSet.getString(3);										
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(institutionName);	
					childList.put(year);	
					childList.put(month);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Performance of Cataract Surgery - Govt Sector".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();		
					String institutionName=resultSet.getString(1);	
					String indicator=resultSet.getString(2);	
					int year=resultSet.getInt(3);	
					String month=resultSet.getString(4);										
					BigDecimal val=truncateDecimal(resultSet.getDouble(5),2);
					
					childList.put(institutionName);	
					childList.put(indicator);	
					childList.put(year);	
					childList.put(month);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Performance of Cataract Surgery - NGO Sector".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();		
					String institutionName=resultSet.getString(1);	
					String indicator=resultSet.getString(2);	
					String generalName=resultSet.getString(3);										
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(institutionName);	
					childList.put(indicator);	
					childList.put(generalName);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Receipts and Expenditure for the Months-Overall".equalsIgnoreCase(reportName)) || ("School Eye Screening".equalsIgnoreCase(reportName))
					|| ("Other Eye Disease(Yearly)".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);									
					BigDecimal val=truncateDecimal(resultSet.getDouble(2),2);
			
					childList.put(indicator);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Cornea Collection During the Year".equalsIgnoreCase(reportName)) || ("Monthwise Performance of Cataract Surgery During Year".equalsIgnoreCase(reportName))
					|| ("Cataract Performance for the Year".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String stateName=resultSet.getString(1);							
					String indicator=resultSet.getString(2);							
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
			
					childList.put(stateName);
					childList.put(indicator);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("School Eye Screening(Yearly)".equalsIgnoreCase(reportName)){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String stateName=resultSet.getString(1);							
					String indicator=resultSet.getString(2);	
					String generalName=resultSet.getString(3);	
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
			
					childList.put(stateName);
					childList.put(indicator);
					childList.put(generalName);
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

	public JSONArray getNewSbcsDashboardData(int directorateId,
			String reportName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray jsonArray=new JSONArray();
		String query=null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			int temp = reportName.lastIndexOf("-");
			String newReportName = reportName.substring(temp+1).trim();
			query=getSbcsDashboardQuery(reportName, newReportName);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2,reportName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4,month);
			preparedStatement.setString(5,loggedUser);
			resultSet = preparedStatement.executeQuery();
			jsonArray=getSbcsDashboardData(reportName,newReportName,resultSet);
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
	public String getSbcsDashboardQuery(String reportName,String newReportName){
		String query=null;
		if("State wise".equalsIgnoreCase(newReportName)){
			if("Eye Donation - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_eye_donation(?,?,?,?,?)";
			}
			if("Other Eye Disease - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_other_eye_diseases(?,?,?,?,?)";
			}
			if("Finance - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_finance(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - GOVT Sector - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_perf_cataract_govt(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - NGO Sector - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_perf_cataract_ngo(?,?,?,?,?)";
			}
			if("School Eye Screening - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_school_eye_screening(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - Private - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_perf_cataract_pvt(?,?,?,?,?)";
			}
			if("Abstract of Cataract Surgeries Performed - State wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_state_cataract_perf(?,?,?,?,?)";
			}
		}
		if("District wise".equalsIgnoreCase(newReportName)){
			if("Eye Donation - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_eye_donation(?,?,?,?,?)";
			}
			if("Other Eye Disease - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_other_eye_diseases(?,?,?,?,?)";
			}
			if("Finance - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_finance(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - GOVT Sector - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_perf_cataract_govt(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - NGO Sector - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_perf_cataract_ngo(?,?,?,?,?)";
			}
			if("School Eye Screening - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_school_eye_screening(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - Private - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_perf_cataract_pvt(?,?,?,?,?)";
			}
			if("Abstract of Cataract Surgeries Performed - District wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_dist_cataract_perf(?,?,?,?,?)";
			}
		}
		if("Institution wise".equalsIgnoreCase(newReportName)){
			if("Eye Donation - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_eye_donation(?,?,?,?,?)";
			}
			if("Other Eye Disease - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_other_eye_diseases(?,?,?,?,?)";
			}
			if("Finance - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_finance(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - GOVT Sector - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_perf_cataract_govt(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - NGO Sector - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_perf_cataract_ngo(?,?,?,?,?)";
			}
			if("School Eye Screening - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_school_eye_screening(?,?,?,?,?)";
			}
			if("Performance of Cataract Surgery - Private - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_perf_cataract_pvt(?,?,?,?,?)";
			}
			if("Abstract of Cataract Surgeries Performed - Institution wise".equalsIgnoreCase(reportName)){
				query = "select * from shdrc_dwh.sbcs_dash_inst_cataract_perf(?,?,?,?,?)";
			}
		}
		return query;
	}
	public JSONArray getSbcsDashboardData(String reportName,String newReportName,ResultSet resultSet) throws SQLException, JSONException{
		JSONArray sbcsData=new JSONArray();
		if("State wise".equals(newReportName)){			
			if("Eye Donation - State wise".equalsIgnoreCase(reportName)){
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator", resultSet.getString(1));
					jsonObject.put("General Name", resultSet.getString(2));
					jsonObject.put("Value", resultSet.getDouble(3));					
					sbcsData.put(jsonObject);
			      }
			}
			else if("Finance - State wise".equalsIgnoreCase(reportName)){
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator Category", resultSet.getString(1));
					jsonObject.put("Indicator Name", resultSet.getString(2));
					jsonObject.put("Value", resultSet.getDouble(3));					
					sbcsData.put(jsonObject);
			      }
			}
			else{
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator", resultSet.getString(1));
					jsonObject.put("Value", resultSet.getDouble(2));					
					sbcsData.put(jsonObject);
			      }
			}
		}
		if("District wise".equals(newReportName)){			
			if("Eye Donation - State wise".equalsIgnoreCase(reportName)){
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
		        	jsonObject.put("Indicator", resultSet.getString(1));
					jsonObject.put("General Name", resultSet.getString(2));
					jsonObject.put("District", resultSet.getString(3));
					jsonObject.put("Value", resultSet.getDouble(4));					
					sbcsData.put(jsonObject);
			      }
			}
			else if("Finance - State wise".equalsIgnoreCase(reportName)){
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator Category", resultSet.getString(1));
					jsonObject.put("Indicator Name", resultSet.getString(2));
					jsonObject.put("District", resultSet.getString(3));
					jsonObject.put("Value", resultSet.getDouble(4));					
					sbcsData.put(jsonObject);
			      }
			}
			else{
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator", resultSet.getString(1));
					jsonObject.put("District", resultSet.getString(2));
					jsonObject.put("Value", resultSet.getDouble(3));					
					sbcsData.put(jsonObject);
			      }
			}
		}
		if("Institution wise".equals(newReportName)){
			if("Eye Donation - State wise".equalsIgnoreCase(reportName)){
				while (resultSet.next()) {
					JSONObject jsonObject=new JSONObject();		        	
		        	jsonObject.put("Indicator", resultSet.getString(1));
					jsonObject.put("General Name", resultSet.getString(2));
					jsonObject.put("District", resultSet.getString(3));
					jsonObject.put("Institution", resultSet.getString(4));
					jsonObject.put("Value", resultSet.getDouble(5));					
					sbcsData.put(jsonObject);
			      }
			}
			else if("Finance - State wise".equalsIgnoreCase(reportName)){
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator Category", resultSet.getString(1));
					jsonObject.put("Indicator Name", resultSet.getString(2));
					jsonObject.put("District", resultSet.getString(3));
					jsonObject.put("Institution", resultSet.getString(4));
					jsonObject.put("Value", resultSet.getDouble(5));					
					sbcsData.put(jsonObject);
			      }
			}
			else{
				while (resultSet.next()) {
		        	JSONObject jsonObject=new JSONObject();		        	
					jsonObject.put("Indicator", resultSet.getString(1));
					jsonObject.put("District", resultSet.getString(2));
					jsonObject.put("Institution", resultSet.getString(3));
					jsonObject.put("Value", resultSet.getDouble(4));					
					sbcsData.put(jsonObject);
			      }
			}
		}
		return sbcsData;
	}
}
