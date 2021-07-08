package services.agentappservices.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    String username;
    String password;
    String name;
    String surname;
    String email;
    String phone;
    String gender;
    String dateOfBirth;
    Boolean agent;
    String website;
}
