package com.github.iceant.spring.jdbc.dao.generator.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping(path = {"", "/", "/index", "/home"})
    public ModelAndView index(){
        return new ModelAndView("views/index");
    }

    @RequestMapping(path = {"/{page}"})
    public ModelAndView page(@PathVariable("page") String page){
        if("favicon.ico".equalsIgnoreCase(page)){return null;}
        return new ModelAndView("views/"+page);
    }
}
