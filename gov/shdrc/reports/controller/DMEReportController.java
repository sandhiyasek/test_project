package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.IDMEReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

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
public class DMEReportController {
	@Autowired(required=true)
	IDMEReportManager dmeReportManager;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/dmeDashboardZone", method=RequestMethod.GET)
	public ModelAndView onLoadDashboardZone(HttpServletRequest request) throws SHDRCException{
		 ModelAndView modelAndView=new ModelAndView();	
		 try{
			 UserInfo.hasValidReportAccess(ShdrcConstants.Role.DME);
			 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
			 modelAndView.addObject("directorateId",directorateId);
			 List indTempList=new ArrayList();
			 indTempList.add("Daily");
			 indTempList.add("Monthly");		
			 String indicatorCategory=String.valueOf(indTempList.get(1));
			 CommonStringList commonsList=dmeReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
			 int year=commonsList.getId();
			 String month=commonsList.getName();
			 String loggedUser=UserInfo.getLoggedInUserName();
			 JSONArray indJsonArray=dmeReportManager.getIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);		 
			 modelAndView.addObject("indJsonArray", indJsonArray);
			 modelAndView.addObject("yearList", Util.dashboardYearList);
			 modelAndView.addObject("monthsList", Util.monthsList);
			 modelAndView.addObject("IndFrncyList",indTempList);			
			 modelAndView.addObject("initialTabName","Daily");
			 modelAndView.addObject("loggedUser",loggedUser);		    
	         modelAndView.addObject("indicatorYer", year);
	         modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName(month));
			 modelAndView.setViewName("dmeDashboardZone");
		}finally{
		}
		 return modelAndView;		
	}
	
	@RequestMapping(value="/dmeReportZone", method=RequestMethod.GET)
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
		String institutionName = null;
		List<CommonStringList> institutionTypeList=null;
		List<CommonStringList> institutionList=null;
		List<CommonStringList> postTypeList=null;
		List<CommonStringList> postList=null;
		List<String> fileNames=null;
		String frequency=null;
		String fileName=null;
		String date=null;
		String postName = null;
		try{
			modelAndView = new ModelAndView();
			modelAndView.setViewName("dmeReportZone");
			
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.DME);
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			modelAndView.addObject("directorateId", directorateId);
			
			year = Calendar.getInstance().get(Calendar.YEAR);			
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			reportName="DME_ACCIDENT AND EMERGENCY";
			
			institutionTypeList = new ArrayList<CommonStringList>();
			institutionTypeList = dmeReportManager.getInstitutionTypeList();
			
			String institutionType=null;
	        if(Util.isNotNull(institutionTypeList)){
	        	CommonStringList insType = institutionTypeList.get(0);    	
	        	institutionType=insType.getName();
	        }
			
			institutionList = new ArrayList<CommonStringList>();
    		institutionList = dmeReportManager.getInstitutionList(institutionType);
    		
    		postTypeList = new ArrayList<CommonStringList>();
			postTypeList = dmeReportManager.getPostTypeList();
			
			String postType=null;
	        if(Util.isNotNull(postTypeList)){
	        	CommonStringList PosType = postTypeList.get(0);    	
	        	postType=PosType.getName();
	        }
			
			postList = new ArrayList<CommonStringList>();
    		postList = dmeReportManager.getPostList(postType);   		    		
    		
			fromYear = toYear = year;
			fromMonth = toMonth = month;
			
			userName=UserInfo.getLoggedInUserName();
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			
			frequency="Monthly";
			fileNames =  dmeReportManager.getFileNames(frequency);
			
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
			modelAndView.addObject("institutionTypeList", institutionTypeList);	
			modelAndView.addObject("institutionList", institutionList);	
			modelAndView.addObject("postTypeList", postTypeList);	
			modelAndView.addObject("postList", postList);
			modelAndView.addObject("month", monthName);
			modelAndView.addObject("year", year);
			modelAndView.addObject("reportName", reportName);
			modelAndView.addObject("fileNames", fileNames);
			modelAndView.addObject("frequency", frequency);
			modelAndView.addObject("reportZoneData", dmeReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,institutionName,postName,fileName,frequency,date,userName));
		}finally{
			monthsList = null;	
			yearList = null;
			institutionName = null;
			postName = null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/dmeReportZoneInstTypeChange", method=RequestMethod.POST)
	public @ResponseBody void getReportZoneInstitutionList(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, ParseException{
		PrintWriter out = response.getWriter();
		List<CommonStringList> institutionList=null;
		try{
			String institutionType = request.getParameter("institutionType");
			institutionList = new ArrayList<CommonStringList>();
    		institutionList = dmeReportManager.getInstitutionList(institutionType);
    		String json=new Gson().toJson(institutionList);  
			out.println(json);	
		}finally{}
	}
	
	@RequestMapping(value="/dmeReportZonePostTypeChange", method=RequestMethod.POST)
	public @ResponseBody void getReportZonePostList(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, ParseException{
		PrintWriter out = response.getWriter();
		List<CommonStringList> postList=null;
		try{
			String postType = request.getParameter("postType");
			postList = new ArrayList<CommonStringList>();
			postList = dmeReportManager.getPostList(postType);
    		String json=new Gson().toJson(postList);  
			out.println(json);	
		}finally{}
	}
	
	@RequestMapping(value="/dmeReportZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getcorrespondingMonthData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, ParseException{
		PrintWriter out = response.getWriter();
		String institutionName=null;
		String postName = null;
		String reportName=null;
		JSONArray monthReportData=null;
		try{
			reportName=request.getParameter("reportName");
			institutionName=request.getParameter("institutionName");
			postName=request.getParameter("postName");
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
			String frequency=request.getParameter("frequency");
			String fileName=request.getParameter("fileName");
			String date=request.getParameter("date");
			/*DateFormat df = new SimpleDateFormat("yyyy/dd/MM"); 
			Date date = df.parse(date1);*/
			
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String userName=UserInfo.getLoggedInUserName();
			if((("DME_DENGUE REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Date Wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Date Wise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Institution wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Institution wise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1 REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA REPORT (Monthwise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Monthwise)".equalsIgnoreCase(reportName))
					|| ("DME_DENGUE  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_AH1N1  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_EBOLA  REPORT (Year wise)".equalsIgnoreCase(reportName)) || ("DME_TICK FEVER REPORT (Year wise)".equalsIgnoreCase(reportName)))
					&& (institutionName==null)){
			
				StringBuilder institutionIdList = dmeReportManager.getIdList("institution");
				institutionName = institutionIdList.toString();
			}
			if((("DME_THREE PERCENT RESERVATION FOR DIFFERENTLY ABLED(Post_wise Inst_wise)".equalsIgnoreCase(reportName)) || ("DME_Cadre Strength(Post_wise Inst_wise)".equalsIgnoreCase(reportName)))
					&& (postName==null)){
			
				StringBuilder postIdList = dmeReportManager.getIdList("post");
				postName = postIdList.toString();
			}
			
			monthReportData =  dmeReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,institutionName,postName,fileName,frequency,date,userName);
			out.println(monthReportData);
		}finally{
			institutionName = null;
			reportName = null;
			postName = null;
		}
	}
	
	@RequestMapping(value="/getFileNames", method=RequestMethod.POST)
	public @ResponseBody void getFileNames(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
		PrintWriter out = response.getWriter();
		String frequency=null;
		try{
			frequency=request.getParameter("frequency");
			if("monthly".equalsIgnoreCase(frequency)){
				frequency="Monthly";
			}
			List<String> fileNames=null;
			//JSONObject data=new JSONObject();
			fileNames =  dmeReportManager.getFileNames(frequency);
			//data.put("fileNames", fileNames);
			String json=new Gson().toJson(fileNames);  
			out.println(json);
		}finally{
			frequency=null;
		}
	}
	
	@RequestMapping(value="/dmeAnalysisZone", method=RequestMethod.GET)
	public ModelAndView onLoadAnalysisZone(HttpServletRequest request) throws SHDRCException{
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
			modelAndView.setViewName("dmeAnalysisZone");
			
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.DME);
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
		
			analysisReport1 = "InPatient Census";
			analysisReport2 = "Ceasarian Deliveries Monthwise";
			analysisReport3 = "Major Surgeries Monthwise";
			analysisReport4 = "Minor Surgeries Monthwise";
					
			year = Calendar.getInstance().get(Calendar.YEAR);	
			cal=Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();	
			
			JSONArray analysisZoneReport1Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
			JSONArray analysisZoneReport2Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
			JSONArray analysisZoneReport3Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
			JSONArray analysisZoneReport4Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
			
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
	
	@RequestMapping(value="/dmeAnalysisZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getAnalysisZoneData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
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
		analysisReport1 = "InPatient Census";
		analysisReport2 = "Ceasarian Deliveries Monthwise";
		analysisReport3 = "Major Surgeries Monthwise";
		analysisReport4 = "Minor Surgeries Monthwise";
		
		JSONArray analysisZoneReport1Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
		JSONArray analysisZoneReport2Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
		JSONArray analysisZoneReport3Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
		JSONArray analysisZoneReport4Data= dmeReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
		data.put("analysisZoneReport1Data", analysisZoneReport1Data);
        data.put("analysisZoneReport2Data", analysisZoneReport2Data);
        data.put("analysisZoneReport3Data", analysisZoneReport3Data);
        data.put("analysisZoneReport4Data", analysisZoneReport4Data);
		out.println(data);
	}
	
	@RequestMapping(value="/dmeIndicatorZone", method=RequestMethod.GET)
	public ModelAndView dmeOnLoadIndicatorZone(HttpServletRequest request) throws JSONException, SHDRCException
	{	
		ModelAndView modelAndView=null;
		SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
		Map<String, Integer> monthsMap =null;
		Calendar cal=null;
		String currentMonth=null;
		String previousMonth=null;
		String previousMonth1=null;
        String userName=null;
        int directorateId=0;
        String indicatorCategory= null;
		String indicators=null;
		String district=null;
		String generalCategory=null;
		try{
			modelAndView = new ModelAndView();
			modelAndView.setViewName("dmeIndicatorZone");
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.DME);
			directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 modelAndView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2016);
			maxYearMonth.setName("June");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			monthsMap = Util.monthsMap;
			cal=Calendar.getInstance();
			cal.set(maxYear, monthsMap.get(maxMonth),1);
			currentMonth=sdf.format(cal.getTime());
			cal.set(maxYear, monthsMap.get(maxMonth)-1,1);
			previousMonth=sdf.format(cal.getTime());
			cal.set(maxYear, monthsMap.get(maxMonth)-2,1);
			previousMonth1=sdf.format(cal.getTime());
			currentMonth = currentMonth.substring(0, 3);
			previousMonth = previousMonth.substring(0, 3);
			previousMonth1 = previousMonth1.substring(0, 3);
			modelAndView.addObject("yearList", Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
			modelAndView.addObject("currentMonth", maxMonth);
			modelAndView.addObject("currentYear", maxYear);
			modelAndView.addObject("year", maxYear);
	        modelAndView.addObject("month", maxMonth);
	        List<String> IndicatorCategoryList=dmeReportManager.getIndicatorCategories(directorateId);	        
	        indicatorCategory=IndicatorCategoryList.get(0);
	        modelAndView.addObject("indicatorCategory", indicatorCategory);
	        modelAndView.addObject("IndicatorCategoryList", IndicatorCategoryList);
	      //current year is from year ,to year and current month is from month,to month
	        JSONArray indPvtStateData=dmeReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,currentMonth,maxYear,currentMonth,maxYear,userName);
	        modelAndView.addObject("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        modelAndView.addObject("selectedIndicator", indicators);
		        List<String> generalCategoryList=dmeReportManager.getGeneralCategory(directorateId,indicators);
		        modelAndView.addObject("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        modelAndView.addObject("generalCategory", generalCategory);
		        JSONArray indPvtDistData=dmeReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators, generalCategory, currentMonth, maxYear, currentMonth, maxYear, userName);
		        modelAndView.addObject("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        district=districtObj.get("District").toString();
		        modelAndView.addObject("selectedDistrict", district);
		        JSONArray indPvtInstData=dmeReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,currentMonth,maxYear,currentMonth,maxYear,userName);
		        modelAndView.addObject("indPvtInstData", indPvtInstData);
		        }
		       }

		}finally{
			sdf=null;
			maxYearMonth=null;
			maxMonth=null;
			monthsMap =null;
			cal=null;
			currentMonth=null;
			previousMonth=null;
			previousMonth1=null;
			userName=null;
			indicatorCategory= null;
	        indicators =null;
	        district=null;
		}
		return modelAndView;
	}   	
	
	@RequestMapping(value="/dmeOnSearch", method=RequestMethod.POST)
	public @ResponseBody void dmeGetIndicatorData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtStateData=dmeReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,fromMonth,fromYear,toMonth,toYear,userName);
	        if(indPvtStateData.length() != 0){
		        data.put("indPvtStateData", indPvtStateData);
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        data.put("selectedIndicator", indicators);
		        List<String> generalCategoryList=dmeReportManager.getGeneralCategory(directorateId,indicators);
		        data.put("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        data.put("generalCategory", generalCategory);
		        JSONArray indPvtDistData=dmeReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators,generalCategory, fromMonth, fromYear, toMonth, toYear, userName);
		        if(indPvtDistData.length() != 0){
			        data.put("indPvtDistData", indPvtDistData);
			        JSONObject districtObj=indPvtDistData.getJSONObject(0);
			        district=districtObj.get("District").toString();
			        data.put("selectedDist", district);
			        JSONArray indPvtInstData=dmeReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/dmeDistrictData", method=RequestMethod.POST)
	public @ResponseBody void dmeGetdistrictData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        List<String> generalCategoryList=dmeReportManager.getGeneralCategory(directorateId,indicator);
	        data.put("generalCategoryList", generalCategoryList);
	        generalCategory=generalCategoryList.get(0);
	        data.put("generalCategory", generalCategory); 
	        JSONArray indPvtDistData=dmeReportManager.getIndicatorPvtDistrictData(directorateId,category,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        if(indPvtDistData.length() != 0){
	        	data.put("indPvtDistData", indPvtDistData);
		        JSONObject dtObj=indPvtDistData.getJSONObject(0);
		        district=dtObj.get("District").toString();
		        data.put("district", district);  
		        JSONArray indPvtInstData=dmeReportManager.getIndicatorPvtInstitutionData(directorateId,category,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/dmeOnTabChange", method=RequestMethod.POST)
	public @ResponseBody void dmeOnTabChange(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtDistData=dmeReportManager.getIndicatorPvtDistrictData(directorateId,indicatorCategory,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        if(indPvtDistData.length() != 0){
	        	data.put("indPvtDistData", indPvtDistData);
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        District=districtObj.get("District").toString();
		        data.put("District", District);
		        JSONArray indPvtInstData=dmeReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,District,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/dmeInstitutionData", method=RequestMethod.POST)
	public @ResponseBody void dmeGetInstitutionData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtInstData=dmeReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);     
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
	
	@RequestMapping(value="/dmeReportForDistrict", method=RequestMethod.POST)
	public  @ResponseBody String ShowDistrictReport(HttpServletRequest request) throws JSONException{		
		  int directorateID=Integer.parseInt(request.getParameter("DirectorateID"));
		  String indicatorCategory=request.getParameter("IndFrequency");
		  int year=Integer.parseInt(request.getParameter("Year"));
		  int month=Integer.parseInt(request.getParameter("Month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();
		  
		  String indicatorName=request.getParameter("SelectedIndicatorName");		  
	      JSONObject object=new JSONObject();
	      try {
	    	  JSONArray jsnArray=dmeReportManager.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName, year, indMonth, loggedUser);
	    	  request.setAttribute("indicatorDetailsforDistrict", jsnArray);
		      object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{
	    	  
	      }
	      return object.toString();
	}
	
	@RequestMapping(value="/dmeReportForInstitution", method=RequestMethod.POST)
	public  @ResponseBody String ShowInstitutionReport(HttpServletRequest request) throws JSONException{	
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
	      try {
	    	  JSONArray jsnArray=dmeReportManager.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,districtName, year, indMonth, loggedUser);
	    	  object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{	    	  
	      }
	      return object.toString();
	} 
	
	@RequestMapping(value="/dmeReportForSearchCriteria", method=RequestMethod.POST)
	public  @ResponseBody String ShowSearchCriteriaData(HttpServletRequest request) throws JSONException{	
		  int directorateId=Integer.parseInt(request.getParameter("DirectorateID"));
		  String indicatorCategory=request.getParameter("IndFrequency");
		  int year=Integer.parseInt(request.getParameter("Year"));
		  int month=Integer.parseInt(request.getParameter("Month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();		  	
	      JSONObject object=new JSONObject();
	      try {
	    	  JSONArray jsnArray=dmeReportManager.getIndicatorList(directorateId, indicatorCategory,year, indMonth, loggedUser); 
	    	  object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{
	      }	      
	      return object.toString();
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/dmeSearchIndicatorList", method=RequestMethod.POST)
	public ModelAndView  searchIndicatorListByFilter(HttpServletRequest request,HttpServletResponse response){	    		
		ModelAndView modelView=new ModelAndView();
		List indTempList=new ArrayList();
		 indTempList.add("Daily");
		 indTempList.add("Monthly");
		String indicatorFrequency=request.getParameter("indFrequency");
		String indicatorYer=request.getParameter("year");
		String indicatorMnth=request.getParameter("month");
		indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
		int directorateId=Integer.parseInt(request.getParameter("directorateId"));
		indicatorMnth=new StringBuilder(indicatorMnth).substring(0, 3);
		String loggedUser=UserInfo.getLoggedInUserName();    		
		JSONArray indJsonArray=dmeReportManager.getIndicatorList(directorateId, indicatorFrequency, Integer.parseInt(indicatorYer), indicatorMnth, loggedUser);  		
		modelView.addObject("indJsonArray", indJsonArray);
		modelView.addObject("indicatorFrqncy", indicatorFrequency);
		modelView.addObject("indicatorYer", indicatorYer);
		modelView.addObject("IndFrncyList",indTempList);
		modelView.addObject("indicatorMnth", request.getParameter("month"));	
		modelView.addObject("yearList", Util.dashboardYearList);
		modelView.addObject("monthsList", Util.monthsList);
		modelView.addObject("directorateId",directorateId);	
		modelView.addObject("loggedUser",loggedUser);
		modelView.addObject("initialTabName",indicatorFrequency);
		modelView.setViewName("dmeDashboardZone");
    	return modelView;
	} 
	@RequestMapping(value="/dmeFreeHandZone", method=RequestMethod.GET)
	public ModelAndView FreeHandZone(HttpServletRequest request) throws SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();		 
		 List indCategory=dmeReportManager.getFreeHandZoneIndCategory(directorateId);
		 List<CommonStringList> indNamesByCategory=dmeReportManager.getFreeHandZoneIndNamesByCategory(directorateId,String.valueOf(indCategory.get(0)));
		 List indYearByNameandCate=dmeReportManager.getIndYearByNameandCategory(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()));
		 JSONArray indJsonArray=dmeReportManager.getFreeHandZoneData(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()),Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
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
	
	@RequestMapping(value="/dmeOnCategoryChangeSearch", method=RequestMethod.POST)
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
	     List<CommonStringList> indNamesByCategory=dmeReportManager.getFreeHandZoneIndNamesByCategory(directorateId,indicatorCategory);
	     String json=new Gson().toJson(indNamesByCategory);  
	     out.println(json);
	}
	@RequestMapping(value="/dmeOnIndicatorNameChangeSearch", method=RequestMethod.POST)
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
		 List indYearByNameandCate=dmeReportManager.getIndYearByNameandCategory(directorateId,indicatorCategory,indicatorName);
		 String json=new Gson().toJson(indYearByNameandCate);  
	     out.println(json);
	}
	@RequestMapping(value="/dmeFreeHandZoneBySearch", method=RequestMethod.POST)
	public  @ResponseBody String ShowFreeHandZoneBySearcht(HttpServletRequest request,HttpServletResponse response){
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		  String indicatorCategory=request.getParameter("IndicatorCategory");
		  String indCategoryName=request.getParameter("IndicatorName");
		  String year=request.getParameter("Year");
		  JSONObject object=new JSONObject();      	
	 	  JSONArray indJsonArray=dmeReportManager.getFreeHandZoneData(directorateId,indicatorCategory,indCategoryName,Integer.parseInt(year));
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
}
