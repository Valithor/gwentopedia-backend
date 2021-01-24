package com.gwentopedia.model;
import java.util.Objects;
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
@Table(name="task_type")
public class TaskType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@NotBlank
	@Column(name="name")
    private String name;
	@OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "taskType")
    private Set<Task> tasks;
	
	public TaskType() {}
	
	public TaskType(String name) {
		super();
		this.name = name;
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
	public void addTask(Task task) {
		 tasks.add(task);
		 task.setTaskType(this);
	}
	public void removeTask(Task task) {
		tasks.add(task);
		task.setTaskType(null);
	}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskType taskType = (TaskType) o;
        return Objects.equals(id, taskType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
