package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IHSDashboardDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class HSDashboardDAOJdbc implements IHSDashboardDAO {	

private static final Logger logger = Logger.getLogger(HSDashboardDAOJdbc.class);
@Autowired
JdbcTemplate jdbcTemplate;
public JSONArray getSearchOptionsList(String dashIndName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray jsonArray=new JSONArray();
	
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.HSDASHBOARD_SEARCHOPTIONS);
		preparedStatement.setString(1,dashIndName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	String searchOptions = resultSet.getString(1);
        	String param = resultSet.getString(2);
        	String indName = resultSet.getString(3);
        	
			jsonObject.put("label", searchOptions);
			jsonObject.put("value", param);
			jsonObject.put("indName", indName);
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

public List<String> getChildIndList(String param1){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	List<String> childIndList=new ArrayList<String>();
	try{
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.CHILDINDDETAILSLIST);			
		preparedStatement.setString(1,param1);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			String childIndName=resultSet.getString(1);
			childIndList.add(childIndName);
		}
	}catch (SQLException e) {
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
	return childIndList;
}


public List<String> getMonthList(){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	List<String> monthList=new ArrayList<String>();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.MONTH_LIST);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	String month=resultSet.getString(1);
        	monthList.add(month);
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
        return monthList;
}

public   List<String> getDistrictList(){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	List<String> districtList=new ArrayList<String>();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.DISTRICT_LIST);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	String district=resultSet.getString(1);
        	districtList.add(district);
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
	
public List<String> getInstitutionList(){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	List<String> institutionList=new ArrayList<String>();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.INSTITUTION_LIST);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	String institution=resultSet.getString(1);
        	if(institution!=null)
        		institutionList.add(institution);
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
        return institutionList;
}

public List<String> getYearList(){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	List<String> yearList=new ArrayList<String>();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection.prepareStatement(ShdrcQueryList.YEAR_LIST);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	String year=resultSet.getString(1);
        	yearList.add(year);
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
        return yearList;
}



public JSONArray getFourParamIndDetailsList(String year,String district,String month,
		String institution,String indName,int countChildInd,List<String> childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray fourParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();	
		if(countChildInd==2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.FOURPARAMDERIVEDINDDETAILSLIST);	
			/*preparedStatement.setString(5,childIndList.get(0));
			preparedStatement.setString(6,childIndList.get(1));*/
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YMDIFOURPARAMDIRECTINDDYSFUNCTIONDETAILSLIST);
		}
		else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.FOURPARAMDIRECTINDDETAILSLIST);
		}
			preparedStatement.setInt(1,yearParam);
			preparedStatement.setString(2,month);
			preparedStatement.setString(3,district);				
			preparedStatement.setString(4,institution);	
			preparedStatement.setString(5,indName);
					
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	int yearValue = resultSet.getInt(1);
	        	String monthValue=resultSet.getString(2);
	        	String distValue=resultSet.getString(3);
	        	String instValue=resultSet.getString(4);
	        	String indicatorName=resultSet.getString(5);
	        	double indValue = resultSet.getDouble(6);

	        	jsonObject.put("yearValue",yearValue);
	        	jsonObject.put("monthValue",monthValue);
	        	jsonObject.put("distValue",distValue);
				jsonObject.put("label",instValue);
				jsonObject.put("indicatorName",indicatorName);
				if(!Util.isPercentageIndicator(indName))
	        		jsonObject.put("value",Util.roundIndValue(indValue));
	        	else
	        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
				fourParamIndDetailsList.put(jsonObject);		        	
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
        
		return fourParamIndDetailsList;
} 

public JSONArray getFourParamChildIndDataList(String year,String district,String month,String institution,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray fourChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();		
		preparedStatement = connection.prepareStatement(ShdrcQueryList.FOURPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);			
		preparedStatement.setString(2,month);
		preparedStatement.setString(3,district);				
		preparedStatement.setString(4,institution);
		preparedStatement.setString(5,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	int yearValue = resultSet.getInt(1);		        	
	        	String monthValue=resultSet.getString(2);
	        	String distValue=resultSet.getString(3);
	        	String instValue=resultSet.getString(4);
	        	String childIndName = resultSet.getString(5);
	        	double indValue = resultSet.getDouble(6);		        	
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("monthValue",monthValue);
				jsonObject.put("distValue",distValue);
				jsonObject.put("instValue",instValue);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				fourChildIndDataList.put(jsonObject);		        	
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
		return fourChildIndDataList;
}

public JSONArray getYMDThreeParamIndDetailsList(String year,String month,String district,String indName,int countChildInd,List<String>childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray threeParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();		
		if(countChildInd == 2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDMTHREEPARAMDERIVEDINDDETAILSLIST);						
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDMTHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST);
		}else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDMTHREEPARAMDIRECTINDDETAILSLIST);
		}			
			
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,month);
		preparedStatement.setString(3,district);
		preparedStatement.setString(4,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	int yearValue = resultSet.getInt(1);
        	String secondParameter = resultSet.getString(2);
        	String thirdParameter = resultSet.getString(3);
        	String indicatorName = resultSet.getString(4);
        	double indValue = resultSet.getDouble(5);
        
        	jsonObject.put("yearValue",yearValue);
        	jsonObject.put("secondParameter",secondParameter);
        	jsonObject.put("label",thirdParameter);
        	jsonObject.put("indicatorName",indicatorName);
        	if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("value",Util.roundIndValue(indValue));
        	else
        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
			threeParamIndDetailsList.put(jsonObject);		        	
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
        
		return threeParamIndDetailsList;
}

public JSONArray getYDIThreeParamIndDetailsList(String year,String district,String institution,String indName,int countChildInd,List<String>childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray threeParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();		
		if(countChildInd == 2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDITHREEPARAMDERIVEDINDDETAILSLIST);
			/*preparedStatement.setString(4,childIndList.get(0));
			preparedStatement.setString(5,childIndList.get(1));*/							
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST);
		}
		else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDITHREEPARAMDIRECTINDDETAILSLIST);
		}				
		
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,district);
		preparedStatement.setString(3,institution);
		preparedStatement.setString(4,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	int yearValue = resultSet.getInt(1);
        	String secondParameter = resultSet.getString(2);
        	String thirdParameter = resultSet.getString(3);	 
        	String indicatorName = resultSet.getString(4);
        	double indValue = resultSet.getDouble(5);
        
        	jsonObject.put("yearValue",yearValue);
        	jsonObject.put("secondParameter",secondParameter);
        	jsonObject.put("label",thirdParameter);
        	jsonObject.put("indicatorName",indicatorName);
        	if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("value",Util.roundIndValue(indValue));
        	else
        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
			threeParamIndDetailsList.put(jsonObject);		        	
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
        
		return threeParamIndDetailsList;
}

public JSONArray getYMIThreeParamIndDetailsList(String year,String month,String institution,String indName,int countChildInd,List<String>childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray threeParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();		
		if(countChildInd == 2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YMITHREEPARAMDERIVEDINDDETAILSLIST);						
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YMITHREEPARAMDIRECTINDDYSFUNCTIONDETAILSLIST);
		}
		else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YMITHREEPARAMDIRECTINDDETAILSLIST);
		}			
			
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,month);
		preparedStatement.setString(3,institution);
		preparedStatement.setString(4,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	int yearValue = resultSet.getInt(1);
        	String secondParameter = resultSet.getString(2);
        	String thirdParameter = resultSet.getString(3);	 
        	String indicatorName = resultSet.getString(4);
        	double indValue = resultSet.getDouble(5);
        
        	jsonObject.put("yearValue",yearValue);
        	jsonObject.put("secondParameter",secondParameter);
        	jsonObject.put("label",thirdParameter);
        	jsonObject.put("indicatorName",indicatorName);
        	if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("value",Util.roundIndValue(indValue));
        	else
        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
			threeParamIndDetailsList.put(jsonObject);		        	
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
        
		return threeParamIndDetailsList;
}

public JSONArray getYMDThreeParamChildIndDataList(String year,String month,String district,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray threeChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();			
		preparedStatement = connection.prepareStatement(ShdrcQueryList.YDMTHREEPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,month);
		preparedStatement.setString(3,district);
		preparedStatement.setString(3,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	
	        	int yearValue = resultSet.getInt(1);		        	
	        	String parameter2=resultSet.getString(2);
	        	String parameter3=resultSet.getString(3);
	        	String childIndName = resultSet.getString(4);
	        	double indValue = resultSet.getDouble(5);		        	
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("parameter2",parameter2);
				jsonObject.put("parameter3",parameter3);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				threeChildIndDataList.put(jsonObject);		        	
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
        
		return threeChildIndDataList;
}

public JSONArray getYDIThreeParamChildIndDataList(String year,String district,String institution,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray threeChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();			
		preparedStatement = connection.prepareStatement(ShdrcQueryList.YDITHREEPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,district);
		preparedStatement.setString(3,institution);
		preparedStatement.setString(3,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	
	        	int yearValue = resultSet.getInt(1);		        	
	        	String parameter2=resultSet.getString(2);
	        	String parameter3=resultSet.getString(3);
	        	String childIndName = resultSet.getString(4);
	        	double indValue = resultSet.getDouble(5);		        	
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("parameter2",parameter2);
				jsonObject.put("parameter3",parameter3);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				threeChildIndDataList.put(jsonObject);		        	
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
        
		return threeChildIndDataList;
}

public JSONArray getYMIThreeParamChildIndDataList(String year,String month,String institution,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray threeChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();			
		preparedStatement = connection.prepareStatement(ShdrcQueryList.YMITHREEPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,month);
		preparedStatement.setString(3,institution);
		preparedStatement.setString(3,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	
	        	int yearValue = resultSet.getInt(1);		        	
	        	String parameter2=resultSet.getString(2);
	        	String parameter3=resultSet.getString(3);
	        	String childIndName = resultSet.getString(4);
	        	double indValue = resultSet.getDouble(5);		        	
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("parameter2",parameter2);
				jsonObject.put("parameter3",parameter3);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				threeChildIndDataList.put(jsonObject);		        	
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
        
		return threeChildIndDataList;
}

public JSONArray getYMTwoParamIndDetailsList(String year,String month,String indName,int countChildInd,List<String> childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray twoParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		if(countChildInd==2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YMTWOPARAMDERIVEDINDDETAILSLIST);		
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YMTWOPARAMDERIVEDINDDYSFUNCTIODETAILSLIST);
		}else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YMTWOPARAMDIRECTINDDETAILSLIST);
		}		
			
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,month);
		preparedStatement.setString(3,indName);
		
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	int yearValue = resultSet.getInt(1);
        	String parameter2 = resultSet.getString(2);
        	String indicatorName = resultSet.getString(3);
        	double indValue = resultSet.getDouble(4);
        
        	jsonObject.put("yearValue",yearValue);
        	jsonObject.put("label",parameter2);
        	jsonObject.put("indicatorName",indicatorName);
        	if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("value",Util.roundIndValue(indValue));
        	else
        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
			twoParamIndDetailsList.put(jsonObject);		        	
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
        
		return twoParamIndDetailsList;
}

public JSONArray getYITwoParamIndDetailsList(String year,String institution,String indName,int countChildInd,List<String> childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray twoParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		if(countChildInd==2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YITWOPARAMDERIVEDINDDETAILSLIST);		
			
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YITWOPARAMDERIVEDINDDYSFUNCTIODETAILSLIST);
		}else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YITWOPARAMDIRECTINDDETAILSLIST);
		}		
			
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,institution);
		preparedStatement.setString(3,indName);
		
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	int yearValue = resultSet.getInt(1);
        	String parameter2 = resultSet.getString(2);
        	String indicatorName = resultSet.getString(3);
        	double indValue = resultSet.getDouble(4);
        
        	jsonObject.put("yearValue",yearValue);
        	jsonObject.put("label",parameter2);
        	jsonObject.put("indicatorName",indicatorName);
        	if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("value",Util.roundIndValue(indValue));
        	else
        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
			twoParamIndDetailsList.put(jsonObject);		        	
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
        
		return twoParamIndDetailsList;
}

public JSONArray getYDTwoParamIndDetailsList(String year,String district,String indName,int countChildInd,List<String> childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray twoParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		if(countChildInd==2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDTWOPARAMDERIVEDINDDETAILSLIST);		
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDTWOPARAMDERIVEDINDDYSFUNCTIODETAILSLIST);
		}else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.YDTWOPARAMDIRECTINDDETAILSLIST);
		}		
			
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,district);
		preparedStatement.setString(3,indName);
		
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	int yearValue = resultSet.getInt(1);
        	String parameter2 = resultSet.getString(2);
        	String indicatorName = resultSet.getString(3);
        	double indValue = resultSet.getDouble(4);
        
        	jsonObject.put("yearValue",yearValue);
        	jsonObject.put("label",parameter2);
        	jsonObject.put("indicatorName",indicatorName);
        	if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("value",Util.roundIndValue(indValue));
        	else
        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
			twoParamIndDetailsList.put(jsonObject);		        	
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
        
		return twoParamIndDetailsList;
}

public JSONArray getYMTwoParamChildIndDataList(String year,String month,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray twoChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();			
		preparedStatement = connection.prepareStatement(ShdrcQueryList.YMTWOPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,month);
		preparedStatement.setString(3,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	int yearValue = resultSet.getInt(1);
	        	String parameter2 = resultSet.getString(2);
	        	String childIndName = resultSet.getString(3);
	        	double indValue = resultSet.getDouble(4);
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("parameter2",parameter2);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				twoChildIndDataList.put(jsonObject);		        	
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
        
		return twoChildIndDataList;
}

public JSONArray getYDTwoParamChildIndDataList(String year,String district,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray twoChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();			
		preparedStatement = connection.prepareStatement(ShdrcQueryList.YDTWOPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,district);
		preparedStatement.setString(3,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	int yearValue = resultSet.getInt(1);
	        	String parameter2 = resultSet.getString(2);
	        	String childIndName = resultSet.getString(3);
	        	double indValue = resultSet.getDouble(4);
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("parameter2",parameter2);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				twoChildIndDataList.put(jsonObject);		        	
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
        
		return twoChildIndDataList;
}

public JSONArray getYITwoParamChildIndDataList(String year,String institution,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray twoChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();			
		preparedStatement = connection.prepareStatement(ShdrcQueryList.YITWOPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,institution);
		preparedStatement.setString(3,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	int yearValue = resultSet.getInt(1);
	        	String parameter2 = resultSet.getString(2);
	        	String childIndName = resultSet.getString(3);
	        	double indValue = resultSet.getDouble(4);
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("parameter2",parameter2);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				twoChildIndDataList.put(jsonObject);		        	
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
        
		return twoChildIndDataList;
}

public JSONArray getOneParamIndDetailsList(String year,String indName,int countChildInd,List<String> childIndList){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray oneParamIndDetailsList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		if(countChildInd==2){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.ONEPARAMDERIVEDINDDETAILSLIST);		
		}else if("Dysfunctional Operation Theatre Indicator".equalsIgnoreCase(indName)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.ONEPARAMDERIVEDINDDYSFUNCTIODETAILSLIST);
		}else{
			preparedStatement = connection.prepareStatement(ShdrcQueryList.ONEPARAMDIRECTINDDETAILSLIST);
		}		
			
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,indName);
		
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
        	int yearValue = resultSet.getInt(1);
        	String indicatorName = resultSet.getString(2);
        	double indValue = resultSet.getDouble(3);
   
        	jsonObject.put("label",yearValue);
        	jsonObject.put("indicatorName",indicatorName);
        	if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("value",Util.roundIndValue(indValue));
        	else
        		jsonObject.put("value",Util.formatTwoDecimal(indValue));
			oneParamIndDetailsList.put(jsonObject);		        	
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
        
		return oneParamIndDetailsList;
}

public JSONArray getOneParamChildIndDataList(String year,String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray oneChildIndDataList=new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();			
		preparedStatement = connection.prepareStatement(ShdrcQueryList.ONEPARAMCHILDINDDATA);
		preparedStatement.setInt(1,yearParam);
		preparedStatement.setString(2,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {
        	JSONObject jsonObject=new JSONObject();
	        	int yearValue = resultSet.getInt(1);
	        	String childIndName = resultSet.getString(2);
	        	double indValue = resultSet.getDouble(3);
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("label",childIndName);
				jsonObject.put("value",indValue);
				oneChildIndDataList.put(jsonObject);		        	
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
        
		return oneChildIndDataList;
}

public JSONArray getProjectedValues(String year,String district,String indName){	
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray projectedValues = new JSONArray();
	int yearParam=Integer.parseInt(year);
	try {
		connection=jdbcTemplate.getDataSource().getConnection();	
		if(year!=null && "All Districts".equalsIgnoreCase(district)){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.PROJECTEDALLDISTRICTVALUES);
			preparedStatement.setString(1,indName);
			//preparedStatement.setInt(2,yearParam);
		}else if(year!=null && district!=null){
			preparedStatement = connection.prepareStatement(ShdrcQueryList.PROJECTEDDISTRICTVALUES);
			preparedStatement.setString(1,indName);
			preparedStatement.setString(2,district);
			preparedStatement.setString(3,indName);
			preparedStatement.setString(4,district);
		}/*else{ 
			preparedStatement = connection.prepareStatement(ShdrcQueryList.PROJECTEDYEARVALUES);
			preparedStatement.setInt(1,yearParam);
			preparedStatement.setString(2,indName);
		}*/
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {	        	
        	JSONObject jsonObject=new JSONObject();
        	
        	
	        	/*int yearValue = resultSet.getInt(1);
	        	String districtName = resultSet.getString(2);
	        	String monthName = resultSet.getString(3);
	        	//String projectedIndName = resultSet.getString(4);
	        	int projectedValue = resultSet.getInt(5);*/
	        
				/*jsonObject.put("yearValue",yearValue);
				jsonObject.put("districtName",districtName);*/
				jsonObject.put("label",resultSet.getString(1));
				jsonObject.put("projectedIndName",indName);
				if(!Util.isPercentageIndicator(indName))
	        		jsonObject.put("value",Util.roundIndValue(resultSet.getDouble(2)));
	        	else
	        		jsonObject.put("value",Util.formatTwoDecimal(resultSet.getDouble(2)));
				jsonObject.put("subCaption",resultSet.getString(3));
				projectedValues.put(jsonObject);	
        	
        	/*else{
        		int yearValue = resultSet.getInt(1);
	        	String monthName = resultSet.getString(2);
	        	String projectedIndName = resultSet.getString(3);
	        	int projectedValue = resultSet.getInt(4);
	        
				jsonObject.put("yearValue",yearValue);
				jsonObject.put("monthName",monthName);
				jsonObject.put("projectedIndName",projectedIndName);
				jsonObject.put("projectedValue",projectedValue);
				projectedValues.put(jsonObject);	
        	}*/
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
        
	return projectedValues;
}
	
public JSONArray getCumulativeProjectedValues(String indName){	
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray cumulativeProjectedValue = new JSONArray();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();	
		preparedStatement = connection.prepareStatement(ShdrcQueryList.DIRECT_PROJECTIONS);
		preparedStatement.setString(1,indName);	
		preparedStatement.setString(2,indName);
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {	        	
        	JSONObject jsonObject=new JSONObject();	
			jsonObject.put("district",resultSet.getString(1));
			if(!Util.isPercentageIndicator(indName))
        		jsonObject.put("projectedValue",Util.roundIndValue(resultSet.getDouble(2)));
        	else
        		jsonObject.put("projectedValue",Util.formatTwoDecimal(resultSet.getDouble(2)));
			cumulativeProjectedValue.put(jsonObject);
			jsonObject.put("subCaption",resultSet.getString(3));
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
        
	return cumulativeProjectedValue;
}
public CommonStringList getIndMaxMonthAndYear(String indName){
	CommonStringList maxMonthAndYear=new CommonStringList();
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	try {
		connection=jdbcTemplate.getDataSource().getConnection();	
		preparedStatement = connection.prepareStatement(ShdrcQueryList.IND_MAX_MONTH_YEAR);
		preparedStatement.setString(1,indName);			
		resultSet = preparedStatement.executeQuery();
		
        while (resultSet.next()) {	        	
        	maxMonthAndYear.setId(resultSet.getInt(1));
        	maxMonthAndYear.setName(resultSet.getString(2));
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
        
	return maxMonthAndYear;
}
}
