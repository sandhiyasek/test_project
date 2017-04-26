package gov.shdrc.dataentry.dao;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface INlepDataEntryDAO {
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName);
	public JSONArray getNlepDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String isDemographic,String frequency,String week,
			String quarter,String searchDate,String month,Integer year);
	public boolean insertNlepDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateNlepDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel);
	
}
