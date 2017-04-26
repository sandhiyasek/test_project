package gov.shdrc.reports.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gov.shdrc.reports.dao.INLEPReportDAO;
import gov.shdrc.util.ShdrcReportQueryList;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.ShdrcReportQueryList;





import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
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

@Service
public class NLEPReportDAOJdbc implements INLEPReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		int value=0;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {		
			if("Top 10 - New Leprosy Cases - Districtwise".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.NLEP_ANALYSISZONE;				
			}
			else if("Analysis - Child Rate".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.NLEP_ANALYSISZONE_MULTISERIES;		
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
				if("Top 10 - New Leprosy Cases - Districtwise".equalsIgnoreCase(analysisReportName)){
					String district=resultSet.getString(2);
					value=resultSet.getInt(3);
					jsonObject.put("label",district);
				}
				else if("Analysis - Child Rate".equalsIgnoreCase(analysisReportName)){
					String monthName=resultSet.getString(1);
					value=resultSet.getInt(4);
					jsonObject.put("label",monthName);
				}
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
		JSONArray analysisZoneThirdSeriesData=new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {		
			sr = ShdrcReportQueryList.NLEP_ANALYSISZONE_MULTISERIES;				
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
				JSONObject jsonObject3=new JSONObject();			
				String monthName=resultSet.getString(1);
				int femalecase=resultSet.getInt(2);
				jsonObject1.put("label",monthName);
				jsonObject1.put("value",femalecase);
				jsonObject1.put("seriesName","Cumulative Female case");
				analysisZoneFirstSeriesData.put(jsonObject1);
				
				int leprosyCase=resultSet.getInt(3);
				jsonObject2.put("label",monthName);
				jsonObject2.put("value",leprosyCase);	
				jsonObject2.put("seriesName","Cumulative new Leprosy case");
				analysisZoneSecondSeriesData.put(jsonObject2);
				
				int femaleRate=resultSet.getInt(4);
				jsonObject3.put("label",monthName);
				jsonObject3.put("value",femaleRate);
				jsonObject3.put("seriesName","Female Rate");
				analysisZoneThirdSeriesData.put(jsonObject3);
				
				finalList.put("analysisZoneFirstSeriesData", analysisZoneFirstSeriesData);
				finalList.put("analysisZoneSecondSeriesData", analysisZoneSecondSeriesData);
				finalList.put("analysisZoneThirdSeriesData", analysisZoneThirdSeriesData);
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
			childList.put("Indicator");
			if(("ULF 07 Page 1".equalsIgnoreCase(reportName)) || ("ULF 07 Page 2".equalsIgnoreCase(reportName))){
				childList.put("General Type");	
				childList.put("Value Type");	
			}
			else if(("ULF 07 Page 1-After 2015".equalsIgnoreCase(reportName)) || ("ULF 07 Page 2-After 2015".equalsIgnoreCase(reportName))
					|| ("ULF 07 Page 2 - RCS & Contacts Examined".equalsIgnoreCase(reportName))){
				childList.put("General Type");	
				childList.put("General Name");	
				childList.put("Value Type");	
			}
			else if("Drug".equalsIgnoreCase(reportName)){
				childList.put("Year");		
				childList.put("Month");		
				childList.put("General Name");
				childList.put("General Type");
			}
			else if("District Performance".equalsIgnoreCase(reportName)){
				childList.put("District");
			}
			else if("District Budget".equalsIgnoreCase(reportName)){
				childList.put("General Name");
				childList.put("Year");
				childList.put("Month");
			}

			childList.put("Value");
			
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;

		if("ULF 07 Page 1".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_ULF07_Page_1;
		}
		if("ULF 07 Page 2".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_ULF07_Page_2;
		}
		else if("ULF 07 Page 1-After 2015".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_ULF07_Page1_AFTER_2015;
		}
		else if("ULF 07 Page 2-After 2015".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_ULF07_Page2_AFTER_2015;
		}		
		else if("ULF 07 Page 2 - RCS & Contacts Examined".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_RCS;
		}
		else if("Drug".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_DRUG;
		}
		else if("District Performance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_DISTPERF;
		}
		else if("District Budget".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NLEP_REPORTZONE_DISTBUDGET;
		}
		
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{	
			if(("ULF 07 Page 1".equalsIgnoreCase(reportName)) || ("ULF 07 Page 2".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String generalType=resultSet.getString(2);
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);		
					String valueType=resultSet.getString(8);
							
					childList.put(indicator);	
					childList.put(generalType);	
					childList.put(valueType);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("ULF 07 Page 1-After 2015".equalsIgnoreCase(reportName)) || ("ULF 07 Page 2-After 2015".equalsIgnoreCase(reportName))
					 || ("ULF 07 Page 2 - RCS & Contacts Examined".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String generalType=resultSet.getString(2);
					String generalName=resultSet.getString(3);
					BigDecimal val=truncateDecimal(resultSet.getDouble(8),2);		
					String valueType=resultSet.getString(9);
							
					childList.put(indicator);	
					childList.put(generalType);	
					childList.put(generalName);	
					childList.put(valueType);
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("Drug".equalsIgnoreCase(reportName)){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String generalType=resultSet.getString(2);
					String generalName=resultSet.getString(3);
					int year=resultSet.getInt(4);
					String month=resultSet.getString(5);
					BigDecimal val=truncateDecimal(resultSet.getDouble(8),2);		
					
							
					childList.put(indicator);	
					childList.put(year);
					childList.put(month);
					childList.put(generalName);	
					childList.put(generalType);	
					childList.put(val);
					parentList.put(childList);
				}		
			}
			else if("District Performance".equalsIgnoreCase(reportName)){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String district=resultSet.getString(2);
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);						
							
					childList.put(indicator);	
					childList.put(district);
					childList.put(val);
					parentList.put(childList);
				}		
			}
			else if("District Budget".equalsIgnoreCase(reportName)){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String generalName=resultSet.getString(2);
					int year=resultSet.getInt(3);
					String month=resultSet.getString(4);
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);		
					
							
					childList.put(indicator);	
					childList.put(generalName);	
					childList.put(year);
					childList.put(month);					
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
	
public List<String> getIndicatorCategories(){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	List<String> categoryList=new ArrayList<String>();
	try{
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcReportQueryList.NLEP_GETGENERALCATEGORY);
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
		preparedStatement=connection.prepareStatement(ShdrcReportQueryList.NLEP_GETSTATELIST);
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
		preparedStatement=connection.prepareStatement(ShdrcReportQueryList.NLEP_GETDISTRICTLIST);
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
	
public List<String> getGeneralCategory(String indicator){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	List<String> categoryList=null;
	try{
		categoryList=new ArrayList<String>();
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcReportQueryList.NLEP_GETINDICATORCATEGORY);
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
	preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_nlep_year(?,?,?)");
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
	preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_nlep_ind_name(?,?)");	
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
	preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_nlep_ind_cat(?)");
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
			 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_nlep_main(?,?,?,?)");
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
public JSONArray getNLEPBCPDashboardData(int directorateId,String bcpName,int year,String month,String userName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray childList=null;			
	JSONArray parentList=new JSONArray();
	try {
		 childList=new JSONArray();
		 childList.put("General_Type"); 
		 childList.put("General_Name"); 
		 childList.put("Value");
		 childList.put("Threshold");
		 childList.put("ThresholdValue");
		 parentList.put(childList);
		 connection=jdbcTemplate.getDataSource().getConnection();		
		 if(bcpName.equalsIgnoreCase("BCP (Before 2015)")){
		 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_nlep_state_before2015(?,?,?,?,?)");
		 }else{
		 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_nlep_state_after2015(?,?,?,?,?)");	 
		 }
		 preparedStatement.setInt(1, directorateId);
		 preparedStatement.setString(2, bcpName);
		 preparedStatement.setInt(3, year);	
		 preparedStatement.setString(4, month);
		 preparedStatement.setString(5, userName);
		 resultSet = preparedStatement.executeQuery();
		  while (resultSet.next()) {
			    childList=new JSONArray();
			    String General_Type=resultSet.getString(1);
			    String General_Name=resultSet.getString(2);
			    int Value=resultSet.getInt(3);
			    String threSholdColor=getThrosholdColor(resultSet.getString(4).charAt(0));
			    String threSholdVal=resultSet.getString(5);
			    childList.put(General_Type);
			    childList.put(General_Name);
			    childList.put(Value);
			    childList.put(threSholdColor);
			    childList.put(threSholdVal);
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
public JSONArray getNLEPBCPDashboardDistrictData(int directorateId,String bcpName,int year,String month,String userName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray childList=null;			
	JSONArray parentList=new JSONArray();
	try {
		 childList=new JSONArray();
		 childList.put("District");
		 childList.put("General_Type"); 
		 childList.put("General_Name"); 
		 childList.put("Value");
		 childList.put("Threshold");
		 childList.put("ThresholdValue");
		 parentList.put(childList);
		 connection=jdbcTemplate.getDataSource().getConnection();
		 if(bcpName.equalsIgnoreCase("BCP (Before 2015)")){
		 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_nlep_district_before2015(?,?,?,?,?)");
		 }else{
			 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_nlep_district_after2015(?,?,?,?,?)");
		 }
		 preparedStatement.setInt(1, directorateId);
		 preparedStatement.setString(2, bcpName);
		 preparedStatement.setInt(3, year);	
		 preparedStatement.setString(4, month);
		 preparedStatement.setString(5, userName);
		 resultSet = preparedStatement.executeQuery();
		  while (resultSet.next()) {
			    childList=new JSONArray();
			    String District=resultSet.getString(1);
			    String General_Type=resultSet.getString(2);
			    String General_Name=resultSet.getString(3);
			    int Value=resultSet.getInt(4);
			    String threSholdColor=getThrosholdColor(resultSet.getString(5).charAt(0));
			    String threSholdVal=resultSet.getString(6);
			    childList.put(District);
			    childList.put(General_Type);
			    childList.put(General_Name);
			    childList.put(Value);
			    childList.put(threSholdColor);
			    childList.put(threSholdVal);
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
