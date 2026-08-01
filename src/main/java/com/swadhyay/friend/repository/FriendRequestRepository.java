package com.swadhyay.friend.repository;

import com.swadhyay.friend.entity.FriendRequest;
import com.swadhyay.friend.entity.FriendRequestStatus;
import com.swadhyay.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository
        extends JpaRepository<FriendRequest, Long> {

    // Check if request already exists
    Optional<FriendRequest> findBySenderAndReceiver(
            User sender,
            User receiver
    );

    // Pending requests received by logged-in user
    List<FriendRequest> findByReceiverAndStatus(
            User receiver,
            FriendRequestStatus status
    );

    // Requests sent by logged-in user
    List<FriendRequest> findBySender(
            User sender
    );

    Optional<FriendRequest> findByIdAndReceiver(
            Long id,
            User receiver
    );
    List<FriendRequest> findBySenderAndStatus(
            User sender,
            FriendRequestStatus status
    );

    boolean existsBySenderAndReceiverAndStatus(
            User sender,
            User receiver,
            FriendRequestStatus status
    );

    boolean existsBySenderAndReceiverAndStatusOrSenderAndReceiverAndStatus(
            User sender,
            User receiver,
            FriendRequestStatus status1,
            User receiver2,
            User sender2,
            FriendRequestStatus status2
    );



}