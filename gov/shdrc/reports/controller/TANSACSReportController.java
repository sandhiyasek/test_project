package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.ICommonReportManager;
import gov.shdrc.reports.service.ITANSASCReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

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
public class TANSACSReportController {
	@Autowired(required=true)
	ITANSASCReportManager tansacsReportManager;
	@Autowired(required=true)
	ICommonReportManager commonReportManager;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/tansacsDashboardZone", method=RequestMethod.GET)
	public ModelAndView onLoadDashboardZone(HttpServletRequest request) throws SHDRCException{	
		 ModelAndView modelAndView=new ModelAndView();	
		 try{
			 UserInfo.hasValidReportAccess(ShdrcConstants.Role.TANSACS);
			 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
			 modelAndView.addObject("directorateId",directorateId);
			 List indTempList=new ArrayList();		 
			 indTempList.add("Monthly");
			 indTempList.add("Quarterly");		 
			 String indicatorCategory=String.valueOf(indTempList.get(0));
			 CommonStringList commonsList=tansacsReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
			 int year=commonsList.getId();
			 String month=commonsList.getName();
			 String loggedUser=UserInfo.getLoggedInUserName();
			 JSONArray indJsonArray=tansacsReportManager.getTANSACSIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);		 
			 modelAndView.addObject("indJsonArray", indJsonArray);
			 modelAndView.addObject("yearList", Util.yearList);
			 modelAndView.addObject("monthsList", Util.monthsList);
			 modelAndView.addObject("IndFrncyList",indTempList);			
			 modelAndView.addObject("loggedUser",loggedUser);
			 modelAndView.addObject("initialTabName",indicatorCategory);		       
	         modelAndView.addObject("indicatorYer", year);
	         modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName(month));
			 modelAndView.setViewName("tansacsDashboardZone");
		 }finally{}
		 return modelAndView;
	}
	
	@RequestMapping(value="/tansacsReportZone", method=RequestMethod.GET)
	public ModelAndView onLoadReportZone(HttpServletRequest request) throws SHDRCException
	{	
		ModelAndView modelAndView=null;
		try{
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.TANSACS);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("tansacsReportZone");
		}finally{
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/tansacsAnalysisZone", method=RequestMethod.GET)
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
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.TANSACS);
			modelAndView=new ModelAndView();
			modelAndView.setViewName("tansacsAnalysisZone");
			
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
		
			analysisReport1 = "TANSACS - Blood Bags Comparison";
			analysisReport2 = "TANSACS - Test Kits Comparison";
			analysisReport3 = "TANSACS - No. of Blood units collected";
			analysisReport4 = "TANSACS - STI Comparison";
								
			year = Calendar.getInstance().get(Calendar.YEAR);	
			cal=Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();	
			
			JSONArray analysisZoneReport1Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
			JSONArray analysisZoneReport2Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
			JSONArray analysisZoneReport3Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
			JSONArray analysisZoneReport4Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
			
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
	
	@RequestMapping(value="/tansacsFreeHandZone", method=RequestMethod.GET)
	public ModelAndView FreeHandZone(HttpServletRequest request) throws SHDRCException{
		 UserInfo.hasValidReportAccess(ShdrcConstants.Role.TANSACS);
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();
		 List indCategory=tansacsReportManager.getFreeHandZoneIndCategory(directorateId);
		 List<CommonStringList> indNamesByCategory=tansacsReportManager.getFreeHandZoneIndNamesByCategory(directorateId,String.valueOf(indCategory.get(0)));
		 List indYearByNameandCate=tansacsReportManager.getIndYearByNameandCategory(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()));
		 JSONArray indJsonArray=tansacsReportManager.getFreeHandZoneData(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()),Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
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
	
	@RequestMapping(value="/tansacsOnCategoryChangeSearch", method=RequestMethod.POST)
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
	     List<CommonStringList> indNamesByCategory=tansacsReportManager.getFreeHandZoneIndNamesByCategory(directorateId,indicatorCategory);
	     String json=new Gson().toJson(indNamesByCategory);  
	     out.println(json);
	}
	@RequestMapping(value="/tansacsOnIndicatorNameChangeSearch", method=RequestMethod.POST)
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
		 List indYearByNameandCate=tansacsReportManager.getIndYearByNameandCategory(directorateId,indicatorCategory,indicatorName);
		 String json=new Gson().toJson(indYearByNameandCate);  
	     out.println(json);
	}
	@RequestMapping(value="/tansacsFreeHandZoneBySearch", method=RequestMethod.POST)
	public  @ResponseBody String ShowFreeHandZoneBySearcht(HttpServletRequest request,HttpServletResponse response){
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		  String indicatorCategory=request.getParameter("IndicatorCategory");
		  String indCategoryName=request.getParameter("IndicatorName");
		  String year=request.getParameter("Year");
		  JSONObject object=new JSONObject();      	
	 	  JSONArray indJsonArray=tansacsReportManager.getFreeHandZoneData(directorateId,indicatorCategory,indCategoryName,Integer.parseInt(year));
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
	
	@RequestMapping(value="/tansacsAnalysisZoneSearchChange", method=RequestMethod.POST)
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
		analysisReport1 = "TANSACS - Blood Bags Comparison";
		analysisReport2 = "TANSACS - Test Kits Comparison";
		analysisReport3 = "TANSACS - No. of Blood units collected";
		analysisReport4 = "TANSACS - STI Comparison";
		
		JSONArray analysisZoneReport1Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
		JSONArray analysisZoneReport2Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
		JSONArray analysisZoneReport3Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
		JSONArray analysisZoneReport4Data= tansacsReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
		data.put("analysisZoneReport1Data", analysisZoneReport1Data);
        data.put("analysisZoneReport2Data", analysisZoneReport2Data);
        data.put("analysisZoneReport3Data", analysisZoneReport3Data);
        data.put("analysisZoneReport4Data", analysisZoneReport4Data);
		out.println(data);
	}
	
	@RequestMapping(value="/tansacsIndicatorZone", method=RequestMethod.GET)
	public ModelAndView tansacsOnLoadIndicatorZone(HttpServletRequest request) throws SHDRCException, JSONException
	{	
		ModelAndView modelAndView=null;
		SimpleDateFormat sdf=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
        int directorateId=0;
        String indicatorCategory= null;
		String indicators=null;
		String district=null;
		String generalCategory=null;
		try{
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.TANSACS);
			modelAndView = new ModelAndView();
			modelAndView.setViewName("tansacsIndicatorZone");
			directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			modelAndView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			modelAndView.addObject("yearList", Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
	        List<String> IndicatorCategoryList=tansacsReportManager.getIndicatorCategories(directorateId);
	        indicatorCategory=IndicatorCategoryList.get(0);
	        modelAndView.addObject("IndicatorCategoryList", IndicatorCategoryList);
	        modelAndView.addObject("indicatorCategory", indicatorCategory);
	        CommonStringList commonsList=commonReportManager.getIndicatorMaxMonthAndYear(directorateId,indicatorCategory);
			maxYear=commonsList.getId();
			maxMonth=commonsList.getName().substring(0, 3);
			modelAndView.addObject("month", commonsList.getName());
			modelAndView.addObject("year", maxYear);
	      //current year is from year ,to year and current month is from month,to month
	        JSONArray indPvtStateData=tansacsReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,maxMonth,maxYear,maxMonth,maxYear,userName);
	        modelAndView.addObject("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        modelAndView.addObject("selectedIndicator", indicators);
		        List<String> generalCategoryList=tansacsReportManager.getGeneralCategory(directorateId,indicators);
		        modelAndView.addObject("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        modelAndView.addObject("generalCategory", generalCategory);
		        JSONArray indPvtDistData=tansacsReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators, generalCategory, maxMonth, maxYear, maxMonth, maxYear, userName);
		        modelAndView.addObject("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        district=districtObj.get("District").toString();
		        modelAndView.addObject("selectedDistrict", district);
		        JSONArray indPvtInstData=tansacsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,maxMonth,maxYear,maxMonth,maxYear,userName);
		        modelAndView.addObject("indPvtInstData", indPvtInstData);
	        }
	        }
		}finally{
			sdf=null;
			maxMonth=null;
			userName=null;
			indicatorCategory= null;
	        indicators =null;
	        district=null;
		}
		return modelAndView;
	}   	
	
	@RequestMapping(value="/tansacsOnSearch", method=RequestMethod.POST)
	public @ResponseBody void tansacsGetIndicatorData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtStateData=tansacsReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,fromMonth,fromYear,toMonth,toYear,userName);
	        data.put("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        data.put("selectedIndicator", indicators);
		        List<String> generalCategoryList=tansacsReportManager.getGeneralCategory(directorateId,indicators);
		        data.put("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        data.put("generalCategory", generalCategory);
		        JSONArray indPvtDistData=tansacsReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators,generalCategory, fromMonth, fromYear, toMonth, toYear, userName);
		        data.put("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
			        JSONObject districtObj=indPvtDistData.getJSONObject(0);
			        district=districtObj.get("District").toString();
			        data.put("selectedDist", district);
			        JSONArray indPvtInstData=tansacsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/tansacsDistrictData", method=RequestMethod.POST)
	public @ResponseBody void tansacsGetdistrictData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        List<String> generalCategoryList=tansacsReportManager.getGeneralCategory(directorateId,indicator);
	        data.put("generalCategoryList", generalCategoryList);
	        generalCategory=generalCategoryList.get(0);
	        data.put("generalCategory", generalCategory);
	        JSONArray indPvtDistData=tansacsReportManager.getIndicatorPvtDistrictData(directorateId,category,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        data.put("indPvtDistData", indPvtDistData);
	        if(indPvtDistData.length() != 0){
		        JSONObject dtObj=indPvtDistData.getJSONObject(0);
		        district=dtObj.get("District").toString();
		        data.put("district", district);  
		        JSONArray indPvtInstData=tansacsReportManager.getIndicatorPvtInstitutionData(directorateId,category,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/tansacsOnTabChange", method=RequestMethod.POST)
	public @ResponseBody void tansacsOnTabChange(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtDistData=tansacsReportManager.getIndicatorPvtDistrictData(directorateId,indicatorCategory,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        data.put("indPvtDistData", indPvtDistData);
	        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        District=districtObj.get("District").toString();
		        data.put("District", District);  
		        JSONArray indPvtInstData=tansacsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,District,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/tansacsInstitutionData", method=RequestMethod.POST)
	public @ResponseBody void tansacsGetInstitutionData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtInstData=tansacsReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
	        
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
	
	@RequestMapping(value="/tansacsReportForDistrict", method=RequestMethod.POST)
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
	    	  JSONArray jsnArray=tansacsReportManager.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName, year, indMonth, loggedUser);
	    	  request.setAttribute("indicatorDetailsforDistrict", jsnArray);
		      object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{}
	      return object.toString();
	}
	
	@RequestMapping(value="/tansacsReportForInstitution", method=RequestMethod.POST)
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
	    	  JSONArray jsnArray=tansacsReportManager.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,districtName, year, indMonth, loggedUser);
	    	  object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{}
	      return object.toString();
	} 
	
	@RequestMapping(value="/tansacsReportForSearchCriteria", method=RequestMethod.POST)
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
	    	  JSONArray jsnArray=tansacsReportManager.getTANSACSIndicatorList(directorateId, indicatorCategory,year, indMonth, loggedUser); 
	    	  object.put("indJsonArraySearch",jsnArray);
	      }finally{}
	      return object.toString();
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/tansacsSearchIndicatorList", method=RequestMethod.POST)
	public ModelAndView  searchIndicatorListByFilter(HttpServletRequest request,HttpServletResponse response){	    		
		ModelAndView modelView=new ModelAndView();
		List indTempList=new ArrayList();		
		indTempList.add("Monthly");
		indTempList.add("Quarterly");
		String indicatorFrequency=request.getParameter("indFrequency");
		String indicatorYer=request.getParameter("year");
		String indicatorMnth=request.getParameter("month");
		indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
		int directorateId=Integer.parseInt(request.getParameter("directorateId"));
		indicatorMnth=new StringBuilder(indicatorMnth).substring(0, 3);
		String loggedUser=UserInfo.getLoggedInUserName();    		
		JSONArray indJsonArray=tansacsReportManager.getTANSACSIndicatorList(directorateId, indicatorFrequency, Integer.parseInt(indicatorYer), indicatorMnth, loggedUser);  		
		modelView.addObject("indJsonArray", indJsonArray);
		modelView.addObject("indicatorFrqncy", indicatorFrequency);
		modelView.addObject("indicatorYer", indicatorYer);
		modelView.addObject("IndFrncyList",indTempList);
		modelView.addObject("indicatorMnth", request.getParameter("month"));	
		modelView.addObject("yearList", Util.yearList);
		modelView.addObject("monthsList", Util.monthsList);
		modelView.addObject("directorateId",directorateId);
		modelView.addObject("loggedUser",loggedUser);
		modelView.addObject("initialTabName",indicatorFrequency);
		modelView.setViewName("tansacsDashboardZone");
    	return modelView;
	} 
}
