package services.authservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import services.authservices.model.dto.RegistrationDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Locale;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;

    public UserInfo(RegistrationDTO registrationDTO){
        this.username = registrationDTO.getUsername();
        this.password = registrationDTO.getPassword();
        this.name = registrationDTO.getName();
        this.surname = registrationDTO.getSurname();
        this.email = registrationDTO.getEmail();
        this.phone = registrationDTO.getPhone();
        if(registrationDTO.getGender().toLowerCase().equals(Gender.Male.toString().toLowerCase(Locale.ROOT)))
            this.gender = Gender.Male;
        else if(registrationDTO.getGender().toLowerCase().equals(Gender.Female.toString().toLowerCase(Locale.ROOT)))
            this.gender = Gender.Female;
        else
            this.gender = Gender.NonBinary;
    }
}
