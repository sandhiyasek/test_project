package gov.shdrc.servicemaintenance.dao;

import gov.shdrc.servicemaintenance.util.LocationMaster;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ILocationMasterDAO {
	public List<LocationMaster> getLocationDetailsList(Integer directorateId);
	public String getHUDType(Integer directorateId);
	public List<CommonStringList> getHudNameList(Integer directorateId,String hudType);
	public boolean insertLocation(Integer directorateId,Integer districtId,Integer hudId,String hudType,String talukName,String blockName,String userName) throws SHDRCException;
}
