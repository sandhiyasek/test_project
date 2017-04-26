package gov.shdrc.servicemaintenance.service;

import gov.shdrc.servicemaintenance.util.DirectorateMaster;
import gov.shdrc.util.SHDRCException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IDirectorateMasterManager {
	public boolean insertDirectorate(String directorateName,String directorateLevel,String userName) throws SHDRCException;
	public List<DirectorateMaster> getDirectorateDetailsList();
	public boolean updateDirectorate(int directorateId,String directorateName,String directorateLevel) throws SHDRCException;
	public boolean isDirectorateExists(String directorateName);
}
