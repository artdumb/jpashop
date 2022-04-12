package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보갱신
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성 //생성메소드 이용
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        //주문 생성 //생성메소드 이용
        Order order = Order.createOrder(member,delivery,orderItem);

        //주문저장 //CASCADE.ALL옵션때문에 연쇄적으로 다른 연관 엔티티도 persist가 된다
        orderRepository.save(order);
        return order.getId();
    }
    //주문취소
    @Transactional
    public void cancelOrder(Long orderid){
        Order order = orderRepository.findOne(orderid);
        //엔티티의 비즈니스로직설정해뒀따.
        order.cancel();
    }
    //검색]

}
