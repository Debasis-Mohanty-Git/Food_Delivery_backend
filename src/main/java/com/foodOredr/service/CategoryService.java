package com.foodOredr.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.foodOredr.model.Category;

@Service
public interface CategoryService {

	public Category createCategory(String name,Long userId) throws Exception;
	
	public List<Category> findCategoryByRestaurantId(Long id) throws Exception;
	
	public Category findCategoryById(Long id)throws Exception;
	
}
