package gov.shdrc.util;

import gov.shdrc.exception.SHDRCException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserInfo {
	
	public static String getLoggedInUserName(){
		//IPentahoSession session = PentahoSessionHolder.getSession();
		//String userName = session.getName().toString();
		//return userName;
		//return "tnmsc_de01";
		//return "dfw_nd01";
		//return "drmgr_de02";
		//return "shtd_de01";
		//IPentahoSession session = PentahoSessionHolder.getSession();
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String userName = attr.getRequest().getSession().getAttribute("userName").toString();
		return userName;
	}
	public static String getRolesForUser(){
		List<String> roleList = getUserRoleList();
		 StringBuilder strRole=new StringBuilder();
		 String role=null;
		 for(String abbrRole : roleList){
			 role=Util.getFullRole(abbrRole);
			 if(Util.isNotNull(role)){
				 strRole.append(SplCharsConstants.SINGLEQUOTE+role+SplCharsConstants.SINGLEQUOTE+SplCharsConstants.COMMA);
			  }
		 }
		if(strRole.length()>0){
			role = strRole.substring(0, strRole.length() - 1) ;
			return role;
		}
		return null;
		
	}
	
	public static boolean isAllDirectorate(List<String> userRoleList){
		return userRoleList.contains(ShdrcConstants.ALLDIRECTORATE);
	}
	
	public static List<String> getUserRoleList(){
		//String userName = getLoggedInUserName();
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		List<String> userPentahoRoleList = (List<String>)attr.getRequest().getSession().getAttribute("userRoles");
		List<String> userRoleList = new ArrayList<String>();
		for(String userPentahoRole:userPentahoRoleList){
			String  userShortRole=Util.getRoleShortName(userPentahoRole);
			if(userShortRole!=null)
				userRoleList.add(userShortRole);
			
			//The following check for DEV and Test Server
			/*if("Administrator".equals(userPentahoRole))	
				userRoleList.add("AllDirectorate");*/
		}
		
		//The following check for Prod
		if(userPentahoRoleList.contains("Administrator") && userPentahoRoleList.contains("AllDirectorate")){
			userRoleList.add("AllDirectorate");
		}
		
		/*List<String> userRoleList = new ArrayList<String>();
		//userRoleList.add("AllDirectorate");
		//userRoleList.add("SHTO");
		//userRoleList.add("PVTSEC");
		//userRoleList.add("DFW");
		//userRoleList.add("DRMGR");
		userRoleList.add("DMS T4");*/
		return userRoleList;
	}
	
	public static String getUserTier(){
			//String userName = getLoggedInUserName();
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			String userTier = attr.getRequest().getSession().getAttribute("userTier").toString();
			return userTier;
	}
	public static String createUserTier(List<String> userPentahoRoleList){
		String userTier = null;
		List<String> userRoleList = new ArrayList<String>();
		for(String userPentahoRole:userPentahoRoleList){
				//if(!"Authenticated".equals(userPentahoRole)){
					//if(!"Administrator".equals(userPentahoRole)){
						String  userShortRole=Util.getRoleShortName(userPentahoRole);
						if(userShortRole!=null && !userShortRole.isEmpty()){
							userRoleList.add(userShortRole);
							int index = userPentahoRole.lastIndexOf("T");
							if(index!=-1){
								userTier=userPentahoRole.substring(index, userPentahoRole.length()).trim();
							}
						}
						
					//}
				//}	
		}
		if(userPentahoRoleList.contains("Administrator") && userPentahoRoleList.contains("AllDirectorate")){	
			userRoleList.add("AllDirectorate");
			userTier="T1";
		}
		return userTier;
	}
	public static void hasValidReportAccess(String roleName) throws SHDRCException{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		List<String> userRoleList = (List<String>)attr.getRequest().getSession().getAttribute("userRoles");
		if(!(userRoleList.contains(roleName)||userRoleList.contains(ShdrcConstants.ALLDIRECTORATE))){
			throw new SHDRCException("The current user does not have access to view this page");
		}
	}
	
	public static void hasValidDataEntryAccess(String roleName) throws SHDRCException{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String userTier = attr.getRequest().getSession().getAttribute("userTier").toString();
		roleName=roleName+SplCharsConstants.EMPTY+userTier;
		List<String> userRoleList = (List<String>)attr.getRequest().getSession().getAttribute("userRoles");
		if(!(userRoleList.contains(roleName)||userRoleList.contains(ShdrcConstants.ALLDIRECTORATE))){
			throw new SHDRCException("The current user does not have access to view this page");
		}
	}
}
