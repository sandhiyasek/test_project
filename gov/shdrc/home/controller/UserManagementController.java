package gov.shdrc.home.controller;




import gov.shdrc.dataentry.dao.IUserManagementDAO;
import gov.shdrc.dataentry.service.IMaDataEntryManager;
import gov.shdrc.dataentry.service.IUserManagementService;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.DFWEmployeeForm;
import gov.shdrc.util.SecurityUtil;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.UserManagement;
import gov.shdrc.util.Util;
import gov.shdrc.util.ValidatorHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;





import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserManagementController {
	@Autowired
	IUserManagementService userManagementService;
	@Autowired
	IUserManagementDAO userManagementDAO;
@RequestMapping(value="/userManagement", method=RequestMethod.GET)
public ModelAndView onLoadses(HttpServletRequest request){			
   ModelAndView modelView=null;
  // Object directorateId = Integer.parseInt(request.getParameter("directorateId"));

   try { 
	   
	    modelView=new ModelAndView();	    
	    List<CommonStringList> directoratesList= userManagementService.getDirectoratesList();
	    modelView.addObject("DeroctratelList",directoratesList);	
	    modelView.setViewName("userManagementCheckAction");
	    
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
		} 
   return modelView;
}	
	


@RequestMapping(value="/editUserManagement", method=RequestMethod.GET)
public ModelAndView onLoadEdit(HttpServletRequest request){			
   ModelAndView modelView=null;
  // Object directorateId = Integer.parseInt(request.getParameter("directorateId"));

   try { 
	   
	    modelView=new ModelAndView();	    
	    List<CommonStringList> directoratesList= userManagementService.getDirectoratesList();
	    modelView.addObject("DeroctratelList",directoratesList);
	    modelView.setViewName("editUserManagementCheckAction");
	    
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
		} 
   return modelView;
}	



@RequestMapping(value="/userManagementDetails", method=RequestMethod.POST)
public @ResponseBody void shdrcUserVerification(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
	PrintWriter out = response.getWriter();
	try{
		String userName =request.getParameter("userName");
        String firstName=request.getParameter("firstName");
        String lastName=request.getParameter("lastName");
        String email=request.getParameter("email");
        long mobile = Long.parseLong(request.getParameter("mobile"));
        String password=request.getParameter("password");
        int DeroctrateListId=Integer.parseInt(request.getParameter("DeroctrateListId"));
        
        String tierVal=request.getParameter("tierVal");
        String designation=request.getParameter("designation"); 
        String snName=request.getParameter("snName");
        validateFields(userName,firstName,lastName,email,mobile,password);
       
        boolean successFlag=userManagementService.saveUserManagementDetails(userName,firstName,lastName,email,mobile,password,DeroctrateListId,tierVal,designation,snName);
        if(successFlag)
			out.println("User Details has been Saved successfully");
		 else
			out.println("User Already exists");
	}catch(Exception e){
		out.println(e.getMessage());
	} 
	
	
}
@RequestMapping(value="/UpdateUserManagementValues", method=RequestMethod.POST)
public @ResponseBody void shdrcUpdateAction(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
	PrintWriter out = response.getWriter();
	try{
	
		
		
		
		String userName =request.getParameter("userName");
        String firstName=request.getParameter("firstName");
        String lastName=request.getParameter("lastName");
        String email=request.getParameter("email");
        long mobile = Long.parseLong(request.getParameter("mobile"));
       // String password=request.getParameter("password");
        
        String designation=request.getParameter("designation"); 
        int UserId= Integer.parseInt(request.getParameter("UserId"));
      //  int derId= Integer.parseInt(request.getParameter("derId"));
        /*String oldpassword=request.getParameter("oldpassword");
        String actualPassword=SecurityUtil.digestMd5(oldpassword);
        validateFieldsforUpdate(password,actualPassword);*/
       
        boolean successFlag=userManagementService.updateUserManagementValues(firstName,lastName,email,mobile,designation,UserId);
        if(successFlag)
			out.println("User Details has been Updated successfully");
		 else
			out.println("User Already exists");
	}catch(Exception e){
		out.println(e.getMessage());
	} 
	
	
}






@RequestMapping(value="/editUserManagementDetails", method=RequestMethod.GET)

public @ResponseBody ModelAndView shdrcEditUserOnLoad(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
	PrintWriter out = response.getWriter();
	 ModelAndView modelView=null;
try { 

	modelView=new ModelAndView();	    
    modelView.setViewName("editUserManagementCheckActionOnLoad");
    
    List<CommonStringList> directoratesList= userManagementService.getDirectoratesList();
    modelView.addObject("DeroctratelList",directoratesList);   

    Integer DeroctrateListId=(Util.isNotNull(request.getParameter("DeroctrateListId"))?Integer.parseInt(request.getParameter("DeroctrateListId")):null);		
    modelView.addObject("DeroctrateListId",DeroctrateListId);   
    //Integer DeroctrateListId=2;		
	
    List<UserManagement> userManagementList=userManagementService.getUserManagemenListByDeroctrateId(DeroctrateListId);
    modelView.addObject("userManagementList",userManagementList);  		
	
	}
catch(Exception e){
	out.println(e.getMessage());
} 
finally{} 
return modelView;
	
}
	

@RequestMapping(value="/updateUserManagementDetails", method=RequestMethod.GET)
public ModelAndView onLoads(HttpServletRequest request){			
   ModelAndView modelView=null;
   

   try {
	   String directorateId = request.getParameter("directorateId");
       int userId=Integer.parseInt(request.getParameter("userId"));

	 
	   List<UserManagement> userMasterListByUserId= userManagementService.getUserMasterDetails(userId);
	   modelView=new ModelAndView();
	    modelView.setViewName("updateUserManagementDetailsAction");
		   modelView.addObject("userMasterListByUserId",userMasterListByUserId);  	

		   UserManagement  user =(UserManagement)userMasterListByUserId.get(0);
		  
	 String userName= user.getUserName();
	 String userfirstName=user.getUfname();
	 String userLastName=user.getUlname();
	 String oldpassword =user.getPassword();
	
	 String email =user.getEmail();
	 String phnNo= user.getPhnNum();
	 String tierCD=user.getTierCD();
	
	 String deroctrateListName= user.getDirectrateName();
	 String derId=user.getDirectrateId();
	 
	/* int deroctrateListId=Integer.parseInt("deroctrateListName");
	 String directrateName= userManagementDAO.getDerectrateName(deroctrateListId);*/
	 
	 
	 int UserId=user.getUserIds();
	
	 
	 String disgnation=user.getDesignation();
	 
	 modelView.addObject("userName",userName); 
	 modelView.addObject("userfirstName",userfirstName); 
	 modelView.addObject("userLastName",userLastName);
	 modelView.addObject("email",email);
	 modelView.addObject("phnNo",phnNo);
	 modelView.addObject("disgnation",disgnation);
	 modelView.addObject("UserId",UserId);
	modelView.addObject("tierCD",tierCD);
	modelView.addObject("deroctrateListName",deroctrateListName);
	modelView.addObject("derId",derId);
	modelView.addObject("oldpassword",oldpassword);
	 modelView.addObject("directorateId",directorateId); 
	    
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
		} 
   return modelView;
}


@RequestMapping(value="/ChangePwdValues", method=RequestMethod.POST)
public @ResponseBody void ChangePwdValuesAction(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
	PrintWriter out = response.getWriter();
	try{
	
		
		String userName =request.getParameter("userName");
		String oldPassword=request.getParameter("oldpassword");

		String newPass=request.getParameter("npass");
		 String newPassword=SecurityUtil.digestMd5(newPass);
		String rNewPass=request.getParameter("rnpass");
		String reTypePassword=SecurityUtil.digestMd5(rNewPass);
		 int UserId= Integer.parseInt(request.getParameter("UserId"));
		 int derId= Integer.parseInt(request.getParameter("derId"));
		 
		 validateFieldsforUpdate(newPass,rNewPass);
        
      
   
        boolean successFlag=userManagementService.changePwdUserManagementValues(userName,newPassword,UserId,derId);
        if(successFlag)
			out.println("User Password Details has been Reseted successfully");
		 else
			out.println("User Password Details has been Not Saved");
	}catch(Exception e){
		out.println(e.getMessage());
	} 
	
	
}

@RequestMapping(value="/changePasswordDetails", method=RequestMethod.GET)
public ModelAndView onChangePwdLoad(HttpServletRequest request){			
   ModelAndView modelView=null;
   
   try {
	   String directorateId = request.getParameter("directorateId");
       int userId=Integer.parseInt(request.getParameter("userId"));
       
       List<UserManagement> userMasterListByUserId= userManagementService.getUserMasterDetails(userId);
	   modelView=new ModelAndView();
	   modelView.setViewName("changePwdUserManagementDetailsAction");
	   modelView.addObject("userMasterListByUserId",userMasterListByUserId);  	

	   UserManagement  user =(UserManagement)userMasterListByUserId.get(0);
	   String userName= user.getUserName();
	   String oldpassword =user.getPassword();
	   String derId=user.getDirectrateId();
	   int UserId=user.getUserIds();
	
	 
	   modelView.addObject("userName",userName); 
	   modelView.addObject("oldpassword",oldpassword);
	   modelView.addObject("derId",derId);
	   modelView.addObject("UserId",UserId);


	   modelView.addObject("directorateId",directorateId); 
	    
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
		} 
   return modelView;
}
	

private void validateFields(String userName,String firstName,String lastName,String email,long mobile,String password)throws SHDRCException{
	if(Util.isNullOrBlank(userName)){
		throw new SHDRCException("Please enter User Name");
	} 
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
	if(Util.isNullOrBlank(password)){
		throw new SHDRCException("Please enter  Password");
	}else if(password.length()<8){
		throw new SHDRCException("Please enter atleast 8 char in password");
	}else if(password.length()>15){
		
		throw new SHDRCException("The  password should not exceed more than 15 characters");
	} else if(!ValidatorHelper.isValidSplCharacter(password)){
		throw new SHDRCException("The  password must contain at least one lower case letter, one upper case letter, one digit and one special character");
	}
	
	
	
	
}

private void validateFieldsforUpdate(String newPass,String rNewPass)throws SHDRCException{
	
	
	if(Util.isNullOrBlank(newPass)){
		throw new SHDRCException("Please enter New Password");
	}else if(newPass.length()<8){
		throw new SHDRCException("Please enter atleast 8 char in password");
	}else if(newPass.length()>15){
		
		throw new SHDRCException("The New password should not exceed more than 15 characters");
	} else if(!ValidatorHelper.isValidSplCharacter(newPass)){
		throw new SHDRCException("The New password must contain at least one lower case letter, one upper case letter, one digit and one special character");
	}

	if(Util.isNullOrBlank(rNewPass)){
		throw new SHDRCException("Please enter Re-Type New Password");
	}else if(rNewPass.length()<8){
		throw new SHDRCException("please enter atleast 8 char in password");
	}else if(rNewPass.length()>15){
		throw new SHDRCException("The Retype password should not exceed more than 15 characters");
	}else if(!ValidatorHelper.isValidSplCharacter(rNewPass)){
		throw new SHDRCException("The New password must contain at least one lower case letter, one upper case letter, one digit and one special character");
	}
	if(!rNewPass.equalsIgnoreCase(rNewPass)){
		throw new SHDRCException("New Password does not match with retype password");
	 }
	
	
	
	
}





}



