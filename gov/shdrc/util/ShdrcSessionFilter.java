
package gov.shdrc.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * SelSessionFilter.java
 * 
 * This filter is intercepts all requests. This is used to validate the user
 * session. If the session is valid, then it invokes next filter/servlet in the
 * filter chain. Otherwise it sends the expiry message to the user
 * 
 */
public class ShdrcSessionFilter implements Filter {
	/** logger object for logging */
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @Override
	 * 
	 * @param config
	 * @throws ServletException
	 */
	public void init(FilterConfig config) throws ServletException {
		// does nothing
	}

	/**
	 * @Override, This function is to check the valid user session on all the
	 *            request. In case of invalid session, session expiry message
	 *            will be forward to user.
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Flag to validate the session, session validation is not required for
		// some requests, like login, logout and so on
		boolean isSessionValidationRequired;
		// Flag to represents user session is valid or not
		boolean isValidSession;
		// to validate user session
		HttpSession session = null;
		// HTTP servlet request object to get user session
		HttpServletRequest httpRequest = null;
		// Request uri
		String uri = null;
		try {
			// log debug message
			logger.debug("Filter, session validation start");
			// Set default value
			isValidSession = true;
			// Cast ServletRequest to HttpServletRequest to get session
			httpRequest = (HttpServletRequest) request;
			// Get request url
			uri = httpRequest.getRequestURI();
			// log debug message
			logger.debug("[Request URI: " + uri + "]");

			// If the request is for login, logout or home page, we don't need
			// to validate the user session
			/*isSessionValidationRequired = !((uri.indexOf("login") >= 0)
					|| (uri.indexOf("logout") >= 0) || (uri.indexOf("home") >= 0));*/
			isSessionValidationRequired = !((uri.indexOf("login") >= 0));

			// log debug message
			logger.debug("Session validation requied: "
					+ isSessionValidationRequired);

			if (isSessionValidationRequired) {
				// Get user session
				session = httpRequest.getSession(false);
				// Check user session
				if (session == null
						|| ((String)session.getAttribute("userName")) == null) {
					// This is not a valid session, so we need to notify the
					// user
					isValidSession = false;
				}
			}

			if (isValidSession) {
				// log debug message
				logger.debug("Valid session");
				if(response instanceof HttpServletResponse){
			        HttpServletResponse alteredResponse = ((HttpServletResponse)response);
			        addCorsHeader(alteredResponse);
				}
				// Valid session, so invoke next filter or servlet on the chain
				chain.doFilter(request, response);
			} else {
				// log debug message
				logger.debug("Invalid session. Send session expiry message");
				// Invalid session, so throw the session expired exception
				request.getRequestDispatcher("Login.jsp")
                .forward(request, response);
			}
		}catch (Exception ex) {
			// log error message
			logger.error("Error while validate user session", ex);
			System.out.println(ex);
			// send session expiry error message to user
			request.getRequestDispatcher("Login.jsp")
            .forward(request, response);
		} finally {
			// log debug message
			logger.debug("Filter end");
		}
	}
	
	private void addCorsHeader(HttpServletResponse response){
        //TODO: externalize the Allow-Origin
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        response.setHeader("X-Frame-Options", "DENY");
    }

	/**
	 * @Override
	 */
	public void destroy() {
		// does nothing
	}
}
