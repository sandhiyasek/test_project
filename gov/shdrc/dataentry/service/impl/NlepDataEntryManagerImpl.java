package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.INlepDataEntryDAO;
import gov.shdrc.dataentry.service.INlepDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NlepDataEntryManagerImpl implements INlepDataEntryManager{
	@Autowired
	INlepDataEntryDAO nlepDataEntryDAO;

	public List<CommonStringList> getInstitutionList(Integer districtId,String userName){
		return nlepDataEntryDAO.getInstitutionList(districtId,userName);
	}
	public JSONArray getNlepDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String isDemographic,String frequency,String week,
			String quarter,String searchDate,String month,Integer year){
		return nlepDataEntryDAO.getNlepDirectorateRecords(districtId,institutionId,classificationName,isDemographic,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertNlepDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return nlepDataEntryDAO.insertNlepDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateNlepDirectorateRecords(JSONArray jsonArray){
		return nlepDataEntryDAO.updateNlepDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		return nlepDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
}
