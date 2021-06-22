package services.notificationservices.controller;

import org.springframework.web.bind.annotation.*;
import services.notificationservices.client.AuthClient;
import services.notificationservices.dto.FollowRequestResponse;
import services.notificationservices.model.FollowRequest;
import services.notificationservices.service.IFollowRequestService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/followRequest")
public class FollowRequestController {

    private final IFollowRequestService followRequestService;


    public FollowRequestController(IFollowRequestService followRequestService) {
        this.followRequestService = followRequestService;
    }

    @PostMapping("/new")
    public FollowRequest newRequest(@RequestBody FollowRequest followRequest) {
        return followRequestService.newRequest(followRequest);
    }

    @GetMapping("/checkRequest/{loggedInId}/{currentId}")
    public Boolean checkRequest(@PathVariable int loggedInId, @PathVariable int currentId)
    {
        return followRequestService.checkRequest(loggedInId, currentId);
    }

    @GetMapping("/getAllForProfile/{loggedInId}")
    public List<FollowRequestResponse> getAllForProfile(@PathVariable int loggedInId)
    {
        return followRequestService.getAllForProfile(loggedInId);
    }

    @PutMapping("/delete/{to}/{from}")
    public void delete(@PathVariable int to, @PathVariable int from)
    {
         followRequestService.delete(to, from);
    }
}
