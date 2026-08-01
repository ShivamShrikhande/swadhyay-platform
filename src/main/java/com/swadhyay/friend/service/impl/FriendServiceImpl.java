package com.swadhyay.friend.service.impl;

import com.swadhyay.common.exception.InvalidRequestException;
import com.swadhyay.common.exception.ResourceNotFoundException;
import com.swadhyay.friend.dto.FriendRequestResponse;
import com.swadhyay.friend.dto.FriendResponse;
import com.swadhyay.friend.dto.SendFriendRequest;
import com.swadhyay.friend.entity.FriendRequest;
import com.swadhyay.friend.entity.FriendRequestStatus;
import com.swadhyay.friend.repository.FriendRequestRepository;
import com.swadhyay.friend.service.FriendService;
import com.swadhyay.user.entity.User;
import com.swadhyay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRequestRepository friendRequestRepository;

    private final UserRepository userRepository;


    @Override
    public FriendRequestResponse sendRequest(
            SendFriendRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User sender = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Receiver not found"));

        // Cannot send request to yourself
        if (sender.getId().equals(receiver.getId())) {
            throw new InvalidRequestException(
                    "You cannot send a friend request to yourself."
            );
        }

        // Check duplicate request
        if (friendRequestRepository
                .findBySenderAndReceiver(sender, receiver)
                .isPresent()) {

            throw new InvalidRequestException(
                    "Friend request already sent."
            );
        }

        FriendRequest friendRequest =
                FriendRequest.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .status(FriendRequestStatus.PENDING)
                        .build();

        FriendRequest saved =
                friendRequestRepository.save(friendRequest);

        return FriendRequestResponse.builder()
                .requestId(saved.getId())
                .senderId(sender.getId())
                .senderName(sender.getFirstName() + " " + sender.getLastName())
                .receiverId(receiver.getId())
                .receiverName(receiver.getFirstName() + " " + receiver.getLastName())
                .status(saved.getStatus().name())
                .build();
    }

    @Override
    public List<FriendRequestResponse> getPendingRequests() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User currentUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return friendRequestRepository
                .findByReceiverAndStatus(
                        currentUser,
                        FriendRequestStatus.PENDING
                )
                .stream()
                .map(request ->
                        FriendRequestResponse.builder()
                                .requestId(request.getId())
                                .senderId(request.getSender().getId())
                                .senderName(
                                        request.getSender().getFirstName()
                                                + " "
                                                + request.getSender().getLastName()
                                )
                                .receiverId(currentUser.getId())
                                .receiverName(
                                        currentUser.getFirstName()
                                                + " "
                                                + currentUser.getLastName()
                                )
                                .status(request.getStatus().name())
                                .build()
                )
                .toList();


    }

    @Override
    public FriendRequestResponse acceptRequest(Long requestId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User currentUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        FriendRequest request =
                friendRequestRepository.findByIdAndReceiver(
                                requestId,
                                currentUser)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Friend request not found"));

        if (request.getStatus() != FriendRequestStatus.PENDING) {

            throw new InvalidRequestException(
                    "Friend request already processed."
            );
        }

        request.setStatus(FriendRequestStatus.ACCEPTED);

        FriendRequest saved =
                friendRequestRepository.save(request);

        return FriendRequestResponse.builder()
                .requestId(saved.getId())
                .senderId(saved.getSender().getId())
                .senderName(saved.getSender().getFirstName() + " "
                        + saved.getSender().getLastName())
                .receiverId(saved.getReceiver().getId())
                .receiverName(saved.getReceiver().getFirstName() + " "
                        + saved.getReceiver().getLastName())
                .status(saved.getStatus().name())
                .build();
    }
    @Override
    public FriendRequestResponse rejectRequest(Long requestId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User currentUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        FriendRequest request =
                friendRequestRepository.findByIdAndReceiver(
                                requestId,
                                currentUser)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Friend request not found"));

        if (request.getStatus() != FriendRequestStatus.PENDING) {

            throw new InvalidRequestException(
                    "Friend request already processed."
            );
        }

        request.setStatus(FriendRequestStatus.REJECTED);

        FriendRequest saved = friendRequestRepository.save(request);

        return FriendRequestResponse.builder()
                .requestId(saved.getId())
                .senderId(saved.getSender().getId())
                .senderName(saved.getSender().getFirstName() + " " + saved.getSender().getLastName())
                .receiverId(saved.getReceiver().getId())
                .receiverName(saved.getReceiver().getFirstName() + " " + saved.getReceiver().getLastName())
                .status(saved.getStatus().name())
                .build();
    }
    @Override
    public List<FriendResponse> getFriends() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User currentUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<FriendResponse> friends = new ArrayList<>();

        // Requests I Sent
        List<FriendRequest> sentRequests =
                friendRequestRepository.findBySenderAndStatus(
                        currentUser,
                        FriendRequestStatus.ACCEPTED);

        for (FriendRequest request : sentRequests) {

            User friend = request.getReceiver();

            friends.add(
                    FriendResponse.builder()
                            .id(friend.getId())
                            .fullName(friend.getFirstName() + " " + friend.getLastName())
                            .kendraName(friend.getKendra().getName())
                            .build()
            );
        }

        // Requests I Received
        List<FriendRequest> receivedRequests =
                friendRequestRepository.findByReceiverAndStatus(
                        currentUser,
                        FriendRequestStatus.ACCEPTED);

        for (FriendRequest request : receivedRequests) {

            User friend = request.getSender();

            friends.add(
                    FriendResponse.builder()
                            .id(friend.getId())
                            .fullName(friend.getFirstName() + " " + friend.getLastName())
                            .kendraName(friend.getKendra().getName())
                            .build()
            );
        }

        return friends;
    }

}