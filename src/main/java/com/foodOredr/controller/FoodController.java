package com.foodOredr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodOredr.model.Food;
import com.foodOredr.model.Restaurant;
import com.foodOredr.model.User;
import com.foodOredr.request.CreateFoodRequest;
import com.foodOredr.service.FoodService;
import com.foodOredr.service.RestaurantService;
import com.foodOredr.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	@Autowired
	private UserService userService;
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(
	        @RequestParam String name,
	        @RequestHeader("Authorization") String jwt) throws Exception {
	    
	    User user = userService.findUserByJwtToken(jwt); // just to validate user
	    List<Food> food = foodService.searchFood(name); // search by name
	    
	    return new ResponseEntity<>(food, HttpStatus.OK);
	}

	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(
			@RequestParam (required = false) boolean vegetarian,
			@RequestParam (required = false) boolean seasonal,
			@RequestParam (required = false) boolean nonveg,
			@RequestParam(required = false) String food_category,
			@PathVariable Long restaurantId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Food> food=foodService.getRestaurantFood(restaurantId, vegetarian, seasonal, nonveg, food_category);
		
		return new ResponseEntity<>(food,HttpStatus.OK);
	}
	
	

}
