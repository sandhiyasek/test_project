package gov.shdrc.servicemaintenance.dao;

import gov.shdrc.servicemaintenance.util.GeneralMaster;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IGeneralMasterDAO {

	public List<GeneralMaster> getGeneralDetailsList(Integer directorateId);
	public List<CommonStringList> getTypeList(Integer directorateId);
	public boolean insertGeneral(Integer directorateId,Integer indicatorId,String generalCategory,String type,
			String otherType,String generalName,String subCategory,String subSubCategory,String classification,String userName) throws SHDRCException;
	public boolean updateGeneral(Integer directorateId,Integer indicatorId,String generalCategory,String type,
			String otherType,String generalName,String subCategory,String subSubCategory,String classification) throws SHDRCException;
	public GeneralMaster getGeneralDetails(Integer directorateId,Integer indicatorId);
}
