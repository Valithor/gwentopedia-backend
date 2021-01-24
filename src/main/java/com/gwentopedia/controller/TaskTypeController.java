package com.gwentopedia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwentopedia.repository.TaskTypeRepository;
import com.gwentopedia.exception.ResourceNotFoundException;
import com.gwentopedia.model.TaskType;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tasktypes")
public class TaskTypeController {
	
	@Autowired
	private TaskTypeRepository taskTypeRepository;
	
	//get all tasktypes
	@GetMapping
	public List<TaskType> getAllTaskTypes(){
		return taskTypeRepository.findAll();
	}
	
	//create takstypes rest api
	@PostMapping
    @PreAuthorize("hasRole('ADMIN')")
	public TaskType createTaskType(@RequestBody TaskType taskType) {
		return taskTypeRepository.save(taskType);
	}
	
	//get tasktype by id
	@GetMapping("/{id}")
	public ResponseEntity<TaskType> getTaskTypeById(@PathVariable Long id) {
		TaskType taskType = taskTypeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("TaskType", "id", id));
		return ResponseEntity.ok(taskType);
	}
	
	//update tasktype
	@PutMapping("/{id}")
	public ResponseEntity<TaskType> updateTaskType(@PathVariable Long id, @RequestBody TaskType taskTypeDetails){
		TaskType taskType = taskTypeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("TaskType", "id", id));
		taskType.setName(taskTypeDetails.getName());		
		TaskType updatedTaskType = taskTypeRepository.save(taskType);
		return ResponseEntity.ok(updatedTaskType);			
	}
	
	//delete tasktype
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTaskType(@PathVariable Long id){
		TaskType taskType = taskTypeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("TaskType", "id", id));
		taskTypeRepository.delete(taskType);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
