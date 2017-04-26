package gov.shdrc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAPConnectionHelper {
	private static LDAPConnectionHelper ldapConnectionHelper=null;
	public static Properties properties=null;
	private static Hashtable<String,String> env = null;
	private LDAPConnectionHelper(){
	}
	public static LDAPConnectionHelper getInstance(){
			try {
				if(ldapConnectionHelper==null){	
					ldapConnectionHelper = new LDAPConnectionHelper();
					properties = new Properties();
					InputStream inputStream = LDAPConnectionHelper.class.getClassLoader().getResourceAsStream("ldap.properties");
					properties.load(inputStream);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return ldapConnectionHelper;
	}
	public DirContext getLDAPContext(){
		DirContext context =null;
        InitialDirContext initialContext=null;
		try {
			env=new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, properties.getProperty("initial.context.factory"));
			env.put(Context.PROVIDER_URL, properties.getProperty("provider.url"));
			env.put(Context.SECURITY_AUTHENTICATION, properties.getProperty("security.authentication"));
			env.put(Context.SECURITY_PRINCIPAL, properties.getProperty("security.principal"));
			env.put(Context.SECURITY_CREDENTIALS, properties.getProperty("security.credentials"));
			initialContext = new InitialDirContext(env);
			context = (DirContext)initialContext;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return context;
	}
}
