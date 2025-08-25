package com.foodOredr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodOredr.dto.RestaurantDto;
import com.foodOredr.model.Restaurant;
import com.foodOredr.model.User;
import com.foodOredr.request.CreateRestaurantRequest;

@Service
public interface RestaurantService {

	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	
	public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedRestaurant)throws Exception;
	
	public void deleteRestaurant(Long restaurantId)throws Exception;
	
	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestaurantById(Long id)throws Exception;
	
	public Restaurant getRestaurantByUserId(Long userId)throws Exception;
	
	public RestaurantDto addFavorites(Long restaurantId,User user)throws Exception;
	
	public Restaurant updateRestaurantStatus(Long id)throws Exception;
	
}
