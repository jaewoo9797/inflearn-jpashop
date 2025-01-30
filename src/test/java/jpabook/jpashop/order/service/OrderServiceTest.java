package jpabook.jpashop.order.service;

import static org.assertj.core.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.item.entity.Book;
import jpabook.jpashop.item.entity.Item;
import jpabook.jpashop.item.exception.NotEnoughStockException;
import jpabook.jpashop.member.entity.Address;
import jpabook.jpashop.member.entity.Member;
import jpabook.jpashop.order.db.OrderRepository;
import jpabook.jpashop.order.entity.Order;
import jpabook.jpashop.order.entity.enums.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void 상품주문() {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        // when
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);
        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10_000 * orderCount);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과() {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10_000, 10);

        int orderCount = 11;
        // when
        Throwable throwable = catchThrowable(() -> orderService.order(member.getId(), item.getId(), orderCount));
        // then
        assertThat(throwable)
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void 주문취소() {
        // given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10_000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);
        // when
        orderService.cancelOrder(orderId);
        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}