package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IShtdDataEntryDAO;
import gov.shdrc.dataentry.service.IShtdDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShtdDataEntryManagerImpl implements IShtdDataEntryManager{
	@Autowired
	IShtdDataEntryDAO shtdDataEntryDAO;
	
	public JSONArray getShtoDirectorateRecords(Integer workshopId,Integer mobileId,String classificationName,String frequency,String week,
			String quarter,String searchDate,String month,Integer year){
		return shtdDataEntryDAO.getShtoDirectorateRecords(workshopId,mobileId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertShtoDirectorateRecords(Integer workshopId,Integer mobileId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return shtdDataEntryDAO.insertShtoDirectorateRecords(workshopId,mobileId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateShtoDirectorateRecords(JSONArray jsonArray){
		return shtdDataEntryDAO.updateShtoDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		return shtdDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,dataEntryLevel);
	}
	public List<CommonStringList> getMobileList(Integer districtId,String userName){
		return shtdDataEntryDAO.getMobileList(districtId,userName);
	}
	public List<CommonStringList> getWorkshopList(String userName){
		return shtdDataEntryDAO.getWorkshopList(userName);
	}
}
