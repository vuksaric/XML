package services.postservices.dto;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import services.postservices.model.Comment;
import services.postservices.model.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Comparable< PostResponse >{

    private int id;
    private List<Integer> likeIds;
    private List<Integer> dislikeIds;
    private LocalDate date;
    private String caption;
    private List<PictureDTO> content;
    private String location;
    private List<String> tagged;
    private List<Comment> comments;

    public PostResponse(Post post)
    {
        this.id = post.getId();
        this.likeIds = post.getLikeIds();
        this.dislikeIds = post.getDislikeIds();
        this.date = post.getPostInfo().getDate();
        this.caption = post.getPostInfo().getCaption();
        this.location = post.getPostInfo().getLocation();
        this.tagged = new ArrayList<>();
        this.comments = post.getComments();
        this.content = new ArrayList<>();
    }

    @Override
    public int compareTo(@NotNull PostResponse o) {
        return this.getDate().compareTo(o.getDate());
    }
}


