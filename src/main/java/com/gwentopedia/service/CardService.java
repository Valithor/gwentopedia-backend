package com.gwentopedia.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gwentopedia.exception.BadRequestException;
import com.gwentopedia.model.Card;
import com.gwentopedia.payload.CardRequest;
import com.gwentopedia.payload.CardResponse;
import com.gwentopedia.payload.PagedResponse;
import com.gwentopedia.repository.CardRepository;
import com.gwentopedia.security.UserPrincipal;
import com.gwentopedia.util.AppConstants;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
        
    public PagedResponse<CardResponse> getAllCards(UserPrincipal currentUser, int page, int size, String faction ) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("provision").descending().and(Sort.by("color").descending()).and(Sort.by("type").ascending()).and(Sort.by("power").descending()).and(Sort.by("faction").descending()).and(Sort.by("rarity").descending()));
        Page<Card> cards;
        if(faction.equals("all"))
         cards = cardRepository.findAll(pageable);
        else
        	cards = cardRepository.findByFaction(faction, pageable);

        if(cards.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), cards.getNumber(),
                    cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
        }

        List<CardResponse> cardResponses = cards.map(card -> {
        	return new CardResponse(card.getId(), card.getName(), card.getCategory(), card.getCategoryId(), card.getFaction(), card.getFactionSecondary(), card.getKeyword(), card.getKeywordsHTML(), card.getRelated(), card.getPower(), card.getArmor(), card.getProvision(), card.getColor(), card.getType(), card.getAvailability(), card.getRarity(), card.getArtid(), card.getAbility(), card.getAbilityHTML(), card.getFlavor());
        }).getContent();

        return new PagedResponse<>(cardResponses, cards.getNumber(),
                cards.getSize(), cards.getTotalElements(), cards.getTotalPages(), cards.isLast());
    }

	 public Card createCard(CardRequest cardRequest) { 
		  	Card card;
		 if(cardRepository.existsByName(cardRequest.getName())) {
			 card=cardRepository.findByName(cardRequest.getName());
		 }
		 else {
			 card = new Card();
			  	card.setId(cardRequest.getId());
		 }
			 
				  	card.setName(cardRequest.getName());
				  	card.setCategory(cardRequest.getCategory());
				  	card.setCategoryId(cardRequest.getCategoryId());
				  	card.setFaction(cardRequest.getFaction());
				  	card.setFactionSecondary(cardRequest.getFactionSecondary());
				  	card.setKeyword(cardRequest.getKeyword());
				  	card.setKeywordsHTML(cardRequest.getKeywordsHTML());
				  	card.setRelated(cardRequest.getRelated());
				  	card.setPower(cardRequest.getPower()); 
				  	card.setArmor(cardRequest.getArmor());
				  	card.setProvision(cardRequest.getProvision());
				  	card.setColor(cardRequest.getColor());
				  	card.setType(cardRequest.getType());
				  	card.setAvailability(cardRequest.getAvailability());
				  	card.setRarity(cardRequest.getRarity());
				  	card.setArtid(cardRequest.getArtid());
				  	card.setAbility(cardRequest.getAbility());
				  	card.setAbilityHTML(cardRequest.getAbilityHTML());
				  	card.setFlavor(cardRequest.getFlavor());
				  	 return cardRepository.save(card);
					 
	 
	    }
	 
	    private void validatePageNumberAndSize(int page, int size) {
	        if(page < 0) {
	            throw new BadRequestException("Page number cannot be less than zero.");
	        }

	        if(size > AppConstants.MAX_PAGE_SIZE) {
	            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
	        }
	    }
}
