package services.profileservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavouriteCollection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ElementCollection
    @CollectionTable(name="Collection_Posts", joinColumns=@JoinColumn(name="Collection_ID"))
    @Column(name="post")
    private List<Integer> postIds;


}
