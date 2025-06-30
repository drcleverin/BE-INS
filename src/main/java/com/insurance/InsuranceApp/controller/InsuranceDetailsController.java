//package com.insurance.InsuranceApp.controller;
//
//import com.insurance.InsuranceApp.dto.ClaimList;
//import com.insurance.InsuranceApp.services.InsuranceDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * REST Controller for exposing aggregated insurance details.
// */
//@RestController // Marks this class as a REST controller
//@RequestMapping("/api/insurance") // Base path for all endpoints in this controller
//@CrossOrigin("*")
//public class InsuranceDetailsController {
//
//    private final InsuranceDetailsService insuranceDetailsService;
//
//    // Inject the service using constructor injection (recommended)
//    @Autowired
//    public InsuranceDetailsController(InsuranceDetailsService insuranceDetailsService) {
//        this.insuranceDetailsService = insuranceDetailsService;
//    }
//
//    /**
//     * Endpoint to fetch all aggregated claim details.
//     * URL: GET /api/insurance/claims/aggregated
//     * @return A list of AggregatedClaimDetailsDTO, or an empty list if no claims are found.
//     */
//    @GetMapping("/claims/aggregated")
//    public ResponseEntity<List<ClaimList>> getAggregatedClaimDetails() {
//        // Call the service layer to get the aggregated data
//        List<ClaimList> aggregatedClaims = insuranceDetailsService.getAllAggregatedClaimDetails();
//
//        // Return the list with an OK (200) status
//        return ResponseEntity.ok(aggregatedClaims);
//    }
//    
//    @PutMapping("/claim/update")
//    
//
//    // You can add more endpoints here if needed, e.g., to get details for a specific claim ID
//    // @GetMapping("/claims/aggregated/{claimId}")
//    // public ResponseEntity<AggregatedClaimDetailsDTO> getAggregatedClaimDetailsById(@PathVariable Long claimId) {
//    //     // ... logic to find a single claim and map it ...
//    // }
//}


package com.insurance.InsuranceApp.controller;

import com.insurance.InsuranceApp.dto.ClaimList;
import com.insurance.InsuranceApp.dto.ClaimUpdateDto; // Import the new DTO
import com.insurance.InsuranceApp.services.InsuranceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import HttpStatus for response
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable; // Import PathVariable
import org.springframework.web.bind.annotation.RequestBody; // Import RequestBody

import java.util.List;

/**
 * REST Controller for exposing aggregated insurance details.
 */
@RestController // Marks this class as a REST controller
@RequestMapping("/api/insurance") // Base path for all endpoints in this controller
@CrossOrigin("*")
public class InsuranceDetailsController {

    private final InsuranceDetailsService insuranceDetailsService;

    // Inject the service using constructor injection (recommended)
    @Autowired
    public InsuranceDetailsController(InsuranceDetailsService insuranceDetailsService) {
        this.insuranceDetailsService = insuranceDetailsService;
    }

    /**
     * Endpoint to fetch all aggregated claim details.
     * URL: GET /api/insurance/claims/aggregated
     * @return A list of AggregatedClaimDetailsDTO, or an empty list if no claims are found.
     */
    @GetMapping("/claims/aggregated")
    public ResponseEntity<List<ClaimList>> getAggregatedClaimDetails() {
        // Call the service layer to get the aggregated data
        List<ClaimList> aggregatedClaims = insuranceDetailsService.getAllAggregatedClaimDetails();

        // Return the list with an OK (200) status
        return ResponseEntity.ok(aggregatedClaims);
    }
    
    /**
     * Endpoint to update an existing claim.
     * URL: PUT /api/insurance/claims/{claimId}
     *
     * @param claimId The ID of the claim to update, extracted from the URL path.
     * @param claimUpdateDto The DTO containing the updated claim details, sent in the request body.
     * @return ResponseEntity with:
     * - HttpStatus.NO_CONTENT (204) if the claim was successfully updated.
     * - HttpStatus.NOT_FOUND (404) if no claim with the given ID exists.
     * - HttpStatus.BAD_REQUEST (400) if the request body is invalid (optional, consider @Valid).
     */
    @PutMapping("/claims/{claimId}") // Corrected path for PUT operation
    public ResponseEntity<Void> updateClaim(@PathVariable Long claimId, @RequestBody ClaimUpdateDto claimUpdateDto) {
        // Call the service to update the claim.
        // The service layer will handle the business logic and database interaction.
        boolean updated = insuranceDetailsService.updateClaim(claimId, claimUpdateDto);

        if (updated) {
            // If the update was successful, return 204 No Content, as per REST best practices for PUT.
            return ResponseEntity.noContent().build();
        } else {
            // If the claim was not found (e.g., invalid claimId), return 404 Not Found.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}