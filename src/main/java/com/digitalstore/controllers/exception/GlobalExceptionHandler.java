package com.digitalstore.controllers.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error"); // error.html page name
        System.out.println(ex);
        modelAndView.addObject("errorMessage", ex.getMessage()); // Pass exception message to the error page
        return modelAndView;
    }
}
