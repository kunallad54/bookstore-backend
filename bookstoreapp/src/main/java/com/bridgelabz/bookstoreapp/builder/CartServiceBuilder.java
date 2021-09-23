package com.bridgelabz.bookstoreapp.builder;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.entity.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartServiceBuilder {

    /**
     * Purpose : Ability to add Cart in the database
     *
     * @param cartDTO object of CartDTO that has data saved in repository
     * @return Cart Object
     */
    public Cart buildDO(CartDTO cartDTO) {
        log.info("Inside buildDO CartServiceBuilder Method");
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDTO, cart);
        return cart;
    }
}
