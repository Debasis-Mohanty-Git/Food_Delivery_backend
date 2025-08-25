package com.foodOredr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodOredr.model.IngredientsItem;

@Repository
public interface IngredientsItemRepository extends JpaRepository<IngredientsItem, Long>{

	public List<IngredientsItem> findByRestaurantId(Long id);
}
