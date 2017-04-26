package gov.shdrc.reports.service.impl;

import java.util.List;

import gov.shdrc.reports.dao.INCDReportDAO;
import gov.shdrc.reports.service.INCDReportManager;
import gov.shdrc.util.CommonStringList;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NCDReportManagerImpl implements INCDReportManager {
	
	@Autowired
	INCDReportDAO ncdReportDAO;
	
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,
			String districtName,String subType,String userName){
		return ncdReportDAO.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,districtName,subType,userName);
	}
	public List<CommonStringList> getDistrictList(String userName){
		return ncdReportDAO.getDistrictList(userName);
	}
	public List<CommonStringList> getNcdSubTypeList(){
		return ncdReportDAO.getNcdSubTypeList();
	}
	public StringBuilder getAllDistrictList(String userName){
		return ncdReportDAO.getAllDistrictList(userName);
	}
}
