package com.bridgelabz.bookstoreapp.builder;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderServiceBuilder {

    /**
     * Purpose : Ability to add order in the database
     *
     * @param orderDTO object of OrderDTO
     * @return object of Order
     */
    public Order buildDO(OrderDTO orderDTO) {
        log.info("Inside buildDO OrderServiceBuilder Method");
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        return order;
    }
}
