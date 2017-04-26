package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDphDataEntryManager {
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName);
	public List<CommonStringList> getProgramList();
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName);
	public JSONArray getDphDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String programName,String frequency,String week,String quarter,String searchDate,String month,Integer year);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic);
	public boolean insertDphDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String programName,String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateDphDirectorateRecords(JSONArray jsonArray);
}
