package gov.shdrc.reports.service;

import gov.shdrc.util.CommonStringList;

import org.springframework.stereotype.Service;

@Service
public interface ICommonReportManager {
	public CommonStringList getIndicatorMaxMonthAndYear(int directorateId,String indicatorCategory);
}
