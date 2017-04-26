package gov.shdrc.exception;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ShdrcGlobalExceptionController {

	@ExceptionHandler(SHDRCException.class)
	public ModelAndView handleCustomException(HttpServletRequest request, HttpServletResponse response,SHDRCException ex) {
		ModelAndView modelView = null;
		if(isAjax(request)){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;

		}else{
			modelView = new ModelAndView();
			modelView.setViewName("error");
			//model.addObject("errCode", ex.getErrCode());
			return modelView;
		}	

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(HttpServletRequest request, HttpServletResponse response,Exception ex) {
		ModelAndView modelView = null;
		if(isAjax(request)){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Error Message"+ex.getMessage());
            return null;

		}else{
			modelView = new ModelAndView();
			modelView.setViewName("error");
			//model.addObject("errMsg", "this is Exception.class");	
			System.out.println("Error Message"+ex.getMessage());
			return modelView;
		}
	}
	
	private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}

