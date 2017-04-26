package gov.shdrc.reports.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.ISDGSDao;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.Util;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SDGSDaoJdbc implements ISDGSDao{
	private static final Logger logger = Logger.getLogger(HSDashboardDAOJdbc.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public JSONArray getNewColumnData(String month,String ind){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_BYDIST;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_BYDIST;
			}

			/*connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SHDRC", "postgres", "postgres");*/
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, month);
			preparedStatement.setString(2, month);
/*			preparedStatement=connection.prepareStatement("select * from \"shdrc_pa\".mmr_diallm('Chennai', 'All Institutions','Jan') order by value desc limit 10");*/
			/*preparedStatement=connection.prepareStatement("select * from \"shdrc_pa\".mmr_idm('Total Number of Maternal Death', 'All Districts', 'Jan') order by value desc limit 15");*/
			/*preparedStatement=connection.prepareStatement("select * from \"shdrc_pa\".mmr_dm('Chennai','Apr') limit 15");*/
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				/*String indicator=resultSet.getString(1);
				String district=resultSet.getString(2);*/
				String district=resultSet.getString(1);
				int death=resultSet.getInt(2);
				int live=resultSet.getInt(3);
				BigDecimal ival=truncateDecimal(resultSet.getDouble(4),2);
				jsonObject.put("label",district);
				jsonObject.put("value",death);
				jsonObject.put("live",live);
				jsonObject.put("rate",ival);

							
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
	public JSONArray indzone(String ind1, Integer year,String month){
		/*String sr=null;*/
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
        
		try {
/*			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_BYDIST;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_BYDIST;
			}*/
			childList=new JSONArray();
			childList.put("Category");
			childList.put("District");
			childList.put("Institution");
			childList.put("Year");
			childList.put("Month");
			childList.put("Value");
			parentList.put(childList);
			/*connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SHDRC", "postgres", "postgres");*/
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
				/*int val=resultSet.getInt(7);*/
				
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

	public JSONArray heatMapData(String month,String previousMonth,String previousMonth1,String ind){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray heatMapChildList=new JSONArray();
		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_BYDISTHM;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_BYDISTHM;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, month);
			preparedStatement.setString(2, previousMonth);
			preparedStatement.setString(3, month);
			preparedStatement.setString(4, previousMonth1);
			preparedStatement.setString(5, month);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				String district=resultSet.getString(2);
				int ival=resultSet.getInt(4);
				jsonObject.put("rowid",indicator);
				jsonObject.put("columnid",district);
				jsonObject.put("value",ival);
							
				heatMapChildList.put(jsonObject);

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
		return heatMapChildList;
	}
	public JSONArray ncaDel(String month,String ind){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ncaDel=new JSONArray();
		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_FCHINDNC;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_FCHINDNC;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, month);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, month);
			preparedStatement.setString(4, month);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(3);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				ncaDel.put(jsonObject);

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
		return ncaDel;
	}
	public JSONArray del1(String month,String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray del1=new JSONArray();
		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_DEL1;
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setString(1, month);
				preparedStatement.setString(2, month);
				preparedStatement.setString(3, month);
				preparedStatement.setString(4, month);
				preparedStatement.setString(5, month);
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_DEL1;
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setString(1, month);
				preparedStatement.setString(2, month);

			}
			
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				del1.put(jsonObject);

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
		return del1;
	}
	public JSONArray del2(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray del2=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.MMR_DEL2);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				del2.put(jsonObject);

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
		return del2;
	}
	public JSONArray del3(){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray del3=new JSONArray();
		try {
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(ShdrcQueryList.MMR_DEL3);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				del3.put(jsonObject);

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
		return del3;
	}
	
	public JSONArray anmst(String month, String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray anm=new JSONArray();
		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_CHINDANM;
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setString(1, month);
				preparedStatement.setString(2, month);
				preparedStatement.setString(3, month);
				preparedStatement.setString(4, month);
				preparedStatement.setString(5, month);
				preparedStatement.setString(6, month);
				preparedStatement.setString(7, month);
				preparedStatement.setString(8, month);
				preparedStatement.setString(9, month);
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_CHINDANM;
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(sr);
				preparedStatement.setString(1, month);
				preparedStatement.setString(2, month);
				preparedStatement.setString(3, month);
				preparedStatement.setString(4, month);

			}

			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				anm.put(jsonObject);

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
		return anm;
	}

		public JSONArray drisk(String month,String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray drisk=new JSONArray();
		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_BYRISK;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_BYRISK;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, month);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, month);			
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(3);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				drisk.put(jsonObject);

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
		return drisk;
	}
	public JSONArray getInstDataByDist(String districtName,String month, String ind){
		Connection connection = null;
		String sr = null;

		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray instList=new JSONArray();

		try {
			
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_BYINST;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				 sr = ShdrcQueryList.IMR_BYINST;
			}
		
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				//String indicator=resultSet.getString(1);
				String institution=resultSet.getString(2);
				int ival=resultSet.getInt(5);
				//jsonObject.put("indicator",indicator);
				jsonObject.put("label",institution);
				jsonObject.put("value",ival);	
				instList.put(jsonObject);
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
		return instList;
	}
	public JSONArray getHMDataByDist(String districtName,String month,String previousMonth,String previousMonth1,String ind){
		Connection connection = null;
		String sr= null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray instHMList=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_BYINSTHM;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				 sr = ShdrcQueryList.IMR_BYINSTHM;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, districtName);
			preparedStatement.setString(4, previousMonth);
			preparedStatement.setString(5, districtName);
			preparedStatement.setString(6, month);
			preparedStatement.setString(7, districtName);
			preparedStatement.setString(8, previousMonth1);
			preparedStatement.setString(9, districtName);
			preparedStatement.setString(10, month);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				String institution=resultSet.getString(2);
				int ival=resultSet.getInt(4);
				//jsonObject.put("indicator",indicator);
				jsonObject.put("rowid",indicator);
				jsonObject.put("columnid",institution);
				jsonObject.put("value",ival);	
				instHMList.put(jsonObject);
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
		return instHMList;
	}
	public JSONArray getIndDataByDist(String districtName,String month, String ind){
		Connection connection = null;
		String sr=null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray indList=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_CHINDAN;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				 sr = ShdrcQueryList.IMR_CHINDAN;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				indList.put(jsonObject);

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
		return indList;
	}
	public JSONArray getDelDataByDist(String districtName,String month, String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray delList=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_CHINDDL;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				 sr = ShdrcQueryList.IMR_CHINDDL;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				delList.put(jsonObject);

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
		return delList;
	}
	public JSONArray getDelDataByDist1(String districtName,String month,String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray delList1=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_CHINDDL1;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				 sr = ShdrcQueryList.IMR_CHINDDL1;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				delList1.put(jsonObject);

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
		return delList1;
	}
	public JSONArray getDelDataByDist2(String districtName,String month,String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray delList2=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_CHINDDL2;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				 sr = ShdrcQueryList.IMR_CHINDDL2;
			}		
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				delList2.put(jsonObject);

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
		return delList2;
	}
	public JSONArray getLdDataByDist(String districtName,String month ,String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray LbdList=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_CHINDLD;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				 sr = ShdrcQueryList.IMR_CHINDLD;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				LbdList.put(jsonObject);

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
		return LbdList;
	}
	public JSONArray getndDataByDist(String districtName,String month,String ind){
		Connection connection = null;
		String sr =null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray ncList=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_CHINDNC;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_CHINDNC;
			}
		
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			preparedStatement.setString(2, month);
			preparedStatement.setString(3, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				int ival=resultSet.getInt(2);
				jsonObject.put("label",indicator);
				jsonObject.put("value",ival);
							
				ncList.put(jsonObject);

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
		return ncList;
	}
	public JSONArray getndAxisLineGTData(String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray axisLineGT=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_AXISLINEGT;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_AXISLINEGT;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				String month=resultSet.getString(2);
				int ival=resultSet.getInt(4);
				jsonObject.put("indicator",indicator);
				jsonObject.put("label",month);
				jsonObject.put("value",ival);
							
				axisLineGT.put(jsonObject);

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
		return axisLineGT;
	}
	public JSONArray getndAxisLineGTData1(String districtName,String month, String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray axisLineGT1=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_AXISLINEGT1;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_AXISLINEGT1;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			preparedStatement.setString(1, districtName);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				String monthName=resultSet.getString(2);
				int ival=resultSet.getInt(4);
				jsonObject.put("indicator",indicator);
				jsonObject.put("label",monthName);
				jsonObject.put("value",ival);
							
				axisLineGT1.put(jsonObject);

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
		return axisLineGT1;
	}
	public JSONArray getndAxisLineLTData(String ind){
		Connection connection = null;
		String sr = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray axisLineLT=new JSONArray();

		try {
			if ("maternal mortality rate".equalsIgnoreCase(ind)){
				sr = ShdrcQueryList.MMR_AXISLINELT;
			}
			else if ("infant mortality rate".equalsIgnoreCase(ind)){
				sr=ShdrcQueryList.IMR_AXISLINELT;
			}
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(sr);
			resultSet=preparedStatement.executeQuery();	
		    		
			while(resultSet.next())
			{ 				
				JSONObject jsonObject=new JSONObject();
				String indicator=resultSet.getString(1);
				String month=resultSet.getString(2);
				int ival=resultSet.getInt(4);
				jsonObject.put("indicator",indicator);
				jsonObject.put("label",month);
				jsonObject.put("value",ival);
							
				axisLineLT.put(jsonObject);

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
		return axisLineLT;
	}
public JSONArray getndAxisLineLTData1(String districtName,String month,String ind){
	Connection connection = null;
	String sr =null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray axisLineLT1=new JSONArray();

	try {
		if ("maternal mortality rate".equalsIgnoreCase(ind)){
			sr = ShdrcQueryList.MMR_AXISLINELT1;
		}
		else if ("infant mortality rate".equalsIgnoreCase(ind)){
			sr=ShdrcQueryList.IMR_AXISLINELT1;
		}
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(sr);
		preparedStatement.setString(1, districtName);
		resultSet=preparedStatement.executeQuery();	
	    		
		while(resultSet.next())
		{ 				
			JSONObject jsonObject=new JSONObject();
			String indicator=resultSet.getString(1);
			String monthName=resultSet.getString(2);
			int ival=resultSet.getInt(4);
			jsonObject.put("indicator",indicator);
			jsonObject.put("label",monthName);
			jsonObject.put("value",ival);
						
			axisLineLT1.put(jsonObject);

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
	return axisLineLT1;
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
public JSONArray getChartColumnHeader(String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray chartcolumnHeader=new JSONArray();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcQueryList.CHART_HEADER);
		preparedStatement.setString(1, indName);
		resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			//JSONObject jsonObject=new JSONObject();
			//jsonObject.put("chartId",resultSet.getString(1));
			//jsonObject.put("chartHeader"+resultSet.getString(1),resultSet.getString(2));
			chartcolumnHeader.put(resultSet.getString(2));

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
	return chartcolumnHeader;
}
public JSONArray getChartColumnRange(String indName){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray chartrange=new JSONArray();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcQueryList.CHART_RANGE);
		preparedStatement.setString(1, indName);
		resultSet=preparedStatement.executeQuery();	
		while(resultSet.next())
		{ 				
			//JSONObject jsonObject=new JSONObject();
			//jsonObject.put("chartId",resultSet.getString(1));
			//jsonObject.put("chartHeader"+resultSet.getString(1),resultSet.getString(2));
			chartrange.put(resultSet.getInt(1));
			chartrange.put(resultSet.getInt(2));
			chartrange.put(resultSet.getInt(3));
			chartrange.put(resultSet.getInt(4));
			chartrange.put(resultSet.getInt(5));
			chartrange.put(resultSet.getInt(6));

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
	return chartrange;
}
public JSONArray getDistrictDataNcd(String flag, String param) {
	Connection connection = null;
	String sr = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	JSONArray districtDataNcd = new JSONArray();
	try {
		connection = jdbcTemplate.getDataSource().getConnection();
		if ("category".equalsIgnoreCase(flag)){
			sr = ShdrcQueryList.TM_NCDCLIKD;
			preparedStatement = connection
					.prepareStatement(sr);
			preparedStatement.setString(1, param);
		}
		else{
			sr=ShdrcQueryList.TM_DISTRICTDATANCD;
			preparedStatement = connection
					.prepareStatement(sr);
		}

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			String dist = resultSet.getString(1);
			int value = resultSet.getInt(2);
			jsonObject.put("name", dist);
			jsonObject.put("value", value);
			districtDataNcd.put(jsonObject);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception json) {

	} finally {
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
	return districtDataNcd;
}

/*public JSONArray getDistrictDataCd(String flag, String param) {
	Connection connection = null;
	String sr=null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	JSONArray districtDataCd = new JSONArray();
	try {		
		
		connection = jdbcTemplate.getDataSource().getConnection();
		if ("category".equalsIgnoreCase(flag)){
			sr = ShdrcQueryList.TM_CDCLIKD;
			preparedStatement = connection
					.prepareStatement(sr);
			preparedStatement.setString(1, param);
		}
		else{
			sr=ShdrcQueryList.TM_DISTRICTDATACD;
			preparedStatement = connection
					.prepareStatement(sr);
		}
		
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			String dist = resultSet.getString(1);
			int value = resultSet.getInt(2);
			jsonObject.put("label", dist);
			jsonObject.put("value", value);
			jsonObject.put("link", "JavaScript:showInstDataCd('"+ dist +"');");
			districtDataCd.put(jsonObject);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception json) {

	} finally {
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
	return districtDataCd;
}*/

public JSONArray getInstitutionData(String distName, String flag, String param){
	Connection connection = null;
	String sr = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	JSONArray childList = new JSONArray();
	try {
		connection = jdbcTemplate.getDataSource().getConnection();
		if ("ByDisease".equalsIgnoreCase(flag)){
			sr = ShdrcQueryList.TM_JSONI;
			preparedStatement = connection
			.prepareStatement(sr);
			preparedStatement.setString(1, distName);				
			preparedStatement.setString(2, param);	
		}
		else{
			sr=ShdrcQueryList.TM_JSON;
			preparedStatement = connection
			.prepareStatement(sr);
			preparedStatement.setString(1, distName);
		}
		
		
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			String instName = resultSet.getString(1);
			jsonObject.put("id", instName);
			childList.put(jsonObject);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception json) {

	} finally {
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

public JSONArray getGrandChildDataNcd(String distName, String flag, String param){
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	JSONArray childListNcd = new JSONArray();

	try {
		
		connection = jdbcTemplate.getDataSource().getConnection();
		if ("ByDisease".equalsIgnoreCase(flag)){
			preparedStatement = connection
			.prepareStatement(ShdrcQueryList.TM_NCDCLIKI);
			preparedStatement.setString(1, param);				
			preparedStatement.setString(2, distName);	
		}
		else{
			preparedStatement = connection
					.prepareStatement(ShdrcQueryList.TM_JSONGRANDCHILDNCD);
			preparedStatement.setString(1, distName);
		}
		
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			String insName = resultSet.getString(1);
			String genaralName = resultSet.getString(2);
			int value = resultSet.getInt(3);
			jsonObject.put("insName", insName);
			jsonObject.put("name", genaralName);
			jsonObject.put("value", value);
			childListNcd.put(jsonObject);
		}

	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception json) {

	} finally {
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
	return childListNcd;
}

/*public JSONArray getGrandChildDataCd(String distName, String flag, String param){
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	JSONArray childListCd = new JSONArray();

	try {
		
		connection = jdbcTemplate.getDataSource().getConnection();
		if ("ByDisease".equalsIgnoreCase(flag)){
			preparedStatement = connection
			.prepareStatement(ShdrcQueryList.TM_CDCLIKI);
			preparedStatement.setString(1, param);				
			preparedStatement.setString(2, distName);	
		}
		else{
			preparedStatement = connection
					.prepareStatement(ShdrcQueryList.TM_JSONGRANDCHILDCD);
			preparedStatement.setString(1, distName);
		}

		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			String insName = resultSet.getString(1);
			String genaralName = resultSet.getString(2);
			int value = resultSet.getInt(3);
			jsonObject.put("label", insName);
			jsonObject.put("name", genaralName);
			jsonObject.put("value", value);
			childListCd.put(jsonObject);
		}

	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception json) {

	} finally {
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
	return childListCd;
}*/

public JSONArray getcd() {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	JSONArray gcd = new JSONArray();
	try {
		connection = jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection
				.prepareStatement(ShdrcQueryList.TM_CD);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			String cd = resultSet.getString(1);
			int value = resultSet.getInt(2);
			jsonObject.put("label", cd);
			jsonObject.put("value", value);
			gcd.put(jsonObject);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception json) {

	} finally {
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
	return gcd;
}

public JSONArray getncd() {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	JSONArray gncd = new JSONArray();
	try {
		connection = jdbcTemplate.getDataSource().getConnection();
		preparedStatement = connection
				.prepareStatement(ShdrcQueryList.TM_NCD);
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			String ncd = resultSet.getString(1);
			int value = resultSet.getInt(2);
			jsonObject.put("label", ncd);
			jsonObject.put("value", value);
			gncd.put(jsonObject);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception json) {

	} finally {
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
	return gncd;
}

//sdgs Dashboard DAO

public JSONArray getDistMaternalDeathData(int year,String month){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray distMaternalDeathData=new JSONArray();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcQueryList.DIST_MATERNALDEATH);
	/*	preparedStatement.setInt(1, year);
		preparedStatement.setString(2, month);*/
		resultSet=preparedStatement.executeQuery();	
	    		
		while(resultSet.next())
		{ 				
			JSONObject jsonObject=new JSONObject();
			String indicator=resultSet.getString(1);
			String district=resultSet.getString(2);
			int yearValue=resultSet.getInt(3);
			String monthValue=resultSet.getString(4);
			double value=resultSet.getDouble(5);
			String projectedValue=resultSet.getString(6);
			jsonObject.put("indicator",indicator);
			jsonObject.put("district",district);
			jsonObject.put("yearValue",yearValue);
			jsonObject.put("monthValue",monthValue);
			jsonObject.put("value",value);
			jsonObject.put("projectedValue",projectedValue);
			distMaternalDeathData.put(jsonObject);
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
	return distMaternalDeathData;
}

public JSONArray getMaternalDeathMonVal(int year,String month){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray maternalDeathMonVal=new JSONArray();
	List<Integer> monthList=new ArrayList<Integer>();
	/*String districtName=null;*/
	String indicatorName=null;
	JSONObject jsonObject=null;
	try {			
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcQueryList.MATERNALDEATH_MONTH);
		/*preparedStatement.setInt(1, year);
		preparedStatement.setString(2, month);*/
		resultSet=preparedStatement.executeQuery();	
				    		
		while(resultSet.next())
		{ 				
			jsonObject=new JSONObject();
			String indicator=resultSet.getString(1);
			String district=resultSet.getString(2);
			String monthVal=resultSet.getString(3);
			int yearVal=resultSet.getInt(4);				
			int indValue=resultSet.getInt(5);		
			
			/*if(resultSet.first()){
				districtName=district;
			}*/
			
			/*if(district.equalsIgnoreCase(districtName)){*/
			if(indicator.equalsIgnoreCase(indicatorName)){
				monthList.add(indValue);
			}
			else{
				/*jsonObject.put("districtName",districtName);*/
				jsonObject.put("districtName",indicatorName);
				jsonObject.put("monthList",monthList);
				maternalDeathMonVal.put(jsonObject);
				/*districtName=district;*/
				indicatorName=indicator;
				monthList.clear();
				monthList.add(indValue);
			}
		}
		jsonObject.put("districtName",indicatorName);
		jsonObject.put("monthList",monthList);
		maternalDeathMonVal.put(jsonObject);
		
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
	return maternalDeathMonVal;
}

public JSONArray getMaternalDeathThreshold(){
	Connection connection = null;
	PreparedStatement preparedStatement =null;
	ResultSet resultSet =null;
	JSONArray maternalDeathThreshold=new JSONArray();
	try {
		connection=jdbcTemplate.getDataSource().getConnection();
		preparedStatement=connection.prepareStatement(ShdrcQueryList.SDGS_THRESHOLD);
		resultSet=preparedStatement.executeQuery();	
	    		
		while(resultSet.next())
		{ 				
			JSONObject jsonObject=new JSONObject();
			int greenValue=resultSet.getInt(1);
			int amberValue=resultSet.getInt(2);			
			int redValue=resultSet.getInt(3);				
			jsonObject.put("greenValue",greenValue);
			jsonObject.put("amberValue",amberValue);
			jsonObject.put("redValue",redValue);
			
			maternalDeathThreshold.put(jsonObject);
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
	return maternalDeathThreshold;
}

	public JSONArray getDistrictDataCd(String firstCDName,String month,Integer year) {
		Connection connection = null;
		String sr=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		JSONArray districtDataCd = new JSONArray();
		try {		
			
			connection = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.CD_DISTRICTDATA);	
			preparedStatement.setString(1, firstCDName);
			preparedStatement.setInt(2, year);
			preparedStatement.setString(3, month);
			/*preparedStatement.setString(4, Integer.toString(Util.monthsShortNamesMap.get(month)));*/
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				String dist = resultSet.getString(1);
				int value = resultSet.getInt(2);
				jsonObject.put("label", dist);
				jsonObject.put("value", value);
				districtDataCd.put(jsonObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception json) {
	
		} finally {
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
		return districtDataCd;
	}
	
	public JSONArray getInstDataCd(String firstCDDistName,String firstCDName,String month,Integer year){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		JSONArray childListCd = new JSONArray();
		try {			
			connection = jdbcTemplate.getDataSource().getConnection();			
			preparedStatement = connection.prepareStatement(ShdrcQueryList.CD_INSTITUTIONDATA);
			preparedStatement.setString(1, firstCDDistName);
			preparedStatement.setString(2, firstCDName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				String insName = resultSet.getString(1);
				int value = resultSet.getInt(2);
				jsonObject.put("label", insName);
				jsonObject.put("value", value);
				childListCd.put(jsonObject);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception json) {

		} finally {
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
		return childListCd;
	}
	
	public JSONArray getDistrictDataNCd(String firstCDName,String month,Integer year) {
		Connection connection = null;
		String sr=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		JSONArray districtDataCd = new JSONArray();
		try {		
			
			connection = jdbcTemplate.getDataSource().getConnection();
			preparedStatement = connection.prepareStatement(ShdrcQueryList.NCD_DISTRICTDATA);	
			preparedStatement.setString(1, firstCDName);
			preparedStatement.setInt(2, year);
			preparedStatement.setString(3, month);
			/*preparedStatement.setString(4, Integer.toString(Util.monthsShortNamesMap.get(month)));*/
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				String dist = resultSet.getString(1);
				int value = resultSet.getInt(2);
				jsonObject.put("label", dist);
				jsonObject.put("value", value);
				districtDataCd.put(jsonObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception json) {
	
		} finally {
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
		return districtDataCd;
	}
	
	public JSONArray getInstDataNCd(String firstCDDistName,String firstCDName,String month,Integer year){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		JSONArray childListCd = new JSONArray();
		try {			
			connection = jdbcTemplate.getDataSource().getConnection();			
			preparedStatement = connection.prepareStatement(ShdrcQueryList.NCD_INSTITUTIONDATA);
			preparedStatement.setString(1, firstCDDistName);
			preparedStatement.setString(2, firstCDName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				String insName = resultSet.getString(1);
				int value = resultSet.getInt(2);
				jsonObject.put("label", insName);
				jsonObject.put("value", value);
				childListCd.put(jsonObject);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception json) {

		} finally {
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
		return childListCd;
	}
	
	
	@Override
	public JSONArray getGrandChildDataCd(String districtName,
			String submitType, String generalName) {
		// TODO Auto-generated method stub
		return null;
	}
}
