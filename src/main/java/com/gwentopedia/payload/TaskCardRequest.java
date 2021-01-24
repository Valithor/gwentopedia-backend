package com.gwentopedia.payload;

import javax.validation.constraints.NotBlank;

public class TaskCardRequest {
	@NotBlank
	private long taskId;
	@NotBlank
	private CardRequest card;
	@NotBlank
	private String side;
	private int strength;
	private String correct;
	private String answer;
	
	public TaskCardRequest() {}
	public TaskCardRequest(@NotBlank long taskId, @NotBlank CardRequest card, @NotBlank String side, int strength, String correct, String answer) {
		this.taskId = taskId;
		this.card = card;
		this.side = side;
		this.strength = strength;
		this.correct = correct;
		this.answer = answer;
	}	

	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public CardRequest getCard() {
		return card;
	}
	public void setCard(CardRequest card) {
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
