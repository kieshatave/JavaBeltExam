package com.kieshatave.ideas.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kieshatave.ideas.models.Like;

public interface LikeRepository extends CrudRepository<Like, Long>{
	List<Like> findAll();
}
