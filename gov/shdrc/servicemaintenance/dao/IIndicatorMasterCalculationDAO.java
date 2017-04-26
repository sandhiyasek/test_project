package gov.shdrc.servicemaintenance.dao;

import gov.shdrc.util.SHDRCException;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IIndicatorMasterCalculationDAO {
	public JSONArray getIndicatorsList(Integer directorateId);
	public boolean insertFormulaRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords,String userName) throws SHDRCException;
	public JSONArray getFormulaDetails(Integer directorateId,Integer indicatorId);
	public boolean updateFormulaRecords(Integer directorateId,Integer indicatorId,JSONArray gridRecords)throws SHDRCException;
}
