package services.campaignservices.dto;

import lombok.*;
import services.campaignservices.model.Commercial;
import services.campaignservices.model.TargetGroup;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {

    private LocalDate startDate;
    private LocalDate changedDate;
    private LocalDate endDate;
    private int countAllowed;
    private int countDone;
    private Boolean isItPost;
    private List<Integer> commercials;
    TargetGroup targetGroup;
    String username;
}
