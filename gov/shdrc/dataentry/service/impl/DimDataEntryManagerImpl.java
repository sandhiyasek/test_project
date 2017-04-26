package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDimDataEntryDAO;
import gov.shdrc.dataentry.service.IDimDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DimDataEntryManagerImpl implements IDimDataEntryManager{
	@Autowired
	IDimDataEntryDAO dimDataEntryDAO;

	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String system,String userName){
		return dimDataEntryDAO.getInstitutionTypeList(districtId,system,userName);
	}
	public List<CommonStringList> getSytem(){
		return dimDataEntryDAO.getSytem();
	}
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String system,String userName){
		return dimDataEntryDAO.getInstitutionList(districtId,institutionType,system,userName);
	}
	public JSONArray getDimDirectorateRecords(Integer districtId,String institutionType,String system,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return dimDataEntryDAO.getDimDirectorateRecords(districtId,institutionType,system,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertDimDirectorateRecords(Integer districtId,String institutionType,String system,Integer institutionId,String classificationName,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){
		return dimDataEntryDAO.insertDimDirectorateRecords(districtId,institutionType,system,institutionId,classificationName,frequency,week,quarter,date,
				month,year,gridRecords);
	}
	public boolean updateDimDirectorateRecords(JSONArray jsonArray){
		return dimDataEntryDAO.updateDimDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel,String system){
		return dimDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel,system);
	}
}
