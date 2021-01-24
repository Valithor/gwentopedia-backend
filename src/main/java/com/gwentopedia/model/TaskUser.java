package com.gwentopedia.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gwentopedia.model.audit.DateAudit;
@Entity
@Table(name="taskuser")
public class TaskUser extends DateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2909346469997068139L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@JsonBackReference
	@ManyToOne
	@JoinColumn
	private Task task;	 
	@JsonBackReference
	@ManyToOne
	@JoinColumn
	private User user;
	@Column(name="good")
    private Long good;   
	
	public TaskUser() {}
	
	public TaskUser (Task task, Long good) {
	        this.task = task;
	        this.good = good;
	    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getGood() {
		return good;
	}

	public void setGood(Long good) {
		this.good = good;
	}
}
