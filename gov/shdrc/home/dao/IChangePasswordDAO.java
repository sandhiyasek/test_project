package gov.shdrc.home.dao;

import gov.shdrc.util.User;

import org.springframework.stereotype.Service;

@Service
public interface IChangePasswordDAO {
	public User getuserList(String userName);
	public boolean updatePassword(String encryptedCurrentPassword,String newPwd,String userName,String userOrganisation);
}
