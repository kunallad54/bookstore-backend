package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    ICartService cartService;

    /**
     * Purpose : To add  cart details in the Cart database after validating cart details
     *
     * @param token   input given by user to authenticate user
     * @param cartDTO object of CartDTO
     * @return response  with String object of message
     */
    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestParam(name = "token") String token, @Valid
    @RequestBody CartDTO cartDTO) {
        log.info("Inside addToCart Controller Method");
        return new ResponseEntity<>(cartService.addToCart(cartDTO, token), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to remove item from cart through cart id from database
     *
     * @param token input given by user to authenticate user
     * @param id    cart id
     * @return response  with String object of message
     */
    @DeleteMapping("/delete-from-cart")
    public ResponseEntity<String> removeFromCart(@RequestParam(name = "token") String token,
                                                 @RequestParam(name = "cart id") int id) {
        log.info("Inside removeFromCart Controller Method");
        return new ResponseEntity<>(cartService.removeFromCart(token, id), HttpStatus.OK);
    }

    /**
     * Purpose : To update quantity of item in the cart through its cart id
     *
     * @param token    input given by user to verify user is verified user or not
     * @param cartId   id of cart that specify item to be updated
     * @param quantity updated quantity
     * @return response  with String object of message
     */
    @PutMapping("/update-cart")
    public ResponseEntity<String> updateQuantity(@RequestParam(name = "token") String token,
                                                 @RequestParam(name = "cart id") int cartId, @RequestParam(name = "quantity") int quantity) {
        log.info("Inside updateQuantity Controller Method");
        return new ResponseEntity<>(cartService.updateQuantity(token, cartId, quantity), HttpStatus.OK);
    }
}
