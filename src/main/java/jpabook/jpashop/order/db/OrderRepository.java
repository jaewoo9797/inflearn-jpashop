package jpabook.jpashop.order.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import jpabook.jpashop.dto.OrderSearch;
import jpabook.jpashop.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        if (orderSearch.orderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and ";
            }
            jpql += " o.status = :status";
        }

        if (StringUtils.hasText(orderSearch.memberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and ";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1_000);

        if (orderSearch.orderStatus() != null) {
            query.setParameter("status", orderSearch.orderStatus());
        }
        if (orderSearch.memberName() != null) {
            query.setParameter("name", orderSearch.memberName());
        }
        return query.getResultList();
    }
}
