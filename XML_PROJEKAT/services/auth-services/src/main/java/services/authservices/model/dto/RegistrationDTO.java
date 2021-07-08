package services.authservices.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import services.authservices.model.UserInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    String username;
    String name;
    String surname;
    String email;
    String password;
    String phone;
    String dateOfBirth;
    String gender;
    boolean agent;
    String website;

    public RegistrationDTO(UserInfo userInfo)
    {
        this.username = userInfo.getUsername();
        this.name = userInfo.getName();
        this.surname = userInfo.getSurname();
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.phone = userInfo.getPhone();
        this.dateOfBirth = userInfo.getDateOfBirth().toString();
        this.gender = userInfo.getGender().toString();
        this.agent = userInfo.isAgent();
        this.website = userInfo.getWebsite();
    }
}
