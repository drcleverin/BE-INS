//package com.insurance.InsuranceApp.services;
//
//
//import com.insurance.InsuranceApp.dto.PersonalDetailsDTO;
//import com.insurance.InsuranceApp.model.PersonalDetails;
//import com.insurance.InsuranceApp.model.User;
//import com.insurance.InsuranceApp.repository.PersonalDetailsRepository;
//import com.insurance.InsuranceApp.repository.UserRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@Service
//public class PersonalDetailsService {
//
//    private final PersonalDetailsRepository personalDetailsRepository;
//    private final UserRepository userRepository; // To fetch the User entity
//
//    public PersonalDetailsService(PersonalDetailsRepository personalDetailsRepository, UserRepository userRepository) {
//        this.personalDetailsRepository = personalDetailsRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public PersonalDetails savePersonalDetails(PersonalDetailsDTO personalDetailsDTO) {
//        // 1. Fetch the User entity
//        // In a real application, you might get the User ID from the authenticated context
//        // rather than from the DTO, especially for security and integrity.
//        Optional<User> userOptional = userRepository.findById(personalDetailsDTO.getUserId());
//        if (userOptional.isEmpty()) {
//            throw new IllegalArgumentException("User not found with ID: " + personalDetailsDTO.getUserId());
//        }
//        User user = userOptional.get();
//
//        // Check if PersonalDetails already exists for this user to avoid duplicates
//        // If it exists, update it; otherwise, create a new one.
//        PersonalDetails personalDetails = personalDetailsRepository.findByUserId(user.getUserId());
//        if (personalDetails == null) {
//            personalDetails = new PersonalDetails();
//            personalDetails.setUser(user); // Set the user for new details
//        }
//
//        // 2. Map DTO to Entity and apply default values for nulls
//        // Note: For firstName and lastName, since they are nullable = false in entity,
//        // frontend validation or DTO @NotBlank should prevent them from being null.
//        // However, this default logic acts as a fail-safe.
//        personalDetails.setFirstName(personalDetailsDTO.getFirstName() != null ? personalDetailsDTO.getFirstName() : "Not Provided");
//        personalDetails.setLastName(personalDetailsDTO.getLastName() != null ? personalDetailsDTO.getLastName() : "Not Provided");
//        personalDetails.setEmail(personalDetailsDTO.getEmail() != null ? personalDetailsDTO.getEmail() : "default@example.com");
//        personalDetails.setPhoneNumber(personalDetailsDTO.getPhoneNumber() != null ? personalDetailsDTO.getPhoneNumber() : "N/A");
//        personalDetails.setDateOfBirth(personalDetailsDTO.getDateOfBirth() != null ? personalDetailsDTO.getDateOfBirth() : LocalDate.of(1900, 1, 1));
//        personalDetails.setGender(personalDetailsDTO.getGender() != null ? personalDetailsDTO.getGender() : "Unknown");
//        personalDetails.setAddress(personalDetailsDTO.getAddress() != null ? personalDetailsDTO.getAddress() : "Not Provided");
//        personalDetails.setCity(personalDetailsDTO.getCity() != null ? personalDetailsDTO.getCity() : "Unknown City");
//        personalDetails.setPinCode(personalDetailsDTO.getPinCode() != null ? personalDetailsDTO.getPinCode() : "000000");
//
//        // 3. Save the entity
//        return personalDetailsRepository.save(personalDetails);
//    }
//    
//
//    // You can add more methods here, e.g., getPersonalDetailsByUserId, updatePersonalDetails, etc.
//    @Transactional(readOnly = true)
//    public PersonalDetails getPersonalDetailsByUserId(Long userId) {
//        return personalDetailsRepository.findByUserId(userId);
//    }
//}


package com.insurance.InsuranceApp.services;

import com.insurance.InsuranceApp.dto.PersonalDetailsDTO;
import com.insurance.InsuranceApp.model.PersonalDetails;
import com.insurance.InsuranceApp.model.User;
import com.insurance.InsuranceApp.repository.PersonalDetailsRepository;
import com.insurance.InsuranceApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalDetailsService {

    private final PersonalDetailsRepository personalDetailsRepository;
    private final UserRepository userRepository; // Inject UserRepository

    public PersonalDetailsService(PersonalDetailsRepository personalDetailsRepository, UserRepository userRepository) {
        this.personalDetailsRepository = personalDetailsRepository;
        this.userRepository = userRepository;
    }

    public PersonalDetails savePersonalDetails(PersonalDetailsDTO personalDetailsDTO) {
        Optional<User> userOptional = userRepository.findById(personalDetailsDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with ID " + personalDetailsDTO.getUserId() + " not found.");
        }

        PersonalDetails personalDetails = new PersonalDetails();
        // Assuming personalDetailsDTO correctly maps to PersonalDetails fields
        personalDetails.setFirstName(personalDetailsDTO.getFirstName());
        personalDetails.setLastName(personalDetailsDTO.getLastName());
        personalDetails.setPhoneNumber(personalDetailsDTO.getPhoneNumber());
        personalDetails.setDateOfBirth(personalDetailsDTO.getDateOfBirth());
        personalDetails.setGender(personalDetailsDTO.getGender());
        personalDetails.setAddress(personalDetailsDTO.getAddress());
        personalDetails.setCity(personalDetailsDTO.getCity());
        personalDetails.setPinCode(personalDetailsDTO.getPinCode());
//        personalDetails.setFamilyMembers(personalDetailsDTO.getFamilyMembers());
//        personalDetails.setSumInsured(personalDetailsDTO.getSumInsured());
        personalDetails.setUser(userOptional.get()); // Link to the User entity

        return personalDetailsRepository.save(personalDetails);
    }

 // In PersonalDetailsService.java
    public PersonalDetails getPersonalDetailsByUserId(Long userId) {
        return personalDetailsRepository.findTopByUserIdOrderByPersonalDetailsIdDesc(userId);
    }

    // New method to update personal details
    public PersonalDetails updatePersonalDetails(Long id, PersonalDetailsDTO personalDetailsDTO) {
        Optional<PersonalDetails> existingPersonalDetailsOptional = personalDetailsRepository.findById(id);
        if (!existingPersonalDetailsOptional.isPresent()) {
            throw new IllegalArgumentException("Personal Details with ID " + id + " not found.");
        }

        PersonalDetails existingPersonalDetails = existingPersonalDetailsOptional.get();

        // Update fields from DTO
        existingPersonalDetails.setFirstName(personalDetailsDTO.getFirstName());
        existingPersonalDetails.setLastName(personalDetailsDTO.getLastName());
        existingPersonalDetails.setPhoneNumber(personalDetailsDTO.getPhoneNumber());
        existingPersonalDetails.setDateOfBirth(personalDetailsDTO.getDateOfBirth());
        existingPersonalDetails.setGender(personalDetailsDTO.getGender());
        existingPersonalDetails.setAddress(personalDetailsDTO.getAddress());
        existingPersonalDetails.setCity(personalDetailsDTO.getCity());
        existingPersonalDetails.setPinCode(personalDetailsDTO.getPinCode());
//        existingPersonalDetails.setFamilyMembers(personalDetailsDTO.getFamilyMembers());
//        existingPersonalDetails.setSumInsured(personalDetailsDTO.getSumInsured());
        // No need to update userId here as it's linked to an existing user

        return personalDetailsRepository.save(existingPersonalDetails);
    }
}
