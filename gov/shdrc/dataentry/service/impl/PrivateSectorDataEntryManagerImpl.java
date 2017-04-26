package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IPrivateSectorDataEntryDAO;
import gov.shdrc.dataentry.service.IPrivateSectorDataEntryManager;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivateSectorDataEntryManagerImpl implements IPrivateSectorDataEntryManager{
	@Autowired
	IPrivateSectorDataEntryDAO privatesectorDataEntryDAO;

	public boolean updatePvtSecDirectorateRecords(JSONArray jsonArray){
		return privatesectorDataEntryDAO.updatePvtSecDirectorateRecords(jsonArray);
	}
	public JSONArray getPvtSecDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return privatesectorDataEntryDAO.getPvtSecDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		return privatesectorDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
	public boolean insertPvtSecDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return privatesectorDataEntryDAO.insertPvtSecDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
}
