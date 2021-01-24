package com.gwentopedia.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwentopedia.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	    Page<Task> findByCreatedBy(Long userId, org.springframework.data.domain.Pageable pageable);

	    long countByCreatedBy(Long userId);

	    List<Task> findByIdIn(List<Long> taskids);

	    List<Task> findByIdIn(List<Long> taskIds, Sort sort);
	    
	    Page<Task> findByDifficultyInAndLeaderPl_NameInAndLeaderOpp_NameInAndCreatedByNotAndIdNotIn(List<String> difficulties, List<String> leaderPl, List<String> leaderOpp, Long userId, List<Long> taskIds, Pageable pageable);

}