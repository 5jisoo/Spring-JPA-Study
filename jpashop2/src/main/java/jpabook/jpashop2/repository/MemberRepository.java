package jpabook.jpashop2.repository;

import jpabook.jpashop2.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 알아서 동작함
    // select m from Meber m where m.name = ?
    List<Member> findByName(String name);
}
