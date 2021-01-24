package com.gwentopedia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gwentopedia.model.audit.UserDateAudit;


@Entity
@Table(name="task")
public class Task extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7418780055318336694L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@NotBlank
	@Column(name="name")
    private String name;
	@NotBlank
	@Column(name="difficulty")
	private String difficulty;	
    @JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)   
    private List<TaskCard> taskCards = new ArrayList<>();
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "idTaskType")
    private TaskType taskType;
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("createdAt DESC")
    private List<TaskUser> taskUsers;
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "idLeaderPl")
    private Leader leaderPl;
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name = "idLeaderOpp")
    private Leader leaderOpp;
	
	public Task() {}
	public Task(String name, String difficulty, TaskType taskType, Leader leaderPl, Leader leaderOpp, TaskUser...taskUsers) {
		super();
		this.name = name;
		this.difficulty = difficulty;
		this.taskType = taskType;
	    this.taskUsers = Stream.of(taskUsers).collect(Collectors.toList());
	    this.leaderPl = leaderPl;
	    this.leaderOpp = leaderOpp;
	}     
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public List<TaskCard> getTaskCards() {
		return taskCards;
	}
	public void setTaskCards(List<TaskCard> taskCards) {
		this.taskCards = taskCards;
	}
	public TaskType getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	public List<TaskUser> getTaskUsers() {
		return taskUsers;
	}
	public void setTaskUsers(List<TaskUser> taskUsers) {
		this.taskUsers = taskUsers;
	}
	public void addTaskCard(TaskCard taskCard) {
		taskCards.add(taskCard);
		taskCard.setTask(this);
	}
	public void removeTaskCard(TaskCard taskCard) {
		taskCards.remove(taskCard);
		taskCard.setTask(null);
	}
    public Leader getLeaderPl() {
		return leaderPl;
	}
	public void setLeaderPl(Leader leaderPl) {
		this.leaderPl = leaderPl;
	}
	public Leader getLeaderOpp() {
		return leaderOpp;
	}
	public void setLeaderOpp(Leader leaderOpp) {
		this.leaderOpp = leaderOpp;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
