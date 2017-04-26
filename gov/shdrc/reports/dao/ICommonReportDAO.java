package gov.shdrc.reports.dao;

import java.util.List;

import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ICommonReportDAO{	
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory);
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
	public JSONArray getGovtHospitalData(int directorateId,String indicatorCategory,int year,String month,String userName);
	public CommonStringList getIndicatorIntMaxMonthAndYear(int directorateId);
	public CommonStringList getIndicatorMaxMonthAndYear(int directorateId,String indicatorCategory);
}