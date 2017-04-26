package gov.shdrc.sms;

public class MailSMSException extends Exception {

	private static final long serialVersionUID = 12222L;
	private String message = null;
	 
	    public MailSMSException() {
	        super();
	    }
	 
	    public MailSMSException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public MailSMSException(Throwable cause) {
	        super(cause);
	    }
	 
	    @Override
	    public String toString() {
	        return message;
	    }
	 
	    @Override
	    public String getMessage() {
	        return message;
	    }
}

