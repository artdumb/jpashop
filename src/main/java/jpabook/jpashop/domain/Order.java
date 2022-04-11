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
    //casecacde를 통해 영속성컨텍스트에 넣을 때 orderItems를 같이 넣음
    //삭제할 떄도 같이
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //Delivery와 관계필드
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //==연관관계메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성메소드==//
    //연관된 데이터들을 같이 만들어준다(복잡한걸줄인다.)
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if (delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCLE);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회로직==//
    /**
     * 전체주문가격조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem: orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
        }
}

