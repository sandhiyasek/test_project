package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;

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
public class CommonReportDAOJdbc implements ICommonReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public JSONArray getIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray jsonArray=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			if(directorateId==14){
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_dca_state_new(?,?,?,?,?)");
			}else{
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_state(?,?,?,?,?)");
			}
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
			if(directorateID==14){
			/*childList=new JSONArray();			
			childList.put("ZoneName");					
			childList.put("Value");	
			childList.put("ThresholdTooltip");
			childList.put("Threshold");
			parentList.put(childList);*/
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			if(directorateID==14){
				preparedStatement = connection.prepareStatement("select * from  shdrc_dwh.dash_dca_district_new_zone(?,?,?,?,?,?)");
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
						String toolTip=resultSet.getString(6);
						String color=resultSet.getString(5);
						Double val=resultSet.getDouble(4);
						childList.put(district);				
						childList.put(val);
						childList.put(toolTip);
						childList.put(getThrosholdColor(color.charAt(0)));
						parentList.put(childList);
				      }
			}else{
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
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_dph_inst(?,?,?,?,?,?,?)");
			}else{
				
				parentList.put(childList);
				if(directorateID==14){
					childList.put("Institution");	
					childList.put("Zone");		
					childList.put("ThresholdTooltip");
					childList.put("Value");	
					childList.put("Threshold");
					preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_dca_inst_new(?,?,?,?,?,?,?)");
				}else{
					childList.put("Institution");	
					childList.put("District");							
					childList.put("Value");	
					childList.put("Threshold");
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_inst(?,?,?,?,?,?,?)");
				}
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
			}
			else if(directorateID==14){
				while (resultSet.next()) {
				    childList=new JSONArray();				 
					String zoneName=resultSet.getString(3);					
					Double val=resultSet.getDouble(6);
					String institution=resultSet.getString(5);	
					String toolTip=resultSet.getString(8);
					String color=resultSet.getString(7);					
					childList.put(institution);
					childList.put(zoneName);
					childList.put(toolTip);
					childList.put(val);		
					childList.put(getThrosholdColor(color.charAt(0)));
					parentList.put(childList);
			}
			}
			else{
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
	
	//Indicator Zone	
	public List<String> getIndicatorCategories(int directorateId){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> categoryList=new ArrayList<String>();
		try{
			connection=jdbcTemplate.getDataSource().getConnection();
			String indicatorCategoryQuery=getIndicatorCategoryQuery(directorateId);
			preparedStatement=connection.prepareStatement(indicatorCategoryQuery);
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
		String stateQuery=null;
		try{			
			IndicatorPvtStateData=new JSONArray();			
			connection=jdbcTemplate.getDataSource().getConnection();
			stateQuery=getIndicatorStateQuery(directorateId);
			preparedStatement=connection.prepareStatement(stateQuery);
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
		String districtQuery=null;
		try{
			IndicatorPvtDistrictData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			districtQuery=getIndicatorDistrictQuery(directorateId);
			preparedStatement=connection.prepareStatement(districtQuery);
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
			if(directorateId == 9){
				obj.put("Department", resultSet.getString(3));
			}else{
			obj.put("District", resultSet.getString(3));
			}
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
		String institutionQuery=null;
		try{
			IndicatorPvtInstitutionData=new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			institutionQuery=getIndicatorInstitutionQuery(directorateId);
			preparedStatement=connection.prepareStatement(institutionQuery);
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
			if(directorateId == 16){
				while(resultSet.next())
				{ 	
					JSONObject obj = new JSONObject();
					obj.put("Institution",resultSet.getString(4));
					obj.put("Institution_Type",resultSet.getString(5));
					obj.put("Institution_Name",resultSet.getString(6));
					obj.put("GeneralName",resultSet.getString(7));
					obj.put("Value",resultSet.getString(8));
					IndicatorPvtInstitutionData.put(obj);
				}
			}else if(directorateId == 18){
				while(resultSet.next())
				{
				JSONObject obj = new JSONObject();
				obj.put("Institution_Type",resultSet.getString(4));
				obj.put("Institution",resultSet.getString(5));
				obj.put("Institution_Spec",resultSet.getString(6));
				obj.put("GeneralName",resultSet.getString(7));
				obj.put("Value",resultSet.getString(8));
				IndicatorPvtInstitutionData.put(obj);
				}
			}
			else if(directorateId == 2){
				while(resultSet.next())
				{
				JSONObject obj = new JSONObject();
				obj.put("Institution_Type",resultSet.getString(5));
				obj.put("Institution",resultSet.getString(6));
				obj.put("GeneralName",resultSet.getString(7));
				obj.put("Value",resultSet.getInt(8));
				IndicatorPvtInstitutionData.put(obj);
				}
			}
			else if(directorateId == 7){
				while(resultSet.next())
				{
				JSONObject obj = new JSONObject();
				obj.put("Hud_Type",resultSet.getString(4));
				obj.put("Hud_Name",resultSet.getString(5));
				obj.put("Block_Name",resultSet.getString(6));
				obj.put("Institution",resultSet.getString(7));
				obj.put("GeneralName",resultSet.getString(8));
				obj.put("Value",resultSet.getInt(9));
				IndicatorPvtInstitutionData.put(obj);
				}
			}
			else{
				while(resultSet.next())
				{ 	
					JSONObject obj = new JSONObject();
					obj.put("Institution",resultSet.getString(4));
					obj.put("GeneralName",resultSet.getString(5));
					obj.put("Value",resultSet.getString(6));
					IndicatorPvtInstitutionData.put(obj);
				}
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
		
	public List<String> getGeneralCategory(int directorateId,String indicator){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List<String> categoryList=null;
		String generalCategoryQuery=null;
		try{
			categoryList=new ArrayList<String>();
			connection=jdbcTemplate.getDataSource().getConnection();
			generalCategoryQuery=getGeneralCategoryQuery(directorateId);
			preparedStatement=connection.prepareStatement(generalCategoryQuery);
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
	public CommonStringList getIndicatorIntMaxMonthAndYear(int directorateId) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;CommonStringList commonStringList=null;
		
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.INDICATOR_MAX_MONTH_YEAR);
			preparedStatement.setInt(1, directorateId);
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
	public JSONArray getGovtHospitalData(int directorateId,
			String indicatorCategory, int year, String month, String userName) {
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
	public static String getIndicatorCategoryQuery(Integer directorateId){
        String directorateQuery=null;
        switch (directorateId) {
        case ShdrcConstants.DirectorateId.DMS		:	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dms.\"Indicator_Master_Dim\"  where \"Ind_Order\"  not in (-1,-2) and \"Indicator_Category\" not in ('Extra') and \"Active\" =1 group by \"Indicator_Category\"";
                 	 									break;
        case ShdrcConstants.DirectorateId.DPH		:	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dph.\"Indicator_Master_Dim\" i,shdrc_dph.\"DPH_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=2  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
        case ShdrcConstants.DirectorateId.TNMSC		:	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"TNMSC_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=3 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.TANSACS	: 	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"TANSACS_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=4  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.NRHM		:	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"NRHM_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=5 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.RNTCP		: 	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"RNTCP_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=6  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.MA		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"MA_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=7 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.DME		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dme.\"Indicator_Master_Dim\" i,shdrc_dme.\"DME_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=8 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.DRMGR		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"MGR_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=9 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.DFW		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dfw.\"Indicator_Master_Dim\" i,shdrc_dfw.\"DFW_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=10  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.SHTO		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"SHTO_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=11 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.COC		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_coc.\"Indicator_Master_Dim\" i,shdrc_coc.\"COC_Fact_Part_Mas\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=12 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.SBCS		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"SBCS_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=13  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.DCA		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"DCA_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=14  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.MRB		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"MRB_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=15  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.DFS		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"DFS_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=16  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.CMCHIS	:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"CMCHIS_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=17 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.DIM		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"DIM_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=18  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.NLEP		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"NLEP_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=19  and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N' and \"Indicator_Category\"<>'Drug'";
														break;
		case ShdrcConstants.DirectorateId.SBHI		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"SBHI_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=20 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.ESI		:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"ESI_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=21  and \"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;
		case ShdrcConstants.DirectorateId.PVTSEC	:  	directorateQuery="select distinct \"Indicator_Category\" from shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"PVTSEC_Fact\" f where f.\"Indicator_Id\" = i.\"Indicator_Id\"  and  i.\"Directorate_Id\"=24 and i.\"Active\"=1 and \"Indicator_Fact_Map\" = 'N'";
														break;												
        }  
		return directorateQuery;
	}
	public static String getIndicatorStateQuery(Integer directorateId){
        String directorateQuery=null;
        switch (directorateId) {
        case ShdrcConstants.DirectorateId.DMS		:	directorateQuery="select * from  shdrc_dms.ind_dms_state(?,?,?,?,?,?,?)";
                 	 									break;
        case ShdrcConstants.DirectorateId.DPH		:	directorateQuery="select * from  shdrc_dwh.ind_dph_state(?,?,?,?,?,?,?)";
														break;
        case ShdrcConstants.DirectorateId.TNMSC		:	directorateQuery="select * from  shdrc_dwh.ind_tnmsc_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.TANSACS	: 	directorateQuery="select * from  shdrc_dwh.ind_tansacs_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.NRHM		:	directorateQuery="select * from  shdrc_dwh.ind_nrhm_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.RNTCP		: 	directorateQuery="select * from  shdrc_dwh.ind_rntcp_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.MA		:  	directorateQuery="select * from  shdrc_dwh.ind_ma_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DME		:  	directorateQuery="select * from  shdrc_dwh.ind_dme_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DRMGR		:  	directorateQuery="select * from  shdrc_dwh.ind_mgr_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DFW		:  	directorateQuery="select * from  shdrc_dwh.ind_dfw_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SHTO		:  	directorateQuery="select * from  shdrc_dwh.ind_shtd_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.COC		:  	directorateQuery="select * from  shdrc_dwh.ind_coc_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SBCS		:  	directorateQuery="select * from  shdrc_dwh.ind_sbcs_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DCA		:  	directorateQuery="select * from  shdrc_dwh.ind_dca_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.MRB		:  	directorateQuery="select * from  shdrc_dwh.ind_mrb_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DFS		:  	directorateQuery="select * from shdrc_dwh.ind_dfs_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.CMCHIS	:  	directorateQuery="select * from  shdrc_dwh.ind_cmchis_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DIM		:  	directorateQuery="select * from  shdrc_dwh.ind_dim_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.NLEP		:  	directorateQuery="select * from shdrc_dwh.ind_nlep_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SBHI		:  	directorateQuery="select * from  shdrc_dwh.ind_sbhi_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.ESI		:  	directorateQuery="select * from  shdrc_dwh.ind_esi_state(?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.PVTSEC	:  	directorateQuery="select * from  shdrc_dwh.ind_pvtsec_state(?,?,?,?,?,?,?)";
														break;												
        }  
		return directorateQuery;
	}
	public static String getIndicatorDistrictQuery(Integer directorateId){
        String directorateQuery=null;
        switch (directorateId) {
        case ShdrcConstants.DirectorateId.DMS		:	directorateQuery="select * from shdrc_dms.ind_dms_district(?,?,?,?,?,?,?,?,?)";
                 	 									break;
        case ShdrcConstants.DirectorateId.DPH		:	directorateQuery="select * from shdrc_dwh.ind_dph_district(?,?,?,?,?,?,?,?,?)";
														break;
        case ShdrcConstants.DirectorateId.TNMSC		:	directorateQuery="select * from shdrc_dwh.ind_tnmsc_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.TANSACS		: 	directorateQuery="select * from shdrc_dwh.ind_tansacs_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.NRHM		:	directorateQuery="select * from shdrc_dwh.ind_nrhm_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.RNTCP		: 	directorateQuery="select * from shdrc_dwh.ind_rntcp_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.MA		:  	directorateQuery="select * from shdrc_dwh.ind_ma_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DME		:  	directorateQuery="select * from shdrc_dwh.ind_dme_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DRMGR		:  	directorateQuery="select * from shdrc_dwh.ind_mgr_hud(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DFW		:  	directorateQuery="select * from shdrc_dwh.ind_dfw_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SHTO		:  	directorateQuery="select * from shdrc_dwh.ind_shtd_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.COC		:  	directorateQuery="select * from shdrc_dwh.ind_coc_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SBCS		:  	directorateQuery="select * from shdrc_dwh.ind_sbcs_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DCA		:  	directorateQuery="select * from shdrc_dwh.ind_dca_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.MRB		:  	directorateQuery="select * from shdrc_dwh.ind_mrb_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DFS		:  	directorateQuery="select * from shdrc_dwh.ind_dfs_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.CMCHIS	:  	directorateQuery="select * from shdrc_dwh.ind_cmchis_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DIM		:  	directorateQuery="select * from shdrc_dwh.ind_dim_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.NLEP		:  	directorateQuery="select * from shdrc_dwh.ind_nlep_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SBHI		:  	directorateQuery="select * from shdrc_dwh.ind_sbhi_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.ESI		:  	directorateQuery="select * from shdrc_dwh.ind_esi_district(?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.PVTSEC	:  	directorateQuery="select * from shdrc_dwh.ind_pvtsec_district(?,?,?,?,?,?,?,?,?)";
														break;												
        }  
		return directorateQuery;
	}
	public static String getIndicatorInstitutionQuery(Integer directorateId){
        String directorateQuery=null;
        switch (directorateId) {
        case ShdrcConstants.DirectorateId.DMS		:	directorateQuery="select * from  shdrc_dms.ind_dms_inst(?,?,?,?,?,?,?,?,?,?)";
                 	 									break;
        case ShdrcConstants.DirectorateId.DPH		:	directorateQuery="select * from shdrc_dwh.ind_dph_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
        case ShdrcConstants.DirectorateId.TNMSC		:	directorateQuery="select * from shdrc_dwh.ind_tnmsc_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.TANSACS	: 	directorateQuery="select * from shdrc_dwh.ind_tansacs_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.NRHM		:	directorateQuery="select * from shdrc_dwh.ind_nrhm_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.RNTCP		: 	directorateQuery="select * from shdrc_dwh.ind_rntcp_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.MA		:  	directorateQuery="select * from shdrc_dwh.ind_ma_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DME		:  	directorateQuery="select * from shdrc_dwh.ind_dme_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DRMGR		:  	directorateQuery="select * from shdrc_dwh.ind_mgr_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DFW		:  	directorateQuery="select * from shdrc_dwh.ind_dfw_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SHTO		:  	directorateQuery="select * from shdrc_dwh.ind_shtd_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.COC		:  	directorateQuery="select * from shdrc_dwh.ind_coc_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SBCS		:  	directorateQuery="select * from shdrc_dwh.ind_sbcs_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DCA		:  	directorateQuery="select * from shdrc_dwh.ind_dca_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.MRB		:  	directorateQuery="select * from shdrc_dwh.ind_mrb_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DFS		:  	directorateQuery="select * from shdrc_dwh.ind_dfs_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.CMCHIS	:  	directorateQuery="select * from shdrc_dwh.ind_cmchis_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.DIM		:  	directorateQuery="select * from shdrc_dwh.ind_dim_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.NLEP		:  	directorateQuery="select * from shdrc_dwh.ind_nlep_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.SBHI		:  	directorateQuery="select * from shdrc_dwh.ind_sbhi_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.ESI		:  	directorateQuery="select * from shdrc_dwh.ind_esi_inst(?,?,?,?,?,?,?,?,?,?)";
														break;
		case ShdrcConstants.DirectorateId.PVTSEC	:  	directorateQuery="select * from shdrc_dwh.ind_pvtsec_inst(?,?,?,?,?,?,?,?,?,?)";
														break;												
        }  
		return directorateQuery;
	}
	public static String getGeneralCategoryQuery(Integer directorateId){
        String directorateQuery=null;
        switch (directorateId) {
        case ShdrcConstants.DirectorateId.DMS		:	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dms.\"DMS_Fact\" f,shdrc_dms.\"Indicator_Master_Dim\" i,shdrc_dms.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
                 	 									break;
        case ShdrcConstants.DirectorateId.DPH		:	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dph.\"DPH_Fact\" f,shdrc_dph.\"Indicator_Master_Dim\" i,shdrc_dph.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
        case ShdrcConstants.DirectorateId.TNMSC		:	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"TNMSC_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.TANSACS	: 	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"TANSACS_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.NRHM		:	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"NRHM_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.RNTCP		: 	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"RNTCP_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.MA		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"MA_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.DME		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dme.\"DME_Fact\" f,shdrc_dme.\"Indicator_Master_Dim\" i,shdrc_dme.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.DRMGR		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"MGR_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.DFW		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dfw.\"DFW_Fact\" f,shdrc_dfw.\"Indicator_Master_Dim\" i,shdrc_dfw.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.SHTO		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"SHTO_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.COC		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_coc.\"COC_Fact_Part_Mas\" f,shdrc_coc.\"Indicator_Master_Dim\" i,shdrc_coc.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.SBCS		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"SBCS_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.DCA		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"DCA_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.MRB		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"MRB_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.DFS		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"DFS_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.CMCHIS	:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"CMCHIS_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.DIM		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"DIM_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.NLEP		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"NLEP_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where i.\"Directorate_Id\"=19 and f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.SBHI		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"SBHI_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.ESI		:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"ESI_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;
		case ShdrcConstants.DirectorateId.PVTSEC	:  	directorateQuery="select distinct \"Indicator_Name\",\"General_Category\" from shdrc_dwh.\"PVTSEC_Fact\" f,shdrc_dwh.\"Indicator_Master_Dim\" i,shdrc_dwh.\"General_Master\" g where f.\"Indicator_Id\"=i.\"Indicator_Id\" and f.\"General_Id\"=g.\"General_Id\" and \"Indicator_Name\" like ?";
														break;												
        }  
		return directorateQuery;
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
	@Override
	public CommonStringList getIndicatorMaxMonthAndYear(int directorateId, String indicatorCategory) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		CommonStringList commonStringList=null;
		String query=null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			query=getMaxMonthAndYearQuery(directorateId);
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,indicatorCategory);		
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
	public static String getMaxMonthAndYearQuery(Integer directorateId){
        String directorateQuery=null;
        switch (directorateId) {
        case ShdrcConstants.DirectorateId.DMS		:	directorateQuery="select * from shdrc_dwh.max_year_month_dms_new(?)";
                 	 									break;
        case ShdrcConstants.DirectorateId.DPH		:	directorateQuery="select * from shdrc_dwh.max_year_month_dph_new(?)";
														break;
        case ShdrcConstants.DirectorateId.TNMSC		:	directorateQuery="select * from shdrc_dwh.max_year_month_tnmsc_new(?)";
														break;
		case ShdrcConstants.DirectorateId.TANSACS	: 	directorateQuery="select * from shdrc_dwh.max_year_month_tansacs_new(?)";
														break;
		case ShdrcConstants.DirectorateId.NRHM		:	directorateQuery="select * from shdrc_dwh.max_year_month_nrhm_new(?)";
														break;
		case ShdrcConstants.DirectorateId.RNTCP		: 	directorateQuery="select * from shdrc_dwh.max_year_month_rntcp_new(?)";
														break;
		case ShdrcConstants.DirectorateId.MA		:  	directorateQuery="select * from shdrc_dwh.max_year_month_ma_new(?)";
														break;
		case ShdrcConstants.DirectorateId.DME		:  	directorateQuery="select * from shdrc_dwh.max_year_month_dme_new(?)";
														break;
		case ShdrcConstants.DirectorateId.DRMGR		:  	directorateQuery="select * from shdrc_dwh.max_year_month_mgr_new(?)";
														break;
		case ShdrcConstants.DirectorateId.DFW		:  	directorateQuery="select * from shdrc_dwh.max_year_month_dfw_new(?)";
														break;
		case ShdrcConstants.DirectorateId.SHTO		:  	directorateQuery="select * from shdrc_dwh.max_year_month_shto_new(?)";
														break;
		case ShdrcConstants.DirectorateId.COC		:  	directorateQuery="select * from shdrc_dwh.max_year_month_coc_new(?)";
														break;
		case ShdrcConstants.DirectorateId.SBCS		:  	directorateQuery="select * from shdrc_dwh.max_year_month_sbcs_new(?)";
														break;
		case ShdrcConstants.DirectorateId.DCA		:  	directorateQuery="select * from shdrc_dwh.max_year_month_dca_new(?)";
														break;
		case ShdrcConstants.DirectorateId.MRB		:  	directorateQuery="select * from shdrc_dwh.max_year_month_mrb_new(?)";
														break;
		case ShdrcConstants.DirectorateId.DFS		:  	directorateQuery="select * from shdrc_dwh.max_year_month_dfs_new(?)";
														break;
		case ShdrcConstants.DirectorateId.CMCHIS	:  	directorateQuery="select * from shdrc_dwh.max_year_month_cmchis_new(?)";
														break;
		case ShdrcConstants.DirectorateId.DIM		:  	directorateQuery="select * from shdrc_dwh.max_year_month_dim_new(?)";
														break;
		case ShdrcConstants.DirectorateId.NLEP		:  	directorateQuery="select * from shdrc_dwh.max_year_month_nlep_new(?)";
														break;
		case ShdrcConstants.DirectorateId.SBHI		:  	directorateQuery="select * from shdrc_dwh.max_year_month_sbhi_new(?)";
														break;
		case ShdrcConstants.DirectorateId.ESI		:  	directorateQuery="select * from shdrc_dwh.max_year_month_esi_new(?)";
														break;
		case ShdrcConstants.DirectorateId.PVTSEC	:  	directorateQuery="select * from shdrc_dwh.max_year_month_pvtsec_new(?)";
														break;												
        }  
		return directorateQuery;
	}
}
