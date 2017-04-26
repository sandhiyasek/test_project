/*package gov.shdrc.home.controller;

import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.home.service.IHomeManager;
import gov.shdrc.reports.service.ISDGSManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.UserInfo;
import gov.shdrc.util.Util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

*//**
 * @author lakshmi
 *
 *//*




		@Controller
		public class RPredictionController {
			@Autowired
			ICommonManager commonManager;

			@Autowired(required=true)
			IHomeManager homeManager;
			@Autowired(required=true)
			ISDGSManager SDGSManager;
			
			@RequestMapping(value="/shdrcPredictionSlider", method=RequestMethod.GET)
			public ModelAndView onLoad(HttpServletRequest request){			
			   ModelAndView modelView=null;
			   

			   try { 
				   String userName = UserInfo.getLoggedInUserName();
				    modelView=new ModelAndView();
				    modelView.setViewName("shdrcPredictionSliderAction");
					
				    
				    List<CommonStringList> districtList= commonManager.getDistricts(userName);
			        modelView.addObject("districts", districtList);
				    
			        List<CommonStringList> monthsList= Util.monthsList;
			        System.out.println("monthsList"+monthsList);
			        modelView.addObject("monthsList", monthsList);
				    
					} catch (Exception e) {
						e.printStackTrace();
					}finally{			
					} 
			   return modelView;
			}	
		

	}*/

