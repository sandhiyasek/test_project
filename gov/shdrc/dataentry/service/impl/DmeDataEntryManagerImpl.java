package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDmeDataEntryDAO;
import gov.shdrc.dataentry.service.IDmeDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmeDataEntryManagerImpl implements IDmeDataEntryManager{
	@Autowired
	IDmeDataEntryDAO dmeDataEntryDAO;
	
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName){
		return dmeDataEntryDAO.getInstitutionList(districtId,userName);
	}
	public JSONArray getDmeDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return dmeDataEntryDAO.getDmeDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,
				 searchDate,month,year);
	}
	public boolean insertDmeDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return dmeDataEntryDAO.insertDmeDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,
				date,month,year,gridRecords);
	}
	public boolean updateDmeDirectorateRecords(JSONArray jsonArray){
		return dmeDataEntryDAO.updateDmeDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,
			String generalNameLevel){
		return dmeDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
}
