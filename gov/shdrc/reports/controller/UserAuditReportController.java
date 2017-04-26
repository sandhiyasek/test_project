package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.home.controller.StatisticsController;
import gov.shdrc.reports.service.IUserAuditReportManager;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class UserAuditReportController {
	private static Logger logger=Logger.getLogger(StatisticsController.class);
	@Autowired
	ICommonManager commonManager;
	@Autowired
	IUserAuditReportManager userAuditReportManager;
	
	@RequestMapping(value="/userAudit", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws ServletException, IOException{
		ModelAndView modelView=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		String month=null;
		Integer year;
		String directorateId;
		String freqName=null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("userAudit");		
			
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();		
	    	JSONArray jsonArray = Util.getColumnHearders();
			String encodedJsonArray = encodeJSONArray(jsonArray.toString());
			modelView.addObject("encodedJsonArray", encodedJsonArray);
	        
			directorateId=request.getParameter("directorateId");
			modelView.addObject("directorateId", directorateId);
			 
			 List<CommonStringList> directorateList=null;
			 if("'Chief Ministers Comprehensive Health Insurance Scheme'".equalsIgnoreCase(role)){			
				CommonStringList commonStringList = new CommonStringList();
				directorateList=new ArrayList<CommonStringList>();
				commonStringList.setId(17);
		        commonStringList.setName("Chief Ministers Comprehensive Health Insurance Scheme");
		        directorateList.add(commonStringList);			
			 }
			 else{
				directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
			 }
			 modelView.addObject("directorateList", directorateList);
			 
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelView.addObject("yearList", yearList);
			modelView.addObject("monthsList", monthsList);
			
			year = Calendar.getInstance().get(Calendar.YEAR);			
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			freqName="monthly";
			modelView.addObject("month", monthName);
			modelView.addObject("year", year);
			modelView.addObject("userAuditReportData", userAuditReportManager.getUserAuditReport(directorateId,year,month,freqName));
			
		}catch(Exception exp){
			
		}finally{
			monthsList=null;	
			yearList=null;
			freqName=null;
		}	      
		return modelView;		
	}
	@RequestMapping(value="/userAuditOnSearch", method=RequestMethod.POST)
	public @ResponseBody void userAuditOnSearch(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String month=null;
		String directorateId=null;
		int year=0;
		String freqName=null;
		try{
			PrintWriter out=response.getWriter();
			month=request.getParameter("month");
			if(month != null)
				month=month.substring(0,3);
			year=Integer.parseInt(request.getParameter("year"));
			directorateId=request.getParameter("directorateName");
			freqName=request.getParameter("freqName");
			JSONArray userAuditData=userAuditReportManager.getUserAuditReport(directorateId,year,month,freqName);
			out.println(userAuditData);
		}
		finally{
			month=null;
			directorateId=null;
			freqName=null;
		}
	}
	
	private String encodeJSONArray(String jsonArray){
		 String encodedJsonArray=null;
		try {
			encodedJsonArray = URLEncoder.encode(jsonArray, "UTF-8")   
					   .replaceAll("\\%28", "(")                          
					   .replaceAll("\\%29", ")")   		
					   .replaceAll("\\+", "%20")                          
					   .replaceAll("\\%27", "'")   			   
					   .replaceAll("\\%21", "!")
					   .replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			logger.error("error occured encodeJSONArray method in UserAuditReportController"+e);
		}
		 return encodedJsonArray;		
	}
	
}
