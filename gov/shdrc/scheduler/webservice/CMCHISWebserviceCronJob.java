package gov.shdrc.scheduler.webservice;


import gov.shdrc.dataentry.dao.ICmchisDataEntryDAO;
import gov.shdrc.dataentry.dao.jdbc.CmchisDataEntryDAOJdbc;
import gov.shdrc.webservice.cmchis.client.ServiceSHDRC;
import gov.shdrc.webservice.reader.NewDataSet;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CMCHISWebserviceCronJob implements Job{
	
	@Autowired(required=true)
	ICmchisDataEntryDAO cmchisDataEntryDAO;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Entering into execute mentod in CMCHISWebserviceCronJob");
		/*System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", "10.236.3.21");
		System.setProperty("http.proxyPort", "8002");*/
		ServiceSHDRC serviceSHDRC = new ServiceSHDRC();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String previousDate=getStrDate(cal.getTime());
		String sharePreauthDetailDs=serviceSHDRC.getServiceSHDRCSoap().preauthDetails(previousDate);
		String shareClaimsDetailDs=serviceSHDRC.getServiceSHDRCSoap().claimsDetails(previousDate);
		String hospitalMaster=serviceSHDRC.getServiceSHDRCSoap().hospitalDetails();
		String paymentDetails=serviceSHDRC.getServiceSHDRCSoap().paymentDetails(previousDate);
		String dcDetails=serviceSHDRC.getServiceSHDRCSoap().dcDetails(previousDate);
		cmchisDataEntryDAO=new CmchisDataEntryDAOJdbc();
		//String xmlString ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><employee id=\"100\" xmlns=\"http://claim.cmchistn.com/SHDRC/\"><employeeid>1</employeeid><employeename>sudhakar</employeename><age>20</age></employee>";
		sharePreauthDetailDs="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+sharePreauthDetailDs;
		shareClaimsDetailDs="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+shareClaimsDetailDs;
		hospitalMaster="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+hospitalMaster;
		paymentDetails="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+paymentDetails;
		dcDetails="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+dcDetails;
		//System.out.println("sharePreauthDetailDs*********"+sharePreauthDetailDs);
		//System.out.println("shareClaimsDetailDs*********"+shareClaimsDetailDs);
		boolean preauthStatus=insertWebservicPreAuthData(sharePreauthDetailDs,"Preauth");
		boolean claimsStatus=insertWebservicClaimsAuthData(shareClaimsDetailDs,"Claims");
		boolean paymentStatus=insertWebservicPaymentData(paymentDetails,"Payment");
		boolean dcStatus=insertWebservicDCData(dcDetails,"DC");
		boolean hospitalStatus=insertHospitalList(hospitalMaster,"Hospital");
		System.out.println("Existing into execute mentod in CMCHISWebserviceCronJob");
       
	}
	
	private boolean insertWebservicPreAuthData(String webserviceXml,String columnHeaders){
		 JAXBContext jaxbContext=null;
		 boolean status=false;
			try {
				jaxbContext = JAXBContext.newInstance(NewDataSet.class);
				  Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        StreamSource xmlSource = new StreamSource(new StringReader(webserviceXml));
			        NewDataSet newDataSet = (NewDataSet)unmarshaller.unmarshal(xmlSource);
			        List<NewDataSet.Shrdcpa> cmchisPreAuthDataList=newDataSet.getShrdcpa();
			        
			        status=cmchisDataEntryDAO.insertWebservicPreAuthData(cmchisPreAuthDataList,columnHeaders);
			} catch(Exception e){
				e.printStackTrace();
			}
			 return status;
	}
	private boolean insertWebservicClaimsAuthData(String webserviceXml,String columnHeaders){
		 JAXBContext jaxbContext=null;
		 boolean status=false;
			try {
				jaxbContext = JAXBContext.newInstance(NewDataSet.class);
				  Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        StreamSource xmlSource = new StreamSource(new StringReader(webserviceXml));
			        NewDataSet newDataSet = (NewDataSet)unmarshaller.unmarshal(xmlSource);
			        List<NewDataSet.Shrdccl> cmchisclaimsDataList=newDataSet.getShrdccl();
			        status=cmchisDataEntryDAO.insertWebservicClaimsAuthData(cmchisclaimsDataList,columnHeaders);
			}catch(Exception e){
				e.printStackTrace();
			}
			 return status;
	}
	private boolean insertWebservicPaymentData(String webserviceXml,String columnHeaders){
		 JAXBContext jaxbContext=null;
		 boolean status=false;
			try {
				jaxbContext = JAXBContext.newInstance(NewDataSet.class);
				  Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        StreamSource xmlSource = new StreamSource(new StringReader(webserviceXml));
			        NewDataSet newDataSet = (NewDataSet)unmarshaller.unmarshal(xmlSource);
			        List<NewDataSet.Shrdcpayment> cmchisPaymentDataList=newDataSet.getShrdcpayment();
			        status=cmchisDataEntryDAO.insertWebservicPaymentData(cmchisPaymentDataList,columnHeaders);
			}catch(Exception e){
				e.printStackTrace();
			}
			 return status;
	}
	private boolean insertWebservicDCData(String webserviceXml,String columnHeaders){
		 JAXBContext jaxbContext=null;
		 boolean status=false;
			try {
				jaxbContext = JAXBContext.newInstance(NewDataSet.class);
				  Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        StreamSource xmlSource = new StreamSource(new StringReader(webserviceXml));
			        NewDataSet newDataSet = (NewDataSet)unmarshaller.unmarshal(xmlSource);
			        List<NewDataSet.Shrdcdccl> cmchisDCDataList=newDataSet.getShrdcdccl();
			        status=cmchisDataEntryDAO.insertWebservicDCData(cmchisDCDataList,columnHeaders);
			} catch(Exception e){
				e.printStackTrace();
			}
			 return status;
	}
	private boolean insertHospitalList(String webserviceXml,String columnHeaders){
		 JAXBContext jaxbContext=null;
		 boolean status=false;
			try {
				jaxbContext = JAXBContext.newInstance(NewDataSet.class);
				  Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			        StreamSource xmlSource = new StreamSource(new StringReader(webserviceXml));
			        NewDataSet newDataSet = (NewDataSet)unmarshaller.unmarshal(xmlSource);
			        List<NewDataSet.Shrdchosp> cmchisHospitalList=newDataSet.getShrdchosp();
			        status=cmchisDataEntryDAO.insertHospitalList(cmchisHospitalList); 
			}catch(Exception e){
				e.printStackTrace();
			}
			 return status;
	}
	private String getStrDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}
	public static void main(String arg[]){
		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		System.out.println("Yesterday's date = "+ cal.getTime());*/
		Calendar cal = Calendar.getInstance();
		cal.set(2016, 00, 01);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(formatter.format(cal.getTime()));
	}
}
