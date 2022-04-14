package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        //회원 전부다 읽어옴(select를 사용하기위해)
        List<Member> members = memberService.findMembers();
        //상품들 전부다 불러옴(select를 사용하기 위해)
        List<Item> items = itemService.findItems();
        //불러온것을 모델에 담에서 전해줌
        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";

    }

    @PostMapping("/order")
    //post의 요청 parameter를 가져온다.
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        //서비스에 만들어둔 주문 메소드로! Service에서 구현함(id로 주문이 가능하게 구현했다)
        //핵심비즈니스로직을 서비스객체에서 하는 것이 좋다.
        //이 안에서 멤버,아이템객체자체를 넘겨버리면 영속성컨텍스가 애매해진다.
        orderService.order(memberId,itemId,count);
        return "redirect:/orders";
    }


    @GetMapping("/oders")
    public  String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        return "order/orderList";
    }

    @PostMapping("/order/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
