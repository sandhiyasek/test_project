package gov.shdrc.reports.dao;

import org.json.JSONArray;
import org.json.JSONArray;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IESIReportDAO{	
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,String userName);

	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);
	//Indicator Zone
		public List<String> getIndicatorCategories();
		public JSONArray getIndicatorPvtStateData(int directorateId,String category,String fromMonth,int fromYear,String toMonth,int toYear,String username);
		public JSONArray getIndicatorPvtDistrictData(int directorateId,String category,String indicator,String generalCategory,
				String fromMonth,int fromYear,String toMonth,int toYear,String username);
		public JSONArray getIndicatorPvtInstitutionData(int directorateId,String indicatorCategory,String indicator,String generalCategory,
				String district,String fromMonth,int fromYear,String toMonth,int toYear,String userName);
		public List<String> getGeneralCategory(String indicator);
}
