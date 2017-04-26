package gov.shdrc.reports.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface IDMSReportManager {
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String district, String userName);
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory);
	public List<CommonStringList> getDistrictList(String userName);
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String ind,Integer year,String month,String userName);
	public JSONArray getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	public JSONArray getIndicatorList(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,String indicatorCategory,String indicatorName,int year,String month,String loggedUser);
	public JSONArray getInstitutionwiseIndicaotrDetails(int directorateID,String indicatorCategory,String indicatorName,String districtName,int year,String month,String loggedUser);
	//Indicator Zone
	public List<String> getIndicatorCategories(int directorateId);
	public JSONArray getIndicatorPvtStateData(int directorateId,String category,String fromMonth,int fromYear,String toMonth,int toYear,String username);
	public JSONArray getIndicatorPvtDistrictData(int directorateId,String category,String indicator,String generalCategory,
			String fromMonth,int fromYear,String toMonth,int toYear,String username);
	public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory,String indicator,String generalCategory,
			String district,String fromMonth,int fromYear,String toMonth,int toYear,String userName);
	public List<String> getGeneralCategory(int directorateId,String indicator);
	//FreeHand Zone
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);
}
