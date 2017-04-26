package gov.shdrc.reports.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IUserAuditReportManager {
	public JSONArray getUserAuditReport(String directorateId,Integer year,String month,String freqName);
}
