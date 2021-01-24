package com.gwentopedia.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import com.sipios.springsearch.anotation.SearchSpec;
import org.springframework.data.jpa.domain.Specification;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gwentopedia.repository.TaskRepository;
import com.gwentopedia.security.CurrentUser;
import com.gwentopedia.security.UserPrincipal;
import com.gwentopedia.service.TaskService;
import com.gwentopedia.util.AppConstants;
import com.gwentopedia.exception.ResourceNotFoundException;
import com.gwentopedia.model.Task;
import com.gwentopedia.model.TaskCard;
import com.gwentopedia.payload.ApiResponse;
import com.gwentopedia.payload.PagedResponse;
import com.gwentopedia.payload.TaskRequest;
import com.gwentopedia.payload.TaskResponse;
import com.gwentopedia.payload.TaskUserRequest;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskRepository taskRepository;
	
	//get all tasks
	@GetMapping
	public PagedResponse<TaskResponse> getTasks(@SearchSpec Specification<Task> specs,
			@CurrentUser UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "difficulty", defaultValue = "${difficulty:Very Easy, Easy, Intermediate, Hard, Very Hard}") List<String> difficulty,
            @RequestParam(value = "leaderPl", defaultValue = "${leaderPl:Any}") List<String> leaderPl,
            @RequestParam(value = "leaderOpp", defaultValue = "${leaderOpp:Any}") List<String> leaderOpp ){
return taskService.getAllTasks(currentUser, page, size, difficulty, leaderPl, leaderOpp);
	}
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public PagedResponse<TaskResponse> getTasksAdmin(@SearchSpec Specification<Task> specs,
			@CurrentUser UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
return taskService.getAllTasksAdmin(currentUser, page, size);
	}
	
	//create tasks rest api
	@PostMapping
    @PreAuthorize("hasRole('AUTHOR')")
	public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest taskRequest) {
		Task task = taskService.createTask(taskRequest);
	     URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest().path("/{id}")
	                .buildAndExpand(task.getId()).toUri();

	        return ResponseEntity.created(location)
	                .body(new ApiResponse(true, "Task Created Successfully"));
	}
	
	//get task by id
	@GetMapping("/{taskId}")
	public TaskResponse getTaskById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long taskId) {
		return taskService.getTaskById(taskId, currentUser);
	}
	
	//update task
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
		Task task = taskRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Task", "id", id));
		task.setName(taskDetails.getName());
		task.setDifficulty(taskDetails.getDifficulty());
		
		Task updatedTask = taskRepository.save(task);
		return ResponseEntity.ok(updatedTask);			
	}
	
	//delete task
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Boolean>> deleteTask(@PathVariable Long id){
		Task task = taskRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Task", "id", id));
		taskRepository.delete(task);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/{id}/cards")
	public List<TaskCard> getTaskCards(@PathVariable Long id) {
		   Task task = taskRepository.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("Task", "id", id));
			if (task != null) {
				return task.getTaskCards();
			} else throw new ResourceNotFoundException("Task", "id", id);
	}
	@GetMapping("/users/{username}")
	public void getTasksAnsweredBy(@CurrentUser UserPrincipal currentUser,
	                         @PathVariable String username,
	                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
	                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
	                         @Valid @RequestBody TaskUserRequest taskUserRequest) {
	        	taskService.getTasksAnsweredBy(username, currentUser, page, size);
	}
	
	@PostMapping("/{taskId}/answers")
	@PreAuthorize("hasRole('USER')")
	public void answerTask(@CurrentUser UserPrincipal currentUser,
	                         @PathVariable Long taskId,
	                         @Valid @RequestBody TaskUserRequest taskUserRequest) {
	        	taskService.saveAnswer(taskId, taskUserRequest, currentUser);
	}
	   
	
}
