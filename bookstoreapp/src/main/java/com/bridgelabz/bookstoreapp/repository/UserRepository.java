package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {

    Optional<UserModel> findByEmailId(String emailId);
}
