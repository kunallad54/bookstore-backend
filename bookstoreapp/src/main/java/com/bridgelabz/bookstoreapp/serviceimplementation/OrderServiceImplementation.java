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
     * Purpose : Ability to place order if user is verified user
     *
     * @param orderDTO object of OrderDTO that has data of order saved in repository
     * @param token    input given by user to authenticate user
     * @return String object of messages
     */
    @Override
    public String placeOrder(OrderDTO orderDTO, String token) {
        log.info("Inside placeOrder Service Method");
        if (isUserVerified(token)) {
            Order order = orderServiceBuilder.buildDO(orderDTO);
            Book byBookById = bookServiceImplementation.findByBookById(orderDTO.getBookId());
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
     * Purpose : Ability to cancel or delete order with its order id
     *
     * @param token   input given by user to authenticate user
     * @param orderId variable that carries order id
     * @return String object of messages
     */
    @Override
    public String cancelOrder(String token, int orderId) {
        log.info("Inside cancelOrder Service Method");
        if (isUserVerified(token)) {
            Order orderById = findOrderById(orderId);
            orderRepository.delete(orderById);
            return messageSource.getMessage("ordered.cancelled", null, Locale.ENGLISH);
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
