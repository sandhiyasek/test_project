package gov.shdrc.servicemaintenance.service;

import gov.shdrc.servicemaintenance.util.DistrictMaster;
import gov.shdrc.util.SHDRCException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IDistrictMasterManager {
	public List<DistrictMaster> getDistrictDetailsList();
	public boolean insertDistrict(String districtName,String districtHeadQuarters,String state,Integer yearOfPopulationCount,
			Integer population,String userName) throws SHDRCException;
	public boolean isDistrictExists(String districtName);
	public DistrictMaster getDistrictDetails(Integer districtId);
	public boolean updateDistrict(int districtId,String districtName,String districtHeadQuarters,Integer yearOfPopulationCount,
			Integer population) throws SHDRCException;
}
