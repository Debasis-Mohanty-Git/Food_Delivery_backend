package com.foodOredr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodOredr.model.Address;
import com.foodOredr.model.Cart;
import com.foodOredr.model.CartItem;
import com.foodOredr.model.Order;
import com.foodOredr.model.Orderitem;
import com.foodOredr.model.Restaurant;
import com.foodOredr.model.User;
import com.foodOredr.repository.AddressRepository;
import com.foodOredr.repository.OrderItemRepository;
import com.foodOredr.repository.OrderRepository;
import com.foodOredr.repository.RestaurantRepository;
import com.foodOredr.repository.UserRepository;
import com.foodOredr.request.OrderRequest;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository; // ✅ Needed to save restaurant

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        // ✅ Validate restaurant ID
        if (order.getRestaurantId() == null) {
            throw new Exception("Restaurant ID cannot be null");
        }

        // ✅ Ensure user address list is not null
        if (user.getAddress() == null) {
            user.setAddress(new ArrayList<>());
        }

        // ✅ Save delivery address
        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);

        if (!user.getAddress().contains(savedAddress)) {
            user.getAddress().add(savedAddress);
            userRepository.save(user);
        }

        // ✅ Fetch restaurant
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        // ✅ Fetch user's cart
        Cart cart = cartService.findCartByUserId(user.getId());
        if (cart == null || cart.getItem() == null || cart.getItem().isEmpty()) {
            throw new Exception("Cart is empty");
        }

        // ✅ Create new order object
        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new java.sql.Date(0));
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        // ✅ Convert cart items to order items
        List<Orderitem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItem()) {
            Orderitem orderItem = new Orderitem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            Orderitem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // ✅ Calculate total
        Long totalPrice = cartService.calculateCartTotals(cart);
        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        // ✅ Save order
        Order savedOrder = orderRepository.save(createdOrder);

        // ✅ Link order to restaurant and persist
        restaurant.getOrders().add(savedOrder);
        restaurantRepository.save(restaurant);

        return savedOrder; // ✅ Return saved order
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findorderById(orderId);

        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")) {

            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        findorderById(orderId); // Ensure order exists
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if (orderStatus != null) {
            orders = orders.stream()
                    .filter(o -> o.getOrderStatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findorderById(Long orderId) throws Exception {
        Optional<Order> opt = orderRepository.findById(orderId);

        if (opt.isEmpty()) {
            throw new Exception("Order not found");
        }
        return opt.get();
    }
}
