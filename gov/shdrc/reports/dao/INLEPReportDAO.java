package gov.shdrc.reports.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface INLEPReportDAO{	

	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	public JSONObject getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName);
	//Indicator Zone
	public List<String> getIndicatorCategories();
	public JSONArray getIndicatorPvtStateData(int directorateId,String category,String fromMonth,int fromYear,String toMonth,int toYear,String username);
	public JSONArray getIndicatorPvtDistrictData(int directorateId,String category,String indicator,String generalCategory,String fromMonth,int fromYear,String toMonth,int toYear,String username);
	public List<String> getGeneralCategory(String indicator);
	
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);
	public JSONArray getNLEPBCPDashboardData(int directorateId,String dashBoardType,int year,String month,String userName);
	public JSONArray getNLEPBCPDashboardDistrictData(int directorateId,String dashBoardType,int year,String month,String userName);
}
