package services.postservices.dto;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePostRequest {
    private List<Integer> postIds;
}
