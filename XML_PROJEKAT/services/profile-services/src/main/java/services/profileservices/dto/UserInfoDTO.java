package services.profileservices.dto;

import services.profileservices.model.Gender;

import java.time.LocalDate;

public class UserInfoDTO {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;
}
