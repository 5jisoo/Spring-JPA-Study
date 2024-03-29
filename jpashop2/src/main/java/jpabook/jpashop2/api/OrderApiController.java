package jpabook.jpashop2.api;

import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderItem;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.item.Address;
import jpabook.jpashop2.repository.OrderRepository;
import jpabook.jpashop2.repository.OrderSearch;
import jpabook.jpashop2.repository.order.query.OrderFlatDto;
import jpabook.jpashop2.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop2.repository.order.query.OrderQueryDto;
import jpabook.jpashop2.repository.order.query.OrderQueryRepository;
import jpabook.jpashop2.service.query.OrderQueryService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * 권장 순서
     *
     * 1. 엔티티 조회 방식으로 우선 접근
     *  - 페치조인으로 쿼리 수를 최적화
     *  - 컬렉션 최적화
     *      1. 페이징 필요 => batch-fetch-size 사용하기
     *      2. 페이징 필요 X => join fetch 사용
     * 2. 엔티티 조회 방식으로 해결이 안되면 DTO 조회 방식 사용
     * 3. DTO 조회 방식으로 해결이 안되면 NativeSQL or 스프링 JdbcTemplate
     */

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            // 프록시 강제 초기화 - @JsonIgnore 가 필수
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
        }

        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<OrderDto> result = orders.stream()
                .map(OrderDto::new)
                .collect(toList());
        return result;
    }

    // 쿼리는 하나지만, 데이터 전송량이 많음.
    // 페이징 불가

    private final OrderQueryService orderQueryService;
    @GetMapping("/api/v3/orders")
    public List<jpabook.jpashop2.service.query.OrderDto> ordersV3() {
        return orderQueryService.ordersV3();
    }

    /**
     * 페이징 가능
     */
    // 쿼리는 여러개지만, 데이터 전송량은 적다!

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        // ToOne 여기서 페이징
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        // default_batch_fetch_size 설정으로 빨라짐
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        // api 스펙을 맞추기 위해 (OrderQueryDto 반환을 위해) 중복 제거...... ==> 페이징 불가능
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }


    @Data
    // 또는 @Getter 사용
    static class OrderDto {

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

    @Getter
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;  // 주문 수량


        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
