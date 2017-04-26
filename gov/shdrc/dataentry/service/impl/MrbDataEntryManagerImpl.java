package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IMrbDataEntryDAO;
import gov.shdrc.dataentry.service.IMrbDataEntryManager;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MrbDataEntryManagerImpl implements IMrbDataEntryManager{
	@Autowired
	IMrbDataEntryDAO mrbDataEntryDAO;
	
	public JSONArray getMrbDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return mrbDataEntryDAO.getMrbDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertMrbDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return mrbDataEntryDAO.insertMrbDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateMrbDirectorateRecords(JSONArray jsonArray){
		return mrbDataEntryDAO.updateMrbDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		return mrbDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
}
