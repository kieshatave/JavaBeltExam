package com.kieshatave.ideas.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.kieshatave.ideas.models.User;
import com.kieshatave.ideas.services.UserService;

@Controller
public class UserController {
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String getLoginReg(@ModelAttribute("user") User user) {
		return "idea/loginReg.html";
	}
	
	@RequestMapping(value="/idea/register", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult result, HttpSession session) {
		if (result.hasErrors()) return "loginReg";
		user = this.userService.registerUser(user);
		session.setAttribute("userId", user.getId());
		return "redirect:/ideas";
	}
	
	@RequestMapping(value="/idea/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, 
    		@RequestParam("password") String password, Model model, 
    		HttpSession session) {
    	boolean isAuthenticated = userService.authenticateUser(email, password);
		if(isAuthenticated) {
			User user = userService.findByEmail(email);
			session.setAttribute("userId", user.getId());
			return "redirect:/ideas";
		}
		else {
			model.addAttribute("error", "Invalid Credentials! Please try again!");
			return "idea/loginReg.html";	
		}
    }
	
	@RequestMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
		return "redirect:/";
    }
}
