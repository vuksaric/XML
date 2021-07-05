package services.profileservices.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ViewProfileDTO {
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
    private List<Integer> postIds;
    private List<Integer> storyIds;
    private List<Integer> following;
    private List<Integer> followers;
    private List<Integer> friends;
    private List<Integer> blocked;
    private List<Integer> muted;
    private int id;
}
