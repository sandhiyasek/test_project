package gov.shdrc.reports.dao;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ITANSACSReportDAO{	
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);

	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
}
