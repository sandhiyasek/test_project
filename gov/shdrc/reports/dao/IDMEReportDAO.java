package gov.shdrc.reports.dao;

import gov.shdrc.util.CommonStringList;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDMEReportDAO{	
	//Analysis Zone
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName);
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,
			String institutionName,String postName,String fileName,String frequency,String date,String userName);
	public List<CommonStringList> getInstitutionList(String institutionType);
	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year);
	public List getFreeHandZoneIndCategory(int directorateId);
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category);
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName);
	public StringBuilder getIdList(String idMethod);
	public List<String> getFileNames(String frequency);
	public List<CommonStringList> getInstitutionTypeList();
	public List<CommonStringList> getPostTypeList();
	public List<CommonStringList> getPostList(String postType);
}
