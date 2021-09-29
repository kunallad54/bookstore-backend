package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.entity.Order;

import java.util.List;

public interface IOrderService {

    /**
     * Purpose : Ability to place order if user is verified user and if user id
     * is present in User Database
     *
     * @param token    input given by user to authenticate user
     * @param orderDTO object of OrderDTO that has data of order saved in repository
     * @return response  with String object of message
     */
    String placeOrder(OrderDTO orderDTO, String token);

    /**
     * Purpose : Ability to cancel or delete order by its id from the database if
     * token entered is valid token and user id entered is valid user by
     * checking in User database
     *
     * @param token   input given by user to authenticate user
     * @param userId  id of user that acts as foreign key in Order Table and primary key in User table in DB
     * @param orderId id of Order object
     * @return response  with String object of message
     */
    String cancelOrder(String token, int orderId, int userId);

    /**
     * Purpose : Ability to get all list of orders from database
     *
     * @param token token input given by user to authenticate user
     * @return List of all orders
     */
    List<Order> getAllOrders(String token);

    /**
     * Purpose : To get all orders placed by particular user from database
     *
     * @param token  input given by user to authenticate user
     * @param userId id of user that acts as foreign key in Order Table and primary key in User table in DB
     * @return List of Orders placed by User
     */
    List<Order> getAllOrdersForUser(String token, int userId);
}
