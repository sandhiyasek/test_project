package gov.shdrc.servicemaintenance.service.impl;

import gov.shdrc.servicemaintenance.dao.IIndicatorMasterDAO;
import gov.shdrc.servicemaintenance.service.IIndicatorMasterManager;
import gov.shdrc.servicemaintenance.util.IndicatorMaster;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorMasterManagerImpl implements IIndicatorMasterManager{

	@Autowired
	IIndicatorMasterDAO indicatorMasterDAO;
	
	public List<CommonStringList> getCategoryList(Integer directorateId) {
		return indicatorMasterDAO.getCategoryList(directorateId);
	}

	public int insertIndicator(Integer directorateId, String classification,
			String category, String otherCategory, String indicatorName,
			String subCategory, String subSubCategory, String modeSource,
			String otherfileSystem, String factMap, String frequency,
			String calculation, String hierarchy, String userName) {
		return indicatorMasterDAO.insertIndicator(directorateId, classification, category,
				otherCategory, indicatorName, subCategory, subSubCategory, modeSource, otherfileSystem, factMap, frequency, calculation, hierarchy, userName);
	}

	public boolean isIndicatorExists(Integer directorateId,
			String classification, String category, String indicatorName,
			String frequency) {
		return indicatorMasterDAO.isIndicatorExists(directorateId, classification, category, indicatorName, frequency);
	}

	public List<IndicatorMaster> getindicatorDetailsList(Integer directorateId) {
		return indicatorMasterDAO.getindicatorDetailsList(directorateId);
	}

	public IndicatorMaster getIndicatorDetails(Integer directorateId,
			Integer indicatorId) {
		return indicatorMasterDAO.getIndicatorDetails(directorateId, indicatorId);
	}

	public boolean updateIndicator(Integer directorateId, Integer indicatorId,
			String classification, String category, String otherCategory,
			String indicatorName, String subCategory, String subSubCategory,
			String modeSource, String otherfileSystem, String factMap,
			String frequency, String calculation, String hierarchy) {
		return indicatorMasterDAO.updateIndicator(directorateId, indicatorId, classification, category, otherCategory,
				indicatorName, subCategory, subSubCategory, modeSource, otherfileSystem, factMap, frequency, calculation, hierarchy);
	}

	public List<CommonStringList> getIndHierarchyList(Integer directorateId) {
		return indicatorMasterDAO.getIndHierarchyList(directorateId);
	}

}
