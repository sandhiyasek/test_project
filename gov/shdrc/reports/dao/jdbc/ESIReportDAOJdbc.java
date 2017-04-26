package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IESIReportDAO;
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
public class ESIReportDAOJdbc implements IESIReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_esi_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_esi_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_esi_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_esi_main(?,?,?,?)");
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
	//Indicator Zone	
		public List<String> getIndicatorCategories(){
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			List<String> categoryList=new ArrayList<String>();
			try{
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ESI_GETGENERALCATEGORY);
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
				}catch (SQLException ex) {
	            }
			}
			return categoryList;
		}	
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {		
			sr = ShdrcReportQueryList.ESI_ANALYSISZONE;				
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
				String institution=resultSet.getString(1);
				int value=resultSet.getInt(2);
				jsonObject.put("label",institution);
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
					
		public JSONArray getIndicatorPvtStateData(int directorateId,String category,String fromMonth,int fromYear,String toMonth,int toYear,String username){
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray IndicatorPvtStateData=null;
			try{
				IndicatorPvtStateData=new JSONArray();
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ESI_GETSTATELIST);
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
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ESI_GETDISTRICTLIST);
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
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ESI_GETINSTITUTIONLIST);
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
			
		public List<String> getGeneralCategory(String indicator){
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			List<String> categoryList=null;
			try{
				categoryList=new ArrayList<String>();
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ESI_GETINDICATORCATEGORY);
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
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
        try {
        	childList=new JSONArray();
			childList.put("indicator");
			childList.put("institution");
			childList.put("Value");
			parentList.put(childList);
			
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ESI_REPORTZONE);
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
				String institution=resultSet.getString(2);				
				BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
				
				childList.put(indicator);				
				childList.put(institution);	
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
}
