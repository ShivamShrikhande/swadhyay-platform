//package com.swadhyay.user.service;
//
//import com.swadhyay.auth.dto.ForgotPasswordRequest;
//import com.swadhyay.auth.dto.ForgotPasswordResponse;
//import com.swadhyay.auth.dto.ResetPasswordRequest;
//import com.swadhyay.user.dto.ChangePasswordRequest;
//import com.swadhyay.user.dto.UpdateProfileRequest;
//import com.swadhyay.user.dto.UserRequest;
//import com.swadhyay.user.dto.UserResponse;
//
//import java.util.List;
//
//public interface UserService {
//
//    UserResponse registerUser(UserRequest request);
//
//    UserResponse getUserById(Long id);
//
//    List<UserResponse> getAllUsers();
//
//    void deleteUser(Long id);
//
//    UserResponse getCurrentUser();
//
//    UserResponse updateProfile(UpdateProfileRequest request);
//
//    void changePassword(ChangePasswordRequest request);
//
//
//}
package com.swadhyay.user.service;

import com.swadhyay.user.dto.ChangePasswordRequest;
import com.swadhyay.user.dto.MemberSearchResponse;
import com.swadhyay.user.dto.UpdateProfileRequest;
import com.swadhyay.user.dto.UserRequest;
import com.swadhyay.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);

    UserResponse getCurrentUser();

    UserResponse updateProfile(UpdateProfileRequest request);

    void changePassword(ChangePasswordRequest request);

    List<MemberSearchResponse> searchMembers(String keyword);

}