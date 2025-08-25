package com.foodOredr.service;

import java.util.List;

import com.foodOredr.model.Event;

public interface EventService {
	
	public Event createEvent(Event event,Long restaurantId);
    public void deleteEvent(Long eventId) throws Exception;
    public List<Event> getAllEvents();
    public List<Event> getRestaurantEvents(Long restaurantId);
	
}
