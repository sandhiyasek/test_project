package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IRntcpDataEntryManager {
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName);
	public JSONArray getRntcpDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String searchDate,String month,Integer year);
	public boolean insertRntcpDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String dataEntryLevel,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateRntcpDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel);
	public List<CommonStringList> getDistricts(String userName);
}
