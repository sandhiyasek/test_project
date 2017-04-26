package gov.shdrc.dataentry.service.impl;

import gov.shdrc.dataentry.dao.IDfwDataEntryDAO;
import gov.shdrc.dataentry.service.IDfwDataEntryManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.DFWEmployeeForm;
import gov.shdrc.util.Promotion;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DfwDataEntryManagerImpl implements IDfwDataEntryManager{
	@Autowired
	IDfwDataEntryDAO dfwDataEntryDAO;

	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName){
		return dfwDataEntryDAO.getInstitutionList(districtId,institutionType,userName);
	}
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName){
		return dfwDataEntryDAO.getInstitutionTypeList(districtId,userName);
	}
	public JSONArray getDfwDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year){
		return dfwDataEntryDAO.getDfwDirectorateRecords(districtId,institutionType,institutionId,classificationName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean insertDfwDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String dataEntryLevel,String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords){
		return dfwDataEntryDAO.insertDfwDirectorateRecords(districtId,institutionType,institutionId,classificationName,dataEntryLevel,frequency,week,
				quarter,date,month,year,gridRecords);
	}
	public boolean updateDfwDirectorateRecords(JSONArray jsonArray){
		return dfwDataEntryDAO.updateDfwDirectorateRecords(jsonArray);
	}
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel,
			String dataEntryLevel,String month){
		return dfwDataEntryDAO.getIndicatorList(directorateId,classificationName,frequency,isDemographic,generalNameLevel,dataEntryLevel,month);
	}
	public List<DFWEmployeeForm> getEmployeeDetailsList(Integer districtId,Integer institutionId){
		return dfwDataEntryDAO.getEmployeeDetailsList(districtId,institutionId);
	}
	public List<CommonStringList> getPostList(Integer directorateId){
		return dfwDataEntryDAO.getPostList(directorateId);
	}
	public DFWEmployeeForm getEmployeeDetails(Long employeeId){
		return dfwDataEntryDAO.getEmployeeDetails(employeeId);
	}
	public DFWEmployeeForm getQualificationDetails(Long employeeId){
		return dfwDataEntryDAO.getQualificationDetails(employeeId);
	}
	public Promotion getPromotionDetails(Long employeeId){
		return dfwDataEntryDAO.getPromotionDetails(employeeId);
	}
	public DFWEmployeeForm getLeaveDetails(Long employeeId){
		return dfwDataEntryDAO.getLeaveDetails(employeeId);
	}
	public List<CommonStringList> getInstitutionList(Integer districtId){
		return dfwDataEntryDAO.getInstitutionList(districtId);
	}
	public List<DFWEmployeeForm> getEmployeeTransferDetailsList(){
		return dfwDataEntryDAO.getEmployeeTransferDetailsList();
	}
	public boolean insertEmployeeDetails(Integer districtId,Integer institutionId,String employeeName,String gpfOrCpsNo,String gender,String designation,String payBand,String groupCategory,
			 Integer dutyPay,String workingLocation, String recruitedBy,Integer gradePay,String community,String dobDate,String qualification,String ugMajor,String pgMajor,String others,
			 String firstAppointmentPost,String firstAppointmentDOJ,String dateOfRegularisation,String dateOfProbationDecleration,String dateOfRetirement,String incrementDueMonth,
			 String slsDueDate,String physicalStatus,String punishments,String maritalStatus,String nomineeDetails,String ELTakenFrom,String ELTakenTo,Integer ELTakenDays,Integer balanceEL,
			 String MLTakenFrom,String MLTakenTo,Integer MLTakenDays,Integer balanceML,String UELTakenFrom,String UELTakenTo,Integer UELTakenDays,Integer balanceUEL,
			 List<Promotion> promotionList){
		return dfwDataEntryDAO.insertEmployeeDetails(districtId,institutionId,employeeName,gpfOrCpsNo,gender,designation,payBand,groupCategory,
				 dutyPay,workingLocation,recruitedBy,gradePay,community,dobDate,qualification,ugMajor,pgMajor,others,
				 firstAppointmentPost,firstAppointmentDOJ,dateOfRegularisation,dateOfProbationDecleration,dateOfRetirement,incrementDueMonth,
				 slsDueDate,physicalStatus,punishments,maritalStatus,nomineeDetails,ELTakenFrom,ELTakenTo,ELTakenDays,balanceEL,
				 MLTakenFrom,MLTakenTo,MLTakenDays,balanceML,UELTakenFrom,UELTakenTo,UELTakenDays,balanceUEL,
				 promotionList);
	}
	public boolean updateEmployeeDetails(String gpfOrCpsNo,Long employeeId,Integer districtId,Integer institutionId,String designation,String payBand,String groupCategory,Integer dutyPay,String workingLocation,String recruitedBy,
			 Integer gradePay,String community,String dobDate,String qualification,String ugMajor,String pgMajor,String others,String firstAppointmentPost,String firstAppointmentDOJ,
			 String dateOfRegularisation,String dateOfProbationDecleration,String dateOfRetirement,String incrementDueMonth,String slsDueDate,String physicalStatus,
			 String promotionType,String promotion1,String promotion1DOJ,String punishments,String retirementType,String maritalStatus,String nomineeDetails,String ELTakenFrom,String ELTakenTo,
			 Integer ELTakenDays,Integer balanceEL,String MLTakenFrom,String MLTakenTo,Integer MLTakenDays,Integer balanceML,String UELTakenFrom,
			 String UELTakenTo,Integer UELTakenDays,Integer balanceUEL,String previousPromotionType){
		return dfwDataEntryDAO.updateEmployeeDetails(gpfOrCpsNo,employeeId,districtId,institutionId,designation,payBand,groupCategory,dutyPay,workingLocation,recruitedBy,
				  gradePay,community,dobDate,qualification,ugMajor,pgMajor,others,firstAppointmentPost,firstAppointmentDOJ,
				  dateOfRegularisation,dateOfProbationDecleration,dateOfRetirement,incrementDueMonth,slsDueDate,physicalStatus,
				  promotionType,promotion1,promotion1DOJ,punishments,retirementType,maritalStatus,nomineeDetails,ELTakenFrom,ELTakenTo,
				  ELTakenDays,balanceEL,MLTakenFrom,MLTakenTo,MLTakenDays,balanceML,UELTakenFrom,
				  UELTakenTo,UELTakenDays,balanceUEL,previousPromotionType);
	}
	public boolean transferEmployee(String gpfOrCpsNo,Integer districtId,Long employeeId,Integer institutionId,String transferType,String PromotionName){
		return dfwDataEntryDAO.transferEmployee(gpfOrCpsNo,districtId,employeeId,institutionId,transferType,PromotionName);
	}
	public List<CommonStringList> getTransferInstitutionList(Integer districtId){
		return dfwDataEntryDAO.getTransferInstitutionList(districtId);
	}
	public DFWEmployeeForm getTransferEmployeeDetails(Long employeeId,Integer districtId,Integer institutionId){
		return dfwDataEntryDAO.getTransferEmployeeDetails(employeeId,districtId,institutionId);
	}
	public boolean acceptEmployee(String gpfOrCpsNo,Integer districtId,Long employeeId,Integer institutionId,String transferType,
			String previousTransferType,String PromotionName,String promotionDate,String payBand,String groupCategory,Integer gradePay,String workingLocation){
		return dfwDataEntryDAO.acceptEmployee(gpfOrCpsNo,districtId,employeeId,institutionId,transferType,previousTransferType,PromotionName,
				promotionDate,payBand,groupCategory,gradePay,workingLocation);
	}
}
