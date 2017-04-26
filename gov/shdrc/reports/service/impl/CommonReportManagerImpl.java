package gov.shdrc.reports.service.impl;

import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.service.ICommonReportManager;
import gov.shdrc.util.CommonStringList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CommonReportManagerImpl implements ICommonReportManager{
	@Autowired
	ICommonReportDAO commonReportDAO;
	
	public CommonStringList getIndicatorMaxMonthAndYear(int directorateId,String indicatorCategory){
		return commonReportDAO.getIndicatorMaxMonthAndYear(directorateId,indicatorCategory);
	}
}
