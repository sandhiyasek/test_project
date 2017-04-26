package gov.shdrc.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import Decoder.BASE64Encoder;

import java.util.Base64;












import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;


public class SecurityUtil {
	private static String decryptedText;

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
	  }catch (Exception e) {
			e.printStackTrace();
	  }
  	  return encriptedPwd;
  	}

	public static boolean updateUserPassword(final String password,String userLoginId,String userOrganisation){
		DirContext ctx=null;
		boolean status=false;
        try {
        	ctx=LDAPConnectionHelper.getInstance().getLDAPContext();
        	if(ctx!=null){
	    		ModificationItem[] mods = new ModificationItem[1];
	            //Attribute mod0 = new BasicAttribute("userpassword", digestMd5(password));
	            Attribute mod0 = new BasicAttribute("userpassword", "{MD5}"+password);
	            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
				ctx.modifyAttributes("cn="+userLoginId+",ou="+userOrganisation+",dc=shdrc,dc=com", mods);
				ctx.close();
				status=true;
        	}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}   
	    return status;
	}
	
	public static String addEntry(String customerName,String snName,String password,String orgName,String tierVal) throws Exception{
		//Properties initilaProperties = new Properties();
		String rValue="success";
		/*initilaProperties.put(Context.INITIAL_CONTEXT_FACTORY,
		"com.sun.jndi.ldap.LdapCtxFactory");
		initilaProperties.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		initilaProperties
		.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		initilaProperties.put(Context.SECURITY_CREDENTIALS, "secret");*/
		try {
		DirContext context =LDAPConnectionHelper.getInstance().getLDAPContext();
		ModificationItem[] mods = new ModificationItem[1];
     
		String actualPassword=digestMd5(password);
		 BasicAttributes entry = new BasicAttributes();  
         entry.put(new BasicAttribute("cn", customerName));  
         entry.put(new BasicAttribute("sn", snName));
         entry.put(new BasicAttribute("userPassword", "{MD5}"+actualPassword));
         Attribute objectClass = new BasicAttribute("objectClass", "organizationalPerson");
         entry.put(objectClass);
         if(orgName==null){
        	  orgName="Administrator"; 
        	 
         }
         boolean bValue=SecurityUtil.authenticateUser(customerName, password, orgName);
         if(!bValue){
        	 context.createSubcontext("cn="+customerName+",ou="+orgName+",dc=shdrc,dc=com", entry);  
        	 ModificationItem[] mods1 = new ModificationItem[1];
           Attribute mod0 = new BasicAttribute("uniqueMember", "cn="+customerName+",ou="+orgName+",dc=shdrc,dc=com");
            mods1[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod0);
            context.modifyAttributes("cn="+orgName+" "+tierVal+",ou="+orgName+",dc=shdrc,dc=com", mods1);
         context.close();
		rValue="success inserted";
		}
		} catch (NamingException e) {
			rValue="failed";
		e.printStackTrace();
		return rValue;
		}
		return rValue;
	}
	

	private static int addEntryBasedOnTier(String actualPassword, String customerName,String snName,String orgName){
		/*Properties initilaProperties = new Properties();
		initilaProperties.put(Context.INITIAL_CONTEXT_FACTORY,
		"com.sun.jndi.ldap.LdapCtxFactory");
		initilaProperties.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		initilaProperties
		.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		initilaProperties.put(Context.SECURITY_CREDENTIALS, "secret");*/
		try {
			DirContext context =LDAPConnectionHelper.getInstance().getLDAPContext();
	
			//String actualPassword=digestMd5("password");
			 BasicAttributes entry = new BasicAttributes();  
	         entry.put(new BasicAttribute("cn", customerName));  
	         entry.put(new BasicAttribute("sn", snName));
	         entry.put(new BasicAttribute("userPassword", actualPassword));
	         Attribute objectClass = new BasicAttribute("objectClass", "organizationalPerson");
	         entry.put(objectClass);
	         ModificationItem[] mods = new ModificationItem[1];
	           Attribute mod0 = new BasicAttribute("uniqueMember", "cn=customerName,ou=orgName,dc=shdrc,dc=com");
	            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod0);
	            context.modifyAttributes("cn=DPH T4,ou=orgName,dc=shdrc,dc=com", mods);
		
		} catch (NamingException e) {

		e.printStackTrace();
		}
		return 1;
		
	}

	public static List<String> getLDAPUserRoles(String userName) throws NamingException{
		DirContext ctx=LDAPConnectionHelper.getInstance().getLDAPContext();
		List<String> userRoles=new ArrayList<String>();
		String filter ="objectclass=groupOfUniqueNames";
	    NamingEnumeration<?>  results = ctx.search("dc=shdrc,dc=com", filter, getSearchControl());
        while (results.hasMore()) {
          SearchResult searchResult = (SearchResult) results.next();
          Attributes attributes = searchResult.getAttributes(); 
          String uniqueMember=attributes.get("uniqueMember").toString().replace("uniqueMember: ", "");
         // System.out.println("uniquemember"+uniqueMember);
          String[] uniqueMemberArr=uniqueMember.split(",");
          String cn="cn="+userName;
          String cn1=" cn="+userName;
          boolean isUserRole=Arrays.asList(uniqueMemberArr).contains(cn);
          boolean isUserRole1=Arrays.asList(uniqueMemberArr).contains(cn1);
          if(isUserRole||isUserRole1){
        	  userRoles.add(attributes.get("cn").toString().replace("cn: ", ""));
          }
        }
        System.out.println(userRoles.toString());
		return userRoles;
		
	}
	private static SearchControls getSearchControl(){
		/**
	       * Retrieve the specific attributes 
	       */
	      SearchControls controls = new SearchControls();
	      controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	      controls.setReturningAttributes(new String [] { 
	          "nsRole", 
	          "userPassword",
	                "uid",
	                "objectClass",
	                "givenName",
	                "sn",
	                "cn",
	                "groupOfUniqueNames",
	                "uniqueMember",
	                "ou"
	      });
	      
	    return  controls;
	}
	
	public static boolean authenticateUser (String userName, String password,String organisation) throws Exception {
		password=getDecodedPassword(password);
		Properties ldapProperties=LDAPConnectionHelper.getInstance().properties;
		String dn="cn="+userName+",ou="+organisation+","+ldapProperties.getProperty("userSearch.searchBase");
		Hashtable<String,String> env = new Hashtable <String,String>();
		env.put(Context.SECURITY_AUTHENTICATION, ldapProperties.getProperty("security.authentication"));
		env.put(Context.SECURITY_PRINCIPAL, dn);
		env.put(Context.SECURITY_CREDENTIALS, password);
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY,  ldapProperties.getProperty("initial.context.factory"));
			env.put(Context.PROVIDER_URL, ldapProperties.getProperty("provider.url"));
			new InitialDirContext(env);
			System.out.println("Authentication---Success");
		}
		catch (javax.naming.AuthenticationException e) {
			System.out.println("Authentication---Fail");
			return false;
		}
		return true;
	}
	
	public static String getDecodedPassword(String encriptedPwd){
		byte[] asBytes = Base64.getDecoder().decode(encriptedPwd);
		String base64Decoded = new String(asBytes, StandardCharsets.UTF_8);
		return base64Decoded;
	}
	
	public static void main(String[] arg) throws Exception{
		//getLDAPUserDetails();
		getLDAPUserRoles("dms_de01");
		//authenticateUser("dca_de06","shdrcdcade06","DCA");
	}


}
