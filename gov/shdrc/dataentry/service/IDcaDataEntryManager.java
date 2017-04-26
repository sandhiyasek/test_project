package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDcaDataEntryManager {
	public JSONArray getDcaDirectorateRecords(Integer hudId,Integer blockId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public boolean insertDcaDirectorateRecords(Integer hudId,Integer blockId,Integer institutionId,String dataEntryLevel,String classificationName,
			String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateDcaDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel);
	public List<CommonStringList> getHudList(String userName);
	public List<CommonStringList> getBlockList(Integer hudId,String userName);
	public List<CommonStringList> getInstituteList(Integer hudId,String userName);
}
