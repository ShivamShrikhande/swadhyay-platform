package com.swadhyay.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {

    @NotNull(message = "Receiver is required")
    private Long receiverId;

    @NotBlank(message = "Message cannot be empty")
    private String message;
}