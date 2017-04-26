package gov.shdrc.reports.service.impl;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.ISBCSReportDAO;
import gov.shdrc.reports.service.ISBCSReportManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SBCSReportManagerImpl implements ISBCSReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;
	@Autowired
	ISBCSReportDAO sbcsReportDAO;


	@Override
	public JSONArray getSBCSIndicatorList(int directorateId,
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
	//Analysis Zone
	@Override
	public JSONArray getAnalysisZoneData(Integer directorateId,String ind,Integer year,String month,String userName){
		return sbcsReportDAO.getAnalysisZoneData(directorateId,ind,year,month,userName);
	}
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName){
		return sbcsReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
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
	
	public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory, String indicator, String generalCategory,
			String district, String fromMonth, int fromYear, String toMonth,int toYear, String userName) {
		return commonReportDAO.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,
				district,fromMonth,fromYear,toMonth,toYear,userName);
	}
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return sbcsReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return sbcsReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return sbcsReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return sbcsReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
	public JSONArray getNewSbcsDashboardData(int directorateId,
			String reportName, int year, String month, String loggedUser) {
		return sbcsReportDAO.getNewSbcsDashboardData(directorateId, reportName, year, month, loggedUser);
	}
}
