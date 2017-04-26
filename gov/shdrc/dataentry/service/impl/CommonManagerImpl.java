package gov.shdrc.dataentry.service.impl;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.shdrc.dataentry.fileupload.File;
import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.home.dao.jdbc.CommonDAO;
import gov.shdrc.sms.SmsAlert;
import gov.shdrc.util.CommonStringList;

@Service
public class CommonManagerImpl  implements ICommonManager{
	@Autowired
	CommonDAO commonDAO;
	
	public List<CommonStringList> getDirecorateList(boolean isAllDirectorate,String role){
		return commonDAO.getDirecorateList(isAllDirectorate,role);
	}
	public JSONArray getDirecorateServletList(){
		return commonDAO.getDirecorateServletList();
	}
	public List<CommonStringList> getDistricts(String userName){
		return commonDAO.getDistricts(userName);
	}
	public List<CommonStringList> getClassificationList(Integer directorateId,String query){
		return commonDAO.getClassificationList(directorateId,query);
	}
	public List<CommonStringList> getGeneralCategoryListByFrequency(int directorateId,String frequency,
			String dataEntryLevel,String query){
		return commonDAO.getGeneralCategoryListByFrequency(directorateId,frequency,
				dataEntryLevel,query);
	}
	public boolean isDemographicExists(int directorateId,String classificationName,String frequency,String query){
		return commonDAO.isDemographicExists(directorateId,classificationName,frequency,query);
	}
	public String getUserTier(){
		return commonDAO.getUserTier();
	}
	public String getUserAccessLevel(){
		return commonDAO.getUserAccessLevel();
	}
	public List<File> getFileList(boolean isAllDirectorate,String role,String userName){
		return commonDAO.getFileList(isAllDirectorate,role,userName);
	}
	public List<CommonStringList> getFileListBasedOnFrequency(Integer directorateId,String frequency,String userName){
		return commonDAO.getFileListBasedOnFrequency(directorateId,frequency,userName);
	}
	public boolean isFileExists(Integer directorateId,Integer hierarchyId1,Integer hierarchyId2,String fileName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year){
		return commonDAO.isFileExists(directorateId,hierarchyId1,hierarchyId2,fileName,frequency,week,quarter,searchDate,month,year);
	}
	public boolean addFileUploadAuditLog(Integer directorateId,String hierarchyName1,String hierarchyName2,String fileName,
			String frequency,String userName,String searchDate,String week,String month,String quarter,Integer year){
		return commonDAO.addFileUploadAuditLog(directorateId,hierarchyName1,hierarchyName2,fileName,frequency,userName,searchDate,week,month,quarter,year);
	}
	public String getMessageAlertMobileNo(String directorateIds,boolean isAllDirectorate){
		return commonDAO.getMessageAlertMobileNo(directorateIds,isAllDirectorate);
	}
	public List<SmsAlert> getSmsAlertList(Integer directorateId){
		return commonDAO.getSmsAlertList(directorateId);
	}
	public List<SmsAlert> getSmsAlertList(){
		return commonDAO.getSmsAlertList();
	}
	public boolean updateSmsAlert(Long smsId){
		return commonDAO.updateSmsAlert(smsId);
	}
	public List<CommonStringList> getDirectorateListById(Integer directorateId){
		return commonDAO.getDirectorateListById(directorateId);
	}
	
}
