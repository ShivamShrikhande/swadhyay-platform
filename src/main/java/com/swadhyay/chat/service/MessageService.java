package com.swadhyay.chat.service;

import com.swadhyay.chat.dto.MessageRequest;
import com.swadhyay.chat.dto.MessageResponse;

import java.util.List;

public interface MessageService {

    MessageResponse sendMessage(MessageRequest request);

    List<MessageResponse> getConversation(Long friendId);

    void markConversationAsRead(Long friendId);

    long getUnreadCount();
}