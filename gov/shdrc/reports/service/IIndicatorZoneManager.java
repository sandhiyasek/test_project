package gov.shdrc.reports.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IIndicatorZoneManager {
	public JSONArray indzone(String ind,int year,String previousMonth);
}
