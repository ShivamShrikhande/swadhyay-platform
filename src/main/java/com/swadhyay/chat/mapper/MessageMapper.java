package com.swadhyay.chat.mapper;

import com.swadhyay.chat.dto.MessageResponse;
import com.swadhyay.chat.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toResponse(Message message) {

        return MessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getFirstName() + " "
                        + message.getSender().getLastName())
                .receiverId(message.getReceiver().getId())
                .receiverName(message.getReceiver().getFirstName() + " "
                        + message.getReceiver().getLastName())
                .message(message.getMessage())
                .read(message.getRead())
                .sentAt(message.getCreatedAt())
                .build();
    }
}