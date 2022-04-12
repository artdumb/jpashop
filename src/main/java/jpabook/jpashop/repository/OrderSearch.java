package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Setter @Getter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

}
