package gov.shdrc.reports.dao.jdbc;

import gov.shdrc.reports.dao.ITNMSCReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
public class TNMSCReportDAOJdbc implements ITNMSCReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_tnmsc_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_tnmsc_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_tnmsc_ind_cat(?)");
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
				 childList.put("Equip_Code");
				 childList.put("Equip_Inst_Name");
				 childList.put("Equip_Type");
				 childList.put("Drug_Code");
				 childList.put("Drug_Name");
				 childList.put("Drug_Speciality");
				 childList.put("Drug_Category_Code");
				 childList.put("Category_Name");
				 childList.put("Warehouse_Code");
				 childList.put("Warehouse_Name");
				 childList.put("Lab_Code");
				 childList.put("Lab_Name");
				 childList.put("Supp_Code");
				 childList.put("Supp_Name");
				 childList.put("Time_Month_Name");
				 childList.put("Time_Reg_Year");
				 childList.put("Ind_Value");
				 parentList.put(childList);
				 connection=jdbcTemplate.getDataSource().getConnection();				
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_tnmsc_main(?,?,?,?)");
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
						String Equip_Code=resultSet.getString(11);
						String Equip_Inst_Name=resultSet.getString(12);
						String Equip_Type=resultSet.getString(13);
						String Drug_Code=resultSet.getString(14);
						String Drug_Name=resultSet.getString(15);
						String Drug_Speciality=resultSet.getString(16);
						String Drug_Category_Code=resultSet.getString(17);
						String Category_Name=resultSet.getString(18);
						String Warehouse_Code=resultSet.getString(19);
						String Warehouse_Name=resultSet.getString(20);
						String Lab_Code=resultSet.getString(21);
						String Lab_Name=resultSet.getString(22);
						String Supp_Code=resultSet.getString(23);
						String Supp_Name=resultSet.getString(24);
						String Time_Month_Name=resultSet.getString(25);
						int Time_Reg_Year=resultSet.getInt(26);
						int Ind_Value=resultSet.getInt(27);
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
						
						childList.put(Equip_Code);
						childList.put(Equip_Inst_Name);
						childList.put(Equip_Type);
						childList.put(Drug_Code);
						childList.put(Drug_Name);
						childList.put(Drug_Speciality);
						childList.put(Drug_Category_Code);
						childList.put(Category_Name);
						childList.put(Warehouse_Code);
						childList.put(Warehouse_Name);
						childList.put(Lab_Code);
						childList.put(Lab_Name);
						childList.put(Supp_Code);
						childList.put(Supp_Name);
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
			if("Stock Deficit".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.TNMSC_ANALYSISZONE;
			}
			else if("Blacklisted Drugs".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.TNMSC_ANALYSISZONE_BLACKLISTED_DRUGS;
			}
			else if("Supplier Wise Purchase Details".equalsIgnoreCase(analysisReportName)){
				sr = ShdrcReportQueryList.TNMSC_ANALYSISZONE_SUPPLIERWISE_PURCHASE;
			}
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
				String label=resultSet.getString(1);
				int value=resultSet.getInt(2);
				jsonObject.put("label",label);
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
			childList.put("Indicator");
			childList.put("Value");
			parentList.put(childList);
			
			connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement(ShdrcReportQueryList.TNMSC_REPORTZONE);		
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
//Indicator Zone
	public JSONArray getTNMSCPvtData(int directorateId,
			String reportName, int year, String month, String userName) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray tnmscPvtData=null;
		String query=null;
		try{			
			tnmscPvtData=new JSONArray();			
			connection=jdbcTemplate.getDataSource().getConnection();
			if(reportName.equalsIgnoreCase("Human Resource")){
				query="select * from shdrc_tnmsc.ind_tnmsc_hr(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Equipment avaliability & working")){
				query="select * from shdrc_tnmsc.ind_tnmsc_equip_avail(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Equipment Procurement")){
				query="select * from shdrc_tnmsc.ind_tnmsc_equip_procure(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Drug Procurement")){
				query="select * from shdrc_tnmsc.ind_tnmsc_drug_proc(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Quality Test")){
				query="select * from shdrc_tnmsc.ind_tnmsc_quality_test(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Drug Stock")){
				query="select * from shdrc_tnmsc.ind_tnmsc_drug_stock(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("CT & MRI")){
				query="select * from shdrc_tnmsc.ind_tnmsc_ct_mri(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Passbook-Allotment of Amount in Drug Passbook")){
				query="select * from shdrc_tnmsc.ind_tnmsc_p_allotment(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Passbook-Balance of Amount in Drug Passbook")){
				query="select * from shdrc_tnmsc.ind_tnmsc_p_balance(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Passbook-No of Institutions utilised the Fund")){
				query="select * from shdrc_tnmsc.ind_tnmsc_p_inst_utilised(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Passbook-No Of Institution Under Utilised Passbook Funds")){
				query="select * from shdrc_tnmsc.ind_tnmsc_p_inst_under_utilised(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Passbook- Percentage of Institutions utilised the Fund")){
				query="select * from shdrc_tnmsc.ind_tnmsc_p_per_inst_utilised(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Passbook- Percentage of Institutions Under Utilised the Fund")){
				query="select * from shdrc_tnmsc.ind_tnmsc_p_per_inst_under_utilised(?,?,?,?,?)";
			}
			if(reportName.equalsIgnoreCase("Passbook-Utilization of Amount in Drug Passbook")){
				query="select * from shdrc_tnmsc.ind_tnmsc_p_amt_dp(?,?,?,?,?)";
			}
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, reportName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, userName);
			resultSet=preparedStatement.executeQuery();	 	
			
			if(reportName.equalsIgnoreCase("Human Resource")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("IndicatorName",resultSet.getString(1));
					obj.put("IndicatorValue",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Equipment avaliability & working")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("IndicatorName",resultSet.getString(1));
					obj.put("Equipment avaliability & working",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Equipment Procurement")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("IndicatorName",resultSet.getString(1));
					obj.put("Equipment Procurement",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Drug Procurement")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("IndicatorName",resultSet.getString(1));
					obj.put("Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Quality Test")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("IndicatorName",resultSet.getString(1));
					obj.put("Indicator Total",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Drug Stock")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("IndicatorName",resultSet.getString(1));
					obj.put("Drug Stock",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("CT & MRI")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Indicator Name",resultSet.getString(1));
					obj.put("Institution",resultSet.getString(2));
					obj.put("Ind_Value",resultSet.getInt(3));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Passbook-Allotment of Amount in Drug Passbook")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Institution Name",resultSet.getString(1));
					obj.put("Ind_Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Passbook-Balance of Amount in Drug Passbook")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Institution Name",resultSet.getString(1));
					obj.put("Ind_Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Passbook-No of Institutions utilised the Fund")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Institution Name",resultSet.getString(1));
					obj.put("Ind_Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Passbook-No Of Institution Under Utilised Passbook Funds")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Institution Name",resultSet.getString(1));
					obj.put("Ind_Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Passbook- Percentage of Institutions utilised the Fund")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Institution Name",resultSet.getString(1));
					obj.put("Ind_Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Passbook- Percentage of Institutions Under Utilised the Fund")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Institution Name",resultSet.getString(1));
					obj.put("Ind_Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
			}
			if(reportName.equalsIgnoreCase("Passbook-Utilization of Amount in Drug Passbook")){
				while(resultSet.next())
				{
					JSONObject obj=new JSONObject();
					obj.put("Institution Name",resultSet.getString(1));
					obj.put("Ind_Value",resultSet.getInt(2));
					tnmscPvtData.put(obj);
				}
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
		return tnmscPvtData;
	}
	
	@Override
	public JSONObject getPdAccountBalance1(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_pd_acc_bal_1(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String val = rsmd.getColumnName(2);
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String value=resultSet.getString(2);				
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;

	}

	@Override
	public JSONObject getPdAccountBalance2(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_pd_acc_bal_2(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArraySecondTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
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

	@Override
	public JSONObject getNoofdrugsnotfinalizedcurrentyeartrend1(
			int directorateId, String dashBoardName, int year,
			String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_drug_not_finalized_1(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String val = rsmd.getColumnName(2);
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String value=resultSet.getString(2);				
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getNoofdrugsnotfinalizedcurrentyeartrend2(
			int directorateId, String dashBoardName, int year,
			String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_drug_not_finalized_2(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArraySecondTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getSupplier_drug_item(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_supplier_drug_item(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getBlacklisted_Products_Firm(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_blacklisted_perf(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getQC_Result_Tablets(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_qc_tablets(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getQC_Result_Fluids(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_qc_fluids(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getBlacklisted_Products_Quality(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_blacklisted_qua(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return jsonObject;
	}

	@Override
	public JSONObject getASV_ARV_TT_TCV_Stock(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_asv_stock(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getHypertensiveDiabetiesIVFluidCancer(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_hdicn_stock(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getHospitalStock(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_hosp_stock(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getLocalPurchase(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_local_purchase(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getOnlineIndentNonperformance(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_online_indent(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getEmpanelledLaboratoriesSampleTesting(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_empanelled_lab(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);	  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				Double value=resultSet.getDouble(2);
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getUnutilizedPassbook1(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_unutilized_passbook_1(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String val = rsmd.getColumnName(2);
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String value=resultSet.getString(2);				
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getUnutilizedPassbook2(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_unutilized_passbook_2(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArraySecondTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getTnmsc_amc_expiry_categorya_b1_1(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_amc_expiry_categorya_b1_1(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String val = rsmd.getColumnName(2);
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String value=resultSet.getString(2);				
				String color=resultSet.getString(3);				
				childList.put(indicator);				
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getTnmsc_amc_expiry_categorya_b1_2(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_amc_expiry_categorya_b1_2(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArraySecondTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getTnmsc_amc_expiry_categorya_b1_3(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_amc_expiry_categorya_b1_3(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String equipmentType = rsmd.getColumnName(3);	
			String val = rsmd.getColumnName(4);	
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(equipmentType.substring(0, 1).toUpperCase() + equipmentType.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				String equipementType=resultSet.getString(3);	
				Double value=resultSet.getDouble(4);
				String color=resultSet.getString(5);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(equipementType);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayThirdTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getNilGroundStock(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_ngs_stock(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getNilQC_passedStock(int directorateId,
			String dashBoardName, int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_nillqc(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {	
			e.printStackTrace();
		}
		return jsonObject;
	}

	@Override
	public JSONObject getLessStock(int directorateId, String dashBoardName,
			int year, String month, String loggedUser) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		JSONArray parentList=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		List list=new ArrayList();
        try {
        	connection=jdbcTemplate.getDataSource().getConnection();			
			preparedStatement=connection.prepareStatement("select * from shdrc_tnmsc.dash_tnmsc_less_stock(?,?,?,?,?)");		
			preparedStatement.setInt(1, directorateId);
			preparedStatement.setString(2, dashBoardName);
			preparedStatement.setInt(3, year);
			preparedStatement.setString(4, month);
			preparedStatement.setString(5, loggedUser);			
			resultSet=preparedStatement.executeQuery();	
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String indicatorName = rsmd.getColumnName(1);
			String directorateName = rsmd.getColumnName(2);
			String val = rsmd.getColumnName(3);		  
		    list.add(indicatorName.substring(0, 1).toUpperCase() + indicatorName.substring(1));
		    list.add(directorateName.substring(0, 1).toUpperCase() + directorateName.substring(1));
		    list.add(val.substring(0, 1).toUpperCase() + val.substring(1));
		    
			while(resultSet.next())
			{ 				
				childList=new JSONArray();
				String indicator=resultSet.getString(1);
				String directorate=resultSet.getString(2);	
				Double value=resultSet.getDouble(3);
				String color=resultSet.getString(4);				
				childList.put(indicator);				
				childList.put(directorate);	
				childList.put(value);	
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
        try {
			jsonObject.put("headers", list);
			jsonObject.put("jsonArrayFirstTable", parentList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
}
