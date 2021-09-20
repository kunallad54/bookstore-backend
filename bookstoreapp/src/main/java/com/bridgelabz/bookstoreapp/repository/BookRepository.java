package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookModel,Integer> {

    @Query(value = "select * from book where name=:name", nativeQuery = true)
    Optional<BookModel> findByName(String name);
}
