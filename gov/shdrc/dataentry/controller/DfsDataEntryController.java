package gov.shdrc.dataentry.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.IDfsDataEntryManager;
import gov.shdrc.exception.SHDRCException;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.ShdrcConstants;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class DfsDataEntryController {
	private static Logger log =Logger.getLogger(DfsDataEntryController.class);
	@Autowired
	IDfsDataEntryManager dfsDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/dfsDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws SHDRCException, NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
	  
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DFS);
		    modelView.setViewName("dfsDataentry");			
			
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
	        
	        String userAccessLevel=commonManager.getUserAccessLevel();
	        
	        modelView.addObject("userAccessLevel", userAccessLevel);
	        
	        String districtName=null;
	        if(Util.isNotNull(districtList)){
	        	CommonStringList district = districtList.get(0);
	        	districtName=district.getName();
	        }
	        modelView.addObject("districtName", districtName);
	        
	        List<CommonStringList> labList= dfsDataEntryManager.getLabList(userName);
	        modelView.addObject("labList", labList);
	        
	        String labName=null;
	        if(Util.isNotNull(labList)&&labList.size()>0){
	        	CommonStringList lab = labList.get(0);
	        	labName=lab.getName();
	        }
	        modelView.addObject("labName", labName);
		
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.DFS,ShdrcQueryList.DFS_CLASSIFICATION_LIST);
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
	        
	        modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));

	        modelView.addObject("directorateId", ShdrcConstants.DirectorateId.DFS);
			log.debug(this.getClass().getName() +"- Exit");
			}finally{} 
		
			return modelView;
	}
	
	@RequestMapping(value="/dfslabChange", method=RequestMethod.POST)
	public @ResponseBody void getDistrict(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		
		PrintWriter out = response.getWriter();
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		String userName = UserInfo.getLoggedInUserName();
		List<CommonStringList> districtsList= null;
		if(Util.isNotNull(request.getParameter("labId"))){
			Integer labId = Integer.parseInt(request.getParameter("labId"));
			districtsList= dfsDataEntryManager.getDistrictList(labId,userName);
		}else{
			districtsList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			districtsList.add(commonStringList);
		}
		String json=new Gson().toJson(districtsList);  
		out.println(json);	
		log.debug(this.getClass().getName() +"- Exit");
		}finally{} 
	}	
	
	@RequestMapping(value="/dfsdistrictChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitutionType(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		
		PrintWriter out = response.getWriter();
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		String userName = UserInfo.getLoggedInUserName();
		List<CommonStringList> institutionTypeList= null;
		if(Util.isNotNull(request.getParameter("labId"))){
			Integer labId = Integer.parseInt(request.getParameter("labId"));
			if(Util.isNotNull(request.getParameter("districtId"))){
				Integer districtId = Integer.parseInt(request.getParameter("districtId"));
				institutionTypeList= dfsDataEntryManager.getInstitutionTypeList(labId,districtId,userName);
			}
		}else{
			institutionTypeList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			institutionTypeList.add(commonStringList);
		}
		String json=new Gson().toJson(institutionTypeList);  
		out.println(json);
		log.debug(this.getClass().getName() +"- Exit");
		}finally{} 
	}	
	
	@RequestMapping(value="/dfsinstitutionTypeChange", method=RequestMethod.POST)
	public @ResponseBody void getInstitutionSpeciality(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		PrintWriter out = response.getWriter();
		String userName = UserInfo.getLoggedInUserName();
		List<CommonStringList> institutionSpecialityList= null;
		if(Util.isNotNull(request.getParameter("labId"))){
			Integer labId = Integer.parseInt(request.getParameter("labId"));
			if(Util.isNotNull(request.getParameter("districtId"))){
				Integer districtId = Integer.parseInt(request.getParameter("districtId"));
				String areaType=request.getParameter("areaType");
				institutionSpecialityList= dfsDataEntryManager.getInstitutionSpecialityList(labId,districtId,areaType,userName);
			}
		}else{
			institutionSpecialityList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			institutionSpecialityList.add(commonStringList);
		}
		String json=new Gson().toJson(institutionSpecialityList);  
		out.println(json);	
		log.debug(this.getClass().getName() +"- Exit");
		}finally{}
	}
	
	@RequestMapping(value="/dfsinstitutionSpecialityChange", method=RequestMethod.POST)
	public @ResponseBody void getAreaCode(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		PrintWriter out = response.getWriter();
		String userName = UserInfo.getLoggedInUserName();
		List<CommonStringList> areaCodeList= null;
		if(Util.isNotNull(request.getParameter("labId"))){
			Integer labId = Integer.parseInt(request.getParameter("labId"));
			if(Util.isNotNull(request.getParameter("districtId"))){
				Integer districtId = Integer.parseInt(request.getParameter("districtId"));
				String areaType=request.getParameter("areaType");
				String area=request.getParameter("area");
				areaCodeList= dfsDataEntryManager.getAreaCodeList(labId,districtId,areaType,area,userName);
			}
		}else{
			areaCodeList = new ArrayList<CommonStringList>();
			CommonStringList commonStringList = new CommonStringList();
			commonStringList.setId(ShdrcConstants.SELECTALLID);
			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
			areaCodeList.add(commonStringList);
		}
		String json=new Gson().toJson(areaCodeList);  
		out.println(json);	
		log.debug(this.getClass().getName() +"- Exit");
		}finally{}
	}
		
	@RequestMapping(value="/dfssearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
		Integer labId=(Util.isNotNull(request.getParameter("labId"))?Integer.parseInt(request.getParameter("labId")):null);
		String labName = request.getParameter("labName");
		Integer labDistrictId=(Util.isNotNull(request.getParameter("labDistrictId"))?Integer.parseInt(request.getParameter("labDistrictId")):null);
		String areaType = request.getParameter("areaType");
		String area = request.getParameter("area");
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
		
		String dataEntryLevel = getDataEntryLevel(districtId,labId,labName);
		List<CommonStringList> classificationList=null;
		String classificationName =null;
		JSONArray dfsDirectorateRecords =null;
		 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.DFS,frequency,dataEntryLevel,ShdrcQueryList.DFS_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
		 if(classificationList!=null){
	        	CommonStringList commonStringList = classificationList.get(0);
	        	classificationName = commonStringList.getName();
	        	dfsDirectorateRecords = dfsDataEntryManager.getDfsDirectorateRecords(districtId,labId,labDistrictId,areaType,area,institutionId,classificationName,frequency,week,quarter,
	   				 searchDate, month, year,dataEntryLevel);
	        	if(dfsDirectorateRecords==null){
	    			dfsDirectorateRecords = dfsDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DFS,classificationName,frequency,"N",dataEntryLevel);
	    		}
		 } 	
	
		String json=null;
		if(classificationList!=null){
			obj.put("isFrequencySelectionValid", true);
		}else
			obj.put("isFrequencySelectionValid", false);
		json=new Gson().toJson(classificationList);
		obj.put("dfsDirectorateRecords", dfsDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);	
		log.debug(this.getClass().getName() +"- Exit");
		}finally{}
	}
	
	@RequestMapping(value="/dfstabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, JSONException{
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
		Integer labId=(Util.isNotNull(request.getParameter("labId"))?Integer.parseInt(request.getParameter("labId")):null);
		String labName = request.getParameter("labName");
		Integer labDistrictId=(Util.isNotNull(request.getParameter("labDistrictId"))?Integer.parseInt(request.getParameter("labDistrictId")):null);
		String areaType = request.getParameter("areaType");
		String area = request.getParameter("area");
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
		String dataEntryLevel = getDataEntryLevel(districtId,labId,labName);
		String json=null;
		obj.put("isFrequencySelectionValid", true);
		json=new Gson().toJson(classificationList);
		JSONArray dfsDirectorateRecords = dfsDataEntryManager.getDfsDirectorateRecords(districtId,labId,labDistrictId,areaType,area,institutionId,classificationName,frequency,week,quarter,
				 searchDate, month, year,dataEntryLevel);
		if(dfsDirectorateRecords==null && classificationName!=null){
			dfsDirectorateRecords = dfsDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DFS,classificationName,frequency,"N",dataEntryLevel);
		}
		obj.put("dfsDirectorateRecords", dfsDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);	
		log.debug(this.getClass().getName() +"- Exit");
		}finally{}
	}
	
	@RequestMapping(value="/dfssave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer labId=(Util.isNotNull(request.getParameter("labId"))?Integer.parseInt(request.getParameter("labId")):null);
			String labName = request.getParameter("labName");
			Integer labDistrictId=(Util.isNotNull(request.getParameter("labDistrictId"))?Integer.parseInt(request.getParameter("labDistrictId")):null);
			String areaType = request.getParameter("areaType");
			String area = request.getParameter("area");
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
			String dataEntryLevel = getDataEntryLevel(districtId,labId,labName);
			
			boolean successFlag =dfsDataEntryManager.insertDfsDirectorateRecords(districtId,labId,labDistrictId,areaType,area,institutionId, 
					classificationName,frequency,week,quarter,searchDate, month, year,dataEntryLevel,gridRecords);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
				log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/dfsupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException, IOException, ServletException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			boolean successFlag = dfsDataEntryManager.updateDfsDirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
			else
				out.println("Save Error");
			    log.debug(this.getClass().getName() + "- Exit ");
		}finally{}
	
	}
	
	private String getDataEntryLevel(Integer districtId,Integer labId,String labName){
		String indicatorHierarchy=null;
		try { 
            log.debug(this.getClass().getName() + "- Entering ");
		if(districtId!=null
				&& districtId==ShdrcConstants.SELECTALLID
				&& labId==null)
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELSTATE;
		else if(districtId!=null 
				&& districtId!=ShdrcConstants.SELECTALLID)				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELDISTRICT;
		else if(labId!=null 
				&& labName.contains("FAL"))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELLAB;
		 log.debug(this.getClass().getName() + "- Exit ");
		}catch(Exception e){
			log.error(this.getClass().getName() + "- doUpdate "+e.getMessage());
		}finally{}
		return indicatorHierarchy;
	}
	
}
