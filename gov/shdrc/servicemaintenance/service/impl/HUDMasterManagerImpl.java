package gov.shdrc.servicemaintenance.service.impl;

import java.util.List;

import gov.shdrc.servicemaintenance.dao.IHUDMasterDAO;
import gov.shdrc.servicemaintenance.service.IHUDMasterManager;
import gov.shdrc.servicemaintenance.util.HUDMaster;
import gov.shdrc.util.SHDRCException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HUDMasterManagerImpl implements IHUDMasterManager{
	@Autowired
	IHUDMasterDAO ihudMasterDAO;

	public List<HUDMaster> getHudDetailsList(Integer directorateId) {
		return ihudMasterDAO.getHudDetailsList(directorateId);
	}

	public boolean insertHUD(Integer directorateId, String hudName,
			String hudType, String hudGroup, String userName) throws SHDRCException {
		return ihudMasterDAO.insertHUD(directorateId, hudName, hudType, hudGroup, userName);
	}

	public boolean isHUDExists(String hudName) {
		return ihudMasterDAO.isHUDExists(hudName);
	}

	public HUDMaster getHudDetails(Integer hudId, Integer directorateId) {
		return ihudMasterDAO.getHudDetails(hudId, directorateId);
	}

	public boolean updateHUD(Integer directorateId, int hudId, String hudName,
			String hudType, String hudGroup) throws SHDRCException {
		return ihudMasterDAO.updateHUD(directorateId, hudId, hudName, hudType, hudGroup);
	}

	public String getHUDType(Integer directorateId) {
		return ihudMasterDAO.getHUDType(directorateId);
	}
}
