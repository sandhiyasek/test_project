package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDmsDataEntryDAO;
import gov.shdrc.dataentry.service.IDmsDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmsDataEntryManagerImpl implements IDmsDataEntryManager{
	@Autowired
	IDmsDataEntryDAO dmsDataEntryDAO;
	
	public List<CommonStringList> getHospitalList(Integer districtId,String userName){
		return dmsDataEntryDAO.getHospitalList(districtId,userName);
	}
	public JSONArray getDmsDirectorateRecords(Integer districtId,Integer hospitalId,String classificationName,
			String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return dmsDataEntryDAO.getDmsDirectorateRecords(districtId,hospitalId,classificationName,frequency,
				week,quarter,searchDate,month,year);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic){
		return dmsDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic);
	}
	public boolean insertDmsDirectorateRecords(Integer districtId,Integer hospitalId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return dmsDataEntryDAO.insertDmsDirectorateRecords(districtId,hospitalId,classificationName,frequency,week,quarter,
				date,month,year,gridRecords);
	}
	public boolean updateDmsDirectorateRecords(JSONArray jsonArray){
		return dmsDataEntryDAO.updateDmsDirectorateRecords(jsonArray);
	}
}
