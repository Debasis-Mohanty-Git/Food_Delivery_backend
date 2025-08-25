package com.foodOredr.request;

import com.foodOredr.model.Address;

public class OrderRequest {
	
	private Long restaurantId;
	private Address deliveryAddress;
	public OrderRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderRequest(Long restaurantId, Address deliveryAddress) {
		super();
		this.restaurantId = restaurantId;
		this.deliveryAddress = deliveryAddress;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

}
