package com.foodOredr.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodOredr.model.Event;
import com.foodOredr.response.MessageResponse;
import com.foodOredr.service.EventService;

@RestController
@RequestMapping("/api")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@PostMapping("/admin/event/restaurant/status/{restaurantId}")
	public ResponseEntity<Event> createEvent(
			@PathVariable Long restaurantId,
			@RequestBody Event event){
		
		Event saveEvent=eventService.createEvent(event, restaurantId);
		return new ResponseEntity<>(saveEvent,HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/event/delete/{eventId}")
	public ResponseEntity<MessageResponse> deleteEvent(
			@PathVariable Long eventId) throws Exception{
		
		eventService.deleteEvent(eventId);
		
		MessageResponse res=new MessageResponse();
		res.setMessage("Event is Deleted Successfully....");
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events,HttpStatus.OK);
    }
	
	 @GetMapping("/admin/event/restaurant/{restaurantId}")
	    public ResponseEntity<List<Event>> getRestaurantEvents(@PathVariable Long restaurantId) {
	        List<Event> events = eventService.getRestaurantEvents(restaurantId);
	        return new ResponseEntity<>(events,HttpStatus.OK);
	    }
	
}
