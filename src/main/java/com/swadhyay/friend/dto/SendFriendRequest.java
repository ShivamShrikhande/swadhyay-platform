package com.swadhyay.friend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendFriendRequest {

    @NotNull(message = "Receiver id is required")
    private Long receiverId;

}