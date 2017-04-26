package gov.shdrc.reports.dao;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IIndicatorZoneDAO{
	public JSONArray indzone(String ind,int year,String previousMonth);
}
