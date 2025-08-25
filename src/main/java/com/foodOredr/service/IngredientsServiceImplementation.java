package com.foodOredr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foodOredr.model.IngredientCategory;
import com.foodOredr.model.IngredientsItem;
import com.foodOredr.model.Restaurant;
import com.foodOredr.repository.IngredientCategoryRepository;
import com.foodOredr.repository.IngredientsItemRepository;

import jakarta.el.ELException;

@Service
public class IngredientsServiceImplementation implements IngredientsService {
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	private IngredientsItemRepository ingredientsItemRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		
		Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
		IngredientCategory ingredientCategory=new IngredientCategory();
		ingredientCategory.setRestaurant(restaurant);
		ingredientCategory.setName(name);
		
		return ingredientCategoryRepository.save(ingredientCategory);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		
		Optional<IngredientCategory> opt=ingredientCategoryRepository.findById(id);
		
		if(opt.isEmpty()) {
			throw new Exception("Ingredient category not found...");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
		restaurantService.findRestaurantById(id);
		return ingredientCategoryRepository.findByRestaurantId(id);
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
	        throws Exception {
	    
	    Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

	    IngredientCategory category = ingredientCategoryRepository.findById(categoryId)
	        .orElseThrow(() -> new Exception("IngredientCategory not found with id: " + categoryId));

	    IngredientsItem item = new IngredientsItem();
	    item.setName(ingredientName);
	    item.setRestaurant(restaurant);
	    item.setCategory(category);

	    IngredientsItem savedItem = ingredientsItemRepository.save(item);

	    category.getIngredientsItem().add(savedItem);

	    return savedItem;
	}


	@Override
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
		
		return ingredientsItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		Optional<IngredientsItem> optionalIngredientsItem=ingredientsItemRepository.findById(id);
		
		if(optionalIngredientsItem.isEmpty()) {
			throw new Exception("ingredient Not found");
		}
		
		IngredientsItem ingredientsItem=optionalIngredientsItem.get();
		ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
		
		return ingredientsItemRepository.save(ingredientsItem);
	}
}
