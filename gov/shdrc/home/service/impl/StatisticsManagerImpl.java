/**
 * 
 */
package gov.shdrc.home.service.impl;

import gov.shdrc.home.dao.IStatisticsDAO;
import gov.shdrc.home.service.IStatisticsManager;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsManagerImpl implements IStatisticsManager {
@Autowired(required=true)
IStatisticsDAO statisticsDAO;

	public List<String> getindicatorList(Integer directorateId){
		return statisticsDAO.getindicatorList(directorateId);
	}
	public JSONArray getIndStatisticsData(Integer directorateId,String indName){
		return statisticsDAO.getIndStatisticsData(directorateId,indName);
	}
	public JSONArray getIndDataAvailability(Integer directorateId,String indName){
		return statisticsDAO.getIndDataAvailability(directorateId,indName);
	}

}
