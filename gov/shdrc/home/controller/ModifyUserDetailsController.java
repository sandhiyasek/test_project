
package gov.shdrc.home.controller;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.home.service.IModifyUserManager;
import gov.shdrc.util.CSRFTokenUtil;
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
public class ModifyUserDetailsController {
	private static Logger logger=Logger.getLogger(ModifyUserDetailsController.class);
	@Autowired
	IModifyUserManager modifyUserManager;

	@RequestMapping(value="/modifyUser", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws ServletException, IOException{	
		ModelAndView modelView=new ModelAndView();
		modelView.setViewName("changeUserDetails");
		
		String userName = UserInfo.getLoggedInUserName();
		User user =modifyUserManager.getUserDetails(userName);
		modelView.addObject("user", user);
		modelView.addObject("userName", userName);
		 
		 String csrfToken =null;
		 try {
			 csrfToken = CSRFTokenUtil.getToken (request.getSession());
			 modelView.addObject("CSRF_TOKEN", csrfToken);
			} catch (NoSuchAlgorithmException e) {
				//e.printStackTrace();
				logger.error("error occured onLoad method in ModifyUserDetailsController"+e);
			}
		return modelView;
	}
	
	@RequestMapping(value="/modifyUserDetails", method=RequestMethod.POST)
	public @ResponseBody void shdrcUserVerification(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
		PrintWriter out = response.getWriter();
		try{
			String userName = UserInfo.getLoggedInUserName();
	        String firstName=request.getParameter("ufname");
	        String lastName=request.getParameter("ulname");
	        String email=request.getParameter("email");
	        long mobile = Long.parseLong(request.getParameter("mobile"));
	        validateFields(firstName,lastName,email,request.getParameter("mobile"));
	        boolean successFlag=modifyUserManager.updateUserDetails(firstName,lastName,email,mobile,userName);
	        if(successFlag)
				out.println("User Details has been changed successfully");
			 else
				out.println("Error in changing User Details");
		}catch(SHDRCException e){
			out.println(e.getMessage());
		} 
	}
	
	private void validateFields(String firstName,String lastName,String email,String mobile)throws SHDRCException{
		if(Util.isNullOrBlank(firstName)){
			throw new SHDRCException("Please enter First Name");
		}else if(!ValidatorHelper.isValidNumericChar(firstName)){
			throw new SHDRCException("Please enter Valid First Name");
		} 
		if (Util.isNullOrBlank(lastName)){
			throw new SHDRCException("Please enter Last Name");
		}else if(!ValidatorHelper.isValidNumericChar(lastName)){
			throw new SHDRCException("Please enter Valid Last Name");
		}
		if(Util.isNullOrBlank(mobile)){
			throw new SHDRCException("Please enter valid Mobile Number");
		}
		if(Util.isNullOrBlank(email)){
			throw new SHDRCException("Please enter Email ID");
		} 
		else if (!ValidatorHelper.isValidEmailPattern(email)){
			throw new SHDRCException("Invalid Email ID");
		}
		if(!ValidatorHelper.isNumeric(mobile)){
			throw new SHDRCException("Invalid Mobile Number");
		} 
		else if(mobile.length()<10){	
			throw new SHDRCException("Mobile Number should contain 10 digits");
		}
		String subStr = mobile.toString().substring(0, 1);
		if (subStr.equalsIgnoreCase("0")){
			throw new SHDRCException("Mobile Number cannot start with 0");
		}
	}

}
