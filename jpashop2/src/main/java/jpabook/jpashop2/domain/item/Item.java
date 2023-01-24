package jpabook.jpashop2.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.InheritanceType.*;

@Entity
@Getter @Setter
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
