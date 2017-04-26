package gov.shdrc.dataentry.dao;

import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IShtdDataEntryDAO {
	public JSONArray getShtoDirectorateRecords(Integer workshopId,Integer mobileId,String classificationName,String frequency,String week,
			String quarter,String searchDate,String month,Integer year);
	public boolean insertShtoDirectorateRecords(Integer workshopId,Integer mobileId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateShtoDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String dataEntryLevel);
	public List<CommonStringList> getMobileList(Integer districtId,String userName);
	public List<CommonStringList> getWorkshopList(String userName);
}
