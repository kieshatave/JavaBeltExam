package com.kieshatave.ideas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kieshatave.ideas.models.Like;
import com.kieshatave.ideas.repositories.LikeRepository;

@Service
public class LikeService {
	private final LikeRepository repo;
	public LikeService(LikeRepository repo) {
		this.repo = repo;
	}
	
	public List<Like> allLikes() {
		return repo.findAll();
	}
	
	public Like create(Like like) {
		return repo.save(like);
	}
	
	public void save(Like like) {
		repo.save(like);
	}
	
	public void delete(Like like) {
		repo.delete(like);
	}
}
