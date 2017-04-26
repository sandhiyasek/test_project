package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDrmgrDataEntryDAO;
import gov.shdrc.dataentry.service.IDrmgrDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrmgrDataEntryManagerImpl implements IDrmgrDataEntryManager{
	@Autowired
	IDrmgrDataEntryDAO drmgrDataEntryDAO;
	
	public JSONArray getDrMgrDirectorateRecords(Integer departmentId,String classificationName,String frequency,String quarter,String month,Integer year){
		return drmgrDataEntryDAO.getDrMgrDirectorateRecords(departmentId,classificationName,frequency,quarter,month,year);
	}
	public boolean insertDrMgrDirectorateRecords(Integer departmentId,String classificationName,String frequency,String quarter,String month,
			Integer year,JSONArray gridRecords,String dataEntryLevel){
		return drmgrDataEntryDAO.insertDrMgrDirectorateRecords(departmentId,classificationName,frequency,quarter,month,year,gridRecords,dataEntryLevel);
	}
	public boolean updateDrMgrDirectorateRecords(JSONArray jsonArray){
		return drmgrDataEntryDAO.updateDrMgrDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		return drmgrDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,dataEntryLevel);
	}
	public List<CommonStringList> getDepartmentList(String userName){
		return drmgrDataEntryDAO.getDepartmentList(userName);
	}
}
