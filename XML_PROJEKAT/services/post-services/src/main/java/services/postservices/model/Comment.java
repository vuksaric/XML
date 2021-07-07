package services.postservices.model;

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
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String content;
    @ElementCollection
    @CollectionTable(name="Comment_Tagged", joinColumns=@JoinColumn(name="Comment_ID"))
    @Column(name="tag")
    private List<Integer> taggedIds;

    public Comment(String username, String content, List<Integer> taggedIds)
    {
        this.username = username;
        this.content = content;
        this.taggedIds = taggedIds;
    }
}
