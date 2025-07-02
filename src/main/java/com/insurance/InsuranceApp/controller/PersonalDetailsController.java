//package com.insurance.InsuranceApp.controller;
//
//
//import com.insurance.InsuranceApp.dto.PersonalDetailsDTO;
//import com.insurance.InsuranceApp.model.PersonalDetails;
//import com.insurance.InsuranceApp.services.PersonalDetailsService;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/personal-details")
//@CrossOrigin("*")
//public class PersonalDetailsController {
//	
//
//    private final PersonalDetailsService personalDetailsService;
//
//    public PersonalDetailsController(PersonalDetailsService personalDetailsService) {
//        this.personalDetailsService = personalDetailsService;
//        System.out.println("insideee......");
//    }
//
//    @PostMapping("/save")
//    public ResponseEntity<PersonalDetails> savePersonalDetails(@Valid @RequestBody PersonalDetailsDTO personalDetailsDTO) {
//        try {
//        	System.out.println("from /save/.............");
//        	System.out.println(personalDetailsDTO+ "............. form personalCOntroller");
//            PersonalDetails savedDetails = personalDetailsService.savePersonalDetails(personalDetailsDTO);
//            return new ResponseEntity<>(savedDetails, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            // Handle cases where User ID is not found
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            // Generic error handling
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<PersonalDetails> getPersonalDetailsByUserId(@PathVariable Long userId) {
//        PersonalDetails personalDetails = personalDetailsService.getPersonalDetailsByUserId(userId);
//        if (personalDetails != null) {
//            return new ResponseEntity<>(personalDetails, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}


package com.insurance.InsuranceApp.controller;

import com.insurance.InsuranceApp.dto.PersonalDetailsDTO;
import com.insurance.InsuranceApp.model.PersonalDetails;
import com.insurance.InsuranceApp.services.PersonalDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal-details")
@CrossOrigin("*")
public class PersonalDetailsController {

    private final PersonalDetailsService personalDetailsService;

    public PersonalDetailsController(PersonalDetailsService personalDetailsService) {
        this.personalDetailsService = personalDetailsService;
        System.out.println("insideee......");
    }

    @PostMapping // Changed from /save to just @PostMapping, assumes it's for creation
    public ResponseEntity<PersonalDetails> createPersonalDetails(@Valid @RequestBody PersonalDetailsDTO personalDetailsDTO) {
        try {
            System.out.println("from /api/personal-details (POST).............");
            System.out.println(personalDetailsDTO + "............. from personalController");
            PersonalDetails savedDetails = personalDetailsService.savePersonalDetails(personalDetailsDTO);
            return new ResponseEntity<>(savedDetails, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // New PUT API for updating existing personal details
    @PutMapping("/{id}")
    public ResponseEntity<PersonalDetails> updatePersonalDetails(@PathVariable Long id, @Valid @RequestBody PersonalDetailsDTO personalDetailsDTO) {
        try {
            System.out.println("from /api/personal-details/{id} (PUT).............");
            PersonalDetails updatedDetails = personalDetailsService.updatePersonalDetails(id, personalDetailsDTO);
            return new ResponseEntity<>(updatedDetails, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<PersonalDetails> getPersonalDetailsByUserId(@PathVariable Long userId) {
        PersonalDetails personalDetails = personalDetailsService.getPersonalDetailsByUserId(userId);
        if (personalDetails != null) {
            return new ResponseEntity<>(personalDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
