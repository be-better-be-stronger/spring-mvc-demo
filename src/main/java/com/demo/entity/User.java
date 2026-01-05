package com.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	@Column(nullable = false, unique = true, length = 120)
	private String email;

	@Column(nullable = false, length = 120)
	private String password;

	@Column(nullable = false, length = 20)
	private String role = UserRole.USER.name()  ;
	
	@Column(nullable = false, length = 20)
	private String status = UserStatus.ACTIVE.name();
	
	@Column(name = "disabled_at")
	private LocalDateTime disabledAt;


	public LocalDateTime getDisabledAt() {
		return disabledAt;
	}

	public void setDisabledAt(LocalDateTime disabledAt) {
		this.disabledAt = disabledAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToOne(mappedBy = "user", 
			cascade = CascadeType.ALL, 
			orphanRemoval = true, 
			fetch = FetchType.LAZY, 
			optional = true)
	private UserProfile profile;

	// helper để đồng bộ 2 chiều
	public void setProfile(UserProfile p) {
		this.profile = p;
		if (p != null)
			p.setUser(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserProfile getProfile() {
		return profile;
	}

	/*
	 * @MapsId = Profile không tự có id, nó ăn theo User.id.
	 * 
	 * cascade = ALL = persist User → persist Profile tự động.
	 * 
	 * orphanRemoval = true = set profile = null → row profile bị xóa.
	 */
}
