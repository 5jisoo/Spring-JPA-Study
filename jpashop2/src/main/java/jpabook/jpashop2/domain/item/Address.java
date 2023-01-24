package jpabook.jpashop2.domain.item;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {   // JPA 스펙을 맞춰주기 위한 기본 생성자 - 안전하게 protected로.
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
