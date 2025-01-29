package jpabook.jpashop.delivery.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeliveryStatus {

    READY("준비"),
    COMP("배송")
    ;

    private final String describe;
}
