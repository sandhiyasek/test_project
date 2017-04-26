package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Calendar;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.ICommonReportManager;
import gov.shdrc.reports.service.ISBCSReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;



@Controller
public class SBCSReportController {
	@Autowired(required=true)
	ISBCSReportManager sbcsReportManager;
	@Autowired(required=true)
	ICommonReportManager commonReportManager;
	
/*	@RequestMapping(value="/sbcsDashboardZone", method=RequestMethod.GET)
	public ModelAndView onLoadDashboardZone(HttpServletRequest request){
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();		
		 String indicatorCategory="Monthly";		 
		 CommonStringList commonsList=sbcsReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
		 int year=commonsList.getId();
		 String month=commonsList.getName();
		 String loggedUser=UserInfo.getLoggedInUserName();
		 JSONArray indJsonArray=sbcsReportManager.getSBCSIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);
		 modelAndView.addObject("indJsonArray", indJsonArray);
		 modelAndView.addObject("yearList", Util.dashboardYearList);
		 modelAndView.addObject("monthsList", Util.monthsList);
		 modelAndView.addObject("directorateId",directorateId);
		 modelAndView.addObject("loggedUser",loggedUser);		       
         modelAndView.addObject("indicatorYer", year);
         modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName(month));
		 modelAndView.setViewName("sbcsDashboardZone");
		 return modelAndView;
	}*/
	@RequestMapping(value="/sbcsDashboardZone", method=RequestMethod.GET)
	public ModelAndView sbcsDashboardZoneNew(HttpServletRequest request) throws SHDRCException{		 
		ModelAndView modelAndView=null;
		String month=null;
		int year;
		String reportName=null;
		int directorateId;
		String loggedUser=null;
		try{
			modelAndView = new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.SBCS);
			loggedUser=UserInfo.getLoggedInUserName();
			modelAndView.setViewName("sbcsDashboardZoneNew");
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			String indicatorCategory="Monthly";		 
			CommonStringList commonsList=sbcsReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
			year=commonsList.getId();
			month=commonsList.getName();
			reportName="Eye Donation - State wise";			
			JSONArray indJsonArray = sbcsReportManager.getNewSbcsDashboardData(directorateId,reportName,year,month,loggedUser);
			modelAndView.addObject("yearList",  Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
			modelAndView.addObject("directorateId", directorateId);
			modelAndView.addObject("loggedUser",loggedUser);
			modelAndView.addObject("indicatorYer", year);
	        modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName(month));
			modelAndView.addObject("reportName", reportName);
			modelAndView.addObject("indJsonArray", indJsonArray);
		}finally{
			month=null;
			reportName=null;
			loggedUser=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/sbcsDashboardZoneNewSearchChange", method=RequestMethod.POST)
	public @ResponseBody void sbcsDashboardZoneNewSearchChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		PrintWriter out = response.getWriter();
		String reportName=request.getParameter("reportName");
		int year=Integer.parseInt(request.getParameter("selectedYear"));
		int monthTemp=Integer.parseInt(request.getParameter("monthName"));
		String month=Util.getMonthByIndex(monthTemp).substring(0,3);
		Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		String userName=UserInfo.getLoggedInUserName();
		JSONArray monthReportData =  sbcsReportManager.getNewSbcsDashboardData(directorateId,reportName,year,month,userName);
		out.println(monthReportData);
	}
	@RequestMapping(value="/sbcsReportZone", method=RequestMethod.GET)
	public ModelAndView onLoadReportZone(HttpServletRequest request)  throws SHDRCException
	{	
		ModelAndView modelAndView=null;
		String month=null;
		Integer year;
		Integer fromYear;
		String fromMonth=null;
		Integer toYear;
		String toMonth=null;
		String reportName=null;
		Integer directorateId;
		String userName=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		try{
			modelAndView = new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.SBCS);
			modelAndView.setViewName("sbcsReportZone");			
			year = Calendar.getInstance().get(Calendar.YEAR);				
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			fromYear = toYear = year;
			fromMonth = toMonth = month;
			reportName="Abstract of Cataract Surgeries performed";
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
			modelAndView.addObject("directorateId", directorateId);
			modelAndView.addObject("month", monthName);
			modelAndView.addObject("year", year);
			modelAndView.addObject("reportName", reportName);
			modelAndView.addObject("reportZoneData", sbcsReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName));
		}finally{
			monthsList=null;	
			yearList=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/sbcsReportZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getcorrespondingMonthData(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		PrintWriter out = response.getWriter();
		String reportName=request.getParameter("reportName");
		Integer fromYear=(Util.isNotNull(request.getParameter("fromYear"))?Integer.parseInt(request.getParameter("fromYear")):null);
		String fromMonth=request.getParameter("fromMonth");
		Integer toYear=(Util.isNotNull(request.getParameter("toYear"))?Integer.parseInt(request.getParameter("toYear")):null);
		String toMonth=request.getParameter("toMonth");
		if(fromMonth!=null && fromMonth.length()>3){
			fromMonth=fromMonth.substring(0,3);
		}	
		if(toMonth!=null && toMonth.length()>3){
			toMonth=toMonth.substring(0,3);
		}			
		Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		String userName=UserInfo.getLoggedInUserName();
		JSONArray monthReportData=null;
		monthReportData =  sbcsReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
		out.println(monthReportData);
	}
	
	@RequestMapping(value="/sbcsAnalysisZone", method=RequestMethod.GET)
	public ModelAndView onLoadAnalysisZone(HttpServletRequest request)throws SHDRCException{
		ModelAndView modelAndView=null;
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		String analysisReport4=null;
		Calendar cal=null;
		String month=null;
		Integer year;
		Integer directorateId;
		String userName=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		try{
			modelAndView=new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.SBCS);
			modelAndView.setViewName("sbcsAnalysisZone");			
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);		
			analysisReport1 = "Details of Eye Donation";
			analysisReport2 = "Details of Other Eye Diseases Yearly";
			analysisReport3 = "Details of Performance of Cataract Surgery - Govt Sector";
			analysisReport4 = "Details of Performance of Cataract Surgery - NGO Sector";
			year = Calendar.getInstance().get(Calendar.YEAR);	
			cal=Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();				
			JSONArray analysisZoneReport1Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
			JSONArray analysisZoneReport2Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
			JSONArray analysisZoneReport3Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
			JSONArray analysisZoneReport4Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);			
			modelAndView.addObject("directorateId", request.getParameter("directorateId"));
			modelAndView.addObject("analysisZoneReport1Data", analysisZoneReport1Data);
			modelAndView.addObject("analysisZoneReport2Data", analysisZoneReport2Data);
			modelAndView.addObject("analysisZoneReport3Data", analysisZoneReport3Data);
			modelAndView.addObject("analysisZoneReport4Data", analysisZoneReport4Data);
			modelAndView.addObject("year", year);
			modelAndView.addObject("month", monthName);
		}finally{
			cal=null;
			analysisReport1=null;
			analysisReport2=null;
			analysisReport3=null;
			analysisReport4=null;
			month=null;
		}
		return modelAndView;
	}
	@RequestMapping(value="/sbcsAnalysisZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getAnalysisZoneData(HttpServletRequest request,HttpServletResponse response) throws IOException,SHDRCException,JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;
		Integer year=(Util.isNotNull(request.getParameter("selectedYear"))?Integer.parseInt(request.getParameter("selectedYear")):null);
		String month=request.getParameter("monthName");
		if(month!=null && month.length()>3){
			month=month.substring(0,3);
		}		
		Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		String userName=UserInfo.getLoggedInUserName();
		data=new JSONObject();
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		String analysisReport4=null;
		analysisReport1 = "Details of Eye Donation";
		analysisReport2 = "Details of Other Eye Diseases Yearly";
		analysisReport3 = "Details of Performance of Cataract Surgery - Govt Sector";
		analysisReport4 = "Details of Performance of Cataract Surgery - NGO Sector";
		
		JSONArray analysisZoneReport1Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
		JSONArray analysisZoneReport2Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
		JSONArray analysisZoneReport3Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
		JSONArray analysisZoneReport4Data= sbcsReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
		data.put("analysisZoneReport1Data", analysisZoneReport1Data);
        data.put("analysisZoneReport2Data", analysisZoneReport2Data);
        data.put("analysisZoneReport3Data", analysisZoneReport3Data);
        data.put("analysisZoneReport4Data", analysisZoneReport4Data);
		out.println(data);
	}
	
	@RequestMapping(value="/sbcsIndicatorZone", method=RequestMethod.GET)
	public ModelAndView sbcsOnLoadIndicatorZone(HttpServletRequest request)throws SHDRCException, JSONException
	{	
		ModelAndView modelAndView=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
        int directorateId=0;
        String indicatorCategory= null;
		String indicators=null;
		String district=null;
		String generalCategory=null;
		try{
			modelAndView = new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.SBCS);
			modelAndView.setViewName("sbcsIndicatorZone");
			directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			modelAndView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			modelAndView.addObject("yearList", Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
	        List<String> IndicatorCategoryList=sbcsReportManager.getIndicatorCategories(directorateId);
	        indicatorCategory=IndicatorCategoryList.get(0);
	        modelAndView.addObject("IndicatorCategoryList", IndicatorCategoryList);
	        modelAndView.addObject("indicatorCategory", indicatorCategory);
	        CommonStringList commonsList=commonReportManager.getIndicatorMaxMonthAndYear(directorateId,indicatorCategory);
			maxYear=commonsList.getId();
			maxMonth=commonsList.getName().substring(0, 3);
			modelAndView.addObject("month", commonsList.getName());
			modelAndView.addObject("year", maxYear);
	      //current year is from year ,to year and current month is from month,to month
	        JSONArray indPvtStateData=sbcsReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,maxMonth,maxYear,maxMonth,maxYear,userName);
	        modelAndView.addObject("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        modelAndView.addObject("selectedIndicator", indicators);
		        List<String> generalCategoryList=sbcsReportManager.getGeneralCategory(directorateId,indicators);
		        modelAndView.addObject("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        modelAndView.addObject("generalCategory", generalCategory);
		        JSONArray indPvtDistData=sbcsReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators, generalCategory, maxMonth, maxYear, maxMonth, maxYear, userName);
		        modelAndView.addObject("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        district=districtObj.get("District").toString();
		        modelAndView.addObject("selectedDistrict", district);
		        JSONArray indPvtInstData=sbcsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,maxMonth,maxYear,maxMonth,maxYear,userName);
		        modelAndView.addObject("indPvtInstData", indPvtInstData);
	        }
	        }
		}finally{
			maxMonth=null;
			maxMonth=null;
			userName=null;
			indicatorCategory= null;
	        indicators =null;
	        district=null;
		}
		return modelAndView;
	}   	
	@RequestMapping(value="/sbcsOnSearch", method=RequestMethod.POST)
	public @ResponseBody void sbcsGetIndicatorData(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, JSONException, IOException{
        int directorateId=0;
        String userName=null;
        int fromYear=0;
        int toYear=0;
        String indicatorCategory= null;
        String fromMonth= null;
        String fromYr = null;
        String toMonth = null; 
        String toYr = null;
        PrintWriter out=null;
        JSONObject data=null;
        String indicators=null;
        String district=null;
        String generalCategory=null;
        try{
	        data=new JSONObject();
	        out=response.getWriter();
	        directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			userName=UserInfo.getLoggedInUserName();
	        indicatorCategory = request.getParameter("category");
	        fromMonth = request.getParameter("fromMonth");
	        fromYr = request.getParameter("fromYear");
	        toMonth = request.getParameter("toMonth");
	        toYr = request.getParameter("toYear");
	        fromYear = Integer.parseInt(fromYr);
	        toYear = Integer.parseInt(toYr);
	        fromMonth = fromMonth.substring(0, 3);
	        toMonth = toMonth.substring(0, 3);
	        JSONArray indPvtStateData=sbcsReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,fromMonth,fromYear,toMonth,toYear,userName);
	        data.put("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        data.put("selectedIndicator", indicators);
		        List<String> generalCategoryList=sbcsReportManager.getGeneralCategory(directorateId,indicators);
		        data.put("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        data.put("generalCategory", generalCategory);
		        JSONArray indPvtDistData=sbcsReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators,generalCategory, fromMonth, fromYear, toMonth, toYear, userName);
		        data.put("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
			        JSONObject districtObj=indPvtDistData.getJSONObject(0);
			        district=districtObj.get("District").toString();
			        data.put("selectedDist", district);
			        JSONArray indPvtInstData=sbcsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
			        data.put("indPvtInstData", indPvtInstData);
		        }
	        }
	        out.println(data);
        }finally{
			userName=null;
			indicatorCategory= null;
	        fromMonth= null;
	        toMonth = null; 
	        fromYr = null;
	        toYr = null;
	        district=null;
	        indicators=null;
	        generalCategory=null;
		}
	}
	@RequestMapping(value="/sbcsDistrictData", method=RequestMethod.POST)
	public @ResponseBody void sbcsGetdistrictData(HttpServletRequest request,HttpServletResponse response)throws SHDRCException, JSONException, IOException{
        int directorateId=0;
        String userName=null;
        int fromYear=0;
        int toYear=0;
        String category= null;
        String fromMonth= null;
        String fromYr = null;
        String toMonth = null; 
        String toYr = null;
        PrintWriter out=null;
        JSONObject data=null;
        String indicator=null;
        String district=null;
        String generalCategory=null;
        try{
        data=new JSONObject();
        out=response.getWriter();
        directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		userName=UserInfo.getLoggedInUserName();
        indicator=request.getParameter("selectedIndicator");
        category = request.getParameter("category");
        fromMonth = request.getParameter("fromMonth");
        fromYr = request.getParameter("fromYear");
        toMonth = request.getParameter("toMonth");
        toYr = request.getParameter("toYear");
        fromYear = Integer.parseInt(fromYr);
        toYear = Integer.parseInt(toYr);
        fromMonth = fromMonth.substring(0, 3);
        toMonth = toMonth.substring(0, 3);
        List<String> generalCategoryList=sbcsReportManager.getGeneralCategory(directorateId,indicator);
        data.put("generalCategoryList", generalCategoryList);
        generalCategory=generalCategoryList.get(0);
        data.put("generalCategory", generalCategory);
        JSONArray indPvtDistData=sbcsReportManager.getIndicatorPvtDistrictData(directorateId,category,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
        data.put("indPvtDistData", indPvtDistData);
        if(indPvtDistData.length() != 0){
	        JSONObject dtObj=indPvtDistData.getJSONObject(0);
	        district=dtObj.get("District").toString();
	        data.put("district", district);  
	        JSONArray indPvtInstData=sbcsReportManager.getIndicatorPvtInstitutionData(directorateId,category,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
	        data.put("indPvtInstData", indPvtInstData);
        }
        out.println(data);
        }finally{
			userName=null;
			category= null;
	        fromMonth= null;
	        toMonth = null; 
	        fromYr = null;
	        toYr = null;
	        indicator=null;
	        generalCategory=null;
	        district=null;
		}
	}
	@RequestMapping(value="/sbcsOnTabChange", method=RequestMethod.POST)
	public @ResponseBody void sbcsOnTabChange(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException, SHDRCException{
        int directorateId=0;
        String userName=null;
        int fromYear=0;
        int toYear=0;
        String indicatorCategory= null;
        String fromMonth= null;
        String fromYr = null;
        String toMonth = null; 
        String toYr = null;
        PrintWriter out=null;
        JSONObject data=null;
        String indicator=null;
        String District=null;
        String generalCategory=null;
        try{
        data=new JSONObject();
        out=response.getWriter();
        directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		userName=UserInfo.getLoggedInUserName();
        indicator=request.getParameter("seletecdInd");
        indicatorCategory = request.getParameter("category");
        fromMonth = request.getParameter("fromMonth");
        fromYr = request.getParameter("fromYear");
        toMonth = request.getParameter("toMonth");
        toYr = request.getParameter("toYear");
        generalCategory = request.getParameter("generalCategory");
        fromYear = Integer.parseInt(fromYr);
        toYear = Integer.parseInt(toYr);
        fromMonth = fromMonth.substring(0, 3);
        toMonth = toMonth.substring(0, 3);
        data.put("generalCategory", generalCategory);  
        JSONArray indPvtDistData=sbcsReportManager.getIndicatorPvtDistrictData(directorateId,indicatorCategory,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
        data.put("indPvtDistData", indPvtDistData);
        if(indPvtDistData.length() != 0){
	        JSONObject districtObj=indPvtDistData.getJSONObject(0);
	        District=districtObj.get("District").toString();
	        data.put("District", District);  
	        JSONArray indPvtInstData=sbcsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,District,fromMonth,fromYear,toMonth,toYear,userName);
	        data.put("indPvtInstData", indPvtInstData);
        } 
        out.println(data);
        }finally{
			userName=null;
			indicatorCategory= null;
	        fromMonth= null;
	        toMonth = null; 
	        fromYr = null;
	        toYr = null;
	        indicator=null;
	        generalCategory=null;
	        District=null;
		}
	}
	@RequestMapping(value="/sbcsInstitutionData", method=RequestMethod.POST)
	public @ResponseBody void sbcsGetInstitutionData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, SHDRCException{
        int directorateId=0;
        String userName=null;
        int fromYear=0;
        int toYear=0;
        String indicatorCategory= null;
        String fromMonth= null;
        String fromYr = null;
        String toMonth = null; 
        String toYr = null;
        PrintWriter out=null;
        JSONObject data=null;
        String indicator=null;
        String district=null;
        String generalCategory=null;
        try{
        data=new JSONObject();
        out=response.getWriter();
        directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		userName=UserInfo.getLoggedInUserName();
        indicator=request.getParameter("seletecdInd");
        indicatorCategory = request.getParameter("category");
        district=request.getParameter("selectedDist");
        fromMonth = request.getParameter("fromMonth");
        fromYr = request.getParameter("fromYear");
        toMonth = request.getParameter("toMonth");
        toYr = request.getParameter("toYear");
        generalCategory = request.getParameter("generalCategory");
        fromYear = Integer.parseInt(fromYr);
        toYear = Integer.parseInt(toYr);
        fromMonth = fromMonth.substring(0, 3);
        toMonth = toMonth.substring(0, 3);
        JSONArray indPvtInstData=sbcsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);    
        data.put("indPvtInstData", indPvtInstData);
        out.println(data);
        }finally{
			userName=null;
			indicatorCategory= null;
	        fromMonth= null;
	        toMonth = null; 
	        fromYr = null;
	        toYr = null;
	        indicator=null;
		}
	}
	@RequestMapping(value="/sbcsFreeHandZone", method=RequestMethod.GET)
	public ModelAndView FreeHandZone(HttpServletRequest request) throws SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();
		 UserInfo.hasValidReportAccess(ShdrcConstants.Role.SBCS);
		 List indCategory=sbcsReportManager.getFreeHandZoneIndCategory(directorateId);
		 List<CommonStringList> indNamesByCategory=sbcsReportManager.getFreeHandZoneIndNamesByCategory(directorateId,String.valueOf(indCategory.get(0)));
		 List indYearByNameandCate=sbcsReportManager.getIndYearByNameandCategory(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()));
		 JSONArray indJsonArray=sbcsReportManager.getFreeHandZoneData(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()),Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
		 modelAndView.addObject("indJsonArray", indJsonArray);
		 modelAndView.addObject("indCategory", indCategory);
		 modelAndView.addObject("indNamesByCategory", indNamesByCategory);
		 modelAndView.addObject("yearList", indYearByNameandCate);
		 modelAndView.addObject("selectedIndicator", String.valueOf(indNamesByCategory.get(0).getId()));
		 request.setAttribute("selectedIndicator", String.valueOf(indNamesByCategory.get(0).getId()));
		 modelAndView.addObject("currentYear", Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
		 modelAndView.addObject("directorateId",directorateId);           
		modelAndView.setViewName("esiFreeHandZone");
		
		 return modelAndView;		 
	}
	
	@RequestMapping(value="/sbcsOnCategoryChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onCategoryChangeSearch(HttpServletRequest request,HttpServletResponse response) throws IOException, SHDRCException{
		 String indicatorCategory=request.getParameter("IndicatorCategory");		
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		 PrintWriter out=null;
		 out = response.getWriter();		 
	     List<CommonStringList> indNamesByCategory=sbcsReportManager.getFreeHandZoneIndNamesByCategory(directorateId,indicatorCategory);
	     String json=new Gson().toJson(indNamesByCategory);  
	     out.println(json);
	}
	@RequestMapping(value="/sbcsOnIndicatorNameChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onIndicatorNameChangeSearch(HttpServletRequest request,HttpServletResponse response) throws IOException, SHDRCException{
		 String indicatorCategory=request.getParameter("IndicatorCategory");
		 String indicatorName=request.getParameter("IndicatorName");
		 int directorateId=Integer.parseInt(request.getParameter("DirectorateId"));
		 PrintWriter out=null;
		 out = response.getWriter();		 
		 List indYearByNameandCate=sbcsReportManager.getIndYearByNameandCategory(directorateId,indicatorCategory,indicatorName);
		 String json=new Gson().toJson(indYearByNameandCate);  
	     out.println(json);
	}
	@RequestMapping(value="/sbcsFreeHandZoneBySearch", method=RequestMethod.POST)
	public  @ResponseBody String ShowFreeHandZoneBySearcht(HttpServletRequest request,HttpServletResponse response) throws JSONException, SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		  String indicatorCategory=request.getParameter("IndicatorCategory");
		  String indCategoryName=request.getParameter("IndicatorName");
		  String year=request.getParameter("Year");
		  JSONObject object=new JSONObject();      	
	 	  JSONArray indJsonArray=sbcsReportManager.getFreeHandZoneData(directorateId,indicatorCategory,indCategoryName,Integer.parseInt(year));
			object.put("freeHandZoneData", indJsonArray);
			/*object.put("specifiedCategory", indicatorCategory);
			object.put("specifiedIndicators", indCategoryName);
		 	object.put("specifiedYear", year);*/	 	  
	     return object.toString();
	}
	   	
	@RequestMapping(value="/sbcsReportForDistrict", method=RequestMethod.POST)
	public  @ResponseBody String ShowDistrictReport(HttpServletRequest request) throws JSONException, SHDRCException{		
		  JSONObject jsonObj=new JSONObject();
		  int directorateID=Integer.parseInt(request.getParameter("DirectorateID"));
		  String indicatorCategory=request.getParameter("IndFrequency");
		  int year=Integer.parseInt(request.getParameter("Year"));
		  int month=Integer.parseInt(request.getParameter("Month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();		  
		  String indicatorName=request.getParameter("SelectedIndicatorName");		  
	      JSONObject object=new JSONObject();
    	  JSONArray jsnArray=sbcsReportManager.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName, year, indMonth, loggedUser);
    	  request.setAttribute("indicatorDetailsforDistrict", jsnArray);
	      object.put("indicatorDetailsforDistrict",jsnArray);	     
	      return object.toString();
	}
	@RequestMapping(value="/sbcsReportForInstitution", method=RequestMethod.POST)
	public  @ResponseBody String ShowInstitutionReport(HttpServletRequest request) throws JSONException, SHDRCException{	
		  JSONObject jsonObj=new JSONObject();
		  int directorateID=Integer.parseInt(request.getParameter("DirectorateID"));
		  String indicatorCategory=request.getParameter("IndFrequency");
		  int year=Integer.parseInt(request.getParameter("Year"));
		  int month=Integer.parseInt(request.getParameter("Month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();
		  String districtName=request.getParameter("Disrtict");	
		  String indicatorName=request.getParameter("Indicator");	
	      JSONObject object=new JSONObject();
    	  JSONArray jsnArray=sbcsReportManager.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,districtName, year, indMonth, loggedUser);
    	  object.put("indicatorDetailsforDistrict",jsnArray);
	      return object.toString();
	}    
	@RequestMapping(value="/sbcsSearchIndicatorList", method=RequestMethod.POST)
	public ModelAndView  searchIndicatorListByFilter(HttpServletRequest request,HttpServletResponse response) throws SHDRCException{	    		
		ModelAndView modelView=new ModelAndView();
		UserInfo.hasValidReportAccess(ShdrcConstants.Role.SBCS);
		String indicatorFrequency=request.getParameter("indFrequency");
		String indicatorYer=request.getParameter("year");
		String indicatorMnth=request.getParameter("month");
		String indicatorQuarter=request.getParameter("quarter");
		indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
		int directorateId=Integer.parseInt(request.getParameter("directorateId"));
		indicatorMnth=new StringBuilder(indicatorMnth).substring(0, 3);
		String loggedUser=UserInfo.getLoggedInUserName();    		
		JSONArray indJsonArray=sbcsReportManager.getSBCSIndicatorList(directorateId, indicatorFrequency, Integer.parseInt(indicatorYer), indicatorMnth, loggedUser);  		
		modelView.addObject("indJsonArray", indJsonArray);
		modelView.addObject("indicatorFrqncy", indicatorFrequency);
		modelView.addObject("indicatorYer", indicatorYer);
		modelView.addObject("indicatorMnth", request.getParameter("month"));	
		modelView.addObject("yearList", Util.yearList);
		modelView.addObject("monthsList", Util.monthsList);
		modelView.addObject("directorateId",directorateId);	
		modelView.addObject("loggedUser",loggedUser);
		modelView.setViewName("sbcsDashboardZone");
    	return modelView;
	} 
}
