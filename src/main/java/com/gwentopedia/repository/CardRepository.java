package com.gwentopedia.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gwentopedia.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	Card findByName(String name);
	Page<Card> findByFaction(String faction, Pageable pageable);
	List<Card> findByOrderByProvisionDescColorDescTypeAscPowerDescFactionDescRarityDesc();
	
	boolean existsByName(String name);
}
