
package gov.shdrc.home.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.home.service.IMessageAlertManager;
import gov.shdrc.sms.MailSMSException;
import gov.shdrc.sms.SMSSender;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SplCharsConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.ValidatorHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
public class MessageAlertController {
	private static Logger logger = Logger.getLogger(MessageAlertController.class);
	@Autowired
	IMessageAlertManager messageAlertManager;
	@Autowired
	ICommonManager commonManager;

	@RequestMapping(value="/messageAlert", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws ServletException, IOException{	
		ModelAndView modelView=new ModelAndView();
		modelView.setViewName("msgAlert");
	
		List<String> userRoleList = UserInfo.getUserRoleList(); 
		boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
		String role = null;
		if(!isAllDirectorate)
			role = UserInfo.getRolesForUser();
		
		String csrfToken =null;
		 try {
			 csrfToken = CSRFTokenUtil.getToken (request.getSession());
			 modelView.addObject("CSRF_TOKEN", csrfToken);
			} catch (NoSuchAlgorithmException e) {
				//e.printStackTrace();
				logger.error("error occured onLoad method in MessageAlertController"+e);
			}       
        
        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
        modelView.addObject("directorateList", directorateList);
		
		return modelView;
	}
	
	@RequestMapping(value="/submitnewsnotification", method=RequestMethod.POST)
	public @ResponseBody void shdrcUserVerification(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
		PrintWriter out = response.getWriter();
		try{
			String userName = UserInfo.getLoggedInUserName();
            String message=request.getParameter("message");
            String messageType=request.getParameter("messageType");
            String newsHeader=request.getParameter("newsHeader");
            String[] directorateIds = request.getParameterValues("directorateName");
            String sendSMS=request.getParameter("sendSms");
            validateFields(messageType,newsHeader,message);
           
            StringBuilder strDirectorateIds=new StringBuilder();
            for(String str:directorateIds){
            	strDirectorateIds.append(str);
            	strDirectorateIds.append(SplCharsConstants.SINGLEQUOTE+SplCharsConstants.COMMA+SplCharsConstants.SINGLEQUOTE);
            }
            String directorateId=null;
            if(strDirectorateIds.length()>0){
            	directorateId=strDirectorateIds.substring(0,strDirectorateIds.length()-3);
            }
           if("Y".equalsIgnoreCase(sendSMS)){
            	SMSSender smsSender=new SMSSender();            	
    			List<String> userRoleList = UserInfo.getUserRoleList(); 
    			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
        		String mobileNo=commonManager.getMessageAlertMobileNo(directorateId,isAllDirectorate);
				CommonStringList smsStatus  =smsSender.sendSMS(mobileNo, message, "Bulk");
            }
            boolean successFlag=messageAlertManager.insertMessageAlert(directorateIds,sendSMS,message,messageType,userName,newsHeader);        
	        if(successFlag)
	        	out.println("Message published successfully");
			 else
				out.println("Problem in publishing the message");
            } catch (MailSMSException e) {
            	//e.printStackTrace();
            	logger.error("error occured shdrcUserVerification method in MessageAlertController"+e);
		}catch(SHDRCException e){
			out.println(e.getMessage());
		} 
	}
	
	private void validateFields(String messageType,String newsHeader,String message)throws SHDRCException{
		if("NS".equalsIgnoreCase(messageType)){
			if(!ValidatorHelper.isValidNotificationMessage(newsHeader)){
				throw new SHDRCException("Please enter Valid News Header");
			}
		}
		if(!ValidatorHelper.isValidNotificationMessage(message)){
			throw new SHDRCException("Please enter Valid Message");
		}
	}
	
}
