package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDfsDataEntryDAO;
import gov.shdrc.dataentry.service.IDfsDataEntryManager;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DfsDataEntryManagerImpl implements IDfsDataEntryManager{
	@Autowired
	IDfsDataEntryDAO dfsDataEntryDAO;
	
	public JSONArray getDfsDirectorateRecords(Integer districtId,Integer labId,Integer labDistrictId,String areaType,String area,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year,String dataEntryLevel){
		return dfsDataEntryDAO.getDfsDirectorateRecords(districtId,labId,labDistrictId,areaType,area,institutionId,classificationName,frequency,week,
				quarter,searchDate,month,year,dataEntryLevel);
	}
	public boolean insertDfsDirectorateRecords(Integer districtId,Integer labId,Integer labDistrictId,String areaType,String area,
			Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,String dataEntryLevel,JSONArray gridRecords){
		return dfsDataEntryDAO.insertDfsDirectorateRecords(districtId,labId,labDistrictId,areaType,area,institutionId,classificationName,frequency,week,quarter,
				 date,month,year,dataEntryLevel,gridRecords);
	}
	public boolean updateDfsDirectorateRecords(JSONArray jsonArray){
		return dfsDataEntryDAO.updateDfsDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel){
		return dfsDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,dataEntryLevel);
	}
	public List<CommonStringList> getLabList(String userName){
		return dfsDataEntryDAO.getLabList(userName);
	}
	public List<CommonStringList> getDistrictList(Integer labId,String userName){
		return dfsDataEntryDAO.getDistrictList(labId,userName);
	}
	public List<CommonStringList> getInstitutionTypeList(Integer labId,Integer districtId,String userName){
		return dfsDataEntryDAO.getInstitutionTypeList(labId,districtId,userName);
	}
	public List<CommonStringList> getInstitutionSpecialityList(Integer labId,Integer districtId,String areaType,String userName){
		return dfsDataEntryDAO.getInstitutionSpecialityList(labId,districtId,areaType,userName);
	}
	public List<CommonStringList> getAreaCodeList(Integer labId,Integer districtId,String areaType,String area,String userName){
		return dfsDataEntryDAO.getAreaCodeList(labId,districtId,areaType,area,userName);
	}
}
