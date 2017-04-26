package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IMaDataEntryDAO;
import gov.shdrc.dataentry.service.IMaDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaDataEntryManagerImpl implements IMaDataEntryManager{
	@Autowired
	IMaDataEntryDAO maDataEntryDAO;

	public JSONArray getMaDirectorateRecords(String regionName,Integer corporationId, Integer regionOrCorporationId,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return maDataEntryDAO.getMaDirectorateRecords(regionName,corporationId,regionOrCorporationId,institutionId,classificationName,frequency,
				week,quarter,searchDate,month,year);
	}
	public boolean isMADirectorateRecordsExists(String regionName,Integer districtId,Integer corporationId,Integer municipalityId,Integer institutionId,
			String classificationName,Integer demographicId,Integer generalId,String frequency,Integer week,Integer quarter,
			String searchDate,String month,Integer year){
		return maDataEntryDAO.isMADirectorateRecordsExists(regionName,districtId,corporationId,municipalityId,institutionId,classificationName,demographicId,
				generalId,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertMaDirectorateRecords(String regionName,Integer regionOrCorporationId,Integer corporationId, Integer districtId,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year,JSONArray gridRecords,String dataEntryLevel){
		return maDataEntryDAO.insertMaDirectorateRecords(regionName,regionOrCorporationId,corporationId,districtId,institutionId,classificationName,
				frequency,week,quarter,searchDate,month,year,gridRecords,dataEntryLevel);
	}
	public boolean updateMADirectorateRecords(JSONArray jsonArray){
		return maDataEntryDAO.updateMADirectorateRecords(jsonArray);
	}
	public List<CommonStringList> getCorporationAndMunicipalityList(Integer districtId,String userName){
		return maDataEntryDAO.getCorporationAndMunicipalityList(districtId,userName);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		return maDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,dataEntryLevel);
	}
	public List<CommonStringList> getClassificationList(Integer directorateId){
		return maDataEntryDAO.getClassificationList(directorateId);
	}
	public List<CommonStringList> getRegions(String userName){
		return maDataEntryDAO.getRegions(userName);
	}
	public List<CommonStringList> getDistricts(String userName,String region){
		return maDataEntryDAO.getDistricts(userName,region);
	}
	public List<CommonStringList> getInstitutionList(Integer paramId,String userName,String isCorporation){
		return maDataEntryDAO.getInstitutionList(paramId,userName,isCorporation);
	}
}
