package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.entity.Order;

import java.util.List;

public interface IOrderService {

    /**
     * Purpose : Ability to place order if user is verified user
     *
     * @param orderDTO object of OrderDTO that has data of order saved in repository
     * @param token    input given by user to authenticate user
     * @return String object of messages
     */
    String placeOrder(OrderDTO orderDTO, String token);

    /**
     * Purpose : Ability to cancel or delete order with its order id
     *
     * @param token   input given by user to authenticate user
     * @param orderId variable that carries order id
     * @return String object of messages
     */
    String cancelOrder(String token, int orderId);

    /**
     * Purpose : Ability to get all list of orders from database
     *
     * @param token token input given by user to authenticate user
     * @return List of all orders
     */
    List<Order> getAllOrders(String token);
}
