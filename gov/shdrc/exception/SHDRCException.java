package gov.shdrc.exception;

public class SHDRCException extends Exception {

	private static final long serialVersionUID = 12222L;
	private String message = null;
	 
	    public SHDRCException() {
	        super();
	    }
	 
	    public SHDRCException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public SHDRCException(Throwable cause) {
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

