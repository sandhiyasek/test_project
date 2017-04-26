package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDmeDataEntryManager {
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName);
	public JSONArray getDmeDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public boolean insertDmeDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateDmeDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,
			String generalNameLevel);
}
