package com.foodOredr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodOredr.model.Category;
import com.foodOredr.model.Restaurant;
import com.foodOredr.repository.CategoryRepository;

@Service
public class CategoryServiceImplementation implements CategoryService{
	
	@Autowired
	private RestaurantService restaurantservice;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		
		Restaurant restaurant=restaurantservice.getRestaurantByUserId(userId);
		Category category=new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
		Restaurant restaurant=restaurantservice.getRestaurantByUserId(id);
		return categoryRepository.findByRestaurantId(id);
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
		
		Optional<Category> opt=categoryRepository.findById(id);
		
		if(opt.isEmpty()) {
			throw new Exception("Category not found...");
		}
		return opt.get();
	}

}
