
package gov.shdrc.home.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.home.service.IStatisticsManager;
import gov.shdrc.util.CSRFTokenUtil;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
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
public class StatisticsController {
	private static Logger logger=Logger.getLogger(StatisticsController.class);
	@Autowired
	IStatisticsManager statisticsManager;
	@Autowired
	ICommonManager commonManager;

	@RequestMapping(value="/statistics", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request)throws ServletException, IOException{	
		ModelAndView modelView=new ModelAndView();
		modelView.setViewName("indStatistics");		
		
		List<String> userRoleList = UserInfo.getUserRoleList(); 
		boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
		String role = null;
		if(!isAllDirectorate)
			role = UserInfo.getRolesForUser();		
    	JSONArray jsonArray = Util.getColumnHearders();
		String encodedJsonArray = encodeJSONArray(jsonArray.toString());
		modelView.addObject("encodedJsonArray", encodedJsonArray);
        
        String csrfToken =null;
		 try {
			 csrfToken = CSRFTokenUtil.getToken (request.getSession());
			 modelView.addObject("CSRF_TOKEN", csrfToken);
			} catch (NoSuchAlgorithmException e) {
				//e.printStackTrace();
				logger.error("error occured onLoad method in StatisticsController"+e);
			}
		 
		 List<CommonStringList> directorateList=null;
		 if("'Chief Ministers Comprehensive Health Insurance Scheme'".equalsIgnoreCase(role)){			
			CommonStringList commonStringList = new CommonStringList();
			directorateList=new ArrayList<CommonStringList>();
			commonStringList.setId(17);
	        commonStringList.setName("Chief Ministers Comprehensive Health Insurance Scheme");
	        directorateList.add(commonStringList);			
		 }
		 else{
			directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		 }
		 modelView.addObject("directorateList", directorateList);
        
        JSONArray directorateServletList= commonManager.getDirecorateServletList();
        modelView.addObject("directorateServletList", encodeJSONArray(directorateServletList.toString()));
        
        Integer directorateId;
        if(Util.isNotNull(directorateList) && directorateList.size()>0){
        	CommonStringList directorate = directorateList.get(0);
        	directorateId=directorate.getId();

			List<String> indicatorList=statisticsManager.getindicatorList(directorateId);
			modelView.addObject("indicatorList", indicatorList);
        }
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        modelView.addObject("year", year);
		return modelView;
	}
	
	@RequestMapping(value="/statisticsDirectorateChange", method=RequestMethod.POST)
	public @ResponseBody void getStatIndList(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
		PrintWriter out = response.getWriter();
		try{		
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);		
			 List<String> indicatorList=statisticsManager.getindicatorList(directorateId);
			 String json=new Gson().toJson(indicatorList);  
			 out.println(json);
		}finally{}
	}
	
	@RequestMapping(value="/indicatorStatistics", method=RequestMethod.POST)
	public @ResponseBody void getIndStatistics(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException{	
		PrintWriter out = response.getWriter();
		JSONObject jsonObject=new JSONObject();
		JSONArray data=new JSONArray();
		try{	
			String indName=request.getParameter("indName");
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			JSONArray indStatisticsData=statisticsManager.getIndStatisticsData(directorateId,indName);
			JSONArray indDataAvailabity=statisticsManager.getIndDataAvailability(directorateId,indName);
			jsonObject.put("indStatisticsData", indStatisticsData);
 			jsonObject.put("indDataAvailabity",indDataAvailabity);	 
 			data.put(jsonObject);
 			out.println(jsonObject);
            
		}finally{}
	}
	
	private String encodeJSONArray(String jsonArray){
		 String encodedJsonArray=null;
		try {
			encodedJsonArray = URLEncoder.encode(jsonArray, "UTF-8")   
					   .replaceAll("\\%28", "(")                          
					   .replaceAll("\\%29", ")")   		
					   .replaceAll("\\+", "%20")                          
					   .replaceAll("\\%27", "'")   			   
					   .replaceAll("\\%21", "!")
					   .replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			logger.error("error occured encodeJSONArray method in StatisticsController"+e);
		}
		 return encodedJsonArray;		
	}	 

}
