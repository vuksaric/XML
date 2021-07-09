package services.agentappservices.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    int id;
    int price;
    int quantity;
    String image;
    String name;

    public ProductDTO(String name, String image, int price){
        this.price=price;
        this.name=name;
        this.image=image;
    }
}
