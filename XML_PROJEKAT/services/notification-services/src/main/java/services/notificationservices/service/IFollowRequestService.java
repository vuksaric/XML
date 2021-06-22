package services.notificationservices.service;

import services.notificationservices.dto.FollowRequestResponse;
import services.notificationservices.model.FollowRequest;

import java.util.List;

public interface IFollowRequestService {

    FollowRequest newRequest(FollowRequest request);
    boolean checkRequest(int loggedIn, int current);
    List<FollowRequestResponse> getAllForProfile(int loggedIn);
    void delete(int to, int from);

}
