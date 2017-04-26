package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IRntcpDataEntryDAO;
import gov.shdrc.dataentry.service.IRntcpDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RntcpDataEntryManagerImpl implements IRntcpDataEntryManager{
	@Autowired
	IRntcpDataEntryDAO rntcpDataEntryDAO;
	
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName){
		return rntcpDataEntryDAO.getInstitutionList(districtId,userName);
	}
	public JSONArray getRntcpDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return rntcpDataEntryDAO.getRntcpDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertRntcpDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String dataEntryLevel,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){
		return rntcpDataEntryDAO.insertRntcpDirectorateRecords(districtId,institutionId,classificationName,dataEntryLevel,
				 frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateRntcpDirectorateRecords(JSONArray jsonArray){
		return rntcpDataEntryDAO.updateRntcpDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		return rntcpDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,dataEntryLevel);
	}
	public List<CommonStringList> getDistricts(String userName){
		return rntcpDataEntryDAO.getDistricts(userName);
	}
}
