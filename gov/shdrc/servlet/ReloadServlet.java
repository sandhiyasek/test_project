package gov.shdrc.servlet;

import javax.servlet.http.HttpServlet;

import gov.shdrc.servicemaintenance.util.ShdrcServiceMaintenanceQueryList;
import gov.shdrc.util.ShdrcQueryList;
import gov.shdrc.util.ShdrcReportQueryList;

public class ReloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() {
		ShdrcQueryList.loadParameters();
		ShdrcReportQueryList.loadParameters();
		ShdrcServiceMaintenanceQueryList.loadParameters();
		/*SmsListener op = new SmsListener();
    	op.start();*/
	}

}
