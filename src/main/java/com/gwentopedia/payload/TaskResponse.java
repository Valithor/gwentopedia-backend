package com.gwentopedia.payload;

import java.time.Instant;
import java.util.List;

public class TaskResponse {
	
	private Long id;
	private String name;
	private String difficulty;	
	private List<TaskCardResponse> taskCards;
    private TaskTypeResponse taskType;
    private LeaderResponse leaderPl;
    private LeaderResponse leaderOpp;
    private Long correct;
    private UserSummary createdBy;
    private Instant creationDateTime;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public List<TaskCardResponse> getTaskCards() {
		return taskCards;
	}
	public void setTaskCards(List<TaskCardResponse> taskCards) {
		this.taskCards = taskCards;
	}
	public TaskTypeResponse getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskTypeResponse taskType) {
		this.taskType = taskType;
	}
	public LeaderResponse getLeaderPl() {
		return leaderPl;
	}
	public void setLeaderPl(LeaderResponse leaderPl) {
		this.leaderPl = leaderPl;
	}
	public LeaderResponse getLeaderOpp() {
		return leaderOpp;
	}
	public void setLeaderOpp(LeaderResponse leaderOpp) {
		this.leaderOpp = leaderOpp;
	}
	public Long getCorrect() {
		return correct;
	}
	public void setCorrect(Long correct) {
		this.correct = correct;
	}
	public UserSummary getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserSummary createdBy) {
		this.createdBy = createdBy;
	}
	public Instant getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(Instant creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
}
