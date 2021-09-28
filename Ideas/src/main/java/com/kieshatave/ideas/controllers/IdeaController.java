package com.kieshatave.ideas.controllers;

import java.util.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.kieshatave.ideas.models.*;
import com.kieshatave.ideas.services.*;

@Controller
public class IdeaController {
	private final UserService userService;
	private final IdeaService ideaService;
	private final LikeService likeService;
	public IdeaController(IdeaService ideaService, 
			UserService userService, LikeService likeService) {
		this.ideaService = ideaService;
		this.userService = userService;
		this.likeService = likeService;
	}
	
	@RequestMapping("/ideas")
	public String index(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.findUserById(userId);
		model.addAttribute("user", user);
		List<Idea> ideas = ideaService.findAll();
		model.addAttribute("ideas", ideas);
		return "idea/ideas.html";
	}
	
	@RequestMapping("/idea/create")
	public String getCreateIdea(@Valid @ModelAttribute("idea") Idea idea) {
		return "idea/newIdea.html";
	}
	
	@RequestMapping(value="/idea/create", method=RequestMethod.POST)
	public String createIdea(@Valid @ModelAttribute("idea") Idea idea,
			BindingResult result, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.findUserById(userId);
		if (result.hasErrors()) return "/idea/newIdea.html";
		idea.setUser(user);
		ideaService.createIdea(idea);
		return "redirect:/ideas";
	}
	
	@RequestMapping("/idea/{ideaId}")
	public String getViewIdea(@PathVariable("ideaId") Long ideaId, Model model, HttpSession session) {
		Idea idea = ideaService.findById(ideaId);
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.findUserById(userId);
		List<Like> likes = idea.getLikes();
		model.addAttribute("idea", idea);
		model.addAttribute("user", user);
		model.addAttribute("likes", likes);
		return "idea/viewIdea.html";
	}
	
	@RequestMapping("/idea/edit/{id}")
	public String editPage(@PathVariable("id") Long id, Model model, HttpSession session, 
			@Valid @ModelAttribute("updateIdea") Idea updateIdea) {
		Long userId = (Long) session.getAttribute("userId");
		Idea idea = ideaService.findById(id);
		model.addAttribute("idea", idea);
		return "idea/editIdea.html";
	}
	
	@RequestMapping(value="/idea/update/{id}", method=RequestMethod.POST)
	public String updateIdea(@Valid @ModelAttribute("idea") Idea idea, @PathVariable("id") Long id,
			BindingResult result, HttpSession session) {
		if(result.hasErrors())  { 
			return "redirect:/ideas/edit/" + id;
		} else {
	    	Long userId = (Long) session.getAttribute("userId");
	    	User user = userService.findUserById(userId);
	    	idea.setUser(user);
	    	ideaService.updateIdea(id, idea);
	    	return "redirect:/ideas";
		}
	}
	
	@RequestMapping("/idea/delete/{ideaId}")
	public String deleteIdea(@PathVariable("ideaId") Long ideaId, HttpSession session, Model model) {
		Idea idea = ideaService.findById(ideaId);
		Long userId = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userId);
    	model.addAttribute("idea", idea);
		model.addAttribute("user", user);
		if (idea == null) return "redirect:/ideas";
		ideaService.deleteIdea(ideaId);
		return "redirect:/ideas";
	}
	
	@RequestMapping("/idea/like/{ideaId}")
	public String like(@PathVariable("ideaId") Long ideaId, HttpSession session,
			@Valid Idea idea, BindingResult result) {
		Idea ideaLike = ideaService.findById(ideaId);
    	Long userId = (Long) session.getAttribute("userId");
    	User user = userService.findUserById(userId);
    	//if(idea.getLikes().contains(user.getId())) return "redirect:/ideas";
    	Like like = new Like();
    	like.setIdeas(ideaLike);
    	like.setUsers(user);
    	likeService.create(like);
		return "redirect:/ideas";
	}
	
//	@RequestMapping("/idea/unlike/{ideaId}")
//	public String unlike(@PathVariable("ideaId")Long ideaId, HttpSession session) {
//		Idea idea = ideaService.findById(ideaId);
//    	Long userId = (Long) session.getAttribute("userId");
//    	User user = userService.findUserById(userId);
//    	List<User> likedIdeas = idea.getLikedUser();
//    	likedIdeas.remove(user);
//    	idea.setLikes(likedIdeas);
//    	ideaService.updateIdea(ideaId, idea);
//		return "redirect:/ideas";
//	}
}
