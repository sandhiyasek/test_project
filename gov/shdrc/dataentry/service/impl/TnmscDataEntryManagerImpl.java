package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.ITnmscDataEntryDAO;
import gov.shdrc.dataentry.service.ITnmscDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TnmscDataEntryManagerImpl implements ITnmscDataEntryManager{
	@Autowired
	ITnmscDataEntryDAO tnmscDataEntryDAO;
	
	public List<CommonStringList> getInstitutionList(){
		return tnmscDataEntryDAO.getInstitutionList();
	}
	public JSONArray getTnmscDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return tnmscDataEntryDAO.getTnmscDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic){
		return tnmscDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic);
	}
	public boolean insertTnmscDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return tnmscDataEntryDAO.insertTnmscDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,
				 date,month,year,gridRecords);
	}
	public boolean updateTnmscDirectorateRecords(JSONArray jsonArray){
		return tnmscDataEntryDAO.updateTnmscDirectorateRecords(jsonArray);
	}
}
