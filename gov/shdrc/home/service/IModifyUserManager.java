package gov.shdrc.home.service;

import gov.shdrc.util.User;

import org.springframework.stereotype.Service;

@Service
public interface IModifyUserManager {
	public User getUserDetails(String userName);
	public boolean updateUserDetails(String firstName,String lastName,String email,Long mobile,String userName);
}
