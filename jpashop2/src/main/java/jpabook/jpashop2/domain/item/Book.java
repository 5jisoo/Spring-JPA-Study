package jpabook.jpashop2.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("B") // 기본값 book
public class Book extends Item{
    private String author;
    private String isbn;
}
