package gov.shdrc.reports.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IHSDashboardManager {
	public JSONArray getSearchOptionsList(String dashIndName);
	public List<String> getChildIndList(String param1);
	public List<String> getMonthList();
	public   List<String> getDistrictList();
	public List<String> getInstitutionList();
	public List<String> getYearList();
	public JSONArray getFourParamIndDetailsList(String year,String district,String month,
		String institution,String indName,int countChildInd,List<String> childIndList);
	public JSONArray getFourParamChildIndDataList(String year,String district,String month,String institution,String indName);
	public JSONArray getYMDThreeParamIndDetailsList(String year,String month,String district,String indName,int countChildInd,List<String>childIndList);
	public JSONArray getYDIThreeParamIndDetailsList(String year,String district,String institution,String indName,int countChildInd,List<String>childIndList);
	public JSONArray getYMIThreeParamIndDetailsList(String year,String month,String institution,String indName,int countChildInd,List<String>childIndList);
	public JSONArray getYMDThreeParamChildIndDataList(String year,String month,String district,String indName);
	public JSONArray getYDIThreeParamChildIndDataList(String year,String district,String institution,String indName);
	public JSONArray getYMIThreeParamChildIndDataList(String year,String month,String institution,String indName);
	public JSONArray getYMTwoParamIndDetailsList(String year,String month,String indName,int countChildInd,List<String> childIndList);
	public JSONArray getYITwoParamIndDetailsList(String year,String institution,String indName,int countChildInd,List<String> childIndList);
	public JSONArray getYDTwoParamIndDetailsList(String year,String district,String indName,int countChildInd,List<String> childIndList);
	public JSONArray getYMTwoParamChildIndDataList(String year,String month,String indName);
	public JSONArray getYDTwoParamChildIndDataList(String year,String district,String indName);
	public JSONArray getYITwoParamChildIndDataList(String year,String institution,String indName);
	public JSONArray getOneParamIndDetailsList(String year,String indName,int countChildInd,List<String> childIndList);
	public JSONArray getOneParamChildIndDataList(String year,String indName);
	public JSONArray getProjectedValues(String year,String district,String indName);
	public JSONArray getCumulativeProjectedValues(String indName);
	public CommonStringList getIndMaxMonthAndYear(String indName);
}
