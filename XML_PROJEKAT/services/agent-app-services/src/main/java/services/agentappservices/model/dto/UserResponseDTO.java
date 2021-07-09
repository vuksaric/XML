package services.agentappservices.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import services.agentappservices.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String token;
    private int tokenExpiresIn;

    public UserResponseDTO(User user, String token){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.token = token;
    }
}
