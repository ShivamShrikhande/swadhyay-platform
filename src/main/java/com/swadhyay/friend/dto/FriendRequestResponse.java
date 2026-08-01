package com.swadhyay.friend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequestResponse {

    private Long requestId;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String receiverName;

    private String status;

}