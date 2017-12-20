package com.hellokoding.account.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hellokoding.account.model.User;
import com.hellokoding.account.service.SecurityService;
import com.hellokoding.account.service.UserService;
import com.hellokoding.account.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	
	@PostMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());
		
		return "registartion";
	}
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model ) {
		userValidator.validate(userForm, bindingResult);
	
		if(bindingResult.hasErrors()) {
			return "registration";
		}
		
		userService.save(userForm);
		
		securityService.autologin(userForm.getUsername(), userForm.getPassword());
		
		return "redirect:/welcome";
	}
	
	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if(error != null) {
			model.addAttribute("error", "Your username and password is invalid.");
		}
		
		if(logout != null) {
			model.addAttribute("message", "You have been logged out successfully");
		}
		
		return "login";
	}
	
	@GetMapping({"/","/welcome"})
	public String welcome(Model model) {
		return "welcome";
	}
}
