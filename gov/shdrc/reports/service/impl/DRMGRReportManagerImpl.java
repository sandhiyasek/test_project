package gov.shdrc.reports.service.impl;

import java.util.List;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.IDRMGRReportDAO;
import gov.shdrc.reports.service.IDRMGRReportManager;
import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DRMGRReportManagerImpl implements IDRMGRReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;

	@Autowired
	IDRMGRReportDAO drmgrReportDAO;
	
	@Override
	public JSONArray getDRMGRIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return drmgrReportDAO.getDRMGRIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,
			String indicatorCategory,String indicatorName, String departmentName, int year,
			String month, String loggedUser) {		
		return drmgrReportDAO.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName,departmentName, year, month, loggedUser);
	}
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return drmgrReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
	}

	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return drmgrReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return drmgrReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return drmgrReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return drmgrReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}

	//Reports Zone
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName){
		return drmgrReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
	}
	//Analysis Zone
	@Override
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return drmgrReportDAO.getAnalysisZoneData(directorateId,analysisReportName,year,month,userName);
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
}
