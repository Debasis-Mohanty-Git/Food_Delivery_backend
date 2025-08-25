package com.foodOredr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodOredr.model.CartItem;
import com.foodOredr.model.Order;
import com.foodOredr.model.User;
import com.foodOredr.request.AddCartItemrequest;
import com.foodOredr.request.OrderRequest;
import com.foodOredr.response.PaymentResponse;
import com.foodOredr.service.OrderService;
import com.foodOredr.service.PaymentService;
import com.foodOredr.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderService ordseService;
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/order")
	public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req,
	                                         @RequestHeader("Authorization") String jwt) throws Exception {
	    User user = userService.findUserByJwtToken(jwt);
	    Order order = ordseService.createOrder(req, user);
	    PaymentResponse response=paymentService.createPaymentLink(order);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(
			@RequestHeader("Authorization")String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		List<Order> order=ordseService.getUserOrder(user.getId());
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	
	
	
	
}
