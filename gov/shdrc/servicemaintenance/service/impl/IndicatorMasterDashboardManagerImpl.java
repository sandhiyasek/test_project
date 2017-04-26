package gov.shdrc.servicemaintenance.service.impl;

import gov.shdrc.servicemaintenance.dao.IIndicatorMasterDashboardDAO;
import gov.shdrc.servicemaintenance.service.IIndicatorMasterDashboardManager;
import gov.shdrc.util.SHDRCException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorMasterDashboardManagerImpl implements IIndicatorMasterDashboardManager{
	@Autowired
	IIndicatorMasterDashboardDAO iIndicatorMasterDashboardDAO;

	public JSONArray getIndHierarchyList(Integer directorateId) {
		return iIndicatorMasterDashboardDAO.getIndHierarchyList(directorateId);
	}

	public boolean insertThresholdRecords(Integer directorateId,
			Integer indicatorId, JSONArray gridRecords, String userName)throws SHDRCException {
		return iIndicatorMasterDashboardDAO.insertThresholdRecords(directorateId, indicatorId, gridRecords, userName);
	}

	public JSONArray getThresholdDetails(Integer directorateId,
			Integer indicatorId) {
		return iIndicatorMasterDashboardDAO.getThresholdDetails(directorateId, indicatorId);
	}

	public boolean updateThresholdRecords(Integer directorateId,
			Integer indicatorId, JSONArray gridRecords)throws SHDRCException {
		return iIndicatorMasterDashboardDAO.updateThresholdRecords(directorateId, indicatorId, gridRecords);
	}

}
