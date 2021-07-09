package services.agentappservices.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import services.agentappservices.model.dto.RegistrationDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="userEntity",uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@Column(unique=true)
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Boolean agent;
    private String website;

    public User(RegistrationDTO registrationDTO){
        this.username = registrationDTO.getUsername();
        this.password = registrationDTO.getPassword();
        this.name = registrationDTO.getName();
        this.surname = registrationDTO.getSurname();
        this.email = registrationDTO.getEmail();
        this.phone = registrationDTO.getPhone();
        this.website = registrationDTO.getWebsite();
        this.agent = registrationDTO.getAgent();
        String[] array = registrationDTO.getDateOfBirth().split("T");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(array[0],formatter);
        this.dateOfBirth = dateTime;
        if(registrationDTO.getGender().toLowerCase().equals(Gender.Male.toString().toLowerCase(Locale.ROOT)))
            this.gender = Gender.Male;
        else if(registrationDTO.getGender().toLowerCase().equals(Gender.Female.toString().toLowerCase(Locale.ROOT)))
            this.gender = Gender.Female;
        else
            this.gender = Gender.NonBinary;
    }
}
