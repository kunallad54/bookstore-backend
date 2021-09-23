package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.OrderServiceBuilder;
import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.Order;
import com.bridgelabz.bookstoreapp.entity.User;
import com.bridgelabz.bookstoreapp.exceptions.BookStoreException;
import com.bridgelabz.bookstoreapp.repository.OrderRepository;
import com.bridgelabz.bookstoreapp.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class OrderServiceImplementation implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderServiceBuilder orderServiceBuilder;

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    BookServiceImplementation bookServiceImplementation;

    @Autowired
    ModelMapper modelMapper;

    /**
     * Purpose : Ability to place order if user is verified user and if user id
     * is present in User Database
     *
     * @param token    input given by user to authenticate user
     * @param userId   id of user that acts as foreign key in Order Table and primary key in User table in DB
     * @param orderDTO object of OrderDTO that has data of order saved in repository
     * @return response  with String object of message
     */
    @Override
    public String placeOrder(OrderDTO orderDTO, String token, int userId) {
        log.info("Inside placeOrder Service Method");
        if (isUserVerified(token)) {
            Order order = orderServiceBuilder.buildDO(orderDTO);
            Book byBookById = bookServiceImplementation.findByBookById(orderDTO.getBookId());
            User userById = userServiceImplementation.getUserById(userId);
            order.setUser(userById);
            order.setBook(byBookById);
            order.setOrderDate(LocalDate.now());
            double totalPrice = orderDTO.getQuantity() * byBookById.getPrice();
            order.setPrice(totalPrice);
            orderRepository.save(order);
            return messageSource.getMessage("order.placed", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to cancel or delete order by its id from the database if
     * token entered is valid token and user id entered is valid user by
     * checking in User database
     *
     * @param token   input given by user to authenticate user
     * @param orderId id of Order object
     * @param userId  id of user that acts as foreign key in Order Table and primary key in User table in DB
     * @return response  with String object of message
     */
    @Override
    public String cancelOrder(String token, int orderId, int userId) {
        log.info("Inside cancelOrder Service Method");
        if (isUserVerified(token)) {
            Order orderById = findOrderById(orderId);
            if (orderById.getUser().getId() == userId) {
                orderRepository.delete(orderById);
                return messageSource.getMessage("ordered.cancelled", null, Locale.ENGLISH);
            } else {
                return messageSource.getMessage("incorrect.userid", null, Locale.ENGLISH);
            }
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to get all list of orders from database
     *
     * @param token token input given by user to authenticate user
     * @return List of all orders
     */
    @Override
    public List<Order> getAllOrders(String token) {
        log.info("Inside getAllOrders Service Method");
        if (isUserVerified(token)) {
            return orderRepository.findAll();
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : To get all orders placed by particular user from database
     *
     * @param token  input given by user to authenticate user
     * @param userId id of user that acts as foreign key in Order Table and primary key in User table in DB
     * @return List of Orders placed by User
     */
    @Override
    public List<Order> getAllOrdersForUser(String token, int userId) {
        log.info("Inside getAllOrdersForUser Service Method");
        if (isUserVerified(token)) {
            User userById = userServiceImplementation.getUserById(userId);
            List<Order> allOrdersOfUser = orderRepository.findAllByUser(userById);
            return allOrdersOfUser;
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to authenticate user
     *
     * @param token input to check user is authenticated user or not
     * @return true if verified else false
     */
    private boolean isUserVerified(String token) {
        User userByEmailToken = userServiceImplementation.getUserByEmailToken(token);
        return userByEmailToken.isVerified;
    }

    /**
     * Purpose : Ability to find order by its order id
     *
     * @param id order id to found
     * @return Object of Order or exception if not found
     */
    private Order findOrderById(int id) {
        log.info("Inside findOrderById Service Method");
        return orderRepository.findById(id)
                .orElseThrow(() -> new BookStoreException(messageSource.getMessage("order.not.found",
                        null, Locale.ENGLISH), BookStoreException.ExceptionType.ORDER_NOT_FOUND));
    }
}
