package jpabook.jpashop.order.service;

import java.util.List;
import jpabook.jpashop.delivery.entity.Delivery;
import jpabook.jpashop.delivery.entity.enums.DeliveryStatus;
import jpabook.jpashop.dto.OrderSearch;
import jpabook.jpashop.item.db.ItemRepository;
import jpabook.jpashop.item.entity.Item;
import jpabook.jpashop.member.db.MemberRepositoryImpl;
import jpabook.jpashop.member.entity.Member;
import jpabook.jpashop.order.db.OrderRepository;
import jpabook.jpashop.order.entity.Order;
import jpabook.jpashop.orderitem.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepositoryImpl memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}
