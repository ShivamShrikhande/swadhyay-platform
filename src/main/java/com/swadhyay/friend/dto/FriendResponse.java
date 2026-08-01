package com.swadhyay.friend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendResponse {

    private Long id;

    private String fullName;

    private String kendraName;

}