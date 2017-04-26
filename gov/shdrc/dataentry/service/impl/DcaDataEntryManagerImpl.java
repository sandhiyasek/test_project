package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDcaDataEntryDAO;
import gov.shdrc.dataentry.service.IDcaDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DcaDataEntryManagerImpl implements IDcaDataEntryManager{
	@Autowired
	IDcaDataEntryDAO dcaDataEntryDAO;
	public JSONArray getDcaDirectorateRecords(Integer hudId,Integer blockId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return dcaDataEntryDAO.getDcaDirectorateRecords(hudId,blockId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertDcaDirectorateRecords(Integer hudId,Integer blockId,Integer institutionId,String dataEntryLevel,String classificationName,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){
		return dcaDataEntryDAO.insertDcaDirectorateRecords(hudId,blockId,institutionId,dataEntryLevel,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateDcaDirectorateRecords(JSONArray jsonArray){
		return dcaDataEntryDAO.updateDcaDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		return dcaDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,dataEntryLevel);
	}
	public List<CommonStringList> getHudList(String userName){
		return dcaDataEntryDAO.getHudList(userName);
	}
	public List<CommonStringList> getBlockList(Integer hudId,String userName){
		return dcaDataEntryDAO.getBlockList(hudId,userName);
	}
	public List<CommonStringList> getInstituteList(Integer hudId,String userName){
		return dcaDataEntryDAO.getInstituteList(hudId,userName);
	}
}
