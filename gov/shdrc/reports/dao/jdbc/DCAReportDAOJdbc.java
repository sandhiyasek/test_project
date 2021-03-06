package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IDCAReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;

import java.math.BigDecimal;
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

@Service
public class DCAReportDAOJdbc implements IDCAReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {
			if("Comparison of Blood Bank details".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.DCA_ANALYSISZONE;
			}
			else{
				sr = ShdrcReportQueryList.DCA_ANALYSISZONE_MULTISERIES;			
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
				if("Sales License details".equalsIgnoreCase(analysisReportName)){
					label=resultSet.getString(2);
					value=resultSet.getInt(3);
				}
				else if("Blood Storage Centre-BlockWise".equalsIgnoreCase(analysisReportName)){
					String seriesName=resultSet.getString(1);
					label=resultSet.getString(2);
					value=resultSet.getInt(3);
					jsonObject.put("seriesName",seriesName);
				}
				else if("Comparison of Blood Bank details".equalsIgnoreCase(analysisReportName)){
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
			sr = ShdrcReportQueryList.DCA_ANALYSISZONE_HRDETAILS;				
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
				String generalName=resultSet.getString(1);
				int postSanctionedvalue=resultSet.getInt(2);
				jsonObject1.put("label",generalName);
				jsonObject1.put("value",postSanctionedvalue);
				jsonObject1.put("seriesName","Total Number of Posts Sanctioned (Cadre Wise)");
				analysisZoneFirstSeriesData.put(jsonObject1);
				
				int postVacantvalue=resultSet.getInt(3);
				jsonObject2.put("label",generalName);
				jsonObject2.put("value",postVacantvalue);	
				jsonObject2.put("seriesName","Total Number Vacant (Cadre Wise)");
				analysisZoneSecondSeriesData.put(jsonObject2);
				
				int postInPositionvalue=resultSet.getInt(4);
				jsonObject3.put("label",generalName);
				jsonObject3.put("value",postInPositionvalue);
				jsonObject3.put("seriesName","Total Number In-Position (Cadre Wise)");
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

	@Override
	public JSONArray getDCAIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray jsonArray=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from shdrc_dms.dash_dms_state(?,?,?,?,?)");
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
			childList.put("Indicator");			
			childList.put("Value");		
			parentList.put(childList);
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from  shdrc_dms.dash_dms_district(?,?,?,?,?,?)");
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
					String Indicator=resultSet.getString(1);	
					String state=resultSet.getString(2);
					Double val=resultSet.getDouble(4);
					childList.put(district);					
					childList.put(Indicator);					
					childList.put(val);
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
			childList.put("Institution");	
			childList.put("District");							
			childList.put("Value");				
			parentList.put(childList);
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from shdrc_dms.dash_dms_inst(?,?,?,?,?,?,?)");
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
					childList.put(institution);
					childList.put(district);							
					childList.put(val);					
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
			return "red";
		}
		else if(c=='N'){
			return "green";
		}else{
			return "";
		}
		
	}
	
	//Indicator Zone
	
	public List<String> getIndicatorCategories(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> categoryList=new ArrayList<String>();
		try{
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DCA_GETGENERALCATEGORY);
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
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DCA_GETSTATELIST);
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
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DCA_GETDISTRICTLIST);
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
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DCA_GETINSTITUTIONLIST);
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
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DCA_GETINDICATORCATEGORY);
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
			if(("Analysis of sample-Analyst data".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Indicator");
				childList.put("General Type");
				childList.put("General Name");
				childList.put("Year");
				childList.put("Month");
				childList.put("Value");
			}
			else if(("Blood Bank and related Licences data".equalsIgnoreCase(reportName)) || ("Details of Manufacturing Licence".equalsIgnoreCase(reportName))
					 || ("Details of Sales Licences".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Indicator");
				childList.put("General Type");
				childList.put("General Name");
				childList.put("Value");
			}
			else if(("Blood Storage Centres".equalsIgnoreCase(reportName))){
				childList=new JSONArray();;
				childList.put("General Type");
				childList.put("Year");
				childList.put("Value");
			}
			else if(("Number of Inspections Carried Out".equalsIgnoreCase(reportName)) || ("Number of Samples Drawn".equalsIgnoreCase(reportName))){
				childList=new JSONArray();;
				childList.put("Block Name");
				childList.put("Year");
				childList.put("Month");
				childList.put("Value");
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("Analysis of sample-Analyst data".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DCA_REPORTZONE_SAMPLE_ANALYST_DATA_ANALYSIS;
		}
		else if("Blood Bank and related Licences data".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DCA_REPORTZONE_BLOODBANK_LICENCES_DATA;
		}
		else if("Blood Storage Centres".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DCA_REPORTZONE_BLOOD_STORAGE_CENTRES;
		}
		else if("Details of Manufacturing Licence".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DCA_REPORTZONE_MANUFACTURING_LICENCE_DETAILS;
		}
		else if("Details of Sales Licences".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DCA_REPORTZONE_SALES_LICENCES_DETAILS;
		}
		else if("Number of Inspections Carried Out".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DCA_REPORTZONE_INSPECTIONS_CARRIED_OUT;
		}
		else if("Number of Samples Drawn".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DCA_REPORTZONE_SAMPLES_DRAWN;
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{	
			if(("Analysis of sample-Analyst data".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String generalName=resultSet.getString(2);		
					String generalType=resultSet.getString(3);	
					String month=resultSet.getString(4);		
					int year=resultSet.getInt(5);		
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);
					
					childList.put(indicator);				
					childList.put(generalType);	
					childList.put(generalName);	
					childList.put(year);	
					childList.put(month);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Blood Bank and related Licences data".equalsIgnoreCase(reportName)) || ("Details of Manufacturing Licence".equalsIgnoreCase(reportName))
					|| ("Details of Sales Licences".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);
					String generalName=resultSet.getString(2);		
					String generalType=resultSet.getString(3);		
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(indicator);				
					childList.put(generalType);	
					childList.put(generalName);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Blood Storage Centres".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();	
					String generalType=resultSet.getString(1);	
					int year=resultSet.getInt(2);		
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
								
					childList.put(generalType);	
					childList.put(year);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Number of Inspections Carried Out".equalsIgnoreCase(reportName)) || ("Number of Samples Drawn".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();	
					String blockName=resultSet.getString(1);	
					int year=resultSet.getInt(2);		
					String month=resultSet.getString(3);		
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
								
					childList.put(blockName);	
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
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dca_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dca_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dca_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dca_main(?,?,?,?)");
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
}
