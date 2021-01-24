package com.gwentopedia.util;

import com.gwentopedia.model.*;
import com.gwentopedia.payload.TaskCardResponse;
import com.gwentopedia.payload.TaskResponse;
import com.gwentopedia.payload.TaskTypeResponse;
import com.gwentopedia.payload.UserSummary;
import com.gwentopedia.payload.CardResponse;
import com.gwentopedia.payload.LeaderResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {
	

    public static TaskResponse mapTaskToTaskResponse(Task task, User creator, Long correct) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setName(task.getName());
        taskResponse.setDifficulty(task.getDifficulty());
        taskResponse.setCreationDateTime(task.getCreatedAt());

        List<TaskCardResponse> taskCardResponses = task.getTaskCards().stream().map(taskCard -> {
        	TaskCardResponse taskCardResponse = new TaskCardResponse();
        	Card card = taskCard.getCard();        	
        	CardResponse cardResponse = new CardResponse(card.getId(), card.getName(), card.getCategory(), card.getCategoryId(), card.getFaction(), card.getFactionSecondary(), card.getKeyword(), card.getKeywordsHTML(), card.getRelated(), card.getPower(), card.getArmor(), card.getProvision(), card.getColor(), card.getType(), card.getAvailability(), card.getRarity(), card.getArtid(), card.getAbility(), card.getAbilityHTML(), card.getFlavor());
        	taskCardResponse.setId(taskCard.getId());
            taskCardResponse.setCard(cardResponse);
            taskCardResponse.setSide(taskCard.getSide());
            taskCardResponse.setStrength(taskCard.getStrength());
            taskCardResponse.setCorrect(taskCard.getCorrect());
            taskCardResponse.setAnswer(taskCard.getAnswer());         
            return taskCardResponse;
        }).collect(Collectors.toList());
        
        TaskTypeResponse taskTypeResponse = new TaskTypeResponse(task.getTaskType().getId(), task.getTaskType().getName());
        LeaderResponse leaderPlResponse = new LeaderResponse(task.getLeaderPl().getId(), task.getLeaderPl().getName(), task.getLeaderPl().getProvisions(), task.getLeaderPl().getAbility(), task.getLeaderPl().getImgurl());
        LeaderResponse leaderOppResponse = new LeaderResponse(task.getLeaderOpp().getId(), task.getLeaderOpp().getName(), task.getLeaderOpp().getProvisions(), task.getLeaderOpp().getAbility(), task.getLeaderOpp().getImgurl());
        taskResponse.setLeaderPl(leaderPlResponse);
        taskResponse.setLeaderOpp(leaderOppResponse);
        taskResponse.setTaskType(taskTypeResponse);
        taskResponse.setTaskCards(taskCardResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getGogName(), creator.getAvatar(), null);
        taskResponse.setCreatedBy(creatorSummary);
        
        if(correct != null) {
            taskResponse.setCorrect(correct);
        }
        
        return taskResponse;
    }
}