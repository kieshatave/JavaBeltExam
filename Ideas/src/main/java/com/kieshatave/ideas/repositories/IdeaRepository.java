package com.kieshatave.ideas.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.kieshatave.ideas.models.Idea;

public interface IdeaRepository extends CrudRepository<Idea, Long>{
	List<Idea> findAll();
}
