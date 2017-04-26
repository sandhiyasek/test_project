/**
 * 
 */
package gov.shdrc.home.controller;

import gov.shdrc.home.service.IHomeManager;
import gov.shdrc.reports.service.ISDGSManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.News;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Upendra G
 *
 */
@Controller
public class HomeController {
	
	@Autowired(required=true)
	IHomeManager homeManager;
	@Autowired(required=true)
	ISDGSManager SDGSManager;
	
	@RequestMapping(value="/shdrcHome", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView sdgsDashboard(HttpServletRequest request){			
	   ModelAndView modelView=null;
	   String month=null;
	   int year=0;
	   try { 
		    modelView=new ModelAndView();
		    modelView.setViewName("sdgsDashboard");
			year=2015;
			month="Mar";	
			JSONArray distMaternalDeathData=SDGSManager.getDistMaternalDeathData(year,month);
			JSONArray maternalDeathMonVal = SDGSManager.getMaternalDeathMonVal(year,month);
			JSONArray maternalDeathThreshold = SDGSManager.getMaternalDeathThreshold();
			
			modelView.addObject("maternalDeathMonVal", maternalDeathMonVal);
			modelView.addObject("distMaternalDeathData", distMaternalDeathData);
			modelView.addObject("maternalDeathThreshold", maternalDeathThreshold);
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = UserInfo.getRolesForUser();
			String loggedUser=UserInfo.getLoggedInUserName();
			modelView.addObject("loggedUser",loggedUser);
			List<String> notificationList=homeManager.getNotificationList(isAllDirectorate,role);	 
			List<String> previousNotificationList=homeManager.getPreviousNotificationList(isAllDirectorate,role);
			List<CommonStringList> alertsList=homeManager.getNewsList(isAllDirectorate,role); 
			modelView.addObject("notificationList", notificationList);
			modelView.addObject("previousNotificationList", previousNotificationList);
			modelView.addObject("alertsList", alertsList);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{				month=null;
			} 
	   return modelView;
	}	
	@RequestMapping(value="/News", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws ServletException, IOException{	
		ModelAndView modelView=new ModelAndView();
		modelView.setViewName("fetchNews");
		
		Integer messageId=(Util.isNotNull(request.getParameter("messageId"))?Integer.parseInt(request.getParameter("messageId")):null);
		List<String> userRoleList = UserInfo.getUserRoleList(); 
		boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
		String role = UserInfo.getRolesForUser();
		News news=homeManager.getNews(messageId);
		List<CommonStringList> newsList=homeManager.getNewsList(isAllDirectorate,role); 
		modelView.addObject("newsObj", news);
		modelView.addObject("newsList", newsList);
		return modelView;
	}	
	@RequestMapping(value="/documentation", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView getTrainingMaterial(HttpServletRequest request){		
	   ModelAndView modelView=null;
	   modelView=new ModelAndView();
	   modelView.setViewName("trainingMaterial");
	   return modelView;
	}
	@RequestMapping(value="/userSessionInvalidate", method={RequestMethod.GET})
	public String userSessionCloser(HttpServletRequest request,HttpServletResponse response){
	  /* ModelAndView modelView=new ModelAndView();*/
			
	   response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
       response.setHeader("Pragma", "no-cache");
       response.setDateHeader("Expires", 0);      
	   HttpSession session = request.getSession(false);
	   if(session!=null){		
	     session.invalidate();
	   }
	   handleLogOutResponse(request,response);
	   
	   
	   /*modelView.setViewName("Login.jsp");
	   return modelView;*/
	   return "redirect:/login.do";
	 
	}
	private void handleLogOutResponse(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
		cookie.setMaxAge(0);
		cookie.setValue(null);
		cookie.setPath("/");
		response.addCookie(cookie);
		}
	}
}
