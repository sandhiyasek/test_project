package gov.shdrc.dataentry.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.IDfwDataEntryManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.DFWEmployeeForm;
import gov.shdrc.util.Promotion;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.SplCharsConstants;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
public class DfwDataEntryController {
	private static Logger log=Logger.getLogger(DfwDataEntryController.class);
	@Autowired
	IDfwDataEntryManager dfwDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/dfwDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws SHDRCException, NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFW);
		    modelView.setViewName("dfwDataentry");	
		    
		    String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
		
	    	JSONArray jsonArray = Util.getColumnHearders();
			String encodedJsonArray = Util.encodeJSONArray(jsonArray.toString());
	        modelView.addObject("encodedJsonArray", encodedJsonArray);
	        
	        String csrfToken =null;
			
			 csrfToken = CSRFTokenUtil.getToken (request.getSession());
			 modelView.addObject("CSRF_TOKEN", csrfToken);
	        
	        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
	        modelView.addObject("directorateList", directorateList);
	        
	        JSONArray directorateServletList= commonManager.getDirecorateServletList();
	        modelView.addObject("directorateServletList", Util.encodeJSONArray(directorateServletList.toString()));
	        
	        List<CommonStringList> districtList= commonManager.getDistricts(userName);
	        modelView.addObject("districts", districtList);	        

	        String districtName=null;
	        String institutionName=null;
	        List<CommonStringList> institutionList=null;
	        if(Util.isNotNull(districtList) && districtList.size()>0){
	        	CommonStringList district = districtList.get(0);
	        	districtName=district.getName();
	        	List<CommonStringList> institutionTypeList=null;
	        	
	        	if(ShdrcConstants.SELECTALLID==district.getId()){
	        		institutionTypeList = new ArrayList<CommonStringList>();
	        		CommonStringList commonStringList = new CommonStringList();
	    			commonStringList.setId(ShdrcConstants.SELECTALLID);
	    			commonStringList.setName("DFWB");
	    			institutionTypeList.add(commonStringList);
	    			
	    			institutionList = new ArrayList<CommonStringList>();
	        		CommonStringList lst = new CommonStringList();
	        		lst.setId(ShdrcConstants.SELECTALLID);
	        		lst.setName(ShdrcConstants.SELECTALLVALUE);
	    			institutionList.add(lst);
	        	}else{
	        		institutionTypeList= dfwDataEntryManager.getInstitutionTypeList(district.getId(),userName);
	        		if(institutionTypeList!=null && institutionTypeList.size()>0){
	        			CommonStringList inst = institutionTypeList.get(0);
	        			if(ShdrcConstants.SELECTALLID==inst.getId()){
	        				institutionList = new ArrayList<CommonStringList>();
	    	        		CommonStringList lst = new CommonStringList();
	    	        		lst.setId(ShdrcConstants.SELECTALLID);
	    	        		lst.setName(ShdrcConstants.SELECTALLVALUE);
	    	    			institutionList.add(lst);
	        			}else{
		        			institutionList= dfwDataEntryManager.getInstitutionList(district.getId(),inst.getName(),userName);
		        		}	        			
	        		}
	        	}
	        	if(institutionList!=null && institutionList.size()>0){
		        	CommonStringList institution = institutionList.get(0);
		        	institutionName=institution.getName();
		        }
	        	modelView.addObject("institutionList", institutionList);
				modelView.addObject("institutionTypeList", institutionTypeList);
				
				modelView.addObject("districtName", districtName);
		        modelView.addObject("institutionName", institutionName);
	        }
			
			
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.DFW,ShdrcQueryList.DFW_CLASSIFICATION_LIST);
	        modelView.addObject("classificationList", classificationList);
       
	        List<Integer> yearList= Util.yearList;
	        modelView.addObject("yearList", yearList);
	        
	        List<CommonStringList> freuencyList= Util.freuencyList;
	        modelView.addObject("freuencyList", freuencyList);
	        
	        List<CommonStringList> weeksList= Util.weeksList;
	        modelView.addObject("weeksList", weeksList);
	        
	        List<CommonStringList> quarterList= Util.quarterList;
	        modelView.addObject("quarterList", quarterList);
	        
	        List<CommonStringList> monthsList= Util.monthsList;
	        modelView.addObject("monthsList", monthsList);

	        Calendar calendar=Calendar.getInstance();
	        calendar.add(Calendar.MONTH,-1);
	        modelView.addObject("currentMonth", calendar.get(Calendar.MONTH));
	        modelView.addObject("currentYear", calendar.get(Calendar.YEAR));

	        modelView.addObject("directorateId", ShdrcConstants.DirectorateId.DFW);
	        
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{} 
		
			return modelView;
	}

	@RequestMapping(value="/dfwdistrictChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitutionType(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		PrintWriter out = response.getWriter();
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		String userName = UserInfo.getLoggedInUserName();
		
		List<CommonStringList> institutionTypeList= null;
		if(Util.isNotNull(request.getParameter("districtId"))){
			Integer districtId = Integer.parseInt(request.getParameter("districtId"));
			institutionTypeList= dfwDataEntryManager.getInstitutionTypeList(districtId,userName);
		}else{
			institutionTypeList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			institutionTypeList.add(commonStringList);
		}
		String json=new Gson().toJson(institutionTypeList);  
		out.println(json);
		log.debug(this.getClass().getName() + "- Exit ");	
		}finally{} 
	}	
	
	@RequestMapping(value="/dfwinstitutionTypeChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitution(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		PrintWriter out = response.getWriter();
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		String userName = UserInfo.getLoggedInUserName();
		
		List<CommonStringList> institutionList= null;
		if(Util.isNotNull(request.getParameter("districtId")) && Util.isNotNull(request.getParameter("institutionType"))){
			Integer districtId = Integer.parseInt(request.getParameter("districtId"));
			String institutionType = request.getParameter("institutionType");
			institutionList= dfwDataEntryManager.getInstitutionList(districtId,institutionType,userName);
		}else{
			institutionList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			institutionList.add(commonStringList);
		}
		String json=new Gson().toJson(institutionList);  
		out.println(json);
		log.debug(this.getClass().getName() + "- Exit ");	
		}finally{} 
	}	
		
	@RequestMapping(value="/dfwsearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
		String institutionType = request.getParameter("institutionType");
		Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
		String frequency = request.getParameter("frequency");
		String week = request.getParameter("week");
		String quarter = request.getParameter("quarter");
		String month = request.getParameter("month");
		Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
		String searchDate = request.getParameter("searchDate");
		
		PrintWriter out = response.getWriter();
		JSONArray data=new JSONArray();
		JSONObject obj = new JSONObject();
	
		String dataEntryLevel = getDataEntryLevel(districtId,institutionType,institutionId);
		List<CommonStringList> classificationList=null;
		String classificationName =null;
		JSONArray dfwDirectorateRecords =null;
		String generalLevel=null;

			 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.DFW,frequency,dataEntryLevel,ShdrcQueryList.DFW_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
			 if(classificationList!=null){
		        	CommonStringList commonStringList = classificationList.get(0);
		        	classificationName = commonStringList.getName();
		        	dfwDirectorateRecords = dfwDataEntryManager.getDfwDirectorateRecords(districtId,institutionType,institutionId,classificationName,frequency,week,quarter,
		   				 searchDate, month, year);
		        
		        	if(dfwDirectorateRecords==null){
		        		if(districtId!=null && districtId==ShdrcConstants.SELECTALLID)
		        			generalLevel=SplCharsConstants.SINGLEQUOTE+ShdrcConstants.STATE+SplCharsConstants.SINGLEQUOTE;
		        		else if(institutionId!=null && institutionId==ShdrcConstants.SELECTALLID)
		        			generalLevel=SplCharsConstants.SINGLEQUOTE+ShdrcConstants.DISTRICT+SplCharsConstants.SINGLEQUOTE;
		        		else
		        			generalLevel=SplCharsConstants.SINGLEQUOTE+ShdrcConstants.INSTITUTION+SplCharsConstants.SINGLEQUOTE;
		        		
		    			dfwDirectorateRecords = dfwDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DFW,classificationName,frequency,"N",generalLevel,dataEntryLevel,month);
		    		}
			 } 		
		String json=null;
		if(classificationList!=null){
			obj.put("isFrequencySelectionValid", true);
		}else
			obj.put("isFrequencySelectionValid", false);
		json=new Gson().toJson(classificationList);
		obj.put("dfwDirectorateRecords", dfwDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);
	/*	out.println(json);*/
		log.debug(this.getClass().getName() + "- Exit ");	
		}finally{}
	}
	
	@RequestMapping(value="/dfwtabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
		String institutionType = request.getParameter("institutionType");
		Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
		String frequency = request.getParameter("frequency");
		String week = request.getParameter("week");
		String quarter = request.getParameter("quarter");
		String month = request.getParameter("month");
		Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
		String searchDate = request.getParameter("searchDate");
		
		PrintWriter out = response.getWriter();
		JSONArray data=new JSONArray();
		JSONObject obj = new JSONObject();
	
		List<CommonStringList> classificationList=null;
		String classificationName = request.getParameter("classificationName");
		String dataEntryLevel = getDataEntryLevel(districtId,institutionType,institutionId);
		String json=null;
		String generalLevel=null;
		obj.put("isFrequencySelectionValid", true);
		json=new Gson().toJson(classificationList);
		JSONArray dfwDirectorateRecords = dfwDataEntryManager.getDfwDirectorateRecords(districtId,institutionType,institutionId,classificationName,frequency,week,quarter,
				 searchDate, month, year);
		if(dfwDirectorateRecords==null && classificationName!=null){
			if(districtId==ShdrcConstants.SELECTALLID)
    			generalLevel=SplCharsConstants.SINGLEQUOTE+ShdrcConstants.STATE+SplCharsConstants.SINGLEQUOTE;
			else if(institutionId!=null && institutionId==ShdrcConstants.SELECTALLID)
    			generalLevel=SplCharsConstants.SINGLEQUOTE+ShdrcConstants.DISTRICT+SplCharsConstants.SINGLEQUOTE;
    		else
    			generalLevel=SplCharsConstants.SINGLEQUOTE+ShdrcConstants.INSTITUTION+SplCharsConstants.SINGLEQUOTE;
			dfwDirectorateRecords = dfwDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DFW,classificationName,frequency,"N",generalLevel,dataEntryLevel,month);
		}
		obj.put("dfwDirectorateRecords", dfwDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);
		log.debug(this.getClass().getName() + "- Exit ");	
		}finally{}
	}
	
	@RequestMapping(value="/dfwsave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
	
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			String institutionType = request.getParameter("institutionType");
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String classificationName = request.getParameter("classificationName");
			String frequency = request.getParameter("frequency");
			String week = request.getParameter("week");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			String searchDate = request.getParameter("searchDate");
			
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray gridRecords = new JSONArray(handsOnTableData);
			
			String dataEntryLevel = getDataEntryLevel(districtId,institutionType,institutionId);
			
			boolean successFlag =dfwDataEntryManager.insertDfwDirectorateRecords(districtId,institutionType,institutionId, classificationName,dataEntryLevel,
					frequency,week,quarter,searchDate, month, year,gridRecords);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/dfwupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		try{ 
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			
			boolean successFlag = dfwDataEntryManager.updateDfwDirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
		    }finally{}
	
	}
		
	private String getDataEntryLevel(Integer districtId,String institutionType,Integer institutionId){
		String indicatorHierarchy=null;
		try{
			log.debug(this.getClass().getName() + "- Entering ");
		if(districtId!=null
				&& districtId==ShdrcConstants.SELECTALLID)
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELSTATE;
		else if(districtId!=null 
				&& districtId!=ShdrcConstants.SELECTALLID
				&& institutionType!=null 
				&& "DFWB".equals(institutionType)
				&& institutionId!=null
				&& institutionId==ShdrcConstants.SELECTALLID)
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELDISTRICT;
		else if(institutionType!=null 
				&& !"DFWB".equals(institutionType)
				&& institutionId!=null
				&& institutionId==ShdrcConstants.SELECTALLID)
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELINSTITUTIONTYPE;
		else
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELINSTITUTION;
		log.debug(this.getClass().getName() + "- Exit ");
	    }finally{}
		return indicatorHierarchy;
	}
	
	@RequestMapping(value="/dfwEmployeeDetails", method=RequestMethod.GET)
	public ModelAndView getEmployeeDetails(HttpServletRequest request) throws SHDRCException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFW);
		    modelView.setViewName("dfwEmpDetails");	

		    Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);			
			
			List<DFWEmployeeForm> employeeDetailsList=dfwDataEntryManager.getEmployeeDetailsList(districtId,institutionId);
			String userTier = commonManager.getUserTier();
			
			modelView.addObject("districtId", districtId);
			modelView.addObject("institutionId", institutionId);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			modelView.addObject("userTier", userTier);
			modelView.addObject("employeeDetailsList", employeeDetailsList);			
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/dfwEmployeeAddForm", method=RequestMethod.GET)
	public ModelAndView getEmployeeAddScreen(HttpServletRequest request) throws SHDRCException{		
	   
		ModelAndView modelView=null;
	  
		try { 
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFW);
		    modelView.setViewName("dfwEmpDetailsAdd");	

		    Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			
			List<CommonStringList> postList=dfwDataEntryManager.getPostList(ShdrcConstants.DirectorateId.DFW);
			modelView.addObject("postList", postList);
			modelView.addObject("districtId", districtId);
			modelView.addObject("institutionId", institutionId);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));			
			log.debug(this.getClass().getName() + "- Exit ");		
			}finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/dfwEmployeeChangeForm", method=RequestMethod.GET)
	public ModelAndView getEmployeeChangeScreen(HttpServletRequest request) throws SHDRCException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFW);
		    modelView.setViewName("dfwEmpDetailsChange");	

		    Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			Long employeeId=(Util.isNotNull(request.getParameter("employeeId"))?Long.parseLong(request.getParameter("employeeId")):null);
			DFWEmployeeForm employeeDetails=dfwDataEntryManager.getEmployeeDetails(employeeId);
			List<CommonStringList> postList=dfwDataEntryManager.getPostList(ShdrcConstants.DirectorateId.DFW);
			DFWEmployeeForm qualificationDetails=dfwDataEntryManager.getQualificationDetails(employeeId);
			Promotion promotionDetails=dfwDataEntryManager.getPromotionDetails(employeeId);
			DFWEmployeeForm leaveDetails=dfwDataEntryManager.getLeaveDetails(employeeId);
			
			modelView.addObject("postList", postList);
			modelView.addObject("employeeDetails", employeeDetails);
			modelView.addObject("qualificationDetails", qualificationDetails);
			modelView.addObject("promotionDetails", (promotionDetails!=null?promotionDetails:new Promotion()));
			modelView.addObject("leaveDetails", leaveDetails);
			modelView.addObject("districtId", districtId);
			modelView.addObject("institutionId", institutionId);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));		
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/dfwEmployeeTransferForm", method=RequestMethod.GET)
	public ModelAndView getEmployeeTransferScreen(HttpServletRequest request) throws SHDRCException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFW);
		    modelView.setViewName("dfwEmpDetailsTransfer");	

		    Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			Long employeeId=(Util.isNotNull(request.getParameter("employeeId"))?Long.parseLong(request.getParameter("employeeId")):null);
			String userName = UserInfo.getLoggedInUserName();
			
			List<CommonStringList> postList=dfwDataEntryManager.getPostList(ShdrcConstants.DirectorateId.DFW);
			modelView.addObject("postList", postList);
			List<CommonStringList> districtList= commonManager.getDistricts(userName);
			List<CommonStringList> institutionList= null;
			if(Util.isNotNull(request.getParameter("districtId"))){
				institutionList= dfwDataEntryManager.getInstitutionList(districtId);
			}else{
				institutionList = new ArrayList<CommonStringList>();
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
				institutionList.add(commonStringList);
			}
		
			modelView.addObject("districts", districtList);
			modelView.addObject("institutions", institutionList);
			modelView.addObject("employeeId", employeeId);			
			modelView.addObject("districtId", districtId);
			modelView.addObject("institutionId", institutionId);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));		
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/dfwEmployeeTransferDetails", method=RequestMethod.GET)
	public ModelAndView getEmployeeTransferDetails(HttpServletRequest request) throws SHDRCException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFW);
		    modelView.setViewName("dfwEmployeeTransferList");	
		    
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			List<DFWEmployeeForm> employeeTransferDetailsList=dfwDataEntryManager.getEmployeeTransferDetailsList();
			modelView.addObject("employeeTransferDetailsList", employeeTransferDetailsList);
	        modelView.addObject("districtId", districtId);
			modelView.addObject("institutionId", institutionId);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));		   
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/dfwAddEmployee", method=RequestMethod.POST)
	public @ResponseBody void AddEmployee(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException{
		PrintWriter out = response.getWriter();
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			boolean successFlag=false;
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String employeeName=request.getParameter("employeeName");
			String gpfOrCpsNo=request.getParameter("gpfOrCpsNo");
			String gender=request.getParameter("gender");
			String designation=request.getParameter("designation");
			String payBand=request.getParameter("payBand");
			String groupCategory=request.getParameter("groupCategory");
			Integer dutyPay=(Util.isNotNull(request.getParameter("dutyPay"))?Integer.parseInt(request.getParameter("dutyPay")):null);
			String recruitedBy=request.getParameter("recruitedBy");
			Integer gradePay=(Util.isNotNull(request.getParameter("gradePay"))?Integer.parseInt(request.getParameter("gradePay")):null);
			String community=request.getParameter("community");
			String workingLocation=request.getParameter("workingLocation");
			String dobDate=request.getParameter("dobDate");
			String qualification=request.getParameter("qualification");
			String ugMajor=request.getParameter("ugMajor");
			String pgMajor=request.getParameter("pgMajor");
			String others=request.getParameter("others");
			String firstAppointmentPost=request.getParameter("firstAppointmentPost");
			String firstAppointmentDOJ=request.getParameter("firstAppointmentDOJ");
			String dateOfRegularisation=request.getParameter("dateOfRegularisation");
			String dateOfProbationDecleration=request.getParameter("dateOfProbationDecleration");
			String dateOfRetirement=request.getParameter("dateOfRetirement");
			String incrementDueMonth=request.getParameter("incrementDueMonth");
			String slsDueDate=request.getParameter("slsDueDate");
			String physicalStatus=request.getParameter("physicalStatus");
			String promotion1=request.getParameter("promotion1");
			String promotion1DOJ=request.getParameter("promotion1DOJ");
			String promotion2=request.getParameter("promotion2");
			String promotion2DOJ=request.getParameter("promotion2DOJ");
			String promotion3=request.getParameter("promotion3");
			String promotion3DOJ=request.getParameter("promotion3DOJ");
			String promotion4=request.getParameter("promotion4");
			String promotion4DOJ=request.getParameter("promotion4DOJ");
			String promotion5=request.getParameter("promotion5");
			String promotion5DOJ=request.getParameter("promotion5DOJ");
			String punishments=request.getParameter("punishments");
			String maritalStatus=request.getParameter("maritalStatus");	
			String ELTakenFrom=request.getParameter("ELTakenFrom");
			String ELTakenTo=request.getParameter("ELTakenTo");
			Integer balanceEL=(Util.isNotNull(request.getParameter("balanceEL"))?Integer.parseInt(request.getParameter("balanceEL")):null);
			String MLTakenFrom=request.getParameter("MLTakenFrom");
			String MLTakenTo=request.getParameter("MLTakenTo");
			Integer balanceML=(Util.isNotNull(request.getParameter("balanceML"))?Integer.parseInt(request.getParameter("balanceML")):null);
			String UELTakenFrom=request.getParameter("UELTakenFrom");
			String UELTakenTo=request.getParameter("UELTakenTo");
			Integer balanceUEL=(Util.isNotNull(request.getParameter("balanceUEL"))?Integer.parseInt(request.getParameter("balanceUEL")):null);
			String nomineeDetails=request.getParameter("nomineeDetails");
			Integer ELTakenDays=(Util.isNotNull(request.getParameter("ELTakenDays"))?Integer.parseInt(request.getParameter("ELTakenDays")):null);
			Integer MLTakenDays=(Util.isNotNull(request.getParameter("MLTakenDays"))?Integer.parseInt(request.getParameter("MLTakenDays")):null);
			Integer UELTakenDays=(Util.isNotNull(request.getParameter("UELTakenDays"))?Integer.parseInt(request.getParameter("UELTakenDays")):null);
		
			List<Promotion> promotionList=new ArrayList<Promotion>();
			if(Util.isNotNull(promotion1)){
				Promotion promotionobj=new Promotion();
				promotionobj.setPromotionType("Promotion1");
				promotionobj.setPromotionDOJ(promotion1DOJ);
				promotionobj.setPromotion(promotion1);
				promotionList.add(promotionobj);
			}
			if(Util.isNotNull(promotion2)){
				Promotion promotionobj2=new Promotion();
				promotionobj2.setPromotionType("Promotion2");
				promotionobj2.setPromotionDOJ(promotion2DOJ);
				promotionobj2.setPromotion(promotion2);
				promotionList.add(promotionobj2);
			}
			if(Util.isNotNull(promotion3)){
				Promotion promotionobj3=new Promotion();
				promotionobj3.setPromotionType("Promotion3");
				promotionobj3.setPromotionDOJ(promotion3DOJ);
				promotionobj3.setPromotion(promotion3);
				promotionList.add(promotionobj3);
			}
			if(Util.isNotNull(promotion4)){
				Promotion promotionobj4=new Promotion();
				promotionobj4.setPromotionType("Promotion4");
				promotionobj4.setPromotionDOJ(promotion4DOJ);
				promotionobj4.setPromotion(promotion4);
				promotionList.add(promotionobj4);
			}
			if(Util.isNotNull(promotion5)){
				Promotion promotionobj5=new Promotion();
				promotionobj5.setPromotionType("Promotion5");
				promotionobj5.setPromotionDOJ(promotion5DOJ);
				promotionobj5.setPromotion(promotion5);
				promotionList.add(promotionobj5);
			}
				successFlag=dfwDataEntryManager.insertEmployeeDetails(districtId,institutionId,employeeName,gpfOrCpsNo,gender,designation,payBand,
						groupCategory,dutyPay,workingLocation,recruitedBy,gradePay,community,dobDate,qualification,ugMajor,pgMajor,others,firstAppointmentPost,firstAppointmentDOJ,
						dateOfRegularisation,dateOfProbationDecleration,dateOfRetirement,incrementDueMonth,slsDueDate,physicalStatus,punishments,maritalStatus,nomineeDetails,
						ELTakenFrom,ELTakenTo,ELTakenDays,balanceEL,MLTakenFrom,MLTakenTo,MLTakenDays,balanceML,UELTakenFrom,UELTakenTo,UELTakenDays,balanceUEL,promotionList);
					if(successFlag)
						out.println("Employee details have been saved successfully");
					else
						out.println("Error in saving Employee details");
					log.debug(this.getClass().getName() + "- Exit ");	
			}finally{}
		
	}
	
	@RequestMapping(value="/dfwChangeEmployee", method=RequestMethod.POST)
	public @ResponseBody void ChangeEmployee(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			boolean successFlag=false;
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String gpfOrCpsNo=request.getParameter("gpfOrCpsNo");
			String designation=request.getParameter("designation");
			String payBand=request.getParameter("payBand");
			String groupCategory=request.getParameter("groupCategory");
			Integer dutyPay=(Util.isNotNull(request.getParameter("dutyPay"))?Integer.parseInt(request.getParameter("dutyPay")):null);
			String recruitedBy=request.getParameter("recruitedBy");
			Integer gradePay=(Util.isNotNull(request.getParameter("gradePay"))?Integer.parseInt(request.getParameter("gradePay")):null);
			String community=request.getParameter("community");
			String workingLocation=request.getParameter("workingLocation");
			String dobDate=request.getParameter("dobDate");
			String qualification=request.getParameter("qualification");
			String ugMajor=request.getParameter("ugMajor");
			String pgMajor=request.getParameter("pgMajor");
			String others=request.getParameter("others");
			String firstAppointmentPost=request.getParameter("firstAppointmentPost");
			String firstAppointmentDOJ=request.getParameter("firstAppointmentDOJ");
			String dateOfRegularisation=request.getParameter("dateOfRegularisation");
			String dateOfProbationDecleration=request.getParameter("dateOfProbationDecleration");
			String dateOfRetirement=request.getParameter("dateOfRetirement");
			String incrementDueMonth=request.getParameter("incrementDueMonth");
			String slsDueDate=request.getParameter("slsDueDate");
			String physicalStatus=request.getParameter("physicalStatus");
			String promotion1=request.getParameter("promotion1");
			String promotion1DOJ=request.getParameter("promotion1DOJ");
		/*	String promotion2=request.getParameter("promotion2");
			String promotion2DOJ=request.getParameter("promotion2DOJ");
			String promotion3=request.getParameter("promotion3");
			String promotion3DOJ=request.getParameter("promotion3DOJ");
			String promotion4=request.getParameter("promotion4");
			String promotion4DOJ=request.getParameter("promotion4DOJ");
			String promotion5=request.getParameter("promotion5");
			String promotion5DOJ=request.getParameter("promotion5DOJ");*/
			String punishments=request.getParameter("punishments");
			String retirementType=request.getParameter("retirementType");
			String maritalStatus=request.getParameter("maritalStatus");	
			String ELTakenFrom=request.getParameter("ELTakenFrom");
			String ELTakenTo=request.getParameter("ELTakenTo");
			Integer balanceEL=(Util.isNotNull(request.getParameter("balanceEL"))?Integer.parseInt(request.getParameter("balanceEL")):null);
			String MLTakenFrom=request.getParameter("MLTakenFrom");
			String MLTakenTo=request.getParameter("MLTakenTo");
			Integer balanceML=(Util.isNotNull(request.getParameter("balanceML"))?Integer.parseInt(request.getParameter("balanceML")):null);
			String UELTakenFrom=request.getParameter("UELTakenFrom");
			String UELTakenTo=request.getParameter("UELTakenTo");
			Integer balanceUEL=(Util.isNotNull(request.getParameter("balanceUEL"))?Integer.parseInt(request.getParameter("balanceUEL")):null);
			String nomineeDetails=request.getParameter("nomineeDetails");
			Integer ELTakenDays=(Util.isNotNull(request.getParameter("ELTakenDays"))?Integer.parseInt(request.getParameter("ELTakenDays")):null);
			Integer MLTakenDays=(Util.isNotNull(request.getParameter("MLTakenDays"))?Integer.parseInt(request.getParameter("MLTakenDays")):null);
			Integer UELTakenDays=(Util.isNotNull(request.getParameter("UELTakenDays"))?Integer.parseInt(request.getParameter("UELTakenDays")):null);
			
			Long employeeId=(Util.isNotNull(request.getParameter("employeeId"))?Long.parseLong(request.getParameter("employeeId")):null);
			String previousPromotionType=null;
			Promotion promotionDetails=dfwDataEntryManager.getPromotionDetails(employeeId);
			if(promotionDetails!=null)
				previousPromotionType=promotionDetails.getPromotionType();
			String promotionType=request.getParameter("promotionType");
			
			successFlag=dfwDataEntryManager.updateEmployeeDetails(gpfOrCpsNo,employeeId,districtId,institutionId,designation,payBand,groupCategory,dutyPay,workingLocation,recruitedBy,gradePay,
					community,dobDate,qualification,ugMajor,pgMajor,others,firstAppointmentPost,firstAppointmentDOJ,dateOfRegularisation,dateOfProbationDecleration,
					dateOfRetirement,incrementDueMonth,slsDueDate,physicalStatus,promotionType,promotion1,promotion1DOJ,punishments,retirementType,maritalStatus,nomineeDetails,
					ELTakenFrom,ELTakenTo,ELTakenDays,balanceEL,MLTakenFrom,MLTakenTo,MLTakenDays,balanceML,UELTakenFrom,UELTakenTo,UELTakenDays,balanceUEL,previousPromotionType);
				if(successFlag)
					out.println("Employee details have been changed successfully");
				else
					out.println("Error in changing Employee details");
				log.debug(this.getClass().getName() + "- Exit ");	
				}finally{}
	}
	
	@RequestMapping(value="/dfwTransferEmployee", method=RequestMethod.POST)
	public @ResponseBody void TransferEmployee(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException{
		PrintWriter out = response.getWriter();
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			boolean successFlag=false;
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String gpfOrCpsNo=request.getParameter("gpfOrCpsNo");
			
			Long employeeId=(Util.isNotNull(request.getParameter("employeeId"))?Long.parseLong(request.getParameter("employeeId")):null);
			String transferType=request.getParameter("transferType");
			String PromotionName=request.getParameter("PromotionName");
			successFlag=dfwDataEntryManager.transferEmployee(gpfOrCpsNo,districtId,employeeId,institutionId,transferType,PromotionName);
			if(successFlag)
				out.println("The Employee details have been successfully Transfered");
			else
				out.println("Error in saving Employee details");
			log.debug(this.getClass().getName() + "- Exit ");	
			} finally{}
			
	}
	
	@RequestMapping(value="/dfwTransferDistrictChange", method=RequestMethod.POST)
	public @ResponseBody void TransferEmployeeDistrict(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException{
		PrintWriter out = response.getWriter();
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			List<CommonStringList> institutionList= null;
			if(Util.isNotNull(request.getParameter("districtId"))){
				institutionList= dfwDataEntryManager.getTransferInstitutionList(districtId);
			}else{
				institutionList = new ArrayList<CommonStringList>();
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
				institutionList.add(commonStringList);
			}
			String json=new Gson().toJson(institutionList);  
			out.println(json);
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{}
	}
	
	@RequestMapping(value="/dfwEmployeeTransferAcceptForm", method=RequestMethod.GET)
	public ModelAndView getEmployeeTransferAcceptScreen(HttpServletRequest request) throws SHDRCException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFW);
		    modelView.setViewName("dfwEmpTransferAccept");	
		    	   
		    String userName = UserInfo.getLoggedInUserName();			
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			modelView.addObject("districtId", districtId);
			modelView.addObject("institutionId", institutionId);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			
			List<CommonStringList> postList=dfwDataEntryManager.getPostList(ShdrcConstants.DirectorateId.DFW);
			modelView.addObject("postList", postList);
			List<CommonStringList> districtList= commonManager.getDistricts(userName);
			List<CommonStringList> institutionList= null;
			if(Util.isNotNull(request.getParameter("districtId"))){
				institutionList= dfwDataEntryManager.getInstitutionList(districtId);
			}else{
				institutionList = new ArrayList<CommonStringList>();
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
				institutionList.add(commonStringList);
			}
			Long employeeId=(Util.isNotNull(request.getParameter("employeeId"))?Long.parseLong(request.getParameter("employeeId")):null);
			DFWEmployeeForm transferEmployeeDetails=dfwDataEntryManager.getTransferEmployeeDetails(employeeId,districtId,institutionId);
			modelView.addObject("districts", districtList);
			modelView.addObject("institutions", institutionList);
			modelView.addObject("employeeId", employeeId);
			modelView.addObject("transferEmployeeDetails", transferEmployeeDetails);
			log.debug(this.getClass().getName() + "- Exit ");	
			} finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/dfwAcceptEmployeeTransfer", method=RequestMethod.POST)
	public @ResponseBody void AcceptEmpTransfer(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException{
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
			boolean successFlag=false;
			PrintWriter out = response.getWriter();
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String gpfOrCpsNo=request.getParameter("gpfOrCpsNo");
			Long employeeId=(Util.isNotNull(request.getParameter("employeeId"))?Long.parseLong(request.getParameter("employeeId")):null);
			String transferType=request.getParameter("transferType");
			String previousTransferType=request.getParameter("previousTransferType");
			String PromotionName=request.getParameter("PromotionName");
			String promotionDate=request.getParameter("promotionDate");
			String payBand=request.getParameter("payBand");
			String groupCategory=request.getParameter("groupCategory");
			Integer gradePay=(Util.isNotNull(request.getParameter("gradePay"))?Integer.parseInt(request.getParameter("gradePay")):null);
			String workingLocation=request.getParameter("workingLocation");
			successFlag=dfwDataEntryManager.acceptEmployee(gpfOrCpsNo,districtId,employeeId,institutionId,transferType,previousTransferType,PromotionName,
					promotionDate,payBand,groupCategory,gradePay,workingLocation);
			if(successFlag)
				out.println("The Employee details have been successfully accepted");
			else
				out.println("Error in accepting Employee details");
			log.debug(this.getClass().getName() + "- Exit ");	
			}finally{} 
	}
	
}
