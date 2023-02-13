package com.jwt.Spring_Security_JWT.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jwt.Spring_Security_JWT.Enum.UserRoles;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	@Enumerated(EnumType.STRING)
	private UserRoles name;

	public Role() {

	}

	public Role(UserRoles name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserRoles getName() {
		return name;
	}

	public void setName(UserRoles name) {
		this.name = name;
	}
}