package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDmsDataEntryManager {
	public List<CommonStringList> getHospitalList(Integer districtId,String userName);
	public JSONArray getDmsDirectorateRecords(Integer districtId,Integer hospitalId,String classificationName,
			String frequency,String week,String quarter,String searchDate,String month,Integer year);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic);
	public boolean insertDmsDirectorateRecords(Integer districtId,Integer hospitalId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateDmsDirectorateRecords(JSONArray jsonArray);
}
