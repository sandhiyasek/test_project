package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IAmbulanceReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcReportQueryList;
import gov.shdrc.util.Util;

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
public class AmbulanceReportDAOJdbc implements IAmbulanceReportDAO {	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<CommonStringList> getOnLoadDistrictList(){
		List<CommonStringList> districtList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			districtList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCEONLOADDISTRICT);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	        	
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	districtList.add(commonStringList);	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return districtList;
	}
	
	public List<CommonStringList> getDistrictList(String frequency,String date,String month,String year,String emergencyType){
		List<CommonStringList> districtList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			districtList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_DISTRICT_LIST);
			if("Daily".equalsIgnoreCase(frequency)){
				preparedStatement.setString(1,date);
				preparedStatement.setString(2,date);
			}
			else if("Monthly".equalsIgnoreCase(frequency)){
				preparedStatement.setString(1,year);
				preparedStatement.setString(2,Integer.toString(Util.getMonthIndexByName(month)));
			}
			else if("Yearly".equalsIgnoreCase(frequency)){
				preparedStatement.setString(1,year);
				preparedStatement.setString(2,"NULL");
			}
			preparedStatement.setString(3,emergencyType);
			preparedStatement.setString(4,frequency);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	districtList.add(commonStringList);
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
		return districtList;
	}
	
	public String getAmbulanceReportMaxDate(){
		String maxDate=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCEREPORTMAXDATE);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	maxDate = resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return maxDate;
	}
	
	public List<CommonStringList> getOnLoadEmergencyTypeList(){
		List<CommonStringList> emergencyTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			emergencyTypeList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCEONLOADEMERGENCYTYPE);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	        	
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	emergencyTypeList.add(commonStringList);	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return emergencyTypeList;
	}
	
	public List<CommonStringList> getEmergencyTypeList(String frequency,String date,String month,String year){
		List<CommonStringList> emergencyTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			emergencyTypeList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_EMERGENCYTYPE_LIST);
			if("Daily".equalsIgnoreCase(frequency)){
				preparedStatement.setString(1,date);
				preparedStatement.setString(2,date);
			}
			else if("Monthly".equalsIgnoreCase(frequency)){
				preparedStatement.setString(1,year);
				preparedStatement.setString(2,Integer.toString(Util.getMonthIndexByName(month)));
			}
			else if("Yearly".equalsIgnoreCase(frequency)){
				preparedStatement.setString(1,year);
				preparedStatement.setString(2,"NULL");
			}
			preparedStatement.setString(3,frequency);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	emergencyTypeList.add(commonStringList);
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
		return emergencyTypeList;
	}
	
	public JSONObject getAmbulanceGISReportData(String date,String month,String year,String frequency,String emergencyType,
			String districtName,String methodName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONObject finalList=new JSONObject();
		JSONArray ambulanceGISReportData=new JSONArray();
		JSONObject geoMapDistColor=new JSONObject();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			if("onLoad".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCEONLOADGISREPORTDATA);			
				/*preparedStatement.setString(1,maxDate);*/
			}
			else if("onSearch".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONSEARCH_GISREPORTDATA);			
				if("Daily".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,date);
					preparedStatement.setString(2,date);
				}
				else if("Monthly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,Integer.toString(Util.getMonthIndexByName(month)));
				}
				else if("Yearly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,"NULL");
				}
				preparedStatement.setString(3,emergencyType);
				preparedStatement.setString(4,districtName);
				preparedStatement.setString(5,frequency);
			}
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONArray GISReportData = new JSONArray();
				String district=resultSet.getString(1);
				BigDecimal latitude=truncateDecimal(resultSet.getDouble(2),4);
				BigDecimal longitude=truncateDecimal(resultSet.getDouble(3),4);				
				int value=resultSet.getInt(4);
				String plotColor=resultSet.getString(5);
				
				String toolTipData = district + ":" + value;
				GISReportData.put(toolTipData);
				GISReportData.put(latitude);
				GISReportData.put(longitude);
				GISReportData.put(plotColor);
				GISReportData.put(district);
				ambulanceGISReportData.put(GISReportData);
				
				geoMapDistColor.put(district, plotColor);
			}
			finalList.put("GISReportData",ambulanceGISReportData);
			finalList.put("geoMapDistColor",geoMapDistColor);
			
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


	public JSONArray getAmbulanceHourwiseHeatGridData(String date,String month,String year,String frequency,String emergencyType,
			String districtName,String methodName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambulanceHourwiseHeatGridData=new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			if("onLoad".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCEONLOADHOURWISEHEATGRIDDATA);			
				/*preparedStatement.setString(1,maxDate);*/
			}
			else if("onSearch".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONSEARCH_HOURWISE_HEATGRIDDATA);			
				if("Daily".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,date);
					preparedStatement.setString(2,date);
				}
				else if("Monthly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,Integer.toString(Util.getMonthIndexByName(month)));
				}
				else if("Yearly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,"NULL");
				}
				preparedStatement.setString(3,emergencyType);
				preparedStatement.setString(4,districtName);
				preparedStatement.setString(5,frequency);
			}
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String district=resultSet.getString(1);
				String hour=resultSet.getString(2);
				int value=resultSet.getInt(3);
								
				jsonObject.put("rowid",district);
				jsonObject.put("columnid",hour);
				jsonObject.put("value",value);							
				ambulanceHourwiseHeatGridData.put(jsonObject);
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
		return ambulanceHourwiseHeatGridData;
	}
	
	public JSONArray getAmbulanceDaywiseHeatGridData(String date,String month,String year,String frequency,String emergencyType,
			String districtName,String methodName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambulanceDaywiseHeatGridData=null;
		JSONObject obj=null;
		String nextDist=null;
		int y=0;
		int x=1;
		try{
			ambulanceDaywiseHeatGridData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();
			if("onLoad".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_DAYWISE_HEATGRID_DATA);			
				/*preparedStatement.setString(1,maxDate);*/
			}
			else if("onSearch".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONSEARCH_DAYWISE_HEATGRID_DATA);			
				if("Daily".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,date);
					preparedStatement.setString(2,date);
				}
				else if("Monthly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,Integer.toString(Util.getMonthIndexByName(month)));
				}
				else if("Yearly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,"NULL");
				}
				preparedStatement.setString(3,emergencyType);
				preparedStatement.setString(4,districtName);
				preparedStatement.setString(5,frequency);
			}
			resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			obj=new JSONObject();
			String prevDist=resultSet.getString(1);		
			if(prevDist.equalsIgnoreCase(nextDist)){
				x=x+1;
				obj.put("district", "");
				obj.put("xAxis", x);
				obj.put("yAxis", y);
			}
			else{				
				y=y+x;
				x=1;
				nextDist=prevDist;
				obj.put("district", prevDist);
				obj.put("xAxis", x);
				obj.put("yAxis", y);				
			}			
			obj.put("indicator", resultSet.getString(2));
			obj.put("value", resultSet.getInt(3));
			ambulanceDaywiseHeatGridData.put(obj);
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
		return ambulanceDaywiseHeatGridData;
	}
	
	public JSONObject getAmbulanceComparisonData(String date,String month,String year,String frequency,String emergencyType,
			String districtName,String methodName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambulanceComparisonFirstSeriesData=new JSONArray();
		JSONArray ambulanceComparisonSecondSeriesData=new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			if("onLoad".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_COMPARISON_DATA);			
				/*preparedStatement.setString(1,maxDate);*/
			}
			else if("onSearch".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONSEARCH_COMPARISON_DATA);			
				/*preparedStatement.setString(1,fromDate);
				preparedStatement.setString(2,toDate);*/
				preparedStatement.setString(1,emergencyType);
				preparedStatement.setString(2,districtName);
			}
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 					
				JSONObject jsonObject1=new JSONObject();			
				JSONObject jsonObject2=new JSONObject();			
				String monthName=resultSet.getString(1);
				int currentYearValue=resultSet.getInt(2);
				jsonObject1.put("label",monthName);
				jsonObject1.put("value",currentYearValue);
				jsonObject1.put("seriesName","Current Year Value");
				ambulanceComparisonFirstSeriesData.put(jsonObject1);
				
				int prevYearValue=resultSet.getInt(3);
				jsonObject2.put("label",monthName);
				jsonObject2.put("value",prevYearValue);	
				jsonObject2.put("seriesName","Previous Year Value");
				ambulanceComparisonSecondSeriesData.put(jsonObject2);
				
				finalList.put("ambulanceComparisonFirstSeriesData", ambulanceComparisonFirstSeriesData);
				finalList.put("ambulanceComparisonSecondSeriesData", ambulanceComparisonSecondSeriesData);
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
	
	private static BigDecimal truncateDecimal(double x,int numberofDecimals)
	{
	    if ( x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
	    } else {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
	    }
	}
	
	public JSONArray getAmbIncTableData(String date){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambIncTableData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_AMB_INC_TABLEDATA);	
			preparedStatement.setString(1, date);
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String district=resultSet.getString(1);
				int ambValue=resultSet.getInt(2);
				int incValue=resultSet.getInt(3);
								
				jsonObject.put("label",district);
				jsonObject.put("ambValue",ambValue);
				jsonObject.put("incValue",incValue);							
				ambIncTableData.put(jsonObject);
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
		return ambIncTableData;
	}
	
	public JSONArray getAmbIncByEmerTypeData(String districtName,String maxDate){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambIncByEmerTypeData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_AMB_INC_BY_EMERGENCYTYPE);			
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,maxDate);
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String district=resultSet.getString(1);
				int ambValue=resultSet.getInt(2);
				int incValue=resultSet.getInt(3);
				String emergencyType=resultSet.getString(4);
				String emergencySubType=resultSet.getString(5);
				int count=resultSet.getInt(6);			
				
				jsonObject.put("label",district);
				jsonObject.put("ambValue",ambValue);
				jsonObject.put("incValue",incValue);		
				jsonObject.put("emergencyType",emergencyType);		
				jsonObject.put("emergencySubType",emergencySubType);		
				jsonObject.put("count",count);		
				ambIncByEmerTypeData.put(jsonObject);
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
		return ambIncByEmerTypeData;
	}
	
	public JSONObject getHospitalIncData(String districtName,String maxDate){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray hospitalIncData = new JSONArray();
		JSONArray hospitalIncByEmerTypeChildData = new JSONArray();
		JSONArray hospitalIncByEmerTypeData = new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_HOSP_INC_DATA);			
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,maxDate);
			resultSet=preparedStatement.executeQuery();	
		  
			String prevHosp = null;
			boolean flag=false;
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String hospitalName=resultSet.getString(1);
				int incCount=resultSet.getInt(2);
				String emergencyType=resultSet.getString(3);
				int emerCount=resultSet.getInt(4);		
				
				if(!(hospitalName.equalsIgnoreCase(prevHosp))){				
					jsonObject.put("name",hospitalName);
					jsonObject.put("y",incCount);
					jsonObject.put("drilldown",hospitalName);
					hospitalIncData.put(jsonObject);
				}
				
				if(flag){
					if(!(hospitalName.equalsIgnoreCase(prevHosp))){
						JSONObject hospitalJsonObj=new JSONObject();
						hospitalJsonObj.put("id",prevHosp);
						hospitalJsonObj.put("data",hospitalIncByEmerTypeChildData);
						hospitalIncByEmerTypeData.put(hospitalJsonObj);
						hospitalIncByEmerTypeChildData=new JSONArray();
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(emergencyType);
						tempChildData.put(emerCount);
						hospitalIncByEmerTypeChildData.put(tempChildData);
					}
					else{
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(emergencyType);
						tempChildData.put(emerCount);
						hospitalIncByEmerTypeChildData.put(tempChildData);
					}
				}
				else{
					JSONArray tempChildData=new JSONArray();
					tempChildData.put(emergencyType);
					tempChildData.put(emerCount);
					hospitalIncByEmerTypeChildData.put(tempChildData);
					flag=true;
				}
				
				prevHosp=hospitalName;
			}
			JSONObject hospitalJsonObj=new JSONObject();
			hospitalJsonObj.put("id",prevHosp);
			hospitalJsonObj.put("data",hospitalIncByEmerTypeChildData);
			hospitalIncByEmerTypeData.put(hospitalJsonObj);
			
			finalList.put("hospitalIncData", hospitalIncData);
			finalList.put("hospitalIncByEmerTypeData", hospitalIncByEmerTypeData);
			
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
	
	public String getAmbulanceCaseReportMaxDate(){
		String maxDate=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_CASEREPORT_MAXDATE);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	maxDate = resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return maxDate;
	}
	
	public JSONArray getHospCaseData(String districtName,String maxDate,String hospitalName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray hospCaseData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_HOSP_CASE_DATA);	
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,maxDate);
			preparedStatement.setString(3,hospitalName);
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String incidentLocation=resultSet.getString(1);
				String emergencyType=resultSet.getString(3);
				String emergencySubType=resultSet.getString(4);
				String callTime=resultSet.getString(5);
				String assignedTime=resultSet.getString(6);
				String departureTime=resultSet.getString(7);
				String sceneArrivalTime=resultSet.getString(8);
				String sceneDepartureTime=resultSet.getString(9);
				String hospReachTime=resultSet.getString(10);				
								
				jsonObject.put("incidentLocation",incidentLocation);
				jsonObject.put("emergencyType",emergencyType);
				jsonObject.put("emergencySubType",emergencySubType);	
				jsonObject.put("callTime",callTime);
				jsonObject.put("assignedTime",assignedTime);
				jsonObject.put("departureTime",departureTime);	
				jsonObject.put("sceneArrivalTime",sceneArrivalTime);
				jsonObject.put("sceneDepartureTime",sceneDepartureTime);
				jsonObject.put("hospReachTime",hospReachTime);	
				hospCaseData.put(jsonObject);
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
		return hospCaseData;
	}
	
	public JSONArray getHospCaseComparisonData(String districtName,String maxDate,String hospitalName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray hospitalCaseComparisonData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_HOSP_CASE_COMPARISON_DATA);	
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,maxDate);
			preparedStatement.setString(3,hospitalName);
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String hospital=resultSet.getString(1);
				String seriesName=resultSet.getString(2);
				int value=resultSet.getInt(3);
								
				jsonObject.put("hospital",hospital);
				jsonObject.put("label",seriesName);
				jsonObject.put("value",value);
				hospitalCaseComparisonData.put(jsonObject);
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
		return hospitalCaseComparisonData;
	}
	
	public JSONArray getHospCaseTimingData(String districtName,String maxDate,String hospitalName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray hospitalCaseTimingChildData1 = new JSONArray();
		JSONArray hospitalCaseTimingChildData2 = new JSONArray();
		JSONArray hospitalCaseTimingChildData3 = new JSONArray();
		JSONArray hospitalCaseTimingChildData4 = new JSONArray();
		JSONArray hospitalCaseTimingChildData5 = new JSONArray();
		JSONArray hospitalCaseTimingData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_HOSP_CASE_TIMING_DATA);	
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,maxDate);
			preparedStatement.setString(3,hospitalName);
			resultSet=preparedStatement.executeQuery();	
			
			JSONObject jsonObject1=null;
			JSONObject jsonObject2=null;
			JSONObject jsonObject3=null;
			JSONObject jsonObject4=null;
			JSONObject jsonObject5=null;
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				jsonObject1=new JSONObject();
				jsonObject2=new JSONObject();
				jsonObject3=new JSONObject();
				jsonObject4=new JSONObject();
				jsonObject5=new JSONObject();
				String emergencyType=resultSet.getString(2);
				double case1Time=resultSet.getDouble(4);
				double case2Time=resultSet.getDouble(5);
				double case3Time=resultSet.getDouble(6);
				double case4Time=resultSet.getDouble(7);
				double case5Time=resultSet.getDouble(8);
								
				jsonObject.put("label",emergencyType);				
				jsonObject1.put("value",case1Time);				
				jsonObject2.put("value",case2Time);				
				jsonObject3.put("value",case3Time);				
				jsonObject4.put("value",case4Time);				
				jsonObject5.put("value",case5Time);
				hospitalCaseTimingChildData1.put(jsonObject1);
				hospitalCaseTimingChildData2.put(jsonObject2);
				hospitalCaseTimingChildData3.put(jsonObject3);
				hospitalCaseTimingChildData4.put(jsonObject4);
				hospitalCaseTimingChildData5.put(jsonObject5);
				hospitalCaseTimingData.put(jsonObject);
			}
			jsonObject1=new JSONObject();
			jsonObject2=new JSONObject();
			jsonObject3=new JSONObject();
			jsonObject4=new JSONObject();
			jsonObject5=new JSONObject();
			jsonObject1.put("seriesname","Assigned Time - Call Time");
			jsonObject1.put("data",hospitalCaseTimingChildData1);
			jsonObject2.put("seriesname","Departure Time - Assigned Time");
			jsonObject2.put("data",hospitalCaseTimingChildData2);
			jsonObject3.put("seriesname","Scene Arrival Time - Departure Time");
			jsonObject3.put("data",hospitalCaseTimingChildData3);
			jsonObject4.put("seriesname","Scene Departure Time - Scene Arrival Time");
			jsonObject4.put("data",hospitalCaseTimingChildData4);
			jsonObject5.put("seriesname","Hospital Reach Time - Scene Departure Time");
			jsonObject5.put("data",hospitalCaseTimingChildData5);
			hospitalCaseTimingData.put(jsonObject1);
			hospitalCaseTimingData.put(jsonObject2);
			hospitalCaseTimingData.put(jsonObject3);
			hospitalCaseTimingData.put(jsonObject4);
			hospitalCaseTimingData.put(jsonObject5);
			
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
		return hospitalCaseTimingData;
	}
	
	public JSONObject getHeatMapDataRange(String date,String month,String year,String frequency,String emergencyType,
			String districtName,String methodName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONObject heatMapDataRange=new JSONObject();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			if("onLoad".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ONLOAD_HEATMAP_DATA_RANGE);
			}
			else if("onSearch".equalsIgnoreCase(methodName)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.ONSEARCH_HEATMAP_DATA_RANGE);			
				if("Daily".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,date);
					preparedStatement.setString(2,date);
				}
				else if("Monthly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,Integer.toString(Util.getMonthIndexByName(month)));
				}
				else if("Yearly".equalsIgnoreCase(frequency)){
					preparedStatement.setString(1,year);
					preparedStatement.setString(2,"NULL");
				}
				preparedStatement.setString(3,emergencyType);
				preparedStatement.setString(4,districtName);
				preparedStatement.setString(5,frequency);
			}
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 	
				int maxValue=resultSet.getInt(1);
				int minValue=resultSet.getInt(2);
				int avgValue=resultSet.getInt(3);
								
				heatMapDataRange.put("minValue",minValue);
				heatMapDataRange.put("maxValue",maxValue);
				heatMapDataRange.put("avgValue",avgValue);	
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
		return heatMapDataRange;
	}
	
	public JSONArray getPredictionTalukData(String districtName,String emergencyType) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray predictionData=null;
		JSONObject obj=null;
		String nextDist=null;
		int x=0;
		int y=1;
		try{
			predictionData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_TALUK_PREDICTION_DATA);
			preparedStatement.setString(1,districtName);	
			preparedStatement.setString(2,emergencyType);	
			resultSet=preparedStatement.executeQuery();	
			while(resultSet.next())
			{ 				
				obj=new JSONObject();
				String prevDist=resultSet.getString(1);		
				if(prevDist.equalsIgnoreCase(nextDist)){
					y=y+1;
					obj.put("xAxis", x);
					obj.put("yAxis", y);
				}
				else{				
					x=x+1;
					y=1;
					nextDist=prevDist;
					obj.put("xAxis", x);
					obj.put("yAxis", y);				
				}			
				obj.put("district", prevDist);
				obj.put("hours", resultSet.getString(2));
				obj.put("value", resultSet.getInt(3));
				predictionData.put(obj);
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
		return predictionData;
	}
	
	public List<CommonStringList> getPredictionDistrictList(){
		List<CommonStringList> predictionDistrictList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			predictionDistrictList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_PREDICTION_DISTRICT);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	        	
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	predictionDistrictList.add(commonStringList);	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return predictionDistrictList;
	}
	
	public List<CommonStringList> getPredictionEmerTypeList(){
		List<CommonStringList> predictionEmerTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			predictionEmerTypeList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_PREDICTION_EMERGENCYTYPE);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	        	
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	predictionEmerTypeList.add(commonStringList);	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return predictionEmerTypeList;
	}
	
	public JSONArray getPredictionVillageData(String districtName,String talukName,String emergencyType) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray predictionVillageData=null;
		JSONObject obj=null;
		String nextDist=null;
		int x=0;
		int y=1;
		try{
			predictionVillageData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_VILLAGE_PREDICTION_DATA);
			preparedStatement.setString(1,districtName);	
			preparedStatement.setString(2,talukName);	
			preparedStatement.setString(3,emergencyType);	
			resultSet=preparedStatement.executeQuery();	
			while(resultSet.next())
			{ 				
				obj=new JSONObject();
				String prevDist=resultSet.getString(1);		
				if(prevDist.equalsIgnoreCase(nextDist)){
					y=y+1;
					obj.put("xAxis", x);
					obj.put("yAxis", y);
				}
				else{				
					x=x+1;
					y=1;
					nextDist=prevDist;
					obj.put("xAxis", x);
					obj.put("yAxis", y);				
				}			
				obj.put("district", prevDist);
				obj.put("hours", resultSet.getString(2));
				obj.put("value", resultSet.getInt(3));
				predictionVillageData.put(obj);
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
		return predictionVillageData;
	}
	
	public JSONArray getChartArray(String methodCall,String districtName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=new JSONArray();
		
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			if("TreeChart".equalsIgnoreCase(methodCall)){
				preparedStatement = connection.prepareStatement(ShdrcReportQueryList.TOTALCASESAVAILED_TREECHART);
			}
			else if("SunBurst".equalsIgnoreCase(methodCall)){
				preparedStatement = connection.prepareStatement(ShdrcReportQueryList.TOTALCASESAVAILED_SUNBURST);
				preparedStatement.setString(1,districtName);
			}
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String parentName=resultSet.getString(1);
				String childName=resultSet.getString(2);
				String sizeflag=resultSet.getString(4);				
					
				if("TreeChart".equalsIgnoreCase(methodCall)){
					String color=resultSet.getString(5);	
					jsonObject.put("parent",parentName);
					jsonObject.put("name",childName);
					jsonObject.put("color",color);
					if("Y".equalsIgnoreCase(sizeflag)){	
						jsonObject.put("size",30298);
					}
				}
				else if("SunBurst".equalsIgnoreCase(methodCall)){
					if(districtName.equalsIgnoreCase(parentName)){
						if("N".equalsIgnoreCase(sizeflag)){
							jsonObject.put("parent",parentName);
						}
						else if("Y".equalsIgnoreCase(sizeflag)){
							jsonObject.put("parent",parentName + "_Tal");
						}
						
					}else{
						jsonObject.put("parent",parentName);
					}
					
					if(districtName.equalsIgnoreCase(childName)){
						if("N".equalsIgnoreCase(sizeflag)){
							jsonObject.put("name",childName + "_Tal");
						}
						else if("Y".equalsIgnoreCase(sizeflag)){
							jsonObject.put("name",childName + "_Vil");
						}
					}
					else{
						jsonObject.put("name",childName);
					}
					if("Y".equalsIgnoreCase(sizeflag)){	
						jsonObject.put("size",7000);
					}
				}
				childList.put(jsonObject);		
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
		return childList;
	}
	
	public JSONObject getTotalCaseAvailedBarArray(String districtName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray talukCaseData = new JSONArray();
		JSONArray talukByVillageCaseChildData = new JSONArray();
		JSONArray talukByVillageCaseData = new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.TOTALCASESAVAILED_BARCHART);			
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		  
			String prevTaluk = null;
			boolean flag=false;
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String talukName=resultSet.getString(1);
				int talukCaseCount=resultSet.getInt(3);
				String villageName=resultSet.getString(4);
				int villageCaseCount=resultSet.getInt(6);		
				
				if(!(talukName.equalsIgnoreCase(prevTaluk))){				
					jsonObject.put("name",talukName);
					jsonObject.put("y",talukCaseCount);
					jsonObject.put("drilldown",talukName);
					talukCaseData.put(jsonObject);
				}
				
				if(flag){
					if(!(talukName.equalsIgnoreCase(prevTaluk))){
						JSONObject hospitalJsonObj=new JSONObject();
						hospitalJsonObj.put("id",prevTaluk);
						hospitalJsonObj.put("data",talukByVillageCaseChildData);
						talukByVillageCaseData.put(hospitalJsonObj);
						talukByVillageCaseChildData=new JSONArray();
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(villageName);
						tempChildData.put(villageCaseCount);
						talukByVillageCaseChildData.put(tempChildData);
					}
					else{
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(villageName);
						tempChildData.put(villageCaseCount);
						talukByVillageCaseChildData.put(tempChildData);
					}
				}
				else{
					JSONArray tempChildData=new JSONArray();
					tempChildData.put(villageName);
					tempChildData.put(villageCaseCount);
					talukByVillageCaseChildData.put(tempChildData);
					flag=true;
				}
				
				prevTaluk=talukName;
			}
			JSONObject hospitalJsonObj=new JSONObject();
			hospitalJsonObj.put("id",prevTaluk);
			hospitalJsonObj.put("data",talukByVillageCaseChildData);
			talukByVillageCaseData.put(hospitalJsonObj);
			
			finalList.put("talukCaseData", talukCaseData);
			finalList.put("talukByVillageCaseData", talukByVillageCaseData);
			
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
	
	public JSONArray getTalukCaseCountTableData(String districtName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray talukCaseCountTableData=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_TALUK_CASECOUNT_TABLE_DATA);
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String talukName=resultSet.getString(1);
				String range=resultSet.getString(2);
				int caseCount=resultSet.getInt(3);
				
				jsonObject.put("talukName",talukName);
				jsonObject.put("range",range);
				jsonObject.put("caseCount",caseCount);
				talukCaseCountTableData.put(jsonObject);				
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
		return talukCaseCountTableData;
	}
	
	public JSONArray getVillageCaseCountTableData(String districtName,String talukName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray villageCaseCountTableData=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_VILLAGE_CASECOUNT_TABLE_DATA);
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,talukName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String villageName=resultSet.getString(1);
				String range=resultSet.getString(2);
				int caseCount=resultSet.getInt(3);
				
				jsonObject.put("villageName",villageName);
				jsonObject.put("range",range);
				jsonObject.put("caseCount",caseCount);
				villageCaseCountTableData.put(jsonObject);				
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
		return villageCaseCountTableData;
	}
	
	@Override
	public JSONArray getBaseLocAmbData(String districtName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray BaseLocAmbList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.BASE_LOCATION_AMBULANCE_DATA);
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String baseLocation=resultSet.getString(2);
				String vehRegNo=resultSet.getString(1);
				String flag=resultSet.getString(3);
				
				jsonObject.put("baseLocation",baseLocation);
				jsonObject.put("vehRegNo",vehRegNo);
				jsonObject.put("flag",flag);
				
				BaseLocAmbList.put(jsonObject);				
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
		return BaseLocAmbList;
	}

	@Override
	public JSONObject ambulanceCaseType(String districtName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambulanceCaseTypeData = new JSONArray();
		JSONArray ambulanceCaseByTypeChildData = new JSONArray();
		JSONArray ambulanceCaseByTypeData = new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_CASE_TYPE_DATA);			
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		  
			String prevTrips = null;
			boolean flag=false;
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String caseType=resultSet.getString(1);
				int caseCount=resultSet.getInt(2);
				String emrType=resultSet.getString(3);
				int count=resultSet.getInt(4);
				
				if(!(caseType.equalsIgnoreCase(prevTrips))){				
					jsonObject.put("name",caseType);
					jsonObject.put("y",caseCount);
					jsonObject.put("drilldown",caseType);
					ambulanceCaseTypeData.put(jsonObject);
				}
				
				if(flag){
					if(!(caseType.equalsIgnoreCase(prevTrips))){
						JSONObject districtJsonObj=new JSONObject();
						districtJsonObj.put("id",prevTrips);
						districtJsonObj.put("data",ambulanceCaseByTypeChildData);
						ambulanceCaseByTypeData.put(districtJsonObj);
						ambulanceCaseByTypeChildData=new JSONArray();
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(emrType);
						tempChildData.put(count);
						ambulanceCaseByTypeChildData.put(tempChildData);
					}
					else{
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(emrType);
						tempChildData.put(count);
						ambulanceCaseByTypeChildData.put(tempChildData);
					}
				}
				else{
					JSONArray tempChildData=new JSONArray();
					tempChildData.put(emrType);
					tempChildData.put(count);
					ambulanceCaseByTypeChildData.put(tempChildData);
					flag=true;
				}
				
				prevTrips=caseType;
			}
			JSONObject hospitalJsonObj=new JSONObject();
			hospitalJsonObj.put("id",prevTrips);
			hospitalJsonObj.put("data",ambulanceCaseByTypeChildData);
			ambulanceCaseByTypeData.put(hospitalJsonObj);
			
			finalList.put("ambulanceCaseTypeData", ambulanceCaseTypeData);
			finalList.put("ambulanceCaseByTypeData", ambulanceCaseByTypeData);
			
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
	public JSONArray getGisTotalCasesAvailedData(String districtName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray gisBaseDataList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.TOTALCASESAVAILED_GIS);
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONArray jsonObject=new JSONArray();
				String hospitalName=resultSet.getString(2);
				BigDecimal latitude=truncateDecimal(resultSet.getDouble(3),4);
				BigDecimal longitude=truncateDecimal(resultSet.getDouble(4),4);
				
				jsonObject.put(hospitalName);
				jsonObject.put(latitude);
				jsonObject.put(longitude);				
				
				gisBaseDataList.put(jsonObject);				
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
		return gisBaseDataList;
	}

	
	@Override
	public JSONArray getGisBaseData(String districtName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray gisBaseDataList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GIS_BASE_DATA);
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONArray jsonObject=new JSONArray();
				String vehicleNo=resultSet.getString(1);
				BigDecimal latitude=truncateDecimal(resultSet.getDouble(2),4);
				BigDecimal longitude=truncateDecimal(resultSet.getDouble(3),4);
				String location=resultSet.getString(4);
				
				jsonObject.put(location + " : " + vehicleNo);
				jsonObject.put(latitude);
				jsonObject.put(longitude);
				jsonObject.put(vehicleNo);		
				gisBaseDataList.put(jsonObject);				
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
		return gisBaseDataList;
	}

	
	@Override
	public JSONArray getGisPathStatusData(String districtName, String vehicleNumber) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray gisPathStatusDataList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GIS_PATH_STATUS_DATA);
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,vehicleNumber);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONArray jsonObject=new JSONArray();
				BigDecimal latitude=truncateDecimal(resultSet.getDouble(2),4);
				BigDecimal longitude=truncateDecimal(resultSet.getDouble(3),4);
				
				jsonObject.put(latitude);	
				jsonObject.put(longitude);	
				gisPathStatusDataList.put(jsonObject);
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
		return gisPathStatusDataList;
	}

	@Override
	public JSONArray getGPSSteamingDataList(String districtName, String vehicleNumber) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray gpsStreamDataList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.GPS_STREAMING_DATA);
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,vehicleNumber);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String vehicleNo=resultSet.getString(1);
				BigDecimal latitude=truncateDecimal(resultSet.getDouble(2),4);
				BigDecimal longitude=truncateDecimal(resultSet.getDouble(3),4);
				String location=resultSet.getString(4);
				
				jsonObject.put("vehicleNo",vehicleNo);
				jsonObject.put("latitude",latitude);
				jsonObject.put("longitude",longitude);
				jsonObject.put("location",location);
				
				gpsStreamDataList.put(jsonObject);				
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
		return gpsStreamDataList;
	}
	
	public JSONArray getTotalNoOfAmbList() {
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;
        JSONArray totalNoOfAmbList=new JSONArray();
        try {
               connection=jdbcTemplate.getDataSource().getConnection();                   
               preparedStatement=connection.prepareStatement(ShdrcReportQueryList.TOTAL_NO_OF_AMBULANCE_DATA);
               resultSet=preparedStatement.executeQuery();     
                      
               while(resultSet.next())
               {                          
                     JSONObject jsonObject=new JSONObject();
                     String districtName=resultSet.getString(1);
                     int noOfAmb=resultSet.getInt(2);
                     
                     
                     jsonObject.put("districtName",districtName);
                     jsonObject.put("noOfAmb",noOfAmb);
                     
                     totalNoOfAmbList.put(jsonObject);                      
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
        return totalNoOfAmbList;
 }
	
	public JSONArray getTotalTripsData() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray totalTripsDataList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_TOTAL_TRIPS_DATA);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String districtName=resultSet.getString(1);
				int ambCount=resultSet.getInt(2);
				int noOfAmbUsed=resultSet.getInt(3);
				int caseCount=resultSet.getInt(4);
				
				jsonObject.put("districtName",districtName);
				jsonObject.put("ambCount",ambCount);
				jsonObject.put("noOfAmbUsed",noOfAmbUsed);
				jsonObject.put("caseCount",caseCount);
				totalTripsDataList.put(jsonObject);				
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
		return totalTripsDataList;
	}

	public JSONObject getAmbTotTripsTypeData(String districtName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambTotalTripsData = new JSONArray();
		JSONArray ambTotalTripsByTypeChildData = new JSONArray();
		JSONArray ambTotalTripsByTypeData = new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_ONLOAD_TOT_TRIPS_DATA);			
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		  
			String prevTrips = null;
			boolean flag=false;
			int ambTrip = 1;
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String ambNumber=resultSet.getString(2);
				int totAmb=resultSet.getInt(3);
				String numOfTrips=resultSet.getString(4);		
				
				if(!(numOfTrips.equalsIgnoreCase(prevTrips))){				
					jsonObject.put("name",numOfTrips);
					jsonObject.put("y",totAmb);
					jsonObject.put("drilldown",numOfTrips);
					ambTotalTripsData.put(jsonObject);
				}
				
				if(flag){
					if(!(numOfTrips.equalsIgnoreCase(prevTrips))){
						JSONObject districtJsonObj=new JSONObject();
						districtJsonObj.put("id",prevTrips);
						districtJsonObj.put("data",ambTotalTripsByTypeChildData);
						ambTotalTripsByTypeData.put(districtJsonObj);
						ambTotalTripsByTypeChildData=new JSONArray();
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(ambNumber);
						tempChildData.put(ambTrip);
						ambTotalTripsByTypeChildData.put(tempChildData);
					}
					else{
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(ambNumber);
						tempChildData.put(ambTrip);
						ambTotalTripsByTypeChildData.put(tempChildData);
					}
				}
				else{
					JSONArray tempChildData=new JSONArray();
					tempChildData.put(ambNumber);
					tempChildData.put(ambTrip);
					ambTotalTripsByTypeChildData.put(tempChildData);
					flag=true;
				}
				
				prevTrips=numOfTrips;
			}
			JSONObject hospitalJsonObj=new JSONObject();
			hospitalJsonObj.put("id",prevTrips);
			hospitalJsonObj.put("data",ambTotalTripsByTypeChildData);
			ambTotalTripsByTypeData.put(hospitalJsonObj);
			
			finalList.put("ambTotalTripsData", ambTotalTripsData);
			finalList.put("ambTotalTripsByTypeData", ambTotalTripsByTypeData);
			
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
	
	public String getOnLoadEmgTypeScrollList(String districtName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		String EmgData=null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.TOTALCASESAVAILED_EMG_SCROLL);
			preparedStatement.setString(1, districtName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	 
	        	 EmgData=resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return EmgData;
	}
	
	public String getOnLoadDistScrollList(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		String DistData=null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.TOTALCASESAVAILED_DIST_SCROLL);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	 
	        	DistData=resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return DistData;
	}	


	
	public String getEmergencyTypeAmbulanceCaseReportMaxDate(String emergencyType){
		String maxDate=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.EMERGENCYTYPE_AMBULANCE_CASEREPORT_MAXDATE);
			preparedStatement.setString(1, emergencyType);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	maxDate = resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return maxDate;
	}
	
	
	public List<CommonStringList> getEmergencyTypeOnLoadDistrictList(String emergencyType){
		List<CommonStringList> districtList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			districtList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.EMERGENCYTYPE_AMBULANCEONLOADDISTRICT);
			preparedStatement.setString(1, emergencyType);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	        	
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	districtList.add(commonStringList);	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return districtList;
	}
	
	
	public JSONArray getEmergencyTypeAmbIncTableData(String date,String emergencyType){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambIncTableData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.EMERGENCYTYPE_AMBULANCE_ONLOAD_AMB_INC_TABLEDATA);	
			preparedStatement.setString(1, date);
			preparedStatement.setString(2, emergencyType);
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String district=resultSet.getString(1);
				int ambValue=resultSet.getInt(2);
				int incValue=resultSet.getInt(3);
								
				jsonObject.put("label",district);
				jsonObject.put("ambValue",ambValue);
				jsonObject.put("incValue",incValue);							
				ambIncTableData.put(jsonObject);
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
		return ambIncTableData;
	}
	public JSONArray getEmergencyTypeAmbIncByEmerTypeData(String districtName,String maxDate,String emergencyType){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambIncByEmerTypeData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.EMERGENCYTYPE_AMBULANCE_ONLOAD_AMB_INC_BY_EMERGENCYTYPE);			
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,maxDate);
			preparedStatement.setString(3, emergencyType);
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String district=resultSet.getString(1);
				int ambValue=resultSet.getInt(2);
				int incValue=resultSet.getInt(3);
				String emergencyTypes=resultSet.getString(4);
				String emergencySubType=resultSet.getString(5);
				int count=resultSet.getInt(6);			
				
				jsonObject.put("label",district);
				jsonObject.put("ambValue",ambValue);
				jsonObject.put("incValue",incValue);		
				jsonObject.put("emergencyType",emergencyTypes);		
				jsonObject.put("emergencySubType",emergencySubType);		
				jsonObject.put("count",count);		
				ambIncByEmerTypeData.put(jsonObject);
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
		return ambIncByEmerTypeData;
	}
	
	public JSONObject getEmergencyTypeHospitalIncData(String districtName,String maxDate,String emergencyType){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray hospitalIncData = new JSONArray();
		JSONArray hospitalIncByEmerTypeChildData = new JSONArray();
		JSONArray hospitalIncByEmerTypeData = new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.EMERGENCYTYPE_AMBULANCE_ONLOAD_HOSP_INC_DATA);			
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,maxDate);
			preparedStatement.setString(3, emergencyType);
			resultSet=preparedStatement.executeQuery();	
		  
			String prevHosp = null;
			boolean flag=false;
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String hospitalName=resultSet.getString(1);
				int incCount=resultSet.getInt(2);
				String emergencyTypes=resultSet.getString(3);
				int emerCount=resultSet.getInt(4);		
				
				if(!(hospitalName.equalsIgnoreCase(prevHosp))){				
					jsonObject.put("name",hospitalName);
					jsonObject.put("y",incCount);
					jsonObject.put("drilldown",hospitalName);
					hospitalIncData.put(jsonObject);
				}
				
				if(flag){
					if(!(hospitalName.equalsIgnoreCase(prevHosp))){
						JSONObject hospitalJsonObj=new JSONObject();
						hospitalJsonObj.put("id",prevHosp);
						hospitalJsonObj.put("data",hospitalIncByEmerTypeChildData);
						hospitalIncByEmerTypeData.put(hospitalJsonObj);
						hospitalIncByEmerTypeChildData=new JSONArray();
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(emergencyTypes);
						tempChildData.put(emerCount);
						hospitalIncByEmerTypeChildData.put(tempChildData);
					}
					else{
						JSONArray tempChildData=new JSONArray();
						tempChildData.put(emergencyTypes);
						tempChildData.put(emerCount);
						hospitalIncByEmerTypeChildData.put(tempChildData);
					}
				}
				else{
					JSONArray tempChildData=new JSONArray();
					tempChildData.put(emergencyTypes);
					tempChildData.put(emerCount);
					hospitalIncByEmerTypeChildData.put(tempChildData);
					flag=true;
				}
				
				prevHosp=hospitalName;
			}
			JSONObject hospitalJsonObj=new JSONObject();
			hospitalJsonObj.put("id",prevHosp);
			hospitalJsonObj.put("data",hospitalIncByEmerTypeChildData);
			hospitalIncByEmerTypeData.put(hospitalJsonObj);
			
			finalList.put("hospitalIncData", hospitalIncData);
			finalList.put("hospitalIncByEmerTypeData", hospitalIncByEmerTypeData);
			
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
	
	public List<CommonStringList> getEmergencyTypePredictionDistrictList(String emergencyType){
		List<CommonStringList> predictionDistrictList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			predictionDistrictList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.EMERGENCYTYPE_AMBULANCE_PREDICTION_DISTRICT);
			preparedStatement.setString(1, emergencyType);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	        	
	        	CommonStringList commonStringList = new CommonStringList();
	        	commonStringList.setName(resultSet.getString(1));
	        	predictionDistrictList.add(commonStringList);	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return predictionDistrictList;
	}
	
	public List<CommonStringList> getDirecorateList(boolean isAllDirectorate,String role){
		List<CommonStringList> direcorateList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			direcorateList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			String sqlQuery=null;
			if(isAllDirectorate)
				sqlQuery="select \"Directorate_Id\",\"Directorate_Name\" from shdrc_dwh.\"Directorate_Master_Dim\" order by \"Directorate_Name\" asc";
			else
				sqlQuery="select \"Directorate_Id\",\"Directorate_Name\" from shdrc_dwh.\"Directorate_Master_Dim\" where \"Directorate_Name\" in ("+role+")  order by \"Directorate_Name\" asc";
			preparedStatement = connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	CommonStringList commonStringList = new CommonStringList();
		        	commonStringList.setId(resultSet.getInt(1));
		        	commonStringList.setName(resultSet.getString(2));
		        	direcorateList.add(commonStringList);
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
	        
			return direcorateList;
	}	
	
	
	public JSONArray getAmbBaseLocTickerData(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ambBaseLocTickerData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_BASELOC_TICKER_DATA);	
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String district=resultSet.getString(1);
				int totAmb=resultSet.getInt(2);
				int vehDown=resultSet.getInt(3);
				int ambBaseLoc=resultSet.getInt(4);
				int ambCase=resultSet.getInt(5);
				String ambCaseFlag=resultSet.getString(6);
				String baseLocFlag=resultSet.getString(7);
				
				if("R".equalsIgnoreCase(ambCaseFlag)){
					ambCaseFlag = "Red";
				}
				else if("G".equalsIgnoreCase(ambCaseFlag)){
					ambCaseFlag = "Green";
				}
				
				if("R".equalsIgnoreCase(baseLocFlag)){
					baseLocFlag = "Red";
				}
				else if("G".equalsIgnoreCase(baseLocFlag)){
					baseLocFlag = "Green";
				}
								
				jsonObject.put("district",district);
				jsonObject.put("totAmb",totAmb);
				jsonObject.put("vehDown",vehDown);	
				jsonObject.put("ambBaseLoc",ambBaseLoc);
				jsonObject.put("ambCase",ambCase);
				jsonObject.put("ambCaseFlag",ambCaseFlag);	
				jsonObject.put("baseLocFlag",baseLocFlag);
				ambBaseLocTickerData.put(jsonObject);
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
		return ambBaseLocTickerData;
	}

	public String getTalukTickerData(String districtName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		String scrollData=null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.TALUK_TICKER_DATA);
			preparedStatement.setString(1, districtName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	 
	        	scrollData=resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return scrollData;
	}
	
	public String getVillageTickerData(String districtName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		String scrollData=null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.VILLAGE_TICKER_DATA);
			preparedStatement.setString(1, districtName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	 
	        	scrollData=resultSet.getString(1);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
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
		return scrollData;
	}
	
	public JSONArray getPredictionScrollData(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray predictionScrollData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_PREDICTION_SCROLL_DATA);	
			resultSet=preparedStatement.executeQuery();	
		  
			while(resultSet.next())
			{ 				
				JSONArray jsonObject=new JSONArray();
				String district=resultSet.getString(1);
				int hrs=resultSet.getInt(2);
				int count=resultSet.getInt(3);
				String flag=resultSet.getString(4);
				
				if("R".equalsIgnoreCase(flag)){
					flag = "Red";
				}
				else if("G".equalsIgnoreCase(flag)){
					flag = "Green";
				}
				else if("A".equalsIgnoreCase(flag)){
					flag = "Yellow";
				}
				else if("B".equalsIgnoreCase(flag)){
					flag = "Blue";
				}
				
				jsonObject.put(district);
				jsonObject.put(hrs);
				jsonObject.put(count);	
				jsonObject.put(flag);
				predictionScrollData.put(jsonObject);
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
		return predictionScrollData;
	}
	
	public JSONArray getCycleTimeData() {		
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray cycleTimeData=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CYCLETIME_HEATGRID_108);
			resultSet=preparedStatement.executeQuery();	
		    		
			String prevDist = null;
			JSONObject jsonObject1=new JSONObject();
			boolean flag = false;
			while(resultSet.next())
			{ 	
				String district=resultSet.getString(1);
				String kmCases=resultSet.getString(2);
				int count=resultSet.getInt(3);
				
				if((!(district.equalsIgnoreCase(prevDist))) && flag == true){
					JSONObject jsonObject2=new JSONObject();
					jsonObject2.put("State",prevDist);
					jsonObject2.put("freq",jsonObject1);
					cycleTimeData.put(jsonObject2);
					
					jsonObject1=new JSONObject();					
					if("0 to 30 mins".equalsIgnoreCase(kmCases)){
						kmCases = "verylow";
					}
					else if("30 to 45  mins".equalsIgnoreCase(kmCases)){
						kmCases = "low";
					}
					else if("45 to 60 mins".equalsIgnoreCase(kmCases)){
						kmCases = "lowmid";
					}
					else if("60 to 90 mins".equalsIgnoreCase(kmCases)){
						kmCases = "mid";
					}
					else if("90 to 120 mins".equalsIgnoreCase(kmCases)){
						kmCases = "highmid";
					}
					else if("120 to 180 mins".equalsIgnoreCase(kmCases)){
						kmCases = "high";
					}
					else if("180mins and Above".equalsIgnoreCase(kmCases)){
						kmCases = "veryhigh";
					}
					jsonObject1.put(kmCases,count);
					prevDist = district;
				}
				else{	
					if("0 to 30 mins".equalsIgnoreCase(kmCases)){
						kmCases = "verylow";
					}
					else if("30 to 45  mins".equalsIgnoreCase(kmCases)){
						kmCases = "low";
					}
					else if("45 to 60 mins".equalsIgnoreCase(kmCases)){
						kmCases = "lowmid";
					}
					else if("60 to 90 mins".equalsIgnoreCase(kmCases)){
						kmCases = "mid";
					}
					else if("90 to 120 mins".equalsIgnoreCase(kmCases)){
						kmCases = "highmid";
					}
					else if("120 to 180 mins".equalsIgnoreCase(kmCases)){
						kmCases = "high";
					}
					else if("180mins and Above".equalsIgnoreCase(kmCases)){
						kmCases = "veryhigh";
					}
					jsonObject1.put(kmCases,count);
					prevDist = district;
					flag=true;
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
		return cycleTimeData;
	}
	
	public JSONArray getCycleTimeDataStack(String districtName, String buttonClick){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray cycleTimeStackChildData1 = new JSONArray();
		JSONArray cycleTimeStackChildData2 = new JSONArray();
		JSONArray cycleTimeStackChildData3 = new JSONArray();
		JSONArray cycleTimeStackChildData4 = new JSONArray();
		JSONArray cycleTimeStackChildData5 = new JSONArray();
		JSONArray cycleTimeStackChildData6 = new JSONArray();
		JSONArray cycleTimeStackData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			if("MAX".equalsIgnoreCase(buttonClick)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CYCLETIME_STACK_DATA_108_MAX);
			}
			else if("MIN".equalsIgnoreCase(buttonClick)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CYCLETIME_STACK_DATA_108_MIN);
			}
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
			
			JSONObject jsonObject1=null;
			JSONObject jsonObject2=null;
			JSONObject jsonObject3=null;
			JSONObject jsonObject4=null;
			JSONObject jsonObject5=null;
			JSONObject jsonObject6=null;
		  
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				jsonObject1=new JSONObject();
				jsonObject2=new JSONObject();
				jsonObject3=new JSONObject();
				jsonObject4=new JSONObject();
				jsonObject5=new JSONObject();
				jsonObject6=new JSONObject();
				String id=resultSet.getString(1);
				double case1Time=resultSet.getDouble(2);
				double case2Time=resultSet.getDouble(3);
				double case3Time=resultSet.getDouble(4);
				double case4Time=resultSet.getDouble(5);
				double case5Time=resultSet.getDouble(6);
				double case6Time=resultSet.getDouble(7);
								
				jsonObject.put("label",id);				
				jsonObject1.put("value",case1Time);				
				jsonObject2.put("value",case2Time);				
				jsonObject3.put("value",case3Time);				
				jsonObject4.put("value",case4Time);				
				jsonObject5.put("value",case5Time);
				jsonObject6.put("value",case6Time);
				cycleTimeStackChildData1.put(jsonObject1);
				cycleTimeStackChildData2.put(jsonObject2);
				cycleTimeStackChildData3.put(jsonObject3);
				cycleTimeStackChildData4.put(jsonObject4);
				cycleTimeStackChildData5.put(jsonObject5);
				cycleTimeStackChildData6.put(jsonObject6);
				cycleTimeStackData.put(jsonObject);
			}
			jsonObject1=new JSONObject();
			jsonObject2=new JSONObject();
			jsonObject3=new JSONObject();
			jsonObject4=new JSONObject();
			jsonObject5=new JSONObject();
			jsonObject6=new JSONObject();
			jsonObject1.put("seriesname","Wb (Time)");
			jsonObject1.put("data",cycleTimeStackChildData1);
			jsonObject2.put("seriesname","B-S (Mins)");
			jsonObject2.put("data",cycleTimeStackChildData2);
			jsonObject3.put("seriesname","Scene (Mins)");
			jsonObject3.put("data",cycleTimeStackChildData3);
			jsonObject4.put("seriesname","Sh (Time)");
			jsonObject4.put("data",cycleTimeStackChildData4);
			jsonObject5.put("seriesname","Hospital (Mins)");
			jsonObject5.put("data",cycleTimeStackChildData5);
			jsonObject6.put("seriesname","Hb (Time)");
			jsonObject6.put("data",cycleTimeStackChildData6);
			cycleTimeStackData.put(jsonObject1);
			cycleTimeStackData.put(jsonObject2);
			cycleTimeStackData.put(jsonObject3);
			cycleTimeStackData.put(jsonObject4);
			cycleTimeStackData.put(jsonObject5);
			cycleTimeStackData.put(jsonObject6);
			
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
		return cycleTimeStackData;
	}
	
	public JSONArray getCycleTimeDataPie(String districtName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray cycleTimePieData = new JSONArray();
		try {	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CYCLETIME_PIE_DATA_108);			
			preparedStatement.setString(1,districtName);
			resultSet=preparedStatement.executeQuery();	
		  while(resultSet.next()){
			  JSONObject jsonObject=new JSONObject();
			  String remarks=resultSet.getString(1);
			  int count=resultSet.getInt(2);
				jsonObject.put("name",remarks);
				jsonObject.put("y",count);
				
				cycleTimePieData.put(jsonObject);
			  
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
		return cycleTimePieData;
	}
	
	public JSONArray getKilometersCoveredList() {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray kilometersCoveredData=null;
		JSONObject obj=null;
		String nextDist=null;
		int x=0;
		int y=1;
		try{
			kilometersCoveredData = new JSONArray();
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_KILOMETERS_COVERED_LIST);
			resultSet=preparedStatement.executeQuery();	
			while(resultSet.next())
			{ 				
				obj=new JSONObject();
				String prevDist=resultSet.getString(1);		
				if(prevDist.equalsIgnoreCase(nextDist)){
					y=y+1;
					obj.put("xAxis", x);
					obj.put("yAxis", y);
				}
				else{				
					x=x+1;
					y=1;
					nextDist=prevDist;
					obj.put("xAxis", x);
					obj.put("yAxis", y);				
				}			
				
				obj.put("hours", resultSet.getString(2));
				obj.put("district", prevDist);
				obj.put("value", resultSet.getInt(3));
				kilometersCoveredData.put(obj);
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
		return kilometersCoveredData;
	}

public JSONArray getKilometersCoveredAmbData(String districtName, String hours) {
	
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray kilometersCoveredAmbData=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.KILOMETERS_COVERED_AMBULANCE_DATA);
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,hours);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String ambNumber=resultSet.getString(1);
				String sourceLocation=resultSet.getString(2);
				String hospitalName=resultSet.getString(3);
				String iftNiftText=resultSet.getString(4);
				String flag=resultSet.getString(5);
				if(hospitalName==null){
					hospitalName="";
				}
				jsonObject.put("ambNumber",ambNumber);
				jsonObject.put("sourceLocation",sourceLocation);
				jsonObject.put("hospitalName",hospitalName);
				jsonObject.put("iftNiftText",iftNiftText);
				jsonObject.put("flag",flag);
				
				kilometersCoveredAmbData.put(jsonObject);				
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
		return kilometersCoveredAmbData;
	}

public JSONArray getAmbKilometersCoveredData(String districtName) {
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray ambKilometersCoveredData = new JSONArray();
	try {	
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_KILOMETERS_COVERED_IFT_DATA);			
		preparedStatement.setString(1,districtName);
		resultSet=preparedStatement.executeQuery();	
	  while(resultSet.next()){
		  JSONObject jsonObject=new JSONObject();
		  String ambNo=resultSet.getString(1);
		  String bbInKm=resultSet.getString(2);
		  BigDecimal bbInMin=truncateDecimal(resultSet.getDouble(3),4); 
			jsonObject.put("ambNo",ambNo);
			jsonObject.put("bbInKm",bbInKm);
			jsonObject.put("bbInMin",bbInMin);
			
			ambKilometersCoveredData.put(jsonObject);
		  
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
	return ambKilometersCoveredData;
}
	
	public JSONArray getKilometersCoveredDrilldown() {		
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray kilometersCoveredDrilldown=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.KILOMETERS_COVERED_DRILLDOWN_DATA);
			resultSet=preparedStatement.executeQuery();	
		    		
			String prevDist = null;
			JSONObject jsonObject1=new JSONObject();
			boolean flag = false;
			while(resultSet.next())
			{ 	
				String district=resultSet.getString(1);
				String kmDrilldown=resultSet.getString(2);
				int count=resultSet.getInt(3);
				
				if((!(district.equalsIgnoreCase(prevDist))) && flag == true){
					JSONObject jsonObject2=new JSONObject();
					jsonObject2.put("State",prevDist);
					jsonObject2.put("freq",jsonObject1);
					kilometersCoveredDrilldown.put(jsonObject2);
					
					jsonObject1=new JSONObject();					
					if("0 to 15 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "verylow";
					}
					else if("15 to 30  KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "low";
					}
					else if("30 to 60 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "lowmid";
					}
					else if("60 to 100 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "mid";
					}
					else if("100 to 150 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "highmid";
					}
					else if("150 to 200 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "high";
					}
					else if("200 KM and Above".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "veryhigh";
					}
					jsonObject1.put(kmDrilldown,count);
					prevDist = district;
				}
				else{	
					if("0 to 15 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "verylow";
					}
					else if("15 to 30  KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "low";
					}
					else if("30 to 60 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "lowmid";
					}
					else if("60 to 100 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "mid";
					}
					else if("100 to 150 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "highmid";
					}
					else if("150 to 200 KM".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "high";
					}
					else if("200 KM and Above".equalsIgnoreCase(kmDrilldown)){
						kmDrilldown = "veryhigh";
					}
					jsonObject1.put(kmDrilldown,count);
					prevDist = district;
					flag=true;
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
		return kilometersCoveredDrilldown;
	}
	
	public JSONObject getKmCoveredMultiSeriesData(String districtName,String buttonClick){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray kmCoveredFirstSeriesData=new JSONArray();
		JSONArray kmCoveredSecondSeriesData=new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {					
			connection=jdbcTemplate.getDataSource().getConnection();
			if("ift".equalsIgnoreCase(buttonClick)){
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.KM_COVERED_MULTISERIES);
			}
			else if("nonIft".equalsIgnoreCase(buttonClick)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.KM_COVERED_MULTISERIES_NON_IFT);
				}
			preparedStatement.setString(1, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 					
				JSONObject jsonObject1=new JSONObject();			
				JSONObject jsonObject2=new JSONObject();			
				String ambNo=resultSet.getString(1);
				int kms=resultSet.getInt(2);
				BigDecimal mins=truncateDecimal(resultSet.getDouble(3),4);
				jsonObject1.put("label",ambNo);
				jsonObject1.put("value",kms);
				jsonObject1.put("link","JavaScript:showAlert("+ambNo+","+kms+","+mins+")" );
				
				jsonObject1.put("seriesName","Kms");
				kmCoveredFirstSeriesData.put(jsonObject1);
				
				
				jsonObject2.put("label",ambNo);
				jsonObject2.put("value",mins);	
				jsonObject2.put("seriesName","Mins");
				kmCoveredSecondSeriesData.put(jsonObject2);
				
				finalList.put("kmCoveredFirstSeriesData", kmCoveredFirstSeriesData);
				finalList.put("kmCoveredSecondSeriesData", kmCoveredSecondSeriesData);
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

	public JSONArray getkilometersCoveredAmbulanceData(String districtName,
			String ambNo, int kms, String mins) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray kilometersCoveredAmbData=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.KILOMETERS_COVERED_AMBULANCE_DATA);
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,ambNo);
			preparedStatement.setString(3,Integer.toString(kms));
			preparedStatement.setString(4,mins);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				
				String sourceLocation=resultSet.getString(1);
				String hospitalName=resultSet.getString(2);
				String iftNiftText=resultSet.getString(3);
				String emgType=resultSet.getString(4);
				String emgSubType=resultSet.getString(5);
				String flag=resultSet.getString(6);
				if(hospitalName==null){
					hospitalName="";
				}
				jsonObject.put("sourceLocation",sourceLocation);
				jsonObject.put("hospitalName",hospitalName);
				jsonObject.put("iftNiftText",iftNiftText);
				jsonObject.put("emgType",emgType);
				jsonObject.put("emgSubType",emgSubType);
				jsonObject.put("flag",flag);
				
				kilometersCoveredAmbData.put(jsonObject);				
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
		return kilometersCoveredAmbData;
	}
	
	public JSONObject getCycleTimeComparisonData(String districtName,String buttonClick){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray cycleTimeFirstSeriesData=new JSONArray();
		JSONArray cycleTimeSecondSeriesData=new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {		
			connection=jdbcTemplate.getDataSource().getConnection();
			if("MAX".equalsIgnoreCase(buttonClick)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CYCLETIME_COMPARISON_TOP_DATA);
			}
			else if("MIN".equalsIgnoreCase(buttonClick)){
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.CYCLETIME_COMPARISON_BOTTOM_DATA);
			}
			preparedStatement.setString(1, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 					
				JSONObject jsonObject1=new JSONObject();			
				JSONObject jsonObject2=new JSONObject();			
				String label=resultSet.getString(1);
				BigDecimal deliverTime=truncateDecimal(resultSet.getDouble(3),4);
				jsonObject1.put("label",label);
				jsonObject1.put("value",deliverTime);
				jsonObject1.put("seriesName","Deliver Time");
				cycleTimeFirstSeriesData.put(jsonObject1);
				
				BigDecimal returnTime=truncateDecimal(resultSet.getDouble(4),4);
				jsonObject2.put("label",label);
				jsonObject2.put("value",returnTime);	
				jsonObject2.put("seriesName","Return Time");
				cycleTimeSecondSeriesData.put(jsonObject2);
				
				finalList.put("cycleTimeFirstSeriesData", cycleTimeFirstSeriesData);
				finalList.put("cycleTimeSecondSeriesData", cycleTimeSecondSeriesData);
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
	
	
	public JSONArray getTnegaDistrictName() {
	Connection connection = null;
	PreparedStatement preparedStatement =null;
    ResultSet resultSet =null;
    JSONArray finalList=new JSONArray();
	try {

		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcReportQueryList.AMBULANCE_TNEGA_DATA);
		resultSet = preparedStatement.executeQuery();
	 while(resultSet.next())
     {                          
           JSONObject jsonObject=new JSONObject();
           /*int districtName=resultSet.getInt(1);
           int noOfAmb=resultSet.getInt(2);*/
           String districtName= ""+Integer.toString(resultSet.getInt(1))+"";
           String  noOfAmb=Integer.toString(resultSet.getInt(2)); 
           
           
           jsonObject.put("code",districtName);
           jsonObject.put("value",""+noOfAmb+"");
           
           finalList.put(jsonObject);                      
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

	
    public JSONArray getEmriDistricltList() {
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;
        JSONArray emriDistlist=new JSONArray();
        try {
               connection=jdbcTemplate.getDataSource().getConnection();                   
               preparedStatement=connection.prepareStatement(ShdrcReportQueryList.EMRI_DISTRICT_LIST);
               resultSet=preparedStatement.executeQuery();     
                      
               while(resultSet.next())
               {                          
                     JSONObject jsonObject=new JSONObject();
                     int District_id=resultSet.getInt(1);
                     String district_name=resultSet.getString(2);
                     int tot_amb=resultSet.getInt(3);
                     BigDecimal lat=truncateDecimal(resultSet.getDouble(4),4);
                     BigDecimal lon=truncateDecimal(resultSet.getDouble(5),4);                     
                     
                     jsonObject.put("DistrictId",District_id);
                  jsonObject.put("districtName",district_name);
                  jsonObject.put("Tot_Amb",tot_amb);
                  jsonObject.put("lat",lat);
                  jsonObject.put("lon",lon);
                     
                     emriDistlist.put(jsonObject);                      
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
        return emriDistlist;
    }
    
    
    public JSONArray getEmriTaluktList(String distName) {
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;
        JSONArray emriTaluklist=new JSONArray();
        try {
               connection=jdbcTemplate.getDataSource().getConnection();                   
               preparedStatement=connection.prepareStatement(ShdrcReportQueryList.EMRI_TALUK_LIST);
                      preparedStatement.setString(1, distName);

               resultSet=preparedStatement.executeQuery();     
                      
               while(resultSet.next())
               {                          
                     JSONObject jsonObject=new JSONObject();
                     int District_id=resultSet.getInt(1);
                     int TalukId=resultSet.getInt(2);
                     String Taluk=resultSet.getString(3);
                     
                     
                     jsonObject.put("DistrictId",District_id);
                  jsonObject.put("TalukId",TalukId);
                  jsonObject.put("Taluk",Taluk);
                     
                  emriTaluklist.put(jsonObject);                      
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
        return emriTaluklist;
    }

    public JSONArray getVehicleBaseLoc(String districtName,String vehicleNo){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray gisBaseDataList=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.VEHICLE_BASE_LOCATION);
			preparedStatement.setString(1,districtName);
			preparedStatement.setString(2,vehicleNo);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONArray jsonObject=new JSONArray();
				BigDecimal latitude=truncateDecimal(resultSet.getDouble(2),4);
				BigDecimal longitude=truncateDecimal(resultSet.getDouble(3),4);
				String location=resultSet.getString(4);
				
				jsonObject.put(location + " : " + vehicleNo);
				jsonObject.put(latitude);
				jsonObject.put(longitude);
				jsonObject.put(vehicleNo);		
				gisBaseDataList.put(jsonObject);				
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
		return gisBaseDataList;
	}

}
