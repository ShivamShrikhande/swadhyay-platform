package com.swadhyay.chat.controller;

import com.swadhyay.chat.dto.MessageRequest;
import com.swadhyay.chat.dto.MessageResponse;
import com.swadhyay.chat.service.MessageService;
import com.swadhyay.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ApiResponse<MessageResponse> sendMessage(
            @Valid @RequestBody MessageRequest request) {

        MessageResponse response =
                messageService.sendMessage(request);

        return ApiResponse.<MessageResponse>builder()
                .success(true)
                .message("Message sent successfully")
                .data(response)
                .build();
    }
    @GetMapping("/{friendId}")
    public ApiResponse<List<MessageResponse>> getConversation(
            @PathVariable Long friendId) {

        List<MessageResponse> response =
                messageService.getConversation(friendId);

        return ApiResponse.<List<MessageResponse>>builder()
                .success(true)
                .message("Conversation fetched successfully")
                .data(response)
                .build();
    }
    @PutMapping("/read/{friendId}")
    public ApiResponse<Void> markConversationAsRead(
            @PathVariable Long friendId) {

        messageService.markConversationAsRead(friendId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Conversation marked as read")
                .data(null)
                .build();
    }
    @GetMapping("/unread-count")
    public ApiResponse<Long> getUnreadCount() {

        long count = messageService.getUnreadCount();

        return ApiResponse.<Long>builder()
                .success(true)
                .message("Unread count fetched successfully")
                .data(count)
                .build();
    }
}