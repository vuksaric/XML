package services.postservices.dto;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private int postId;
    private String username;
    private String content;
    private List<String> taggedUsernames;
}
