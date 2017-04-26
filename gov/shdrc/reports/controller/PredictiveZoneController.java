package gov.shdrc.reports.controller;

import gov.shdrc.reports.service.IPredictiveZoneManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class PredictiveZoneController {
	
	@Autowired(required=true)
	IPredictiveZoneManager predictiveZoneManager;
	
	@RequestMapping(value="/IMRPredictiveZone", method=RequestMethod.GET)
	public ModelAndView IMRPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
		String indicators=null;
		List<String> districtList=null;
		String indicator=null;
		String district=null;
		String month=null;
		try{
        	modelView = new ModelAndView();
        	modelView.setViewName("imrPredictiveZone");
			indicators = request.getParameter("indicator");
		    String ind = indicators==null?"IMR Dashboard I":indicators;
			modelView.addObject("indicatorName", ind);
			userName=UserInfo.getLoggedInUserName();			
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2015);
			maxYearMonth.setName("Apr");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			modelView.addObject("yearList", Util.yearList);
			modelView.addObject("monthsList", Util.monthsList);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			modelView.addObject("districtList", districtList);
			indicator="IMR Analytics";
			month="April";
			JSONArray topLeftData=predictiveZoneManager.getIMRHeatMapTopLeftData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,userName);
			JSONArray topRightData=predictiveZoneManager.getIMRHeatMapTopRightData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,userName);
			JSONArray bottomLeftData=predictiveZoneManager.getIMRHeatMapBottomLeftData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,userName);
			JSONArray bottomRightData=predictiveZoneManager.getIMRHeatMapBottomRightData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,userName);
			modelView.addObject("topLeftData", topLeftData);
			modelView.addObject("topRightData", topRightData);
			modelView.addObject("bottomLeftData", bottomLeftData);
			modelView.addObject("bottomRightData", bottomRightData);
			modelView.addObject("district", district);
			modelView.addObject("year", maxYear);
			modelView.addObject("month", month);
			
		}catch(Exception exp){
		}finally{
			sdf=null;
			maxYearMonth=null;
			maxMonth=null;
			userName=null;
			districtList=null;
			indicator=null;
			district=null;
			month=null;
		}
		return modelView;
	}
	@RequestMapping(value="/imrPredictiveOnSearch", method=RequestMethod.POST)
	public  @ResponseBody void predictiveOnSearch(HttpServletRequest request,HttpServletResponse response){	
		  String userName=null;
		  String fromMonth=null;
		  int fromYear=0;
		  String toMonth=null;
		  int toYear=0;
		  String district=null;
		  String indicator=null;
		  JSONObject data=null;
		  PrintWriter out=null;
	      try {
	    	  data=new JSONObject();
	    	  out=response.getWriter();
	    	  userName=UserInfo.getLoggedInUserName();
	    	  indicator="IMR Analytics";
	    	  fromMonth=request.getParameter("fromMonth");
	    	  fromYear=Integer.parseInt(request.getParameter("fromYear"));
	    	  toMonth=request.getParameter("toMonth");
	    	  toYear=Integer.parseInt(request.getParameter("toYear"));
	    	  district=request.getParameter("district");
	    	  JSONArray topLeftData=predictiveZoneManager.getIMRHeatMapTopLeftData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
			  JSONArray topRightData=predictiveZoneManager.getIMRHeatMapTopRightData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
			  JSONArray bottomLeftData=predictiveZoneManager.getIMRHeatMapBottomLeftData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
			  JSONArray bottomRightData=predictiveZoneManager.getIMRHeatMapBottomRightData(indicator,fromMonth,fromYear,toMonth,toYear,district,userName);
			  data.put("topLeftData", topLeftData);
			  data.put("topRightData", topRightData);
			  data.put("bottomLeftData", bottomLeftData);
			  data.put("bottomRightData", bottomRightData);
			  data.put("district", district);
			  data.put("fromYear", fromYear);
			  data.put("fromMonth", fromMonth);
			  data.put("toMonth", toMonth);
			  data.put("toYear", toYear);
			  out.println(data);
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      }finally{
	    	 indicator=null;
	    	 district=null;
	    	 fromMonth=null;
			 toMonth=null;
			 userName=null;
		  }
	}
	@RequestMapping(value="/IMRClusteringPredictiveZone", method=RequestMethod.GET)
	public ModelAndView IMRClusteringPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
        String userName=null;
        List<String> districtList=null;
		String indicator=null;
		String district=null;
		int cluster=0;
		String indicatorName=null;
		try{
			indicatorName=request.getParameter("indicator");
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("imrClusteringPredictiveZone");
			indicator="IMR Analytics 2";
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			modelView.addObject("indicatorName", indicatorName);
			modelView.addObject("districtList", districtList);
			cluster=3;
			JSONArray IMR2TopLeftData=predictiveZoneManager.getIMR2HeatMapTopLeftData(indicator,district,userName,cluster);
			JSONArray IMR2TopRightData=predictiveZoneManager.getIMR2HeatMapTopRightData(indicator,district,userName,cluster);
			JSONArray IMR2BottomData=predictiveZoneManager.getIMR2HeatMapBottomLeftData(indicator,district,userName,cluster);
			modelView.addObject("IMR2TopLeftData", IMR2TopLeftData);
			modelView.addObject("IMR2TopRightData", IMR2TopRightData);
			modelView.addObject("IMR2BottomData", IMR2BottomData);
			modelView.addObject("cluster", cluster);
			modelView.addObject("district", district);
		}catch(Exception exp){
			
		}finally{
			districtList=null;
			userName=null;
			district=null;
			indicator=null;
		}
		return modelView;
	}
	@RequestMapping(value="/IMRClusterPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void IMRClusterPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
        String userName=null;
		String indicator=null;
		String district=null;
		JSONObject data=null;
		PrintWriter out=null;
		int cluster=0;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="IMR Analytics 2";
			district=request.getParameter("district");
			cluster=Integer.parseInt(request.getParameter("cluster"));
			JSONArray IMR2TopLeftData=predictiveZoneManager.getIMR2HeatMapTopLeftData(indicator,district,userName,cluster);
			JSONArray IMR2TopRightData=predictiveZoneManager.getIMR2HeatMapTopRightData(indicator,district,userName,cluster);
			JSONArray IMR2BottomData=predictiveZoneManager.getIMR2HeatMapBottomLeftData(indicator,district,userName,cluster);
			data.put("IMR2TopLeftData", IMR2TopLeftData);
			data.put("IMR2TopRightData", IMR2TopRightData);
			data.put("IMR2BottomData", IMR2BottomData);
			data.put("district", district);
			data.put("cluster", cluster);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			indicator=null;
		}
	}
	@RequestMapping(value="/DiseaseOutbreakPredictiveZone", method=RequestMethod.GET)
	public ModelAndView DiseaseOutbreakPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
        List<String> districtList=null;
		String indicator=null;
		String district=null;
		String disease=null;
		List<String> diseaseList=null;
		String indicatorName=null;
		try{
			indicatorName=request.getParameter("indicator");			
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("diseaseOutbreakPredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2015);
			maxYearMonth.setName("Apr");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			modelView.addObject("yearList", Util.yearList);
			modelView.addObject("monthsList", Util.monthsList);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			modelView.addObject("districtList", districtList);
			diseaseList=predictiveZoneManager.getDiseaseList();
			disease=diseaseList.get(2);
			modelView.addObject("diseaseList", diseaseList);
			indicator="Disease Outbreak Analytics";
			StringBuilder builder=new StringBuilder();
			for(String s : diseaseList){
				builder.append(s);
				builder.append(",");
			}
			String diseaseStr=builder.toString();
			JSONArray topLeftData=predictiveZoneManager.getDOBTopLeftData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,diseaseStr,userName);
			JSONArray topRightData=predictiveZoneManager.getDOBTopRightData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,disease,userName);
			JSONArray bottomLeftData=predictiveZoneManager.getDOBBottomLeftData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,disease,userName);
			JSONArray bottomRightData=predictiveZoneManager.getDOBBottomRightData(indicator,maxMonth,maxYear,maxMonth,maxYear,district,disease,userName);
			modelView.addObject("topLeftData", topLeftData);
			modelView.addObject("topRightData", topRightData);
			modelView.addObject("bottomLeftData", bottomLeftData);
			modelView.addObject("bottomRightData", bottomRightData);			
			modelView.addObject("year", maxYear);
			modelView.addObject("month", maxMonth);
			modelView.addObject("district", district);
			modelView.addObject("disease", disease);
			
		}catch(Exception exp){
			
		}finally{
			sdf=null;
			maxYearMonth=null;
			maxMonth=null;
			userName=null;
			districtList=null;
			indicator=null;
			district=null;
			disease=null;
			diseaseList=null;
		}
		return modelView;
	}
	@RequestMapping(value="/DOBPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void DOBPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
        String userName=null;
		String indicator=null;
		String district=null;
		JSONObject data=null;
		PrintWriter out=null;
		String disease=null;
		int fromYear=0;
		String fromMonth=null;
		int toYear=0;
		String toMonth=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="Disease Outbreak Analytics";
			district=request.getParameter("district");
			disease=request.getParameter("disease");
			fromYear=Integer.parseInt(request.getParameter("fromYear"));
			fromMonth=request.getParameter("fromMonth");
			toYear=Integer.parseInt(request.getParameter("toYear"));
			toMonth=request.getParameter("toMonth");
			//JSONArray topLeftData=predictiveZoneManager.getDOBTopLeftData(indicator,fromMonth,fromYear,toMonth,toYear,district,disease,userName);
			JSONArray topRightData=predictiveZoneManager.getDOBTopRightData(indicator,fromMonth,fromYear,toMonth,toYear,district,disease,userName);
			JSONArray bottomLeftData=predictiveZoneManager.getDOBBottomLeftData(indicator,fromMonth,fromYear,toMonth,toYear,district,disease,userName);
			JSONArray bottomRightData=predictiveZoneManager.getDOBBottomRightData(indicator,fromMonth,fromYear,toMonth,toYear,district,disease,userName);
			//data.put("topLeftData", topLeftData);
			data.put("topRightData", topRightData);
			data.put("bottomLeftData", bottomLeftData);
			data.put("bottomRightData", bottomRightData);
			data.put("district", district);
			data.put("disease", disease);
			data.put("fromYear", fromYear);
			data.put("fromMonth", fromMonth);
			data.put("toYear", toYear);
			data.put("toMonth", toMonth);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			indicator=null;
		}
	}
	@RequestMapping(value="/CMCHISServicePredictiveZone", method=RequestMethod.GET)
	public ModelAndView CMCHISServicePredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
        String indicatorName=null;
        List<String> districtList=null;
        String district=null;
        String indicator=null;
        String month=null;
		try{
			indicatorName=request.getParameter("indicator");
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("cmchisServicePredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2015);
			maxYearMonth.setName("Nov");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			month="November";
			modelView.addObject("yearList", Util.yearList);
			modelView.addObject("monthsList", Util.monthsList);
			indicator="CMCHIS_Service Dashboard";
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(1);
			modelView.addObject("districtList", districtList);
			JSONArray CSPTopLeftData=predictiveZoneManager.getCSPTopLeftData(indicator,maxYear,maxMonth,district,userName);
			JSONArray CSPTopRightData=predictiveZoneManager.getCSPTopRightData(indicator,maxYear,maxMonth,district,userName);
			JSONArray CSPBottomLeftData=predictiveZoneManager.getCSPBottomLeftData(indicator,maxYear,maxMonth,district,userName);
			JSONArray CSPBottomRightData=predictiveZoneManager.getCSPBottomRightData(indicator,maxYear,maxMonth,district,userName);
			modelView.addObject("CSPTopLeftData", CSPTopLeftData);
			modelView.addObject("CSPTopRightData", CSPTopRightData);
			modelView.addObject("CSPBottomLeftData", CSPBottomLeftData);
			modelView.addObject("CSPBottomRightData", CSPBottomRightData);
			modelView.addObject("year", maxYear);
			modelView.addObject("month", month);
			modelView.addObject("district", district);
			
		}catch(Exception exp){
			
		}finally{
			sdf=null;
			maxYearMonth=null;
			maxMonth=null;
			userName=null;
			district=null;
			indicator=null;
			districtList=null;
		}
		return modelView;
	}
	@RequestMapping(value="/CSPPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void CSPPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
     
		JSONObject data=null;
		PrintWriter out=null;
        String userName=null;
        String indicator=null;
        String district=null;
        int year=0;
        String month=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="CMCHIS_Service Dashboard";
			district=request.getParameter("district");
			month=request.getParameter("month").substring(0,3);
			year=Integer.parseInt(request.getParameter("year"));
			JSONArray CSPTopLeftData=predictiveZoneManager.getCSPTopLeftData(indicator,year,month,district,userName);
			JSONArray CSPTopRightData=predictiveZoneManager.getCSPTopRightData(indicator,year,month,district,userName);
			JSONArray CSPBottomLeftData=predictiveZoneManager.getCSPBottomLeftData(indicator,year,month,district,userName);
			JSONArray CSPBottomRightData=predictiveZoneManager.getCSPBottomRightData(indicator,year,month,district,userName);
			data.put("CSPTopLeftData", CSPTopLeftData);
			data.put("CSPTopRightData", CSPTopRightData);
			data.put("CSPBottomLeftData", CSPBottomLeftData);
			data.put("CSPBottomRightData", CSPBottomRightData);
			data.put("month", request.getParameter("month"));
			data.put("district", district);
			data.put("year", year);
			out.println(data);
		}catch(Exception exp){
			
		}finally{			
	        userName=null;
	        indicator=null;
	        district=null;
	        month=null;
		}
	}
	@RequestMapping(value="/CMCHISProcedurePredictiveZone", method=RequestMethod.GET)
	public ModelAndView CMCHISProcedurePredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		String userName=null;
		String indicatorName=null;
		String indicator=null;
		String instType=null;
		List<String> districtList=null;
        String district=null;
        List<String> specialityList=null;
        String speciality=null;
		try{
			indicatorName=request.getParameter("indicator");
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("cmchisProcedurePredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			indicator="CMCHIS_Procedure Dashboard";
			instType="Govt";
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(1);
			modelView.addObject("districtList", districtList);
			specialityList=predictiveZoneManager.getSpecialityList();
			speciality=specialityList.get(0);
			modelView.addObject("specialityList", specialityList);
			JSONArray CPPTopLeftData=predictiveZoneManager.getCPPTopLeftData(indicator,instType,district,speciality,userName);
			JSONArray CPPTopRightData=predictiveZoneManager.getCPPTopRightData(indicator,instType,district,speciality,userName);
			JSONArray CPPBottomLeftData=predictiveZoneManager.getCPPBottomLeftData(indicator,instType,district,speciality,userName);
			JSONArray CPPBottomRightData=predictiveZoneManager.getCPPBottomRightData(indicator,instType,district,speciality,userName);
			modelView.addObject("CPPTopLeftData", CPPTopLeftData);
			modelView.addObject("CPPTopRightData", CPPTopRightData);
			modelView.addObject("CPPBottomLeftData", CPPBottomLeftData);
			modelView.addObject("CPPBottomRightData", CPPBottomRightData);
			modelView.addObject("district", district);
			modelView.addObject("instType", instType);
			modelView.addObject("speciality", speciality);
			
		}catch(Exception exp){
			
		}finally{
			userName=null;
	        indicator=null;
	        district=null;
	        instType=null;
	        speciality=null;
		}
		return modelView;
	}
	@RequestMapping(value="/CPPPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void CPPPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
     
		JSONObject data=null;
		PrintWriter out=null;
		String userName=null;
		String indicator=null;
		String instType=null;
        String district=null;
        String speciality=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="CMCHIS_Procedure Dashboard";
			district=request.getParameter("district");
			instType=request.getParameter("instType");
			speciality=request.getParameter("speciality");
			JSONArray CPPTopLeftData=predictiveZoneManager.getCPPTopLeftData(indicator,instType,district,speciality,userName);
			JSONArray CPPTopRightData=predictiveZoneManager.getCPPTopRightData(indicator,instType,district,speciality,userName);
			JSONArray CPPBottomLeftData=predictiveZoneManager.getCPPBottomLeftData(indicator,instType,district,speciality,userName);
			JSONArray CPPBottomRightData=predictiveZoneManager.getCPPBottomRightData(indicator,instType,district,speciality,userName);
			data.put("CPPTopLeftData", CPPTopLeftData);
			data.put("CPPTopRightData", CPPTopRightData);
			data.put("CPPBottomLeftData", CPPBottomLeftData);
			data.put("CPPBottomRightData", CPPBottomRightData);
			data.put("instType", instType);
			data.put("district", district);
			data.put("speciality", speciality);
			out.println(data);
		}catch(Exception exp){
			
		}finally{			
	        userName=null;
	        indicator=null;
	        district=null;
	        instType=null;
	        speciality=null;
		}
	}
	@RequestMapping(value="/CMCHISDeniedPredictiveZone", method=RequestMethod.GET)
	public ModelAndView CMCHISDeniedPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
        String indicatorName=null;
        String indicator=null;
        List<String> districtList=null;
        String district=null;
        List<String> fileTypeList=null;
        String fileType=null;
        List<String> reasonList=null;
        String reason=null;
        String instType=null;
        String month=null;
		try{
			indicatorName=request.getParameter("indicator");
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("cmchisDeniedPredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2015);
			maxYearMonth.setName("Jan");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			modelView.addObject("yearList", Util.yearList);
			modelView.addObject("monthsList", Util.monthsList);
			indicator="CMCHIS_Denied Dashboard";
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(1);
			modelView.addObject("districtList", districtList);
			fileTypeList=predictiveZoneManager.getFileTypeList();
			fileType=fileTypeList.get(0);
			modelView.addObject("fileTypeList", fileTypeList);
			reasonList=predictiveZoneManager.getReasonList(fileType);
			reason=reasonList.get(0);
			modelView.addObject("reasonList", reasonList);
			instType="Govt";
			month="January";
			JSONArray CDDTopLeftData=predictiveZoneManager.getCDDTopLeftData(indicator,maxYear,maxMonth,fileType,district,reason,instType,userName);
			JSONArray CDDTopRightData=predictiveZoneManager.getCDDTopRightData(indicator,maxYear,maxMonth,fileType,district,reason,instType,userName);
			JSONArray CDDBottomLeftData=predictiveZoneManager.getCDDBottomLeftData(indicator,maxYear,maxMonth,fileType,district,reason,instType,userName);
			JSONArray CDDBottomRightData=predictiveZoneManager.getCDDBottomRightData(indicator,maxYear,maxMonth,fileType,district,reason,instType,userName);
			modelView.addObject("CDDTopLeftData", CDDTopLeftData);
			modelView.addObject("CDDTopRightData", CDDTopRightData);
			modelView.addObject("CDDBottomLeftData", CDDBottomLeftData);
			modelView.addObject("CDDBottomRightData", CDDBottomRightData);			
			modelView.addObject("instType", instType);
			modelView.addObject("reason", reason);
			modelView.addObject("fileType", fileType);
			modelView.addObject("district", district);
			modelView.addObject("year", maxYear);
			modelView.addObject("month", month);
			
		}catch(Exception exp){
			
		}finally{
			sdf=null;
			maxYearMonth=null;
			maxMonth=null;			
	        userName=null;
	        indicatorName=null;
	        indicator=null;
	        districtList=null;
	        district=null;
	        fileTypeList=null;
	        fileType=null;
	        reasonList=null;
	        reason=null;
	        instType=null;
		}
		return modelView;
	}
	@RequestMapping(value="/getReasons", method=RequestMethod.POST)
	public @ResponseBody void getReasons(HttpServletRequest request,HttpServletResponse response)
	{	
		PrintWriter out=null;
		String fileType=null;
		List<String> reasonList=null;
		String json=null;
		try{
	    	out=response.getWriter();
	    	fileType=request.getParameter("fileType");
	    	reasonList=predictiveZoneManager.getReasonList(fileType);
	    	json=new Gson().toJson(reasonList); 
			out.println(json);
		}catch(Exception exp){
			
		}finally{
			fileType=null;
			reasonList=null;
		}
	}
	@RequestMapping(value="/CDDPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void CDDPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
     
		JSONObject data=null;
		PrintWriter out=null;
        String userName=null;
        String indicator=null;
        String district=null;
        String fileType=null;
        String reason=null;
        String instType=null;
      /*  int fromYear=0;
        String fromMonth=null;
        int toYear=0;
        String toMonth=null;*/
        int year=0;
        String month=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="CMCHIS_Denied Dashboard";
			district=request.getParameter("district");
			fileType=request.getParameter("fileType");
			reason=request.getParameter("reason");
			instType=request.getParameter("instType");
			/*fromYear=Integer.parseInt(request.getParameter("fromYear"));
			fromMonth=request.getParameter("fromMonth");
			toYear=Integer.parseInt(request.getParameter("toYear"));
			toMonth=request.getParameter("toMonth");*/
			month=request.getParameter("month").substring(0,3);
			year=Integer.parseInt(request.getParameter("year"));
			JSONArray CDDTopLeftData=predictiveZoneManager.getCDDTopLeftData(indicator,year,month,fileType,district,reason,instType,userName);
			JSONArray CDDTopRightData=predictiveZoneManager.getCDDTopRightData(indicator,year,month,fileType,district,reason,instType,userName);
			JSONArray CDDBottomLeftData=predictiveZoneManager.getCDDBottomLeftData(indicator,year,month,fileType,district,reason,instType,userName);
			JSONArray CDDBottomRightData=predictiveZoneManager.getCDDBottomRightData(indicator,year,month,fileType,district,reason,instType,userName);
			data.put("CDDTopLeftData", CDDTopLeftData);
			data.put("CDDTopRightData", CDDTopRightData);
			data.put("CDDBottomLeftData", CDDBottomLeftData);
			data.put("CDDBottomRightData", CDDBottomRightData);
			data.put("instType", instType);
			data.put("reason", reason);
			data.put("fileType", fileType);
			data.put("district", district);
			data.put("month", month);
			data.put("year", year);
			out.println(data);
		}catch(Exception exp){
			
		}finally{			
	        userName=null;
	        indicator=null;
	        district=null;
	        fileType=null;
	        reason=null;
	        instType=null;
	        month=null;
		}
	}
	@RequestMapping(value="/FullyImmunizedPredictiveZone", method=RequestMethod.GET)
	public ModelAndView FullyImmunizedPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
        String userName=null;
        String indicatorName=null;
        String indicator=null;
        List<String> yearList=null;
        String year=null;
        List<String> districtList=null;
        String district=null;
		try{
			indicatorName=request.getParameter("indicator");
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("fullyImmunizedPredictiveZone");			
			indicator="Immunization_New";
			yearList=predictiveZoneManager.getFIPYearList();
			modelView.addObject("yearList", yearList);
			year=yearList.get(0);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			modelView.addObject("districtList", districtList);
			JSONArray FIPTopLeftData=predictiveZoneManager.getFIPTopLeftData(indicator,year,district,userName);
			JSONArray FIPTopRightData=predictiveZoneManager.getFIPTopRightData(indicator,year,district,userName);
			JSONArray FIPBottomLeftData=predictiveZoneManager.getFIPBottomLeftData(indicator,year,district,userName);
			JSONArray FIPBottomRightData=predictiveZoneManager.getFIPBottomRightData(indicator,year,district,userName);
			modelView.addObject("FIPTopLeftData", FIPTopLeftData);
			modelView.addObject("FIPTopRightData", FIPTopRightData);
			modelView.addObject("FIPBottomLeftData", FIPBottomLeftData);
			modelView.addObject("FIPBottomRightData", FIPBottomRightData);
			modelView.addObject("district", district);
			modelView.addObject("year", year);		
			modelView.addObject("indicatorName", indicatorName);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			indicatorName=null;
			indicator=null;
			yearList=null;
			year=null;
	        districtList=null;
	        district=null;
		}
		return modelView;
	}
	@RequestMapping(value="/FIPPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void FIPPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
		String userName=null;
        String year=null;
        String district=null;
        JSONObject data=null;
		PrintWriter out=null;
		String indicator=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="Immunization_New";
			year=request.getParameter("year");
			district=request.getParameter("district");
			JSONArray FIPTopLeftData=predictiveZoneManager.getFIPTopLeftData(indicator,year,district,userName);
			JSONArray FIPTopRightData=predictiveZoneManager.getFIPTopRightData(indicator,year,district,userName);
			JSONArray FIPBottomLeftData=predictiveZoneManager.getFIPBottomLeftData(indicator,year,district,userName);
			JSONArray FIPBottomRightData=predictiveZoneManager.getFIPBottomRightData(indicator,year,district,userName);
			data.put("FIPTopLeftData", FIPTopLeftData);
			data.put("FIPTopRightData", FIPTopRightData);
			data.put("FIPBottomLeftData", FIPBottomLeftData);
			data.put("FIPBottomRightData", FIPBottomRightData);
			data.put("district", district);
			data.put("year", year);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			indicator=null;
			district=null;
			year=null;
		}
	}
	@RequestMapping(value="/MeaslesPredictiveZone", method=RequestMethod.GET)
	public ModelAndView MeaslesPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
        String userName=null;
        String indicatorName=null;
        String indicator=null;
        List<String> yearList=null;
        String year=null;
        List<String> districtList=null;
        String district=null;
		try{
			indicatorName=request.getParameter("indicator");
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("measlesPredictiveZone");			
			indicator="Measles_Dashboard";
			yearList=predictiveZoneManager.getFIPYearList();
			modelView.addObject("yearList", yearList);
			year=yearList.get(0);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			modelView.addObject("districtList", districtList);
			JSONArray MeaslesTopLeftData=predictiveZoneManager.getMeaslesTopLeftData(indicator,year,district,userName);
			JSONArray MeaslesTopRightData=predictiveZoneManager.getMeaslesTopRightData(indicator,year,district,userName);
			JSONArray MeaslesBottomLeftData=predictiveZoneManager.getMeaslesBottomLeftData(indicator,year,district,userName);
			JSONArray MeaslesBottomRightData=predictiveZoneManager.getMeaslesBottomRightData(indicator,year,district,userName);
			modelView.addObject("MeaslesTopLeftData", MeaslesTopLeftData);
			modelView.addObject("MeaslesTopRightData", MeaslesTopRightData);
			modelView.addObject("MeaslesBottomLeftData", MeaslesBottomLeftData);
			modelView.addObject("MeaslesBottomRightData", MeaslesBottomRightData);
			modelView.addObject("district", district);
			modelView.addObject("year", year);		
			modelView.addObject("indicatorName", indicatorName);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			indicatorName=null;
			indicator=null;
			yearList=null;
			year=null;
	        districtList=null;
	        district=null;
		}
		return modelView;
	}
	@RequestMapping(value="/MeaslesPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void MeaslesPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
		String userName=null;
        String year=null;
        String district=null;
        JSONObject data=null;
		PrintWriter out=null;
		String indicator=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="Measles_Dashboard";
			year=request.getParameter("year");
			district=request.getParameter("district");
			JSONArray MeaslesTopLeftData=predictiveZoneManager.getMeaslesTopLeftData(indicator,year,district,userName);
			JSONArray MeaslesTopRightData=predictiveZoneManager.getMeaslesTopRightData(indicator,year,district,userName);
			JSONArray MeaslesBottomLeftData=predictiveZoneManager.getMeaslesBottomLeftData(indicator,year,district,userName);
			JSONArray MeaslesBottomRightData=predictiveZoneManager.getMeaslesBottomRightData(indicator,year,district,userName);
			data.put("MeaslesTopLeftData", MeaslesTopLeftData);
			data.put("MeaslesTopRightData", MeaslesTopRightData);
			data.put("MeaslesBottomLeftData", MeaslesBottomLeftData);
			data.put("MeaslesBottomRightData", MeaslesBottomRightData);
			data.put("district", district);
			data.put("year", year);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			indicator=null;
			district=null;
			year=null;
		}
	}
	@RequestMapping(value="/BloodBankStockPredictiveZone", method=RequestMethod.GET)
	public ModelAndView BloodBankStockPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		String date=null;
        String userName=null;
        String indicator=null;
        List<String> districtList=null;
        String district=null;
        String instType=null;
        List<String> indCategorylist=null;
        String indCategory=null;
        String indicatorName=null;
		try{
			indicatorName=request.getParameter("indicator");
			indicator="PA_Bloodbank_Both";
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("bloodBankStockPredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(1);
			modelView.addObject("districtList", districtList);
			indCategorylist=predictiveZoneManager.getIndCategoryList();
			indCategory=indCategorylist.get(0);
			modelView.addObject("indicatorCategoryList", indCategorylist);
			date="27-06-2016";
			instType="Govt";
			JSONArray BBSTopLeftData=predictiveZoneManager.getBBSTopLeftData(indicator,date,instType,indCategory,district,userName);
			JSONArray BBSTopRightData=predictiveZoneManager.getBBSTopRightData(indicator,date,instType,indCategory,district,userName);
			JSONArray BBSBottomLeftData=predictiveZoneManager.getBBSBottomLeftData(indicator,date,instType,indCategory,district,userName);
			JSONArray BBSBottomRightData=predictiveZoneManager.getBBSBottomRightData(indicator,date,instType,indCategory,district,userName);
			modelView.addObject("BBSTopLeftData", BBSTopLeftData);
			modelView.addObject("BBSTopRightData", BBSTopRightData);
			modelView.addObject("BBSBottomLeftData", BBSBottomLeftData);
			modelView.addObject("BBSBottomRightData", BBSBottomRightData);
			modelView.addObject("date", date);
			modelView.addObject("indCategory", indCategory);
			modelView.addObject("district", district);
			modelView.addObject("instType", instType);
			
		}catch(Exception exp){
			
		}finally{
			date=null;
			userName=null;
			districtList=null;
			indicator=null;
			district=null;
			indCategorylist=null;
			indCategory=null;
			instType=null;
		}
		return modelView;
	}
	@RequestMapping(value="/BBSPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void BBSPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
		String userName=null;
        String date=null;
        String instType=null;
        String district=null;
        String indCategory=null;
        JSONObject data=null;
		PrintWriter out=null;
		String indicator=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="PA_Bloodbank_Both";
			date=request.getParameter("date");
			district=request.getParameter("district");
			indCategory=request.getParameter("indicatorCategory");
			instType=request.getParameter("institutionType");
			JSONArray BBSTopLeftData=predictiveZoneManager.getBBSTopLeftData(indicator,date,instType,indCategory,district,userName);
			JSONArray BBSTopRightData=predictiveZoneManager.getBBSTopRightData(indicator,date,instType,indCategory,district,userName);
			JSONArray BBSBottomLeftData=predictiveZoneManager.getBBSBottomLeftData(indicator,date,instType,indCategory,district,userName);
			JSONArray BBSBottomRightData=predictiveZoneManager.getBBSBottomRightData(indicator,date,instType,indCategory,district,userName);
			data.put("BBSTopLeftData", BBSTopLeftData);
			data.put("BBSTopRightData", BBSTopRightData);
			data.put("BBSBottomLeftData", BBSBottomLeftData);
			data.put("BBSBottomRightData", BBSBottomRightData);
			data.put("date", date);
			data.put("district", district);
			data.put("indCategory", indCategory);
			data.put("instType", instType);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			date=null;
			userName=null;
			indicator=null;
			district=null;
			indCategory=null;
			instType=null;
		}
	}
	@RequestMapping(value="/BloodBankStockInstitutionWisePredictiveZone", method=RequestMethod.GET)
	public ModelAndView BloodBankStockInstitutionWisePredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
        String userName=null;
        String indicator=null;
        String date=null;
        String instType=null;
        List<String> districtList=null;
        String district=null;
        String indicatorName=null;
		try{
			indicatorName=request.getParameter("indicator");
			indicator="PA_Bloodbank_Stock_Inst";
			userName=UserInfo.getLoggedInUserName();
			date="27-06-2016";
			instType="Pvt";
			modelView = new ModelAndView();
			modelView.setViewName("bloodBankStockInstitutionWisePredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(1);
			modelView.addObject("districtList", districtList);
			JSONArray BBSITopLeftData=predictiveZoneManager.getBBSIHeatMapTopLeftData(indicator,date,instType,district,userName);
			JSONArray BBSITopRightData=predictiveZoneManager.getBBSIHeatMapTopRightData(indicator,date,instType,district,userName);
			JSONArray BBSIBottomData=predictiveZoneManager.getBBSIHeatMapBottomLeftData(indicator,date,instType,district,userName);
			modelView.addObject("BBSITopLeftData", BBSITopLeftData);
			modelView.addObject("BBSITopRightData", BBSITopRightData);
			modelView.addObject("BBSIBottomData", BBSIBottomData);	
			modelView.addObject("date", date);
			modelView.addObject("district", district);
			modelView.addObject("instType", instType);
		}catch(Exception exp){
			
		}finally{
			date=null;
			userName=null;
			instType=null;
			districtList=null;
	        district=null;
		}
		return modelView;
	}
	@RequestMapping(value="/BBSIPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void BBSIPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
		String userName=null;
        String date=null;
        String instType=null;
        String district=null;
        JSONObject data=null;
		PrintWriter out=null;
		String indicator=null;
		try{
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="PA_Bloodbank_Stock_Inst";
			date=request.getParameter("date");
			district=request.getParameter("district");
			instType=request.getParameter("institutionType");
			JSONArray BBSITopLeftData=predictiveZoneManager.getBBSIHeatMapTopLeftData(indicator,date,instType,district,userName);
			JSONArray BBSITopRightData=predictiveZoneManager.getBBSIHeatMapTopRightData(indicator,date,instType,district,userName);
			JSONArray BBSIBottomData=predictiveZoneManager.getBBSIHeatMapBottomLeftData(indicator,date,instType,district,userName);
			data.put("BBSITopLeftData", BBSITopLeftData);
			data.put("BBSITopRightData", BBSITopRightData);
			data.put("BBSIBottomData", BBSIBottomData);
			data.put("date", date);
			data.put("district", district);
			data.put("instType", instType);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			instType=null;
			date=null;
			indicator=null;
		}
	}
	@RequestMapping(value="/HOBParaPredictiveZone", method=RequestMethod.GET)
	public ModelAndView HOBParaPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
        String indicator=null;
        List<String> districtList=null;
        String district=null;
        String instType=null;
        String indicatorName=null;
        String month=null;
		try{
			indicatorName=request.getParameter("indicator");
			indicator="Parent_more than 3 child";
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("hobParaPredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2014);
			maxYearMonth.setName("Apr");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			modelView.addObject("yearList", Util.yearList);
			modelView.addObject("monthsList", Util.monthsList);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			modelView.addObject("districtList", districtList);
			instType="Pvt";
			month="April";
			JSONArray HOBTopLeftData=predictiveZoneManager.getHOBHeatMapTopLeftData(indicator,maxMonth,maxYear,district,instType,userName);
			JSONArray HOBTopRightData=predictiveZoneManager.getHOBHeatMapTopRightData(indicator,maxMonth,maxYear,district,instType,userName);
			JSONArray HOBBottomData=predictiveZoneManager.getHOBHeatMapBottomData(indicator,maxMonth,maxYear,district,instType,userName);
			modelView.addObject("HOBTopLeftData", HOBTopLeftData);
			modelView.addObject("HOBTopRightData", HOBTopRightData);
			modelView.addObject("HOBBottomData", HOBBottomData);
			modelView.addObject("district", district);
			modelView.addObject("instType", instType);
			modelView.addObject("year", maxYear);
			modelView.addObject("month", month);
			
		}catch(Exception exp){
			
		}finally{
			sdf=null;
			maxYearMonth=null;
			maxMonth=null;
			userName=null;
			districtList=null;
			district=null;
			instType=null;
		}
		return modelView;
	}
	@RequestMapping(value="/HOBPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void HOBPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
        String userName=null;
		String indicator=null;
		String district=null;
		JSONObject data=null;
		PrintWriter out=null;
		String month=null;
		String instType=null;
		int year=0;		
		try{			
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="Parent_more than 3 child";
			month=request.getParameter("month");
			month=month.substring(0,3);
			year=Integer.parseInt(request.getParameter("year"));
			district=request.getParameter("district");
			instType=request.getParameter("institutionType");
			JSONArray HOBTopLeftData=predictiveZoneManager.getHOBHeatMapTopLeftData(indicator,month,year,district,instType,userName);
			JSONArray HOBTopRightData=predictiveZoneManager.getHOBHeatMapTopRightData(indicator,month,year,district,instType,userName);
			JSONArray HOBBottomData=predictiveZoneManager.getHOBHeatMapBottomData(indicator,month,year,district,instType,userName);
			data.put("HOBTopLeftData", HOBTopLeftData);
			data.put("HOBTopRightData", HOBTopRightData);
			data.put("HOBBottomData", HOBBottomData);
			data.put("instType", instType);
			data.put("district", district);
			data.put("year", year);
			data.put("month", request.getParameter("month"));
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			indicator=null;
			month=null;
			instType=null;
		}
	}
	@RequestMapping(value="/CNPParaPredictiveZone", method=RequestMethod.GET)
	public ModelAndView CNPParaPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
		SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String userName=null;
        String indicator=null;
        List<String> districtList=null;
        String district=null;
        String instType=null;
        String indicatorName=null;
        String month=null;
		try{
			indicatorName=request.getParameter("indicator");
			indicator="CMCHIS_Need More Info Dashboard";
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("cmchisNMIPredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2015);
			maxYearMonth.setName("Nov");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			modelView.addObject("yearList", Util.yearList);
			modelView.addObject("monthsList", Util.monthsList);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(1);
			modelView.addObject("districtList", districtList);
			instType="Govt";
			month="November";
			JSONArray CNPTopLeftData=predictiveZoneManager.getCNPTopLeftData(indicator,maxYear,maxMonth,district,instType,userName);
			JSONArray CNPTopRightData=predictiveZoneManager.getCNPTopRightData(indicator,maxYear,maxMonth,district,instType,userName);
			JSONArray CNPBottomLeftData=predictiveZoneManager.getCNPBottomLeftData(indicator,maxYear,maxMonth,district,instType,userName);
			JSONArray CNPBottomRightData=predictiveZoneManager.getCNPBottomRightData(indicator,maxYear,maxMonth,district,instType,userName);
			modelView.addObject("CNPTopLeftData", CNPTopLeftData);
			modelView.addObject("CNPTopRightData", CNPTopRightData);
			modelView.addObject("CNPBottomLeftData", CNPBottomLeftData);
			modelView.addObject("CNPBottomRightData", CNPBottomRightData);
			modelView.addObject("district", district);
			modelView.addObject("year", maxYear);
			modelView.addObject("month", month);
			modelView.addObject("instType", instType);
		}catch(Exception exp){
			
		}finally{
			sdf=null;
			maxYearMonth=null;
			maxMonth=null;
			userName=null;
			districtList=null;
			district=null;
			instType=null;
		}
		return modelView;
	}
	@RequestMapping(value="/CNPPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void CNPPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
        String userName=null;
		String indicator=null;
		String district=null;
		JSONObject data=null;
		PrintWriter out=null;
		String month=null;
		String instType=null;
		int year=0;		
		try{			
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="CMCHIS_Need More Info Dashboard";
			month=request.getParameter("month");
			month=month.substring(0,3);
			year=Integer.parseInt(request.getParameter("year"));
			district=request.getParameter("district");
			instType=request.getParameter("institutionType");
			JSONArray CNPTopLeftData=predictiveZoneManager.getCNPTopLeftData(indicator,year,month,district,instType,userName);
			JSONArray CNPTopRightData=predictiveZoneManager.getCNPTopRightData(indicator,year,month,district,instType,userName);
			JSONArray CNPBottomLeftData=predictiveZoneManager.getCNPBottomLeftData(indicator,year,month,district,instType,userName);
			JSONArray CNPBottomRightData=predictiveZoneManager.getCNPBottomRightData(indicator,year,month,district,instType,userName);
			data.put("CNPTopLeftData", CNPTopLeftData);
			data.put("CNPTopRightData", CNPTopRightData);
			data.put("CNPBottomLeftData", CNPBottomLeftData);
			data.put("CNPBottomRightData", CNPBottomRightData);
			data.put("year", year);
			data.put("month", request.getParameter("month"));
			data.put("district", district);
			data.put("instType", instType);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			indicator=null;
			month=null;
			instType=null;
		}
	}
	@RequestMapping(value="/hsDashboardPredictiveZone", method=RequestMethod.GET)
	public ModelAndView HSDashboardPredictiveZone(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
        String userName=null;
        String month=null;
        String year=null;
        Integer year1=0;
        //String indicator=null;
        List<String> districtList=null;
        String district=null;
        String indicatorName=null;
        List<String> indName=null;
		try{
			indicatorName=request.getParameter("indicator");
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("hsDashboardPredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			districtList=predictiveZoneManager.getDistrictList();
			modelView.addObject("districtList", districtList);
			Calendar cal =  Calendar.getInstance();
			cal.add(Calendar.MONTH ,1);
			month  = new SimpleDateFormat("MMMMM").format(cal.getTime());
			year1=Calendar.getInstance().get(Calendar.YEAR);
			year=year1.toString();
			district="All Districts";
			indName=new ArrayList<String>();
			if(indicatorName.equalsIgnoreCase("HS Deliveries")){
				indName.add("Deliveries Conducted");
				indName.add("Percentage of High Risk Pregnancies among ANCs registered");
				indName.add("Percentage of Assisted Deliveries");
				indName.add("Percentage of Cesarian Deliveries");
			}
			if(indicatorName.equalsIgnoreCase("HS Disease")){
				indName.add("Chickunguniya Cases");
				indName.add("Dengue Cases");
				indName.add("Fever Cases");
				indName.add("Swine Flu Cases");
			}
			if(indicatorName.equalsIgnoreCase("HS Hospital Deaths")){
				indName.add("Hospital Death Rate");
				indName.add("Inpatients Treated");
				indName.add("Outpatients Treated");
				indName.add("Excess of Average Length of Stay");
			}
			if(indicatorName.equalsIgnoreCase("HS Beds and Equipment Related")){
				indName.add("Dysfunctional Equipment Indicator");
				indName.add("Dysfunctional Operation Theatre Indicator");
				indName.add("Operation Theatre Utilization / Underutilized Operation Theatres");
				indName.add("Percentage of Beds Vacant / Underutilized Bed Occupancy");
			}
			if(indicatorName.equalsIgnoreCase("HS Drug and Lab Test")){
				indName.add("Non Availability of Drugs");
				indName.add("Number of Lab Tests done per 1000 patients");
				indName.add("Short Supply of Drugs");
				indName.add("Surgical Load of Surgeons");
			}
			if(indicatorName.equalsIgnoreCase("HS Deaths")){
				indName.add("Total Number of Infant Deaths");
				indName.add("Total Number of Maternal Deaths");
				indName.add("Total Number of Deaths reported due to TB");
				indName.add("Percentage of Neonatal Deaths among Live Births");
			}
			if(indicatorName.equalsIgnoreCase("HS Referral and MMR")){
				indName.add("Total Number of Mothers referred out");
				indName.add("Total Number of Neonatal Cases Referred out");
				indName.add("Percentage of Referral among inpatients treated");
				indName.add("Maternal Mortality Rate");
			}
			if(indicatorName.equalsIgnoreCase("HS Others")){
				indName.add("Staff Deficiency");
				indName.add("Total Number of Organ Transplants done");
				indName.add("Percentage of Deaths among Accident and Emergency Cases");
				indName.add("Total Number of Vehicles Off-road");
			}
			JSONArray TopLeftData=predictiveZoneManager.getTopLeftData(year,district,indName.get(0));
			JSONArray TopRightData=predictiveZoneManager.getTopRightData(year,district,indName.get(1));
			JSONArray BottomLeftData=predictiveZoneManager.getBottomLeftData(year,district,indName.get(2));
			JSONArray BottomRightData=predictiveZoneManager.getBottomRightData(year,district,indName.get(3));
			modelView.addObject("TopLeftData", TopLeftData);
			modelView.addObject("TopRightData", TopRightData);
			modelView.addObject("BottomLeftData", BottomLeftData);
			modelView.addObject("BottomRightData", BottomRightData);
			modelView.addObject("TopLeftDataSubCaption", (TopLeftData!=null&&TopLeftData.length()>0)?TopLeftData.getJSONObject(0).get("subCaption"):null);
			modelView.addObject("TopRightDataSubCaption", (TopRightData!=null&&TopRightData.length()>0)?TopRightData.getJSONObject(0).get("subCaption"):null);
			modelView.addObject("BottomLeftDataSubCaption", (BottomLeftData!=null&&BottomLeftData.length()>0)?BottomLeftData.getJSONObject(0).get("subCaption"):null);
			modelView.addObject("BottomRightDataSubCaption", (BottomRightData!=null&&BottomRightData.length()>0)?BottomRightData.getJSONObject(0).get("subCaption"):null);
			modelView.addObject("district", district);
			modelView.addObject("year", year);
			modelView.addObject("month", month);
			modelView.addObject("indNames", indName.size());
			modelView.addObject("topLeftIndName", indName.get(0));
			modelView.addObject("topRightIndName", indName.get(1));
			modelView.addObject("bottomLeftIndName", indName.get(2));
			modelView.addObject("bottomRightIndName", indName.get(3));
		}catch(Exception exp){
			
		}finally{
			year=null;
			month=null;
			userName=null;
			districtList=null;
			district=null;
		}
		return modelView;
	}
	@RequestMapping(value="/hsDashboardPredictiveOnSearch", method=RequestMethod.POST)
	public @ResponseBody void hsDashboardPredictiveOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
        String userName=null;
		String district=null;
		JSONObject data=null;
		PrintWriter out=null;
		String month=null;
		String topLeftIndName=null;
		String topRightIndName=null;
		String bottomLeftIndName=null;
		String bottomRightIndName=null;
		int indlength=0;
		String year=null;		
		try{			
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			month=request.getParameter("month");
			year=request.getParameter("year");
			district=request.getParameter("district");
			indlength=Integer.parseInt(request.getParameter("indlength"));
			data.put("indNames",indlength);
			//if(indlength == 4){
				topLeftIndName=request.getParameter("topLeftIndName");
				topRightIndName=request.getParameter("topRightIndName");
				bottomLeftIndName=request.getParameter("bottomLeftIndName");
				bottomRightIndName=request.getParameter("bottomRightIndName");
				JSONArray TopLeftData=predictiveZoneManager.getTopLeftData(year,district,topLeftIndName);
				JSONArray TopRightData=predictiveZoneManager.getTopRightData(year,district,topRightIndName);
				JSONArray BottomLeftData=predictiveZoneManager.getBottomLeftData(year,district,bottomLeftIndName);
				JSONArray BottomRightData=predictiveZoneManager.getBottomRightData(year,district,bottomRightIndName);
				data.put("TopLeftData", TopLeftData);
				data.put("TopRightData", TopRightData);
				data.put("BottomLeftData", BottomLeftData);
				data.put("BottomRightData", BottomRightData);
				data.put("TopLeftDataSubCaption", (TopLeftData!=null&&TopLeftData.length()>0)?TopLeftData.getJSONObject(0).get("subCaption"):null);
				data.put("TopRightDataSubCaption", (TopRightData!=null&&TopRightData.length()>0)?TopRightData.getJSONObject(0).get("subCaption"):null);
				data.put("BottomLeftDataSubCaption", (BottomLeftData!=null&&BottomLeftData.length()>0)?BottomLeftData.getJSONObject(0).get("subCaption"):null);
				data.put("BottomRightDataSubCaption", (BottomRightData!=null&&BottomRightData.length()>0)?BottomRightData.getJSONObject(0).get("subCaption"):null);
			//}
			/*if(indlength == 3){
				topLeftIndName=request.getParameter("topLeftIndName");
				topRightIndName=request.getParameter("topRightIndName");
				bottomLeftIndName=request.getParameter("bottomLeftIndName");
				JSONArray TopLeftData=predictiveZoneManager.getTopLeftData(year,district,topLeftIndName);
				JSONArray TopRightData=predictiveZoneManager.getTopRightData(year,district,topRightIndName);
				JSONArray BottomLeftData=predictiveZoneManager.getBottomLeftData(year,district,bottomLeftIndName);
				data.put("TopLeftData", TopLeftData);
				data.put("TopRightData", TopRightData);
				data.put("BottomLeftData", BottomLeftData);
				data.put("TopLeftDataSubCaption", (TopLeftData!=null&&TopLeftData.length()>0)?TopLeftData.getJSONObject(0).get("subCaption"):null);
				data.put("TopRightDataSubCaption", (TopRightData!=null&&TopRightData.length()>0)?TopRightData.getJSONObject(0).get("subCaption"):null);
				data.put("BottomLeftDataSubCaption", (BottomLeftData!=null&&BottomLeftData.length()>0)?BottomLeftData.getJSONObject(0).get("subCaption"):null);
			}
			if(indlength == 2){
				topLeftIndName=request.getParameter("topLeftIndName");
				topRightIndName=request.getParameter("topRightIndName");
				JSONArray TopLeftData=predictiveZoneManager.getTopLeftData(year,district,topLeftIndName);
				JSONArray TopRightData=predictiveZoneManager.getTopRightData(year,district,topRightIndName);
				data.put("TopLeftData", TopLeftData);
				data.put("TopRightData", TopRightData);
				data.put("TopLeftDataSubCaption", (TopLeftData!=null&&TopLeftData.length()>0)?TopLeftData.getJSONObject(0).get("subCaption"):null);
				data.put("TopRightDataSubCaption", (TopRightData!=null&&TopRightData.length()>0)?TopRightData.getJSONObject(0).get("subCaption"):null);
			}*/
				data.put("month", month);
				data.put("year", year);
				data.put("district", district);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			month=null;
			topLeftIndName=null;
			topRightIndName=null;
			bottomLeftIndName=null;
			bottomRightIndName=null;
			year=null;		
		}
	}
	@RequestMapping(value="/MMRInsides", method=RequestMethod.GET)
	public ModelAndView MMRInsides(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
        String userName=null;
        String indicator=null;
        List<String> districtList=null;
        String district=null;
        String indicatorName=null;
        SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String month=null;
        List<Integer> yearList=null;
		try{
			indicatorName=request.getParameter("indicator");
			indicator="MMR 2015 Insides";
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("mmrInsidesPredictiveZone");
			modelView.addObject("indicatorName", indicatorName);
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2015);
			maxYearMonth.setName("Nov");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			yearList=new ArrayList<Integer>();
			yearList.add(2015);
			modelView.addObject("yearList", yearList);
			modelView.addObject("monthsList", Util.monthsList);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			modelView.addObject("districtList", districtList);
			month="November";
			JSONArray MMRITopLeftData=predictiveZoneManager.getMMRITopLeftData(indicator,maxYear,maxMonth,userName);
			JSONArray MMRITopRightData=predictiveZoneManager.getMMRITopRightData(indicator,maxYear,userName);
			JSONArray MMRIBottomLeftData=predictiveZoneManager.getMMRIBottomLeftData(indicator,district,userName);
			modelView.addObject("MMRITopLeftData", MMRITopLeftData);
			modelView.addObject("MMRITopRightData", MMRITopRightData);
			modelView.addObject("MMRIBottomLeftData", MMRIBottomLeftData);
			modelView.addObject("district", district);
			modelView.addObject("year", maxYear);
			modelView.addObject("month", month);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			districtList=null;
			district=null;
		}
		return modelView;
	}
	@RequestMapping(value="/MMRInsidesOnSearch", method=RequestMethod.POST)
	public @ResponseBody void MMRInsidesOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
        String userName=null;
		String indicator=null;
		String district=null;
		JSONObject data=null;
		PrintWriter out=null;
		int year=0;
		String month=null;
		try{			
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="MMR 2015 Insides";
			district=request.getParameter("district");
			month=request.getParameter("month").substring(0,3);
			year=Integer.parseInt(request.getParameter("year"));			
			JSONArray MMRITopLeftData=predictiveZoneManager.getMMRITopLeftData(indicator,year,month,userName);
			JSONArray MMRITopRightData=predictiveZoneManager.getMMRITopRightData(indicator,year,userName);
			JSONArray MMRIBottomLeftData=predictiveZoneManager.getMMRIBottomLeftData(indicator,district,userName);
			data.put("MMRITopLeftData", MMRITopLeftData);
			data.put("MMRITopRightData", MMRITopRightData);
			data.put("MMRIBottomLeftData", MMRIBottomLeftData);
			data.put("year", year);
			data.put("district", district);
			data.put("month", request.getParameter("month"));
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			indicator=null;
			month=null;
		}
	}
	@RequestMapping(value="/MMROperationalAnalytics", method=RequestMethod.GET)
	public ModelAndView MMROperationalAnalytics(HttpServletRequest request)
	{	
		ModelAndView modelView=null;
        String userName=null;
        String indicator=null;
        List<String> districtList=null;
        String district=null;
        String indicatorName=null;
        SimpleDateFormat sdf=null;
		CommonStringList maxYearMonth=null;
		String maxMonth=null;
		int maxYear=0;
        String month=null;
        List<Integer> yearList=null;
        String reasonableIndicator=null;
        List<String> reasonableIndicatorList=null;
		try{
			indicatorName=request.getParameter("indicator");
			indicator="MMR Operational Analytic";
			userName=UserInfo.getLoggedInUserName();
			modelView = new ModelAndView();
			modelView.setViewName("mmrOperationalAnalytics");
			modelView.addObject("indicatorName", indicatorName);
			sdf=new SimpleDateFormat("MMMM");
			maxYearMonth=new CommonStringList();
			maxYearMonth.setId(2015);
			maxYearMonth.setName("Nov");
			maxMonth=maxYearMonth.getName();
			maxYear=maxYearMonth.getId();
			yearList=new ArrayList<Integer>();
			yearList.add(2015);
			modelView.addObject("yearList", yearList);
			modelView.addObject("monthsList", Util.monthsList);
			districtList=predictiveZoneManager.getDistrictList();
			district=districtList.get(0);
			month="November";
			modelView.addObject("districtList", districtList);
			reasonableIndicatorList=predictiveZoneManager.getReasonableIndList();
			modelView.addObject("reasonableIndicatorList", reasonableIndicatorList);
			JSONArray MMROTopLeftData=predictiveZoneManager.getMMROTopLeftData(indicator,maxYear,maxMonth,userName);
			JSONArray MMROTopRightData=predictiveZoneManager.getMMROTopRightData(indicator,maxYear,maxMonth,district,userName);
			JSONArray MMROBottomLeftData=predictiveZoneManager.getMMROBottomLeftData(indicator,reasonableIndicatorList.get(0),maxYear,maxMonth,userName);
			JSONArray MMROBottomRightData=predictiveZoneManager.getMMROBottomRightData(indicator,userName);
			modelView.addObject("MMROTopLeftData", MMROTopLeftData);
			modelView.addObject("MMROTopRightData", MMROTopRightData);
			modelView.addObject("MMROBottomLeftData", MMROBottomLeftData);
			modelView.addObject("MMROBottomRightData", MMROBottomRightData);
			modelView.addObject("district", district);
			modelView.addObject("month", month);
			modelView.addObject("year", maxYear);
			modelView.addObject("reasonableIndicator", reasonableIndicatorList.get(0));
		}catch(Exception exp){
			
		}finally{
			userName=null;
			districtList=null;
			district=null;
		}
		return modelView;
	}
	@RequestMapping(value="/MMROperationalAnalyticsOnSearch", method=RequestMethod.POST)
	public @ResponseBody void MMROperationalAnalyticsOnSearch(HttpServletRequest request,HttpServletResponse response)
	{	
        String userName=null;
		String indicator=null;
		String district=null;
		JSONObject data=null;
		PrintWriter out=null;
		int year=0;
		String month=null;
		String reasonableIndicator=null;
		try{			
			data=new JSONObject();
	    	out=response.getWriter();
			userName=UserInfo.getLoggedInUserName();
			indicator="MMR Operational Analytic";
			district=request.getParameter("district");
			year=Integer.parseInt(request.getParameter("year"));
			month=request.getParameter("month").substring(0,3);
			reasonableIndicator=request.getParameter("reasonableInd");
			JSONArray MMROTopLeftData=predictiveZoneManager.getMMROTopLeftData(indicator,year,month,userName);
			JSONArray MMROTopRightData=predictiveZoneManager.getMMROTopRightData(indicator,year,month,district,userName);
			JSONArray MMROBottomLeftData=predictiveZoneManager.getMMROBottomLeftData(indicator,reasonableIndicator,year,month,userName);
			JSONArray MMROBottomRightData=predictiveZoneManager.getMMROBottomRightData(indicator,userName);
			data.put("MMROTopLeftData", MMROTopLeftData);
			data.put("MMROTopRightData", MMROTopRightData);
			data.put("MMROBottomLeftData", MMROBottomLeftData);
			data.put("MMROBottomRightData", MMROBottomRightData);
			data.put("district", district);
			data.put("month", request.getParameter("month"));
			data.put("year", year);
			data.put("reasonableIndicator", reasonableIndicator);
			out.println(data);
		}catch(Exception exp){
			
		}finally{
			userName=null;
			district=null;
			indicator=null;
			month=null;
		}
	}
}
