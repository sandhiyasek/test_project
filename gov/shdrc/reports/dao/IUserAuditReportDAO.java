package gov.shdrc.reports.dao;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IUserAuditReportDAO{	
	public JSONArray getUserAuditReport(String directorateId,Integer year,String month,String freqName);
}
