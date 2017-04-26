package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ITansacsDataEntryManager {
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName);
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName);
	public JSONArray getAidsDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String isDemographic,String frequency,String week,String quarter,String searchDate,String month,Integer year);
	public boolean insertAidsDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String dataEntryLevel,String frequency,String week,String quarter,String date,String month,Integer year,
			JSONArray gridRecords);
	public boolean updateAidsDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel);
}
