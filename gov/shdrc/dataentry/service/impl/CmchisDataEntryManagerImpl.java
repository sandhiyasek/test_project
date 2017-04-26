package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.ICmchisDataEntryDAO;
import gov.shdrc.dataentry.service.ICmchisDataEntryManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.webservice.reader.NewDataSet;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmchisDataEntryManagerImpl implements ICmchisDataEntryManager{
	@Autowired
	ICmchisDataEntryDAO cmchisDataEntryDAO;

	public List<CommonStringList> getInstitutionList(Integer districtId,String userName){
		return cmchisDataEntryDAO.getInstitutionList(districtId,userName);
	}
	public JSONArray getCmchisDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return cmchisDataEntryDAO.getCmchisDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertCmchisDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords){
		return cmchisDataEntryDAO.insertCmchisDirectorateRecords(districtId,institutionId,classificationName,frequency,week,quarter,date,month,year,gridRecords);
	}
	public boolean updateCmchisDirectorateRecords(JSONArray jsonArray){
		return cmchisDataEntryDAO.updateCmchisDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel){
		return cmchisDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel);
	}
	public boolean insertWebservicPreAuthData(List<NewDataSet.Shrdcpa> cmchisPreAuthDataList,String columnHeaders){
		return cmchisDataEntryDAO.insertWebservicPreAuthData(cmchisPreAuthDataList,columnHeaders);
	}
	public boolean insertWebservicClaimsAuthData(List<NewDataSet.Shrdccl> cmchisClaimsDataList,String columnHeaders){
		return cmchisDataEntryDAO.insertWebservicClaimsAuthData(cmchisClaimsDataList,columnHeaders);
	}
	public boolean insertWebservicPaymentData(List<NewDataSet.Shrdcpayment> cmchisPaymentDataList,String columnHeaders){
		return cmchisDataEntryDAO.insertWebservicPaymentData(cmchisPaymentDataList,columnHeaders);
	}
	public boolean insertWebservicDCData(List<NewDataSet.Shrdcdccl> cmchisDCDataList,String columnHeaders){
		return cmchisDataEntryDAO.insertWebservicDCData(cmchisDCDataList,columnHeaders);
	}
	public boolean insertHospitalList(List<NewDataSet.Shrdchosp> hospitalList){
		return cmchisDataEntryDAO.insertHospitalList(hospitalList);
	}
	public boolean disableHospitalList(){
		return cmchisDataEntryDAO.disableHospitalList();
	}
}
