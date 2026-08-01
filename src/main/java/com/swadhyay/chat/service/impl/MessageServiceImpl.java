package com.swadhyay.chat.service.impl;

import com.swadhyay.chat.dto.MessageRequest;
import com.swadhyay.chat.dto.MessageResponse;
import com.swadhyay.chat.entity.Message;
import com.swadhyay.chat.mapper.MessageMapper;
import com.swadhyay.chat.repository.MessageRepository;
import com.swadhyay.chat.service.MessageService;
import com.swadhyay.common.exception.InvalidRequestException;
import com.swadhyay.common.exception.ResourceNotFoundException;
import com.swadhyay.friend.entity.FriendRequestStatus;
import com.swadhyay.friend.repository.FriendRequestRepository;
import com.swadhyay.user.entity.User;
import com.swadhyay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Override
    public MessageResponse sendMessage(MessageRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User sender = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Receiver not found"));

        boolean areFriends =
                friendRequestRepository
                        .existsBySenderAndReceiverAndStatusOrSenderAndReceiverAndStatus(
                                sender,
                                receiver,
                                FriendRequestStatus.ACCEPTED,
                                receiver,
                                sender,
                                FriendRequestStatus.ACCEPTED
                        );

        if (!areFriends) {

            throw new InvalidRequestException(
                    "You can only chat with accepted friends."
            );
        }

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .message(request.getMessage())
                .read(false)
                .build();

        Message saved = messageRepository.save(message);

        return messageMapper.toResponse(saved);
    }

    @Override
    public List<MessageResponse> getConversation(Long friendId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User currentUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        User friend = userRepository.findById(friendId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Friend not found"));

        boolean areFriends =
                friendRequestRepository
                        .existsBySenderAndReceiverAndStatusOrSenderAndReceiverAndStatus(
                                currentUser,
                                friend,
                                FriendRequestStatus.ACCEPTED,
                                friend,
                                currentUser,
                                FriendRequestStatus.ACCEPTED
                        );

        if (!areFriends) {

            throw new InvalidRequestException(
                    "You can only view conversations with accepted friends."
            );
        }

        return messageRepository
                .findBySenderAndReceiverOrReceiverAndSenderOrderByCreatedAtAsc(
                        currentUser,
                        friend,
                        currentUser,
                        friend
                )
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }
    @Override
    public void markConversationAsRead(Long friendId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User currentUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        User friend = userRepository.findById(friendId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Friend not found"));

        List<Message> unreadMessages =
                messageRepository.findBySenderAndReceiverAndReadFalse(
                        friend,
                        currentUser
                );

        unreadMessages.forEach(message -> message.setRead(true));

        messageRepository.saveAll(unreadMessages);
    }
    @Override
    public long getUnreadCount() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User currentUser = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return messageRepository.countByReceiverAndReadFalse(currentUser);
    }
}