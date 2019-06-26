package com.cody.learning.components;

// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexHandler {
    @RequestMapping("/")
    public ModelAndView indexHandler() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "welcome");
        mv.setViewName("index");
        return mv;
    }
}