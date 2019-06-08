package com.leo.springboot.im.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	private Long id;
	private String username;

	public User() {
		super();
	}

	public User(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public static class UserBuilder {
		private Long id;
		private String username;

		public UserBuilder() {

		}

		public UserBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public UserBuilder username(String username) {
			this.username = username;
			return this;
		}

		public User build() {
			return new User(id, username);
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

	}

}