package services.profileservices.dto;

import lombok.*;
import services.profileservices.model.Profile;
import services.profileservices.model.ProfileCategory;

import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private String biography;
    private Boolean isPrivate;
    private String website;
    private Boolean canBeTagged;
    private Boolean canBeMessaged;
    private Boolean notifyProfileActivity;
    private Boolean canBeMessagedPrivate;
    private Boolean notifyPost;
    private Boolean notifyStory;
    private Boolean notifyComment;
    private int id;
    private Boolean usernameChanged;
}
