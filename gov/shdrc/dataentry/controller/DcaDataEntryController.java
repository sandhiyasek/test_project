package gov.shdrc.dataentry.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.dataentry.service.IDcaDataEntryManager;
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
public class DcaDataEntryController {
	private static Logger log=Logger.getLogger(DcaDataEntryController.class);
	@Autowired
	IDcaDataEntryManager dcaDataEntryManager;
	@Autowired
	ICommonManager commonManager;
	
	@RequestMapping(value="/dcaDataEntry", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws SHDRCException, NoSuchAlgorithmException, ServletException{		
	   
		ModelAndView modelView=null;
	  
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		    modelView=new ModelAndView();
		    UserInfo.hasValidDataEntryAccess(ShdrcConstants.Role.DCA);
		    modelView.setViewName("dcaDataentry");			
			
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
	        
	        List<CommonStringList> hudList= dcaDataEntryManager.getHudList(userName);
	        modelView.addObject("huds", hudList);	       
	        
	        Integer hudId=null;
	        String hudName=null;
	        String blockName=null;
	        List<CommonStringList> blockList=null;
	        List<CommonStringList> institutionList=null;
	        if(Util.isNotNull(hudList)){
	        	CommonStringList hud = hudList.get(0);
	        	hudId=hud.getId();
	        	hudName=hud.getName();	        	
		        if(ShdrcConstants.SELECTALLID==hud.getId()){
	        		blockList = new ArrayList<CommonStringList>();
	        		CommonStringList commonStringList = new CommonStringList();
	    			commonStringList.setId(ShdrcConstants.SELECTALLID);
	    			commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
	    			blockList.add(commonStringList);
		        }else if(hudName.equalsIgnoreCase("NA")){
		        	institutionList= dcaDataEntryManager.getInstituteList(hudId,userName);
		        	modelView.addObject("institutionList",institutionList);
		        	if(institutionList!=null && institutionList.size()>0){
			        	CommonStringList institution = institutionList.get(0);
			 	        modelView.addObject("institutionName", institution.getName());	        
					}
		        } else{
		        	blockList= dcaDataEntryManager.getBlockList(hud.getId(),userName);
		         }	        	
		        modelView.addObject("blockList", blockList);
				if(blockList!=null && blockList.size()>0){
		        	CommonStringList block = blockList.get(0);
		        	blockName=block.getName();				
				}
	        }
	       
	        modelView.addObject("hudName", hudName);
	        modelView.addObject("blockName", blockName);	        
		
	        List<CommonStringList> classificationList= commonManager.getClassificationList(ShdrcConstants.DirectorateId.DCA,ShdrcQueryList.DCA_CLASSIFICATION_LIST);
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

	        modelView.addObject("directorateId", ShdrcConstants.DirectorateId.DCA);    
	        log.debug(this.getClass().getName() + "- Exit ");
			}finally{} 
		
			return modelView;
	}

	@RequestMapping(value="/dcahudChange", method=RequestMethod.POST)
	public @ResponseBody void getBlock(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, JSONException{
		
		PrintWriter out = response.getWriter();
		try { 
			log.debug(this.getClass().getName() + "- Entering ");
		String userName = UserInfo.getLoggedInUserName();
		String hudName=request.getParameter("hudName");
		if(!hudName.equalsIgnoreCase("NA")){
			List<CommonStringList> blockList= null;
			if(Util.isNotNull(request.getParameter("hudId"))){
				Integer hudId = Integer.parseInt(request.getParameter("hudId"));
				blockList= dcaDataEntryManager.getBlockList(hudId,userName);
			}else{
				blockList = new ArrayList<CommonStringList>();
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
				blockList.add(commonStringList);
			}
			String json=new Gson().toJson(blockList);				
			out.println(json);
		}else{
			List<CommonStringList> institutionList= null;
			if(Util.isNotNull(request.getParameter("hudId"))){
				Integer hudId = Integer.parseInt(request.getParameter("hudId"));
				institutionList= dcaDataEntryManager.getInstituteList(hudId,userName);
			}else{
				institutionList = new ArrayList<CommonStringList>();
				CommonStringList commonStringList = new CommonStringList();
				commonStringList.setId(ShdrcConstants.SELECTALLID);
				commonStringList.setName(ShdrcConstants.SELECTALLVALUE);
				institutionList.add(commonStringList);
			}			       	
			String json=new Gson().toJson(institutionList);				
			out.println(json);
		}
		log.debug(this.getClass().getName() + "- Exit ");
		} finally{} 
	}	
		
	@RequestMapping(value="/dcasearch", method=RequestMethod.POST)
	public @ResponseBody void search(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, JSONException{
	try { 
		log.debug(this.getClass().getName() + "- Entering ");
		Integer hudId=(Util.isNotNull(request.getParameter("hudId"))?Integer.parseInt(request.getParameter("hudId")):null);
		Integer blockId=(Util.isNotNull(request.getParameter("blockId"))?Integer.parseInt(request.getParameter("blockId")):null);
		Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
		String frequency = request.getParameter("frequency");
		String week = request.getParameter("week");
		String quarter = request.getParameter("quarter");
		String month = request.getParameter("month");
		Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
		String searchDate = request.getParameter("searchDate");
		String hudName = request.getParameter("hudName");
		String blockName = request.getParameter("blockName");
		
		PrintWriter out = response.getWriter();
		JSONArray data=new JSONArray();
		JSONObject obj = new JSONObject();
	
		String dataEntryLevel = getDataEntryLevel(hudId,hudName,blockId,blockName,institutionId);
		List<CommonStringList> classificationList=null;
		String classificationName =null;
		JSONArray dcaDirectorateRecords =null;
		
			 classificationList=commonManager.getGeneralCategoryListByFrequency(ShdrcConstants.DirectorateId.DCA,frequency,dataEntryLevel,ShdrcQueryList.DCA_GENERAL_CATEGORY_LIST_BY_FREQUENCY);
			 if(classificationList!=null){
		        	CommonStringList commonStringList = classificationList.get(0);
		        	classificationName = commonStringList.getName();
		        	dcaDirectorateRecords = dcaDataEntryManager.getDcaDirectorateRecords(hudId,blockId,institutionId,classificationName,frequency,week,quarter,
		   				 searchDate, month, year);
		        	obj.put("isDataExists", dcaDirectorateRecords!=null);
		        	if(dcaDirectorateRecords==null){ 
		    			dcaDirectorateRecords = dcaDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DCA,classificationName,frequency,"N",dataEntryLevel);
		    		}
			 } 				
		String json=null;
		if(classificationList!=null){
			obj.put("isFrequencySelectionValid", true);
		}else
			obj.put("isFrequencySelectionValid", false);
		json=new Gson().toJson(classificationList);
		obj.put("dcaDirectorateRecords", dcaDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);
		log.debug(this.getClass().getName() + "- Exit ");
	}finally{}
	}
	
	@RequestMapping(value="/dcatabChange", method=RequestMethod.POST)
	public @ResponseBody void onTabChange(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, JSONException{
	try { 
		log.debug(this.getClass().getName() + "- Entering ");
		Integer hudId=(Util.isNotNull(request.getParameter("hudId"))?Integer.parseInt(request.getParameter("hudId")):null);
		Integer blockId=(Util.isNotNull(request.getParameter("blockId"))?Integer.parseInt(request.getParameter("blockId")):null);
		Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
		String frequency = request.getParameter("frequency");
		String week = request.getParameter("week");
		String quarter = request.getParameter("quarter");
		String month = request.getParameter("month");
		Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
		String searchDate = request.getParameter("searchDate");
		String hudName = request.getParameter("hudName");
		String blockName = request.getParameter("blockName");
		
		PrintWriter out = response.getWriter();
		JSONArray data=new JSONArray();
		JSONObject obj = new JSONObject();
		
		List<CommonStringList> classificationList=null;
		String classificationName = request.getParameter("classificationName");
		String json=null;
		obj.put("isFrequencySelectionValid", true);
		json=new Gson().toJson(classificationList);
		JSONArray dcaDirectorateRecords = dcaDataEntryManager.getDcaDirectorateRecords(hudId,blockId,institutionId,classificationName,frequency,week,quarter,
				 searchDate, month, year);
		obj.put("isDataExists", dcaDirectorateRecords!=null);
		String dataEntryLevel = getDataEntryLevel(hudId,hudName,blockId,blockName,institutionId);
		if(dcaDirectorateRecords==null && classificationName!=null){
			dcaDirectorateRecords = dcaDataEntryManager.getIndicatorList(ShdrcConstants.DirectorateId.DCA,classificationName,frequency,"N",dataEntryLevel);
		}
		obj.put("dcaDirectorateRecords", dcaDirectorateRecords);
		obj.put("genearlCategoryList", json);
		data.put(obj);
		out.println(obj);
		log.debug(this.getClass().getName() + "- Exit ");
	}finally{}
		
	}
	
	@RequestMapping(value="/dcasave", method=RequestMethod.POST)
	public @ResponseBody void doSave(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, ServletException, JSONException{
		
		PrintWriter out = response.getWriter();
			log.debug(this.getClass().getName() + "- Entering ");
			Integer hudId=(Util.isNotNull(request.getParameter("hudId"))?Integer.parseInt(request.getParameter("hudId")):null);
			Integer blockId=(Util.isNotNull(request.getParameter("blockId"))?Integer.parseInt(request.getParameter("blockId")):null);
			Integer institutionId=(Util.isNotNull(request.getParameter("institutionId"))?Integer.parseInt(request.getParameter("institutionId")):null);
			String classificationName = request.getParameter("classificationName");
			String frequency = request.getParameter("frequency");
			String week = request.getParameter("week");
			String quarter = request.getParameter("quarter");
			String month = request.getParameter("month");
			Integer year=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			String searchDate = request.getParameter("searchDate");
			String hudName = request.getParameter("hudName");
			String blockName = request.getParameter("blockName");
			
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray gridRecords = new JSONArray(handsOnTableData);
			String dataEntryLevel = getDataEntryLevel(hudId,hudName,blockId,blockName,institutionId);
			
			boolean successFlag =dcaDataEntryManager.insertDcaDirectorateRecords(hudId,blockId,institutionId,dataEntryLevel,classificationName,frequency,week,quarter,
						searchDate, month, year,gridRecords);
			if(successFlag)
				out.println("The record has been saved successfully");
			else
				out.println("Save Error");
			    log.debug(this.getClass().getName() + "- Exit ");
	}
	
	@RequestMapping(value="/dcaupdate", method=RequestMethod.POST)
	public @ResponseBody void doUpdate(HttpServletRequest request,HttpServletResponse response) throws SHDRCException,IOException, ServletException, JSONException{
		try{
			log.debug(this.getClass().getName() + "- Entering ");
			PrintWriter out = response.getWriter();
			String handsOnTableData=request.getParameter("handsOnTableData");
			JSONArray jsonArray = new JSONArray(handsOnTableData);
			boolean successFlag = dcaDataEntryManager.updateDcaDirectorateRecords(jsonArray);
			if(successFlag)
				out.println("The record has been updated successfully");
			else
				out.println("Save Error");
			    log.debug(this.getClass().getName() + "- Exit ");
		}finally{}	
	}
	
	private String getDataEntryLevel(Integer hudId,String hudName,Integer blockId,String blockName,Integer institutionId){
		String indicatorHierarchy=null;
		try{
			log.debug(this.getClass().getName() + "- Entering ");
		if(hudId!=null
				&& hudName.equalsIgnoreCase("Headquarters") 
				&& blockName.equalsIgnoreCase("NA"))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELZONEHEADQUARTERS;
		else if(hudId!=null
				&& hudName.equalsIgnoreCase("Headquarters") 
				&& !(blockName.equalsIgnoreCase("NA")))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELRANGEHEADQUARTERS;
		else if(hudId!=null
				&& hudName.equalsIgnoreCase("Mobilesquad") 
				&& blockName.equalsIgnoreCase("NA"))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELZONEMOBILESQUAD;
		else if(hudId!=null
				&& hudName.equalsIgnoreCase("Mobilesquad") 
				&& !(blockName.equalsIgnoreCase("NA")))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELRANGEMOBILESQUAD;
		else if(institutionId!=null 
				&& hudName.equalsIgnoreCase("NA"))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELLAB;
		else if(hudId!=null
				&& hudName.contains("Zone") 
				&& blockName.equalsIgnoreCase("NA"))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELZONE;
		else if(hudId!=null
				&& hudName.contains("Zone") 
				&& !(blockName.equalsIgnoreCase("NA")))				
			indicatorHierarchy=ShdrcConstants.DATAENTRYLEVELRANGE;
		log.debug(this.getClass().getName() + "- Exit ");
		}catch(Exception e){
			log.error(this.getClass().getName() + "-getDataEntryLevel "+e.getMessage());
		}finally{}
		return indicatorHierarchy;
	}
	
}
