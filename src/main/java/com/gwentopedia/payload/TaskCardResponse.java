package com.gwentopedia.payload;

public class TaskCardResponse {
	private Long id;
	private TaskResponse task;
	private CardResponse card;
	private String side;
	private int strength;
	private String correct;
	private String answer;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TaskResponse getTask() {
		return task;
	}
	public void setTask(TaskResponse task) {
		this.task = task;
	}
	public CardResponse getCard() {
		return card;
	}
	public void setCard(CardResponse card) {
		this.card = card;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public String getCorrect() {
		return correct;
	}
	public void setCorrect(String correct) {
		this.correct = correct;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
