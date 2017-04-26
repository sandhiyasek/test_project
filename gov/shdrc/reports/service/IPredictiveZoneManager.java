package gov.shdrc.reports.service;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;
@Service
public interface IPredictiveZoneManager {
	public List<String> getDistrictList();
	public List<String> getDiseaseList();
	//IMR1
	public JSONArray getIMRHeatMapTopLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName);
	public JSONArray getIMRHeatMapTopRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName);
	public JSONArray getIMRHeatMapBottomLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName);
	public JSONArray getIMRHeatMapBottomRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String userName);	
	//IMR2
	public JSONArray getIMR2HeatMapTopLeftData(String indicator,String district,String userName,int cluster);
	public JSONArray getIMR2HeatMapTopRightData(String indicator,String district,String userName,int cluster);
	public JSONArray getIMR2HeatMapBottomLeftData(String indicator,String district,String userName,int cluster);
	//DOB
	public JSONArray getDOBTopLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String disease,String userName);
	public JSONArray getDOBTopRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String disease,String userName);
	public JSONArray getDOBBottomLeftData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String disease,String userName);
	public JSONArray getDOBBottomRightData(String indicator,String fromMonth,int fromYear,String toMonth,int toYear,String district,String disease,String userName);
	//HOB
	public JSONArray getHOBHeatMapTopLeftData(String indicator,String maxMonth,int maxYear,String district,String instType,String userName);
	public JSONArray getHOBHeatMapTopRightData(String indicator,String maxMonth,int maxYear,String district,String instType,String userName);
	public JSONArray getHOBHeatMapBottomData(String indicator,String maxMonth,int maxYear,String district,String instType,String userName);
	//BBSI
	public JSONArray getBBSIHeatMapTopLeftData(String indicator,String date,String instType,String district,String userName);
	public JSONArray getBBSIHeatMapTopRightData(String indicator,String date,String instType,String district,String userName);
	public JSONArray getBBSIHeatMapBottomLeftData(String indicator,String date,String instType,String district,String userName);
	//BBS
	public JSONArray getBBSTopLeftData(String indicator,String date,String instType,String indCategory,String district,String userName);
	public JSONArray getBBSTopRightData(String indicator,String date,String instType,String indCategory,String district,String userName);
	public JSONArray getBBSBottomLeftData(String indicator,String date,String instType,String indCategory,String district,String userName);
	public JSONArray getBBSBottomRightData(String indicator,String date,String instType,String indCategory,String district,String userName);
	public List<String> getIndCategoryList();
	//FIP
	public JSONArray getFIPTopLeftData(String indicator,String year,String district,String userName);
	public JSONArray getFIPTopRightData(String indicator,String year,String district,String userName);
	public JSONArray getFIPBottomLeftData(String indicator,String year,String district,String userName);
	public JSONArray getFIPBottomRightData(String indicator,String year,String district,String userName);
	public List<String> getFIPYearList();
	//CDD
	public List<String> getFileTypeList();
	public List<String> getReasonList(String fileType);
	public JSONArray getCDDTopLeftData(String indicator,int year,String month,String fileType,String district,String reason,String instType,String userName);
	public JSONArray getCDDTopRightData(String indicator,int year,String month,String fileType,String district,String reason,String instType,String userName);
	public JSONArray getCDDBottomLeftData(String indicator,int year,String month,String fileType,String district,String reason,String instType,String userName);
	public JSONArray getCDDBottomRightData(String indicator,int year,String month,String fileType,String district,String reason,String instType,String userName);
	//CSP
	public JSONArray getCSPTopLeftData(String indicator,int year,String month,String district,String userName);
	public JSONArray getCSPTopRightData(String indicator,int year,String month,String district,String userName);
	public JSONArray getCSPBottomLeftData(String indicator,int year,String month,String district,String userName);
	public JSONArray getCSPBottomRightData(String indicator,int year,String month,String district,String userName);
	//CPP
	public JSONArray getCPPTopLeftData(String indicator,String instType,String district,String speciality,String userName);
	public JSONArray getCPPTopRightData(String indicator,String instType,String district,String speciality,String userName);
	public JSONArray getCPPBottomLeftData(String indicator,String instType,String district,String speciality,String userName);
	public JSONArray getCPPBottomRightData(String indicator,String instType,String district,String speciality,String userName);
	public List<String> getSpecialityList();
	//CNP
	public JSONArray getCNPTopLeftData(String indicator,int year,String month,String district,String instType,String userName);
	public JSONArray getCNPTopRightData(String indicator,int year,String month,String district,String instType,String userName);
	public JSONArray getCNPBottomLeftData(String indicator,int year,String month,String district,String instType,String userName);
	public JSONArray getCNPBottomRightData(String indicator,int year,String month,String district,String instType,String userName);
	//HS
	public JSONArray getTopLeftData(String year,String district,String indName);
	public JSONArray getTopRightData(String year,String district,String indName);
	public JSONArray getBottomLeftData(String year,String district,String indName);
	public JSONArray getBottomRightData(String year,String district,String indName);
	//MMR Insides
	public JSONArray getMMRITopLeftData(String indicator,int year,String month,String userName);
	public JSONArray getMMRITopRightData(String indicator,int year,String userName);
	public JSONArray getMMRIBottomLeftData(String indicator,String district,String userName);
	//MMR Operational
	public List<String> getReasonableIndList();
	public JSONArray getMMROTopLeftData(String indicator,int year,String month,String userName);
	public JSONArray getMMROTopRightData(String indicator,int year,String month,String district,String userName);
	public JSONArray getMMROBottomLeftData(String indicator,String reasonableInd,int year,String month,String userName);
	public JSONArray getMMROBottomRightData(String indicator,String userName);
	//Measles
	public JSONArray getMeaslesTopLeftData(String indicator,String year,String district,String userName);
	public JSONArray getMeaslesTopRightData(String indicator,String year,String district,String userName);
	public JSONArray getMeaslesBottomLeftData(String indicator,String year,String district,String userName);
	public JSONArray getMeaslesBottomRightData(String indicator,String year,String district,String userName);
}
