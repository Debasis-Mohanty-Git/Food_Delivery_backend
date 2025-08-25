package com.foodOredr.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodOredr.dto.RestaurantDto;
import com.foodOredr.model.Address;
import com.foodOredr.model.Restaurant;
import com.foodOredr.model.User;
import com.foodOredr.repository.AddressRepository;
import com.foodOredr.repository.RestaurantRepository;
import com.foodOredr.repository.UserRepository;
import com.foodOredr.request.CreateRestaurantRequest;

@Service
public class RestaurantserviceImplementation implements RestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address address=addressRepository.save(req.getAddress());
		
		Restaurant restaurant=new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisinType(req.getCuisinType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		
		if(restaurant.getCuisinType()!=null) {
			restaurant.setCuisinType(updatedRestaurant.getCuisinType());
		}
		
		if(restaurant.getDescription()!=null) {
			restaurant.setDescription(updatedRestaurant.getDescription());
		}
		
		if(restaurant.getName()!=null) {
			restaurant.setName(updatedRestaurant.getName());
		}
		
		return restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		
		Restaurant restaurant=findRestaurantById(restaurantId);
		
		restaurantRepository.delete(restaurant);
		
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		
		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		
		return restaurantRepository.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		
		Optional<Restaurant> opt=restaurantRepository.findById(id);
		
		if(opt.isEmpty()) {
			throw new Exception("Restaurant Not found With this Id.."+id);
		}
		
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
	    Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
	    if (restaurant == null) {
	        throw new Exception("Restaurant not Found with Owner id..:" + userId);
	    }
	    return restaurant;
	}


	@Override
	public RestaurantDto addFavorites(Long restaurantId, User user) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		
		RestaurantDto dto=new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);
		
		boolean isFavorited=false;
		List<RestaurantDto> favorites=user.getFavourites();
		
		for(RestaurantDto favorite:favorites) {
			if(favorite.getId().equals(restaurantId)) {
				isFavorited=true;
				break;
			}
			
		}
		
		if(isFavorited) {
			favorites.removeIf(favorite->favorite.getId().equals(restaurantId));
		}
		else {
			favorites.add(dto);
		}
			
		
		userRepository.save(user);
		return dto;
	}

//	@Override
//	public Restaurant updateRestaurantStatus(Long id) throws Exception {
//		
//		Restaurant restaurant=findRestaurantById(id);
//		restaurant.setOpen(!restaurant.isOpen());
//		return restaurantRepository.save(restaurant);
//	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) {
	    Restaurant restaurant = restaurantRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Restaurant not found with id " + id));

	    restaurant.setOpen(!restaurant.isOpen()); // toggle
	    return restaurantRepository.save(restaurant);
	}

}
