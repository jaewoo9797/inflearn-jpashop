package jpabook.jpashop.order.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

    ORDER("주문"),
    CANCEL("주문 취소")
    ;

    private final String describe;
}
