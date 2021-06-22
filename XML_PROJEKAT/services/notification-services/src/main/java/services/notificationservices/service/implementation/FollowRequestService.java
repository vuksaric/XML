package services.notificationservices.service.implementation;

import org.springframework.stereotype.Service;
import services.notificationservices.client.AuthClient;
import services.notificationservices.dto.FollowRequestResponse;
import services.notificationservices.model.FollowRequest;
import services.notificationservices.repository.FollowRequestRepository;
import services.notificationservices.service.IFollowRequestService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowRequestService implements IFollowRequestService {

    private final FollowRequestRepository followRequestRepository;
    private final AuthClient authClient;

    public FollowRequestService(FollowRequestRepository followRequestRepository, AuthClient authClient) {
        this.followRequestRepository = followRequestRepository;
        this.authClient = authClient;
    }

    @Override
    public FollowRequest newRequest(FollowRequest request) {
        return followRequestRepository.save(request);
    }

    @Override
    public boolean checkRequest(int loggedIn, int current) {
        List<FollowRequest> followRequests = followRequestRepository.findAll();
        for(FollowRequest followRequest : followRequests)
        {
            if(followRequest.getFromProfileId() == loggedIn && followRequest.getToProfileId()==current)
                return true;
        }
        return false;
    }

    @Override
    public List<FollowRequestResponse> getAllForProfile(int loggedIn) {
        List<FollowRequest> followRequests = followRequestRepository.findAllByToProfileId(loggedIn);
        List<FollowRequestResponse> usernames = new ArrayList<>();
        for(FollowRequest followRequest : followRequests)
        {
            String username = authClient.getUsername(followRequest.getFromProfileId());
            usernames.add(new FollowRequestResponse(username,followRequest.getFromProfileId()));
        }
        return usernames;
    }

    @Override
    public void delete(int to, int from) {
        FollowRequest followRequest = followRequestRepository.findOneByToAndFrom(to, from);
        followRequestRepository.delete(followRequest);

    }


}
