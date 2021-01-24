package com.gwentopedia.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gwentopedia.model.TaskUser;
import com.gwentopedia.model.User;

@Repository
public interface TaskUserRepository extends JpaRepository<TaskUser, Long> {
    
	@Query("SELECT t.task.id FROM TaskUser t WHERE t.user.id = :userId")
	Page<Long> findAnsweredByUser(@Param("userId") Long userId, Pageable pageable);
	
    @Query("SELECT t FROM TaskUser t where t.user.id = :userId and t.task.id in :taskIds")
    List<TaskUser> findByUserIdAndTaskIdIn(@Param("userId") Long userId, @Param("taskIds") List<Long> taskIds);

	
    Page<Long> findByUser(User user, Pageable pageable);

}
