package gov.shdrc.home.service;

import gov.shdrc.util.User;

import org.springframework.stereotype.Service;

@Service
public interface IChangePasswordManager {
	public User getuserList(String userName);
	public boolean updatePassword(String encryptedCurrentPassword,String newPwd,String userName,String userOrganisation);
}
