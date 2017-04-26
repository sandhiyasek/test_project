package gov.shdrc.dataentry.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IMrbDataEntryManager {
	public JSONArray getMrbDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public boolean insertMrbDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateMrbDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel);
}
