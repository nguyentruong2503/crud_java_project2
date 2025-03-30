package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // auto increment
	private Long id;
	
	@Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "status")
    private boolean status;
    
    @Column(name = "email")
    private String email;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "userid", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "roleid", nullable = false))
    private List<RoleEntity> roles = new ArrayList<>();

	public List<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public boolean isStatus() {
		return status;
	}

	public String getEmail() {
		return email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
