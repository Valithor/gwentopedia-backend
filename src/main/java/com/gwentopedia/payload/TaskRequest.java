package com.gwentopedia.payload;

import java.util.List;
import java.util.Set;

public class TaskRequest {

	private String name;
	private String difficulty;	
	private List<TaskCardRequest> taskCards;
    private String taskType;
    private String leaderPl;
    private String leaderOpp;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public List<TaskCardRequest> getTaskCards() {
		return taskCards;
	}
	public void setTaskCards(List<TaskCardRequest> taskCards) {
		this.taskCards = taskCards;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getLeaderPl() {
		return leaderPl;
	}
	public void setLeaderPl(String leaderPl) {
		this.leaderPl = leaderPl;
	}
	public String getLeaderOpp() {
		return leaderOpp;
	}
	public void setLeaderOpp(String leaderOpp) {
		this.leaderOpp = leaderOpp;
	}

}
