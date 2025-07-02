//package com.insurance.InsuranceApp.controller;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.insurance.InsuranceApp.model.User;
//import com.insurance.InsuranceApp.repository.UserRepository;
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/users")
//    public List<User> getAllUsersWithPolicies() {
////        return userRepository.findAllWithPolicies();
//    	return userRepository.findAll();
//    }
////    @PostMapping("/users")
////    public ResponseEntity<User> createUser(@RequestBody User user) {
////        User savedUser = userRepository.save(user);
////        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
////    }
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
//    }
//    @PutMapping("/users/{id}")
//    public ResponseEntity<User> updateUser(
//            @PathVariable Long id,
//            @RequestBody User updatedInfo) {
//
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(updatedInfo.getUsername());
//                    user.setEmail(updatedInfo.getEmail());
//                    user.setRole(updatedInfo.getRole()); // assuming this is a Role entity or string
//                    User updatedUser = userRepository.save(user);
//                    return ResponseEntity.ok(updatedUser);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//    @DeleteMapping("/users/delete/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        if (userRepository.existsById(id)) {
//            userRepository.deleteById(id);
//            return ResponseEntity.noContent().build(); // 204
//        } else {
//            return ResponseEntity.notFound().build(); // 404
//        }
//    }
//}



package com.insurance.InsuranceApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.InsuranceApp.model.User;
import com.insurance.InsuranceApp.services.UserService; // Import UserService

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService; // Use UserService

    @Autowired
    public AdminController(UserService userService) { // Inject UserService
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() { // Renamed method for clarity
        return userService.getAllUsers(); // Delegate to UserService
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id) // Delegate to UserService
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user); // Delegate to UserService
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedInfo) {
        return userService.updateUser(id, updatedInfo) // Delegate to UserService
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) { // Delegate to UserService
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
