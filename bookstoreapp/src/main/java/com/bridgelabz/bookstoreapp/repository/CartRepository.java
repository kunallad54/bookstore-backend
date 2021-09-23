package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.Cart;
import com.bridgelabz.bookstoreapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findById(int id);

    List<Cart> findAllByUser(User user);
}
