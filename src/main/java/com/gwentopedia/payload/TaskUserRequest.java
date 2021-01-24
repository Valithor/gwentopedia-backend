package com.gwentopedia.payload;

import com.gwentopedia.model.Task;


public class TaskUserRequest {
    private Long good;  
    
	public TaskUserRequest() {}

	public TaskUserRequest(long userId, Task task, long good) {
		super();
		this.good = good;
	}

	public Long getGood() {
		return good;
	}
	public void setGood(Long good) {
		this.good = good;
	}

}
