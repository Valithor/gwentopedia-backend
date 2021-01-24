package com.gwentopedia.controller;

import com.gwentopedia.exception.ResourceNotFoundException;
import com.gwentopedia.model.Role;
import com.gwentopedia.model.RoleName;
import com.gwentopedia.model.Task;
import com.gwentopedia.model.User;
import com.gwentopedia.payload.*;
import com.gwentopedia.repository.RoleRepository;
import com.gwentopedia.repository.TaskRepository;
import com.gwentopedia.repository.UserRepository;
import com.gwentopedia.security.UserPrincipal;
import com.gwentopedia.service.TaskService;
import com.gwentopedia.security.CurrentUser;
import com.gwentopedia.util.AppConstants;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private TaskService taskService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    PagedResponse<User> getAllUsers(@CurrentUser UserPrincipal currentUser,
    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
    @RequestParam(value = "faction", defaultValue = "all") String faction){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<User> users = userRepository.findAll(pageable);
        return new PagedResponse<>(users.getContent(), users.getNumber(),
                users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
	}
    
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getGogName(), currentUser.getAvatar(), currentUser.getAuthorities());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long taskCount = taskRepository.countByCreatedBy(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getGogName(), user.getAvatar(), user.getCreatedAt(), taskCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/tasks")
    public PagedResponse<TaskResponse> getTasksCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return taskService.getTasksCreatedBy(username, currentUser, page, size);
    }
    @GetMapping("/users/{username}/votes")
    public PagedResponse<TaskResponse> getTasksAnswered(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return taskService.getTasksAnsweredBy(username, currentUser, page, size);
    }
	//delete task
	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
		User user = userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	@PutMapping("/user/promote/{id}/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Boolean>> promoteUser(@PathVariable Long id, @PathVariable Long roleId){
		User user = userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		Role role = roleRepository.findById(roleId)
				.orElseThrow(()-> new ResourceNotFoundException("Role", "role name", roleId));
		user.getRoles().add(role);
		userRepository.save(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("promoted", Boolean.TRUE);
		return ResponseEntity.ok(response);

	}

}