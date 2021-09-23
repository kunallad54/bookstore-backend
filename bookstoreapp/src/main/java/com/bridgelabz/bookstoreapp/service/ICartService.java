package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDTO;

public interface ICartService {

    /**
     * Purpose : To add  cart details in the Cart database
     *
     * @param cartDTO object of CartDTO which carries cart details
     * @param token   input given by user to verify user is verified user or not
     * @return String object of messages
     */
    String addToCart(CartDTO cartDTO, String token);

    /**
     * Purpose : Ability to remove item from cart through cart id
     *
     * @param token  input given by user to verify user is verified user or not
     * @param cartId id of cart that specify item to be deleted
     * @return String object of messages
     */
    String removeFromCart(String token, int cartId);

    /**
     * Purpose : To update quantity of item in the cart through its cart id
     *
     * @param token    input given by user to verify user is verified user or not
     * @param cartId   id of cart that specify item to be updated
     * @param quantity updated quantity
     * @return String object of messages
     */
    String updateQuantity(String token, int cartId, int quantity);
}
