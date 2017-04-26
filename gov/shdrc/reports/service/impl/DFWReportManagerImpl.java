package gov.shdrc.reports.service.impl;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.IDFWReportDAO;
import gov.shdrc.reports.service.IDFWReportManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DFWReportManagerImpl implements IDFWReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;
	@Autowired
	IDFWReportDAO dfwReportDAO;
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return dfwReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return dfwReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return dfwReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return dfwReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
	@Override
	public JSONArray getDFWIndicatorList(int directorateId,
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
	@Override
	public JSONObject getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return dfwReportDAO.getAnalysisZoneData(directorateId,analysisReportName,year,month,userName);
	}

	//Indicator Zone	
	public List<String> getIndicatorCategories(int directorateId) {
		return commonReportDAO.getIndicatorCategories(directorateId);
	}

	public JSONArray getIndicatorPvtStateData(int directorateId, String category,
			String fromMonth, int fromYear, String toMonth, int toYear,
			String username) {
		return commonReportDAO.getIndicatorPvtStateData(directorateId, category, fromMonth, fromYear, toMonth, toYear, username);
	}

	public JSONArray getIndicatorPvtDistrictData(int directorateId, String category,
			String indicator, String generalCategory, String fromMonth, int fromYear, String toMonth,
			int toYear, String username) {
		return commonReportDAO.getIndicatorPvtDistrictData(directorateId, category, indicator, generalCategory, 
				fromMonth, fromYear, toMonth, toYear, username);
	}

	public List<String> getGeneralCategory(int directorateId,String indicator) {
		return commonReportDAO.getGeneralCategory(directorateId,indicator);
	}
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return commonReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
	}
	public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory, String indicator, String generalCategory,
			String district, String fromMonth, int fromYear, String toMonth,int toYear, String userName) {
		return commonReportDAO.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,
				district,fromMonth,fromYear,toMonth,toYear,userName);
	}
	
	//Reports Zone
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName){
		return dfwReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
	}
	public JSONArray getReportZoneEmpData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,
			String toMonth,String userName){
		return dfwReportDAO.getReportZoneEmpData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
	}
}
