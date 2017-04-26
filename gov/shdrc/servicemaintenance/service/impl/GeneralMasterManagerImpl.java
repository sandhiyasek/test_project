package gov.shdrc.servicemaintenance.service.impl;

import java.util.List;

import gov.shdrc.servicemaintenance.dao.IGeneralMasterDAO;
import gov.shdrc.servicemaintenance.service.IGeneralMasterManager;
import gov.shdrc.servicemaintenance.util.GeneralMaster;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralMasterManagerImpl implements IGeneralMasterManager{
	@Autowired
	IGeneralMasterDAO iGeneralMasterDAO;

	public List<GeneralMaster> getGeneralDetailsList(Integer directorateId) {
		return iGeneralMasterDAO.getGeneralDetailsList(directorateId);
	}

	public List<CommonStringList> getTypeList(Integer directorateId) {
		return iGeneralMasterDAO.getTypeList(directorateId);
	}

	public boolean insertGeneral(Integer directorateId, Integer indicatorId,
			String generalCategory, String type, String otherType,
			String generalName, String subCategory, String subSubCategory,
			String classification, String userName) throws SHDRCException {
		return iGeneralMasterDAO.insertGeneral(directorateId, indicatorId, generalCategory, type,
				otherType, generalName, subCategory, subSubCategory, classification, userName);
	}

	public boolean updateGeneral(Integer directorateId, Integer indicatorId,
			String generalCategory, String type, String otherType,
			String generalName, String subCategory, String subSubCategory,
			String classification) throws SHDRCException {
		return iGeneralMasterDAO.updateGeneral(directorateId, indicatorId, generalCategory, type,
				otherType, generalName, subCategory, subSubCategory, classification);
	}

	public GeneralMaster getGeneralDetails(Integer directorateId,
			Integer indicatorId) {
		return iGeneralMasterDAO.getGeneralDetails(directorateId, indicatorId);
	}
}
