package com.foodOredr.service;

import com.foodOredr.model.Order;
import com.foodOredr.response.PaymentResponse;

public interface PaymentService {

	public PaymentResponse createPaymentLink(Order order);
}
