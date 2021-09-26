package com.kieshatave.ideas.controllers;

import java.util.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.kieshatave.ideas.models.Idea;
import com.kieshatave.ideas.models.User;
import com.kieshatave.ideas.services.*;

@Controller
public class IdeaController {
	private final UserService userService;
	private final IdeaService ideaService;
	public IdeaController(IdeaService ideaService, UserService userService) {
		this.ideaService = ideaService;
		this.userService = userService;
	}
	
	@GetMapping("/ideas")
	public String index(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		List<Idea> ideas = ideaService.findAll();
		model.addAttribute("ideas", ideas);
		return "/idea/ideas.html";
	}
	
	@GetMapping("/idea/create")
	public String getCreateIdea(@Valid @ModelAttribute("idea") Idea idea) {
		return "/idea/newIdea.html";
	}
	
	@PostMapping("/idea/create")
	public String createIdea(@Valid @ModelAttribute("idea") Idea idea,
			BindingResult result, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.findUserById(userId);
		if (result.hasErrors()) return "newIdea";
		idea.setCreator(user);
		ideaService.createIdea(idea);
		return "redirect:/ideas";
	}
	
	@GetMapping("/idea/{ideaId}")
	public String getViewIdea(@PathVariable("ideaId") Long ideaId, Model model) {
		Idea idea = ideaService.findById(ideaId);
		model.addAttribute("idea", idea);
		return "/idea/viewIdea.html";
	}
	
	@GetMapping("/idea/edit/{ideaId}")
	public String editPage(@PathVariable("ideaId") Long ideaId, @Valid 
			@ModelAttribute("updatedidea") Idea updatedidea, 
			HttpSession session, Model model) {
		Idea idea = ideaService.findById(ideaId);
		model.addAttribute("idea", idea);
    	Long userId = (Long) session.getAttribute("userId");
		if(idea.getCreator().getId().equals(userId)) {
			return "/idea/editIdea.html";
		} else {
			return "redirect:/ideas";
		}
	}
	
	@PostMapping("/ideas/edit/{ideaId}")
	public String edit(@PathVariable("ideaId") Long ideaId, 
			@Valid @ModelAttribute("idea") Idea idea,
			BindingResult result, HttpSession session) {
		if(result.hasErrors()) return "redirect:/idea/edit/" + ideaId;
    	Long userId = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userId);
    	idea.setCreator(user);
    	ideaService.updateIdea(idea);
    	return "redirect:/ideas";
	}
	
	@RequestMapping("/idea/delete/{ideaId}")
	public String deleteIdea(@PathVariable("ideaId") Long ideaId) {
//		Idea idea = ideaService.findById(ideaId);
//		if (idea == null) return "redirect:/ideas";
		ideaService.deleteIdea(ideaId);
		return "redirect:/ideas";
	}
	
	@PostMapping("/idea/like/{ideaId}")
	public String like(@PathVariable("ideaId")Long ideaId, HttpSession session) {
		Idea idea = ideaService.findById(ideaId);
    	Long userId = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userId);
    	List<User> likedIdeas = idea.getLikedBy();
    	likedIdeas.add(user);
    	idea.setLikedBy(likedIdeas);
    	ideaService.updateIdea(idea);
		return "redirect:/ideas";
	}
	
	@PostMapping("/idea/unlike/{ideaId}")
	public String unlike(@PathVariable("ideaId")Long ideaId, HttpSession session) {
		Idea idea = ideaService.findById(ideaId);
    	Long userId = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userId);
    	List<User> likedIdeas = idea.getLikedBy();
    	likedIdeas.remove(user);
    	idea.setLikedBy(likedIdeas);
    	ideaService.updateIdea(idea);
		return "redirect:/ideas";
	}
}
