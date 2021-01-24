package com.gwentopedia.payload;

import com.gwentopedia.model.Task;

public class TaskUserResponse {
	private Long userId;
	private Task task;	 
    private Long good;
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Long getGood() {
		return good;
	}
	public void setGood(Long good) {
		this.good = good;
	}  
}
