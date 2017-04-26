package gov.shdrc.reports.service.impl;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.dao.IRNTCPReportDAO;
import gov.shdrc.reports.service.IRNTCPReportManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RNTCPReportManagerImpl implements IRNTCPReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;
	@Autowired
	IRNTCPReportDAO rntcpReportDAO;

	@Override
	public JSONArray getRNTCPIndicatorList(int directorateId,
			String indicatorCategory, int year, String month,String indQuarter, String loggedUser) {		
		return rntcpReportDAO.getRNTCPIndicatorList(directorateId, indicatorCategory, year, month,indQuarter, loggedUser);
	}

	@Override
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,
			String indicatorCategory, String indicatorName, int year,
			String month,String indQuarter,  String loggedUser) {		
		return rntcpReportDAO.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, year, month,indQuarter,loggedUser);
	}

	@Override
	public JSONArray getInstitutionwiseIndicaotrDetails(int directorateID,
			String indicatorCategory, String indicatorName,
			String districtName, int year, String month,String indQuarter,String loggedUser) {
		
		return rntcpReportDAO.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, districtName, year, month,indQuarter,loggedUser);
	}
	
	//Analysis Zone
	@Override
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName,String quarter){
		return rntcpReportDAO.getAnalysisZoneData(directorateId,analysisReportName,year,month,userName,quarter);
	}
	
	@Override
	public JSONArray getAnalysisZoneGeoMapData(Integer directorateId,String analysisReportName,Integer year,String month,String userName,String quarter){
		return rntcpReportDAO.getAnalysisZoneGeoMapData(directorateId,analysisReportName,year,month,userName,quarter);
	}
	
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName,String districtName){
		return rntcpReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName,districtName);
	}
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return rntcpReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
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
		return rntcpReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return rntcpReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return rntcpReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return rntcpReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
}
