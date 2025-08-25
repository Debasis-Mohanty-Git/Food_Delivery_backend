package com.foodOredr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodOredr.model.IngredientCategory;
import com.foodOredr.model.IngredientsItem;

@Service
public interface IngredientsService {

	public IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;
	
	public IngredientCategory findIngredientCategoryById(Long id)throws Exception;
	
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id)throws Exception;
	
	public IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId)throws Exception;
	
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);
	
	public IngredientsItem updateStock(Long id)throws Exception;



}
