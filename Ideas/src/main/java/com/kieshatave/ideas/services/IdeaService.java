package com.kieshatave.ideas.services;

import java.util.*;
import org.springframework.stereotype.Service;
import com.kieshatave.ideas.models.Idea;
import com.kieshatave.ideas.repositories.IdeaRepository;

@Service
public class IdeaService {
	private final IdeaRepository repo;
	public IdeaService(IdeaRepository repo) {
		this.repo = repo;
	}
	
	public Idea createIdea(Idea idea) {
		return repo.save(idea);
	}
	
	public List<Idea> findAll() {
		return repo.findAll();
	}
	
	public Idea findById(Long id) {
		Optional<Idea> idea = repo.findById(id);
		if(idea.isPresent()) return idea.get();
		return null;
	}
	
	public void updateIdea(Long id, Idea ideaUpdate) {
		Idea idea = this.findById(id);
		idea.setContent(ideaUpdate.getContent());
		repo.save(idea);
	}
	
	public void deleteIdea(Long id) {
		repo.deleteById(id);
	}
	
	public void save(Idea idea) {
		this.repo.save(idea);
	}
}
