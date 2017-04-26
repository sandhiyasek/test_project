package gov.shdrc.servicemaintenance.service.impl;

import gov.shdrc.servicemaintenance.dao.ILocationMasterDAO;
import gov.shdrc.servicemaintenance.service.ILocationMasterManager;
import gov.shdrc.servicemaintenance.util.LocationMaster;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationMasterManagerImpl implements ILocationMasterManager {
	@Autowired
	ILocationMasterDAO locationMasterDAO;
	
	public List<LocationMaster> getLocationDetailsList(Integer directorateId) {
		return locationMasterDAO.getLocationDetailsList(directorateId);
	}

	public String getHUDType(Integer directorateId) {
		return locationMasterDAO.getHUDType(directorateId);
	}

	public List<CommonStringList> getHudNameList(Integer directorateId,
			String hudType) {
		return locationMasterDAO.getHudNameList(directorateId, hudType);
	}

	public boolean insertLocation(Integer directorateId, Integer districtId,
			Integer hudId, String hudType, String talukName, String blockName,
			String userName) throws SHDRCException{
		return locationMasterDAO.insertLocation(directorateId, districtId, hudId, hudType, talukName, blockName, userName);
	}

}
