package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDrmgrDataEntryManager {
	public JSONArray getDrMgrDirectorateRecords(Integer departmentId,String classificationName,String frequency,String quarter,String month,Integer year);
	public boolean insertDrMgrDirectorateRecords(Integer departmentId,String classificationName,String frequency,String quarter,String month,
			Integer year,JSONArray gridRecords,String dataEntryLevel);
	public boolean updateDrMgrDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel);
	public List<CommonStringList> getDepartmentList(String userName);
}
