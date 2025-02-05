package com.qa.choonz.persistence.domain;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.qa.choonz.rest.dto.UserDTO;
import com.qa.choonz.utils.UserSecurity;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	@Size(max = 100)
	@Column(unique = true, name = "username")
	private String username;

	@NotNull
	@Size(max = 100)
	@Column(unique = true, name = "password")
	private String password;

	public User() {
		super();
	}

	public User(long id, @NotNull @Size(max = 100) String username, @NotNull @Size(max = 100) String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		super();
		this.id = id;
		this.username = username;
		this.password = UserSecurity.encrypt(password, UserSecurity.getSalt());

	}

	public User(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super();
		this.username = username;
		this.password = UserSecurity.encrypt(password, UserSecurity.getSalt());
	}

	public User(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User(@Valid UserDTO userDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super();
		this.id = userDTO.getId();
		this.username = userDTO.getUsername();
		this.password = UserSecurity.encrypt(userDTO.getPassword(), UserSecurity.getSalt());
	}

	public String getUsername() {
		return username;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}


	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
