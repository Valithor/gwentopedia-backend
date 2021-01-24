package com.gwentopedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gwentopedia.model.Leader;

public interface LeaderRepository extends JpaRepository<Leader, Long>{
	Leader findByName(String name);
	   @Query("select l.name from Leader l")
	   List<String> getNames();
}
