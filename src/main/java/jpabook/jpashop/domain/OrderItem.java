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

    //==생성메소드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        //주문수량만큼 재고 깐다.
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스로직==//
    //제고수량은 원상복귀
    public void cancel() {
        getItem().addStock(count);
    }

    //
    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}
