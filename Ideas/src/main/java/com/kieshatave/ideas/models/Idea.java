package com.kieshatave.ideas.models;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="ideas")
public class Idea {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column
	private String content;
	@Column(updatable=false)
	private Date createdAt;
	@Column
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinTable(name="user_id")
	private User user;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="idea_likes",
			joinColumns=@JoinColumn(name="idea_id"),
			inverseJoinColumns=@JoinColumn(name="user_id")
			)
	private List<Like> likes;
	
	public Idea() {}
	
	public Idea(String content) {
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Like> getLikes() {
		return likes;
	}
	public void setLikes(List<Like> likes) {
		this.likes = likes;
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
