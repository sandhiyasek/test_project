package gov.shdrc.reports.service.impl;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.shdrc.reports.dao.IHSDashboardDAO;
import gov.shdrc.reports.dao.IPredictiveZoneDAO;
import gov.shdrc.reports.service.IPredictiveZoneManager;

@Service
public class PredictiveZoneManagerImpl implements IPredictiveZoneManager{

	@Autowired
	IPredictiveZoneDAO predictiveZoneDAO;
	@Autowired(required=true)
	IHSDashboardDAO hsDashboardDAO;
	public List<String> getDistrictList() {
		return predictiveZoneDAO.getDistrictList();
	}
	//IMR1
	public JSONArray getIMRHeatMapTopLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		return predictiveZoneDAO.getIMRHeatMapTopLeftData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
	}

	public JSONArray getIMRHeatMapTopRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		return predictiveZoneDAO.getIMRHeatMapTopRightData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
	}

	public JSONArray getIMRHeatMapBottomLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		return predictiveZoneDAO.getIMRHeatMapBottomLeftData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
	}

	public JSONArray getIMRHeatMapBottomRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName) {
		return predictiveZoneDAO.getIMRHeatMapBottomRightData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
	}
	//IMR2
	public JSONArray getIMR2HeatMapTopLeftData(String indicator,
			String district, String userName,int cluster) {
		return predictiveZoneDAO.getIMR2HeatMapTopLeftData(indicator, district, userName,cluster);
	}
	public JSONArray getIMR2HeatMapTopRightData(String indicator,
			String district, String userName,int cluster) {
		return predictiveZoneDAO.getIMR2HeatMapTopRightData(indicator, district, userName,cluster);
	}
	public JSONArray getIMR2HeatMapBottomLeftData(String indicator,
			String district, String userName,int cluster) {
		return predictiveZoneDAO.getIMR2HeatMapBottomLeftData(indicator, district, userName,cluster);
	}
	//DOB
	public List<String> getDiseaseList() {
		return predictiveZoneDAO.getDiseaseList();
	}
	public JSONArray getDOBTopLeftData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		return predictiveZoneDAO.getDOBTopLeftData(indicator, fromMonth, fromYear, toMonth, toYear, district, disease, userName);
	}
	public JSONArray getDOBTopRightData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		return predictiveZoneDAO.getDOBTopRightData(indicator, fromMonth, fromYear, toMonth, toYear, district, disease, userName);
	}
	public JSONArray getDOBBottomLeftData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		return predictiveZoneDAO.getDOBBottomLeftData(indicator, fromMonth, fromYear, toMonth, toYear, district, disease, userName);
	}
	public JSONArray getDOBBottomRightData(String indicator, String fromMonth,
			int fromYear, String toMonth, int toYear, String district,
			String disease, String userName) {
		return predictiveZoneDAO.getDOBBottomRightData(indicator, fromMonth, fromYear, toMonth, toYear, district, disease, userName);
	}
	//HOB
	public JSONArray getHOBHeatMapTopLeftData(String indicator,
			String maxMonth, int maxYear, String district, String instType,
			String userName) {
		return predictiveZoneDAO.getHOBHeatMapTopLeftData(indicator, maxMonth, maxYear, district, instType, userName);
	}
	public JSONArray getHOBHeatMapTopRightData(String indicator,
			String maxMonth, int maxYear, String district, String instType,
			String userName) {
		return predictiveZoneDAO.getHOBHeatMapTopRightData(indicator, maxMonth, maxYear, district, instType, userName);
	}
	public JSONArray getHOBHeatMapBottomData(String indicator, String maxMonth,
			int maxYear, String district, String instType, String userName) {
		return predictiveZoneDAO.getHOBHeatMapBottomData(indicator, maxMonth, maxYear, district, instType, userName);
	}
	//BBSI
	public JSONArray getBBSIHeatMapTopLeftData(String indicator, String date,
			String instType, String district, String userName) {
		return predictiveZoneDAO.getBBSIHeatMapTopLeftData(indicator, date, instType, district, userName);
	}
	public JSONArray getBBSIHeatMapTopRightData(String indicator, String date,
			String instType, String district, String userName) {
		return predictiveZoneDAO.getBBSIHeatMapTopRightData(indicator, date, instType, district, userName);
	}
	public JSONArray getBBSIHeatMapBottomLeftData(String indicator,
			String date, String instType, String district, String userName) {
		return predictiveZoneDAO.getBBSIHeatMapBottomLeftData(indicator, date, instType, district, userName);
	}
	//BBS
	public JSONArray getBBSTopLeftData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		return predictiveZoneDAO.getBBSTopLeftData(indicator, date, instType, indCategory, district, userName);
	}
	public JSONArray getBBSTopRightData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		return predictiveZoneDAO.getBBSTopRightData(indicator, date, instType, indCategory, district, userName);
	}
	public JSONArray getBBSBottomLeftData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		return predictiveZoneDAO.getBBSBottomLeftData(indicator, date, instType, indCategory, district, userName);
	}
	public JSONArray getBBSBottomRightData(String indicator, String date,
			String instType, String indCategory, String district,
			String userName) {
		return predictiveZoneDAO.getBBSBottomRightData(indicator, date, instType, indCategory, district, userName);
	}
	public List<String> getIndCategoryList() {
		return predictiveZoneDAO.getIndCategoryList();
	}
	//FIP
	public JSONArray getFIPTopLeftData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getFIPTopLeftData(indicator, year, district, userName);
	}
	public JSONArray getFIPTopRightData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getFIPTopRightData(indicator, year, district, userName);
	}
	public JSONArray getFIPBottomLeftData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getFIPBottomLeftData(indicator, year, district, userName);
	}
	public JSONArray getFIPBottomRightData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getFIPBottomRightData(indicator, year, district, userName);
	}
	public List<String> getFIPYearList() {
		return predictiveZoneDAO.getFIPYearList();
	}
	//CDD
	public List<String> getFileTypeList() {
		return predictiveZoneDAO.getFileTypeList();
	}
	public List<String> getReasonList(String fileType) {
		return predictiveZoneDAO.getReasonList(fileType);
	}
	public JSONArray getCDDTopLeftData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		return predictiveZoneDAO.getCDDTopLeftData(indicator, year, month, fileType, district, reason, instType, userName);
	}
	public JSONArray getCDDTopRightData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		return predictiveZoneDAO.getCDDTopRightData(indicator, year, month, fileType, district, reason, instType, userName);
	}
	public JSONArray getCDDBottomLeftData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		return predictiveZoneDAO.getCDDBottomLeftData(indicator, year, month, fileType, district, reason, instType, userName);
	}
	public JSONArray getCDDBottomRightData(String indicator, int year,
			String month, String fileType, String district, String reason,
			String instType, String userName) {
		return predictiveZoneDAO.getCDDBottomRightData(indicator, year, month, fileType, district, reason, instType, userName);
	}
	//CSP
	public JSONArray getCSPTopLeftData(String indicator, int year,
			String month, String district, String userName) {
		return predictiveZoneDAO.getCSPTopLeftData(indicator, year, month, district, userName);
	}
	public JSONArray getCSPTopRightData(String indicator, int year,
			String month, String district, String userName) {
		return predictiveZoneDAO.getCSPTopRightData(indicator, year, month, district, userName);
	}
	public JSONArray getCSPBottomLeftData(String indicator, int year,
			String month, String district, String userName) {
		return predictiveZoneDAO.getCSPBottomLeftData(indicator, year, month, district, userName);
	}
	public JSONArray getCSPBottomRightData(String indicator, int year,
			String month, String district, String userName) {
		return predictiveZoneDAO.getCSPBottomRightData(indicator, year, month, district, userName);
	}
	
	//CPP
	public JSONArray getCPPTopLeftData(String indicator, String instType,
			String district, String speciality, String userName) {
		return predictiveZoneDAO.getCPPTopLeftData(indicator, instType, district, speciality, userName);
	}
	public JSONArray getCPPTopRightData(String indicator, String instType,
			String district, String speciality, String userName) {
		return predictiveZoneDAO.getCPPTopRightData(indicator, instType, district, speciality, userName);
	}
	public JSONArray getCPPBottomLeftData(String indicator, String instType,
			String district, String speciality, String userName) {
		return predictiveZoneDAO.getCPPBottomLeftData(indicator, instType, district, speciality, userName);
	}
	public JSONArray getCPPBottomRightData(String indicator, String instType,
			String district, String speciality, String userName) {
		return predictiveZoneDAO.getCPPBottomRightData(indicator, instType, district, speciality, userName);
	}
	public List<String> getSpecialityList() {
		return predictiveZoneDAO.getSpecialityList();
	}
	//CNP
	public JSONArray getCNPTopLeftData(String indicator, int year,
			String month, String district, String instType, String userName) {
		return predictiveZoneDAO.getCNPTopLeftData(indicator, year, month, district, instType, userName);
	}
	public JSONArray getCNPTopRightData(String indicator, int year,
			String month, String district, String instType, String userName) {
		return predictiveZoneDAO.getCNPTopRightData(indicator, year, month, district, instType, userName);
	}
	public JSONArray getCNPBottomLeftData(String indicator, int year,
			String month, String district, String instType, String userName) {
		return predictiveZoneDAO.getCNPBottomLeftData(indicator, year, month, district, instType, userName);
	}
	public JSONArray getCNPBottomRightData(String indicator, int year,
			String month, String district, String instType, String userName) {
		return predictiveZoneDAO.getCNPBottomRightData(indicator, year, month, district, instType, userName);
	}
	//HS
	public JSONArray getTopLeftData(String year, String district, String indName) {
		return hsDashboardDAO.getProjectedValues(year, district, indName);
	}
	public JSONArray getTopRightData(String year, String district, String indName) {
		return hsDashboardDAO.getProjectedValues(year, district, indName);
	}
	public JSONArray getBottomLeftData(String year, String district, String indName) {
		return hsDashboardDAO.getProjectedValues(year, district, indName);
	}
	public JSONArray getBottomRightData(String year, String district, String indName) {
		return hsDashboardDAO.getProjectedValues(year, district, indName);
	}
	//MMR Insides
	public JSONArray getMMRITopLeftData(String indicator,int year,String month,
			String userName) {
		return predictiveZoneDAO.getMMRITopLeftData(indicator,year,month,userName);
	}
	public JSONArray getMMRITopRightData(String indicator,int year, String userName) {
		return predictiveZoneDAO.getMMRITopRightData(indicator,year, userName);
	}
	public JSONArray getMMRIBottomLeftData(String indicator, String district,
			String userName) {
		return predictiveZoneDAO.getMMRIBottomLeftData(indicator, district, userName);
	}
	// MMR Operational
	public List<String> getReasonableIndList() {
		return predictiveZoneDAO.getReasonableIndList();
	}
	public JSONArray getMMROTopLeftData(String indicator, int year,
			String month, String userName) {
		return predictiveZoneDAO.getMMROTopLeftData(indicator, year, month, userName);
	}
	public JSONArray getMMROTopRightData(String indicator, int year,
			String month, String district, String userName) {
		return predictiveZoneDAO.getMMROTopRightData(indicator, year, month, district, userName);
	}
	public JSONArray getMMROBottomLeftData(String indicator,
			String reasonableInd, int year, String month, String userName) {
		return predictiveZoneDAO.getMMROBottomLeftData(indicator, reasonableInd, year, month, userName);
	}
	public JSONArray getMMROBottomRightData(String indicator, String userName) {
		return predictiveZoneDAO.getMMROBottomRightData(indicator, userName);
	}
	//Measles
	public JSONArray getMeaslesTopLeftData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getMeaslesTopLeftData(indicator, year, district, userName);
	}
	public JSONArray getMeaslesTopRightData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getMeaslesTopRightData(indicator, year, district, userName);
	}
	public JSONArray getMeaslesBottomLeftData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getMeaslesBottomLeftData(indicator, year, district, userName);
	}
	public JSONArray getMeaslesBottomRightData(String indicator, String year,
			String district, String userName) {
		return predictiveZoneDAO.getMeaslesBottomRightData(indicator, year, district, userName);
	}
}
