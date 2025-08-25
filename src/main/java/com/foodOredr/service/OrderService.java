package com.foodOredr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.foodOredr.model.Order;
import com.foodOredr.model.User;
import com.foodOredr.request.OrderRequest;

@Service
public interface OrderService {
	
	public Order createOrder(OrderRequest order,User user)throws Exception;
	
	public Order updateOrder(Long orderId,String orderStatus)throws Exception;
	
	public void cancelOrder(Long orderId)throws Exception;
	
	public List<Order> getUserOrder(Long userId)throws Exception;
	
	public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus)throws Exception;

	public Order findorderById(Long orderId)throws Exception;
}
