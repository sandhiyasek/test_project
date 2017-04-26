package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;
import gov.shdrc.webservice.reader.NewDataSet;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ICmchisDataEntryManager {
	public List<CommonStringList> getInstitutionList(Integer districtId,String userName);
	public JSONArray getCmchisDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public boolean insertCmchisDirectorateRecords(Integer districtId,Integer institutionId,String classificationName,String frequency,String week,String quarter,
			String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateCmchisDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel);
	public boolean insertWebservicPreAuthData(List<NewDataSet.Shrdcpa> cmchisPreAuthDataList,String columnHeaders);
	public boolean insertWebservicClaimsAuthData(List<NewDataSet.Shrdccl> cmchisClaimsDataList,String columnHeaders);
	public boolean insertWebservicPaymentData(List<NewDataSet.Shrdcpayment> cmchisPaymentDataList,String columnHeaders);
	public boolean insertWebservicDCData(List<NewDataSet.Shrdcdccl> cmchisDCDataList,String columnHeaders);
	public boolean insertHospitalList(List<NewDataSet.Shrdchosp> hospitalList);
	public boolean disableHospitalList();	
}
