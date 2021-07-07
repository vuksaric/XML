package services.postservices.dto;

import lombok.*;
import services.postservices.model.Story;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponse {
    private int id;
    private String caption;
    private String location;
    private List<Integer> taggedIds;
    private Boolean closeFriends;
    private Boolean highlight;
    private LocalDateTime timeStamp;
    private List<PictureDTO> content;
    private boolean picture;

    public StoryResponse(Story story)
    {
        this.id = story.getId();
        this.caption = story.getPostInfo().getCaption();
        this.location = story.getPostInfo().getLocation();
        this.taggedIds = story.getPostInfo().getTaggedIds();
        this.closeFriends = story.getCloseFriends();
        this.highlight = story.getHighlight();
        this.timeStamp = story.getTimeStamp();
        this.content = new ArrayList<>();
    }
}
