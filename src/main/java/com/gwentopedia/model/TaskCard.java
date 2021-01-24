package com.gwentopedia.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="taskcard")
public class TaskCard implements Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = -6315331882781714452L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@JsonBackReference
	@ManyToOne
	@JoinColumn
	private Task task;		
	@JsonBackReference
	@ManyToOne
	@JoinColumn
	private Card card;
	@NotBlank
    private String side;
	private int strength;
	private String correct;	
    private String answer;
    
    public TaskCard() {}
    
    public TaskCard (Card card, String side, int strength, String correct, String answer) {
        this.card = card;
        this.side = side;
        this.strength = strength;
        this.correct = correct;
        this.answer = answer;
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskCard)) return false;
        TaskCard that = (TaskCard) o;
        return Objects.equals(card.getName(), that.card.getName()) &&
                Objects.equals(task.getName(), that.task.getName()) &&
                Objects.equals(side, that.side) &&
                Objects.equals(strength, that.strength)&&
                Objects.equals(correct, that.correct)&&
                Objects.equals(answer, that.answer);

    }

    @Override
    public int hashCode() {
        return Objects.hash(card.getName(), task.getName(), side, strength, correct, answer);
    }
}