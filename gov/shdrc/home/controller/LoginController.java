/**
 * 
 */
package gov.shdrc.home.controller;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.home.service.ILoginManager;
import gov.shdrc.util.SecurityUtil;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
	private static Logger log=Logger.getLogger(LoginController.class);
	@Autowired(required=true)
	ILoginManager loginManager;

	@RequestMapping(value="/shdrcLogin", method=RequestMethod.GET)
	public ModelAndView shdrcLogin(HttpServletRequest request){	
		ModelAndView modelView=new ModelAndView();
		modelView.addObject("id", 1);
		modelView.setViewName("login");
		return modelView;
	}
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginView(HttpServletRequest request,HttpServletResponse response) throws NamingException, SHDRCException{
		ModelAndView modelView=new ModelAndView();
		modelView.addObject("id", 1);
		modelView.setViewName("login");
		return modelView;
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String shdrcUserVerification(HttpServletRequest request,HttpServletResponse response) throws NamingException, SHDRCException{
		
		log.debug("start");
		log.error("start");
		String viewName=null;		
		String userName=request.getParameter("userName");
		String userPassword=request.getParameter("userPassword");
		boolean isValidUser;
		
		String userIpAddress = request.getHeader("X-Forwarded-For");		 
	    if (userIpAddress == null || userIpAddress.length() == 0 || "unknown".equalsIgnoreCase(userIpAddress)) {
	    	userIpAddress = request.getHeader("Proxy-Client-IP");
	    }
	    if (userIpAddress == null || userIpAddress.length() == 0 || "unknown".equalsIgnoreCase(userIpAddress)) {
	    	userIpAddress = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (userIpAddress == null || userIpAddress.length() == 0 || "unknown".equalsIgnoreCase(userIpAddress)) {
	    	userIpAddress = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (userIpAddress == null || userIpAddress.length() == 0 || "unknown".equalsIgnoreCase(userIpAddress)) {
	    	userIpAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (userIpAddress == null || userIpAddress.length() == 0 || "unknown".equalsIgnoreCase(userIpAddress)) {
	    	userIpAddress = request.getRemoteAddr();
	    }
	    if (userIpAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
	        InetAddress inetAddress;
			try {
				inetAddress = InetAddress.getLocalHost();			
		        String ipAddress = inetAddress.getHostAddress();
		        userIpAddress = ipAddress;
		    }
		    catch (UnknownHostException e) {
				e.printStackTrace();
			}
	    }
		isValidUser = loginManager.validateUser(userName, userPassword,userIpAddress);
		//isValidUser=true;
		if(isValidUser){
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession();
			//invalidation session and create new session.
	        session.invalidate();
			// create new session for the user
			session = request.getSession(true);
			List<String> userRoles = SecurityUtil.getLDAPUserRoles(userName);
			//List<String> userRoles = UserInfo.getUserRoleList();
			String userTier=UserInfo.createUserTier(userRoles);
			session.setAttribute("userTier", userTier);
			session.setAttribute("userRoles", userRoles);
			session.setAttribute("userName", userName);
			/*PentahoSessionHolder.getInstance().setSession(session);
			PentahoSessionHolder.getInstance().setUserRoles(userRoles);
			PentahoSessionHolder.getInstance().setLoggedInUserName(userName);*/
			//String sessionId=RequestContextHolder.currentRequestAttributes().getSessionId();
			//System.out.println("sessionId"+sessionId);
			String directorateController=null;
			String roleShortName=null;
			if(userTier!=null && ShdrcConstants.UserTier.TIER4.equalsIgnoreCase(userTier)){
				for(String role:userRoles){
					roleShortName=Util.getRoleShortName(role);
					directorateController=getDirectorateController(roleShortName);
					if(directorateController!=null){
						viewName="redirect:"+directorateController;
						return viewName;
					}
			
				}
		    }
			return "redirect:/shdrcHome.do";
		}else{			
			viewName="login";			
			request.setAttribute("credentialsResponse", "UserName/Password is Invalid Please try again.");
		}
		return viewName;
	}
	
	private String getDirectorateController(String role){
		 String directorateController=null;
	       switch (role) {
	       	case ShdrcConstants.Role.DMS  : directorateController= "/dmsDataEntry.do";
	           				break;
	       	case ShdrcConstants.Role.DPH :  directorateController= "/dphDataEntry.do";
	        				break;
	       	case ShdrcConstants.Role.TNMSC : directorateController= "/tnmscDataEntry.do";
	           				break;
	       	case ShdrcConstants.Role.TANSACS : directorateController="/tansacsDataEntry.do";
	           				break;
	       	case ShdrcConstants.Role. NRHM :  directorateController= "/nrhmDataEntry.do";
	                    	 break;
	       	case ShdrcConstants.Role.RNTCP : directorateController= "/rntcpDataEntry.do";
	                    	 break;
	        case ShdrcConstants.Role.MA :  directorateController= "/maDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.DME :  directorateController= "/uploadExcel.do";
	                    	break;
			case ShdrcConstants.Role.DRMGR :  directorateController= "/drmgrDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.DFW :  directorateController= "/dfwDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.SHTO :  directorateController= "/shtdDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.COC :  directorateController= "/cocDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.SBCS :  directorateController= "/sbcsDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.DCA :  directorateController= "/dcaDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.MRB :  directorateController= "/mrbDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.DFS :  directorateController= "/dfsDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.CMCHIS :  directorateController= "/cmchisDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.DIM :  directorateController= "/dimDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.NLEP :  directorateController= "/nlepDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.SBHI :  directorateController= "/sbhiDataEntry.do";
	                    	break;
			case ShdrcConstants.Role.ESI :  directorateController= "/esiDataEntry.do";
	                    	break;			
			case ShdrcConstants.Role.ALLDIRECTORATE :  directorateController= "/dmsDataEntry.do";
	                    	break;
	       }
		 return directorateController;
	   }
	
	private String getUserIpAddress(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip; 
	}

}
