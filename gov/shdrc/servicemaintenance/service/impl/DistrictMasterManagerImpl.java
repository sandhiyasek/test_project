package gov.shdrc.servicemaintenance.service.impl;

import java.util.List;

import gov.shdrc.servicemaintenance.dao.IDistrictMasterDAO;
import gov.shdrc.servicemaintenance.service.IDistrictMasterManager;
import gov.shdrc.servicemaintenance.util.DistrictMaster;
import gov.shdrc.util.SHDRCException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictMasterManagerImpl implements IDistrictMasterManager{
	@Autowired
	IDistrictMasterDAO iDistrictMasterDAO;

	public List<DistrictMaster> getDistrictDetailsList() {
		return iDistrictMasterDAO.getDistrictDetailsList();
	}

	public boolean insertDistrict(String districtName,
			String districtHeadQuarters, String state,
			Integer yearOfPopulationCount, Integer population, String userName) throws SHDRCException{
		return iDistrictMasterDAO.insertDistrict(districtName, districtHeadQuarters, state, yearOfPopulationCount, population, userName);
	}

	public boolean isDistrictExists(String districtName) {
		return iDistrictMasterDAO.isDistrictExists(districtName);
	}

	public DistrictMaster getDistrictDetails(Integer districtId) {
		return iDistrictMasterDAO.getDistrictDetails(districtId);
	}

	public boolean updateDistrict(int districtId, String districtName,
			String districtHeadQuarters, Integer yearOfPopulationCount,
			Integer population) throws SHDRCException{
		return iDistrictMasterDAO.updateDistrict(districtId, districtName, districtHeadQuarters, yearOfPopulationCount, population);
	}
}
