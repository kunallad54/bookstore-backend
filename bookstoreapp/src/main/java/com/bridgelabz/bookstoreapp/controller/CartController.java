package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    ICartService cartService;

    @Autowired
    MessageSource messageSource;

    /**
     * Purpose : To add  cart details in the Cart database after validating cart details and
     * user token and user id needs to be valid
     *
     * @param token   input given by user to authenticate user
     * @param userId  id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @param cartDTO object of CartDTO
     * @return response  with String object of message
     */
    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestParam(name = "token") String token,
                                            @RequestParam(name = "user id") int userId,
                                            @Valid @RequestBody CartDTO cartDTO) {
        log.info("Inside addToCart Controller Method");
        return new ResponseEntity<>(cartService.addToCart(cartDTO, token, userId), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to remove item from cart through cart id and user id from database
     * if user token and user id are valid
     *
     * @param token   input given by user to authenticate user
     * @param userId  id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @param cart_id cart id
     * @return response  with String object of message
     */
    @DeleteMapping("/delete-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestParam(name = "token") String token,
                                                 @RequestParam(name = "user id") int userId,
                                                 @RequestParam(name = "cart id") int cart_id) {
        log.info("Inside removeFromCart Controller Method");
        return new ResponseEntity<>(cartService.removeFromCart(token, cart_id, userId), HttpStatus.OK);
    }

    /**
     * Purpose : To update quantity of item in the cart through its cart id if user token
     * and user id is valid
     *
     * @param token    input given by user to verify user is verified user or not
     * @param cartId   id of cart that specify item to be updated
     * @param userId   id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @param quantity updated quantity
     * @return response  with String object of message
     */
    @PutMapping("/update-cart")
    public ResponseEntity<String> updateQuantity(@RequestParam(name = "token") String token,
                                                 @RequestParam(name = "cart id") int cartId,
                                                 @RequestParam(name = "user id") int userId,
                                                 @RequestParam(name = "quantity") int quantity) {
        log.info("Inside updateQuantity Controller Method");
        return new ResponseEntity<>(cartService.updateQuantity(token, cartId, userId, quantity), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to get all cart items or cart orders from the database if
     * user token is valid
     *
     * @param token input given by user to verify user is verified user or not
     * @return List of all Cart objects along with a String object of message
     */
    @GetMapping("get-all-cart-orders")
    public ResponseEntity<ResponseDTO> getAllCartOrders(@RequestParam(name = "token") String token) {
        log.info("Inside getAllCartOrders Controller Method");
        ResponseDTO responseDTO = new ResponseDTO(messageSource.getMessage(
                "get.all.cart.orders", null, Locale.ENGLISH), cartService.getAllCartOrders(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Purpose : To get all list of cart items of particular user if user token and user id
     * is valid
     *
     * @param token  input given by user to verify user is verified user or not
     * @param userId id of user that acts as foreign key in Cart Table and primary key in User table in DB
     * @return List of all Cart objects of the user along with a String object of message
     */
    @GetMapping("get-user-cart-orders")
    public ResponseEntity<ResponseDTO> getAllCartOrdersForUser(@RequestParam(name = "token") String token,
                                                               @RequestParam(name = "user id") int userId) {
        log.info("Inside getAllCartOrdersForUser Controller Method");
        ResponseDTO responseDTO = new ResponseDTO(messageSource.getMessage("get.user.cart.orders",
                null, Locale.ENGLISH), cartService.getAllCartOrdersForUser(token, userId));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
