package gov.shdrc.sms;


import gov.shdrc.dataentry.service.ICmchisDataEntryManager;
import gov.shdrc.dataentry.service.ICommonManager;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.SplCharsConstants;
import gov.shdrc.util.Util;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class SmsAlertCronJob implements Job{
	final static Logger logger = Logger.getLogger(SmsAlertCronJob.class);
	@Autowired
	ICommonManager commonManager;
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/*System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", "10.236.3.21");
		System.setProperty("http.proxyPort", "8002");*/
		try {
			List<SmsAlert> smsAlertList = commonManager.getSmsAlertList();
			if(smsAlertList!=null && smsAlertList.size()>0){
				SMSSender smsSender=new SMSSender();
				CommonStringList smsStatus =null;
				for(SmsAlert smsAlert : smsAlertList ){
					StringBuilder smsBody= new StringBuilder();
					smsBody.append("Indicator value is "+smsAlert.getThresholdComparator()+" Threshold"+SplCharsConstants.COMMA);
					smsBody.append(smsAlert.getDirectorateName()+SplCharsConstants.EMPTY+SplCharsConstants.HYPEN+SplCharsConstants.EMPTY);
					smsBody.append(smsAlert.getDrillDownLevlName()+SplCharsConstants.HYPEN);
					smsBody.append(smsAlert.getHierarchyName()+SplCharsConstants.COMMA);
					smsBody.append(smsAlert.getMonth()+SplCharsConstants.HYPEN+smsAlert.getYear()+SplCharsConstants.COMMA);
					smsBody.append("Indicator Name"+SplCharsConstants.COLON+smsAlert.getIndicatorName()+SplCharsConstants.COMMA);
					smsBody.append("Current Value"+SplCharsConstants.COLON+smsAlert.getIndicatorValue()+SplCharsConstants.COMMA);
					smsBody.append("Target Value"+SplCharsConstants.COLON+smsAlert.getTargetValue());
					smsStatus = smsSender.sendSMS(smsAlert.getMobileNo().toString(), smsBody.toString(),"Single");
					if(Util.isNotNull(smsStatus) && Util.isNotNull(smsStatus.getName())){
						commonManager.updateSmsAlert(smsAlert.getSmsId());
					}
				}		
			}//System.out.println("SmsAlertCronJob*********Existing into SmsAlertCronJob");
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MailSMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
