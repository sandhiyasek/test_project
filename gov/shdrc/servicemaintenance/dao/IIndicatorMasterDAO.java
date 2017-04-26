package gov.shdrc.servicemaintenance.dao;

import gov.shdrc.servicemaintenance.util.IndicatorMaster;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IIndicatorMasterDAO {
	public List<CommonStringList> getCategoryList(Integer directorateId);
	public int insertIndicator(Integer directorateId,String classification,String category,
			String otherCategory,String indicatorName,String subCategory,String subSubCategory,String modeSource,String otherfileSystem,
			String factMap,String frequency,String calculation,String hierarchy,String userName);
	public boolean isIndicatorExists(Integer directorateId,String classification,String category,String indicatorName,String frequency);
	public List<IndicatorMaster> getindicatorDetailsList(Integer directorateId);
	public IndicatorMaster getIndicatorDetails(Integer directorateId,Integer indicatorId);
	public boolean updateIndicator(Integer directorateId,Integer indicatorId,String classification,String category,
			String otherCategory,String indicatorName,String subCategory,String subSubCategory,String modeSource,String otherfileSystem,
			String factMap,String frequency,String calculation,String hierarchy);
	public List<CommonStringList> getIndHierarchyList(Integer directorateId);
}
