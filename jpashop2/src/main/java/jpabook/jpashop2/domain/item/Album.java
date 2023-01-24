package jpabook.jpashop2.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("A") // 기본값 album
public class Album extends Item{
    private String artist;
    private String etc;
}
