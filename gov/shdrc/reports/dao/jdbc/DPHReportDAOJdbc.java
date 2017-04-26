/**
 * 
 */
package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.IDPHReportDAO;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Upendra G
 *
 * 
 */
@Service
public class DPHReportDAOJdbc implements IDPHReportDAO {
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
				sr = ShdrcReportQueryList.DPH_ANALYSISZONE;				
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
					String monthName=resultSet.getString(2);
					int value=resultSet.getInt(3);
					jsonObject.put("label",monthName);
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
				childList.put("Value");
				parentList.put(childList);
				
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DPH_REPORTZONE);
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
					BigDecimal val=truncateDecimal(resultSet.getDouble(2),2);
					
					childList.put(indicator);	
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
		
		public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			List resultList=new ArrayList();
			
			try {
			connection=jdbcTemplate.getDataSource().getConnection();				
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dph_year(?,?,?)");
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
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dph_ind_name(?,?)");	
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
			preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dph_ind_cat(?)");
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
					 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dph_main(?,?,?,?)");
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
				childList.put("Institution Type");
				childList.put("Institution");	
				childList.put("District");							
				childList.put("Value");	
				childList.put("Threshold");
				parentList.put(childList);
				preparedStatement = connection.prepareStatement("select * from shdrc_dwh.dash_dph_inst(?,?,?,?,?,?,?)");
			
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
			catch(SQLException e) {
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
