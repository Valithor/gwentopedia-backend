package com.gwentopedia.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

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

import com.gwentopedia.repository.CardRepository;
import com.gwentopedia.security.CurrentUser;
import com.gwentopedia.security.UserPrincipal;
import com.gwentopedia.service.CardService;
import com.gwentopedia.util.AppConstants;
import com.gwentopedia.exception.ResourceNotFoundException;
import com.gwentopedia.model.Card;
import com.gwentopedia.payload.ApiResponse;
import com.gwentopedia.payload.CardRequest;
import com.gwentopedia.payload.CardResponse;
import com.gwentopedia.payload.PagedResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cards")
public class CardController {
	@Autowired
	private CardService cardService;
	@Autowired
	private CardRepository cardRepository;

	//get all cards
	@GetMapping
	public PagedResponse<CardResponse> getCards(@CurrentUser UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "faction", defaultValue = "all") String faction){
		return cardService.getAllCards(currentUser, page, size, faction);
	}
	
	//create cards rest api
	@PostMapping
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createCards(@Valid @RequestBody CardRequest cardRequest) {
		Card card=cardService.createCard(cardRequest);
	     URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest().path("/{id}")
	                .buildAndExpand(card.getId()).toUri();

	        return ResponseEntity.created(location)
	                .body(new ApiResponse(true, "Card Created Successfully"));
	}
	
	//get card by id
	@GetMapping("/{id}")
	public ResponseEntity<Card> getCardById(@PathVariable Long id) {
		Card card = cardRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Card", "id", id));
		return ResponseEntity.ok(card);
	}
	
	//update card
	@PutMapping("/{id}")
	public ResponseEntity<Card> updateCard(@PathVariable Long id, @RequestBody Card cardDetails){
		Card card = cardRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Card", "id", id));
		card.setName(cardDetails.getName());
		card.setFaction(cardDetails.getFaction());
		card.setAbility(cardDetails.getAbility());
		
		Card updatedCard = cardRepository.save(card);
		return ResponseEntity.ok(updatedCard);			
	}
	
	//delete card
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCard(@PathVariable Long id){
		Card card = cardRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Card", "id", id));
		cardRepository.delete(card);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
