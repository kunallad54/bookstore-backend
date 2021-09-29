package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.CartServiceBuilder;
import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.entity.Book;
import com.bridgelabz.bookstoreapp.entity.Cart;
import com.bridgelabz.bookstoreapp.entity.User;
import com.bridgelabz.bookstoreapp.exceptions.BookStoreException;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    BookRepository bookRepository;

    /**
     * Purpose : To add  cart details in the Cart database after validating cart details and
     * user token and user id needs to be valid
     *
     * @param cartDTO object of CartDTO which carries cart details
     * @param token   input given by user to verify user is verified user or not
     * @return String object of messages
     */
    @Override
    public String addToCart(CartDTO cartDTO, String token) {
        log.info("Inside addToCart Service Method");
        User userByToken = userServiceImplementation.getUserByToken(token);
        if (userByToken.isVerified) {
            Cart cart = cartServiceBuilder.buildDO(cartDTO);
            Book BookById = bookServiceImplementation.findByBookById(cartDTO.getBookId());
            BookById.setIsBookAdded(true);
            bookRepository.save(BookById);
            cart.setUser(userByToken);
            cart.setBook(BookById);
            cartRepository.save(cart);
            return messageSource.getMessage("added.to.cart", null, Locale.ENGLISH);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified", null
                    , Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to remove item from cart through cart id and user id from database
     * if user token and user id are valid
     *
     * @param token  input given by user to verify user is verified user or not
     * @param cartId id of cart that specify item to be deleted
     * @return String object of messages
     */
    @Override
    public String removeFromCart(String token, int cartId) {
        log.info("Inside removeFromCart Service Method");
        User userByToken = userServiceImplementation.getUserByToken(token);
        if (userByToken.isVerified) {
            Cart cartById = findCartById(cartId);
            if (cartById.getUser().getId() == userByToken.getId()) {
                Book book = cartById.getBook();
                book.setIsBookAdded(false);
                bookRepository.save(book);
                cartRepository.delete(cartById);
                return messageSource.getMessage("removed.from.cart", null, Locale.ENGLISH);
            } else {
                return messageSource.getMessage("incorrect.userid", null, Locale.ENGLISH);
            }
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified", null
                    , Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : To update quantity of item in the cart through its cart id if user token
     * and user id is valid
     *
     * @param token    input given by user to verify user is verified user or not
     * @param cartId   id of cart that specify item to be updated
     * @param quantity updated quantity
     * @return String object of messages
     */
    @Override
    public String updateQuantity(String token, int cartId, int quantity) {
        log.info("Inside updateQuantity Service Method");
        User userByToken = userServiceImplementation.getUserByToken(token);
        if (userByToken.isVerified) {
            Cart cartById = findCartById(cartId);
            if (cartById.getUser().getId() == userByToken.getId()) {
                cartById.setQuantity(quantity);
                cartRepository.save(cartById);
                return messageSource.getMessage("updated.cart", null, Locale.ENGLISH);
            } else {
                return messageSource.getMessage("incorrect.userid", null, Locale.ENGLISH);
            }

        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified", null
                    , Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to get all cart items or cart orders from the database if
     * user token is valid
     *
     * @param token input given by user to verify user is verified user or not
     * @return List of all Cart objects along with a String object of message
     */
    @Override
    public List<Cart> getAllCartOrders(String token) {
        log.info("Inside getAllCartOrders Service Method");
        User userByToken = userServiceImplementation.getUserByToken(token);
        if (userByToken.isVerified) {
            return cartRepository.findAll();
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified", null
                    , Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : To get all list of cart items of particular user if user token and user id
     * is valid
     *
     * @param token  input given by user to verify user is verified user or not
     * @return List of all Cart objects of the user along with a String object of message
     */
    @Override
    public List<Cart> getAllCartOrdersForUser(String token) {
        log.info("Inside getAllCartOrdersForUser Service Method");
        User userByToken = userServiceImplementation.getUserByToken(token);
        if (userByToken.isVerified) {
            List<Cart> allByUser = cartRepository.findAllByUser(userByToken);
            return allByUser;
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

}
