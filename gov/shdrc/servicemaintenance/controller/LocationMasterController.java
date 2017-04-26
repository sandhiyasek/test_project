package gov.shdrc.servicemaintenance.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.servicemaintenance.service.ILocationMasterManager;
import gov.shdrc.servicemaintenance.util.LocationMaster;
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

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
public class LocationMasterController {
	@Autowired(required=true)
	ILocationMasterManager iLocationMasterManager;
	@Autowired(required=true)
	ICommonManager commonManager;
	
	@RequestMapping(value="/LocationMasterMaintenance", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("locationMasterMaintenance");
			
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
  
		        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		        modelView.addObject("directorateList", directorateList);
		        
		        List<CommonStringList> districtList= commonManager.getDistricts(userName);
		        modelView.addObject("districts", districtList);
		        
		        modelView.addObject("directorateId", directorateId);
				List<LocationMaster> locationDetailsList=iLocationMasterManager.getLocationDetailsList(directorateId);
				modelView.addObject("locationDetailsList", locationDetailsList);
		
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/LocationMasterAdd", method=RequestMethod.GET)
	public ModelAndView onLoadLocationMasterAdd(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("locationMasterAdd");
			
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
  
		        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		        modelView.addObject("directorateList", directorateList);
		        
		        List<CommonStringList> districtList= commonManager.getDistricts(userName);
		        modelView.addObject("districts", districtList);
		        
		        String hudType=iLocationMasterManager.getHUDType(directorateId);
				 List<CommonStringList> hudList= iLocationMasterManager.getHudNameList(directorateId,hudType);
			    modelView.addObject("hudList", hudList);
				modelView.addObject("hudType", hudType);
				modelView.addObject("directorateId", directorateId);
			
		}finally{}
		return modelView;
	}
	
	/* @RequestMapping(value="/LocationMasterChange", method=RequestMethod.GET)
	public ModelAndView onLoadLocationMasterChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		try{
			modelView.setViewName("locationMasterChange");
			
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
  
		        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		        modelView.addObject("directorateList", directorateList);
		        
		        List<CommonStringList> districtList= commonManager.getDistricts(userName);
		        modelView.addObject("districts", districtList);
		
		}finally{}
		return modelView; 
	} */
	
	@RequestMapping(value="/LocationMasterAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void addLocationMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			
			Integer districtId=(Util.isNotNull(request.getParameter("districtName"))?Integer.parseInt(request.getParameter("districtName")):null);
			Integer hudId=(Util.isNotNull(request.getParameter("hudName"))?Integer.parseInt(request.getParameter("hudName")):null);
			String hudType=request.getParameter("hudType");
			String talukName=request.getParameter("talukName");
			String blockName=request.getParameter("blockName");
			String userName = UserInfo.getLoggedInUserName();

			Integer directorateId=(Util.isNotNull(request.getParameter("directorateName"))?Integer.parseInt(request.getParameter("directorateName")):null);
			successFlag=iLocationMasterManager.insertLocation(directorateId,districtId,hudId,hudType,talukName,blockName,userName);        
	        if(successFlag)
	        	out.println("Location has been added successfully");
			else
				out.println("Problem in adding the Location");
	}finally{}
	}
	
	/*@RequestMapping(value="/LocationMasterChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void changeLocationMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			
			
	}finally{}
	}*/
	
	@RequestMapping(value="/locationMasterDirectorateChange", method=RequestMethod.POST)
	public @ResponseBody void changeLocationMasterDirectorate(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			Integer districtId=(Util.isNotNull(request.getParameter("districtName"))?Integer.parseInt(request.getParameter("districtName")):null);
			Integer hudId=(Util.isNotNull(request.getParameter("hudName"))?Integer.parseInt(request.getParameter("hudName")):null);
			String hudType=request.getParameter("hudType");
			String talukName=request.getParameter("talukName");
			String blockName=request.getParameter("blockName");
			String userName = UserInfo.getLoggedInUserName();
			
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			hudType=iLocationMasterManager.getHUDType(directorateId);
			List<CommonStringList> hudList= iLocationMasterManager.getHudNameList(directorateId,hudType);
			String json=new Gson().toJson(hudList);  
			out.println(json);
			
	}finally{}
	}
}
