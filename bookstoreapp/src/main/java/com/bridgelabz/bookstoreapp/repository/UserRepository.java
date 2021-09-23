package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);

    Optional<User> findUserByEmailId(String email);
}
