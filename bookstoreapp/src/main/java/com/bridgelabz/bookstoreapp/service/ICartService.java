package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.entity.Cart;

import java.util.List;

public interface ICartService {

    /**
     * Purpose : To add  cart details in the Cart database after validating cart details and
     * user token and user id needs to be valid
     *
     * @param cartDTO object of CartDTO which carries cart details
     * @param token   input given by user to verify user is verified user or not
     * @param userId  id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @return String object of messages
     */
    String addToCart(CartDTO cartDTO, String token, int userId);

    /**
     * Purpose : Ability to remove item from cart through cart id and user id from database
     * if user token and user id are valid
     *
     * @param token  input given by user to verify user is verified user or not
     * @param cartId id of cart that specify item to be deleted
     * @param userId id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @return String object of messages
     */
    String removeFromCart(String token, int cartId, int userId);

    /**
     * Purpose : To update quantity of item in the cart through its cart id if user token
     * and user id is valid
     *
     * @param token    input given by user to verify user is verified user or not
     * @param cartId   id of cart that specify item to be updated
     * @param quantity updated quantity
     * @param userId   id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @return String object of messages
     */
    String updateQuantity(String token, int cartId, int quantity, int userId);

    /**
     * Purpose : Ability to get all cart items or cart orders from the database if
     * user token is valid
     *
     * @param token input given by user to verify user is verified user or not
     * @return List of all Cart objects along with a String object of message
     */
    List<Cart> getAllCartOrders(String token);

    /**
     * Purpose : To get all list of cart items of particular user if user token and user id
     * is valid
     *
     * @param token  input given by user to verify user is verified user or not
     * @param userId id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @return List of all Cart objects of the user along with a String object of message
     */
    List<Cart> getAllCartOrdersForUser(String token, int userId);
}
