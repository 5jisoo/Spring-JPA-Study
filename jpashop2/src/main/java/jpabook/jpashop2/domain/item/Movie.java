package jpabook.jpashop2.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop2.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("M") // 기본값 movie
public class Movie extends Item {
    private String director;
    private String actor;
}
