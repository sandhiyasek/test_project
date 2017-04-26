package gov.shdrc.webservice.cmchis.client;

import gov.shdrc.exception.SHDRCException;
import gov.shdrc.webservice.reader.NewDataSet;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;


public class Test1 {
	public static void main(String[] arg) throws ParserConfigurationException, SAXException, IOException, SHDRCException {
		System.out.println("Entering into Main method*****");
/*		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", "10.236.245.251");
		System.setProperty("http.proxyPort", "9191");*/
		ServiceSHDRC serviceSHDRC = new ServiceSHDRC();
		//String xmlString=serviceSHDRC.getServiceSHDRCSoap().sharePreauthDetailDs("12/02/2016");
		String claimsDetail=serviceSHDRC.getServiceSHDRCSoap().claimsDetails("12/02/2016");
	//	String hospitalMaster=serviceSHDRC.getServiceSHDRCSoap().hospitalMasters();
		System.out.println(claimsDetail);
		
		
		//String xmlString ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><employee id=\"100\" xmlns=\"http://claim.cmchistn.com/SHDRC/\"><employeeid>1</employeeid><employeename>sudhakar</employeename><age>20</age></employee>";
/*		xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+xmlString;
		claimsDetail="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+claimsDetail;
		System.out.println("xmlString*****"+xmlString);
		System.out.println("claimsDetail*****"+claimsDetail);*/
        JAXBContext jc;
        System.out.println("Existing into Main method*****");
		try {
		/*	jc = JAXBContext.newInstance(NewDataSet.class);
			  Unmarshaller unmarshaller = jc.createUnmarshaller();
		        StreamSource xmlSource = new StreamSource(new StringReader(xmlString));
		        NewDataSet newDataSet = (NewDataSet)unmarshaller.unmarshal(xmlSource);
		        List<NewDataSet.Shrdc> cmchisDataList=newDataSet.getShrdc();
		        CMCHISDirectorateDAO cmchisDirectorateDAO = new CMCHISDirectorateDAO();
		        boolean status=cmchisDirectorateDAO.insertCmchisWebserviceData(cmchisDataList);
		        for(NewDataSet.Shrdc cmchis:shdrc){
		        	 System.out.println("PatientName"+cmchis.getPatientName());
		        }	       */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public static void main(String[] arg) throws ParserConfigurationException, SAXException, IOException, SHDRCException {
		CMCHISDirectorateDAO cmchisDirectorateDAO = new CMCHISDirectorateDAO();
		cmchisDirectorateDAO.getInstitutionList(12, "cmchis");
	}*/

}
