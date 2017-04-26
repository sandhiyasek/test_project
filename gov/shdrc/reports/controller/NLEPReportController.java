package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.ICommonReportManager;
import gov.shdrc.reports.service.INLEPReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

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
public class NLEPReportController {
	@Autowired(required=true)
	INLEPReportManager nlepReportManager;
	@Autowired(required=true)
	ICommonReportManager commonReportManager;
	
	@RequestMapping(value="/nlepDashboardZone", method=RequestMethod.GET)
	public ModelAndView onLoadDashboardZone(HttpServletRequest request) throws SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();		 
		 UserInfo.hasValidReportAccess(ShdrcConstants.Role.NLEP);
		 String indicatorCategory="Monthly";
		 CommonStringList commonsList=nlepReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
		 int year=commonsList.getId();
		 String month=commonsList.getName();
		 String loggedUser=UserInfo.getLoggedInUserName();
		 JSONArray indJsonArray=nlepReportManager.getNLEPIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);
		 modelAndView.addObject("indJsonArray", indJsonArray);
		 modelAndView.addObject("yearList", Util.yearList);
		 modelAndView.addObject("monthsList", Util.monthsList);
		 modelAndView.addObject("directorateId",directorateId);
		 modelAndView.addObject("loggedUser",loggedUser);		       
         modelAndView.addObject("indicatorYer", year);
         modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName(month));
		 modelAndView.setViewName("nlepDashboardZone");
		 return modelAndView;
		
	}
	
	@RequestMapping(value="/nlepReportZone", method=RequestMethod.GET)
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
			modelAndView = new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.NLEP);
			modelAndView.setViewName("nlepReportZone");
			
			year = Calendar.getInstance().get(Calendar.YEAR);				
			
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			fromYear = toYear = year;
			fromMonth = toMonth = month;
			reportName="ULF 07 Page 1";
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
			modelAndView.addObject("reportZoneData", nlepReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName));
		}finally{
			monthsList=null;	
			yearList=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/nlepReportZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getcorrespondingMonthData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
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
		monthReportData = nlepReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
		out.println(monthReportData);
	}
	
	@RequestMapping(value="/nlepAnalysisZone", method=RequestMethod.GET)
	public ModelAndView onLoadAnalysisZone(HttpServletRequest request) throws JSONException, SHDRCException{
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
		JSONArray analysisZoneReport2FirstSeriesData=null;
		JSONArray analysisZoneReport2SecondSeriesData=null;
		JSONArray analysisZoneReport2ThirdSeriesData=null;
		JSONArray analysisZoneFirstSeriesData=null;
		JSONArray analysisZoneSecondSeriesData=null;
		JSONArray analysisZoneThirdSeriesData=null;
		try{
			modelAndView=new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.NLEP);
			modelAndView.setViewName("nlepAnalysisZone");
			
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
		
			analysisReport1 = "Top 10 - New Leprosy Cases - Districtwise";
			analysisReport2 = "Analysis - Female Rate";
			analysisReport3 = "Analysis - MB Rate";
			analysisReport4 = "Analysis - Child Rate";			
			year = Calendar.getInstance().get(Calendar.YEAR);	
			cal=Calendar.getInstance();			
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();	
			
			JSONArray analysisZoneReport1Data= nlepReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
			JSONObject analysisZoneReport2Data= nlepReportManager.getAnalysisZoneMultiSeriesData(directorateId,analysisReport2,year,month,userName);
			JSONObject analysisZoneReport3Data= nlepReportManager.getAnalysisZoneMultiSeriesData(directorateId,analysisReport3,year,month,userName);
			JSONArray analysisZoneReport4Data= nlepReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
			
			if(analysisZoneReport2Data!=null && analysisZoneReport2Data.length()>0){
				analysisZoneReport2FirstSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneReport2SecondSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneSecondSeriesData");
				analysisZoneReport2ThirdSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneThirdSeriesData");	
			}				
			if(analysisZoneReport3Data!=null && analysisZoneReport3Data.length()>0){
				analysisZoneFirstSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneSecondSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneSecondSeriesData");
				analysisZoneThirdSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneThirdSeriesData");	
			}
			
			modelAndView.addObject("directorateId", request.getParameter("directorateId"));
			modelAndView.addObject("analysisZoneReport1Data", analysisZoneReport1Data);
			modelAndView.addObject("analysisZoneReport2Data1", analysisZoneReport2FirstSeriesData);
			modelAndView.addObject("analysisZoneReport2Data2", analysisZoneReport2SecondSeriesData);
			modelAndView.addObject("analysisZoneReport2Data3", analysisZoneReport2ThirdSeriesData);
			modelAndView.addObject("analysisZoneReport3Data1", analysisZoneFirstSeriesData);
			modelAndView.addObject("analysisZoneReport3Data2", analysisZoneSecondSeriesData);
			modelAndView.addObject("analysisZoneReport3Data3", analysisZoneThirdSeriesData);
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
	
	@RequestMapping(value="/nlepAnalysisZoneSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getAnalysisZoneData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException,SHDRCException{
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
		JSONArray analysisZoneFirstSeriesData=null;
		JSONArray analysisZoneSecondSeriesData=null;
		JSONArray analysisZoneThirdSeriesData=null;
		JSONArray analysisZoneReport2FirstSeriesData=null;
		JSONArray analysisZoneReport2SecondSeriesData=null;
		JSONArray analysisZoneReport2ThirdSeriesData=null;
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		String analysisReport4=null;
		analysisReport1 = "Top 10 - New Leprosy Cases - Districtwise";
		analysisReport2 = "Analysis - Female Rate";
		analysisReport3 = "Analysis - MB Rate";
		analysisReport4 = "Analysis - Child Rate";	
		
		JSONArray analysisZoneReport1Data= nlepReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
		JSONObject analysisZoneReport2Data= nlepReportManager.getAnalysisZoneMultiSeriesData(directorateId,analysisReport2,year,month,userName);
		JSONObject analysisZoneReport3Data= nlepReportManager.getAnalysisZoneMultiSeriesData(directorateId,analysisReport3,year,month,userName);
		JSONArray analysisZoneReport4Data= nlepReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
		
		if(analysisZoneReport2Data!=null && analysisZoneReport2Data.length()>0){
			analysisZoneReport2FirstSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneReport2SecondSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneSecondSeriesData");
			analysisZoneReport2ThirdSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneThirdSeriesData");	
		}	
		if(analysisZoneReport3Data!=null && analysisZoneReport3Data.length()>0){
			analysisZoneFirstSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneSecondSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneSecondSeriesData");
			analysisZoneThirdSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneThirdSeriesData");	
		}
				
		data.put("analysisZoneReport1Data", analysisZoneReport1Data);
		data.put("analysisZoneReport2Data1", analysisZoneReport2FirstSeriesData);
		data.put("analysisZoneReport2Data2", analysisZoneReport2SecondSeriesData);
		data.put("analysisZoneReport2Data3", analysisZoneReport2ThirdSeriesData);
        data.put("analysisZoneReport3Data1", analysisZoneFirstSeriesData);
        data.put("analysisZoneReport3Data2", analysisZoneSecondSeriesData);
        data.put("analysisZoneReport3Data3", analysisZoneThirdSeriesData);
        data.put("analysisZoneReport4Data", analysisZoneReport4Data);
		out.println(data);
	}
	
	
	@RequestMapping(value="/nlepIndicatorZone", method=RequestMethod.GET)
	public ModelAndView onLoadIndicatorZone(HttpServletRequest request) throws JSONException, SHDRCException
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
		/*String district=null;*/
		String generalCategory=null;
		try{
			modelAndView = new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.NLEP);
			modelAndView.setViewName("nlepIndicatorZone");
			directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			modelAndView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			modelAndView.addObject("yearList", Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
	        List<String> IndicatorCategoryList=nlepReportManager.getIndicatorCategories(directorateId);
	        indicatorCategory=IndicatorCategoryList.get(0);
	        modelAndView.addObject("IndicatorCategoryList", IndicatorCategoryList);
	        modelAndView.addObject("indicatorCategory", indicatorCategory);
	        CommonStringList commonsList=commonReportManager.getIndicatorMaxMonthAndYear(directorateId,indicatorCategory);
			maxYear=commonsList.getId();
			maxMonth=commonsList.getName().substring(0, 3);
			modelAndView.addObject("month", commonsList.getName());
			modelAndView.addObject("year", maxYear);
	      //current year is from year ,to year and current month is from month,to month
	        JSONArray indPvtStateData=nlepReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,maxMonth,maxYear,maxMonth,maxYear,userName);
	        JSONObject stateObj=indPvtStateData.getJSONObject(0);
	        indicators=stateObj.get("Indicator").toString();
	        List<String> generalCategoryList=nlepReportManager.getGeneralCategory(directorateId,indicators);
	        generalCategory=generalCategoryList.get(0);
	        JSONArray indPvtDistData=nlepReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators, generalCategory, maxMonth, maxYear, maxMonth, maxYear, userName);
	        /*JSONObject districtObj=indPvtDistData.getJSONObject(0);
	        district=districtObj.get("District").toString();
	        JSONArray indPvtInstData=nlepReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,currentMonth,maxYear,currentMonth,maxYear,userName);*/
	        modelAndView.addObject("indPvtStateData", indPvtStateData);
	        modelAndView.addObject("indPvtDistData", indPvtDistData);
	        /*modelAndView.addObject("indPvtInstData", indPvtInstData);*/
	        modelAndView.addObject("selectedIndicator", indicators);
	        /*modelAndView.addObject("selectedDistrict", district);*/
	        modelAndView.addObject("generalCategory", generalCategory);
	        modelAndView.addObject("generalCategoryList", generalCategoryList);			
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
	       /* district=null;*/
		}
		return modelAndView;
	}   
	@RequestMapping(value="/nlepFreeHandZone", method=RequestMethod.GET)
	public ModelAndView FreeHandZone(HttpServletRequest request) throws SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();
		 UserInfo.hasValidReportAccess(ShdrcConstants.Role.NLEP);
		 List indCategory=nlepReportManager.getFreeHandZoneIndCategory(directorateId);
		 List<CommonStringList> indNamesByCategory=nlepReportManager.getFreeHandZoneIndNamesByCategory(directorateId,String.valueOf(indCategory.get(0)));
		 List indYearByNameandCate=nlepReportManager.getIndYearByNameandCategory(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()));
		 JSONArray indJsonArray=nlepReportManager.getFreeHandZoneData(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()),Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
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
	
	@RequestMapping(value="/nlepOnCategoryChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onCategoryChangeSearch(HttpServletRequest request,HttpServletResponse response) throws IOException,SHDRCException{
		 String indicatorCategory=request.getParameter("IndicatorCategory");		
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		 PrintWriter out=null;
			out = response.getWriter();
	     List<CommonStringList> indNamesByCategory=nlepReportManager.getFreeHandZoneIndNamesByCategory(directorateId,indicatorCategory);
	     String json=new Gson().toJson(indNamesByCategory);  
	     out.println(json);
	}
	@RequestMapping(value="/nlepOnIndicatorNameChangeSearch", method=RequestMethod.POST)
	public  @ResponseBody void onIndicatorNameChangeSearch(HttpServletRequest request,HttpServletResponse response) throws IOException,SHDRCException{
		 String indicatorCategory=request.getParameter("IndicatorCategory");
		 String indicatorName=request.getParameter("IndicatorName");
		 int directorateId=Integer.parseInt(request.getParameter("DirectorateId"));
		 PrintWriter out=null;
			out = response.getWriter();
		 List indYearByNameandCate=nlepReportManager.getIndYearByNameandCategory(directorateId,indicatorCategory,indicatorName);
		 String json=new Gson().toJson(indYearByNameandCate);  
	     out.println(json);
	}
	@RequestMapping(value="/nlepFreeHandZoneBySearch", method=RequestMethod.POST)
	public  @ResponseBody String ShowFreeHandZoneBySearcht(HttpServletRequest request,HttpServletResponse response) throws JSONException,SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		  String indicatorCategory=request.getParameter("IndicatorCategory");
		  String indCategoryName=request.getParameter("IndicatorName");
		  String year=request.getParameter("Year");
		  JSONObject object=new JSONObject();      	
	 	  JSONArray indJsonArray=nlepReportManager.getFreeHandZoneData(directorateId,indicatorCategory,indCategoryName,Integer.parseInt(year));
			object.put("freeHandZoneData", indJsonArray);
			/*object.put("specifiedCategory", indicatorCategory);
			object.put("specifiedIndicators", indCategoryName);
		 	object.put("specifiedYear", year);*/ 	  
	     return object.toString();
	}
	@RequestMapping(value="/nlepReportForDistrict", method=RequestMethod.POST)
	public  @ResponseBody String ShowDistrictReport(HttpServletRequest request) throws JSONException,SHDRCException{		
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
	    	  JSONArray jsnArray=nlepReportManager.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName, year, indMonth, loggedUser);
	    	  request.setAttribute("indicatorDetailsforDistrict", jsnArray);
		      object.put("indicatorDetailsforDistrict",jsnArray);
	      return object.toString();
	}
	@RequestMapping(value="/nlepReportForInstitution", method=RequestMethod.POST)
	public  @ResponseBody String ShowInstitutionReport(HttpServletRequest request) throws JSONException,SHDRCException{			
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
	    	  JSONArray jsnArray=nlepReportManager.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,districtName, year, indMonth, loggedUser);
	    	  object.put("indicatorDetailsforDistrict",jsnArray);
	      return object.toString();
	}    
	@RequestMapping(value="/nlepSearchIndicatorList", method=RequestMethod.POST)
	public ModelAndView  searchIndicatorListByFilter(HttpServletRequest request,HttpServletResponse response) throws SHDRCException{	    		
		ModelAndView modelView=new ModelAndView();
		UserInfo.hasValidReportAccess(ShdrcConstants.Role.NLEP);
		String indicatorFrequency=request.getParameter("indFrequency");
		String indicatorYer=request.getParameter("year");
		String indicatorMnth=request.getParameter("month");	
		indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
		int directorateId=Integer.parseInt(request.getParameter("directorateId"));
		indicatorMnth=new StringBuilder(indicatorMnth).substring(0, 3);
		String loggedUser=UserInfo.getLoggedInUserName();    		
		JSONArray indJsonArray=nlepReportManager.getNLEPIndicatorList(directorateId, indicatorFrequency, Integer.parseInt(indicatorYer), indicatorMnth, loggedUser);  		
		modelView.addObject("indJsonArray", indJsonArray);
		modelView.addObject("indicatorFrqncy", indicatorFrequency);
		modelView.addObject("indicatorYer", indicatorYer);
		modelView.addObject("indicatorMnth", request.getParameter("month"));	
		modelView.addObject("yearList", Util.yearList);
		modelView.addObject("monthsList", Util.monthsList);
		modelView.addObject("directorateId",directorateId);	
		modelView.addObject("loggedUser",loggedUser);
		modelView.setViewName("nlepDashboardZone");
    	return modelView;
	} 
	@RequestMapping(value="/nlepOnSearch", method=RequestMethod.POST)
	public @ResponseBody void getIndicatorData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException,SHDRCException{
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
        /*String district=null;*/
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
	        JSONArray indPvtStateData=nlepReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,fromMonth,fromYear,toMonth,toYear,userName);
	        JSONObject stateObj=indPvtStateData.getJSONObject(0);
	        indicators=stateObj.get("Indicator").toString();
	        List<String> generalCategoryList=nlepReportManager.getGeneralCategory(directorateId,indicators);
	        generalCategory=generalCategoryList.get(0);
	        JSONArray indPvtDistData=nlepReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators,generalCategory, fromMonth, fromYear, toMonth, toYear, userName);
	        /*JSONObject districtObj=indPvtDistData.getJSONObject(0);
	        district=districtObj.get("District").toString();
	        JSONArray indPvtInstData=nlepReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);*/
	        
	        data.put("indPvtStateData", indPvtStateData);
	        data.put("indPvtDistData", indPvtDistData);
	        /*data.put("indPvtInstData", indPvtInstData);*/
	        data.put("generalCategoryList", generalCategoryList);
	        data.put("selectedIndicator", indicators);
	        /*data.put("selectedDist", district);*/
	        data.put("generalCategory", generalCategory);
	        out.println(data);
        }finally{
			userName=null;
			indicatorCategory= null;
	        fromMonth= null;
	        toMonth = null; 
	        fromYr = null;
	        toYr = null;
	        /*district=null;*/
	        indicators=null;
	        generalCategory=null;
		}
	}
	@RequestMapping(value="/nlepDistrictData", method=RequestMethod.POST)
	public @ResponseBody void getdistrictData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException,SHDRCException{
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
        /*String district=null;*/
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
        List<String> generalCategoryList=nlepReportManager.getGeneralCategory(directorateId,indicator);
        generalCategory=generalCategoryList.get(0);
        JSONArray indPvtDistData=nlepReportManager.getIndicatorPvtDistrictData(directorateId,category,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
        /*JSONObject dtObj=indPvtDistData.getJSONObject(0);
        district=dtObj.get("District").toString();*/
        /*JSONArray indPvtInstData=nlepReportManager.getIndicatorPvtInstitutionData(directorateId,category,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);*/
        data.put("generalCategoryList", generalCategoryList);
        data.put("indPvtDistData", indPvtDistData);
        /*data.put("indPvtInstData", indPvtInstData);*/
        data.put("generalCategory", generalCategory);  
        /*data.put("district", district);*/  
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
	       /* district=null;*/
		}
	}
	@RequestMapping(value="/nlepOnTabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException,SHDRCException{
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
        /*String District=null;*/
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
        JSONArray indPvtDistData=nlepReportManager.getIndicatorPvtDistrictData(directorateId,indicatorCategory,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
        /*JSONObject districtObj=indPvtDistData.getJSONObject(0);
        District=districtObj.get("District").toString();*/
        /*JSONArray indPvtInstData=nlepReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,District,fromMonth,fromYear,toMonth,toYear,userName);*/
        data.put("indPvtDistData", indPvtDistData);
        /*data.put("indPvtInstData", indPvtInstData);*/
        data.put("generalCategory", generalCategory);        
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
	        /*District=null;*/
		}
	}
	@RequestMapping(value="/nlepBCPDashBoard", method=RequestMethod.GET)
	public ModelAndView showNLEPBCPDashBoardOnLoad(HttpServletRequest request,HttpServletResponse response){
	ModelAndView modelView=new ModelAndView();
	String bcpName=request.getParameter("dashboardTypeId");
	int directorateId=Integer.parseInt(request.getParameter("directorateId"));
	int year=Integer.parseInt(request.getParameter("year"));
	int month=Integer.parseInt(request.getParameter("month"));
    modelView.addObject("directorateId", directorateId);
    modelView.addObject("yearList", Util.yearList);
    modelView.addObject("monthsList", Util.monthsList);
    modelView.addObject("indicatorYer", year);
    modelView.addObject("indicatorMnth",Util.getMonthIndexByShortName(request.getParameter("month")));
	modelView.setViewName("NLEPBCPDashBoard");
	return modelView;
	}
	@RequestMapping(value="/nlepBCPDashBoard", method=RequestMethod.POST)
	public ModelAndView showNLEPBCPDashBoard(HttpServletRequest request,HttpServletResponse response){
		  ModelAndView modelView=new ModelAndView();
		  String bcpName=request.getParameter("dashboardTypeId");
		  int directorateId=Integer.parseInt(request.getParameter("directorateId"));
		  int year=Integer.parseInt(request.getParameter("year"));
		  int month=Integer.parseInt(request.getParameter("month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();
		  JSONArray jsonArray=nlepReportManager.getNLEPBCPDashboardData(directorateId,bcpName,year,indMonth,loggedUser);
		  JSONArray jsonArrayRight=nlepReportManager.getNLEPBCPDashboardDistrictData(directorateId,bcpName,year,indMonth,loggedUser);
          modelView.addObject("jsonArrayRight", jsonArrayRight);
		  modelView.addObject("jsonArray", jsonArray);
          modelView.addObject("directorateId", directorateId);
          modelView.addObject("yearList", Util.yearList);
          modelView.addObject("monthsList", Util.monthsList);
          modelView.addObject("indicatorYer", year);
          modelView.addObject("bcpName", bcpName);
          modelView.addObject("indicatorMnth",month);
		  modelView.setViewName("NLEPBCPDashBoard");
		  return modelView;
	}
	@RequestMapping(value="/searchNLEPBCPDashBoard", method=RequestMethod.POST)
	public @ResponseBody String searchNLEPBCPDashBoard(HttpServletRequest request,HttpServletResponse response){
		  String bcpName=request.getParameter("dashboardTypeId");
		  int directorateId=Integer.parseInt(request.getParameter("directorateId"));
		  int year=Integer.parseInt(request.getParameter("year"));
		  int month=Integer.parseInt(request.getParameter("month"));
		  String indMonth=Util.getMonthByIndex(month);
		  indMonth=new StringBuilder(indMonth).substring(0,3);
		  String loggedUser=UserInfo.getLoggedInUserName();
		  JSONArray jsonArray=nlepReportManager.getNLEPBCPDashboardData(directorateId,bcpName,year,indMonth,loggedUser);
		  JSONArray jsonArrayRight=nlepReportManager.getNLEPBCPDashboardDistrictData(directorateId,bcpName,year,indMonth,loggedUser);
          JSONObject object=new JSONObject();
          try {
			  object.put("jsonArrayRight", jsonArrayRight);
			  object.put("jsonArray", jsonArray);
	          object.put("directorateId", directorateId);
	          object.put("yearList", Util.yearList);
	          object.put("monthsList", Util.monthsList);
	          object.put("indicatorYer", year);
	          object.put("bcpName", bcpName);
	          object.put("indicatorMnth",month);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          return object.toString();
		 
	}
}
