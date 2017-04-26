package gov.shdrc.reports.service.impl;

import gov.shdrc.reports.dao.IUserAuditReportDAO;
import gov.shdrc.reports.service.IUserAuditReportManager;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuditReportManagerImpl implements IUserAuditReportManager {
	@Autowired
	IUserAuditReportDAO userAuditReportDAO;
	
	public JSONArray getUserAuditReport(String directorateId,Integer year,String month,String freqName){
		return userAuditReportDAO.getUserAuditReport(directorateId,year,month,freqName);
	}
}
