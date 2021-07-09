package services.agentappservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int pictureId;
    private int price;
    private int count;
    private int agentId; //kom agentu pripada proizvod!!!
    private String name;

    public Product(int pictureId, int price, int quantity, int agentId,String name){
        this.agentId=agentId;
        this.count=quantity;
        this.price=price;
        this.pictureId=pictureId;
        this.name = name;
    }
}
