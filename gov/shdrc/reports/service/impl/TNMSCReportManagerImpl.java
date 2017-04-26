package gov.shdrc.reports.service.impl;


import java.util.List;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.ITNMSCReportDAO;
import gov.shdrc.reports.service.ITNMSCReportManager;
import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TNMSCReportManagerImpl implements ITNMSCReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;
	@Autowired
	ITNMSCReportDAO tnmscReportDAO;
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return tnmscReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return tnmscReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
	@Override
	public JSONArray getTNMSCIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return commonReportDAO.getIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,
			String indicatorCategory, String indicatorName, int year,
			String month, String loggedUser) {		
		return commonReportDAO.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, year, month, loggedUser);
	}
	@Override
	public JSONArray getInstitutionwiseIndicaotrDetails(int directorateID,
			String indicatorCategory, String indicatorName,
			String districtName, int year, String month, String loggedUser) {		
		return commonReportDAO.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, districtName, year, month, loggedUser);
	}
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return commonReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
	}
	//Analysis Zone
	@Override
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return tnmscReportDAO.getAnalysisZoneData(directorateId,analysisReportName,year,month,userName);
	}
	
	//Reports Zone
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName){
		return tnmscReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
	}
	//Indicator Zone
	public JSONArray getTNMSCPvtData(int directorateId,
			String reportName, int year, String month, String userName) {
		return tnmscReportDAO.getTNMSCPvtData(directorateId, reportName, year, month, userName);
	}
	@Override
	public JSONObject getPdAccountBalance1(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		
		return tnmscReportDAO.getPdAccountBalance1(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getPdAccountBalance2(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		
		return tnmscReportDAO.getPdAccountBalance2(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getNoofdrugsnotfinalizedcurrentyeartrend1(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		
		return tnmscReportDAO.getNoofdrugsnotfinalizedcurrentyeartrend1(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getNoofdrugsnotfinalizedcurrentyeartrend2(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		
		return tnmscReportDAO.getNoofdrugsnotfinalizedcurrentyeartrend2(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getSupplier_drug_item(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		return tnmscReportDAO.getSupplier_drug_item(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getBlacklisted_Products_Firm(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		return tnmscReportDAO.getBlacklisted_Products_Firm(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getQC_Result_Tablets(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getQC_Result_Tablets(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getQC_Result_Fluids(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getQC_Result_Fluids(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getBlacklisted_Products_Quality(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getBlacklisted_Products_Quality(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getASV_ARV_TT_TCV_Stock(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getASV_ARV_TT_TCV_Stock(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getHypertensiveDiabetiesIVFluidCancer(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getHypertensiveDiabetiesIVFluidCancer(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getHospitalStock(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getHospitalStock(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getLocalPurchase(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getLocalPurchase(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getOnlineIndentNonperformance(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getOnlineIndentNonperformance(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getEmpanelledLaboratoriesSampleTesting(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getEmpanelledLaboratoriesSampleTesting(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getUnutilizedPassbook1(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {
		// TODO Auto-generated method stub
		return tnmscReportDAO.getUnutilizedPassbook1(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getUnutilizedPassbook2(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return tnmscReportDAO.getUnutilizedPassbook2(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getTnmsc_amc_expiry_categorya_b1_1(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return tnmscReportDAO.getTnmsc_amc_expiry_categorya_b1_1(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getTnmsc_amc_expiry_categorya_b1_2(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return tnmscReportDAO.getTnmsc_amc_expiry_categorya_b1_2(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getTnmsc_amc_expiry_categorya_b1_3(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return tnmscReportDAO.getTnmsc_amc_expiry_categorya_b1_3(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getNilGroundStock(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return tnmscReportDAO.getNilGroundStock(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getNilQC_passedStock(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return tnmscReportDAO.getNilQC_passedStock(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONObject getLessStock(int directorateId, String indicatorCategory,
			int year, String month, String loggedUser) {		
		return tnmscReportDAO.getLessStock(directorateId, indicatorCategory, year, month, loggedUser);
	}
}
