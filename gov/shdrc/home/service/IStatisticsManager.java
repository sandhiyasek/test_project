package gov.shdrc.home.service;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IStatisticsManager {
	public List<String> getindicatorList(Integer directorateId);
	public JSONArray getIndStatisticsData(Integer directorateId,String indName);
	public JSONArray getIndDataAvailability(Integer directorateId,String indName);
}
