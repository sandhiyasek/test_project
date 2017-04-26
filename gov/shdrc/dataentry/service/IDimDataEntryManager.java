package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDimDataEntryManager {
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String system,String userName);
	public List<CommonStringList> getSytem();
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String system,String userName);
	public JSONArray getDimDirectorateRecords(Integer districtId,String institutionType,String system,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String searchDate,String month,Integer year);
	public boolean insertDimDirectorateRecords(Integer districtId,String institutionType,String system,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateDimDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel,String system);
}
