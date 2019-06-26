package com.cody.learning.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView maxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletResponse resp)
            throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "file size out of limit");
        mv.setViewName("error");
        return mv;
    }
}