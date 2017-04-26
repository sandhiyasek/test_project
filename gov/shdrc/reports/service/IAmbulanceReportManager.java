package gov.shdrc.reports.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface IAmbulanceReportManager {
	public List<CommonStringList> getOnLoadDistrictList();
	public String getAmbulanceReportMaxDate();
	public List<CommonStringList> getOnLoadEmergencyTypeList();
	public JSONObject getAmbulanceGISReportData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName);
	public JSONArray getAmbulanceHourwiseHeatGridData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName);
	public JSONArray getAmbulanceDaywiseHeatGridData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName);
	public JSONObject getAmbulanceComparisonData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName);
	public List<CommonStringList> getEmergencyTypeList(String frequency,String date,String month,String year);
	public List<CommonStringList> getDistrictList(String frequency,String date,String month,String year,String emergencyType);
	public JSONArray getAmbIncTableData(String maxDate);
	public JSONArray getAmbIncByEmerTypeData(String districtName,String maxDate);
	public JSONObject getHospitalIncData(String districtName,String maxDate);
	public String getAmbulanceCaseReportMaxDate();
	public JSONArray getHospCaseData(String districtName,String maxDate,String hospitalName);
	public JSONArray getHospCaseComparisonData(String districtName,String maxDate,String hospitalName);
	public JSONArray getHospCaseTimingData(String districtName,String maxDate,String hospitalName);
	public JSONObject getHeatMapDataRange(String date,String month,String year,String frequency,String emergencyType,
			String districtName,String methodName);
	public JSONArray getPredictionTalukData(String districtName,String emergencyType);
	public List<CommonStringList> getPredictionDistrictList();
	public List<CommonStringList> getPredictionEmerTypeList();
	public JSONArray getPredictionVillageData(String districtName,String talukName,String emergencyType);
	public JSONArray getChartArray(String methodCall,String districtName);
	public JSONObject getTotalCaseAvailedBarArray(String districtName);
	public JSONArray getTotalTripsData();
	public JSONObject getAmbTotTripsTypeData(String districtName);
	public JSONArray getTalukCaseCountTableData(String districtName);
	public JSONArray getVillageCaseCountTableData(String districtName,String talukName);
	public JSONArray getBaseLocAmbData(String districtName);
	public JSONObject ambulanceCaseType(String districtName);
	public JSONArray getGisBaseData(String districtName);
	public JSONArray getGisPathStatusData(String districtName, String vehicleNo);
	public JSONArray getGPSSteamingDataList(String districtName, String vehicleNo);
	public JSONArray getTotalNoOfAmbList(); 
	public String getOnLoadEmgTypeScrollList(String districtName);
	public String getEmergencyTypeAmbulanceCaseReportMaxDate(String emergencyType);
	public JSONArray getEmergencyTypeAmbIncTableData(String date,String emergencyType);
	public List<CommonStringList> getEmergencyTypeOnLoadDistrictList(String emergencyType);
	public JSONArray getEmergencyTypeAmbIncByEmerTypeData(String districtName,String maxDate,String emergencyType);
	public JSONObject getEmergencyTypeHospitalIncData(String districtName,String maxDate,String emergencyType);
	public List<CommonStringList> getEmergencyTypePredictionDistrictList(String emergencyType);
	public JSONArray getGisTotalCasesAvailedData(String districtName);
	public List<CommonStringList> getDirecorateList(boolean isAllDirectorate,String role);
	public JSONArray getAmbBaseLocTickerData();
	public String getTalukTickerData(String districtName);
	public String getVillageTickerData(String districtName);
	public JSONArray getPredictionScrollData();
	public JSONArray getCycleTimeData(); 
	public JSONArray getCycleTimeDataStack(String districtName, String buttonClick);
	public JSONArray getCycleTimeDataPie(String districtName);
	public JSONArray getKilometersCoveredList();
	public JSONArray getKilometersCoveredAmbData(String districtName, String hours);
	public JSONArray getAmbKilometersCoveredData(String districtName);
	public JSONArray getKilometersCoveredDrilldown();
	public JSONObject getKmCoveredMultiSeriesData(String districtName,String buttonClick);
	public JSONArray getkilometersCoveredAmbulanceData(String districtName, String ambNo, int kms, String mins);
	public JSONObject getCycleTimeComparisonData(String districtName, String buttonClick);
	public String getOnLoadDistScrollList();
	public JSONArray getTnegaDistrictName();
	public JSONArray getEmriDistricltList();
	public JSONArray getEmriTaluktList(String disId);
	public JSONArray getVehicleBaseLoc(String districtName,String vehicleNo);
}
