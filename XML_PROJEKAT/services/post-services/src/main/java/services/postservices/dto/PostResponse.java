package services.postservices.dto;

import lombok.*;
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
public class PostResponse {

    private List<Integer> likeIds;
    private List<Integer> dislikeIds;
    private LocalDate date;
    private String caption;
    private List<String> contentSrcs;
    private int report;
    private String location;
    private List<Integer> taggedIds;
    private List<Comment> comments;

    public PostResponse(Post post)
    {
        this.likeIds = post.getLikeIds();
        this.dislikeIds = post.getDislikeIds();
        this.date = post.getPostInfo().getDate();
        this.caption = post.getPostInfo().getCaption();
        this.report = post.getPostInfo().getReport();
        this.location = post.getPostInfo().getLocation();
        this.taggedIds = post.getPostInfo().getTaggedIds();
        this.comments = post.getComments();
        this.contentSrcs = new ArrayList<>();
    }
}


