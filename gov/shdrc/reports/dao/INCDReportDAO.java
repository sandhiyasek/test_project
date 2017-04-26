package gov.shdrc.reports.dao;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface INCDReportDAO{
	//Reports Zone
	public JSONArray getReportZoneData(Integer directorateId,String reportName,Integer fromYear,String fromMonth,Integer toYear,String toMonth,
			String districtName,String subType,String userName);
	public List<CommonStringList> getDistrictList(String userName);
	public List<CommonStringList> getNcdSubTypeList();
	public StringBuilder getAllDistrictList(String userName);
}
