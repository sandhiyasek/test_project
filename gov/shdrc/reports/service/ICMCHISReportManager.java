/**
 * 
 */
package gov.shdrc.reports.service;

import java.util.List;

import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Upendra G
 *
 * 
 */
@Service
public interface ICMCHISReportManager {
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory);
	public JSONArray getCMCHISIndicatorList(int directorateId,String indicatorCategory,int year,String month,String loggedUser);
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,String indicatorCategory,String indicatorName,int year,String month,String loggedUser);
	public JSONArray getInstitutionwiseIndicaotrDetails(int directorateID,String indicatorCategory,String indicatorName,String districtName,int year,String month,String loggedUser);
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);
	
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,/*Integer fromYear,String fromMonth,Integer toYear,String toMonth,*/String userName);
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	public JSONObject getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);

	//Indicator Zone
	public JSONArray getIndicatorPvtStateData(int directorateId,String indicatorCategory,int year,String month,String userName);
	public JSONArray getIndicatorPvtDistrictData(int directorateId,String indicatorCategory,String indicators,int year,String month,String userName);
	public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory,String indicators,String district,int year,String maxMonth,String userName);
	public JSONArray getGovtHospitalData(int directorateId,String indicatorCategory,int year,String month,String userName);
}
