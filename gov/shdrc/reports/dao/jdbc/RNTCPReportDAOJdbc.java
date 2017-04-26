package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IRNTCPReportDAO;
import gov.shdrc.util.ShdrcReportQueryList;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.IRNTCPReportDAO;
import gov.shdrc.util.ShdrcReportQueryList;
import gov.shdrc.reports.dao.IRNTCPReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RNTCPReportDAOJdbc implements IRNTCPReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public JSONArray getRNTCPIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String quarter,String loggedUser) {		
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray jsonArray=new JSONArray();
			try {
				connection=jdbcTemplate.getDataSource().getConnection();
				if("Quarterly".equalsIgnoreCase(indicatorCategory)){
					preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_state_rntcpold(?,?,?,?,?)");
				}else{
					preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_state(?,?,?,?,?)");
				}				
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2,indicatorCategory);
				preparedStatement.setInt(3, year);
				if("Quarterly".equalsIgnoreCase(indicatorCategory)){
					preparedStatement.setString(4,quarter);
				}else{
				preparedStatement.setString(4,month);
				}
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
			String month,String quarter,String loggedUser) {		
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			if("Quarterly".equalsIgnoreCase(indicatorCategory)){
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_district_rntcpold(?,?,?,?,?,?)");
			}else{
				preparedStatement = connection.prepareStatement("select * from  shdrc_dwh.dash_district_zone(?,?,?,?,?,?)");
			}			
			preparedStatement.setInt(1, directorateID);
			preparedStatement.setString(2,indicatorCategory);
			preparedStatement.setString(3,indicatorName);
			preparedStatement.setInt(4, year);
			if("Quarterly".equalsIgnoreCase(indicatorCategory)){
				preparedStatement.setString(5,quarter);
			}else{
				preparedStatement.setString(5,month);
			}			
			preparedStatement.setString(6,loggedUser);
			resultSet = preparedStatement.executeQuery();
			 while (resultSet.next()) {
				    childList=new JSONArray();				    			   
					String district=resultSet.getString(3);	
					String color=resultSet.getString(5);
					Double val=resultSet.getDouble(4);
					String zone=resultSet.getString(7);
					childList.put(district);										
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
			String districtName, int year, String month,String quarter,String loggedUser) {		
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
			if("Quarterly".equalsIgnoreCase(indicatorCategory)){
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_inst_rntcpold(?,?,?,?,?,?,?)");
			}else{
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_inst(?,?,?,?,?,?,?)");
			}				
			preparedStatement.setInt(1, directorateID);
			preparedStatement.setString(2,indicatorCategory);
			preparedStatement.setString(3,indicatorName);
			preparedStatement.setString(4, districtName);
			preparedStatement.setInt(5, year);
			if("Quarterly".equalsIgnoreCase(indicatorCategory)){
				preparedStatement.setString(6,quarter);
			}else{
				preparedStatement.setString(6,month);
			}			
			preparedStatement.setString(7,loggedUser);
			resultSet = preparedStatement.executeQuery();
			 while (resultSet.next()) {
				   /* childList=new JSONArray();				 
					String district=resultSet.getString(3);					
					Double val=resultSet.getDouble(5);
					String institution=resultSet.getString(4);					
					childList.put(institution);
					childList.put(district);							
					childList.put(val);					
					parentList.put(childList);*/
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
	
	//Analysis Zone
		public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName,String quarter){
			String sr=null;
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray analysisZoneData=new JSONArray();
			try {		
				sr = ShdrcReportQueryList.RNTCP_ANALYSISZONE;				
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, analysisReportName);
				preparedStatement.setInt(3, year);
				if(year>2015){
					preparedStatement.setString(4, month);
				}
				else{
					preparedStatement.setString(4, quarter);
				}
				preparedStatement.setString(5, userName);
				resultSet=preparedStatement.executeQuery();	
			    		
				while(resultSet.next())
				{ 				
					JSONObject jsonObject=new JSONObject();								
					String district=resultSet.getString(1);
					BigDecimal value=truncateDecimal(resultSet.getDouble(2),2);
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
		
		public JSONArray getAnalysisZoneGeoMapData(Integer directorateId,String analysisReportName,Integer year,String month,String userName,String quarter){
			String sr=null;
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray analysisZoneGeoMapData=new JSONArray();
			try {		
				sr = ShdrcReportQueryList.RNTCP_ANALYSISZONE_GEOMAP;				
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setInt(1, directorateId);
				preparedStatement.setString(2, analysisReportName);
				preparedStatement.setInt(3, year);
				if(year>2015){
					preparedStatement.setString(4, month);
				}
				else{
					preparedStatement.setString(4, quarter);
				}
				preparedStatement.setString(5, userName);
				resultSet=preparedStatement.executeQuery();	
			    		
				while(resultSet.next())
				{ 				
					JSONArray analysisZoneReportData = new JSONArray();
					String district=resultSet.getString(1);
					String institution=resultSet.getString(2);
					int value=resultSet.getInt(3);
					int latitude=resultSet.getInt(4);
					int longitude=resultSet.getInt(5);
					
					analysisZoneReportData.put(district);
					analysisZoneReportData.put(institution);
					analysisZoneReportData.put(value);
					analysisZoneReportData.put(latitude);
					analysisZoneReportData.put(longitude);
					analysisZoneReportData.put("B");
					analysisZoneGeoMapData.put(analysisZoneReportData);
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
			return analysisZoneGeoMapData;
		}
		
		//Reports Zone
		public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,
				String userName,String districtName){
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
				preparedStatement.setString(8, districtName);
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
				if(("Circular 2-TB units-Suspects examined".equalsIgnoreCase(reportName)) || ("Circular 4-TB-HIV Collaboration-Supervision & Monitoring".equalsIgnoreCase(reportName)) 
						|| ("Circular 4-MDR-TBCase Finding -Supervision & Monitoring".equalsIgnoreCase(reportName))){
					childList=new JSONArray();
					childList.put("Indicator");
					childList.put("Region");
					childList.put("Value");	
				}
				else if(("Circular 4-Case Findings - New and Retreatment cases".equalsIgnoreCase(reportName)) || ("Circular 4-Case Findings - New and Retreatment cases - TB Cases Only".equalsIgnoreCase(reportName))
						 || ("Circular 4-Case Findings - New and Retreatment cases Age wise".equalsIgnoreCase(reportName)) || ("Circular 4-Sputum Conversion of New and Retreatment cases".equalsIgnoreCase(reportName))
						 || ("Circular 4-Sputum Conversion-Cat II Re-treatment".equalsIgnoreCase(reportName)) || ("Circular 4-PPM-DOTS-RNTCP-Scheme-Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-ACSM(IEC)-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-PPM-DOTS-Other sector involvement Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-PPM-DOTS-Staff Provided-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-PPM-DOTS-Nature of Ownership-Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-Medication-Items-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-Staff Position and Training -Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-EQA-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-Supervissory Activity-Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-Basic RNTCP Service -Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-Result of Treatments-Supervision & Monitoring".equalsIgnoreCase(reportName))){
					childList=new JSONArray();
					childList.put("Indicator");
					childList.put("General Name");
					childList.put("Region");
					childList.put("Value");	
				}
			}catch(Exception json){
				
			}
			return childList;
		}
		
		private String getQueryByReportName(String reportName){
			String query = null;
			
			if("Circular 2-TB units-Suspects examined".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR2_TBUNITS_SUSPECTS_EXAMINED;
			}
			else if("Circular 4-Case Findings - New and Retreatment cases".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_CASEFINDINGS_NEW_RETREATMENT_CASES;
			}
			else if("Circular 4-Case Findings - New and Retreatment cases - TB Cases Only".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_CASEFINDINGS_NEW_RETREATMENT_CASES_TBCASES_ONLY;
			}
			else if("Circular 4-Case Findings - New and Retreatment cases Age wise".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_CASEFINDINGS_NEW_RETREATMENT_CASES_AGEWISE;
			}
			else if("Circular 4-Sputum Conversion of New and Retreatment cases".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_SPUTUM_CONVERSION_NEW_RETREATMENT_CASES;
			}
			else if("Circular 4-Sputum Conversion-Cat II Re-treatment".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_SPUTUM_CONVERSION_CAT_RETREATMENT;
			}
			else if("Circular 4-PPM-DOTS-RNTCP-Scheme-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_PPM_DOTS_SUPERVISION_MONITORING;
			}
			else if("Circular 4-ACSM(IEC)-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_ACSM_SUPERVISION_MONITORING;
			}
			else if("Circular 4-PPM-DOTS-Other sector involvement Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_PPM_DOTS_OTHERSECTOR_INVOLVEMENT;
			}
			else if("Circular 4-PPM-DOTS-Staff Provided-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_PPM_DOTS_STAFF_PROVIDED;
			}
			else if("Circular 4-PPM-DOTS-Nature of Ownership-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_PPM_DOTS_OWNERSHIP_NATURE;
			}
			else if("Circular 4-Medication-Items-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_MEDICATION_ITEMS;
			}
			else if("Circular 4-Staff Position and Training -Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_STAFF_POSITION_TRAINING;
			}
			else if("Circular 4-EQA-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_EQA;
			}
			else if("Circular 4-Supervissory Activity-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_SUPERVISORY_ACTIVITY;
			}
			else if("Circular 4-Basic RNTCP Service -Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_RNTCP_SERVICES;
			}
			else if("Circular 4-Result of Treatments-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_TREATMENT_RESULTS;
			}
			else if("Circular 4-TB-HIV Collaboration-Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_TB_HIV_COLLABORATION;
			}
			else if("Circular 4-MDR-TBCase Finding -Supervision & Monitoring".equalsIgnoreCase(reportName)){
				query=ShdrcReportQueryList.RNTCP_REPORTZONE_CIRCULAR4_MDR_TBCASE_FINDING;
			}
			return query;
		}
		
		private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
			JSONArray childList=null;
			try{	
				if(("Circular 2-TB units-Suspects examined".equalsIgnoreCase(reportName)) || ("Circular 4-TB-HIV Collaboration-Supervision & Monitoring".equalsIgnoreCase(reportName)) 
						|| ("Circular 4-MDR-TBCase Finding -Supervision & Monitoring".equalsIgnoreCase(reportName))){
					while(resultSet.next()){ 				
						childList=new JSONArray();
						String indicator=resultSet.getString(1);
						String region=resultSet.getString(2);			
						BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
						
						childList.put(indicator);
						childList.put(region);
						childList.put(val);
						parentList.put(childList);
					}
				}
				else if(("Circular 4-Case Findings - New and Retreatment cases".equalsIgnoreCase(reportName)) || ("Circular 4-Case Findings - New and Retreatment cases - TB Cases Only".equalsIgnoreCase(reportName))
						|| ("Circular 4-Case Findings - New and Retreatment cases Age wise".equalsIgnoreCase(reportName)) || ("Circular 4-Sputum Conversion of New and Retreatment cases".equalsIgnoreCase(reportName))
						|| ("Circular 4-Sputum Conversion-Cat II Re-treatment".equalsIgnoreCase(reportName)) || ("Circular 4-PPM-DOTS-RNTCP-Scheme-Supervision & Monitoring".equalsIgnoreCase(reportName))
						|| ("Circular 4-ACSM(IEC)-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-PPM-DOTS-Other sector involvement Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-PPM-DOTS-Staff Provided-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-PPM-DOTS-Nature of Ownership-Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-Medication-Items-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-Staff Position and Training -Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-EQA-Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-Supervissory Activity-Supervision & Monitoring".equalsIgnoreCase(reportName))
						 || ("Circular 4-Basic RNTCP Service -Supervision & Monitoring".equalsIgnoreCase(reportName)) || ("Circular 4-Result of Treatments-Supervision & Monitoring".equalsIgnoreCase(reportName))){
					while(resultSet.next()){ 				
						childList=new JSONArray();
						String indicator=resultSet.getString(1);
						String generalName=resultSet.getString(2);			
						String region=resultSet.getString(3);			
						BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
						
						childList.put(indicator);
						childList.put(generalName);
						childList.put(region);
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
		
		//Indicator Zone
		
		public List<String> getIndicatorCategories(){
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			List<String> categoryList=new ArrayList<String>();
			try{
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.RNTCP_GETGENERALCATEGORY);
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
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.RNTCP_GETSTATELIST);
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
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.RNTCP_GETDISTRICTLIST);
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
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.RNTCP_GETINSTITUTIONLIST);
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
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.RNTCP_GETINDICATORCATEGORY);
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
		public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indFrequency) {
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;CommonStringList commonStringList=null;
			
			try {
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement = connection.prepareStatement(ShdrcReportQueryList.DASHBOARD_MAX_MONTH_YEAR);
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
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_rntcp_year(?,?,?)");
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
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_rntcp_ind_name(?,?)");	
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
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_rntcp_ind_cat(?)");
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
					 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_rntcp_main(?,?,?,?)");
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
