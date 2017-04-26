package gov.shdrc.reports.dao.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.IDFSReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;






import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DFSReportDAOJdbc implements IDFSReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfs_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfs_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfs_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfs_main(?,?,?,?)");
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
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneData=new JSONArray();
		try {		
			sr = ShdrcReportQueryList.DFS_ANALYSISZONE;				
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
				String indicator=resultSet.getString(1);
				int value=resultSet.getInt(2);
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
	
	public JSONObject getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray analysisZoneFirstSeriesData=new JSONArray();
		JSONArray analysisZoneSecondSeriesData=new JSONArray();
		JSONArray analysisZoneThirdSeriesData=new JSONArray();
		JSONArray analysisZoneFourthSeriesData=new JSONArray();
		JSONObject finalList=new JSONObject();	
		try {		
			sr = ShdrcReportQueryList.DFS_ANALYSISZONE_MULTISERIES;				
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
				JSONObject jsonObject4=new JSONObject();			
				String district=resultSet.getString(1);
				int samplesAnalysed=resultSet.getInt(2);
				jsonObject1.put("label",district);
				jsonObject1.put("value",samplesAnalysed);
				jsonObject1.put("seriesName","Number of Act Samples analysed");
				analysisZoneFirstSeriesData.put(jsonObject1);
				
				int samplesMisbranded=resultSet.getInt(3);
				jsonObject2.put("label",district);
				jsonObject2.put("value",samplesMisbranded);	
				jsonObject2.put("seriesName","Number of Act Samples found substandard/Misbranded etc");
				analysisZoneSecondSeriesData.put(jsonObject2);
				
				int samplesUnsafe=resultSet.getInt(4);
				jsonObject3.put("label",district);
				jsonObject3.put("value",samplesUnsafe);
				jsonObject3.put("seriesName","Number of Act Samples found unsafe");
				analysisZoneThirdSeriesData.put(jsonObject3);
				
				int samplesLifted=resultSet.getInt(4);
				jsonObject4.put("label",district);
				jsonObject4.put("value",samplesLifted);
				jsonObject4.put("seriesName","Number of Act Samples lifted ");
				analysisZoneFourthSeriesData.put(jsonObject4);
				
				finalList.put("analysisZoneFirstSeriesData", analysisZoneFirstSeriesData);
				finalList.put("analysisZoneSecondSeriesData", analysisZoneSecondSeriesData);
				finalList.put("analysisZoneThirdSeriesData", analysisZoneThirdSeriesData);
				finalList.put("analysisZoneFourthSeriesData", analysisZoneFourthSeriesData);
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
			if(("DO Report Category Wise".equalsIgnoreCase(reportName)) || ("Licenses-FBO Monthly Report of the Tamil Nadu".equalsIgnoreCase(reportName))
					|| ("FSS ACT Samples Monthly Report".equalsIgnoreCase(reportName)) || ("Monthly Progress Report on issue of registration certificate by the FSOs".equalsIgnoreCase(reportName))
					|| ("Monthly progress report on the issue of license  by the DOs".equalsIgnoreCase(reportName)) || ("Registration-FBO Monthly Report of the Tamil Nadu".equalsIgnoreCase(reportName))
					|| ("Surveillance Samples (Food Stuff Wise)-Monthly Report".equalsIgnoreCase(reportName))){
				childList=new JSONArray();	
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Value");
			}
			else if(("FSS-ACT Samples Line Listing(UnSafe)".equalsIgnoreCase(reportName)) || ("FSS-ACT Samples Line Listing(Safe)".equalsIgnoreCase(reportName))){
				childList=new JSONArray();
				childList.put("District_Name");
				childList.put("Name of the Block/Municipality/Corporation");
				childList.put("Area Code");
				childList.put("Sample No/Year");
				childList.put("Name of the Food Safety Officer");
				childList.put("Nature of the sample with Brand Name");
				childList.put("Category of Food Stuff");
				childList.put("Category of Traders");
				childList.put("Date of Sampling");
				childList.put("Date of receipt in the lab");
				childList.put("Format VII A Report No and Date");
				if(("FSS-ACT Samples Line Listing(UnSafe)".equalsIgnoreCase(reportName))){
					childList.put("P1 Others");
				}
				childList.put("Time Fiscal Year");
				childList.put("Time Month Name");
			}
			else if(("Surveillance Samples Line Listing".equalsIgnoreCase(reportName))){ 				
				childList=new JSONArray();
				childList.put("District_Name");
				childList.put("Name of the Block/Municipality/Corporation");
				childList.put("Area Code");
				childList.put("Sample No/Year");
				childList.put("Name of the Food Safety Officer");
				childList.put("Nature of the sample with Brand Name");
				childList.put("Category of Food Stuff");
				childList.put("License No / Registration No");
				childList.put("Date of Sampling");
				childList.put("Date of receipt in the lab");
				childList.put("Format VII A Report No and Date");
				childList.put("Final Conclusion");
				childList.put("Action taken/Remarks");
				childList.put("Status of Sample conform or Non Conform");
				childList.put("Tests Performed (Yes/No)-Microbilogical");
				childList.put("Tests Performed (Yes/No)-Pesticides/Heavy Metals");
				childList.put("Tests Performed (Yes/No)-Chemical");
				childList.put("Tests Performed (Yes/No)-Physical");				
				childList.put("Time Fiscal Year");
				childList.put("Time Month Name");
				childList.put("Name of the manufacturing company");
			}
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("DO Report Category Wise".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE;
		}
		else if("Licenses-FBO Monthly Report of the Tamil Nadu".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_LICENSES;
		}
		else if(("FSS-ACT Samples Line Listing(UnSafe)".equalsIgnoreCase(reportName))){
			query=ShdrcReportQueryList.DFS_REPORTZONE_FSS_ACT_SAMPLES_LINELISTING_UNSAFE;
		}
		else if("Surveillance Samples Line Listing".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_SURVEILLANCE_SAMPLES_LINELISTING;
		}
		else if("FSS ACT Samples Monthly Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_FSS_ACT_SAMPLES_MONTHLY_REPORT;
		}
		else if("Monthly Progress Report on issue of registration certificate by the FSOs".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_ISSUE_OF_REGISTRATION_CERTIFICATE;
		}
		else if("Monthly progress report on the issue of license  by the DOs".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_ISSUE_OF_LICENSE;
		}
		else if("Registration-FBO Monthly Report of the Tamil Nadu".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_FBO_MONTHLY_REPORT;
		}
		else if("Surveillance Samples (Food Stuff Wise)-Monthly Report".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_SURVEILLANCE_SAMPLES_MONTHLY_REPORT;
		}
		else if("FSS-ACT Samples Line Listing(Safe)".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFS_REPORTZONE_FSS_ACT_SAMPLES_LINELISTING_SAFE;
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{	
			if(("DO Report Category Wise".equalsIgnoreCase(reportName)) || ("Licenses-FBO Monthly Report of the Tamil Nadu".equalsIgnoreCase(reportName))
					|| ("FSS ACT Samples Monthly Report".equalsIgnoreCase(reportName)) || ("Monthly Progress Report on issue of registration certificate by the FSOs".equalsIgnoreCase(reportName))
					|| ("Monthly progress report on the issue of license  by the DOs".equalsIgnoreCase(reportName)) || ("Registration-FBO Monthly Report of the Tamil Nadu".equalsIgnoreCase(reportName))
					|| ("Surveillance Samples (Food Stuff Wise)-Monthly Report".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();					
					String generalName=resultSet.getString(1);	
					String indicator=resultSet.getString(2);
					BigDecimal val=truncateDecimal(resultSet.getDouble(3),2);
					
					childList.put(indicator);				
					childList.put(generalName);	
					childList.put(val);
					parentList.put(childList);
				}
			}
			else if(("FSS-ACT Samples Line Listing(UnSafe)".equalsIgnoreCase(reportName)) || ("Surveillance Samples Line Listing".equalsIgnoreCase(reportName))
					 || ("FSS-ACT Samples Line Listing(Safe)".equalsIgnoreCase(reportName))){
				String firstCol = null;
				String secondCol = null;
				String thirdCol = null;
				String fourthCol = null;
				String fifthCol = null;
				String sixthCol = null;
				String seventhCol = null;
				String eighthCol = null;
				String ninthCol = null;
				String tenthCol = null;
				String eleventhCol = null;
				String twelfthCol = null;
				String thirteenthCol = null;
				String fourteenthCol = null;
				String fifteenthCol = null;
				String sixteenthCol = null;
				String seventeenthCol = null;
				String eighteenthCol = null;
				String nineteenthCol = null;
				String twentiethCol = null;
				String twentyfirstCol = null;
				
				while(resultSet.next())
				{ 				
					childList=new JSONArray();					
					firstCol=resultSet.getString(1);	
					secondCol=resultSet.getString(2);
					thirdCol=resultSet.getString(3);	
					fourthCol=resultSet.getString(4);
					fifthCol=resultSet.getString(5);	
					sixthCol=resultSet.getString(6);
					seventhCol=resultSet.getString(7);	
					eighthCol=resultSet.getString(8);
					ninthCol=resultSet.getString(9);	
					tenthCol=resultSet.getString(10);
					eleventhCol=resultSet.getString(11);	
					twelfthCol=resultSet.getString(12);
					thirteenthCol=resultSet.getString(13);
					if(("FSS-ACT Samples Line Listing(UnSafe)".equalsIgnoreCase(reportName)) || ("Surveillance Samples Line Listing".equalsIgnoreCase(reportName))){
						fourteenthCol=resultSet.getString(14);
					}
					if("Surveillance Samples Line Listing".equalsIgnoreCase(reportName)){
						fifteenthCol=resultSet.getString(15);
						sixteenthCol=resultSet.getString(16);	
						seventeenthCol=resultSet.getString(17);
						eighteenthCol=resultSet.getString(18);	
						nineteenthCol=resultSet.getString(19);
						twentiethCol=resultSet.getString(20);	
						twentyfirstCol=resultSet.getString(21);
					}
					
					childList.put(firstCol);				
					childList.put(secondCol);	
					childList.put(thirdCol);
					childList.put(fourthCol);				
					childList.put(fifthCol);	
					childList.put(sixthCol);
					childList.put(seventhCol);				
					childList.put(eighthCol);	
					childList.put(ninthCol);
					childList.put(tenthCol);				
					childList.put(eleventhCol);	
					childList.put(twelfthCol);
					childList.put(thirteenthCol);	
					if(("FSS-ACT Samples Line Listing(UnSafe)".equalsIgnoreCase(reportName)) || ("Surveillance Samples Line Listing".equalsIgnoreCase(reportName))){
						childList.put(fourteenthCol);	
					}
					if("Surveillance Samples Line Listing".equalsIgnoreCase(reportName)){
						childList.put(fifteenthCol);	
						childList.put(sixteenthCol);
						childList.put(seventeenthCol);				
						childList.put(eighteenthCol);	
						childList.put(nineteenthCol);
						childList.put(twentiethCol);				
						childList.put(twentyfirstCol);
					}
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
}
