//package com.swadhyay.user.repository;
//
//import com.swadhyay.user.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//
//    Optional<User> findByMobileNumber(String mobileNumber);
//
//    Optional<User> findByEmail(String email);
//
//    boolean existsByMobileNumber(String mobileNumber);
//
//    boolean existsByEmail(String email);
//}

package com.swadhyay.user.repository;

import com.swadhyay.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobileNumber(String mobileNumber);

    Optional<User> findByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByEmail(String email);

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName
    );
}