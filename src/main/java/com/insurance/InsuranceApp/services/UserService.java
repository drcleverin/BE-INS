//// src/main/java/com/example/insuranceapp/services/UserService.java
//package com.insurance.InsuranceApp.services;
//
//import com.insurance.InsuranceApp.model.User;
//import com.insurance.InsuranceApp.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//    
//    public Long getUserIdByUsername(String username) {
//        return userRepository.findByUsername(username)
//                .map(User::getUserId) // or getId(), depending on your field name
//                .orElse(null);
//    }
//
//    public String getUserRoleById(Long userId) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (user.getRole() != null) {
//                return user.getRole().getRoleType();
//            }
//        }
//        return null; // User not found or role not assigned
//    }
//
//    // Other user-related methods (e.g., getting user details, updating profile)
//}


package com.insurance.InsuranceApp.services;

import com.insurance.InsuranceApp.model.User;
import com.insurance.InsuranceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // New method: Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // New method: Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // New method: Create a user
    public User createUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // New method: Update a user
    public Optional<User> updateUser(Long id, User updatedInfo) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedInfo.getUsername());
                    user.setEmail(updatedInfo.getEmail());
                    // Consider if role should be updated via this endpoint or a separate admin-only one
                    if (updatedInfo.getRole() != null) {
                        user.setRole(updatedInfo.getRole());
                    }
                    // If password is part of updatedInfo and needs to be updated, encode it
                    // if (updatedInfo.getPassword() != null && !updatedInfo.getPassword().isEmpty()) {
                    //     user.setPassword(passwordEncoder.encode(updatedInfo.getPassword()));
                    // }
                    return userRepository.save(user);
                });
    }

    // New method: Delete a user
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getUserId) // or getId(), depending on your field name
                .orElse(null);
    }

    public String getUserRoleById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole() != null) {
                return user.getRole().getRoleType();
            }
        }
        return null; // User not found or role not assigned
    }
}