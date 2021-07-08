package services.profileservices.dto;


import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedStoryRequest {
    List<Integer> storyIds;
    String username;
}
