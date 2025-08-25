package com.foodOredr.request;

import java.util.List;

public class AddCartItemrequest {
	
	private Long foodId;
	private int quantity;
	private List<String> ingredients;
	public AddCartItemrequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddCartItemrequest(Long foodId, int quantity, List<String> ingredients) {
		super();
		this.foodId = foodId;
		this.quantity = quantity;
		this.ingredients = ingredients;
	}
	public Long getFoodId() {
		return foodId;
	}
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

}
