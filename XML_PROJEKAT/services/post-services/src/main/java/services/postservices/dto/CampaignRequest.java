package services.postservices.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRequest {

    int postId;
    String website;
    String username;
}
