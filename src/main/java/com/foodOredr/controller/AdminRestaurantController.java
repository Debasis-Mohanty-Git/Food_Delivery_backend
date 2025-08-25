package com.foodOredr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodOredr.model.Restaurant;
import com.foodOredr.model.User;
import com.foodOredr.request.CreateRestaurantRequest;
import com.foodOredr.response.MessageResponse;
import com.foodOredr.service.RestaurantService;
import com.foodOredr.service.UserService;

@RestController
@RequestMapping("/api/admin/restaurant")
public class AdminRestaurantController {
	
	@Autowired
	private RestaurantService restaurantservice;
	@Autowired	
	private UserService userservice;
	
	@PostMapping("/created")
	public ResponseEntity<Restaurant> createRestaurant(
			@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantservice.createRestaurant(req, user);
		
		return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(
			@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantservice.updateRestaurant(id, req);
		
		return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse> deleteRestaurant(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		restaurantservice.deleteRestaurant(id);
		
		MessageResponse res=new MessageResponse();
		res.setMessage("Restaurant is Deleted Successfully....");
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	@PutMapping("/status/{id}")
	public ResponseEntity<Restaurant> updateRestaurantStatus(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantservice.updateRestaurantStatus(id);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantByUserId(
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		
		User user=userservice.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantservice.findRestaurantById(user.getId());
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
	}

}
