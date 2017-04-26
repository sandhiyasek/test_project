package gov.shdrc.reports.dao;



import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ITNMSCReportDAO{	
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);
	public JSONObject getPdAccountBalance1(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getPdAccountBalance2(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getNoofdrugsnotfinalizedcurrentyeartrend1(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getNoofdrugsnotfinalizedcurrentyeartrend2(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getSupplier_drug_item(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getBlacklisted_Products_Firm(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getQC_Result_Tablets(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getQC_Result_Fluids(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getBlacklisted_Products_Quality(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getASV_ARV_TT_TCV_Stock(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getHypertensiveDiabetiesIVFluidCancer(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getHospitalStock(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getLocalPurchase(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getOnlineIndentNonperformance(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getEmpanelledLaboratoriesSampleTesting(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getUnutilizedPassbook1(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getUnutilizedPassbook2(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getTnmsc_amc_expiry_categorya_b1_1(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getTnmsc_amc_expiry_categorya_b1_2(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getTnmsc_amc_expiry_categorya_b1_3(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getNilGroundStock(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getNilQC_passedStock(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONObject getLessStock(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName);
	//Indicator Zone
	public JSONArray getTNMSCPvtData(int directorateId,String reportName,int year,String month,String userName);
}
