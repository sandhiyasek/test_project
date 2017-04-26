package gov.shdrc.servicemaintenance.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.shdrc.servicemaintenance.service.IDistrictMasterManager;
import gov.shdrc.servicemaintenance.util.DistrictMaster;
import gov.shdrc.util.CSRFTokenUtil;
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
public class DistrictMasterController {
	@Autowired(required=true)
	IDistrictMasterManager districtMasterManager;
	
	@RequestMapping(value="/DistrictMasterMaintenance", method=RequestMethod.GET)
	public ModelAndView onLoad(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("districtMasterMaintenance");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			String submitType = request.getParameter("submitType");
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			
			List<DistrictMaster> districtDetailsList=districtMasterManager.getDistrictDetailsList();
		    modelView.addObject("districtDetailsList", districtDetailsList);
		
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/DistrictMasterAdd", method=RequestMethod.GET)
	public ModelAndView onLoadDistrictMasterAdd(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("districtMasterAdd");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			List<Integer> yearList= Util.yearList;
			modelView.addObject("yearList", yearList);
			
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/DistrictMasterChange", method=RequestMethod.GET)
	public ModelAndView onLoadDistrictMasterChange(HttpServletRequest request) throws NoSuchAlgorithmException, ServletException{
		ModelAndView modelView=null;
		String csrfToken =null;
		try{
		    modelView=new ModelAndView();
			modelView.setViewName("districtMasterChange");
			
			csrfToken = CSRFTokenUtil.getToken (request.getSession());
			modelView.addObject("CSRF_TOKEN", csrfToken);
			modelView.addObject("currentHours", Util.getCurrentHours());
			modelView.addObject("currentDate", Util.getStrDate(new Date()));
			
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			
			DistrictMaster districtDetails =districtMasterManager.getDistrictDetails(districtId);
			List<Integer> yearList= Util.yearList;
			modelView.addObject("yearList", yearList);
			modelView.addObject("districtId", districtId);
			modelView.addObject("districtDetails", districtDetails);
		
		}finally{}
		return modelView;
	}
	
	@RequestMapping(value="/DistrictMasterAddOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void addDistrictMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			String districtName=request.getParameter("districtName");
			String districtHeadQuarters=request.getParameter("districtHeadQuarters");
			String state=request.getParameter("state");
			// Integer active=(Util.isNotNull(request.getParameter("districtActive"))?Integer.parseInt(request.getParameter("districtActive")):null);
			Integer yearOfPopulationCount=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			Integer population=(Util.isNotNull(request.getParameter("population"))?Integer.parseInt(request.getParameter("population")):null);
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String userName = UserInfo.getLoggedInUserName();
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
		    validateFields(districtName,yearOfPopulationCount,population);	
			successFlag=districtMasterManager.insertDistrict(districtName,districtHeadQuarters,state,yearOfPopulationCount,population,userName);
			if(successFlag)
		        	out.println("District has been added successfully");
				else
					out.println("Problem in adding the District");
			
	}finally{}
	}
	
	@RequestMapping(value="/DistrictMasterChangeOnSubmit", method=RequestMethod.POST)
	public @ResponseBody void changeDistrictMaster(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException, NoSuchAlgorithmException, ServletException, gov.shdrc.exception.SHDRCException, SHDRCException{
		PrintWriter out = response.getWriter();
		boolean successFlag=false;
		try{
			String districtName=request.getParameter("districtName");
			String districtHeadQuarters=request.getParameter("districtHeadQuarters");
			String state=request.getParameter("state");
			// Integer active=(Util.isNotNull(request.getParameter("districtActive"))?Integer.parseInt(request.getParameter("districtActive")):null);
			Integer yearOfPopulationCount=(Util.isNotNull(request.getParameter("year"))?Integer.parseInt(request.getParameter("year")):null);
			Integer population=(Util.isNotNull(request.getParameter("population"))?Integer.parseInt(request.getParameter("population")):null);
			Integer districtId=(Util.isNotNull(request.getParameter("districtId"))?Integer.parseInt(request.getParameter("districtId")):null);
			Integer directorateId=(Util.isNotNull(request.getParameter("directorateId"))?Integer.parseInt(request.getParameter("directorateId")):null);
			String userName = UserInfo.getLoggedInUserName();
			boolean  isvalid=false; 
			isvalid = CSRFTokenUtil.isValid (request);
		    validateFields(districtName,yearOfPopulationCount,population);	
		    successFlag=districtMasterManager.updateDistrict(districtId,districtName,districtHeadQuarters,yearOfPopulationCount,population); 
		    if(successFlag)
		        out.println("District details have been changed successfully");
			else
				out.println("Problem in changing the District details");
			
			
	}finally{}
	}
	
	private void validateFields(String districtName,Integer yearOfPopulationCount,Integer population )throws SHDRCException{
		if(Util.isNullOrBlank(districtName)){
			throw new SHDRCException("Please Enter the name of the District");
		}
		if(Util.isNullOrBlank(yearOfPopulationCount)){
			throw new SHDRCException("Please select the year in which population count was taken");
		}
		if(Util.isNullOrBlank(population)){
			throw new SHDRCException("Please enter the population of the District");
		}
		
	}
}
