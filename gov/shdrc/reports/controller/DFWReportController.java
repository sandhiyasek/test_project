package gov.shdrc.reports.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import gov.shdrc.dataentry.service.IDfwDataEntryManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.ICommonReportManager;
import gov.shdrc.reports.service.IDFWReportManager;
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
public class DFWReportController {
	@Autowired(required=true)
	IDFWReportManager dfwReportManager;
	@Autowired
	IDfwDataEntryManager dfwDataEntryManager;
	@Autowired(required=true)
	ICommonReportManager commonReportManager;
	
	@RequestMapping(value="/dfwDashboardZone", method=RequestMethod.GET)
	public ModelAndView onLoadDashboardZone(HttpServletRequest request) throws SHDRCException{
		int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();	
		 UserInfo.hasValidReportAccess(ShdrcConstants.Role.DFW);
		 String indicatorCategory="Monthly";
		 CommonStringList commonsList=dfwReportManager.getDashboardIntMaxMonthAndYear(directorateId, indicatorCategory);
		 int year=commonsList.getId();
		 String month=commonsList.getName();
		 String loggedUser=UserInfo.getLoggedInUserName();
		 JSONArray indJsonArray=dfwReportManager.getDFWIndicatorList(directorateId, indicatorCategory, year, month, loggedUser);
		 modelAndView.addObject("indJsonArray", indJsonArray);
		 modelAndView.addObject("yearList", Util.dashboardYearList);
		 modelAndView.addObject("monthsList", Util.monthsList);
		 modelAndView.addObject("directorateId",directorateId);
		 modelAndView.addObject("loggedUser",loggedUser);		       
         modelAndView.addObject("indicatorYer", year);
         /*modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName(month));*/
         modelAndView.addObject("indicatorMnth",Util.getMonthIndexByShortName("Apr"));
		 modelAndView.setViewName("dfwDashboardZone");
		 return modelAndView;	
	}
	
	@RequestMapping(value="/dfwReportZone", method=RequestMethod.GET)
	public ModelAndView onLoadReportZone(HttpServletRequest request) throws SHDRCException
	{	
		ModelAndView modelAndView=null;
		Integer year;
		String month=null;
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
			modelAndView.setViewName("dfwReportZone");
			
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.DFW);
			year = Calendar.getInstance().get(Calendar.YEAR);			
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			fromYear = toYear = year;
			fromMonth = toMonth = month;			
			reportName="DFW_Male_Sterilisation_New";
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
			modelAndView.addObject("reportZoneData", dfwReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName));
		}finally{
			monthsList=null;	
			yearList=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/dfwReportZoneSearchChange", method=RequestMethod.POST)
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
		String userName=UserInfo.getLoggedInUserName();		
		
		JSONArray monthReportData=null;
		monthReportData =  dfwReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
		out.println(monthReportData);		
	}
	
	@RequestMapping(value="/dfwReportZoneEmployeeReport", method=RequestMethod.GET)
	public ModelAndView onLoadReportZoneEmployeeReport(HttpServletRequest request) throws SHDRCException
	{	
		ModelAndView modelAndView=null;
		Integer year;
		String month=null;
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
			modelAndView.setViewName("dfwReportZoneEmployeeReport");
			
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.DFW);
			year = Calendar.getInstance().get(Calendar.YEAR);			
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			fromYear = toYear = year;
			fromMonth = toMonth = month;			
			reportName="DFW_EmpForm";
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			List<CommonStringList> postList=dfwDataEntryManager.getPostList(ShdrcConstants.DirectorateId.DFW);
			modelAndView.addObject("postList", postList);
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
			modelAndView.addObject("directorateId", directorateId);
			modelAndView.addObject("month", monthName);
			modelAndView.addObject("year", year);
			modelAndView.addObject("reportName", reportName);
			modelAndView.addObject("reportZoneEmpData", dfwReportManager.getReportZoneEmpData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName));
		}finally{
			monthsList=null;	
			yearList=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/dfwReportZoneEmployeeReportSearchChange", method=RequestMethod.POST)
	public @ResponseBody void getcorrespondingEmployeeData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
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
		
		JSONArray reportZoneEmpData=null;
		reportZoneEmpData =  dfwReportManager.getReportZoneEmpData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,userName);
		out.println(reportZoneEmpData);		
	}
	
	@RequestMapping(value="/dfwAnalysisZone", method=RequestMethod.GET)
	public ModelAndView onLoadAnalysisZone(HttpServletRequest request) throws SHDRCException, JSONException{
		ModelAndView modelAndView=null;
		String analysisReport1=null;
		String analysisReport2=null;
		String analysisReport3=null;
		String analysisReport4=null;
		String analysisReport5=null;
		String analysisReport6=null;
		Calendar cal=null;
		String month=null;
		Integer year;
		Integer directorateId;
		String userName=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		JSONArray analysisZoneReport1FirstSeriesData=null;
		JSONArray analysisZoneReport1SecondSeriesData=null;
		JSONArray analysisZoneReport2FirstSeriesData=null;
		JSONArray analysisZoneReport2SecondSeriesData=null;
		JSONArray analysisZoneReport3FirstSeriesData=null;
		JSONArray analysisZoneReport3SecondSeriesData=null;
		JSONArray analysisZoneReport4FirstSeriesData=null;
		JSONArray analysisZoneReport4SecondSeriesData=null;
		JSONArray analysisZoneReport5FirstSeriesData=null;
		JSONArray analysisZoneReport5SecondSeriesData=null;
		JSONArray analysisZoneReport6FirstSeriesData=null;
		JSONArray analysisZoneReport6SecondSeriesData=null;
		try{
			modelAndView=new ModelAndView();
			modelAndView.setViewName("dfwAnalysisZone");
			
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.DFW);
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			modelAndView.addObject("yearList", yearList);
			modelAndView.addObject("monthsList", monthsList);
		
			analysisReport1 = "Sterilisation_Comparison_yearwise";
			analysisReport2 = "Sterilisation_Comparison_deptwise";
			analysisReport3 = "IUCD_Comparison_yearwise";
			analysisReport4 = "Coverage_of_Deliveries";
			analysisReport5 = "IUD_Prime_Child_Comparison";
			analysisReport6 = "DFW_PHC_Performance_Comparison";
			year = Calendar.getInstance().get(Calendar.YEAR);	
			cal=Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			userName=UserInfo.getLoggedInUserName();	
			
			JSONObject analysisZoneReport1Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
			JSONObject analysisZoneReport2Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
			JSONObject analysisZoneReport3Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
			JSONObject analysisZoneReport4Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
			JSONObject analysisZoneReport5Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport5,year,month,userName);
			JSONObject analysisZoneReport6Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport6,year,month,userName);
			
			if(analysisZoneReport1Data!=null && analysisZoneReport1Data.length()>0){
				analysisZoneReport1FirstSeriesData=analysisZoneReport1Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneReport1SecondSeriesData=analysisZoneReport1Data.getJSONArray("analysisZoneSecondSeriesData");
			}	
			if(analysisZoneReport2Data!=null && analysisZoneReport2Data.length()>0){
				analysisZoneReport2FirstSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneReport2SecondSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneSecondSeriesData");
			}	
			if(analysisZoneReport3Data!=null && analysisZoneReport3Data.length()>0){
				analysisZoneReport3FirstSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneReport3SecondSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneSecondSeriesData");
			}	
			if(analysisZoneReport4Data!=null && analysisZoneReport4Data.length()>0){
				analysisZoneReport4FirstSeriesData=analysisZoneReport4Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneReport4SecondSeriesData=analysisZoneReport4Data.getJSONArray("analysisZoneSecondSeriesData");
			}	
			if(analysisZoneReport5Data!=null && analysisZoneReport5Data.length()>0){
				analysisZoneReport5FirstSeriesData=analysisZoneReport5Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneReport5SecondSeriesData=analysisZoneReport5Data.getJSONArray("analysisZoneSecondSeriesData");
			}	
			if(analysisZoneReport6Data!=null && analysisZoneReport6Data.length()>0){
				analysisZoneReport6FirstSeriesData=analysisZoneReport6Data.getJSONArray("analysisZoneFirstSeriesData");
				analysisZoneReport6SecondSeriesData=analysisZoneReport6Data.getJSONArray("analysisZoneSecondSeriesData");
			}			
			
			modelAndView.addObject("directorateId", request.getParameter("directorateId"));
			modelAndView.addObject("analysisZoneReport1Data1", analysisZoneReport1FirstSeriesData);
			modelAndView.addObject("analysisZoneReport1Data2", analysisZoneReport1SecondSeriesData);
			modelAndView.addObject("analysisZoneReport2Data1", analysisZoneReport2FirstSeriesData);
			modelAndView.addObject("analysisZoneReport2Data2", analysisZoneReport2SecondSeriesData);
			modelAndView.addObject("analysisZoneReport3Data1", analysisZoneReport3FirstSeriesData);
			modelAndView.addObject("analysisZoneReport3Data2", analysisZoneReport3SecondSeriesData);
			modelAndView.addObject("analysisZoneReport4Data1", analysisZoneReport4FirstSeriesData);
			modelAndView.addObject("analysisZoneReport4Data2", analysisZoneReport4SecondSeriesData);
			modelAndView.addObject("analysisZoneReport5Data1", analysisZoneReport5FirstSeriesData);
			modelAndView.addObject("analysisZoneReport5Data2", analysisZoneReport5SecondSeriesData);
			modelAndView.addObject("analysisZoneReport6Data1", analysisZoneReport6FirstSeriesData);
			modelAndView.addObject("analysisZoneReport6Data2", analysisZoneReport6SecondSeriesData);
			modelAndView.addObject("year", year);
			modelAndView.addObject("month", monthName);
		}finally{
			cal=null;
			analysisReport1=null;
			analysisReport2=null;
			analysisReport3=null;
			analysisReport4=null;
			analysisReport5=null;
			analysisReport6=null;
			month=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/dfwAnalysisZoneSearchChange", method=RequestMethod.POST)
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
		String analysisReport5=null;
		String analysisReport6=null;
		JSONArray analysisZoneReport1FirstSeriesData=null;
		JSONArray analysisZoneReport1SecondSeriesData=null;
		JSONArray analysisZoneReport2FirstSeriesData=null;
		JSONArray analysisZoneReport2SecondSeriesData=null;
		JSONArray analysisZoneReport3FirstSeriesData=null;
		JSONArray analysisZoneReport3SecondSeriesData=null;
		JSONArray analysisZoneReport4FirstSeriesData=null;
		JSONArray analysisZoneReport4SecondSeriesData=null;
		JSONArray analysisZoneReport5FirstSeriesData=null;
		JSONArray analysisZoneReport5SecondSeriesData=null;
		JSONArray analysisZoneReport6FirstSeriesData=null;
		JSONArray analysisZoneReport6SecondSeriesData=null;
		analysisReport1 = "Sterilisation_Comparison_yearwise";
		analysisReport2 = "Sterilisation_Comparison_deptwise";
		analysisReport3 = "IUCD_Comparison_yearwise";
		analysisReport4 = "Coverage_of_Deliveries";
		analysisReport5 = "IUD_Prime_Child_Comparison";
		analysisReport6 = "DFW_PHC_Performance_Comparison";
		
		JSONObject analysisZoneReport1Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport1,year,month,userName);
		JSONObject analysisZoneReport2Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport2,year,month,userName);
		JSONObject analysisZoneReport3Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport3,year,month,userName);
		JSONObject analysisZoneReport4Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport4,year,month,userName);
		JSONObject analysisZoneReport5Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport5,year,month,userName);
		JSONObject analysisZoneReport6Data= dfwReportManager.getAnalysisZoneData(directorateId,analysisReport6,year,month,userName);
		
		if(analysisZoneReport1Data!=null && analysisZoneReport1Data.length()>0){
			analysisZoneReport1FirstSeriesData=analysisZoneReport1Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneReport1SecondSeriesData=analysisZoneReport1Data.getJSONArray("analysisZoneSecondSeriesData");
		}	
		if(analysisZoneReport2Data!=null && analysisZoneReport2Data.length()>0){
			analysisZoneReport2FirstSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneReport2SecondSeriesData=analysisZoneReport2Data.getJSONArray("analysisZoneSecondSeriesData");
		}	
		if(analysisZoneReport3Data!=null && analysisZoneReport3Data.length()>0){
			analysisZoneReport3FirstSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneReport3SecondSeriesData=analysisZoneReport3Data.getJSONArray("analysisZoneSecondSeriesData");
		}	
		if(analysisZoneReport4Data!=null && analysisZoneReport4Data.length()>0){
			analysisZoneReport4FirstSeriesData=analysisZoneReport4Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneReport4SecondSeriesData=analysisZoneReport4Data.getJSONArray("analysisZoneSecondSeriesData");
		}	
		if(analysisZoneReport5Data!=null && analysisZoneReport5Data.length()>0){
			analysisZoneReport5FirstSeriesData=analysisZoneReport5Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneReport5SecondSeriesData=analysisZoneReport5Data.getJSONArray("analysisZoneSecondSeriesData");
		}	
		if(analysisZoneReport6Data!=null && analysisZoneReport6Data.length()>0){
			analysisZoneReport6FirstSeriesData=analysisZoneReport6Data.getJSONArray("analysisZoneFirstSeriesData");
			analysisZoneReport6SecondSeriesData=analysisZoneReport6Data.getJSONArray("analysisZoneSecondSeriesData");
		}	
		
		data.put("analysisZoneReport1Data1", analysisZoneReport1FirstSeriesData);
		data.put("analysisZoneReport1Data2", analysisZoneReport1SecondSeriesData);
		data.put("analysisZoneReport2Data1", analysisZoneReport2FirstSeriesData);
		data.put("analysisZoneReport2Data2", analysisZoneReport2SecondSeriesData);
		data.put("analysisZoneReport3Data1", analysisZoneReport3FirstSeriesData);
		data.put("analysisZoneReport3Data2", analysisZoneReport3SecondSeriesData);
		data.put("analysisZoneReport4Data1", analysisZoneReport4FirstSeriesData);
		data.put("analysisZoneReport4Data2", analysisZoneReport4SecondSeriesData);
		data.put("analysisZoneReport5Data1", analysisZoneReport5FirstSeriesData);
		data.put("analysisZoneReport5Data2", analysisZoneReport5SecondSeriesData);
		data.put("analysisZoneReport6Data1", analysisZoneReport6FirstSeriesData);
		data.put("analysisZoneReport6Data2", analysisZoneReport6SecondSeriesData);
		out.println(data);
	}
	
	@RequestMapping(value="/dfwIndicatorZone", method=RequestMethod.GET)
	public ModelAndView dfwOnLoadIndicatorZone(HttpServletRequest request) throws JSONException, SHDRCException
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
			modelAndView.setViewName("dfwIndicatorZone");
			
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.DFW);
			directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
	        modelAndView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			modelAndView.addObject("yearList", Util.yearList);
			modelAndView.addObject("monthsList", Util.monthsList);
	        List<String> IndicatorCategoryList=dfwReportManager.getIndicatorCategories(directorateId);
	        indicatorCategory=IndicatorCategoryList.get(1);
	        modelAndView.addObject("IndicatorCategoryList", IndicatorCategoryList);
	        modelAndView.addObject("indicatorCategory", indicatorCategory);
	        CommonStringList commonsList=commonReportManager.getIndicatorMaxMonthAndYear(directorateId,indicatorCategory);
			maxYear=commonsList.getId();
			maxMonth=commonsList.getName().substring(0, 3);
			modelAndView.addObject("month", commonsList.getName());
			modelAndView.addObject("year", maxYear);
	      //current year is from year ,to year and current month is from month,to month
	        JSONArray indPvtStateData=dfwReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,maxMonth,maxYear,maxMonth,maxYear,userName);
	        modelAndView.addObject("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        modelAndView.addObject("selectedIndicator", indicators);
		        List<String> generalCategoryList=dfwReportManager.getGeneralCategory(directorateId,indicators);
		        modelAndView.addObject("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        modelAndView.addObject("generalCategory", generalCategory);
		        JSONArray indPvtDistData=dfwReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators, generalCategory, maxMonth, maxYear, maxMonth, maxYear, userName);
		        modelAndView.addObject("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        district=districtObj.get("District").toString();
		        modelAndView.addObject("selectedDistrict", district);
		        JSONArray indPvtInstData=dfwReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,maxMonth,maxYear,maxMonth,maxYear,userName);
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
	
	@RequestMapping(value="/dfwOnSearch", method=RequestMethod.POST)
	public @ResponseBody void dfwGetIndicatorData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtStateData=dfwReportManager.getIndicatorPvtStateData(directorateId,indicatorCategory,fromMonth,fromYear,toMonth,toYear,userName);
	        data.put("indPvtStateData", indPvtStateData);
	        if(indPvtStateData.length() != 0){
		        JSONObject stateObj=indPvtStateData.getJSONObject(0);
		        indicators=stateObj.get("Indicator").toString();
		        data.put("selectedIndicator", indicators);
		        List<String> generalCategoryList=dfwReportManager.getGeneralCategory(directorateId,indicators);
		        data.put("generalCategoryList", generalCategoryList);
		        generalCategory=generalCategoryList.get(0);
		        data.put("generalCategory", generalCategory);
		        JSONArray indPvtDistData=dfwReportManager.getIndicatorPvtDistrictData(directorateId, indicatorCategory, indicators,generalCategory, fromMonth, fromYear, toMonth, toYear, userName);
		        data.put("indPvtDistData", indPvtDistData);
		        if(indPvtDistData.length() != 0){
			        JSONObject districtObj=indPvtDistData.getJSONObject(0);
			        district=districtObj.get("District").toString();
			        data.put("selectedDist", district);
			        JSONArray indPvtInstData=dfwReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicators,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	@RequestMapping(value="/dfwDistrictData", method=RequestMethod.POST)
	public @ResponseBody void dfwGetdistrictData(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        List<String> generalCategoryList=dfwReportManager.getGeneralCategory(directorateId,indicator);
	        data.put("generalCategoryList", generalCategoryList);
	        generalCategory=generalCategoryList.get(0);
	        data.put("generalCategory", generalCategory);
	        JSONArray indPvtDistData=dfwReportManager.getIndicatorPvtDistrictData(directorateId,category,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        data.put("indPvtDistData", indPvtDistData);
	        if(indPvtDistData.length() != 0){
		        JSONObject dtObj=indPvtDistData.getJSONObject(0);
		        district=dtObj.get("District").toString();
		        data.put("district", district);  
		        JSONArray indPvtInstData=dfwReportManager.getIndicatorPvtInstitutionData(directorateId,category,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/dfwOnTabChange", method=RequestMethod.POST)
	public @ResponseBody void dfwOnTabChange(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException{
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
	        JSONArray indPvtDistData=dfwReportManager.getIndicatorPvtDistrictData(directorateId,indicatorCategory,indicator,generalCategory,fromMonth,fromYear,toMonth,toYear,userName);        
	        data.put("indPvtDistData", indPvtDistData);
	        if(indPvtDistData.length() != 0){
		        JSONObject districtObj=indPvtDistData.getJSONObject(0);
		        District=districtObj.get("District").toString();
		        data.put("District", District);  
		        JSONArray indPvtInstData=dfwReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,District,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/dfwInstitutionData", method=RequestMethod.POST)
	public @ResponseBody void dfwGetInstitutionData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{
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
	        JSONArray indPvtInstData=dfwReportManager.getIndicatorPvtInstitutionData(directorateId,indicatorCategory,indicator,generalCategory,district,fromMonth,fromYear,toMonth,toYear,userName);
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
	
	@RequestMapping(value="/dfwFreeHandZone", method=RequestMethod.GET)
	public ModelAndView FreeHandZone(HttpServletRequest request) throws SHDRCException{
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));		
		 ModelAndView modelAndView=new ModelAndView();
		 List indCategory=dfwReportManager.getFreeHandZoneIndCategory(directorateId);
		 List<CommonStringList> indNamesByCategory=dfwReportManager.getFreeHandZoneIndNamesByCategory(directorateId,String.valueOf(indCategory.get(0)));
		 List indYearByNameandCate=dfwReportManager.getIndYearByNameandCategory(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()));
		 JSONArray indJsonArray=dfwReportManager.getFreeHandZoneData(directorateId,String.valueOf(indCategory.get(0)),String.valueOf(indNamesByCategory.get(0).getId()),Integer.parseInt(String.valueOf(indYearByNameandCate.get(0))));
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
	
	@RequestMapping(value="/dfwOnCategoryChangeSearch", method=RequestMethod.POST)
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
	     List<CommonStringList> indNamesByCategory=dfwReportManager.getFreeHandZoneIndNamesByCategory(directorateId,indicatorCategory);
	     String json=new Gson().toJson(indNamesByCategory);  
	     out.println(json);
	}
	@RequestMapping(value="/dfwOnIndicatorNameChangeSearch", method=RequestMethod.POST)
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
		 List indYearByNameandCate=dfwReportManager.getIndYearByNameandCategory(directorateId,indicatorCategory,indicatorName);
		 String json=new Gson().toJson(indYearByNameandCate);  
	     out.println(json);
	}
	@RequestMapping(value="/dfwFreeHandZoneBySearch", method=RequestMethod.POST)
	public  @ResponseBody String ShowFreeHandZoneBySearcht(HttpServletRequest request,HttpServletResponse response){
		 int directorateId=Integer.parseInt(request.getParameter("directorateId"));	
		  String indicatorCategory=request.getParameter("IndicatorCategory");
		  String indCategoryName=request.getParameter("IndicatorName");
		  String year=request.getParameter("Year");
		  JSONObject object=new JSONObject();      	
	 	  JSONArray indJsonArray=dfwReportManager.getFreeHandZoneData(directorateId,indicatorCategory,indCategoryName,Integer.parseInt(year));
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
	@RequestMapping(value="/dfwReportForDistrict", method=RequestMethod.POST)
	public  @ResponseBody String ShowDistrictReport(HttpServletRequest request) throws JSONException{		
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
	      try {
	    	  JSONArray jsnArray=dfwReportManager.getDistrictwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName, year, indMonth, loggedUser);
	    	  request.setAttribute("indicatorDetailsforDistrict", jsnArray);
		      object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{}
	      return object.toString();
	}
	
	@RequestMapping(value="/dfwReportForInstitution", method=RequestMethod.POST)
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
	    	  JSONArray jsnArray=dfwReportManager.getInstitutionwiseIndicaotrDetails(directorateID, indicatorCategory,indicatorName,districtName, year, indMonth, loggedUser);
	    	  object.put("indicatorDetailsforDistrict",jsnArray);
	      }finally{} 
	      return object.toString();
	}    
	
	@RequestMapping(value="/dfwSearchIndicatorList", method=RequestMethod.POST)
	public ModelAndView searchIndicatorListByFilter(HttpServletRequest request,HttpServletResponse response){	    		
		ModelAndView modelView=new ModelAndView();
		String indicatorFrequency=request.getParameter("indFrequency");
		String indicatorYer=request.getParameter("year");
		String indicatorMnth=request.getParameter("month");		
		indicatorMnth=Util.getMonthByIndex(Integer.parseInt(indicatorMnth));
		int directorateId=Integer.parseInt(request.getParameter("directorateId"));
		indicatorMnth=new StringBuilder(indicatorMnth).substring(0,3);
		String loggedUser=UserInfo.getLoggedInUserName();    		
		JSONArray indJsonArray=dfwReportManager.getDFWIndicatorList(directorateId, indicatorFrequency, Integer.parseInt(indicatorYer), indicatorMnth, loggedUser);  		
		modelView.addObject("indJsonArray", indJsonArray);
		modelView.addObject("indicatorFrqncy", indicatorFrequency);
		modelView.addObject("indicatorYer", indicatorYer);
		modelView.addObject("indicatorMnth", request.getParameter("month"));	
		modelView.addObject("yearList", Util.yearList);
		modelView.addObject("monthsList", Util.monthsList);
		modelView.addObject("directorateId",directorateId);	
		modelView.addObject("loggedUser",loggedUser);
		modelView.setViewName("dfwDashboardZone");
    	return modelView;
	} 
}
