package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.CartServiceBuilder;
import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.Cart;
import com.bridgelabz.bookstoreapp.entity.User;
import com.bridgelabz.bookstoreapp.exceptions.BookStoreException;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class CartServiceImplementation implements ICartService {

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    CartServiceBuilder cartServiceBuilder;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MessageSource messageSource;

    @Autowired
    BookServiceImplementation bookServiceImplementation;

    /**
     * Purpose : To add  cart details in the Cart database
     *
     * @param cartDTO object of CartDTO which carries cart details
     * @param token   input given by user to verify user is verified user or not
     * @return String object of messages
     */
    @Override
    public String addToCart(CartDTO cartDTO, String token) {
        log.info("Inside addToCart Service Method");
        if (isUserVerified(token)) {
            Cart cart = cartServiceBuilder.buildDO(cartDTO);
            Book BookById = bookServiceImplementation.findByBookById(cartDTO.getBookId());
            cart.setBook(BookById);
            cartRepository.save(cart);
            return messageSource.getMessage("added.to.cart", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified", null
                    , Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to remove item from cart through cart id
     *
     * @param token  input given by user to verify user is verified user or not
     * @param cartId id of cart that specify item to be deleted
     * @return String object of messages
     */
    @Override
    public String removeFromCart(String token, int cartId) {
        log.info("Inside removeFromCart Service Method");
        if (isUserVerified(token)) {
            Cart cartById = findCartById(cartId);
            cartRepository.delete(cartById);
            return messageSource.getMessage("removed.from.cart", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified", null
                    , Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : To update quantity of item in the cart through its cart id
     *
     * @param token    input given by user to verify user is verified user or not
     * @param cartId   id of cart that specify item to be updated
     * @param quantity updated quantity
     * @return String object of messages
     */
    @Override
    public String updateQuantity(String token, int cartId, int quantity) {
        log.info("Inside updateQuantity Service Method");
        if (isUserVerified(token)) {
            Cart cartById = findCartById(cartId);
            cartById.setQuantity(quantity);
            cartRepository.save(cartById);
            return messageSource.getMessage("updated.cart", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified", null
                    , Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to find Cart object by its id
     *
     * @param id cart id
     * @return object of Cart or exception if not found
     */
    private Cart findCartById(int id) {
        log.info("Inside findCartById Service Method");
        return cartRepository.findById(id)
                .orElseThrow(() -> new BookStoreException(messageSource.getMessage("cart.not.found",
                        null, Locale.ENGLISH), BookStoreException.ExceptionType.CART_NOT_FOUND));
    }

    /**
     * Purpose : To check user is verified user or not
     *
     * @param token input given by user for authentication
     * @return true if verified user else false
     */
    private boolean isUserVerified(String token) {
        User userByEmailToken = userServiceImplementation.getUserByEmailToken(token);
        return userByEmailToken.isVerified;
    }
}
