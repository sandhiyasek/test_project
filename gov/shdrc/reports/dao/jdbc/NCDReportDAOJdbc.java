package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.INCDReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class NCDReportDAOJdbc implements INCDReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,
			String districtName,String subType,String userName){
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
			Array districtArray=preparedStatement.getConnection().createArrayOf("text", districtName.split(","));
			preparedStatement.setArray(3, districtArray);
			if(("Form 2 Part A: Hypertension and Diabetes Screening".equalsIgnoreCase(reportName)) || ("Form 2 Part B: Screening for Common Cancers".equalsIgnoreCase(reportName))
				|| ("Form 3B Part A : Screening for HTN and Diabetes".equalsIgnoreCase(reportName)) || ("Form 3B PART B: Screening for Common Cancers".equalsIgnoreCase(reportName))
				|| ("Form 3A".equalsIgnoreCase(reportName))){
				preparedStatement.setInt(4, fromYear);
				preparedStatement.setString(5, fromMonth);
				preparedStatement.setInt(6, toYear);
				preparedStatement.setString(7, toMonth);
				preparedStatement.setString(8, userName);
			}
			else{
				Array subTypeArray=preparedStatement.getConnection().createArrayOf("text", subType.split(","));
				preparedStatement.setArray(4, subTypeArray);
				preparedStatement.setInt(5, fromYear);
				preparedStatement.setString(6, fromMonth);
				preparedStatement.setInt(7, toYear);
				preparedStatement.setString(8, toMonth);
				preparedStatement.setString(9, userName);
			}
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
			if(("Form 2 Part A: Hypertension and Diabetes Screening".equalsIgnoreCase(reportName))){				
				childList.put("District");
				childList.put("Name Of the Sub Centre / PHC");
				childList.put("NCD SubType");
				childList.put("Indicator");
				childList.put("Gender");
				childList.put("Value");
			}
			else if(("Form 2 Part B: Screening for Common Cancers".equalsIgnoreCase(reportName)) || ("Form 3A".equalsIgnoreCase(reportName))
					|| ("Form 4".equalsIgnoreCase(reportName)) || ("Form 5A".equalsIgnoreCase(reportName))){
				childList.put("District");
				childList.put("Name Of the Sub Centre / PHC");
				childList.put("NCD SubType");
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Gender");
				childList.put("Value");
			}
			else if(("Form 3B Part A : Screening for HTN and Diabetes".equalsIgnoreCase(reportName)) || ("Form 3B PART B: Screening for Common Cancers".equalsIgnoreCase(reportName))
					|| ("Form 5B Part A : Screening for HTN and Diabetes".equalsIgnoreCase(reportName)) || ("Form 5B PART B: Screening for Common Cancers".equalsIgnoreCase(reportName))){
				childList.put("District");
				childList.put("NCD SubType");
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Gender");
				childList.put("Value");
			}
			else if("Form 6".equalsIgnoreCase(reportName)){
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Gender");
				childList.put("Value");
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("Form 2 Part A: Hypertension and Diabetes Screening".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM2_PARTA;
		}
		else if("Form 2 Part B: Screening for Common Cancers".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM2_PARTB;
		}
		else if("Form 3A".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM3A;
		}
		else if("Form 3B Part A : Screening for HTN and Diabetes".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM3B_PARTA;
		}
		else if("Form 3B PART B: Screening for Common Cancers".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM3B_PARTB;
		}
		else if("Form 4".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM4;
		}
		else if("Form 5A".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM5A;
		}
		else if("Form 5B Part A : Screening for HTN and Diabetes".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM5B_PARTA;
		}
		else if("Form 5B PART B: Screening for Common Cancers".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM5B_PARTB;
		}
		else if("Form 6".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.NCD_REPORTZONE_FORM6;
		}
		
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{	
			if(("Form 2 Part A: Hypertension and Diabetes Screening".equalsIgnoreCase(reportName)) || ("Form 3B Part A : Screening for HTN and Diabetes".equalsIgnoreCase(reportName))
					|| ("Form 3B PART B: Screening for Common Cancers".equalsIgnoreCase(reportName)) || ("Form 5B Part A : Screening for HTN and Diabetes".equalsIgnoreCase(reportName))
					|| ("Form 5B PART B: Screening for Common Cancers".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String firstCol=resultSet.getString(1);		
					String secondCol=resultSet.getString(2);		
					String thirdCol=resultSet.getString(3);	
					String fourthCol=resultSet.getString(4);						
					String fifthCol=resultSet.getString(5);
					BigDecimal val=truncateDecimal(resultSet.getDouble(6),2);
					
					childList.put(firstCol);				
					childList.put(secondCol);				
					childList.put(thirdCol);	
					childList.put(fourthCol);	
					childList.put(fifthCol);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("Form 2 Part B: Screening for Common Cancers".equalsIgnoreCase(reportName)) || ("Form 3A".equalsIgnoreCase(reportName))
					|| ("Form 4".equalsIgnoreCase(reportName)) || ("Form 5A".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String district=resultSet.getString(1);		
					String institution=resultSet.getString(2);		
					String ncdSubType=resultSet.getString(3);	
					String indicator=resultSet.getString(4);		
					String generalName=resultSet.getString(5);
					String gender=resultSet.getString(6);
					BigDecimal val=truncateDecimal(resultSet.getDouble(7),2);
					
					childList.put(district);				
					childList.put(institution);				
					childList.put(ncdSubType);	
					childList.put(indicator);	
					childList.put(generalName);	
					if(gender!=null){
						childList.put(gender);	
					}
					else{
						childList.put("");
					}
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if("Form 6".equalsIgnoreCase(reportName)){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String indicator=resultSet.getString(1);		
					String generalName=resultSet.getString(2);
					String gender=resultSet.getString(3);
					BigDecimal val=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(indicator);	
					childList.put(generalName);	
					if(gender!=null){
						childList.put(gender);	
					}
					else{
						childList.put("");
					}
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
	
	public List<CommonStringList> getDistrictList(String userName){
		List<CommonStringList> districtsList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			districtsList=new ArrayList<CommonStringList>();
			String districtQuery=null;			
			districtQuery=ShdrcQueryList.DISTRICT_NAME;
						
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(districtQuery);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	if(!ShdrcConstants.NAVALUE.equals(resultSet.getString(2))){
		        	CommonStringList CommonStringList = new CommonStringList();
		        	CommonStringList.setId(resultSet.getInt(1));
		        	CommonStringList.setName(resultSet.getString(2));
		        	districtsList.add(CommonStringList);
	        	}	
	        }
	        if(districtsList.size()==0)
	        	districtsList=null;
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
			return districtsList;
	}
	
	public StringBuilder getAllDistrictList(String userName){
		StringBuilder strBuilderList=new StringBuilder();
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.DISTRICT_NAME);
			preparedStatement.setString(1,userName);
			preparedStatement.setString(2,userName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,userName);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {
	        	strBuilderList.append(resultSet.getString(2));				
				strBuilderList.append(",");	        	
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
			return strBuilderList;
	}
	
	public List<CommonStringList> getNcdSubTypeList(){
		List<CommonStringList> ncdSubTypeList=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			ncdSubTypeList=new ArrayList<CommonStringList>();
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcReportQueryList.NCD_SUBTYPE);
			resultSet = preparedStatement.executeQuery();
	        while (resultSet.next()) {	        	
		        	CommonStringList CommonStringList = new CommonStringList();
		        	CommonStringList.setName(resultSet.getString(1));
		        	ncdSubTypeList.add(CommonStringList);	        		
	        }
	        if(ncdSubTypeList.size()==0)
	        	ncdSubTypeList=null;
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
			return ncdSubTypeList;
	}
}
