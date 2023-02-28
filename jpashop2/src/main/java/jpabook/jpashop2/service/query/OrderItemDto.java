package jpabook.jpashop2.service.query;

import jpabook.jpashop2.domain.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;  // 주문 수량


    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}