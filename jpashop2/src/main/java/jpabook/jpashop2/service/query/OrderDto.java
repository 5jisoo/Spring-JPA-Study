package jpabook.jpashop2.service.query;

import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderItem;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.item.Address;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
// 또는 @Getter 사용
public class OrderDto {

    private Long orderId;

    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems; // 엔티티 의존 완전 끄기

    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(toList());
    }

}

