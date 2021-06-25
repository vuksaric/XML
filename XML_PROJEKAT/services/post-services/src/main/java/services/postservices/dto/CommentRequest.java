package services.postservices.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private int postId;
    private String username;
    private String content;
}
