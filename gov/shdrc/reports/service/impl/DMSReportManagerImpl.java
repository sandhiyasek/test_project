package gov.shdrc.reports.service.impl;

import gov.shdrc.home.dao.ICommonDAO;
import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.IDMSReportDAO;
import gov.shdrc.reports.service.IDMSReportManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DMSReportManagerImpl implements IDMSReportManager {
	
	@Autowired
	IDMSReportDAO dmsReportDAO;
	@Autowired
	ICommonReportDAO commonReportDAO;
	@Autowired
	ICommonDAO commonDAO;
	
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return dmsReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return dmsReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return dmsReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return dmsReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
	//Reports Zone
	
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String district,String userName){
		return dmsReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,district,userName);}
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return commonReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
	}
	/*public JSONArray getDMSIndicatorList(int directorateId,String indicatorCategory,int year,String month,String loggedUser){
		return dmsReportDAO.getDMSIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);
	}
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,String indicatorCategory,String indicatorName,int year,String month,String loggedUser){
		return dmsReportDAO.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, year, month, loggedUser);
	}
	public JSONArray getInstitutionwiseIndicaotrDetails(int directorateID,String indicatorCategory,String indicatorName,String districtName,int year,String month,String loggedUser){
		return dmsReportDAO.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, districtName, year, month, loggedUser);
	}*/
	@Override
	public JSONArray getIndicatorList(int directorateId,
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
	public JSONArray getAnalysisZoneData(Integer directorateId,String ind,Integer year,String month,String userName){
		return dmsReportDAO.getAnalysisZoneData(directorateId,ind,year,month,userName);
	}
	public JSONArray getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return dmsReportDAO.getAnalysisZoneMultiSeriesData(directorateId,analysisReportName,year,month,userName);			
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
	@Override
	public List<CommonStringList> getDistrictList(String userName) {
		return commonDAO.getDistricts(userName);
	}	
}
