package gov.shdrc.reports.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.reports.service.INCDReportManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NCDReportController {
	
@Autowired(required=true)
INCDReportManager ncdReportManager;
	
	//Reports Zone
	@RequestMapping(value="/ncdReportZone", method=RequestMethod.GET)
	public ModelAndView onLoadPivotTable(HttpServletRequest request) throws SHDRCException
	{	
		ModelAndView modelView=null;
		String month=null;
		Integer year;
		Integer fromYear;
		String fromMonth=null;
		Integer toYear;
		String toMonth=null;
		String reportName=null;
		Integer directorateId;
		String userName=null;
		String districtName=null;
		String subType=null;
		List<CommonStringList> monthsList=null;
		List<Integer> yearList=null;
		try{
			modelView = new ModelAndView();
			UserInfo.hasValidReportAccess(ShdrcConstants.Role.NCD);
			modelView.setViewName("ncdReportsZone");
			year = Calendar.getInstance().get(Calendar.YEAR);				
		
			Calendar cal =  Calendar.getInstance();
			String monthName = new SimpleDateFormat("MMMMM").format(cal.getTime());
			if(monthName!=null && monthName.length()>3){
				month=monthName.substring(0,3);
			}
			fromYear = toYear = year;
			fromMonth = toMonth = month;
			reportName="Form 2 Part A: Hypertension and Diabetes Screening";
			directorateId=Integer.parseInt(request.getParameter("directorateId"));
			modelView.addObject("directorateId", directorateId);
			userName=UserInfo.getLoggedInUserName();
			monthsList= Util.monthsList;
			yearList= Util.yearList;
			List<CommonStringList> districtList= ncdReportManager.getDistrictList(userName);
			List<CommonStringList> ncdSubTypeList= ncdReportManager.getNcdSubTypeList();
			
			StringBuilder allDistrictList = ncdReportManager.getAllDistrictList(userName);
			districtName = allDistrictList.toString();
			
			 if(Util.isNotNull(ncdSubTypeList)){
		        	CommonStringList ncdSubType = ncdSubTypeList.get(0);
		        	subType=ncdSubType.getName();
			 }
			
	        modelView.addObject("ncdSubTypeList", ncdSubTypeList);
	        modelView.addObject("districts", districtList);
		    modelView.addObject("yearList", yearList);
			modelView.addObject("monthsList", monthsList);
			modelView.addObject("month", monthName);
			modelView.addObject("year", year);
			modelView.addObject("reportName", reportName);
	        modelView.addObject("reportZoneData", ncdReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,
	        		districtName,subType,userName));
		}finally{			
			monthsList=null;	
			yearList=null;
		}
		return modelView;
	}
	
	@RequestMapping(value="/ncdReportZoneSearchChange", method=RequestMethod.POST)
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
		String districtName=request.getParameter("districtName");
		String subType=request.getParameter("subType");
		JSONArray monthReportData=null;
		monthReportData =  ncdReportManager.getReportZoneData(directorateId,reportName,fromYear,fromMonth,toYear,toMonth,districtName,subType,userName);
		out.println(monthReportData);
	}
	
}
