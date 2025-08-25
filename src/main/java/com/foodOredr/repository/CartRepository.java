package com.foodOredr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodOredr.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	public Cart findByCustomerId(Long userId); 

}
