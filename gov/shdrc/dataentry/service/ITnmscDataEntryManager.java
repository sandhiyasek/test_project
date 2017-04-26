package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ITnmscDataEntryManager {
	public List<CommonStringList> getInstitutionList();
	public JSONArray getTnmscDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic);
	public boolean insertTnmscDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateTnmscDirectorateRecords(JSONArray jsonArray);
}
