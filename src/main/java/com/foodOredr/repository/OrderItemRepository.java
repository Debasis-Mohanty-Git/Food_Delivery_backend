package com.foodOredr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodOredr.model.Orderitem;

@Repository
public interface OrderItemRepository extends JpaRepository<Orderitem, Long> {

}
