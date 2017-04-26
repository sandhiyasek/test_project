package gov.shdrc.reports.dao;

import java.util.List;

import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDRMGRReportDAO{
	//DASHBOARD ZONE
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
}
