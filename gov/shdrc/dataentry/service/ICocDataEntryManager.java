package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ICocDataEntryManager {
	public List<CommonStringList> getInstitutionList(String region,String userName);
	public JSONArray getCocDirectorateRecords(String regionName,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public boolean insertCocDirectorateRecords(String regionName,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateCocDirectorateRecords(JSONArray jsonArray);
	public List<CommonStringList> getRegions(String userName);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel);
}
