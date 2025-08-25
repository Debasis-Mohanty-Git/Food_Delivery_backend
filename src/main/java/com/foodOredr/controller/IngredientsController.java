package com.foodOredr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodOredr.model.IngredientCategory;
import com.foodOredr.model.IngredientsItem;
import com.foodOredr.request.IngredientCategoryRequest;
import com.foodOredr.request.IngredientRequest;
import com.foodOredr.service.IngredientsService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientsController {
	
	@Autowired
	public IngredientsService ingredientsService;
	
	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory(
			@RequestBody IngredientCategoryRequest req) throws Exception{
		
		IngredientCategory item=ingredientsService.createIngredientCategory(req.getName(),req.getRestaurantId());
		
		return new ResponseEntity<>(item,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/created")
	public ResponseEntity<IngredientsItem> createIngredientItem(
	        @RequestBody IngredientRequest req) throws Exception{

	    IngredientsItem item = ingredientsService.createIngredientItem(
	            req.getRestaurantId(),
	            req.getName(),
	            req.getCategoryId());

	    return new ResponseEntity<>(item, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}/stock")
	public ResponseEntity<IngredientsItem> updateIngredientStock(
			@PathVariable Long id) throws Exception{
		
		IngredientsItem item=ingredientsService.updateStock(id);
		return new ResponseEntity<>(item,HttpStatus.OK);
		
	}
	
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
			@PathVariable Long id) throws Exception{
		
		List<IngredientsItem> item=ingredientsService.findRestaurantIngredients(id);
		return new ResponseEntity<>(item,HttpStatus.OK);
		
	}
	
	@GetMapping("/restaurant/{restaurantId}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
			@PathVariable Long restaurantId) throws Exception{
		
		List<IngredientCategory> item=ingredientsService.findIngredientCategoryByRestaurantId(restaurantId);
		return new ResponseEntity<>(item,HttpStatus.OK);
		
	}

}
