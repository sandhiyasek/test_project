package gov.shdrc.reports.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




import java.util.ArrayList;
import java.util.List;

import gov.shdrc.reports.dao.IDFWReportDAO;
import gov.shdrc.reports.dao.IDMEReportDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcReportQueryList;
import gov.shdrc.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DFWReportDAOJdbc implements IDFWReportDAO{
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List getIndYearByNameandCategory(int directorateId,String indCategory,String indName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		List resultList=new ArrayList();
		
		try {
		connection=jdbcTemplate.getDataSource().getConnection();				
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfw_year(?,?,?)");
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfw_ind_name(?,?)");	
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
		preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfw_ind_cat(?)");
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
				 preparedStatement = connection.prepareStatement("select * from shdrc_dwh.fh_dfw_main(?,?,?,?)");
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
	public JSONObject getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		String sr=null;
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		String query=null;
		JSONArray analysisZoneFirstSeriesData=new JSONArray();
		JSONArray analysisZoneSecondSeriesData=new JSONArray();
		JSONObject finalList=new JSONObject();			
		try {	
			query = getAnalysisZoneQuery(analysisReportName);	
			connection=jdbcTemplate.getDataSource().getConnection();
			preparedStatement=connection.prepareStatement(query);
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
				String label=resultSet.getString(1);
				BigDecimal currentYearValue=truncateDecimal(resultSet.getDouble(2),2);
				jsonObject1.put("label",label);
				jsonObject1.put("value",currentYearValue);
				jsonObject1.put("seriesName","Current Year Value");
				analysisZoneFirstSeriesData.put(jsonObject1);
				
				BigDecimal previousYearValue=truncateDecimal(resultSet.getDouble(3),2);
				jsonObject2.put("label",label);
				jsonObject2.put("value",previousYearValue);	
				jsonObject2.put("seriesName","Previous Year Value");
				analysisZoneSecondSeriesData.put(jsonObject2);
				
				finalList.put("analysisZoneFirstSeriesData", analysisZoneFirstSeriesData);
				finalList.put("analysisZoneSecondSeriesData", analysisZoneSecondSeriesData);
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
	
	private String getAnalysisZoneQuery(String reportName){
		String query = null;
		
		if("Sterilisation_Comparison_yearwise".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_ANALYSISZONE_STERILISATION_COMP_YEARWISE;
		}
		else if("Sterilisation_Comparison_deptwise".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_ANALYSISZONE_STERILISATION_COMP_DEPTWISE;
		}
		else if("IUCD_Comparison_yearwise".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_ANALYSISZONE_IUCD_COMP_YEARWISE;
		}
		else if("Coverage_of_Deliveries".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_ANALYSISZONE_DELIVERIES_COVERAGE;
		}
		else if("IUD_Prime_Child_Comparison".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_ANALYSISZONE_IUD_PRIME_CHILD_COMPARISON;
		}
		else if("DFW_PHC_Performance_Comparison".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_ANALYSISZONE_PHC_PERFORMANCE_COMPARISON;
		}
		return query;
	}
	
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,
			String toMonth,String userName){
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		JSONArray childList=null;
		String query=null;
		JSONArray parentList=new JSONArray();
        try {        	
        	childList = getReportTemplateHeader(reportName,toYear,toMonth);
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
	
	private JSONArray getReportTemplateHeader(String reportName,Integer toYear,String toMonth){
		JSONArray childList=null;
		try{
			childList=new JSONArray();			
			
			if(("DFW_Female_Sterilisation".equalsIgnoreCase(reportName)) || ("DFW_Male_Sterilisation_New".equalsIgnoreCase(reportName)) || ("DFW_IUCD_Performance".equalsIgnoreCase(reportName)) 
					|| ("DFW_PPIUCD_Performance".equalsIgnoreCase(reportName)) || ("DFW_Total_Sterilisation".equalsIgnoreCase(reportName)) || ("DFW_CC_Programme".equalsIgnoreCase(reportName)) 
					|| ("DFW_OP_Programme".equalsIgnoreCase(reportName))){
				childList.put("District");
				childList.put("eld");
				childList.put("proportionate");
				childList.put("Previous month");
				childList.put("Current month");
				childList.put("Upto month");
				childList.put("per_wrt_eld");
				childList.put("rank_by_per_wrt_eld");
			}
			else if(("Contraceptive_Stock_Position".equalsIgnoreCase(reportName)) || ("District_Wise_Hospital_Deliveries".equalsIgnoreCase(reportName)) || 
					("PHC_Sterilization".equalsIgnoreCase(reportName)) || ("Hospital_Sterilization".equalsIgnoreCase(reportName))){
				childList.put("District");
				childList.put("Indicator");
				childList.put("Value");
			}
			else if(("DFW_Sterilisation_Performance_Comparison".equalsIgnoreCase(reportName)) || ("Sterilisation_IUCD_Performance".equalsIgnoreCase(reportName)) || 
			 ("DFW_IUCD_Performance_Comparison".equalsIgnoreCase(reportName))){
				childList.put("District");
				childList.put("Current Year");
				childList.put("Previous Year");
				childList.put("Inc Dec");
				childList.put("Per Inc Dec");
			}
			else if(("Sterilization_Acceptors_2_Less_Than_2".equalsIgnoreCase(reportName)) || ("Sterilization_Acceptors_One_Child".equalsIgnoreCase(reportName))){
				childList.put("District");
				childList.put("Total Sterilization");
				childList.put("Sterilization with less than 2 children");
				childList.put("Per Sterilization with less than 2 children");
				childList.put("Rank");
			}	
			else if(("DFW_DERep_AccreditedPvtHosplDist".equalsIgnoreCase(reportName)) || ("DFW_DERep_HRDFWBConsolidation".equalsIgnoreCase(reportName))
					 || ("DFW_DERep_HRDistrict".equalsIgnoreCase(reportName)) || ("DFW_DERep_HRInstDistrictConsolidation".equalsIgnoreCase(reportName))){
				childList.put("District");
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Value");
			}
			else if(("DFW_DERep_AccreditedPvtHosplState".equalsIgnoreCase(reportName)) || ("DFW_DERep_EquipmentState".equalsIgnoreCase(reportName))
					 || ("DFW_DERep_FinanceState".equalsIgnoreCase(reportName))|| ("DFW_DERep_HRState".equalsIgnoreCase(reportName))
					 || ("DFW_DERep_KitsState".equalsIgnoreCase(reportName)) || ("DFW_DERep_ProposedTrainingState".equalsIgnoreCase(reportName)) 
					 || ("DFW_DERep_TrainingState".equalsIgnoreCase(reportName)) || ("DFW_DERep_VehiclesState".equalsIgnoreCase(reportName))){
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("Value");
			}
			else if(("DFW_DERep_DrugSupply".equalsIgnoreCase(reportName))){
				childList.put("Indicator");
				childList.put("District");
				childList.put("Institution Type");
				childList.put("Institution Name");
				childList.put("Value");
			}
			else if(("DFW_DERep_Equipment".equalsIgnoreCase(reportName)) || ("DFW_DERep_Finance".equalsIgnoreCase(reportName))
					|| ("DFW_DERep_HRInstitution".equalsIgnoreCase(reportName))|| ("DFW_DERep_Indemnity".equalsIgnoreCase(reportName))
					|| ("DFW_DERep_Kits".equalsIgnoreCase(reportName)) || ("DFW_DERep_ProposedTraining".equalsIgnoreCase(reportName)) 
					|| ("DFW_DERep_Training".equalsIgnoreCase(reportName)) || ("DFW_DERep_Vehicles".equalsIgnoreCase(reportName))){
				childList.put("Indicator");
				childList.put("General Name");
				childList.put("District");
				childList.put("Institution Type");
				childList.put("Institution Name");
				childList.put("Value");
			}
			else if("MTP_Performance".equalsIgnoreCase(reportName)){
				childList.put("District");	
				childList.put("Indicator Category");	
				childList.put("Sterilisation upto" + " " + toMonth + " " + toYear);
				childList.put("IUD upto" + " " + toMonth + " " + toYear);				
				childList.put("OP_CC upto" + " " + toMonth + " " + toYear);
				childList.put("Total upto" + " " + toMonth + " " + toYear);
				childList.put("Last Year");	
				childList.put("Inc/Dec");	
				
			}			
			else if("DFW Performance with respect to ELD".equalsIgnoreCase(reportName)){
				childList.put("Programme");	
				childList.put("Annual eld");	
				childList.put("Performance during" + " " + toMonth + " " + toYear);
				childList.put("Performance upto" + " " + toMonth + " " + toYear);
				childList.put("Percentage wrt eld");					
			}	
			else if("DFW Performance of Methods".equalsIgnoreCase(reportName)){
				childList.put("Method");	
				childList.put("Current");	
				childList.put("Previous");
				childList.put("Increase/Decrease");
				childList.put("Percentage of Increase/Decrease");					
			}	
			else if("DFW Performance - Sharing of Contribution".equalsIgnoreCase(reportName)){
				childList.put("Department");	
				childList.put("Current Year");	
				childList.put("Percentage of Performance");
				childList.put("Previous Year");
				childList.put("Increase/Decrease");					
			}	
		}catch(Exception json){
			
		}
		return childList;
	}
	
	private String getQueryByReportName(String reportName){
		String query = null;
		
		if("DFW_Female_Sterilisation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_FEMALE_STERILISATION;
		}
		else if("DFW_Male_Sterilisation_New".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_MALE_STERILISATION;
		}
		else if("DFW_IUCD_Performance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_IUCD_PERFORMANCE;
		}
		else if("DFW_PPIUCD_Performance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_PPIUCD_PERFORMANCE;
		}
		else if("DFW_Total_Sterilisation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_TOTAL_STERILISATION;
		}
		else if("DFW_CC_Programme".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_CC_PROGRAMME;
		}
		else if("DFW_OP_Programme".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_OP_PROGRAMME;
		}
		else if("Contraceptive_Stock_Position".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_CONTRACEPTIVE_STOCK_POSITION;
		}
		else if("District_Wise_Hospital_Deliveries".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DISTRICTWISE_HOSP_DELIVERIES;
		}
		else if("PHC_Sterilization".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_PHC_STERILISATION;
		}
		else if("Hospital_Sterilization".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_HOSPITAL_STERILISATION;
		}
		else if("DFW_Sterilisation_Performance_Comparison".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_STERILISATION_PERF_COMP;
		}
		else if("Sterilisation_IUCD_Performance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_STERILISATION_IUCD_PERF;
		}
		else if("DFW_IUCD_Performance_Comparison".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_IUCD_PERF_COMP;
		}
		else if("Sterilization_Acceptors_2_Less_Than_2".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_STERILISATION_ACCEPTORS_LESS_THAN_TWO;
		}
		else if("Sterilization_Acceptors_One_Child".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_STERILISATION_ACCEPTORS_ONE_CHILD;
		}
		else if("DFW_DERep_AccreditedPvtHosplDist".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_ACCREDITEDPVTHOSPDIST;
		}
		else if("DFW_DERep_AccreditedPvtHosplState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_ACCREDITEDPVTHOSPSTATE;
		}
		else if("DFW_DERep_DrugSupply".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_DRUGSUPPLY;
		}
		else if("DFW_DERep_Equipment".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_EQUIPMENT;
		}
		else if("DFW_DERep_EquipmentState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_EQUIPMENT_STATE;
		}
		else if("DFW_DERep_Finance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_FINANCE;
		}
		else if("DFW_DERep_FinanceState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_FINANCE_STATE;
		}
		else if("DFW_DERep_HRDFWBConsolidation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_HRDFWBCONSOLIDATION;
		}
		else if("DFW_DERep_HRDistrict".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_HRDISTRICT;
		}
		else if("DFW_DERep_HRInstitution".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_HRINSTITUTION;
		}
		else if("DFW_DERep_HRInstDistrictConsolidation".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_HRINSTDISTCONSOLIDATION;
		}
		else if("DFW_DERep_HRState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_HRSTATE;
		}
		else if("DFW_DERep_Indemnity".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_INDEMNITY;
		}
		else if("DFW_DERep_KitsState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_KITSSTATE;
		}
		else if("DFW_DERep_ProposedTrainingState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_PROPOSEDTRAININGSTATE;
		}
		else if("DFW_DERep_TrainingState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_TRAININGSTATE;
		}
		else if("DFW_DERep_VehiclesState".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_VEHICLESSTATE;
		}
		else if("DFW_DERep_Kits".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_KITS;
		}
		else if("DFW_DERep_ProposedTraining".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_PROPOSEDTRAINING;
		}
		else if("DFW_DERep_Training".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_TRAINING;
		}
		else if("DFW_DERep_Vehicles".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_DEREP_VEHICLES;
		}
		else if("MTP_Performance".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_MTP_PERFORMANCE;
		}
		else if("DFW Performance with respect to ELD".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_PERFORMANCE_WRT_ELD;
		}
		else if("DFW Performance of Methods".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_PERFORMANCE_OF_METHODS;
		}
		else if("DFW Performance - Sharing of Contribution".equalsIgnoreCase(reportName)){
			query=ShdrcReportQueryList.DFW_REPORTZONE_PERFORMANCE_SHARING_OF_CONTRIBUTION;
		}
		return query;
	}
	
	private JSONArray getDataByReportName(ResultSet resultSet,String reportName,JSONArray parentList){
		JSONArray childList=null;
		try{
			if(("DFW_Female_Sterilisation".equalsIgnoreCase(reportName)) || ("DFW_Male_Sterilisation_New".equalsIgnoreCase(reportName)) || ("DFW_IUCD_Performance".equalsIgnoreCase(reportName)) 
					|| ("DFW_PPIUCD_Performance".equalsIgnoreCase(reportName)) || ("DFW_Total_Sterilisation".equalsIgnoreCase(reportName)) || ("DFW_CC_Programme".equalsIgnoreCase(reportName)) 
					|| ("DFW_OP_Programme".equalsIgnoreCase(reportName))){
				BigDecimal secondColTotal = null;
				BigDecimal thirdColTotal = null;
				BigDecimal fourthColTotal = null;
				BigDecimal fifthColTotal = null;
				BigDecimal sixthColTotal = null;
				BigDecimal seventhColTotal = null;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String district=resultSet.getString(1);			
					BigDecimal secondColValue=truncateDecimal(resultSet.getDouble(2),2);
					BigDecimal thirdColValue=truncateDecimal(resultSet.getDouble(3),2);
					BigDecimal fourthColValue=truncateDecimal(resultSet.getDouble(4),2);
					BigDecimal fifthColValue=truncateDecimal(resultSet.getDouble(5),2);
					BigDecimal sixthColValue=truncateDecimal(resultSet.getDouble(6),2);
					BigDecimal seventhColValue=truncateDecimal(resultSet.getDouble(7),2);
					int eighthColValue=resultSet.getInt(8);
					
					if(secondColTotal==null){
						secondColTotal=secondColValue;
					}
					else{
						secondColTotal=secondColTotal.add(secondColValue);
					}
					if(thirdColTotal==null){
						thirdColTotal=thirdColValue;
					}
					else{
						thirdColTotal=thirdColTotal.add(thirdColValue);
					}
					if(fourthColTotal==null){
						fourthColTotal=fourthColValue;
					}
					else{
						fourthColTotal=fourthColTotal.add(fourthColValue);
					}
					if(fifthColTotal==null){
						fifthColTotal=fifthColValue;	
					}
					else{
						fifthColTotal=fifthColTotal.add(fifthColValue);
					}
					if(sixthColTotal==null){
						sixthColTotal=sixthColValue;
					}
					else{
						sixthColTotal=sixthColTotal.add(sixthColValue);
					}
					if(seventhColTotal==null){
						seventhColTotal=seventhColValue;
					}
					else{
						seventhColTotal=seventhColTotal.add(seventhColValue);
					}
					
					childList.put(district);				
					childList.put(secondColValue);	
					childList.put(thirdColValue);
					childList.put(fourthColValue);
					childList.put(fifthColValue);				
					childList.put(sixthColValue);	
					childList.put(seventhColValue);
					childList.put(eighthColValue);
					parentList.put(childList);
				}
				childList=new JSONArray();
				childList.put("Totals");				
				childList.put(secondColTotal);				
				childList.put(thirdColTotal);	
				childList.put(fourthColTotal);
				childList.put(fifthColTotal);
				childList.put(sixthColTotal);				
				childList.put(seventhColTotal);
				childList.put("");	
				parentList.put(childList);
			}
			else if(("Contraceptive_Stock_Position".equalsIgnoreCase(reportName)) || ("District_Wise_Hospital_Deliveries".equalsIgnoreCase(reportName)) || 
					("PHC_Sterilization".equalsIgnoreCase(reportName)) || ("Hospital_Sterilization".equalsIgnoreCase(reportName)) || ("DFW_DERep_AccreditedPvtHosplState".equalsIgnoreCase(reportName))
					|| ("DFW_DERep_EquipmentState".equalsIgnoreCase(reportName)) || ("DFW_DERep_FinanceState".equalsIgnoreCase(reportName))
					|| ("DFW_DERep_HRState".equalsIgnoreCase(reportName))|| ("DFW_DERep_KitsState".equalsIgnoreCase(reportName)) || ("DFW_DERep_ProposedTrainingState".equalsIgnoreCase(reportName)) 
					 || ("DFW_DERep_TrainingState".equalsIgnoreCase(reportName)) || ("DFW_DERep_VehiclesState".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String district=resultSet.getString(1);	
					String indicator=resultSet.getString(2);	
					BigDecimal value=truncateDecimal(resultSet.getDouble(3),2);
					
					childList.put(district);
					childList.put(indicator);
					childList.put(value);
					parentList.put(childList);
				}
			}
			else if(("DFW_Sterilisation_Performance_Comparison".equalsIgnoreCase(reportName)) || ("Sterilisation_IUCD_Performance".equalsIgnoreCase(reportName)) || 
					 ("DFW_IUCD_Performance_Comparison".equalsIgnoreCase(reportName))){
				BigDecimal secondColTotal = null;
				BigDecimal thirdColTotal = null;
				BigDecimal fourthColTotal = null;
				BigDecimal fifthColTotal = null;
				
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String district=resultSet.getString(1);			
					BigDecimal secondColValue=truncateDecimal(resultSet.getDouble(2),2);
					BigDecimal thirdColValue=truncateDecimal(resultSet.getDouble(3),2);
					BigDecimal fourthColValue=truncateDecimal(resultSet.getDouble(4),2);
					BigDecimal fifthColValue=truncateDecimal(resultSet.getDouble(5),2);
										
					if(secondColTotal==null){
						secondColTotal=secondColValue;
					}
					else{
						secondColTotal=secondColTotal.add(secondColValue);
					}
					if(thirdColTotal==null){
						thirdColTotal=thirdColValue;
					}
					else{
						thirdColTotal=thirdColTotal.add(thirdColValue);
					}
					if(fourthColTotal==null){
						fourthColTotal=fourthColValue;
					}
					else{
						fourthColTotal=fourthColTotal.add(fourthColValue);
					}
					if(fifthColTotal==null){
						fifthColTotal=fifthColValue;	
					}
					else{
						fifthColTotal=fifthColTotal.add(fifthColValue);
					}
					
					childList.put(district);				
					childList.put(secondColValue);	
					childList.put(thirdColValue);
					childList.put(fourthColValue);
					childList.put(fifthColValue);
					parentList.put(childList);
				}
				childList=new JSONArray();
				childList.put("Totals");				
				childList.put(secondColTotal);				
				childList.put(thirdColTotal);	
				childList.put(fourthColTotal);
				childList.put(fifthColTotal);
				parentList.put(childList);
			}
			else if(("Sterilization_Acceptors_2_Less_Than_2".equalsIgnoreCase(reportName)) || ("Sterilization_Acceptors_One_Child".equalsIgnoreCase(reportName))){
				BigDecimal secondColTotal = null;
				BigDecimal thirdColTotal = null;
				BigDecimal fourthColTotal = null;
				
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String district=resultSet.getString(1);			
					BigDecimal secondColValue=truncateDecimal(resultSet.getDouble(2),2);
					BigDecimal thirdColValue=truncateDecimal(resultSet.getDouble(3),2);
					BigDecimal fourthColValue=truncateDecimal(resultSet.getDouble(4),2);
					int fifthColValue=resultSet.getInt(5);
										
					if(secondColTotal==null){
						secondColTotal=secondColValue;
					}
					else{
						secondColTotal=secondColTotal.add(secondColValue);
					}
					if(thirdColTotal==null){
						thirdColTotal=thirdColValue;
					}
					else{
						thirdColTotal=thirdColTotal.add(thirdColValue);
					}
					if(fourthColTotal==null){
						fourthColTotal=fourthColValue;
					}
					else{
						fourthColTotal=fourthColTotal.add(fourthColValue);
					}
					
					childList.put(district);				
					childList.put(secondColValue);	
					childList.put(thirdColValue);
					childList.put(fourthColValue);
					childList.put(fifthColValue);
					parentList.put(childList);
				}
				childList=new JSONArray();
				childList.put("Totals");				
				childList.put(secondColTotal);				
				childList.put(thirdColTotal);	
				childList.put(fourthColTotal);
				childList.put("");	
				parentList.put(childList);
			}
			else if(("DFW_DERep_AccreditedPvtHosplDist".equalsIgnoreCase(reportName)) || ("DFW_DERep_HRDFWBConsolidation".equalsIgnoreCase(reportName))
					 || ("DFW_DERep_HRDistrict".equalsIgnoreCase(reportName)) || ("DFW_DERep_HRInstDistrictConsolidation".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String district=resultSet.getString(1);	
					String indicator=resultSet.getString(2);	
					String generalName=resultSet.getString(3);	
					BigDecimal value=truncateDecimal(resultSet.getDouble(4),2);
					
					childList.put(district);
					childList.put(indicator);
					childList.put(generalName);
					childList.put(value);
					parentList.put(childList);
				}
			}
			else if("MTP_Performance".equalsIgnoreCase(reportName)){
				BigDecimal thirdColTotal = null;
				BigDecimal fourthColTotal = null;
				BigDecimal fifthColTotal = null;
				BigDecimal sixthColTotal = null;
				BigDecimal seventhColTotal = null;
				BigDecimal eighthColTotal = null;
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String district=resultSet.getString(1);		
					String indicatorCategory=resultSet.getString(2);	
					BigDecimal thirdColValue=truncateDecimal(resultSet.getDouble(3),2);
					BigDecimal fourthColValue=truncateDecimal(resultSet.getDouble(4),2);
					BigDecimal fifthColValue=truncateDecimal(resultSet.getDouble(5),2);
					BigDecimal sixthColValue=truncateDecimal(resultSet.getDouble(7),2);
					BigDecimal seventhColValue=truncateDecimal(resultSet.getDouble(8),2);
					BigDecimal eighthColValue=truncateDecimal(resultSet.getDouble(9),2);
					
					if(thirdColTotal==null){
						thirdColTotal=thirdColValue;
					}
					else{
						thirdColTotal=thirdColTotal.add(thirdColValue);
					}
					if(fourthColTotal==null){
						fourthColTotal=fourthColValue;
					}
					else{
						fourthColTotal=fourthColTotal.add(fourthColValue);
					}
					if(fifthColTotal==null){
						fifthColTotal=fifthColValue;	
					}
					else{
						fifthColTotal=fifthColTotal.add(fifthColValue);
					}
					if(sixthColTotal==null){
						sixthColTotal=sixthColValue;
					}
					else{
						sixthColTotal=sixthColTotal.add(sixthColValue);
					}
					if(seventhColTotal==null){
						seventhColTotal=seventhColValue;
					}
					else{
						seventhColTotal=seventhColTotal.add(seventhColValue);
					}
					if(eighthColTotal==null){
						eighthColTotal=eighthColValue;
					}
					else{
						eighthColTotal=seventhColTotal.add(eighthColValue);
					}
					
					childList.put(district);				
					childList.put(indicatorCategory);	
					childList.put(thirdColValue);
					childList.put(fourthColValue);
					childList.put(fifthColValue);				
					childList.put(sixthColValue);	
					childList.put(seventhColValue);
					childList.put(eighthColValue);
					parentList.put(childList);
				}
				childList=new JSONArray();
				childList.put("Totals");	
				childList.put("");	
				childList.put(thirdColTotal);	
				childList.put(fourthColTotal);
				childList.put(fifthColTotal);
				childList.put(sixthColTotal);				
				childList.put(seventhColTotal);
				childList.put(eighthColTotal);				
				parentList.put(childList);
			}
			else if(("DFW_DERep_DrugSupply".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String institutionType=resultSet.getString(1);	
					String institutionName=resultSet.getString(2);	
					String district=resultSet.getString(3);	
					String indicator=resultSet.getString(4);	
					BigDecimal value=truncateDecimal(resultSet.getDouble(5),2);
					
					childList.put(indicator);
					childList.put(district);
					childList.put(institutionType);
					childList.put(institutionName);
					childList.put(value);
					parentList.put(childList);
				}
			}
			else if(("DFW_DERep_Equipment".equalsIgnoreCase(reportName)) || ("DFW_DERep_Finance".equalsIgnoreCase(reportName))
					|| ("DFW_DERep_HRInstitution".equalsIgnoreCase(reportName))|| ("DFW_DERep_Indemnity".equalsIgnoreCase(reportName))
					|| ("DFW_DERep_Kits".equalsIgnoreCase(reportName)) || ("DFW_DERep_ProposedTraining".equalsIgnoreCase(reportName)) 
					|| ("DFW_DERep_Training".equalsIgnoreCase(reportName)) || ("DFW_DERep_Vehicles".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String institutionType=resultSet.getString(1);	
					String institutionName=resultSet.getString(2);	
					String district=resultSet.getString(3);	
					String indicator=resultSet.getString(4);	
					String generalName=resultSet.getString(5);	
					BigDecimal value=truncateDecimal(resultSet.getDouble(6),2);
					
					childList.put(indicator);
					childList.put(generalName);
					childList.put(district);
					childList.put(institutionType);
					childList.put(institutionName);
					childList.put(value);
					parentList.put(childList);
				}
			}
			else if(("DFW Performance with respect to ELD".equalsIgnoreCase(reportName)) || ("DFW Performance of Methods".equalsIgnoreCase(reportName))
					|| ("DFW Performance - Sharing of Contribution".equalsIgnoreCase(reportName))){
				while(resultSet.next())
				{ 				
					childList=new JSONArray();
					String firstCol=resultSet.getString(1);	
					BigDecimal secondCol=truncateDecimal(resultSet.getDouble(2),2);
					BigDecimal thirdCol=truncateDecimal(resultSet.getDouble(3),2);
					BigDecimal fourthCol=truncateDecimal(resultSet.getDouble(4),2);
					BigDecimal fifthCol=truncateDecimal(resultSet.getDouble(5),2);
					
					childList.put(firstCol);
					childList.put(secondCol);
					childList.put(thirdCol);
					childList.put(fourthCol);
					childList.put(fifthCol);
					parentList.put(childList);
				}
			}
			
		}catch(Exception json){
			
		}
		return parentList;
	}
	
	//Reports Zone
		public JSONArray getReportZoneEmpData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,
				String toMonth,String userName){
			Connection connection = null;
			PreparedStatement preparedStatement =null;
			ResultSet resultSet =null;
			JSONArray childList=null;
			JSONArray parentList=new JSONArray();
	        try {  
	        	childList = new JSONArray();
	        	childList.put("Employee Name");
				childList.put("GPF CPS no");
				childList.put("Gender");
				childList.put("Designation");
				childList.put("Pay Band");
				childList.put("Duty Pay");
				childList.put("Grade Pay");
				childList.put("Recruited by");
				childList.put("Group");
				childList.put("Community");				
				childList.put("Date of birth");
				childList.put("First Appointment post");
				childList.put("First Appointment DOJ");
				childList.put("Regularisation Date");
				childList.put("Probation Declaration date");
				childList.put("Retirement Date");
				childList.put("Increment Due Month");
				childList.put("SLS Due Date");
				childList.put("Physical Status");				
				childList.put("Punishments");
				childList.put("Nominee Details");
				childList.put("District Name");
				childList.put("Institution Name");
				childList.put("Transfer Status");
				childList.put("Retirement Type");
				childList.put("Transfer Type");
				childList.put("Working Location");
				childList.put("Marital Status");
				childList.put("Qualification");
				childList.put("UG Major");
				childList.put("PG Major");
				parentList.put(childList);
							
				connection=jdbcTemplate.getDataSource().getConnection();
				preparedStatement=connection.prepareStatement(ShdrcReportQueryList.DFW_REPORTZONE_EMPLOYEE_REPORT);
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
					String employeeName=resultSet.getString(3);	
					String gpfcpsNo=resultSet.getString(4);	
					String gender=resultSet.getString(5);	
					String designation=resultSet.getString(6);	
					String payBand=resultSet.getString(7);	
					String dutyPay=resultSet.getString(8);	
					String gradePay=resultSet.getString(9);	
					String recruitedBy=resultSet.getString(10);	
					String group=resultSet.getString(11);	
					String community=resultSet.getString(12);					
					String dateofBirth=resultSet.getString(13);	
					String firstAppointmentPost=resultSet.getString(14);	
					String firstAppointmentDOJ=resultSet.getString(15);	
					String regularisationDate=resultSet.getString(16);	
					String probationDeclarationDate=resultSet.getString(17);
					String retirementDate=resultSet.getString(18);	
					String incrementDueMonth=resultSet.getString(19);	
					String slsDueDate=resultSet.getString(20);	
					String physicalStatus=resultSet.getString(21);	
					String punishments=resultSet.getString(22);	
					String nomineeDetails=resultSet.getString(23);	
					String districtName = resultSet.getString(24);	
					String institutionName = resultSet.getString(25);	
					String transferStatus=resultSet.getString(26);	
					String retirementType=resultSet.getString(27);	
					String transferType=resultSet.getString(28);	
					String workingLocation=resultSet.getString(29);	
					String maritalStatus=resultSet.getString(30);	
					String qualification=resultSet.getString(31);	
					String ugmajor=resultSet.getString(32);	
					String pgmajor=resultSet.getString(33);	
					
					childList.put(employeeName);
					childList.put(gpfcpsNo);
					childList.put(gender);
					childList.put(designation);
					childList.put(payBand);
					childList.put(dutyPay);
					childList.put(gradePay);
					childList.put(recruitedBy);
					childList.put(group);
					childList.put(community);					
					childList.put(dateofBirth);
					childList.put(firstAppointmentPost);
					childList.put(firstAppointmentDOJ);
					childList.put(regularisationDate);
					if(probationDeclarationDate!=null)
						childList.put(probationDeclarationDate);
					else
						childList.put("");
					childList.put(retirementDate);
					childList.put(incrementDueMonth);
					childList.put(slsDueDate);
					childList.put(physicalStatus);
					childList.put(punishments);
					childList.put(nomineeDetails);
					childList.put(districtName);
					childList.put(institutionName);
					childList.put(transferStatus);
					childList.put(retirementType);
					childList.put(transferType);
					childList.put(workingLocation);
					childList.put(maritalStatus);
					childList.put(qualification);
					childList.put(ugmajor);
					childList.put(pgmajor);
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

