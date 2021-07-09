package services.profileservices.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedPostRequest {
    int postId;
    String username;
}
