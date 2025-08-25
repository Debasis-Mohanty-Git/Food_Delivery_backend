package com.foodOredr.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodOredr.model.Cart;
import com.foodOredr.model.CartItem;
import com.foodOredr.model.Food;
import com.foodOredr.model.User;
import com.foodOredr.repository.CartItemRepository;
import com.foodOredr.repository.CartRepository;
import com.foodOredr.request.AddCartItemrequest;

@Service
public class CartServiceImplementation implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CartItemRepository cartItemrepository;
	@Autowired
	private FoodService foodService;

	@Override
	public CartItem addItemTocart(AddCartItemrequest req, String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);

	    Food food = foodService.findFoodById(req.getFoodId());

	    Cart cart = cartRepository.findByCustomerId(user.getId());
	    if (cart == null) {
	        cart = new Cart();
	        cart.setCustomer(user);
	        cart.setItem(new ArrayList<>());
	        cartRepository.save(cart);
	    }

	    // Check if food already exists in cart
	    for (CartItem cartItem : cart.getItem()) {
	        if (cartItem.getFood() != null && cartItem.getFood().getId().equals(food.getId())) {
	            int newQuantity = cartItem.getQuantity() + req.getQuantity();
	            return updatecartItemQuantity(cartItem.getId(), newQuantity);
	        }
	    }

	    // Create new cart item
	    CartItem newCartItem = new CartItem();
	    newCartItem.setFood(food);
	    newCartItem.setCart(cart);
	    newCartItem.setQuantity(req.getQuantity());
	    newCartItem.setIngredients(req.getIngredients() != null ? req.getIngredients() : new ArrayList<>());
	    newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());

	    CartItem savedCartItem = cartItemrepository.save(newCartItem);
	    cart.getItem().add(savedCartItem);
	    cartRepository.save(cart);

	    return savedCartItem;
	}



	@Override
	public CartItem updatecartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItem> opt=cartItemrepository.findById(cartItemId);
		if(opt.isEmpty()) {
			throw new Exception("Cart Item not found...");
		}
		CartItem item=opt.get();
		item.setQuantity(quantity);
		
		item.setTotalPrice(item.getFood().getPrice()*quantity);
		return cartItemrepository.save(item);
	}

	
	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user=userService.findUserByJwtToken(jwt);
		Cart cart=cartRepository.findByCustomerId(user.getId());
		
		Optional<CartItem> opt=cartItemrepository.findById(cartItemId);
		if(opt.isEmpty()) {
			throw new Exception("Cart Item not found...");
		}
		CartItem item=opt.get();
		cart.getItem().remove(item);
		
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total=0L;
		
		for(CartItem cartItem:cart.getItem()) {
			total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optCart=cartRepository.findById(id);
		if(optCart.isEmpty()) {
			throw new Exception("Cart not found with this id: "+id);
		}
		return optCart.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		
//		User user=userService.findUserByJwtToken(userId);
		Cart cart=cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotals(cart));
		
	return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
//		User user=userService.findUserByJwtToken(userId);
		Cart cart=findCartById(userId);
		cart.getItem().clear();
		
		return cartRepository.save(cart);
	}
	

}
