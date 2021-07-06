package services.profileservices.dto;

import lombok.*;
import services.profileservices.model.FavouriteCollection;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteResponse {
    List<Integer> postIds;
    List<FavouriteCollection> collections;
}
