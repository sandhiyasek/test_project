package gov.shdrc.dataentry.service;

import gov.shdrc.dataentry.fileupload.File;
import gov.shdrc.sms.SmsAlert;
import gov.shdrc.util.CommonStringList;

import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ICommonManager {
	public List<CommonStringList> getDirecorateList(boolean isAllDirectorate,String role);
	public JSONArray getDirecorateServletList();
	public List<CommonStringList> getDistricts(String userName);
	public List<CommonStringList> getClassificationList(Integer directorateId,String query);
	public List<CommonStringList> getGeneralCategoryListByFrequency(int directorateId,String frequency,String dataEntryLevel,String query);
	public boolean isDemographicExists(int directorateId,String classificationName,String frequency,String query);
	public String getUserTier();
	public String getUserAccessLevel();
	public List<File> getFileList(boolean isAllDirectorate,String role,String userName);
	public List<CommonStringList> getFileListBasedOnFrequency(Integer directorateId,String frequency,String userName);
	public boolean isFileExists(Integer directorateId,Integer hierarchyId1,Integer hierarchyId2,String fileName,String frequency,String week,String quarter,
			String searchDate,String month,Integer year);
	public boolean addFileUploadAuditLog(Integer directorateId,String hierarchyName1,String hierarchyName2,String fileName,
			String frequency,String userName,String searchDate,String week,String month,String quarter,Integer year);
	public String getMessageAlertMobileNo(String directorateIds,boolean isAllDirectorate);
	public List<SmsAlert> getSmsAlertList(Integer directorateId);
	public List<SmsAlert> getSmsAlertList();
	public boolean updateSmsAlert(Long smsId);
	public List<CommonStringList> getDirectorateListById(Integer directorateId);
}
