package services.profileservices.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerificationRequest {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProfileCategory category;
    private int officialDocument; //TREBA BITI SLIKA
    private boolean confirmed;
    private int profileId;

    public VerificationRequest(String name, String surname, ProfileCategory category, int officialDocument, int profileId){
        this.name=name;
        this.surname = surname;
        this.category = category;
        this.officialDocument = officialDocument;
        this.profileId = profileId;
        this.confirmed = false;
    }
}
