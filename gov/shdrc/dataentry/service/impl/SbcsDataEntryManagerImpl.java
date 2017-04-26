package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.ISbcsDataEntryDAO;
import gov.shdrc.dataentry.service.ISbcsDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SbcsDataEntryManagerImpl implements ISbcsDataEntryManager{
	@Autowired
	ISbcsDataEntryDAO sbcsDataEntryDAO;
	
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName){
		return sbcsDataEntryDAO.getInstitutionList(districtId,userName);
	}
	public JSONArray getSbcsDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return sbcsDataEntryDAO.getSbcsDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertSbcsDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return sbcsDataEntryDAO.insertSbcsDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateSbcsDirectorateRecords(JSONArray jsonArray){
		return sbcsDataEntryDAO.updateSbcsDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		return sbcsDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
}
