package jpabook.jpashop2.api;

import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.item.Address;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.repository.OrderSearch;
import jpabook.jpashop2.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop2.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne 관계의 성능 최적화
 * (ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // 애초에 엔티티 직접 노출은 안됨...!!!
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            // force-lazy-loading을 설정하는 옵션을, 이렇게 **Lazy 강제 초기화**를 사용하자.
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    // 성능이 안좋음. - 쿼리가 여러번 나감.
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        System.out.println("===");
        // 쿼리 1 + N + N 번 실행됨.
        return orders.stream()
                .map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());
    }

    // join fetch로 한번에 조회하자!
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());
    }


    // 성능은 좋지만~ OrderRepository에 작성하기엔 api 스펙에 맞춘 느낌이 있긴 함..
    // > OrderSimpleQueryRepository를 사용하기 :: 유지 보수성 좋음
    // 하지만? v3와 v4의 성능 차이는 심하지 않음. >> API에 따라 선택하기. 트래픽이 많으면 v4.
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        // 테이블을 3개나 join 해야함.
        private Long orderId;
        private String username;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            username = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
