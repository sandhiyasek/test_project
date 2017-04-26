package gov.shdrc.reports.service.impl;

import java.util.List;

import gov.shdrc.reports.dao.IAmbulanceReportDAO;
import gov.shdrc.reports.service.IAmbulanceReportManager;
import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmbulanceReportManagerImpl implements IAmbulanceReportManager{
	@Autowired
	IAmbulanceReportDAO ambulanceReportDAO;
	
	@Override
	public List<CommonStringList> getOnLoadDistrictList() {
		return ambulanceReportDAO.getOnLoadDistrictList();
	}
	public String getAmbulanceReportMaxDate(){
		return ambulanceReportDAO.getAmbulanceReportMaxDate();
	}
	public List<CommonStringList> getOnLoadEmergencyTypeList(){
		return ambulanceReportDAO.getOnLoadEmergencyTypeList();
	}
	public JSONObject getAmbulanceGISReportData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName){
		return ambulanceReportDAO.getAmbulanceGISReportData(date,month,year,frequency,emergencyType,districtName,methodName);
	}
	public JSONArray getAmbulanceHourwiseHeatGridData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName){
		return ambulanceReportDAO.getAmbulanceHourwiseHeatGridData(date,month,year,frequency,emergencyType,districtName,methodName);
	}
	public JSONArray getAmbulanceDaywiseHeatGridData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName){
		return ambulanceReportDAO.getAmbulanceDaywiseHeatGridData(date,month,year,frequency,emergencyType,districtName,methodName);
	}
	public JSONObject getAmbulanceComparisonData(String date,String month,String year,String frequency,String emergencyType,String districtName,String methodName){
		return ambulanceReportDAO.getAmbulanceComparisonData(date,month,year,frequency,emergencyType,districtName,methodName);
	}
	public List<CommonStringList> getEmergencyTypeList(String frequency,String date,String month,String year){
		return ambulanceReportDAO.getEmergencyTypeList(frequency,date,month,year);
	}
	public List<CommonStringList> getDistrictList(String frequency,String date,String month,String year,String emergencyType){
		return ambulanceReportDAO.getDistrictList(frequency,date,month,year,emergencyType);
	}
	public JSONArray getAmbIncTableData(String maxDate){
		return ambulanceReportDAO.getAmbIncTableData(maxDate);
	}
	public JSONArray getAmbIncByEmerTypeData(String districtName,String maxDate){
		return ambulanceReportDAO.getAmbIncByEmerTypeData(districtName,maxDate);
	}
	public JSONObject getHospitalIncData(String districtName,String maxDate){
		return ambulanceReportDAO.getHospitalIncData(districtName,maxDate);
	}
	public String getAmbulanceCaseReportMaxDate(){
		return ambulanceReportDAO.getAmbulanceCaseReportMaxDate();
	}
	public JSONArray getHospCaseData(String districtName,String maxDate,String hospitalName){
		return ambulanceReportDAO.getHospCaseData(districtName,maxDate,hospitalName);
	}
	public JSONArray getHospCaseComparisonData(String districtName,String maxDate,String hospitalName){
		return ambulanceReportDAO.getHospCaseComparisonData(districtName,maxDate,hospitalName);
	}
	public JSONArray getHospCaseTimingData(String districtName,String maxDate,String hospitalName){
		return ambulanceReportDAO.getHospCaseTimingData(districtName,maxDate,hospitalName);
	}
	public JSONObject getHeatMapDataRange(String date,String month,String year,String frequency,String emergencyType,
			String districtName,String methodName){
		return ambulanceReportDAO.getHeatMapDataRange(date,month,year,frequency,emergencyType,districtName,methodName);
	}
	public JSONArray getPredictionTalukData(String districtName,String emergencyType){
		return ambulanceReportDAO.getPredictionTalukData(districtName,emergencyType);
	}
	public List<CommonStringList> getPredictionDistrictList(){
		return ambulanceReportDAO.getPredictionDistrictList();
	}
	public List<CommonStringList> getPredictionEmerTypeList(){
		return ambulanceReportDAO.getPredictionEmerTypeList();
	}
	public JSONArray getPredictionVillageData(String districtName,String talukName,String emergencyType){
		return ambulanceReportDAO.getPredictionVillageData(districtName,talukName,emergencyType);
	}
	public JSONArray getChartArray(String methodCall,String districtName) {
		return ambulanceReportDAO.getChartArray(methodCall,districtName);
	}
	public JSONObject getTotalCaseAvailedBarArray(String districtName){
		return ambulanceReportDAO.getTotalCaseAvailedBarArray(districtName);
	}
	public JSONArray getTotalTripsData() {
		return ambulanceReportDAO.getTotalTripsData();
	}
	public JSONObject getAmbTotTripsTypeData(String districtName) {
		return ambulanceReportDAO.getAmbTotTripsTypeData(districtName);
	}
	public JSONArray getTalukCaseCountTableData(String districtName){
		return ambulanceReportDAO.getTalukCaseCountTableData(districtName);
	}
	public JSONArray getVillageCaseCountTableData(String districtName,String talukName){
		return ambulanceReportDAO.getVillageCaseCountTableData(districtName,talukName);
	}
	public JSONArray getBaseLocAmbData(String districtName) {
		return ambulanceReportDAO.getBaseLocAmbData(districtName);
	}
	public JSONObject ambulanceCaseType(String districtName) {
		return ambulanceReportDAO.ambulanceCaseType(districtName);
	}
	public JSONArray getGisBaseData(String districtName) {
		return ambulanceReportDAO.getGisBaseData(districtName);
	}
	public JSONArray getGisPathStatusData(String districtName, String vehicleNo) {
		return ambulanceReportDAO.getGisPathStatusData(districtName,vehicleNo);
	}
	public JSONArray getGPSSteamingDataList(String districtName,
			String vehicleNo) {
		return ambulanceReportDAO.getGPSSteamingDataList(districtName,vehicleNo);
	}
	public JSONArray getTotalNoOfAmbList(){
		return ambulanceReportDAO.getTotalNoOfAmbList(); 
	}
	public String getOnLoadEmgTypeScrollList(String districtName){
		return ambulanceReportDAO.getOnLoadEmgTypeScrollList(districtName);
	}
	public String getEmergencyTypeAmbulanceCaseReportMaxDate(String emergencyType){
		return ambulanceReportDAO.getEmergencyTypeAmbulanceCaseReportMaxDate(emergencyType);
	}
	public JSONArray getEmergencyTypeAmbIncTableData(String maxDate,String emergencyType){
		return ambulanceReportDAO.getEmergencyTypeAmbIncTableData(maxDate,emergencyType);
	}
	public List<CommonStringList> getEmergencyTypeOnLoadDistrictList(String emergencyType) {
		return ambulanceReportDAO.getEmergencyTypeOnLoadDistrictList(emergencyType);
	}
	public JSONArray getEmergencyTypeAmbIncByEmerTypeData(String districtName,String maxDate,String emergencyType){
		return ambulanceReportDAO.getEmergencyTypeAmbIncByEmerTypeData(districtName,maxDate,emergencyType);
	}
	public JSONObject getEmergencyTypeHospitalIncData(String districtName,String maxDate,String emergencyType){
		return ambulanceReportDAO.getEmergencyTypeHospitalIncData(districtName,maxDate,emergencyType);
	}
	public List<CommonStringList> getEmergencyTypePredictionDistrictList(String emergencyType){
		return ambulanceReportDAO.getEmergencyTypePredictionDistrictList(emergencyType);
	}
	public JSONArray getGisTotalCasesAvailedData(String districtName){
		return ambulanceReportDAO.getGisTotalCasesAvailedData(districtName);
	}
	public List<CommonStringList> getDirecorateList(boolean isAllDirectorate,String role){
		return ambulanceReportDAO.getDirecorateList(isAllDirectorate,role);
	}
	public JSONArray getAmbBaseLocTickerData(){
		return ambulanceReportDAO.getAmbBaseLocTickerData();
	}
	public String getTalukTickerData(String districtName){
		return ambulanceReportDAO.getTalukTickerData(districtName);
	}
	public String getVillageTickerData(String districtName){
		return ambulanceReportDAO.getVillageTickerData(districtName);
	}
	public JSONArray getPredictionScrollData(){
		return ambulanceReportDAO.getPredictionScrollData();
	}
	public JSONArray getCycleTimeData(){
		return ambulanceReportDAO.getCycleTimeData(); 
	}
	public JSONArray getCycleTimeDataPie(String districtName){
		return ambulanceReportDAO.getCycleTimeDataPie(districtName); 
	}
	public JSONArray getCycleTimeDataStack(String districtName, String buttonClick){
		return ambulanceReportDAO.getCycleTimeDataStack(districtName,buttonClick); 
	}
	public JSONArray getKilometersCoveredList() {
		return ambulanceReportDAO.getKilometersCoveredList();
	}
	public JSONArray getKilometersCoveredAmbData(String districtName, String hours) {
		return ambulanceReportDAO.getKilometersCoveredAmbData(districtName,hours);
	}
	public JSONArray getAmbKilometersCoveredData(String districtName) {
		return ambulanceReportDAO.getAmbKilometersCoveredData(districtName);
	}
	public JSONArray getKilometersCoveredDrilldown() {
		return ambulanceReportDAO.getKilometersCoveredDrilldown() ;
	}
	public JSONObject getKmCoveredMultiSeriesData(String districtName, String buttonClick){
		return ambulanceReportDAO.getKmCoveredMultiSeriesData(districtName,buttonClick);
	}
	public JSONArray getkilometersCoveredAmbulanceData(String districtName, String ambNo, int kms, String mins) {
		return ambulanceReportDAO.getkilometersCoveredAmbulanceData(districtName,ambNo,kms,mins);

	}
	public JSONObject getCycleTimeComparisonData(String districtName,String buttonClick){
		return ambulanceReportDAO.getCycleTimeComparisonData(districtName,buttonClick);
	}
	public String getOnLoadDistScrollList(){
		return ambulanceReportDAO.getOnLoadDistScrollList();
	}
	public JSONArray getTnegaDistrictName() {
		return ambulanceReportDAO.getTnegaDistrictName();
	}
	
	public JSONArray getEmriDistricltList() {
		return ambulanceReportDAO.getEmriDistricltList();
	}
	public JSONArray getEmriTaluktList(String disId) {
		return ambulanceReportDAO.getEmriTaluktList(disId);
	}
	public JSONArray getVehicleBaseLoc(String districtName,String vehicleNo){
		return ambulanceReportDAO.getVehicleBaseLoc(districtName,vehicleNo);
	}
}
