package jpabook.jpashop.dto;

import jpabook.jpashop.order.entity.enums.OrderStatus;

public record OrderSearch(
        String memberName,
        OrderStatus orderStatus
) {
}
