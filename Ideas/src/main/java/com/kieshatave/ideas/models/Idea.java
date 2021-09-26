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
	private User creator;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="idea_likes",
			joinColumns=@JoinColumn(name="idea_id"),
			inverseJoinColumns=@JoinColumn(name="user_id")
			)
	private List<User> likedBy;
	
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
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public List<User> getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(List<User> likedBy) {
		this.likedBy = likedBy;
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
