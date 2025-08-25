package com.foodOredr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodOredr.model.Category;
import com.foodOredr.model.Food;
import com.foodOredr.model.Restaurant;
import com.foodOredr.request.CreateFoodRequest;

@Service
public interface FoodService {
	
	public Food createFood(CreateFoodRequest req,Category category,Restaurant restaurant);

	public void deleteFood(Long foodId)throws Exception;
	
	public List<Food> getRestaurantFood(Long restaurantId,
			boolean isVegetarian,
			boolean isNonveg,
			boolean isSeasonal,
			String foodCategory);

	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId)throws Exception;
	
	public Food updateAvailabilityStatus(Long foodId)throws Exception;
}