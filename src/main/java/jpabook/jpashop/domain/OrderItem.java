package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem {


    //OrderItem 기본필드
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;
    private int orderPrice;
    private int count;

    //order과 관계필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;


    //item과 관계필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;



}
