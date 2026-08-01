package com.swadhyay.friend.service;
import java.util.List;
import com.swadhyay.friend.dto.FriendRequestResponse;
import com.swadhyay.friend.dto.FriendResponse;
import com.swadhyay.friend.dto.SendFriendRequest;

public interface FriendService {

    FriendRequestResponse sendRequest(
            SendFriendRequest request
    );

    List<FriendRequestResponse> getPendingRequests();
    FriendRequestResponse acceptRequest(Long requestId);
    FriendRequestResponse rejectRequest(Long requestId);
    List<FriendResponse> getFriends();
}