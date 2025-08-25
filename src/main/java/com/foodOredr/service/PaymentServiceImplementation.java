package com.foodOredr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.foodOredr.model.Order;
import com.foodOredr.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentServiceImplementation implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public PaymentResponse createPaymentLink(Order order) {
        // Set your Stripe secret key
        Stripe.apiKey = stripeSecretKey;

        try {
            // Build Stripe checkout session parameters
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(frontendUrl + "/payment/success/" + order.getId())
                    .setCancelUrl(frontendUrl + "/payment/fail")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("inr")
                                                    .setUnitAmount((long) (order.getTotalPrice() * 100)) // in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("TummyTime")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            // Create session in Stripe
            Session session = Session.create(params);

            // Build response with payment URL
            PaymentResponse response = new PaymentResponse();
            response.setPayment_url(session.getUrl());
            return response;

        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe session: " + e.getMessage(), e);
        }
    }
}
