package jpa.shoppingmall.domain;

import jpa.shoppingmall.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String seller;

    public Item() {
    }

    public Item(String name, int price, int stockQuantity, String seller) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.seller = seller;
    }

    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategory = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restQuantity = stockQuantity -quantity;
        if(restQuantity < 0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }

        this.stockQuantity = restQuantity;
    }

}
