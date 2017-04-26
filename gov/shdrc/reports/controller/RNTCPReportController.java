package gov.shdrc.reports.controller;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.dataentry.service.IRntcpDataEntryManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.ICommonReportManager;
import gov.shdrc.reports.service.IRNTCPReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class RNTCPReportController {
	@Autowired(required=true)
	IRNTCPReportManager rntcpReportManager;
	
	@Autowired
	IRntcpDataEntryManager rntcpDataEntryManager;
	@Autowired(required=true)
	ICommonReportManager commonReportManager;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/rntcpDashboardZone", method=RequestMethod.GET)
	public ModelAndView onLoadDashboardZone(HttpServletRequest request) throws SHDRCException{	
		 UserInfo.hasValidReportAccess(ShdrcConstants.Role.RNTCP);
		 ModelAndView modelAndView=new ModelAndView();
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 String indicatorCategory="Monthly";
		 CommonStringList commonsList=rntcpReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
		 int year=2016;/*commonsList.getId();*/
		 String month="Jan";/*commonsList.getName();*/
		 String indQuarter=null;
		 String loggedUser=UserInfo.getLoggedInUserName();
		 JSONArray indJsonArray=rntcpReportManager.getRNTCPIndicatorList(directorateId, indicatorCategory, year, month, loggedUser,indQuarter);
		 modelAndView.addObject("indJsonArray", indJsonArray);
		 modelAndView.addObject("yearList", Util.yearList);
		 modelAndView.addObject("monthsList", Util.monthsList);
		 List frncyList= new ArrayList();
		 frncyList.add("Monthly");frncyList.add("Quarterly");
		 modelAndView.addObject("frncyList",frncyList);
		 modelAndView.addObject("directorateId",directorateId);
		 modelAndView.addObject("loggedUser",loggedUser);
		 List<CommonStringList> quarterList= Util.quarterList;
		 modelAndView.addObject("quarterList", quarterList);
		 modelAndView.addObject("indQuarter",indQuarter);
		 modelAndView.addObject("dashboardTabOption", "Monthly Indicator");		       
         modelAndView.addObject("indicatorYer", year);
         modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName(month));		
		 modelAndView.setViewName("rntcpDashboardZone");
		 return modelAndView;
	}
	
	@RequestMapping(value="/rntcpReportZone", method=RequestMethod.GET)
	public ModelAndView onLoadReportZone(HttpServletRequest request) throws SHDRCException
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
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.RNTCP);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("rntcpReportZone");
			year = Calendar.getInstance().get(Calendar.YEAR);				
			
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			fromYear = toYear = year;
			fromMonth = toMonth = month;
			reportName="Circular 2-TB units-Suspects examined";
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();
			monthsList= Util.monthsList;
			yearList= Util.yearList;			
			List<CommonStringList> districtList= rntcpDataEntryManager.getDistricts(userName);
			 String districtName=null;
		     if(Util.isNotNull(districtList)){
	        	CommonStringList district = districtList.get(0);
	        	districtName=district.getName();
		     }
			modelAndView.addObject("districts", districtList);	    
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
			modelAndView.addObject("directorateId", directorateId);
			modelAndView.addObject("month", monthName);
			modelAndView.addObject("year", year);
			modelAndView.addObject("reportName", reportName);
			modelAndView.addObject("reportZoneData", rntcpReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName,districtName));
		}finally{
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/rntcpReportZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getcorrespondingMonthData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
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
		String districtName=request.getParameter("districtName");
		String userName=UserInfo.getLoggedInUserName();
		JSONArray monthReportData=null;
		monthReportData =  rntcpReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName,districtName);
		out.println(monthReportData);
	}
	
	@RequestMapping(value="/rntcpAnalysisZone", method=RequestMethod.GET)
	public ModelAndView onLoadAnalysisZone(HttpServletRequest request) throws SHDRCException{
		ModelAndView modelAndView=null;
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		String analysisReport4=null;
		Calendar cal=null;
		String month=null;		
		Integer year;
		String quarter=null;
		Integer directorateId;
		String userName=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		List<CommonStringList> quarterList=null;
		try{
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.RNTCP);
			modelAndView=new ModelAndView();
			modelAndView.setViewName("rntcpAnalysisZone");
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			quarterList= Util.quarterList;
	       
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
			modelAndView.addObject("quarterList", quarterList);
		
			analysisReport1 = "TB Detection Rate";
			analysisReport2 = "TB Incidence Rate";
			analysisReport3 = "TB Prevalence Rate";
			analysisReport4 = "TB Success Rate";			
			year = Calendar.getInstance().get(Calendar.YEAR);	
			cal=Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();	
			
			JSONArray analysisZoneReport1Data= rntcpReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName,quarter);
			JSONArray analysisZoneReport2Data= rntcpReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName,quarter);
			JSONArray analysisZoneReport3Data= rntcpReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName,quarter);
			//JSONArray analysisZoneReport4Data= rntcpReportManager.getAnalysisZoneGeoMapData(directorateId,analysisReport4,year,month,userName,quarter);
			
			modelAndView.addObject("directorateId", request.getParameter("directorateId"));
			modelAndView.addObject("analysisZoneReport1Data", analysisZoneReport1Data);
			modelAndView.addObject("analysisZoneReport2Data", analysisZoneReport2Data);
			modelAndView.addObject("analysisZoneReport3Data", analysisZoneReport3Data);
		//	modelAndView.addObject("analysisZoneReport4Data", analysisZoneReport4Data);
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
	
	@RequestMapping(value="/rntcpAnalysisZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getAnalysisZoneData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		JSONObject data=null;
		Integer year=(Util.isNotNull(request.getParameter("selectedYear"))?Integer.parseInt(request.getParameter("selectedYear")):null);
		String month=request.getParameter("monthName");
		if(month!=null && month.length()>3){
			month=month.substring(0,3);
		}		
		String quarter=request.getParameter("selectedQuarter");
		Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		String userName=UserInfo.getLoggedInUserName();
		data=new JSONObject();
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		String analysisReport4=null;
		analysisReport1 = "TB Detection Rate";
		analysisReport2 = "TB Incidence Rate";
		analysisReport3 = "TB Prevalence Rate";
		analysisReport4 = "TB Success Rate";	
		
		JSONArray analysisZoneReport1Data= rntcpReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName,quarter);
		JSONArray analysisZoneReport2Data= rntcpReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName,quarter);
		JSONArray analysisZoneReport3Data= rntcpReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName,quarter);
		//JSONArray analysisZoneReport4Data= rntcpReportManager.getAnalysisZoneGeoMapData(directorateId,analysisReport4,year,month,userName,quarter);
		data.put("analysisZoneReport1Data", analysisZoneReport1Data);
        data.put("analysisZoneReport2Data", analysisZoneReport2Data);
        data.put("analysisZoneReport3Data", analysisZoneReport3Data);
       // data.put("analysisZoneReport4Data", analysisZoneReport4Data);
		out.println(data);
	}
	
	@RequestMapping(value="/rntcpIndicatorZone", method=RequestMethod.GET)
	public ModelAndView rntcpOnLoadIndicatorZone(HttpServletRequest request) throws SHDRCException, JSONException
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
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.RNTCP);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("rntcpIndicatorZone");
			directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			modelAndView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			modelAndView.addObject("yearList", Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
	        List<String> IndicatorCategoryList=rntcpReportManager.getIndicatorCategories(directorateId);
	        indicatorCategory=IndicatorCategoryList.get(0);
	        modelAndView.addObject("IndicatorCategoryList", IndicatorCategoryList);
	        modelAndView.addObject("indicatorCategory", indicatorCategory);
	        CommonStringList commonsList=commonReportManager.getIndicatorMaxMonthAndYear(directorateId,indicatorCategory);
			maxYear=commonsList.getId();
			maxMonth=commonsList.getName().substring(0, 3);
			modelAndView.addObject("month", commonsList.getName());
			modelAndView.addObject("year", maxYear);
	      //current year is from year ,to year and current month is from month,to month
	        JSONArray indPvtStateData=rntcpReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,maxMonth,maxYear,maxMonth,maxYear,userName);
	        modelAndView.addObject("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        modelAndView.addObject("selectedIndicator", indicators);
		        List<String> generalCategoryList=rntcpReportManager.getGeneralCategory(directorateId,indicators);
		        modelAndView.addObject("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        modelAndView.addObject("generalCategory", generalCategory);
		        JSONArray indPvtDistData=rntcpReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators, generalCategory, maxMonth, maxYear, maxMonth, maxYear, userName);
		        modelAndView.addObject("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        district=districtObj.get("District").toString();
		        modelAndView.addObject("selectedDistrict", district);
		        JSONArray indPvtInstData=rntcpReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,maxMonth,maxYear,maxMonth,maxYear,userName);
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
	
	@RequestMapping(value="/rntcpOnSearch", method=RequestMethod.POST)
	public @ResponseBody void rntcpGetIndicatorData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtStateData=rntcpReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,fromMonth,fromYear,toMonth,toYear,userName);
	        data.put("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        data.put("selectedIndicator", indicators);
		        List<String> generalCategoryList=rntcpReportManager.getGeneralCategory(directorateId,indicators);
		        data.put("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        data.put("generalCategory", generalCategory);
		        JSONArray indPvtDistData=rntcpReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators,generalCategory, fromMonth, fromYear, toMonth, toYear, userName);
		        data.put("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
			        JSONObject districtObj=indPvtDistData.getJSONObject(0);
			        district=districtObj.get("District").toString();
			        data.put("selectedDist", district);
			        JSONArray indPvtInstData=rntcpReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/rntcpFreeHandZone", method=RequestMethod.GET)
	public ModelAndView FreeHandZone(HttpServletRequest request) throws SHDRCException{
		UserInfo.hasValidReportAccess(ShdrcConstants.Role.RNTCP);
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();
		 List indCategory=rntcpReportManager.getFreeHandZoneIndCategory(directorateId);
		 List<CommonStringList> indNamesByCategory=rntcpReportManager.getFreeHandZoneIndNamesByCategory(directorateId,String.valueOf(indCategory.get(0)));
		 List indYearByNameandCate=rntcpReportManager.getIndYearByNameandCategory(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()));
		 JSONArray indJsonArray=rntcpReportManager.getFreeHandZoneData(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()),Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
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
	
	@RequestMapping(value="/rntcpOnCategoryChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onCategoryChangeSearch(HttpServletRequest request,HttpServletResponse response){
		 String indicatorCategory=request.getParameter("IndicatorCategory");		
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		 PrintWriter out=null;
	     try {
			out = response.getWriter();
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
	     List<CommonStringList> indNamesByCategory=rntcpReportManager.getFreeHandZoneIndNamesByCategory(directorateId,indicatorCategory);
	     String json=new Gson().toJson(indNamesByCategory);  
	     out.println(json);
	}
	@RequestMapping(value="/rntcpOnIndicatorNameChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onIndicatorNameChangeSearch(HttpServletRequest request,HttpServletResponse response){
		 String indicatorCategory=request.getParameter("IndicatorCategory");
		 String indicatorName=request.getParameter("IndicatorName");
		 int directorateId=Integer.parseInt(request.getParameter("DirectorateId"));
		 PrintWriter out=null;
	     try {
			out = response.getWriter();
		 } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		 List indYearByNameandCate=rntcpReportManager.getIndYearByNameandCategory(directorateId,indicatorCategory,indicatorName);
		 String json=new Gson().toJson(indYearByNameandCate);  
	     out.println(json);
	}
	@RequestMapping(value="/rntcpFreeHandZoneBySearch", method=RequestMethod.POST)
	public  @ResponseBody String ShowFreeHandZoneBySearcht(HttpServletRequest request,HttpServletResponse response){
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		  String indicatorCategory=request.getParameter("IndicatorCategory");
		  String indCategoryName=request.getParameter("IndicatorName");
		  String year=request.getParameter("Year");
		  JSONObject object=new JSONObject();      	
	 	  JSONArray indJsonArray=rntcpReportManager.getFreeHandZoneData(directorateId,indicatorCategory,indCategoryName,Integer.parseInt(year));
	 	  try {
			object.put("freeHandZoneData", indJsonArray);
			/*object.put("specifiedCategory", indicatorCategory);
			object.put("specifiedIndicators", indCategoryName);
		 	object.put("specifiedYear", year);*/
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 	  
	     return object.toString();
}
	
	@RequestMapping(value="/rntcpDistrictData", method=RequestMethod.POST)
	public @ResponseBody void rntcpGetdistrictData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        List<String> generalCategoryList=rntcpReportManager.getGeneralCategory(directorateId,indicator);
	        data.put("generalCategoryList", generalCategoryList);
	        generalCategory=generalCategoryList.get(0);
	        data.put("generalCategory", generalCategory);
	        JSONArray indPvtDistData=rntcpReportManager.getIndicatorPvtDistrictData(directorateId,category,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        data.put("indPvtDistData", indPvtDistData);
	        if(indPvtDistData.length() != 0){
		        JSONObject dtObj=indPvtDistData.getJSONObject(0);
		        district=dtObj.get("District").toString();
		        data.put("district", district);  
		        JSONArray indPvtInstData=rntcpReportManager.getIndicatorPvtInstitutionData(directorateId,category,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/rntcpOnTabChange", method=RequestMethod.POST)
	public @ResponseBody void rntcpOnTabChange(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtDistData=rntcpReportManager.getIndicatorPvtDistrictData(directorateId,indicatorCategory,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        data.put("indPvtDistData", indPvtDistData);
	        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        District=districtObj.get("District").toString();
		        data.put("District", District);  
		        JSONArray indPvtInstData=rntcpReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,District,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/rntcpInstitutionData", method=RequestMethod.POST)
	public @ResponseBody void rntcpGetInstitutionData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtInstData=rntcpReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
	        
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
	
	 @RequestMapping(value="/rntcpReportForDistrict", method=RequestMethod.POST)
 	public  @ResponseBody String ShowDistrictReport(HttpServletRequest request) throws JSONException{		
 		  JSONObject jsonObj=new JSONObject();
 		  int directorateID=6;
 		  String indicatorCategory=request.getParameter("IndCategory");
 		  int year=Integer.parseInt(request.getParameter("Year"));
		  String indicatorMnth=request.getParameter("Month");
 		  String loggedUser=UserInfo.getLoggedInUserName(); 		  
 		  String indicatorName=request.getParameter("SelectedIndicatorName");
 		  String indQuarter=request.getParameter("Quarter");
 		  indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
 		 indicatorMnth=new StringBuilder(indicatorMnth).substring(0, 3);
 	      JSONObject object=new JSONObject();
 	      try {
 	    	  JSONArray jsnArray=rntcpReportManager.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,year, indicatorMnth,indQuarter,loggedUser);
 	    	  request.setAttribute("indicatorDetailsforDistrict", jsnArray);
 		      object.put("indicatorDetailsforDistrict",jsnArray);
 	      }finally{}
 	      return object.toString();
 	}
	 
 	@RequestMapping(value="/rntcpReportForInstitution", method=RequestMethod.POST)
 	public  @ResponseBody String ShowInstitutionReport(HttpServletRequest request) throws JSONException{	
 		JSONObject jsonObj=new JSONObject();
 		  int directorateID=Integer.parseInt(request.getParameter("DirectorateID"));;
 		  String indicatorCategory=request.getParameter("IndCategory");
 		  int year=Integer.parseInt(request.getParameter("Year"));
 		  String indicatorMnth=request.getParameter("Month");
 		  String loggedUser=UserInfo.getLoggedInUserName();
 		  String districtName=request.getParameter("Disrtict");	
 		  String indicatorName=request.getParameter("Indicator");	
 		  String indQuarter=request.getParameter("Quarter");
 		  indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
 		  indicatorMnth=new StringBuilder(indicatorMnth).substring(0, 3);
 	      JSONObject object=new JSONObject();
 	      try {
 	    	  JSONArray jsnArray=rntcpReportManager.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,districtName, year, indicatorMnth,indQuarter,loggedUser);
 	    	  object.put("indicatorDetailsforDistrict",jsnArray);
 	      }finally{}
 	      return object.toString();
 	}    
 	
 	@SuppressWarnings("unchecked")
	@RequestMapping(value="/rntcpSearchIndicatorList", method=RequestMethod.POST)
 	public ModelAndView  searchIndicatorListByFilter(HttpServletRequest request,HttpServletResponse response){  
 		ModelAndView modelView=new ModelAndView();
 		String indicatorFrequency=request.getParameter("indFrequency");
 		String indicatorYer=request.getParameter("year");
 		String indicatorMnth=request.getParameter("month");
 		String indQuarter=request.getParameter("quarter");
 		indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
 		indicatorMnth=new StringBuilder(indicatorMnth).substring(0, 3);
 		int directorateId=Integer.parseInt(request.getParameter("directorateId")); 		
 		String loggedUser=UserInfo.getLoggedInUserName(); 	
 		JSONArray indJsonArray=rntcpReportManager.getRNTCPIndicatorList(directorateId, indicatorFrequency, Integer.parseInt(indicatorYer), indicatorMnth,indQuarter,loggedUser); 		
 			modelView.addObject("indJsonArray", indJsonArray);
 			modelView.addObject("indicatorFrqncy", indicatorFrequency);
 			modelView.addObject("indicatorYer", indicatorYer);
			if(Integer.parseInt(indicatorYer)<=2015){
				modelView.addObject("dashboardTabOption", "Quarterly Indicator");
			}else{
				modelView.addObject("dashboardTabOption", "Monthly Indicator");
			}
			modelView.addObject("indicatorMnth", request.getParameter("month"));	
			modelView.addObject("yearList", Util.yearList);
			List<CommonStringList> quarterList= Util.quarterList;
			modelView.addObject("quarterList", quarterList);
			List frncyList= new ArrayList();
			frncyList.add("Monthly");frncyList.add("Quarterly");
			modelView.addObject("indQuarter",indQuarter);
			modelView.addObject("frncyList",frncyList);
			modelView.addObject("monthsList", Util.monthsList);
			modelView.addObject("directorateId",directorateId);
			modelView.addObject("loggedUser",loggedUser);
			modelView.setViewName("rntcpDashboardZone");
	    	return modelView;
 	}  
 	
}
