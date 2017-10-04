package com.main.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.main.java.service.HomeService;
@Deprecated
@Controller
public class InitController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());  

	@Autowired
	private HomeService homeService;
	
    @RequestMapping(value = {"/","/home",""}, method = RequestMethod.GET)  
    public String home() {  
    	System.out.println(homeService.testHome());
    	LOG.debug("loghahaha....");
    	LOG.info("loghahaha....");
    	LOG.error("loghahaha....");
    	List<String> list = new ArrayList<String>();
    	list.add("a");
    	list.add("b");
    	list.add("c");
    	
    	list.stream().forEach(item -> System.out.println(item));
    	
        return "application/search";  
    }  
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)  
    public String add() {  
    	homeService.addHome1();
    	
        return "application/search";  
    }  
}
