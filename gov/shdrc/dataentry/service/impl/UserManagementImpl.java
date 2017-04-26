package gov.shdrc.dataentry.service.impl;

import java.util.List;

import gov.shdrc.dataentry.dao.IMaDataEntryDAO;
import gov.shdrc.dataentry.dao.IUserManagementDAO;
import gov.shdrc.dataentry.service.IUserManagementService;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SecurityUtil;
import gov.shdrc.util.UserManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserManagementImpl implements IUserManagementService{
	@Autowired
	IUserManagementDAO userManagementDAO;
	@Override
	public List<CommonStringList> getDirectoratesList() {
		// TODO Auto-generated method stub
		return userManagementDAO.getDirectoratesList();
	}

	
	
	@Override
	public boolean saveUserManagementDetails(String userName, String firstName,
			String lastName, String email, long mobile, String password,
			int deroctrateListId, String tierVal,String designation, String snName) throws Exception {
		// TODO Auto-generated method stub
		boolean saveFlag=false;
		String orgName = userManagementDAO.getDerectrateName(deroctrateListId);
	
		String b = SecurityUtil.addEntry(userName, snName, password, orgName,tierVal);
		if(b.equalsIgnoreCase("success inserted")){
			 saveFlag = userManagementDAO.saveUserManagementDetails(userName,firstName,lastName,email,mobile,password,deroctrateListId,tierVal,designation);	
		
		
		}
		return saveFlag;
	}


	@Override
	public List<UserManagement> getUserManagemenListByDeroctrateId(
			int deroctrateListId) {
		// TODO Auto-generated method stub
		return userManagementDAO.getUserManagemenListByDeroctrateId(deroctrateListId);
	}


	@Override
	public List<UserManagement> getUserMasterDetails(int userId) {
		// TODO Auto-generated method stub
		return userManagementDAO.getUserMasterDetails(userId);
	}
	@Override
	public boolean updateUserManagementValues(String firstName,
			String lastName, String email, long mobile, String designation,
			int userId) {
		// TODO Auto-generated method stub
		boolean saveFlag=false;
		 saveFlag = userManagementDAO.updateUserManagementValues(firstName,lastName,email,mobile,designation,userId);	
			
			return saveFlag;
	}
	
	@Override
	public boolean changePwdUserManagementValues(String userName,
			String newPassword, int userId, int derId)  {
		// TODO Auto-generated method stub
		boolean saveFlag=false;
		String orgName = userManagementDAO.getDerectrateName(derId);
		boolean b =SecurityUtil.updateUserPassword(newPassword, userName , orgName);
		if(b==true){
		 saveFlag = userManagementDAO.changePwdUserManagementValues(userName,newPassword,userId,derId);	
		}
		return saveFlag;
	}
	/*@Override
	public boolean updateUserManagementValues(String userName,
			String firstName, String lastName, String email, long mobile, String designation, int UserId,int derId) throws Exception {
		// TODO Auto-generated method stub
		
		boolean saveFlag=false;
		String orgName = userManagementDAO.getDerectrateName(derId);
		boolean b =SecurityUtil.updateUserPassword(password, userName , orgName);
		if(b==true){
		 saveFlag = userManagementDAO.updateUserManagementValues(userName,firstName,lastName,email,mobile,designation,UserId);	
		
		return saveFlag;
	}*/

}
