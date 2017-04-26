package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IEsiDataEntryDAO;
import gov.shdrc.dataentry.service.IEsiDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsiDataEntryManagerImpl implements IEsiDataEntryManager{
	@Autowired
	IEsiDataEntryDAO esiDataEntryDAO;
	
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName){
		return esiDataEntryDAO.getInstitutionList(districtId,userName);
	}
	public JSONArray getEsiDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return esiDataEntryDAO.getEsiDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertEsiDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return esiDataEntryDAO.insertEsiDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateEsiDirectorateRecords(JSONArray jsonArray){
		return esiDataEntryDAO.updateEsiDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		return esiDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
}
