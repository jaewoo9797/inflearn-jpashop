package jpabook.jpashop.dto;

import jpabook.jpashop.order.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderSearch {
    String memberName;
    int orderCount;
    OrderStatus orderStatus;
}
