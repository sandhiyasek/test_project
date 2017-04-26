
package gov.shdrc.home.controller;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.home.service.IChangePasswordManager;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.SecurityUtil;
import gov.shdrc.util.User;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;
import gov.shdrc.util.ValidatorHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChangePasswordController {
	private static Logger logger = Logger.getLogger(ChangePasswordController.class);
	@Autowired
	IChangePasswordManager changePasswordManager;

	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws ServletException, IOException{	
		ModelAndView modelView=new ModelAndView();
		modelView.setViewName("changePwd");
		
		String csrfToken =null;
		 try {
			 csrfToken = CSRFTokenUtil.getToken (request.getSession());
			 modelView.addObject("CSRF_TOKEN", csrfToken);
			} catch (NoSuchAlgorithmException e) {
				logger.error("error occured onLoad method in ChangePasswordController"+e);
			}
		return modelView;
	}
	
	@RequestMapping(value="/changeUserPassword", method=RequestMethod.POST)
	public @ResponseBody void shdrcUserVerification(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
		PrintWriter out = response.getWriter();
		try{
			String userName = UserInfo.getLoggedInUserName();
            String currentPwd=request.getParameter("currentpassword");
            String newPwd=request.getParameter("newpassword");
            String retypePwd=request.getParameter("retypepassword");
            validateFields(currentPwd,newPwd,retypePwd);
            String encryptedCurrentPassword=SecurityUtil.digestMd5(currentPwd);
            String encryptedNewPassword=SecurityUtil.digestMd5(newPwd);
            User user =changePasswordManager.getuserList(userName);
            if(user!=null){
	             String password=user.getUpass();
	             if(!encryptedCurrentPassword.equals(password)){
	            	 out.println("Please enter correct current password");
	             }else if(encryptedNewPassword.equals(password)){
	            	 out.println("Please enter different new password");
	             }else{
	            	 boolean successFlag=changePasswordManager.updatePassword(encryptedCurrentPassword,encryptedNewPassword,userName,user.getOrganisation());
	            	 if(successFlag)
						out.println("Success");
					 else
						out.println("Failure");
	             }
            }
		}catch(SHDRCException e){
			out.println(e.getMessage());
		} 
	}
	
	  private void validateFields(String currentpassword,String newpassword,String retypepassword)throws SHDRCException{
	    	if(Util.isNullOrBlank(currentpassword)){
	    		throw new  SHDRCException("Please enter Current Password");
	    	}else if(currentpassword.length()<8){
	    		throw new SHDRCException("Please enter atleast 8 char in password");
	    	}else if(currentpassword.length()>15){
	    		throw new SHDRCException("The Current password should not exceed more than 15 characters");
	    	}
	    	
	    	if(Util.isNullOrBlank(newpassword)){
	    		throw new SHDRCException("Please enter New Password");
	    	}else if(newpassword.length()<8){
	    		throw new SHDRCException("Please enter atleast 8 char in password");
	    	}else if(newpassword.length()>15){
	    		throw new SHDRCException("The New password should not exceed more than 15 characters");
	    	} else if(!ValidatorHelper.isValidSplCharacter(newpassword)){
	    		throw new SHDRCException("The New password must contain at least one lower case letter, one upper case letter, one digit and one special character");
	    	}

	    	if(Util.isNullOrBlank(retypepassword)){
	    		throw new SHDRCException("Please enter Re-Type New Password");
	    	}else if(retypepassword.length()<8){
	    		throw new SHDRCException("please enter atleast 8 char in password");
	    	}else if(retypepassword.length()>15){
	    		throw new SHDRCException("The Retype password should not exceed more than 15 characters");
	    	}else if(!ValidatorHelper.isValidSplCharacter(retypepassword)){
	    		throw new SHDRCException("The New password must contain at least one lower case letter, one upper case letter, one digit and one special character");
	    	}
	    	if(!newpassword.equalsIgnoreCase(retypepassword)){
	    		throw new SHDRCException("New Password does not match with retype password");
	    	 }
	    	
	    }

}
