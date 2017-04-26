/**
 * 
 */
package gov.shdrc.home.service.impl;


import gov.shdrc.home.dao.IChangePasswordDAO;
import gov.shdrc.home.service.IChangePasswordManager;
import gov.shdrc.util.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordManagerImpl implements IChangePasswordManager {
@Autowired(required=true)
IChangePasswordDAO changePasswordDAO;

	public User getuserList(String userName){
		return changePasswordDAO.getuserList(userName);
	}
	public boolean updatePassword(String encryptedCurrentPassword,String newPwd,String userName,String userOrganisation){
		return changePasswordDAO.updatePassword(encryptedCurrentPassword,newPwd,userName,userOrganisation);
	}
}
