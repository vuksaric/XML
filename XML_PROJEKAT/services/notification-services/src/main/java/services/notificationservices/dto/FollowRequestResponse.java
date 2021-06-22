package services.notificationservices.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequestResponse {

    private String username;
    private int id;
}
