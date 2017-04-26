package gov.shdrc.servicemaintenance.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.servicemaintenance.service.IHUDMasterManager;
import gov.shdrc.servicemaintenance.util.HUDMaster;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SHDRCException;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HUDMasterController {
	@Autowired(required=true)
	IHUDMasterManager hudMasterManager;
	@Autowired(required=true)
	ICommonManager commonManager;
	
	@RequestMapping(value="/HUDMasterMaintenance", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("hudMasterMaintenance");
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);   
			 
		        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		        modelView.addObject("directorateList", directorateList);
				List<HUDMaster> hudDetailsList=hudMasterManager.getHudDetailsList(directorateId);
				modelView.addObject("hudDetailsList", hudDetailsList);
				modelView.addObject("directorateId", directorateId);
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/HUDMasterAdd", method=RequestMethod.GET)
	public ModelAndView onLoadHUDMasterAdd(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("hudMasterAdd");
			
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);   
			 
		        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		        modelView.addObject("directorateList", directorateList);
		        
		        String hudType=hudMasterManager.getHUDType(directorateId);
				modelView.addObject("hudType", hudType);
				modelView.addObject("directorateId", directorateId);

			
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/HUDMasterChange", method=RequestMethod.GET)
	public ModelAndView onLoadHUDMasterChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("hudMasterChange");
			
			String userName = UserInfo.getLoggedInUserName();
			List<String> userRoleList = UserInfo.getUserRoleList(); 
			boolean isAllDirectorate =  UserInfo.isAllDirectorate(userRoleList);
			String role = null;
			if(!isAllDirectorate)
				role = UserInfo.getRolesForUser();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);   
			 
		        List<CommonStringList> directorateList= commonManager.getDirecorateList(isAllDirectorate,role);
		        modelView.addObject("directorateList", directorateList);
		        
		        Integer hudId=(Util.isNotNull(request.getParameter("hudId"))?Integer.parseInt(request.getParameter("hudId")):null);
				
				HUDMaster hudDetails =hudMasterManager.getHudDetails(hudId,directorateId);
				modelView.addObject("hudId", hudId);
				modelView.addObject("directorateId", directorateId);
				modelView.addObject("hudDetails", hudDetails);
		
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/HUDMasterAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void addHUDMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			
			String userName = UserInfo.getLoggedInUserName();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateName"))?Integer.parseInt(request.getParameter("directorateName")):null);
			String hudName=request.getParameter("hudName");
			String hudType=request.getParameter("hudType");
			String hudGroup=request.getParameter("hudGroup");
			Integer hudId=(Util.isNotNull(request.getParameter("hudId"))?Integer.parseInt(request.getParameter("hudId")):null);
				successFlag=hudMasterManager.insertHUD(directorateId,hudName,hudType,hudGroup,userName);        
	        if(successFlag)
	        	out.println("HUD has been added successfully");
			else
				out.println("Problem in adding the HUD");
			
			
	}finally{}
	}
	
	@RequestMapping(value="/HUDMasterChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void changeHUDMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			String userName = UserInfo.getLoggedInUserName();
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateName"))?Integer.parseInt(request.getParameter("directorateName")):null);
			String hudName=request.getParameter("hudName");
			String hudType=request.getParameter("hudType");
			String hudGroup=request.getParameter("hudGroup");
			Integer hudId=(Util.isNotNull(request.getParameter("hudId"))?Integer.parseInt(request.getParameter("hudId")):null);
			
			successFlag=hudMasterManager.updateHUD(directorateId,hudId,hudName,hudType,hudGroup);        
	        if(successFlag)
	        	out.println("HUD details have been changed successfully");
			else
				out.println("Problem in changing the HUD details");
			
	}finally{}
	}
}
