package com.bridgelabz.bookstoreapp.builder;

import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.entity.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserServiceBuilder {

    /**
     * Purpose : Ability to add user to the database
     *
     * @param userRegistrationDTO is object of UserRegistrationDTO to store data in repository
     * @return userRegistration Object
     */
    public UserModel buildDO(UserRegistrationDTO userRegistrationDTO) {
        log.info("Inside buildDO Method");
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRegistrationDTO, userModel);
        return userModel;
    }
}
