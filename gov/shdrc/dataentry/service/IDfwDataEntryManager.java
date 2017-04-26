package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.DFWEmployeeForm;
import gov.shdrc.util.Promotion;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface IDfwDataEntryManager {
	public List<CommonStringList> getInstitutionList(Integer districtId,String institutionType,String userName);
	public List<CommonStringList> getInstitutionTypeList(Integer districtId,String userName);
	public JSONArray getDfwDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,
			String classificationName,String frequency,String week,String quarter,String searchDate,String month,Integer year);
	public boolean insertDfwDirectorateRecords(Integer districtId,String institutionType,Integer institutionId,String classificationName,
			String dataEntryLevel,String frequency,String week,String quarter,String date,String month,Integer year,JSONArray gridRecords);
	public boolean updateDfwDirectorateRecords(JSONArray jsonArray);
	public JSONArray getIndicatorList(int directorateId,String classificationName,String frequency,String isDemographic,String generalNameLevel,
			String dataEntryLevel,String month);
	public List<DFWEmployeeForm> getEmployeeDetailsList(Integer districtId,Integer institutionId);
	public List<CommonStringList> getPostList(Integer directorateId);
	public DFWEmployeeForm getEmployeeDetails(Long employeeId);
	public DFWEmployeeForm getQualificationDetails(Long employeeId);
	public Promotion getPromotionDetails(Long employeeId);
	public DFWEmployeeForm getLeaveDetails(Long employeeId);
	public List<CommonStringList> getInstitutionList(Integer districtId);
	public List<DFWEmployeeForm> getEmployeeTransferDetailsList();
	public boolean insertEmployeeDetails(Integer districtId,Integer institutionId,String employeeName,String gpfOrCpsNo,String gender,String designation,String payBand,String groupCategory,
			 Integer dutyPay,String workingLocation, String recruitedBy,Integer gradePay,String community,String dobDate,String qualification,String ugMajor,String pgMajor,String others,
			 String firstAppointmentPost,String firstAppointmentDOJ,String dateOfRegularisation,String dateOfProbationDecleration,String dateOfRetirement,String incrementDueMonth,
			 String slsDueDate,String physicalStatus,String punishments,String maritalStatus,String nomineeDetails,String ELTakenFrom,String ELTakenTo,Integer ELTakenDays,Integer balanceEL,
			 String MLTakenFrom,String MLTakenTo,Integer MLTakenDays,Integer balanceML,String UELTakenFrom,String UELTakenTo,Integer UELTakenDays,Integer balanceUEL,
			 List<Promotion> promotionList);
	public boolean updateEmployeeDetails(String gpfOrCpsNo,Long employeeId,Integer districtId,Integer institutionId,String designation,String payBand,String groupCategory,Integer dutyPay,String workingLocation,String recruitedBy,
			 Integer gradePay,String community,String dobDate,String qualification,String ugMajor,String pgMajor,String others,String firstAppointmentPost,String firstAppointmentDOJ,
			 String dateOfRegularisation,String dateOfProbationDecleration,String dateOfRetirement,String incrementDueMonth,String slsDueDate,String physicalStatus,
			 String promotionType,String promotion1,String promotion1DOJ,String punishments,String retirementType,String maritalStatus,String nomineeDetails,String ELTakenFrom,String ELTakenTo,
			 Integer ELTakenDays,Integer balanceEL,String MLTakenFrom,String MLTakenTo,Integer MLTakenDays,Integer balanceML,String UELTakenFrom,
			 String UELTakenTo,Integer UELTakenDays,Integer balanceUEL,String previousPromotionType);
	public boolean transferEmployee(String gpfOrCpsNo,Integer districtId,Long employeeId,Integer institutionId,String transferType,String PromotionName);
	public List<CommonStringList> getTransferInstitutionList(Integer districtId);
	public DFWEmployeeForm getTransferEmployeeDetails(Long employeeId,Integer districtId,Integer institutionId);
	public boolean acceptEmployee(String gpfOrCpsNo,Integer districtId,Long employeeId,Integer institutionId,String transferType,
			String previousTransferType,String PromotionName,String promotionDate,String payBand,String groupCategory,Integer gradePay,String workingLocation);
}
