package com.main.java.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.main.java.model.AdminUser;


@Controller
public class LoginController {
	
	//登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {

		ModelAndView model = new ModelAndView();
		model.setViewName("login");

		return model;

	}

	@Deprecated                      
	@RequestMapping(value = "/login/init", method = RequestMethod.GET)
	public String loginInit(Principal adminUser) {
		try{
			
			if(adminUser == null || StringUtils.isBlank(((AdminUser)adminUser).getRoleId())) {
				return "redirect:/login";
			}else {
				//return "redirect:/login?error";
				return "redirect:/adminUser/init";
			}
		}catch(Exception e) {
			return "redirect:/login";
		}
	}
	
	//退出页面
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    } 

}
