package gov.shdrc.dataentry.service;

import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.UserManagement;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public interface IUserManagementService {

	public List<CommonStringList> getDirectoratesList();

	public boolean saveUserManagementDetails(String userName, String firstName,
			String lastName, String email, long mobile, String password,
			int deroctrateListId, String tierVal,String designation,String snName)throws Exception;

	public List<UserManagement> getUserManagemenListByDeroctrateId(
			int deroctrateListId);

	public List<UserManagement> getUserMasterDetails(int userId);

	public boolean updateUserManagementValues(String firstName,
			String lastName, String email, long mobile, String designation,
			int userId);

	public boolean changePwdUserManagementValues(String userName,
			String newPassword, int userId, int derId);


	
	
}
