package gov.shdrc.servicemaintenance.service.impl;

import java.util.List;

import gov.shdrc.servicemaintenance.dao.IInstitutionMasterDAO;
import gov.shdrc.servicemaintenance.service.IInstitutionMasterManager;
import gov.shdrc.servicemaintenance.util.InstitutionMaster;
import gov.shdrc.util.CommonStringList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionMasterManagerImpl implements IInstitutionMasterManager {
	@Autowired
	IInstitutionMasterDAO institutionMasterDAO;

	public List<InstitutionMaster> getInstitutionDetailsList(
			Integer directorateId) {
		return institutionMasterDAO.getInstitutionDetailsList(directorateId);
	}

	public String getHUDType(Integer directorateId) {
		return institutionMasterDAO.getHUDType(directorateId);
	}

	public List<CommonStringList> getHudNameList(Integer directorateId,
			String hudType) {
		return institutionMasterDAO.getHudNameList(directorateId, hudType);
	}

	public List<CommonStringList> getLocationList(Integer directorateId,
			Integer districtId, Integer hudId) {
		return institutionMasterDAO.getLocationList(directorateId, districtId, hudId);
	}
}
