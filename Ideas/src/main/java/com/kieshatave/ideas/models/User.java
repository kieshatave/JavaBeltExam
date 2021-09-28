package com.kieshatave.ideas.models;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=3, max=16)
	private String name;
	@Email(message="Invalid entry") 
	private String email;
	@Size(min=8, message="Password must be at least 8 characters.")
	private String password;
	@Transient
	private String passConfirm;
	@Column(updatable=false)
	private Date createdAt;
	@Column
	private Date updatedAt;
	
	public User() {}
	
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Idea> myIdeas;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="idea_likes",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="idea_id")
			)
	private List<Idea> likedIdeas;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassConfirm() {
		return passConfirm;
	}
	public void setPassConfirm(String passConfirm) {
		this.passConfirm = passConfirm;
	}		
	public List<Idea> getMyIdeas() {
		return myIdeas;
	}
	public void setMyIdeas(List<Idea> myIdeas) {
		this.myIdeas = myIdeas;
	}
	public List<Idea> getLikedIdeas() {
		return likedIdeas;
	}
	public void setLikedIdeas(List<Idea> likedIdeas) {
		this.likedIdeas = likedIdeas;
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
