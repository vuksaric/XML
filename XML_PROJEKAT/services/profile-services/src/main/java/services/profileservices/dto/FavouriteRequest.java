package services.profileservices.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteRequest {

    private int profileId;
    private int postId;
    private String collectionName;
    private boolean collection;
}
