package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    //Order 기본필드
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [order, cancle]

    //Member과 관계필드
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //OrderItem과 관계필드
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    //Delivery와 관계필드
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;




}
