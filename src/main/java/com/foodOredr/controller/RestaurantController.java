package com.foodOredr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodOredr.dto.RestaurantDto;
import com.foodOredr.model.Restaurant;
import com.foodOredr.model.User;
import com.foodOredr.service.RestaurantService;
import com.foodOredr.service.UserService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantservice;
	@Autowired	
	private UserService userservice;
	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(
			@RequestHeader("Authorization") String jwt,
			@RequestParam String keyword
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		List<Restaurant> restaurant=restaurantservice.searchRestaurant(keyword);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
	}
	
	@GetMapping("/allRestaurant")
	public ResponseEntity<List<Restaurant>> getAllRestaurant(
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		List<Restaurant> restaurant=restaurantservice.getAllRestaurant();
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantservice.findRestaurantById(id);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
	}
	
	@PutMapping("/add-favorites/{id}")
	public ResponseEntity<RestaurantDto> addToFavorites(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		RestaurantDto restaurant=restaurantservice.addFavorites(id, user);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
	}

}
