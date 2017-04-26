package gov.shdrc.servicemaintenance.service;

import gov.shdrc.servicemaintenance.util.InstitutionMaster;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IInstitutionMasterManager {
	public List<InstitutionMaster> getInstitutionDetailsList(Integer directorateId);
	public String getHUDType(Integer directorateId);
	public List<CommonStringList> getHudNameList(Integer directorateId,String hudType);
	public List<CommonStringList> getLocationList(Integer directorateId,Integer districtId,Integer hudId);
}
