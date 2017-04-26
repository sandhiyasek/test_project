package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.ITansacsDataEntryDAO;
import gov.shdrc.dataentry.service.ITansacsDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TansacsDataEntryManagerImpl implements ITansacsDataEntryManager{
	@Autowired
	ITansacsDataEntryDAO tansacsDataEntryDAO;
	
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName){
		return tansacsDataEntryDAO.getInstitutionList(districtId,institutionType,userName);
	}
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName){
		return tansacsDataEntryDAO.getInstitutionTypeList(districtId,userName);
	}
	public JSONArray getAidsDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String isDemographic,String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return tansacsDataEntryDAO.getAidsDirectorateRecords(districtId,institutionType,institutionId,classificationName,isDemographic,frequency,
				week,quarter,searchDate,month,year);
	}
	public boolean insertAidsDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,String dataEntryLevel,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){
		return tansacsDataEntryDAO.insertAidsDirectorateRecords(districtId,institutionType,institutionId,classificationName,dataEntryLevel,frequency,
				week,quarter,date,month,year,gridRecords);
	}
	public boolean updateAidsDirectorateRecords(JSONArray jsonArray){
		return tansacsDataEntryDAO.updateAidsDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		return tansacsDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,dataEntryLevel);
	}
}
