/**
 * 
 */
package gov.shdrc.home.service.impl;

import gov.shdrc.home.dao.IModifyUserDAO;
import gov.shdrc.home.service.IModifyUserManager;
import gov.shdrc.util.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyUserManagerImpl implements IModifyUserManager {
@Autowired(required=true)
IModifyUserDAO modifyUserDAO;

	public User getUserDetails(String userName){
		return modifyUserDAO.getUserDetails(userName);
	}
	public boolean updateUserDetails(String firstName,String lastName,String email,Long mobile,String userName){
		return modifyUserDAO.updateUserDetails(firstName,lastName,email,mobile,userName);
	}
}
