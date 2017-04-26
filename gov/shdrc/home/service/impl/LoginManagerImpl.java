/**
 * 
 */
package gov.shdrc.home.service.impl;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.home.dao.ILoginDAO;
import gov.shdrc.home.service.ILoginManager;
import gov.shdrc.util.SecurityUtil;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.User;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManagerImpl implements ILoginManager {
	private static Logger log=Logger.getLogger(LoginManagerImpl.class);
@Autowired(required=true)
ILoginDAO loginDAO;

public boolean validateUser(String userName,String password,String userIpAddress) throws SHDRCException {
	// To get user object from database
	User userObj = null;
	boolean isValidUser=false;
	try {
		log.debug(this.getClass().getName() + "- Entering ");
		// Get user object from database
		//userObj = userDao.getUser(user.getUfname());
		userObj=loginDAO.getuserList(userName);
		String org=null;
		if(userName.contains("admin")
				||userName.contains("tnhs")
				||userName.contains("tncs"))
			org="Administrator";
		else
			org=userObj.getOrganisation();
		isValidUser=SecurityUtil.authenticateUser(userName,password,org);
		if (!isValidUser) {
			throw new SHDRCException(ShdrcConstants.ERROR_INVALID_USER);
		} 		
		// Valid user, so update last logged-in date
		/*loginDAO.insertLoginAudit("192.168.0.0",userName);*/
		loginDAO.insertLoginAudit(userIpAddress,userName);	
		log.debug(this.getClass().getName() + "- Exit ");
		
	} catch (Exception ex) {
	} finally {
		// Do nothing
	}
	return isValidUser;
}

}
