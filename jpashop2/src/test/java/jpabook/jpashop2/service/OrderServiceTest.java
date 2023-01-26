package jpabook.jpashop2.service;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.item.Address;
import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.domain.item.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);

        Item item = new Book();

        // when

        // then
    }

    @Test
    public void 주문취소() throws Exception {
        // given

        // when

        // then
    }

    @Test
    public void 재고수량초과() throws Exception {
        // given

        // when

        // then
    }

}