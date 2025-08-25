package com.foodOredr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodOredr.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
	public List<Event> findByRestaurantId(Long restaurantId);

}
