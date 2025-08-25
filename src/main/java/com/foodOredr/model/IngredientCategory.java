package com.foodOredr.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class IngredientCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JsonIgnore
	private Restaurant restaurant;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category" ,cascade = CascadeType.ALL)
	private List<IngredientsItem> ingredientsItem=new ArrayList<>();

	public IngredientCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IngredientCategory(Long id, String name, Restaurant restaurant, List<IngredientsItem> ingredientsItem) {
		super();
		this.id = id;
		this.name = name;
		this.restaurant = restaurant;
		this.ingredientsItem = ingredientsItem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<IngredientsItem> getIngredientsItem() {
		return ingredientsItem;
	}

	public void setIngredientsItem(List<IngredientsItem> ingredientsItem) {
		this.ingredientsItem = ingredientsItem;
	}

}
