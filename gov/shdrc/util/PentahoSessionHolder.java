package gov.shdrc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PentahoSessionHolder {
	private static PentahoSessionHolder pentahoSessionHolder=null;
	private HttpSession session=null;
	private List<String> userRoles=null;
	
	public static PentahoSessionHolder getInstance(){
		try {
			if(pentahoSessionHolder==null){	
				pentahoSessionHolder = new PentahoSessionHolder();
			}
		} catch (Exception e) {
		}
		return pentahoSessionHolder;
	}
	
	public PentahoSessionHolder(){
	}
	
	public List<String> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}
	public void setSession(HttpSession session) {
		this.session=session;
	}
	public HttpSession getSession(){
		return session;
	}
	public String getLoggedInUserName(){
		return (String)session.getAttribute("UserName");
	}
	public void setLoggedInUserName(String userName){
		this.session.setAttribute("UserName", userName);
	}
	
}

