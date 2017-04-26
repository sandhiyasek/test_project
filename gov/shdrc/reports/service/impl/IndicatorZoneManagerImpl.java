package gov.shdrc.reports.service.impl;

import gov.shdrc.reports.dao.IIndicatorZoneDAO;
import gov.shdrc.reports.service.IIndicatorZoneManager;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndicatorZoneManagerImpl implements IIndicatorZoneManager {
	
	@Autowired
	IIndicatorZoneDAO indicatorZoneDAO;
	
	public JSONArray indzone(String ind, int year, String previousMonth) {
		return indicatorZoneDAO.indzone(ind, year, previousMonth);
	}
	
}
