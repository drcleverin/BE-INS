package com.insurance.InsuranceApp.controller;
// src/main/java/com/example/policyapp/controller/PolicyController.java


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

import com.insurance.InsuranceApp.dto.PolicyDTO;
import com.insurance.InsuranceApp.services.PolicyService;

import jakarta.validation.Valid;

/**
 * REST Controller for Policy operations.
 * Handles incoming HTTP requests related to policies,
 * delegates to the service layer, and returns appropriate HTTP responses.
 */
@RestController
@RequestMapping("/api/policies") // Base path for all policy-related endpoints
@CrossOrigin("*")
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    /**
     * Handles POST requests to create a new policy.
     * The @Valid annotation triggers validation checks defined in PolicyDTO.
     *
     * @param policyDTO The PolicyDTO object received in the request body.
     * @return ResponseEntity containing the created PolicyDTO and HTTP status.
     */
    @PostMapping("/addPolicy")
    public ResponseEntity<PolicyDTO> createPolicy(@Valid @RequestBody PolicyDTO policyDTO) {
        // Log the incoming DTO (for debugging purposes)
        System.out.println("Received request to create policy: " + policyDTO);

        // Call the service layer to create the policy
        PolicyDTO createdPolicy = policyService.createPolicy(policyDTO);

        // Return a 201 Created status with the newly created policy data
        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    // You can add other controller methods here, e.g., GET for retrieving policies, PUT for updating, DELETE for deleting
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<PolicyDTO>> getPoliciesByUserId(@PathVariable Long userId) {
        List<PolicyDTO> policies = policyService.getPoliciesByUserId(userId);
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }
    
    @GetMapping // This will map to /api/policies (assuming @RequestMapping("/api/policies") on the class)
    public ResponseEntity<List<PolicyDTO>> getAllPolicies() {
        List<PolicyDTO> policies = policyService.getAllPolicies();
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }
    
    @PutMapping("/{policyId}") // e.g., PUT http://localhost:8093/api/policies/1
    public ResponseEntity<PolicyDTO> updatePolicy(@PathVariable Long policyId, @Valid @RequestBody PolicyDTO policyDTO) {
        try {
            PolicyDTO updatedPolicy = policyService.updatePolicy(policyId, policyDTO);
            return new ResponseEntity<>(updatedPolicy, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // This could be thrown by service if policyId not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Catch other potential errors during update
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{policyId}") // e.g., DELETE http://localhost:8093/api/policies/1
    public ResponseEntity<Void> deletePolicy(@PathVariable Long policyId) {
        boolean deleted = policyService.deletePolicy(policyId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content for successful deletion
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found if policy doesn't exist
    }
}
