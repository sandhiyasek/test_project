package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IDMSReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcQueryList;
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
public class DMSReportDAOJdbc implements IDMSReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String district1,String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
        try {
        	if("Data Availability Status-Institution Wise".equalsIgnoreCase(reportName)){
    			connection=jdbcTemplate.getDataSource().getConnection();
    			preparedStatement=connection.prepareStatement("select * from shdrc_dms.data_avail_status(?,?,?)");
    			preparedStatement.setString(1, district1);
    			preparedStatement.setInt(2, fromYear);
    			preparedStatement.setString(3, fromMonth);
    			resultSet=preparedStatement.executeQuery();	
	    		while(resultSet.next())
	    		{ 	
	    			JSONObject obj=new JSONObject();
	    			obj.put("Indicator",resultSet.getString(1));
	    			obj.put("Institution",resultSet.getString(2));
	    			obj.put("Value",resultSet.getString(3));
	    			parentList.put(obj);
	    		}
        	}else{
        	childList=new JSONArray();
			childList.put("indicator");
			childList.put("District");
			childList.put("institutionType");
			childList.put("institution");
			childList.put("Value");
			if("Surgeonwise Surgery".equalsIgnoreCase(reportName)){
				childList.put("Doctor");
				childList.put("Speciality");
			}
			parentList.put(childList);
			connection=jdbcTemplate.getDataSource().getConnection();
			if("Surgeonwise Surgery".equalsIgnoreCase(reportName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DMS_REPORTZONE_SURGEONWISESURGERY);
			}
			else{			
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DMS_REPORTZONE);
			}
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, reportName);
			preparedStatement.setInt(3, fromYear);
			preparedStatement.setString(4, fromMonth);
			preparedStatement.setInt(5, toYear);
			preparedStatement.setString(6, toMonth);
			preparedStatement.setString(7, userName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String district=resultSet.getString(2);
				String institutionType=resultSet.getString(3);
				String institution=resultSet.getString(4);				
				BigDecimal val=truncateDecimal(resultSet.getDouble(5),2);
				
				childList.put(indicator);
				childList.put(district);
				childList.put(institutionType);
				childList.put(institution);	
				childList.put(val);
				if("Surgeonwise Surgery".equalsIgnoreCase(reportName)){
					String doctorName=resultSet.getString(6);
					String speciality=resultSet.getString(7);					
					childList.put(doctorName);
					childList.put(speciality);
				}
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

	
	//Indicator Zone
	
	public List<String> getGeneralCategories(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> categoryList=new ArrayList<String>();
		try{
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GETGENERALCATEGORY);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			String cat=resultSet.getString(1);						
			categoryList.add(cat);
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
	
	public JSONArray indzone(String ind1, int year,String month){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
        try {
			childList=new JSONArray();
			childList.put("Category");
			childList.put("District");
			childList.put("Institution");
			childList.put("Year");
			childList.put("Month");
			childList.put("Value");
			parentList.put(childList);
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.IND_DIST);
			preparedStatement.setString(1, ind1);
			preparedStatement.setLong(2, year);
			preparedStatement.setString(3, month);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String category=resultSet.getString(2);
				String district=resultSet.getString(3);
				String institution=resultSet.getString(4);
				int yr=resultSet.getInt(5);
				String mon=resultSet.getString(6);	
				
				BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);
				childList.put(category);
				childList.put(district);
				childList.put(institution);
				childList.put(yr);
				childList.put(mon);
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
	
	private static BigDecimal truncateDecimal(double x,int numberofDecimals)
	{
	    if ( x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
	    } else {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
	    }
	}
	
	public List<CommonStringList> getIndicatorList(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<CommonStringList> indList=new ArrayList<CommonStringList>();
		try{
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GETINDICATORLIST);
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 	
			CommonStringList commonStringList=new CommonStringList();						
			commonStringList.setName(resultSet.getString(2));
			commonStringList.setId(resultSet.getInt(1));
			indList.add(commonStringList);
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
		return indList;
	}	
	
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {		
			sr = ShdrcReportQueryList.DMS_ANALYSISZONE;				
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
				String district=resultSet.getString(1);
				int value=resultSet.getInt(2);
				jsonObject.put("label",district);
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
	
	public JSONArray getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneMultiSeriesData=new JSONArray();
		try {		
			sr = ShdrcReportQueryList.DMS_ANALYSISZONE_MULTISERIES;				
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
				String district=resultSet.getString(1);
				String xaxisName=resultSet.getString(2);
				String seriesName=resultSet.getString(3);
				int value=resultSet.getInt(4);
				jsonObject.put("label",district);
				jsonObject.put("xaxisName",xaxisName);
				jsonObject.put("seriesName",seriesName);
				jsonObject.put("value",value);					
				analysisZoneMultiSeriesData.put(jsonObject);
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
		return analysisZoneMultiSeriesData;
	}
	
	public JSONArray getPvtData(int directorateId,String category,String fromMonth,int fromYear,String toMonth,int toYear,String username){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray pvtData=null;
		try{
			pvtData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GETSTATELIST);
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
			pvtData.put(obj);
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
		return pvtData;
	}

	public JSONArray getPvtDistrictData(int directorateId,String category,String indicator, String generalCategory,
			String fromMonth,int fromYear,String toMonth,int toYear,String username){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray pvtData=null;
		try{
			pvtData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GETDISTRICTLIST);
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
			pvtData.put(obj);
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
		return pvtData;
	}
	public JSONArray getPvtInstitutionData(int directorateId,String category,String indicator,String generalCategory,
			String district, String fromMonth,int fromYear,String toMonth,int toYear,String username){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray pvtData=null;
		try{
			pvtData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GETINSTITUTIONLIST);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, category);
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
			pvtData.put(obj);
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
		return pvtData;
	}
	
	public List<String> getIndicatorCategory(String indicator){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> categoryList=null;
		try{
			categoryList=new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GETINDICATORCATEGORY);
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

	@Override
	public JSONArray getDMSIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray jsonArray=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from shdrc_dms.dash_dms_state_new(?,?,?,?,?)");
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
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement("select * from  shdrc_dms.dash_dms_district_new_zone(?,?,?,?,?,?)");
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
					//String color=resultSet.getString(5);
					Double val=resultSet.getDouble(4);
					childList.put(district);					
					//childList.put(Indicator);					
					childList.put(val);
					childList.put(zone);
					//childList.put(getThrosholdColor(color.charAt(0)));
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
			preparedStatement = connection.prepareStatement("select * from shdrc_dms.dash_dms_inst_new(?,?,?,?,?,?,?)");
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
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dms_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dms_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dms_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dms_main(?,?,?,?)");
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
