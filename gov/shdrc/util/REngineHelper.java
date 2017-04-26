package gov.shdrc.util;

import gov.shdrc.dataentry.controller.DmsDataEntryController;

import org.apache.log4j.Logger;
import org.rosuda.JRI.Rengine;

public class REngineHelper {
	private static Logger log=Logger.getLogger(REngineHelper.class);
	private static Rengine rengineIMRHelper=null;
	private static Rengine rengineMMRHelper=null;
	private REngineHelper(){
	}
	public static Rengine getIMRInstance(){
	 try{	
		if(rengineIMRHelper==null){	
			System.out.println("Starting Rengine..");
			System.out.println("R_HOME =" + System.getenv("R_HOME"));
			System.out.println("java.library.path =" + System.getProperty("java.library.path"));
			log.error("Starting Rengine");
			log.error("CATALINA_HOME ="+System.getenv("CATALINA_HOME"));
			log.error("JAVA_HOME ="+System.getenv("JAVA_HOME"));
			log.error("R_HOME ="+System.getenv("R_HOME"));
			log.error("java.library.path =+"+ System.getProperty("java.library.path"));
			rengineIMRHelper= new Rengine(new String[] { " --save " }, true, null);
			rengineIMRHelper.eval("source(\"C:/SHDRC/shiny_imr/negbin_imr.R\")");
			//rengineIMRHelper.eval("source(\"home/pentaho/R_Source_Files/shiny_imr/negbin_imr.R\")");
		}
	 }catch(Exception e){
		 e.printStackTrace();
	 }	
		return rengineIMRHelper;
	}
	
	
	public static Rengine getMMRInstance(){
		if(rengineMMRHelper==null){	
			rengineMMRHelper= new Rengine(new String[] { " --save " }, true, null);
			rengineMMRHelper.eval("source(\"home/pentaho/R_Source_Files/shiny_mmr/mmr_load.R\")");
		}
		return rengineMMRHelper;
	}
}
