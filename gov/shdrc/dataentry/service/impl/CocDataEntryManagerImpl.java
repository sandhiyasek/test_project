package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.ICocDataEntryDAO;
import gov.shdrc.dataentry.service.ICocDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CocDataEntryManagerImpl implements ICocDataEntryManager{
	@Autowired
	ICocDataEntryDAO cocDataEntryDAO;

	public List<CommonStringList> getInstitutionList(String region,String userName){
		return cocDataEntryDAO.getInstitutionList(region,userName);
	}
	public JSONArray getCocDirectorateRecords(String regionName,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return cocDataEntryDAO.getCocDirectorateRecords(regionName,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertCocDirectorateRecords(String regionName,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return 	cocDataEntryDAO.insertCocDirectorateRecords(regionName,institutionId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateCocDirectorateRecords(JSONArray jsonArray){
		return cocDataEntryDAO.updateCocDirectorateRecords(jsonArray);
	}
	public List<CommonStringList> getRegions(String userName){
		return cocDataEntryDAO.getRegions(userName);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		return cocDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
}
