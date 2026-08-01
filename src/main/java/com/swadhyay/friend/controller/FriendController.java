package com.swadhyay.friend.controller;

import com.swadhyay.common.dto.ApiResponse;
import com.swadhyay.friend.dto.FriendRequestResponse;
import com.swadhyay.friend.dto.FriendResponse;
import com.swadhyay.friend.dto.SendFriendRequest;
import com.swadhyay.friend.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request")
    public ApiResponse<FriendRequestResponse> sendRequest(
            @Valid @RequestBody SendFriendRequest request) {

        FriendRequestResponse response =
                friendService.sendRequest(request);

        return ApiResponse.<FriendRequestResponse>builder()
                .success(true)
                .message("Friend request sent successfully")
                .data(response)
                .build();
    }
    @GetMapping("/pending")
    public ApiResponse<List<FriendRequestResponse>> getPendingRequests() {

        List<FriendRequestResponse> response =
                friendService.getPendingRequests();

        return ApiResponse.<List<FriendRequestResponse>>builder()
                .success(true)
                .message("Pending requests fetched successfully")
                .data(response)
                .build();
    }

    @PostMapping("/accept/{requestId}")
    public ApiResponse<FriendRequestResponse> acceptRequest(
            @PathVariable Long requestId) {

        FriendRequestResponse response =
                friendService.acceptRequest(requestId);

        return ApiResponse.<FriendRequestResponse>builder()
                .success(true)
                .message("Friend request accepted successfully")
                .data(response)
                .build();
    }
    @PostMapping("/reject/{requestId}")
    public ApiResponse<FriendRequestResponse> rejectRequest(
            @PathVariable Long requestId) {

        FriendRequestResponse response =
                friendService.rejectRequest(requestId);

        return ApiResponse.<FriendRequestResponse>builder()
                .success(true)
                .message("Friend request rejected successfully")
                .data(response)
                .build();
    }
    @GetMapping
    public ApiResponse<List<FriendResponse>> getFriends() {

        List<FriendResponse> response =
                friendService.getFriends();

        return ApiResponse.<List<FriendResponse>>builder()
                .success(true)
                .message("Friends fetched successfully")
                .data(response)
                .build();
    }
}