package gov.shdrc.reports.service.impl;

import java.util.Date;
import java.util.List;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.IDMEReportDAO;
import gov.shdrc.reports.service.IDMEReportManager;
import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DMEReportManagerImpl implements IDMEReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;
	@Autowired
	IDMEReportDAO dmeReportDAO;
	
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return dmeReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return dmeReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return dmeReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return dmeReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
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
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return commonReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
	}
	//Analysis Zone
	@Override
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return dmeReportDAO.getAnalysisZoneData(directorateId,analysisReportName,year,month,userName);
	}
	
	//Reports Zone
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String institutionName,String postName,String fileName,String frequency,String date,String userName){
		return dmeReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,institutionName,postName,fileName,frequency,date,userName);
	}
	
	public List<CommonStringList> getInstitutionList(String institutionType){
		return dmeReportDAO.getInstitutionList(institutionType);
	}
	
	public StringBuilder getIdList(String idMethod){
		return dmeReportDAO.getIdList(idMethod);
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
	public List<String> getFileNames(String frequency) {
		return dmeReportDAO.getFileNames(frequency);
	}
	public List<CommonStringList> getInstitutionTypeList(){
		return dmeReportDAO.getInstitutionTypeList();
	}
	public List<CommonStringList> getPostTypeList(){
		return dmeReportDAO.getPostTypeList();
	}
	public List<CommonStringList> getPostList(String postType){
		return dmeReportDAO.getPostList(postType);
	}
}
