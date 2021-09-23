package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    IOrderService orderService;

    @Autowired
    MessageSource messageSource;

    /**
     * Purpose : Ability to place order if user is verified user
     *
     * @param token    input given by user to authenticate user
     * @param orderDTO object of OrderDTO that has data of order saved in repository
     * @return response  with String object of message
     */
    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestParam(name = "token") String token, @Valid
    @RequestBody OrderDTO orderDTO) {
        log.info("Inside placeOrder Controller Method");
        return new ResponseEntity<>(orderService.placeOrder(orderDTO, token), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to get all list of orders from database
     *
     * @param token input given by user to authenticate user
     * @return response  with String object of message
     */
    @GetMapping("/get-all-orders")
    public ResponseEntity<ResponseDTO> getAllOrders(@RequestParam(name = "token") String token) {
        log.info("Inside getAllOrders Controller Method");
        ResponseDTO responseDTO = new ResponseDTO(messageSource.getMessage("get.orders",
                null, Locale.ENGLISH), orderService.getAllOrders(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Purpose : Ability to cancel or delete order by its id from the database
     *
     * @param token   input given by user to authenticate user
     * @param orderId id of Order object
     * @return response  with String object of message
     */
    @GetMapping("/cancel-order")
    public ResponseEntity<String> cancelOrder(@RequestParam(name = "token") String token,
                                              @RequestParam(name = "order id") int orderId) {
        log.info("Inside cancelOrder Controller Method");
        return new ResponseEntity<>(orderService.cancelOrder(token, orderId), HttpStatus.OK);
    }
}
