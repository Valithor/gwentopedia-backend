package com.gwentopedia.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="leader")
public class Leader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@NotBlank
	@Column(name="name")
    private String name;
	@NotBlank
	@Column(name="provisions")
    private String provisions;
	@NotBlank
	@Column(name="ability")
    private String ability;	
	@NotBlank
	@Column(name="imgurl")
    private String imgurl;
	@OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "taskType")
    private Set<Task> tasksPl;
	@OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "taskType")
    private Set<Task> tasksOpp;
	public Leader() {}
	public Leader(@NotBlank String name, @NotBlank String provisions, @NotBlank String ability, @NotBlank String imgurl,
			Set<Task> tasksPl, Set<Task> tasksOpp) {
		super();
		this.name = name;
		this.provisions = provisions;
		this.ability = ability;
		this.imgurl = imgurl;
		this.tasksPl = tasksPl;
		this.tasksOpp = tasksOpp;
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
	public String getProvisions() {
		return provisions;
	}
	public void setProvisions(String provisions) {
		this.provisions = provisions;
	}
	public String getAbility() {
		return ability;
	}
	public void setAbility(String ability) {
		this.ability = ability;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public void addTasksPl(Task task) {
		tasksPl.add(task);
		task.setLeaderPl(this);
	}
	public void removeTasksPl(Task task) {
		tasksPl.add(task);
		task.setLeaderPl(null);
	}
	
	public void addTasksOpp(Task task) {
		tasksOpp.add(task);
		task.setLeaderOpp(this);
	}
	public void removeTasksOpp(Task task) {
		tasksOpp.add(task);
		task.setLeaderOpp(null);
	}
}