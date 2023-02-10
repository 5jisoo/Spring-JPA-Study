package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop2.domain.item.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // order 테이블에 있는 member 필드에 의해 매핑되었음을 알림
    private List<Order> orders = new ArrayList<>();

}
