package services.campaignservices.dto;

import lombok.*;
import services.campaignservices.model.Gender;
import services.campaignservices.model.ProfileCategory;

import java.time.LocalDate;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    LocalDate dateOfBirth;
    Gender gender;
    ProfileCategory profileCategory;
}
