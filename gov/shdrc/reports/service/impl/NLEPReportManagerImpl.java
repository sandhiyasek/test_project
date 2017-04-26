package gov.shdrc.reports.service.impl;

import java.util.List;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.INLEPReportDAO;
import gov.shdrc.reports.service.INLEPReportManager;
import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NLEPReportManagerImpl implements INLEPReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;

	@Autowired
	INLEPReportDAO nlepReportDAO;

	@Override
	public JSONArray getNLEPIndicatorList(int directorateId,
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
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return nlepReportDAO.getAnalysisZoneData(directorateId,analysisReportName,year,month,userName);
	}
	@Override
	public JSONObject getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return nlepReportDAO.getAnalysisZoneMultiSeriesData(directorateId,analysisReportName,year,month,userName);
	}
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName){
		return nlepReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
	}
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return commonReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
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

	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return nlepReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return nlepReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return nlepReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return nlepReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
	@Override
	public JSONArray getNLEPBCPDashboardData(int directorateId,String dashBoardType,int year,String month,String userName){
	    return nlepReportDAO.getNLEPBCPDashboardData(directorateId,dashBoardType,year,month,userName);
	}
	@Override
	public JSONArray getNLEPBCPDashboardDistrictData(int directorateId,String dashBoardType,int year,String month,String userName){
	    return nlepReportDAO.getNLEPBCPDashboardDistrictData(directorateId,dashBoardType,year,month,userName);
	}
}
