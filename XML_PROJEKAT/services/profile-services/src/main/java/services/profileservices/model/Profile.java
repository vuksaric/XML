package services.profileservices.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String biography;
    private Boolean isPrivate;
    private String website;
    private ProfileCategory category;
    private Boolean canBeTagged;
    private Boolean canBeMessaged;

    private Boolean notifyProfileActivity;
    private Boolean canBeMessagedPrivate;
    private Boolean notifyPost;
    private Boolean notifyStory;
    private Boolean notifyComment;

    @ElementCollection
    @CollectionTable(name="Profile_Posts", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="post")
    private List<Integer> postIds;
    @ElementCollection
    @CollectionTable(name="Profile_Stories", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="story")
    private List<Integer> storyIds;
    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private int userInfoId;

    @ElementCollection
    @CollectionTable(name="Profile_Following", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="Following_ID")
    private List<Integer> following;
    @ElementCollection
    @CollectionTable(name="Profile_Followers", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="Followers_ID")
    private List<Integer> followers;
    @ElementCollection
    @CollectionTable(name="Profile_Friends", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="Friends_ID")
    private List<Integer> friends;
    @ElementCollection
    @CollectionTable(name="Profile_Blocked", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="Blocked_ID")
    private List<Integer> blocked;
    @ElementCollection
    @CollectionTable(name="Profile_Muted", joinColumns=@JoinColumn(name="Profile_ID"))
    @Column(name="Muted_ID")
    private List<Integer> muted;



}
