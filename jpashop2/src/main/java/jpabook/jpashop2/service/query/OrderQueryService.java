package jpabook.jpashop2.service.query;

import jpabook.jpashop2.api.OrderApiController;
import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 쿼리용 서비스를 별도로 분리함
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        // 변환 로직들을 OrderQueryService로 가져가자!!
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }

}
