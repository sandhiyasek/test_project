package gov.shdrc.reports.service.impl;

import gov.shdrc.reports.dao.IHSDashboardDAO;
import gov.shdrc.reports.service.IHSDashboardManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HSDashboardManagerImpl implements IHSDashboardManager {
@Autowired(required=true)
IHSDashboardDAO hsDashboardDAO;
	
	public JSONArray getSearchOptionsList(String dashIndName){
		return hsDashboardDAO.getSearchOptionsList(dashIndName);
	}
	public List<String> getChildIndList(String param1){
		return hsDashboardDAO.getChildIndList(param1);
	}
	public List<String> getMonthList(){
		return hsDashboardDAO.getMonthList();
	}
	public   List<String> getDistrictList(){
		return hsDashboardDAO.getDistrictList();
	}
	public List<String> getInstitutionList(){
		return hsDashboardDAO.getInstitutionList();
	}
	public List<String> getYearList(){
		return hsDashboardDAO.getYearList();
	}
	public JSONArray getFourParamIndDetailsList(String year,String district,String month,
		String institution,String indName,int countChildInd,List<String> childIndList){
		return hsDashboardDAO.getFourParamIndDetailsList(year,district,month,
				institution,indName,countChildInd,childIndList);
	}
	public JSONArray getFourParamChildIndDataList(String year,String district,String month,String institution,String indName){
		return hsDashboardDAO.getFourParamChildIndDataList(year,district,month,institution,indName);
	}
	public JSONArray getYMDThreeParamIndDetailsList(String year,String month,String district,String indName,int countChildInd,List<String>childIndList){
		return hsDashboardDAO.getYMDThreeParamIndDetailsList(year,month,district,indName,countChildInd,childIndList);
	}
	public JSONArray getYDIThreeParamIndDetailsList(String year,String district,String institution,String indName,int countChildInd,List<String>childIndList){
		return hsDashboardDAO.getYDIThreeParamIndDetailsList(year,district,institution,indName,countChildInd,childIndList);
	}
	public JSONArray getYMIThreeParamIndDetailsList(String year,String month,String institution,String indName,int countChildInd,List<String>childIndList){
		return hsDashboardDAO.getYMIThreeParamIndDetailsList(year,month,institution,indName,countChildInd,childIndList);
	}
	public JSONArray getYMDThreeParamChildIndDataList(String year,String month,String district,String indName){
		return hsDashboardDAO.getYMDThreeParamChildIndDataList(year,month,district,indName);
	}
	public JSONArray getYDIThreeParamChildIndDataList(String year,String district,String institution,String indName){
		return hsDashboardDAO.getYDIThreeParamChildIndDataList(year,district,institution,indName);
	}
	public JSONArray getYMIThreeParamChildIndDataList(String year,String month,String institution,String indName){
		return hsDashboardDAO.getYMIThreeParamChildIndDataList(year,month,institution,indName);
	}
	public JSONArray getYMTwoParamIndDetailsList(String year,String month,String indName,int countChildInd,List<String> childIndList){
		return hsDashboardDAO.getYMTwoParamIndDetailsList(year,month,indName,countChildInd,childIndList);
	}
	public JSONArray getYITwoParamIndDetailsList(String year,String institution,String indName,int countChildInd,List<String> childIndList){
		return hsDashboardDAO.getYITwoParamIndDetailsList(year,institution,indName,countChildInd,childIndList);
	}
	public JSONArray getYDTwoParamIndDetailsList(String year,String district,String indName,int countChildInd,List<String> childIndList){
		return hsDashboardDAO.getYDTwoParamIndDetailsList(year,district,indName,countChildInd,childIndList);
	}
	public JSONArray getYMTwoParamChildIndDataList(String year,String month,String indName){
		return hsDashboardDAO.getYMTwoParamChildIndDataList(year,month,indName);
	}
	public JSONArray getYDTwoParamChildIndDataList(String year,String district,String indName){
		return hsDashboardDAO.getYDTwoParamChildIndDataList(year,district,indName);
	}
	public JSONArray getYITwoParamChildIndDataList(String year,String institution,String indName){
		return hsDashboardDAO.getYITwoParamChildIndDataList(year,institution,indName);
	}
	public JSONArray getOneParamIndDetailsList(String year,String indName,int countChildInd,List<String> childIndList){
		return hsDashboardDAO.getOneParamIndDetailsList(year,indName,countChildInd,childIndList);
	}
	public JSONArray getOneParamChildIndDataList(String year,String indName){
		return hsDashboardDAO.getOneParamChildIndDataList(year,indName);
	}
	public JSONArray getProjectedValues(String year,String district,String indName){
		return hsDashboardDAO.getProjectedValues(year,district,indName);
	}
	public JSONArray getCumulativeProjectedValues(String indName){
		return hsDashboardDAO.getCumulativeProjectedValues(indName);
	}
	public CommonStringList getIndMaxMonthAndYear(String indName){
		return hsDashboardDAO.getIndMaxMonthAndYear(indName);
	}

}
