package com.gwentopedia.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwentopedia.model.TaskType;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long>{
	TaskType findByName(String name);

}