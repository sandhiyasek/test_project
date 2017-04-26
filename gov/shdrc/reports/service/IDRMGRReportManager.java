package gov.shdrc.reports.service;

import java.util.List;

import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDRMGRReportManager {
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory);
	public JSONArray getDRMGRIndicatorList(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,String indicatorCategory,String selectedindicatorName,String indicatorName,int year,String month,String loggedUser);
	
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName);
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	//Indicator Zone
	public List<String> getIndicatorCategories(int directorateId);
	public JSONArray getIndicatorPvtStateData(int directorateId,String category,String fromMonth,int fromYear,String toMonth,int toYear,String username);
	public JSONArray getIndicatorPvtDistrictData(int directorateId,String category,String indicator,String generalCategory,
			String fromMonth,int fromYear,String toMonth,int toYear,String username);
	public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory,String indicator,String generalCategory,
			String district,String fromMonth,int fromYear,String toMonth,int toYear,String userName);
	public List<String> getGeneralCategory(int directorateId,String indicator);
}
