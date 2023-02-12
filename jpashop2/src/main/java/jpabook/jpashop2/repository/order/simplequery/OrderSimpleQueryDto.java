package jpabook.jpashop2.repository.order.simplequery;

import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.item.Address;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    // 테이블을 3개나 join 해야함.
    private Long orderId;
    private String username;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long id, String name, LocalDateTime date, OrderStatus status, Address address) {
        orderId = id;
        username = name; // LAZY 초기화
        orderDate = date;
        orderStatus = status;
        this.address = address; // LAZY 초기화
    }
}

