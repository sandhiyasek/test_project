package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDfsDataEntryManager {
	public JSONArray getDfsDirectorateRecords(Integer districtId,Integer labId,Integer labDistrictId,String areaType,String area,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year,String dataEntryLevel);
	public boolean insertDfsDirectorateRecords(Integer districtId,Integer labId,Integer labDistrictId,String areaType,String area,
			Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,String dataEntryLevel,JSONArray gridRecords);
	public boolean updateDfsDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel);
	public List<CommonStringList> getLabList(String userName);
	public List<CommonStringList> getDistrictList(Integer labId,String userName);
	public List<CommonStringList> getInstitutionTypeList(Integer labId,Integer districtId,String userName);
	public List<CommonStringList> getInstitutionSpecialityList(Integer labId,Integer districtId,String areaType,String userName);
	public List<CommonStringList> getAreaCodeList(Integer labId,Integer districtId,String areaType,String area,String userName);
}
