package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDphDataEntryDAO;
import gov.shdrc.dataentry.service.IDphDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DphDataEntryManagerImpl implements IDphDataEntryManager{
	@Autowired
	IDphDataEntryDAO dphDataEntryDAO;
	
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName){
		return dphDataEntryDAO.getInstitutionList(districtId,institutionType,userName);
	}
	public List<CommonStringList> getProgramList(){
		return dphDataEntryDAO.getProgramList();
	}
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName){
		return	dphDataEntryDAO.getInstitutionTypeList(districtId,userName);
	}
	public JSONArray getDphDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String programName,String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return dphDataEntryDAO.getDphDirectorateRecords(districtId,institutionType,institutionId,classificationName,
				programName,frequency,week,quarter,searchDate,month,year);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic){
		return dphDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic);
	}
	public boolean insertDphDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String programName,String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){
		return dphDataEntryDAO.insertDphDirectorateRecords(districtId,institutionType,institutionId,classificationName,programName,frequency,week,
				quarter,date,month,year,gridRecords);
	}
	public boolean updateDphDirectorateRecords(JSONArray jsonArray){
		return dphDataEntryDAO.updateDphDirectorateRecords(jsonArray);
	}
}
