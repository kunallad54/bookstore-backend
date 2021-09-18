package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRegistration,Integer> {

    Optional<UserRegistration> findByEmailId(String emailId);
}
