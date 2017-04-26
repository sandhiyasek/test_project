package gov.shdrc.home.service;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public interface ILoginManager {
	public boolean validateUser(String userName,String password,String userIpAddress) throws SHDRCException;
}
