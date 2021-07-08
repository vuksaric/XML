package services.authservices.dto;

import lombok.*;
import services.authservices.model.RegistrationRequest;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {

    int id;
    String name;


    public RegistrationRequestDTO(RegistrationRequest request)
    {
        this.id = request.getId();
        this.name = request.getUserInfo().getUsername() + request.getUserInfo().getSurname();
    }
}
