package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.INrhmDataEntryDAO;
import gov.shdrc.dataentry.service.INrhmDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NrhmDataEntryManagerImpl implements INrhmDataEntryManager{
	@Autowired
	INrhmDataEntryDAO nrhmDataEntryDAO;
	
	public JSONArray getNrhmDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String programName,String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return nrhmDataEntryDAO.getNrhmDirectorateRecords(districtId,institutionType,institutionId,classificationName,programName,frequency,
				week,quarter,searchDate,month,year);
	}
	public boolean insertNrhmDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String programName,String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){
		return nrhmDataEntryDAO.insertNrhmDirectorateRecords(districtId,institutionType,institutionId,classificationName, programName,frequency,
				week,quarter,date,month,year,gridRecords);
	}
	public boolean updateNrhmDirectorateRecords(JSONArray jsonArray){
		return nrhmDataEntryDAO.updateNrhmDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic){
		return nrhmDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic);
	}
	public List<CommonStringList> getProgramList(){
		return nrhmDataEntryDAO.getProgramList();
	}
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName){
		return nrhmDataEntryDAO.getInstitutionTypeList(districtId,userName);
	}
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName){
		return nrhmDataEntryDAO.getInstitutionList(districtId,institutionType,userName);
	}
}
