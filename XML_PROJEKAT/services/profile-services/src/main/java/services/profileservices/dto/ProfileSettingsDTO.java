package services.profileservices.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileSettingsDTO {
    private String username;
    private boolean checkPost;
    private boolean checkStory;
    private boolean checkComment;
    private boolean checkMessage;

}
