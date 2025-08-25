package com.foodOredr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodOredr.model.Order;
import com.foodOredr.model.User;
import com.foodOredr.service.OrderService;
import com.foodOredr.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderService ordseService;
	
	@GetMapping("/order/restaurant/{restaurantId}")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestParam(required = false) String order_status,
			@RequestHeader("Authorization")String jwt,
			@PathVariable Long restaurantId) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		List<Order> order=ordseService.getRestaurantOrder(restaurantId, order_status);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("/order/{orderId}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(
			@PathVariable String orderStatus,
			@RequestHeader("Authorization")String jwt,
			@PathVariable Long orderId) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Order order=ordseService.updateOrder(orderId, orderStatus);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
}
