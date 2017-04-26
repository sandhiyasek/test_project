package gov.shdrc.reports.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import gov.shdrc.reports.service.IIndicatorZoneManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndicatorZoneController {
	
@Autowired(required=true)
IIndicatorZoneManager indicatorZoneManager;

@RequestMapping(value="/indicatorZone", method=RequestMethod.GET)
public ModelAndView onLoadPivotTable(HttpServletRequest request)
{	
	ModelAndView modelView=null;
	SimpleDateFormat sdf=null;
	CommonStringList commonStringList=null;
	String maxMonth=null;
	int maxYear=0;
	int year=0;
	Map<String, Integer> monthsMap =null;
	Calendar cal=null;
	String currentMonth=null;
	String previousMonth=null;
	String previousMonth1=null;
	List<CommonStringList> monthsList=null;
	String ind = null;
	try{
		modelView = new ModelAndView();
		modelView.setViewName("indicatorZone");
		sdf=new SimpleDateFormat("MMMM");
		commonStringList=new CommonStringList();
		commonStringList.setId(2016);
		commonStringList.setName("April");
		maxMonth=commonStringList.getName();
		maxYear=commonStringList.getId();
		monthsMap = Util.monthsMap;
		cal=Calendar.getInstance();
		cal.set(maxYear, monthsMap.get(maxMonth),1);
		currentMonth=sdf.format(cal.getTime());
		cal.set(maxYear, monthsMap.get(maxMonth)-1,1);
		previousMonth=sdf.format(cal.getTime());
		cal.set(maxYear, monthsMap.get(maxMonth)-2,1);
		previousMonth1=sdf.format(cal.getTime());
		currentMonth = currentMonth.substring(0, 3);
		previousMonth = previousMonth.substring(0, 3);
		previousMonth1 = previousMonth1.substring(0, 3);
		year=2015;
		monthsList= Util.monthsList;
		modelView.addObject("monthsList", monthsList);
		modelView.addObject("currentMonth", maxMonth);
		modelView.addObject("currentYear", maxYear);
        String indicators = request.getParameter("indicators");
        ind = indicators==null?"Disease Outbreak":indicators;
        modelView.addObject("indData", indicatorZoneManager.indzone(ind,year,previousMonth));
	}catch(Exception exp){
		
	}finally{
		sdf=null;
		commonStringList=null;
		maxMonth=null;
		monthsMap =null;
		cal=null;
		currentMonth=null;
		previousMonth=null;
		previousMonth1=null;
		monthsList=null;
		ind = null;
	}
	return modelView;
}
}
