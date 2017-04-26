package gov.shdrc.servicemaintenance.service.impl;

import gov.shdrc.servicemaintenance.dao.IDirectorateMasterDAO;
import gov.shdrc.servicemaintenance.service.IDirectorateMasterManager;
import gov.shdrc.servicemaintenance.util.DirectorateMaster;
import gov.shdrc.util.SHDRCException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectorateMasterManagerImpl implements IDirectorateMasterManager {	

	@Autowired
	IDirectorateMasterDAO directorateMasterDAO;
	
	public boolean insertDirectorate(String directorateName,String directorateLevel,String userName) throws SHDRCException{
		return directorateMasterDAO.insertDirectorate(directorateName,directorateLevel,userName);
	}
	public List<DirectorateMaster> getDirectorateDetailsList(){
		return directorateMasterDAO.getDirectorateDetailsList();
	}
	public boolean updateDirectorate(int directorateId,String directorateName,String directorateLevel) throws SHDRCException{
		return directorateMasterDAO.updateDirectorate(directorateId,directorateName,directorateLevel);
	}
	public boolean isDirectorateExists(String directorateName){
		return directorateMasterDAO.isDirectorateExists(directorateName);
	}
	
}
