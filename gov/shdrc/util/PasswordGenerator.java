package gov.shdrc.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import Decoder.BASE64Encoder;

public class PasswordGenerator {
	public static String digestMd5(final String password) {
	  	  String encriptedPwd=null;
	  	  try {
	  	     MessageDigest digest = MessageDigest.getInstance("MD5");
	  	     digest.update(password.getBytes("UTF-8"));
	  	     encriptedPwd = new BASE64Encoder().encode(digest.digest());
	  	  }
	  	  catch (NoSuchAlgorithmException e) {
	  	     throw new RuntimeException(e);
	  	  }catch (UnsupportedEncodingException e) {
				e.printStackTrace();
		  }
	  	  return encriptedPwd;
	  	}
	public static void main(String[] str){
		//boolean staus=updateUserPassword("cc03e747a6afbbcbf8be7668acfebee5","dfw_de01","DFW");
		//System.out.println("staus"+staus);
		
		List<String> orgList=new ArrayList<String>();
		//orgList.add("dms");
		//orgList.add("dph");
		orgList.add("ncd");
		//orgList.add("tnmsc");
		//orgList.add("tansacs");
		//orgList.add("nrhm");
		//orgList.add("rntcp");
		//orgList.add("ma");
		//orgList.add("dme");
		//orgList.add("drmgr");
		//orgList.add("dfw");
		//orgList.add("shtd");
		//orgList.add("coc");
		//orgList.add("sbcs");
		//orgList.add("dca");
		//orgList.add("mrb");
		//orgList.add("dfs");
		//orgList.add("cmchis");
		//orgList.add("dim");
		//orgList.add("nlep");
		//orgList.add("esi");*/
		
		dataEntryUserPwd(orgList);
		
	}
	public static void dataEntryUserPwd(List<String> orgList){
		
		System.out.println("--Full Access User");
		System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5("shdrcpd01")+"' WHERE \"User_Name\"='"+"ald_pd01"+"';");
		System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5("shdrchmismo")+"' WHERE \"User_Name\"='"+"hmis_mo"+"';");
		System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5("shdrc2715")+"' WHERE \"User_Name\"='"+"admins1"+"';");
		System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5("shdrc2715")+"' WHERE \"User_Name\"='"+"admins2"+"';");
		System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5("shdrc2715")+"' WHERE \"User_Name\"='"+"sysadmin"+"';");
		System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5("shdrctnhs")+"' WHERE \"User_Name\"='"+"tnhs"+"';");
		System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5("shdrctncs")+"' WHERE \"User_Name\"='"+"tncs"+"';");
		String newLine1 = System.getProperty("line.separator");
		System.out.println(newLine1);
		String newLine = System.getProperty("line.separator");
		
		for(String org:orgList){
			String password="shdrc"+org+"de0";
			String password1="shdrc"+org+"de";
			String userName=org+"_de0";
			String userName1=org+"_de";
			String ndUserName=org+"_nd0";
			String ndUserName1=org+"_nd";
			String ndUserName2=org+"_nd03";
			String ndUserName3=org+"_nd04";
			String ndUserName4=org+"_nd05";
			String jdUserName=org+"_jd01";
			String ndPassword="shdrc"+org+"nd0";
			String ndPassword1="shdrc"+org+"nd";
			String ndPassword2="shdrc"+org+"nd03";
			String ndPassword3="shdrc"+org+"nd04";
			String ndPassword4="shdrc"+org+"nd05";
			String jdPassword="shdrc"+org+"jd01";
			
			System.out.println("--"+org+"JD/ND User");
			
			/*System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(ndPassword)+"' WHERE \"User_Name\"='"+ndUserName+"';");
			System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(ndPassword1)+"' WHERE \"User_Name\"='"+ndUserName1+"';");
			System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(ndPassword2)+"' WHERE \"User_Name\"='"+ndUserName2+"';");
			System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(ndPassword3)+"' WHERE \"User_Name\"='"+ndUserName3+"';");
			System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(ndPassword4)+"' WHERE \"User_Name\"='"+ndUserName4+"';");*/
			for(int i=1;i<46;i++){
				if(i<10)
					System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(ndPassword+i)+"' WHERE \"User_Name\"='"+ndUserName+i+"';");
				else
					System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(ndPassword1+i)+"' WHERE \"User_Name\"='"+ndUserName1+i+"';");
			}
			System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(jdPassword)+"' WHERE \"User_Name\"='"+jdUserName+"';");
			
			System.out.println(newLine);
			System.out.println("--"+org+"Data Entry User");
			
			for(int i=1;i<37;i++){
				if(i<10)
					System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(password+i)+"' WHERE \"User_Name\"='"+userName+i+"';");
				else
					System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(password1+i)+"' WHERE \"User_Name\"='"+userName1+i+"';");
			}
			System.out.println(newLine);
			
			
			
		}
		////Nodal officer password for dms 32 district
		/*for(String org:orgList){
			String password="shdrc"+org+"nd0";
			String password1="shdrc"+org+"nd";
			String userName=org+"_nd0";
			String userName1=org+"_nd";
			for(int i=1;i<33;i++){
				if(i<10)
					System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(password+i)+"' WHERE \"User_Name\"='"+userName+i+"';");
				else
					System.out.println("UPDATE shdrc_dwh.\"User_Master\" SET \"Upass\"='"+digestMd5(password1+i)+"' WHERE \"User_Name\"='"+userName1+i+"';");
			}
			System.out.println(newLine);
		}*/
	}

}
