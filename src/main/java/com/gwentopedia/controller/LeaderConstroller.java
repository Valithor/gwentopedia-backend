package com.gwentopedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwentopedia.model.Leader;
import com.gwentopedia.repository.LeaderRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/leaders")
public class LeaderConstroller {
	
	@Autowired
	private LeaderRepository leaderRepository;
	
	//get all leaders
	@GetMapping
	public List<Leader> getAllLeaders(){
		return leaderRepository.findAll();
	}
}
