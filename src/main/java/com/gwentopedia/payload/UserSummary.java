package com.gwentopedia.payload;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class UserSummary {
    private Long id;
    private String username;
    private String gogName;
    private String avatar;
    private List<TaskResponse> tasks;
    private Collection<? extends GrantedAuthority> authorities;


    public UserSummary(Long id, String username, String gogName, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.gogName = gogName;
        this.avatar = avatar;
        this.authorities = authorities;
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

    public String getGogName() {
        return gogName;
    }

    public void setGogName(String gogName) {
        this.gogName = gogName;
    }

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<TaskResponse> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskResponse> tasks) {
		this.tasks = tasks;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

}