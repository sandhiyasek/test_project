/**
 * 
 */
package gov.shdrc.reports.service.impl;


import gov.shdrc.reports.dao.ICMCHISReportDAO;
import gov.shdrc.reports.dao.ICommonReportDAO;
import gov.shdrc.reports.service.ICMCHISReportManager;
import gov.shdrc.util.CommonStringList;





import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Upendra G
 *
 * 
 */
@Service
public class CMCHISReportManagerImpl implements ICMCHISReportManager {
	@Autowired
	ICommonReportDAO commonReportDAO;
	@Autowired
	ICMCHISReportDAO cmchisReportDAO;
	
	@Override
	public JSONArray getCMCHISIndicatorList(int directorateId,
			String indicatorCategory, int year, String month, String loggedUser) {		
		return cmchisReportDAO.getIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);
	}
	@Override
	public JSONArray getDistrictwiseIndicaotrDetails(int directorateID,
			String indicatorCategory, String indicatorName, int year,
			String month, String loggedUser) {		
		return cmchisReportDAO.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, year, month, loggedUser);
	}
	@Override
	public JSONArray getInstitutionwiseIndicaotrDetails(int directorateID,
			String indicatorCategory, String indicatorName,
			String districtName, int year, String month, String loggedUser) {		
		return cmchisReportDAO.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory, indicatorName, districtName, year, month, loggedUser);
	}
	public CommonStringList getDashboardIntMaxMonthAndYear(int directorateId,String indicatorCategory){
		return commonReportDAO.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
	}

	public JSONArray getFreeHandZoneData(int directorate,String category,String indName,int year){
		return cmchisReportDAO.getFreeHandZoneData(directorate,category,indName,year);
	}
	public List getFreeHandZoneIndCategory(int directorateId){
		return cmchisReportDAO.getFreeHandZoneIndCategory(directorateId);
	}
	@Override
	public List<CommonStringList> getFreeHandZoneIndNamesByCategory(int directorateId,String category) {
		// TODO Auto-generated method stub
		return cmchisReportDAO.getFreeHandZoneIndNamesByCategory(directorateId,category);
	}
	@Override
	public List getIndYearByNameandCategory(int directorate,String indCategory,String indName) {
		// TODO Auto-generated method stub
		return cmchisReportDAO.getIndYearByNameandCategory(directorate,indCategory,indName);
	}
	//Reports Zone
	@Override
	public JSONArray getReportZoneData(Integer directorateId,String reportName,/*Integer fromYear,String fromMonth,Integer toYear,String toMonth,*/String userName){
		return cmchisReportDAO.getReportZoneData(directorateId,reportName,/*fromYear,fromMonth,toYear,toMonth,*/userName);
	}
	//Analysis Zone
	@Override
	public JSONArray getAnalysisZoneData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return cmchisReportDAO.getAnalysisZoneData(directorateId,analysisReportName,year,month,userName);
	}
	@Override
	public JSONObject getAnalysisZoneMultiSeriesData(Integer directorateId,String analysisReportName,Integer year,String month,String userName){
		return cmchisReportDAO.getAnalysisZoneMultiSeriesData(directorateId,analysisReportName,year,month,userName);
	}
	//Indicator Zone	

	public JSONArray getGovtHospitalData(int directorateId,
			String indicatorCategory, int year, String month, String userName) {
		return cmchisReportDAO.getGovtHospitalData(directorateId, indicatorCategory, year, month, userName);
	}
	public JSONArray getIndicatorPvtStateData(int directorateId,
			String indicatorCategory, int year, String month, String userName) {
		return cmchisReportDAO.getIndicatorPvtStateData(directorateId, indicatorCategory, year, month, userName);
	}
	public JSONArray getIndicatorPvtDistrictData(int directorateId,
			String indicatorCategory, String indicators, int year,
			String month, String userName) {
		return cmchisReportDAO.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators, year, month, userName);
	}
	public JSONArray getIndicatorPvtInstitutionData(int directorateId,
			String indicatorCategory, String indicators, String district,
			int year, String maxMonth, String userName) {
		return cmchisReportDAO.getIndicatorPvtInstitutionData(directorateId, indicatorCategory, indicators, district, year, maxMonth, userName);
	}
}
