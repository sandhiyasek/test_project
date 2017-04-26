package gov.shdrc.servicemaintenance.dao;

import gov.shdrc.util.SHDRCException;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IIndicatorMasterDashboardDAO {
	public JSONArray getIndHierarchyList(Integer directorateId);
	public boolean insertThresholdRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords,String userName)throws SHDRCException;
	public JSONArray getThresholdDetails(Integer directorateId,Integer indicatorId);
	public boolean updateThresholdRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords)throws SHDRCException;
}
