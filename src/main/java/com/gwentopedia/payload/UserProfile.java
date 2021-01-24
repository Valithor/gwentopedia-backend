package com.gwentopedia.payload;

import java.time.Instant;

public class UserProfile {
    private Long id;
    private String username;
    private String gogName;
    private String avatar;
    private Instant joinedAt;
	private Long tasksCount;

    public UserProfile(Long id, String username, String gogName, String avatar, Instant joinedAt, Long tasksCount) {
        this.id = id;
        this.username = username;
        this.gogName = gogName;
        this.avatar = avatar;
        this.joinedAt = joinedAt;
        this.tasksCount = tasksCount;
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

	public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getTasksCount() {
 		return tasksCount;
 	}

 	public void setTasksCount(Long tasksCount) {
 		this.tasksCount = tasksCount;
 	}

}