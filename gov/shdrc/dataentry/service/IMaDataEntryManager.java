package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IMaDataEntryManager {
	public JSONArray getMaDirectorateRecords(String regionName,Integer corporationId, Integer regionOrCorporationId,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year);
	public boolean isMADirectorateRecordsExists(String regionName,Integer districtId,Integer corporationId,Integer municipalityId,Integer institutionId,
			String classificationName,Integer demographicId,Integer generalId,String frequency,Integer week,Integer quarter,
			String searchDate,String month,Integer year);
	public boolean insertMaDirectorateRecords(String regionName,Integer regionOrCorporationId,Integer corporationId, Integer districtId,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year,JSONArray gridRecords,String dataEntryLevel);
	public boolean updateMADirectorateRecords(JSONArray jsonArray);
	public List<CommonStringList> getCorporationAndMunicipalityList(Integer districtId,String userName);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel);
	public List<CommonStringList> getClassificationList(Integer directorateId);
	public List<CommonStringList> getRegions(String userName);
	public List<CommonStringList> getDistricts(String userName,String region);
	public List<CommonStringList> getInstitutionList(Integer paramId,String userName,String isCorporation);
}
