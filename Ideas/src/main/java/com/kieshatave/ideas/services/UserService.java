package com.kieshatave.ideas.services;

import java.util.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.kieshatave.ideas.models.User;
import com.kieshatave.ideas.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository repo;
	public UserService(UserRepository repo) {
		this.repo = repo;
	}
	
	public User registerUser(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		return repo.save(user);
	}
	
	public User findByEmail(String email) {
        return repo.findByEmail(email);
    }
	
	public boolean authenticateUser(String email, String password) {
        User user = repo.findByEmail(email);
        if(user == null) {
            return false;
        } else {
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
	
	public User findUserById(Long id) {
    	Optional<User> user = repo.findById(id);    	
    	if(user.isPresent()) return user.get();    	
    	return null;
    }
	
	public void updateUser(User user) {
		repo.save(user);
	}
}
