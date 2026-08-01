package com.swadhyay.chat.repository;

import com.swadhyay.chat.entity.Message;
import com.swadhyay.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderByCreatedAtAsc(
            User sender,
            User receiver,
            User receiver2,
            User sender2
    );
    List<Message> findBySenderAndReceiverAndReadFalse(
            User sender,
            User receiver
    );
    @Query("""
SELECT COUNT(m)
FROM Message m
WHERE m.receiver = :receiver
AND m.read = false
""")
    long countByReceiverAndReadFalse(User receiver);
}