package gov.shdrc.servicemaintenance.service;

import gov.shdrc.servicemaintenance.util.HUDMaster;
import gov.shdrc.util.SHDRCException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IHUDMasterManager {
	public List<HUDMaster> getHudDetailsList(Integer directorateId);
	public boolean insertHUD(Integer directorateId,String hudName,String hudType,String hudGroup,String userName) throws SHDRCException;
	public boolean isHUDExists(String hudName);
	public HUDMaster getHudDetails(Integer hudId,Integer directorateId);
	public boolean updateHUD(Integer directorateId,int hudId,String hudName,String hudType,String hudGroup) throws SHDRCException;
	public String getHUDType(Integer directorateId);
}
