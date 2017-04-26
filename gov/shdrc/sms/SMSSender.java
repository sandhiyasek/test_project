package gov.shdrc.sms;

import gov.shdrc.home.dao.jdbc.CommonDAO;
import gov.shdrc.util.CommonStringList;
import gov.shdrc.util.MailSMSConstants;
import gov.shdrc.util.Util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

public class SMSSender{
	
	private Properties smsProperties;
	private static HttpURLConnection connection = null;

	public SMSSender(){
		try {
			smsProperties = new Properties();
			InputStream inputStream = SMSSender.class.getClassLoader().getResourceAsStream("sms.properties");
			smsProperties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	/*public CommonStringList sendSMS(Long phoneNos,String smsBody) throws MailSMSException, UnsupportedEncodingException{
			if(phoneNos == null){
				throw new MailSMSException("Sending SMS Failed - To PhoneNos is empty");
			}
			
			if(Util.isNullOrBlank(smsBody.toString())){
				throw new MailSMSException("Sending SMS Failed - Message is empty");	
			}
			if(smsProperties!=null){
				String isSmsServiceEnabled=smsProperties.getProperty(MailSMSConstants.SMSProperties.ISSMSSERVICEENABLED);
				if(isSmsServiceEnabled==null || "N".equalsIgnoreCase(isSmsServiceEnabled))
					throw new MailSMSException("Sending SMS Failed - Enable SMS Properties");	
				smsBody = URLEncoder.encode(smsBody, "UTF-8");
				StringBuilder weburl = new StringBuilder();
				weburl.append(smsProperties.getProperty(MailSMSConstants.SMSProperties.SERVERURL) + "?");
				weburl.append("user="+smsProperties.getProperty(MailSMSConstants.SMSProperties.USER));
				weburl.append("&pwd="+smsProperties.getProperty(MailSMSConstants.SMSProperties.PASSWORD));
				weburl.append("&to="+phoneNos);
				weburl.append("&sid="+smsProperties.getProperty(MailSMSConstants.SMSProperties.SENDERID));
				weburl.append("&msg="+smsBody);
				weburl.append("&fl=0");
				
				//weburl.append("http://login.smsgatewayhub.com/smsapi/pushsms.aspx?user=ecsudhakar&pwd=894692&to=9884116613&sid=WEBSMS&msg=HiMahesh&fl=0");			//9884116613
				return new CommonStringList(0, sendSmsSender(weburl.toString()));
			}

		return null;
		
	}*/
	
/*	public String sendSmsSender(String weburl){
		String smsResponseDesc = null;
		BufferedReader bufferedReader = null;
		
		try{
			HttpURLConnection connection = (HttpURLConnection)new URL(weburl).openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    String inputLine;
		    while((inputLine = bufferedReader.readLine()) != null) {
		    	smsResponseDesc = inputLine.replaceAll("\\r|\\n", "");
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}  finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
			}
		} 
		 return smsResponseDesc;
	}*/
	public CommonStringList sendSMS(String phoneNos,String smsBody,String senderType) throws MailSMSException, UnsupportedEncodingException{
		try {
			if(phoneNos == null){
				throw new MailSMSException("Sending SMS Failed - To PhoneNos is empty");
			}
			
			if(Util.isNullOrBlank(smsBody.toString())){
				throw new MailSMSException("Sending SMS Failed - Message is empty");	
			}
			if(smsProperties!=null){
				String isSmsServiceEnabled=smsProperties.getProperty(MailSMSConstants.SMSProperties.ISSMSSERVICEENABLED);
				if(isSmsServiceEnabled==null || "N".equalsIgnoreCase(isSmsServiceEnabled))
					throw new MailSMSException("Sending SMS Failed - Enable SMS Properties");
				String serverUrl=smsProperties.getProperty(MailSMSConstants.SMSProperties.SERVERURL);
				URL url = new URL(serverUrl);
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setFollowRedirects(true);
				if(senderType.equalsIgnoreCase("Single")){
					connection = sendSingleSMS(smsProperties.getProperty(MailSMSConstants.SMSProperties.USER)
						, smsProperties.getProperty(MailSMSConstants.SMSProperties.PASSWORD), 
						smsProperties.getProperty(MailSMSConstants.SMSProperties.SENDERID),
						phoneNos, smsBody);
				}else{
					connection = sendBulkSMS(smsProperties.getProperty(MailSMSConstants.SMSProperties.USER)
							, smsProperties.getProperty(MailSMSConstants.SMSProperties.PASSWORD), 
							smsProperties.getProperty(MailSMSConstants.SMSProperties.SENDERID),
							phoneNos, smsBody);
				}
				return new CommonStringList(connection.getResponseCode(),connection.getResponseMessage());
			}	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			phoneNos=null;
			smsBody=null;
			connection=null;
		}
	return null;
	
}
	// Method for sending single SMS.
	public static HttpURLConnection sendSingleSMS(String username,
				String password, String senderId, 
	String mobileNo, String message) {
	try {
			String smsservicetype = "singlemsg"; // For single message.
				String query = "username=" + URLEncoder.encode(username)
					+ "&password=" + URLEncoder.encode(password)
					+ "&smsservicetype=" + URLEncoder.encode(smsservicetype)
					+ "&content=" + URLEncoder.encode(message) + "&mobileno="
					+ URLEncoder.encode(mobileNo) + "&senderid="
					+ URLEncoder.encode(senderId);

			connection.setRequestProperty("Content-length", String
				.valueOf(query.length()));
			connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
			connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");

			// open up the output stream of the connection
				DataOutputStream output = new DataOutputStream(connection
						.getOutputStream());

			// write out the data
			int queryLength = query.length();
			output.writeBytes(query);
			// output.close();

			// get ready to read the response from the cgi script
			DataInputStream input = new DataInputStream(connection
						.getInputStream());

			// read in each character until end-of-stream is detected
			for (int c = input.read(); c != -1; c = input.read())
				//System.out.print((char) c);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}
	
	public static HttpURLConnection sendBulkSMS(String username,
			String password, String senderId, String mobileNos, String message) {
	try {
			String smsservicetype = "bulkmsg"; // For bulk msg
			String query = "username=" + URLEncoder.encode(username)
				+ "&password=" + URLEncoder.encode(password)
				+ "&smsservicetype=" + URLEncoder.encode(smsservicetype)
				+ "&content=" + URLEncoder.encode(message) 
	+ "&bulkmobno=" + URLEncoder.encode(mobileNos, "UTF-8") 
	+ "&senderid=" + URLEncoder.encode(senderId);

			connection.setRequestProperty("Content-length", String
				.valueOf(query.length()));
			connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
			connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");

			// open up the output stream of the connection
			DataOutputStream output = new DataOutputStream(connection
				.getOutputStream());

			// write out the data
			int queryLength = query.length();
			output.writeBytes(query);
			// output.close();

			System.out.println("Resp Code:" + connection.getResponseCode());
			System.out.println("Resp Message:" + connection.getResponseMessage());

			// get ready to read the response from the cgi script
			DataInputStream input = new DataInputStream(connection
				.getInputStream());

			// read in each character until end-of-stream is detected
			for (int c = input.read(); c != -1; c = input.read())
				System.out.print((char) c);
			input.close();
			} catch (Exception e) {
				System.out.println("Something bad just happened.");
				System.out.println(e);
				e.printStackTrace();
			}
			return connection;
		}
	
	public static void main(String[] arg) throws UnsupportedEncodingException, MailSMSException{
		SMSSender smsSender=new SMSSender();
		String directorateIds="1,2,3";
		CommonDAO commonDAO =new CommonDAO();
		boolean isAllDirectorate=false;
		String messageBody="Test Msg - Indicator reached Max Threshold";
		//String mobileNo=commonDAO.getMessageAlertMobileNo(directorateIds,isAllDirectorate);
		//String mobileNo="9486812926,9884116613,9600026718";
		String mobileNo="9840777135";
		CommonStringList smsStatus  =smsSender.sendSMS(mobileNo, messageBody, "Single");
	}

}
