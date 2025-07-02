// src/main/java/com/insurance/InsuranceApp/services/PolicyService.java
package com.insurance.InsuranceApp.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insurance.InsuranceApp.dto.PolicyDTO;
import com.insurance.InsuranceApp.mapper.PolicyMapper;
import com.insurance.InsuranceApp.model.InsurancePlan;
import com.insurance.InsuranceApp.model.Policy;
import com.insurance.InsuranceApp.model.User;
import com.insurance.InsuranceApp.model.Vehicle; // Import Vehicle entity
import com.insurance.InsuranceApp.repository.InsurancePlanRepository;
import com.insurance.InsuranceApp.repository.PolicyRepository;
import com.insurance.InsuranceApp.repository.UserRepository;
import com.insurance.InsuranceApp.repository.VehicleRepository; // Import VehicleRepository
 
/**
* Service layer for Policy management.
* Contains business logic and orchestrates data access operations
* between the controller and the repository.
*/
@Service
public class PolicyService {
 
    private final PolicyRepository policyRepository;
    private final InsurancePlanRepository insurancePlanRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository; // Added VehicleRepository
 
    @Autowired
    public PolicyService(PolicyRepository policyRepository,
                         InsurancePlanRepository insurancePlanRepository,
                         UserRepository userRepository,
                         VehicleRepository vehicleRepository) { // Inject VehicleRepository
        this.policyRepository = policyRepository;
        this.insurancePlanRepository = insurancePlanRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository; // Initialize
    }
 
    /**
     * Creates a new policy based on the provided DTO.
     * Maps the DTO to an Entity, saves it to the database,
     * and then maps the saved Entity back to a DTO for response.
     *
     * @param policyDTO The PolicyDTO containing policy details.
     * @return The created PolicyDTO with the generated policy ID.
     */
    @Transactional // Ensures the entire method executes as a single database transaction
    public PolicyDTO createPolicy(PolicyDTO policyDTO) {
        // Find the InsurancePlan by ID. If not found, throw an IllegalArgumentException.
    	System.out.println(policyDTO.getPolicyId()+" is ploiss");
        InsurancePlan plan = insurancePlanRepository.findById(policyDTO.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid planId: " + policyDTO.getPlanId()));
 
        // Find the User by ID. If not found, throw an IllegalArgumentException.
        User user = userRepository.findById(policyDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + policyDTO.getUserId()));
 
        // ********************************** FIX STARTS HERE **********************************
        // Find the Vehicle by ID if provided. It's nullable, so handle its absence.
        Vehicle vehicle = null;
        if (policyDTO.getVehicleId() != null) {
            vehicle = vehicleRepository.findById(policyDTO.getVehicleId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid vehicleId: " + policyDTO.getVehicleId()));
        }
        // *********************************** FIX ENDS HERE ***********************************
 
        // Map PolicyDTO to Policy entity
        Policy policy = new Policy();
        policy.setPolicyEndDate(policyDTO.getPolicyEndDate());
        policy.setPolicyStartDate(policyDTO.getPolicyStartDate());
        policy.setPolicyStatus(policyDTO.getPolicyStatus());
        policy.setPremiumAmount(policyDTO.getPremiumAmount());
        policy.setInsurancePlan(plan);
        policy.setUser(user);
        policy.setVehicle(vehicle); // ****************** Set the Vehicle object (can be null) ******************
 
        // Save the policy entity to the database
        Policy savedPolicy = policyRepository.save(policy);
 
        // Map the saved Policy entity back to a PolicyDTO for response
        PolicyDTO responseDTO = new PolicyDTO();
        responseDTO.setPolicyId(savedPolicy.getPolicyId());
        responseDTO.setPolicyEndDate(savedPolicy.getPolicyEndDate());
        responseDTO.setPolicyStartDate(savedPolicy.getPolicyStartDate());
        responseDTO.setPolicyStatus(savedPolicy.getPolicyStatus());
        responseDTO.setPremiumAmount(savedPolicy.getPremiumAmount());
        responseDTO.setPlanId(savedPolicy.getInsurancePlan().getPlanId());
        responseDTO.setUserId(savedPolicy.getUser().getUserId());
        // ********************************** FIX STARTS HERE **********************************
        responseDTO.setVehicleId(savedPolicy.getVehicle() != null ? savedPolicy.getVehicle().getVehicleId() : null); // Get vehicleId from the Vehicle object (handle null)
        // *********************************** FIX ENDS HERE ***********************************
 
        return responseDTO;
    }
 
    // You can add other service methods here, e.g., getPolicyById, updatePolicy, deletePolicy
    @Autowired
    private PolicyMapper policyMapper;
    public List<PolicyDTO> getPoliciesByUserId(Long userId) {
        List<Policy> policies = policyRepository.findByUser_UserId(userId); // Adjust based on your field name
        return policies.stream()
                .map(policyMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PolicyDTO updatePolicy(Long policyId, PolicyDTO policyDTO) {
        // Find the existing policy
        Policy existingPolicy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with ID: " + policyId));

        // Update basic fields
        existingPolicy.setPolicyEndDate(policyDTO.getPolicyEndDate());
        existingPolicy.setPolicyStartDate(policyDTO.getPolicyStartDate());
        existingPolicy.setPolicyStatus(policyDTO.getPolicyStatus());
        existingPolicy.setPremiumAmount(policyDTO.getPremiumAmount());

        // Update relationships (Plan, User, Vehicle) - re-fetch to ensure they exist and are valid
        if (!existingPolicy.getInsurancePlan().getPlanId().equals(policyDTO.getPlanId())) {
            InsurancePlan newPlan = insurancePlanRepository.findById(policyDTO.getPlanId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid planId: " + policyDTO.getPlanId()));
            existingPolicy.setInsurancePlan(newPlan);
        }

        if (!existingPolicy.getUser().getUserId().equals(policyDTO.getUserId())) {
            User newUser = userRepository.findById(policyDTO.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid userId: " + policyDTO.getUserId()));
            existingPolicy.setUser(newUser);
        }

        // Handle vehicle update, including setting to null if vehicleId is removed
        if (policyDTO.getVehicleId() != null) {
            if (existingPolicy.getVehicle() == null || !existingPolicy.getVehicle().getVehicleId().equals(policyDTO.getVehicleId())) {
                Vehicle newVehicle = vehicleRepository.findById(policyDTO.getVehicleId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid vehicleId: " + policyDTO.getVehicleId()));
                existingPolicy.setVehicle(newVehicle);
            }
        } else {
            existingPolicy.setVehicle(null); // Explicitly set to null if DTO has null vehicleId
        }


        Policy updatedPolicy = policyRepository.save(existingPolicy); // Save the updated entity

        return mapToDTO(updatedPolicy); // Return the updated DTO
    }
    @Transactional
    public boolean deletePolicy(Long policyId) {
        if (!policyRepository.existsById(policyId)) {
            throw new IllegalArgumentException("Policy not found with ID: " + policyId);
        }
        policyRepository.deleteById(policyId);
		return false;
    }
    
    @Transactional(readOnly = true)
    public List<PolicyDTO> getAllPolicies() {
        return policyRepository.findAll().stream()
                .map(this::mapToDTO) // Reuse your existing mapToDTO helper
                .collect(Collectors.toList());
    }
    private PolicyDTO mapToDTO(Policy policy) {
        PolicyDTO dto = new PolicyDTO();
        dto.setPolicyId(policy.getPolicyId());
        dto.setPolicyEndDate(policy.getPolicyEndDate());
        dto.setPolicyStartDate(policy.getPolicyStartDate());
        dto.setPolicyStatus(policy.getPolicyStatus());
        dto.setPremiumAmount(policy.getPremiumAmount());
        dto.setPlanId(policy.getInsurancePlan().getPlanId());
        dto.setPlanName(policy.getInsurancePlan().getPlanName());
        dto.setUserId(policy.getUser().getUserId());
        dto.setVehicleId(policy.getVehicle() != null ? policy.getVehicle().getVehicleId() : null);
        return dto;
    }

    // Optional: Add a method to get a single policy as DTO (similar to DashboardService but might be useful here)
    @Transactional(readOnly = true)
    public PolicyDTO getPolicyById(Long policyId) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with ID: " + policyId));
        return mapToDTO(policy);
    }

}
 