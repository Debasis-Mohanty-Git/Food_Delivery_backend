package com.foodOredr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodOredr.model.Event;
import com.foodOredr.model.Restaurant;
import com.foodOredr.repository.EventRepository;
import com.foodOredr.repository.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventServiceImplementation implements EventService{
	
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	

	@Override
	public Event createEvent(Event event,Long restaurantId) {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId));
		
		event.setRestaurant(restaurant);
		return eventRepository.save(event);
	}

	@Override
	public void deleteEvent(Long eventId) throws Exception {
		 if (!eventRepository.existsById(eventId)) {
	            throw new EntityNotFoundException("Event not found with id: " + eventId);
	        }
	        eventRepository.deleteById(eventId);
	}
	
	@Override
	public List<Event> getAllEvents() {
		
		return eventRepository.findAll();
	}

	@Override
	public List<Event> getRestaurantEvents(Long restaurantId) {
		// TODO Auto-generated method stub
		return eventRepository.findByRestaurantId(restaurantId);
	}

}
