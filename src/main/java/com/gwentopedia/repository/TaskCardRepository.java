package com.gwentopedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwentopedia.model.TaskCard;

@Repository
public interface TaskCardRepository extends JpaRepository<TaskCard, Long>{
}
