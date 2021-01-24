package com.gwentopedia.model;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="cards")
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	@NotBlank
	@Column(name="name")
    private String name;
	@Column
	private String category;
	@Column
	private String categoryId;
	@NotBlank
	@Column(name="faction")
    private String faction;
	@Column
	private String factionSecondary;
	@Column
	private String keyword;
	@Column
	private String keywordsHTML;
	@Column
	private String related;
	@Column
	private long power;
	@Column
	private long armor;
	@Column
	private long provision;
	@Column
	private String color;
	@Column
	private String type;
	@Column
	private String availability;
	@Column
	private String rarity;
	@Column
	private String artid;	
	@Column(name="ability")
    private String ability;
	@Column
	private String abilityHTML;
	@Column
	private String flavor;	
	@JsonManagedReference
	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<TaskCard> taskCards;
	
	public Card() {}
	
	public Card(@NotBlank String name, String category, String categoryId, @NotBlank String faction,
			String factionSecondary, String keyword, String keywordsHTML, String related, long power, long armor,
			long provision, String color, String type, String availability, String rarity, String artid,
			String ability, String abilityHTML, String flavor, TaskCard... taskCards) {
		this.name = name;
		this.category = category;
		this.categoryId = categoryId;
		this.faction = faction;
		this.factionSecondary = factionSecondary;
		this.keyword = keyword;
		this.keywordsHTML = keywordsHTML;
		this.related = related;
		this.power = power;
		this.armor = armor;
		this.provision = provision;
		this.color = color;
		this.type = type;
		this.availability = availability;
		this.rarity = rarity;
		this.artid = artid;
		this.ability = ability;
		this.abilityHTML = abilityHTML;
		this.flavor = flavor;
        this.taskCards = Stream.of(taskCards).collect(Collectors.toList());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getFactionSecondary() {
		return factionSecondary;
	}

	public void setFactionSecondary(String factionSecondary) {
		this.factionSecondary = factionSecondary;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeywordsHTML() {
		return keywordsHTML;
	}

	public void setKeywordsHTML(String keywordsHTML) {
		this.keywordsHTML = keywordsHTML;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public long getArmor() {
		return armor;
	}

	public void setArmor(long armor) {
		this.armor = armor;
	}

	public long getProvision() {
		return provision;
	}

	public void setProvision(long provision) {
		this.provision = provision;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getArtid() {
		return artid;
	}

	public void setArtid(String artid) {
		this.artid = artid;
	}

	public String getAbilityHTML() {
		return abilityHTML;
	}

	public void setAbilityHTML(String abilityHTML) {
		this.abilityHTML = abilityHTML;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public List<TaskCard> getTaskCards() {
		return taskCards;
	}

	public void setTaskCards(List<TaskCard> taskCards) {
		this.taskCards = taskCards;
	}

    
}