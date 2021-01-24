package com.gwentopedia.service;

import com.gwentopedia.exception.BadRequestException;
import com.gwentopedia.exception.ResourceNotFoundException;
import com.gwentopedia.model.*;
import com.gwentopedia.payload.PagedResponse;
import com.gwentopedia.payload.TaskRequest;
import com.gwentopedia.payload.TaskResponse;
import com.gwentopedia.payload.TaskUserRequest;
import com.gwentopedia.repository.TaskRepository;
import com.gwentopedia.repository.TaskTypeRepository;
import com.gwentopedia.repository.TaskUserRepository;
import com.gwentopedia.repository.UserRepository;
import com.gwentopedia.repository.CardRepository;
import com.gwentopedia.repository.LeaderRepository;
import com.gwentopedia.security.UserPrincipal;
import com.gwentopedia.util.AppConstants;
import com.gwentopedia.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class TaskService {
	
	@Autowired
	private TaskTypeRepository taskTypeRepository;
	
	@Autowired
	private TaskUserRepository taskUserRepository;

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LeaderRepository leaderRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public PagedResponse<TaskResponse> getAllTasksAdmin(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);
        
        // Retrieve tasks
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Task> tasks = taskRepository.findAll(pageable);

        if (tasks.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), tasks.getNumber(),
                    tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
        }         
        
        List<Long> taskIds = tasks.map(Task::getId).getContent();
        
        Map<Long, Long> taskUserMap = currentUser!=null?getTaskUserMap(currentUser.getId()==null?null:currentUser.getId(), taskIds):null;

        Map<Long, User> creatorMap = getTaskCreatorMap(tasks.getContent());

        List<TaskResponse> taskResponses = tasks.map(task -> {
            return ModelMapper.mapTaskToTaskResponse(task,
                    creatorMap.get(task.getCreatedBy()),
                    taskUserMap == null ? null : taskUserMap.getOrDefault(task.getId(), null)
                    );
        }).getContent();

        return new PagedResponse<>(taskResponses, tasks.getNumber(),
                tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
    }
    
    public PagedResponse<TaskResponse> getAllTasks(UserPrincipal currentUser, int page, int size, List<String> difficulty, List<String> leaderPl, List<String> leaderOpp) {
        validatePageNumberAndSize(page, size);
        if(leaderPl.contains("Any"))
            leaderPl=leaderRepository.getNames();

        if(leaderOpp.contains("Any"))
            leaderOpp=leaderRepository.getNames();
        
        // Retrieve tasks
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        List<Long> answeredTasks;
        if(currentUser!=null) {
        Page<Long> userAnsweredTaskIds = taskUserRepository.findAnsweredByUser(currentUser.getId(), pageable);
        answeredTasks = userAnsweredTaskIds.getContent();

        if (userAnsweredTaskIds.getNumberOfElements() == 0) {
        	answeredTasks =new ArrayList<>();
            answeredTasks.add((long) 0);
        }
        }
        else {answeredTasks =new ArrayList<>();
        answeredTasks.add((long) 0);}
        Page<Task> tasks = taskRepository.findByDifficultyInAndLeaderPl_NameInAndLeaderOpp_NameInAndCreatedByNotAndIdNotIn(difficulty, leaderPl, leaderOpp, currentUser!=null?currentUser.getId():0, answeredTasks, pageable);       
             
        
        List<Long> taskIds = tasks.map(Task::getId).getContent();
        
        Map<Long, Long> taskUserMap = currentUser!=null?getTaskUserMap(currentUser.getId()==null?null:currentUser.getId(), taskIds):null;

        Map<Long, User> creatorMap = getTaskCreatorMap(tasks.getContent());

        List<TaskResponse> taskResponses = tasks.map(task -> {
            return ModelMapper.mapTaskToTaskResponse(task,
                    creatorMap.get(task.getCreatedBy()),
                    taskUserMap == null ? null : taskUserMap.getOrDefault(task.getId(), null)
                    );
        }).getContent();

        return new PagedResponse<>(taskResponses, tasks.getNumber(),
                tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
    }

    public PagedResponse<TaskResponse> getTasksCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all tasks created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Task> tasks = taskRepository.findByCreatedBy(user.getId(), pageable);

        if (tasks.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), tasks.getNumber(),
                    tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
        }
        List<Long> taskIds = tasks.map(Task::getId).getContent();
        
        Map<Long, Long> taskUserMap = currentUser!=null?getTaskUserMap(currentUser.getId()==null?null:currentUser.getId(), taskIds):null;


        List<TaskResponse> taskResponses = tasks.map(task -> {
            return ModelMapper.mapTaskToTaskResponse(task, user, taskUserMap == null ? null : taskUserMap.getOrDefault(task.getId(), null));
        }).getContent();

        return new PagedResponse<>(taskResponses, tasks.getNumber(),
                tasks.getSize(), tasks.getTotalElements(), tasks.getTotalPages(), tasks.isLast());
    }
    
    public PagedResponse<TaskResponse> getTasksAnsweredBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all tasks created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Long> userAnsweredTaskIds = taskUserRepository.findAnsweredByUser(user.getId(), pageable);

        if (userAnsweredTaskIds.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), userAnsweredTaskIds.getNumber(),
            		userAnsweredTaskIds.getSize(), userAnsweredTaskIds.getTotalElements(),
            		userAnsweredTaskIds.getTotalPages(), userAnsweredTaskIds.isLast());
        }
        List<Long> taskIds = userAnsweredTaskIds.getContent();

        Sort sort = Sort.by(Sort.Direction.DESC, "taskUsers.createdAt");
        List<Task> tasks = taskRepository.findByIdIn(taskIds, sort);
        tasks = tasks.stream().limit(userAnsweredTaskIds.getTotalElements()).collect(Collectors.toList());

        Map<Long, Long> taskUserMap = getTaskUserMap(user.getId(), taskIds);
        
        Map<Long, User> creatorMap = getTaskCreatorMap(tasks);

        List<TaskResponse> taskResponses = tasks.stream().map(task -> {
            return ModelMapper.mapTaskToTaskResponse(task, creatorMap.get(task.getCreatedBy()), taskUserMap == null ? null : taskUserMap.getOrDefault(task.getId(), null));
        }).collect(Collectors.toList());

        return new PagedResponse<>(taskResponses, userAnsweredTaskIds.getNumber(),
        		userAnsweredTaskIds.getSize(), userAnsweredTaskIds.getTotalElements(), userAnsweredTaskIds.getTotalPages(), userAnsweredTaskIds.isLast());
    }


    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setName(taskRequest.getName());
        task.setDifficulty(taskRequest.getDifficulty());
       
		  taskRequest.getTaskCards().forEach(taskCardRequest ->{
			  TaskCard taskCard = new TaskCard();
		    	Card card = cardRepository.findByName(taskCardRequest.getCard().getName());
		    	taskCard.setCard(card);
		    	if(taskCardRequest.getAnswer()!=null)
		    	taskCard.setAnswer(taskCardRequest.getAnswer());
		    	taskCard.setCorrect(taskCardRequest.getCorrect());
		    	taskCard.setStrength(taskCardRequest.getStrength());
		    	taskCard.setSide(taskCardRequest.getSide()); 
		    	task.addTaskCard(taskCard);
		  }); 
		 
        TaskType selectedTaskType = taskTypeRepository.findByName(taskRequest.getTaskType());
        task.setTaskType(selectedTaskType);
        Leader selectedLeaderPl = leaderRepository.findByName(taskRequest.getLeaderPl());
        task.setLeaderPl(selectedLeaderPl);
        Leader selectedLeaderOpp = leaderRepository.findByName(taskRequest.getLeaderOpp());
        task.setLeaderOpp(selectedLeaderOpp);
        
        return taskRepository.save(task);
    }

    public TaskResponse getTaskById(Long taskId, UserPrincipal currentUser) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ResourceNotFoundException("Task", "id", taskId));

        User creator = userRepository.findById(task.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", task.getCreatedBy()));
        List<Long> taskIds = List.of(taskId);
        Map<Long, Long> taskUserMap = currentUser!=null?getTaskUserMap(currentUser.getId()==null?null:currentUser.getId(), taskIds):null;

        return ModelMapper.mapTaskToTaskResponse(task, creator, taskUserMap == null ? null : taskUserMap.getOrDefault(task.getId(), null));
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
    
    
    public void saveAnswer(Long taskId, TaskUserRequest taskUserRequest, UserPrincipal currentUser) {
    	Task task = taskRepository.findById(taskId)
    			.orElseThrow(()-> new ResourceNotFoundException("Task", "id", taskId));
    	if(task.getCreatedBy().equals(currentUser.getId())) {
            logger.info("User {} has has created Task {}", currentUser.getId(), taskId);
            throw new BadRequestException("Sorry! You creator can't solve his tasks");   		

    	}    	
        User user = userRepository.getOne(currentUser.getId());
        TaskUser taskUser = new TaskUser();
        taskUser.setGood(taskUserRequest.getGood());
        taskUser.setTask(task);
        taskUser.setUser(user);
        
        try {
            taskUser = taskUserRepository.save(taskUser);
        } catch (DataIntegrityViolationException ex) {
            logger.info("User {} has already completed Task {}", currentUser.getId(), taskId);
            throw new BadRequestException("Sorry! You have already answered this task");
        }    	
    }
    
    
    private Map<Long, Long> getTaskUserMap(Long userId, List<Long> taskIds) {
        Map<Long, Long> taskUserMap = null;
        if(userId != null) {
            List<TaskUser> taskUsers = taskUserRepository.findByUserIdAndTaskIdIn(userId, taskIds);

            taskUserMap = taskUsers.stream()
                    .collect(Collectors.toMap(taskUser -> taskUser.getTask().getId(), taskUser -> taskUser.getGood()));
        }
        return taskUserMap;
    }


    Map<Long, User> getTaskCreatorMap(List<Task> polls) {
        // Get Poll Creator details of the given list of polls
        List<Long> creatorIds = polls.stream()
                .map(Task::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}
