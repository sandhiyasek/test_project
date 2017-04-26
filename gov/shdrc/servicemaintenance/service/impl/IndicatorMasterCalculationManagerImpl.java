package gov.shdrc.servicemaintenance.service.impl;

import gov.shdrc.servicemaintenance.dao.IIndicatorMasterCalculationDAO;
import gov.shdrc.servicemaintenance.service.IIndicatorMasterCalculationManager;
import gov.shdrc.util.SHDRCException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorMasterCalculationManagerImpl implements IIndicatorMasterCalculationManager {
	@Autowired
	IIndicatorMasterCalculationDAO iIndicatorMasterCalculationDAO;

	public JSONArray getIndicatorsList(Integer directorateId) {
		return iIndicatorMasterCalculationDAO.getIndicatorsList(directorateId);
	}

	public boolean insertFormulaRecords(Integer directorateId,
			Integer indicatorId, JSONArray gridRecords, String userName)throws SHDRCException {
		return iIndicatorMasterCalculationDAO.insertFormulaRecords(directorateId, indicatorId, gridRecords, userName);
	}

	public JSONArray getFormulaDetails(Integer directorateId,
			Integer indicatorId) {
		return iIndicatorMasterCalculationDAO.getFormulaDetails(directorateId, indicatorId);
	}

	public boolean updateFormulaRecords(Integer directorateId,
			Integer indicatorId, JSONArray gridRecords)throws SHDRCException {
		return iIndicatorMasterCalculationDAO.updateFormulaRecords(directorateId, indicatorId, gridRecords);
	}
}

