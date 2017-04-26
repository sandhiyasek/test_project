package gov.shdrc.servicemaintenance.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.servicemaintenance.service.IGeneralMasterManager;
import gov.shdrc.servicemaintenance.service.IIndicatorMasterCalculationManager;
import gov.shdrc.servicemaintenance.service.IIndicatorMasterDashboardManager;
import gov.shdrc.servicemaintenance.service.IIndicatorMasterManager;
import gov.shdrc.servicemaintenance.util.GeneralMaster;
import gov.shdrc.servicemaintenance.util.IndicatorMaster;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
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
public class IndicatorMasterController {
	@Autowired(required=true)
	IIndicatorMasterManager indicatorMasterManager;
	@Autowired(required=true)
	IIndicatorMasterCalculationManager iIndicatorMasterCalculationManager;
	@Autowired(required=true)
	IIndicatorMasterDashboardManager iIndicatorMasterDashboardManager;
	@Autowired(required=true)
	ICommonManager commonManager;
	@Autowired(required=true)
	IGeneralMasterManager iGeneralMasterManager;
	
	@RequestMapping(value="/IndicatorMasterMaintenance", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		 String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorMasterMaintenance");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			 
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();		
		        
		    Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		        
	        List<CommonStringList> directorateList= commonManager.getDirectorateListById(directorateId);
	        modelView.addObject("directorateList", directorateList);
			
			modelView.addObject("directorateId", directorateId);
			List<IndicatorMaster> indicatorDetailsList=indicatorMasterManager.getindicatorDetailsList(directorateId);
			modelView.addObject("indicatorDetailsList", indicatorDetailsList);
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/IndicatorMasterAdd", method=RequestMethod.GET)
	public ModelAndView onLoadIndicatorMasterAdd(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		 String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorMasterAdd");		
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
	        
	        Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
	        
	        List<CommonStringList> directorateList= commonManager.getDirectorateListById(directorateId);
	        modelView.addObject("directorateList", directorateList);
			modelView.addObject("directorateId", directorateId);			
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/IndicatorMasterChange", method=RequestMethod.GET)
	public ModelAndView onLoadIndicatorMasterChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorMasterChange");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			 
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();		
		        
		    Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
		        
	        List<CommonStringList> directorateList= commonManager.getDirectorateListById(directorateId);
	        modelView.addObject("directorateList", directorateList);
	        
	        modelView.addObject("directorateId", directorateId);	
	        
			Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			String prevSubmitType=request.getParameter("prevSubmitType");
			IndicatorMaster indicatorDetails =indicatorMasterManager.getIndicatorDetails(directorateId,indicatorId);
			
			List<CommonStringList> categoryList= indicatorMasterManager.getCategoryList(directorateId);
			List<CommonStringList> indHierarchyList= indicatorMasterManager.getIndHierarchyList(directorateId);
			
			 modelView.addObject("categoryList", categoryList);
			 modelView.addObject("indHierarchyList", indHierarchyList);
			 modelView.addObject("indicatorId", indicatorId);
			 modelView.addObject("indicatorDetails", indicatorDetails);
			 modelView.addObject("prevSubmitType", prevSubmitType);
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/IndicatorMasterAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void addIndicatorMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		String userName = UserInfo.getLoggedInUserName();
		try{
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateName"))?Integer.parseInt(request.getParameter("directorateName")):null);
			String classification=request.getParameter("classification");
			String category=request.getParameter("category");
			String otherCategory=request.getParameter("otherCategory");
			String indicatorName=request.getParameter("indicatorName");
			String subCategory=request.getParameter("subCategory");
			String subSubCategory=request.getParameter("subSubCategory");
			/*String level=request.getParameter("level");*/
			/*String dataEntry=request.getParameter("dataEntry");*/
			String modeSource=request.getParameter("modeSource");
			String otherfileSystem=request.getParameter("otherfileSystem");
			String factMap=request.getParameter("factMap");
			String frequency=request.getParameter("frequency");
			String calculation=request.getParameter("calculation");
			String hierarchy=request.getParameter("hierarchy");
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
		    validateFields(directorateId,classification,category,otherCategory,indicatorName,subCategory,subSubCategory,frequency,hierarchy);
		    Integer indicatorId=indicatorMasterManager.insertIndicator(directorateId,classification,category,
					otherCategory,indicatorName,subCategory,subSubCategory,modeSource,otherfileSystem,factMap,frequency,calculation,hierarchy,userName); 
		    	if(indicatorId!=null){
			    	request.setAttribute("indicatorId", indicatorId);		      
		        	if(factMap.equalsIgnoreCase("D")){
			        	response.sendRedirect("IndicatorMasterDashBoard.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&calculation="+calculation+"&prevServlet=IndicatorMasterChange.do"+"&submitType=add");
		        	}else if(calculation.equalsIgnoreCase("C")){
		        		response.sendRedirect("IndicatorMasterCalculation.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterChange.do"+"&submitType=add");
		        	}else{
		        		response.sendRedirect("GeneralMasterAdd.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterAdd.do"+"&submitType=add");
		        	}
		    	}else{
		    		out.println("Error in adding a new Indicator. Please Contact System Administrator");
		    	}
			
	}finally{}
	}
	
	@RequestMapping(value="/IndicatorMasterChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void changeIndicatorMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateName"))?Integer.parseInt(request.getParameter("directorateName")):null);
			String classification=request.getParameter("classification");
			String category=request.getParameter("category");
			String otherCategory=request.getParameter("otherCategory");
			String indicatorName=request.getParameter("indicatorName");
			String subCategory=request.getParameter("subCategory");
			String subSubCategory=request.getParameter("subSubCategory");
			/*String level=request.getParameter("level");*/
			/*String dataEntry=request.getParameter("dataEntry");*/
			String modeSource=request.getParameter("modeSource");
			String otherfileSystem=request.getParameter("otherfileSystem");
			String factMap=request.getParameter("factMap");
			String frequency=request.getParameter("frequency");
			String calculation=request.getParameter("calculation");
			String hierarchy=request.getParameter("hierarchy");
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
		    validateFields(directorateId,classification,category,otherCategory,indicatorName,subCategory,subSubCategory,frequency,hierarchy);
		    Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
	    	String prevSubmitType=request.getParameter("prevSubmitType");
	    	if(prevSubmitType.equals("null"))
	    		prevSubmitType="change";
	    	
	    	successFlag=indicatorMasterManager.updateIndicator(directorateId,indicatorId,classification,category,
				otherCategory,indicatorName,subCategory,subSubCategory,modeSource,otherfileSystem,factMap,frequency,calculation,hierarchy);      
	    	if(successFlag){
	    		if(factMap.equalsIgnoreCase("D")){
	    			response.sendRedirect("IndicatorMasterDashboardChange.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&calculation="+calculation+"&prevServlet=IndicatorMasterChange.do"+"&submitType="+prevSubmitType);
	    		}else if(calculation.equalsIgnoreCase("C")){
	    			response.sendRedirect("IndicatorMasterCalcuChange.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterChange.do"+"&submitType="+prevSubmitType);
	    		}else{
	    			response.sendRedirect("GeneralMasterChange.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterChange.do"+"&submitType="+prevSubmitType);
	    		}
	    	}else
	    		out.println("Please Contact System Administrator");	
	}finally{}
	}
	
	@RequestMapping(value="/IndicatorMasterDirectChange", method=RequestMethod.POST)
	public @ResponseBody void IndicatorMasterDirectChange(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		String userName = UserInfo.getLoggedInUserName();
		PrintWriter out = response.getWriter();
		try{
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateName"))?Integer.parseInt(request.getParameter("directorateName")):null);
			
				List<CommonStringList> categoryList= indicatorMasterManager.getCategoryList(directorateId);
				List<CommonStringList> indHierarchyList= indicatorMasterManager.getIndHierarchyList(directorateId);
				
				JSONArray data=new JSONArray();
				JSONObject obj = new JSONObject();
				String jsonCategoryList=null;
				String jsonIndHierarchyList=null;
				jsonCategoryList=new Gson().toJson(categoryList);
				obj.put("jsonCategoryList", jsonCategoryList);
				jsonIndHierarchyList=new Gson().toJson(indHierarchyList);
				obj.put("jsonIndHierarchyList", jsonIndHierarchyList);
				data.put(obj);
				out.println(obj);
			
	}finally{}
	}
	
	/////////IndicatorMasterDashBoard
	
	@RequestMapping(value="/IndicatorMasterDashBoard", method=RequestMethod.GET)
	public ModelAndView onLoadIndicatorMasterDashBoard(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorMasterDashBoard");
				 csrfToken = CSRFTokenUtil.getToken (request.getSession());
				  modelView.addObject("CSRF_TOKEN", csrfToken);
			 JSONArray jsonArray = null;
			 
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String calculation = request.getParameter("calculation");
			 String prevServlet = request.getParameter("prevServlet");
			
			 jsonArray = iIndicatorMasterDashboardManager.getIndHierarchyList(directorateId);
			 String encodedJsonArray = Util.encodeJSONArray(jsonArray.toString());
		     modelView.addObject("encodedJsonArray", encodedJsonArray);
		        
	        String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));
	        
	        modelView.addObject("directorateName", directorateName);
	        modelView.addObject("indicatorName", indicatorName);
	        modelView.addObject("directorateId", directorateId);
	        modelView.addObject("indicatorId", indicatorId);
	        modelView.addObject("calculation", calculation);
	        modelView.addObject("prevServlet", prevServlet);
	        
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/IndicatorMasterDashboardChange", method=RequestMethod.GET)
	public ModelAndView onLoadIndicatorMasterDashBoardChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		 String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorMasterDashboardChange");
				 csrfToken = CSRFTokenUtil.getToken (request.getSession());
				 modelView.addObject("CSRF_TOKEN", csrfToken);
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String calculation = request.getParameter("calculation");
			 String prevServlet = request.getParameter("prevServlet");
			
			 JSONArray jsonArray = iIndicatorMasterDashboardManager.getIndHierarchyList(directorateId);
			 String encodedJsonArray = Util.encodeJSONArray(jsonArray.toString());
			 modelView.addObject("encodedJsonArray", encodedJsonArray);
		        
	        String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));
	        modelView.addObject("directorateName", directorateName);
	        modelView.addObject("indicatorName", indicatorName);
	        modelView.addObject("directorateId", directorateId);
	        modelView.addObject("indicatorId", indicatorId);
	        modelView.addObject("calculation", calculation);
	        modelView.addObject("prevServlet", prevServlet);
	        String prevSubmitType=request.getParameter("prevSubmitType");
    		
    		JSONArray thresholdJsonArray = null;
	   		 thresholdJsonArray = iIndicatorMasterDashboardManager.getThresholdDetails(directorateId,indicatorId);
	   		if(thresholdJsonArray!=null){
	   			String thresholdEncodedJsonArray = Util.encodeJSONArray(thresholdJsonArray.toString());
	   			modelView.addObject("thresholdEncodedJsonArray", thresholdEncodedJsonArray);
		   	}
	   	  modelView.addObject("prevSubmitType", prevSubmitType);
		}finally{}
		return modelView;
	}
	
	
	@RequestMapping(value="/IndicatorMasterDashBoardAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void IndicatorMasterDashBoardAddOnSubmit(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		String userName = UserInfo.getLoggedInUserName();
		boolean  isvalid=false; 
		PrintWriter out = response.getWriter();
		try{
			isvalid = CSRFTokenUtil.isValid (request);
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String thresholdTableData=request.getParameter("thresholdTableData");
			 String calculation=request.getParameter("calculation");
			 String indicatorName=request.getParameter("indicatorName");
			 String submitType = request.getParameter("submitType");
			 JSONArray gridRecords = new JSONArray(thresholdTableData);
			 boolean successFlag =iIndicatorMasterDashboardManager.insertThresholdRecords(directorateId,indicatorId,gridRecords,userName);
				if(successFlag){
					if(calculation.equalsIgnoreCase("C")){
						response.sendRedirect("IndicatorMasterCalculation.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterDashboardChange.do"+"&submitType=add");
					}
					else{
						response.sendRedirect("GeneralMasterAdd.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterDashboardChange.do"+"&submitType=add");
					}
				}
				else
					out.println("Save Error");
	}finally{}
	}
	
	@RequestMapping(value="/IndicatorMasterDashBoardChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void IndicatorMasterDashBoardChangeOnSubmit(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		String userName = UserInfo.getLoggedInUserName();
		boolean  isvalid=false; 
		PrintWriter out = response.getWriter();
		try{
			isvalid = CSRFTokenUtil.isValid (request);
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String thresholdTableData=request.getParameter("thresholdTableData");
			 String calculation=request.getParameter("calculation");
			 String indicatorName=request.getParameter("indicatorName");
			 String submitType = request.getParameter("submitType");
			 JSONArray gridRecords = new JSONArray(thresholdTableData);	
			 String prevSubmitType=request.getParameter("prevSubmitType");
				if(prevSubmitType.equals("null"))
		    		prevSubmitType="change";
				boolean successFlag =iIndicatorMasterDashboardManager.updateThresholdRecords(directorateId,indicatorId,gridRecords);
				if(successFlag){
					if(calculation.equalsIgnoreCase("C")){
						response.sendRedirect("IndicatorMasterCalcuChange.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterDashboardChange.do"+"&submitType="+prevSubmitType);
					}
					else{
						response.sendRedirect("GeneralMasterChange.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterDashboardChange.do"+"&submitType="+prevSubmitType);
					}
				}
				else
					out.println("Save Error");
	}finally{}
	}
	
//	///////////////////////IndicatorMasterCalc
	
	@RequestMapping(value="/IndicatorMasterCalculation", method=RequestMethod.GET)
	public ModelAndView onLoadIndicatorMasterCalculation(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorMasterCalculation");
				 csrfToken = CSRFTokenUtil.getToken (request.getSession());
				 modelView.addObject("CSRF_TOKEN", csrfToken);
			 JSONArray jsonArray = null;
			 
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String prevServlet = request.getParameter("prevServlet");
			
			 jsonArray = iIndicatorMasterCalculationManager.getIndicatorsList(directorateId);
			 String encodedJsonArray = Util.encodeJSONArray(jsonArray.toString());
		     modelView.addObject("encodedJsonArray", encodedJsonArray);
	        String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));      
	        
	        modelView.addObject("directorateName", directorateName);
	        modelView.addObject("indicatorName", indicatorName);
	        modelView.addObject("directorateId", directorateId);
	        modelView.addObject("indicatorId", indicatorId);
	        modelView.addObject("prevServlet", prevServlet);
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/IndicatorMasterCalcuChange", method=RequestMethod.GET)
	public ModelAndView onLoadIndicatorMasterCalcuChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorMasterCalcuChange");
			
			 csrfToken = CSRFTokenUtil.getToken (request.getSession());
			 modelView.addObject("CSRF_TOKEN", csrfToken);
			 JSONArray jsonArray = null;
			 
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String prevServlet = request.getParameter("prevServlet");
			 String submitType = request.getParameter("submitType");
			
			 jsonArray = iIndicatorMasterCalculationManager.getIndicatorsList(directorateId);
			 String encodedJsonArray = Util.encodeJSONArray(jsonArray.toString());
			 modelView.addObject("encodedJsonArray", encodedJsonArray);
		        
	        String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));
	        modelView.addObject("directorateName", directorateName);
	        modelView.addObject("indicatorName", indicatorName);
	        modelView.addObject("directorateId", directorateId);
	        modelView.addObject("indicatorId", indicatorId);
	        modelView.addObject("prevServlet", prevServlet);
	        String prevSubmitType=request.getParameter("prevSubmitType");
    		
    		JSONArray formulaJsonArray = null;
    		formulaJsonArray = iIndicatorMasterCalculationManager.getFormulaDetails(directorateId,indicatorId);
    		 if(formulaJsonArray!=null){
		   		 String formulaEncodedJsonArray = Util.encodeJSONArray(formulaJsonArray.toString());
		   		modelView.addObject("formulaEncodedJsonArray", formulaEncodedJsonArray);
		   	     }
    		 modelView.addObject("prevSubmitType", prevSubmitType);
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/IndicatorMasterCalculationAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void IndicatorMasterCalculationAddOnSubmit(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		String userName = UserInfo.getLoggedInUserName();
		boolean  isvalid=false; 
		PrintWriter out = response.getWriter();
		try{
			isvalid = CSRFTokenUtil.isValid (request);
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String calculationTableData=request.getParameter("calculationTableData");
			 JSONArray gridRecords = new JSONArray(calculationTableData);
			 boolean successFlag =iIndicatorMasterCalculationManager.insertFormulaRecords(directorateId,indicatorId,gridRecords,userName);
				
			 if(successFlag){
				 response.sendRedirect("GeneralMasterAdd.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterCalcuChange.do"+"&submitType=add");
			 }
			 else{
				 out.println("Save Error");
			 }
	}finally{}
	}
	
	@RequestMapping(value="/IndicatorMasterCalculationChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void IndicatorMasterCalculationChangeOnSubmit(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		String userName = UserInfo.getLoggedInUserName();
		boolean  isvalid=false; 
		PrintWriter out = response.getWriter();
		try{
			isvalid = CSRFTokenUtil.isValid (request);
			 Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			 String calculationTableData=request.getParameter("calculationTableData");
			 JSONArray gridRecords = new JSONArray(calculationTableData);
			 String prevSubmitType=request.getParameter("prevSubmitType");
				if(prevSubmitType.equals("null"))
		    		prevSubmitType="change";
				boolean successFlag =iIndicatorMasterCalculationManager.updateFormulaRecords(directorateId,indicatorId,gridRecords);
				if(successFlag){
					response.sendRedirect("GeneralMasterChange.do?directorateId="+directorateId+"&indicatorName="+indicatorName+"&indicatorId="+indicatorId+"&prevServlet=IndicatorMasterCalcuChange.do"+"&submitType="+prevSubmitType);
				}
				else{
					out.println("Save Error");
				}
		}finally{}
	}
	
	@RequestMapping(value="/indicatorSummary", method=RequestMethod.GET)
	public ModelAndView indicatorSummaryServlet(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
			modelView=new ModelAndView();
			modelView.setViewName("indicatorSummary");
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			 Integer indicatorId=(Util.isNotNull(request.getParameter("indicatorId"))?Integer.parseInt(request.getParameter("indicatorId")):null);
			 String indicatorName = request.getParameter("indicatorName");
			/* Integer directorateId=1;
			 Integer indicatorId=23648;
			 String indicatorName ="Total No. of Assisted Delivery";*/
			 String directorateName=(Util.getFullRole(Util.getDirectorateShortName(directorateId)));
			 
			 modelView.addObject("directorateName", directorateName);
			 modelView.addObject("indicatorName", indicatorName);
			 modelView.addObject("directorateId", directorateId);
			 modelView.addObject("indicatorId", indicatorId);
		        
		     IndicatorMaster indicatorDetails =indicatorMasterManager.getIndicatorDetails(directorateId,indicatorId);			
			 GeneralMaster generalDetails =iGeneralMasterManager.getGeneralDetails(directorateId,indicatorId);
			/* modelView.addObject("generalDetails", generalDetails);*/
			 
		     JSONArray thresholdJsonArray = null;
		   	 thresholdJsonArray = iIndicatorMasterDashboardManager.getThresholdDetails(directorateId,indicatorId);
		   	 if(thresholdJsonArray!=null){
	   			String thresholdEncodedJsonArray = Util.encodeJSONArray(thresholdJsonArray.toString());	   		 
	   			modelView.addObject("thresholdEncodedJsonArray", thresholdEncodedJsonArray);
		   	 }
		   	 JSONArray formulaJsonArray = null;
			 formulaJsonArray = iIndicatorMasterCalculationManager.getFormulaDetails(directorateId,indicatorId);
			 if(formulaJsonArray!=null){
		   		 String formulaEncodedJsonArray = Util.encodeJSONArray(formulaJsonArray.toString());		   	    
		   		modelView.addObject("formulaEncodedJsonArray",formulaEncodedJsonArray);
		   	 }
			 modelView.addObject("Classification", indicatorDetails.getIndicatorClassification());
			 modelView.addObject("Category", indicatorDetails.getIndicatorCategory());
			 modelView.addObject("SubCategory", indicatorDetails.getIndicatorSubCategory());
			 modelView.addObject("SubSubCategory", indicatorDetails.getIndicatorSubSubCategory());
			 modelView.addObject("ModeOfSource", indicatorDetails.getModeOfSource());
			 modelView.addObject("FactMap", Util.getIndicatorFactCalc(indicatorDetails.getIndicatorFactMap()));
			 modelView.addObject("Frequency", Util.getFrequencyFullName(indicatorDetails.getIndicatorFreq()));
			 modelView.addObject("Calculation",Util.getIndicatorFactCalc(indicatorDetails.getIndicatorCalc()));
			 modelView.addObject("Hierarchy",indicatorDetails.getIndicatorHierarchy());
			 modelView.addObject("GenCategory",generalDetails.getGeneralCategory());
			 modelView.addObject("GenType",generalDetails.getGeneralType());
			 modelView.addObject("GenName",generalDetails.getGeneralName());
			 modelView.addObject("GenSubCategory",generalDetails.getGeneralSubCategory());
			 modelView.addObject("GenSubSubCategory",generalDetails.getGeneralSubSubCategory());
			 modelView.addObject("GenClassification",generalDetails.getGeneralClassification());
		}finally{}
		return modelView;
	}
	
	private void validateFields(Integer directorateId,String classification,String category,String otherCategory,
			String indicatorName,String subCategory,String subSubCategory,String frequency,String hierarchy)throws SHDRCException{
		if(Util.isNullOrBlank(directorateId)){
			throw new SHDRCException("Please select the Directorate name");
		}
		if(Util.isNullOrBlank(classification)){
			throw new SHDRCException("Please select the Classification of the Indicator");
		}
		if(Util.isNullOrBlank(category)){
			throw new SHDRCException("Please select the Category of the Indicator");
		}
		else if(category.equalsIgnoreCase("others")){
			if(Util.isNullOrBlank(otherCategory)){
				throw new SHDRCException("Please enter the Category of the Indicator");
			}
		}
		if(Util.isNullOrBlank(indicatorName)){
			throw new SHDRCException("Please enter Indicator Name");
		}
		if(Util.isNullOrBlank(subCategory)){
			throw new SHDRCException("Please enter SubCategory of the Indicator");
		}
		if(Util.isNullOrBlank(subSubCategory)){
			throw new SHDRCException("Please enter SubSubCategory of the Indicator");
		}
		/*if(Util.isNullOrBlank(level)){
			throw new SHDRCException("Please enter Level of the Indicator");
		}*/
		if(Util.isNullOrBlank(frequency)){
			throw new SHDRCException("Please select the Frequency of the Indicator");
		}
		if(Util.isNullOrBlank(hierarchy)){
			throw new SHDRCException("Please select the Hierarchy of the Indicator");
		}
	}
	
}
