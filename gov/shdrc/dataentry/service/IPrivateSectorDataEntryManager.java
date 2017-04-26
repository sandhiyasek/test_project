package gov.shdrc.dataentry.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IPrivateSectorDataEntryManager {
	public boolean updatePvtSecDirectorateRecords(JSONArray jsonArray);
	public JSONArray getPvtSecDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel);
	public boolean insertPvtSecDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
}
