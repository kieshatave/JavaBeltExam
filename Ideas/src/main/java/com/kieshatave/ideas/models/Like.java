package com.kieshatave.ideas.models;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="idea_likes")
public class Like {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(updatable=false)
	private Date createdAt;
	@Column
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User users;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idea_id")
	private Idea ideas;
	
	public Like() {}
	
	public Like(User user, Idea idea) {
    	
    }
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUsers() {
		return users;
	}
	public void setUsers(User users) {
		this.users = users;
	}
	public Idea getIdeas() {
		return ideas;
	}
	public void setIdeas(Idea ideas) {
		this.ideas = ideas;
	}
	
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}

