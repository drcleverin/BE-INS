//package com.insurance.InsuranceApp.services;
//
//// src/main/java/com/example/policyapp/service/DashboardService.java
////package com.example.policyapp.service;
//
//
//import com.insurance.InsuranceApp.dto.PolicyResponseDTO;
//import com.insurance.InsuranceApp.model.Policy;
//import com.insurance.InsuranceApp.repository.PolicyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///**
// * Service layer for fetching dashboard and policy details.
// * Handles business logic for retrieving and mapping Policy entities
// * to PolicyResponseDTOs.
// */
//@Service
//public class DashboardService {
//
//    private final com.insurance.InsuranceApp.repository.PolicyRepository policyRepository;
//
//    @Autowired
//    public DashboardService(PolicyRepository policyRepository) {
//        this.policyRepository = policyRepository;
//    }
//
//    /**
//     * Retrieves all policies and maps them to PolicyResponseDTOs,
//     * enriching them with associated InsurancePlan details.
//     *
//     * @return A list of PolicyResponseDTOs.
//     */
//    public List<com.insurance.InsuranceApp.dto.PolicyResponseDTO
//            > getAllPolicies() {
//        List<com.insurance.InsuranceApp.model.Policy> policies = policyRepository.findAll();
//        return policies.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//
//
//    /**
//     * Retrieves a single policy by its ID and maps it to a PolicyResponseDTO.
//     *
//     * @param policyId The ID of the policy to retrieve.
//     * @return An Optional containing the PolicyResponseDTO if found, empty otherwise.
//     */
//    public Optional<com.insurance.InsuranceApp.dto.PolicyResponseDTO> getPolicyById(Long policyId) {
//        return policyRepository.findById(policyId)
//                .map(this::convertToDto);
//    }
//
//    /**
//     * Helper method to convert a Policy entity to a PolicyResponseDTO.
//     * This method fetches details from the associated InsurancePlan.
//     *
//     * @param policy The Policy entity to convert.
//     * @return A populated PolicyResponseDTO.
//     */
//    private PolicyResponseDTO convertToDto(Policy policy) {
//        PolicyResponseDTO dto = new PolicyResponseDTO();
//        dto.setPolicyId(policy.getPolicyId());
////        dto.setPolicyNumber(policy.getPolicyNumber());
//        dto.setPolicyEndDate(policy.getPolicyEndDate());
//        dto.setPolicyStartDate(policy.getPolicyStartDate());
//        dto.setPolicyStatus(policy.getPolicyStatus());
//        dto.setPremiumAmount(policy.getPremiumAmount());
//        dto.setUserId(policy.getUserId());
//        dto.setVehicleId(policy.getVehicleId()); // Can be null
//
//        // Populate details from associated InsurancePlan
//        if (policy.getInsurancePlan() != null) {
//            dto.setPlanId(policy.getInsurancePlan().getPlanId());
//            dto.setPlanName(policy.getInsurancePlan().getPlanName());
//            // Ensure planType is converted to String correctly from Enum
//            dto.setPlanType(policy.getInsurancePlan().getPlanType().name());
//            dto.setBasePremium(policy.getInsurancePlan().getBasePremium());
//            dto.setCoverage(policy.getInsurancePlan().getCoverage());
//            dto.setDescriptionAboutPolicy(policy.getInsurancePlan().getDescriptionAboutPolicy());
//        }
//        return dto;
//    }
//}


package com.insurance.InsuranceApp.services;

// src/main/java/com/example/policyapp/service/DashboardService.java
//package com.example.policyapp.service;


import com.insurance.InsuranceApp.dto.PolicyResponseDTO;
import com.insurance.InsuranceApp.model.Policy;
import com.insurance.InsuranceApp.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for fetching dashboard and policy details.
 * Handles business logic for retrieving and mapping Policy entities
 * to PolicyResponseDTOs.
 */
@Service
public class DashboardService {

    private final com.insurance.InsuranceApp.repository.PolicyRepository policyRepository;

    @Autowired
    public DashboardService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    /**
     * Retrieves all policies and maps them to PolicyResponseDTOs,
     * enriching them with associated InsurancePlan details.
     *
     * @return A list of PolicyResponseDTOs.
     */
    public List<com.insurance.InsuranceApp.dto.PolicyResponseDTO
            > getAllPolicies() {
        List<com.insurance.InsuranceApp.model.Policy> policies = policyRepository.findAll();
        return policies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all policies associated with a specific user ID and maps them to PolicyResponseDTOs.
     *
     * @param userId The ID of the user whose policies are to be retrieved.
     * @return A list of PolicyResponseDTOs for the specified user.
     */
    public List<PolicyResponseDTO> getPoliciesByUserId(Long userId) {
        List<Policy> policies = policyRepository.findByUser_UserId(userId);
//        List<Policy> findByUser_UserId(Long userId); // Assuming User entity has a field named 'userId' or 'id'

        return policies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a single policy by its ID and maps it to a PolicyResponseDTO.
     *
     * @param policyId The ID of the policy to retrieve.
     * @return An Optional containing the PolicyResponseDTO if found, empty otherwise.
     */
    public Optional<com.insurance.InsuranceApp.dto.PolicyResponseDTO> getPolicyById(Long policyId) {
        return policyRepository.findById(policyId)
                .map(this::convertToDto);
    }

    /**
     * Helper method to convert a Policy entity to a PolicyResponseDTO.
     * This method fetches details from the associated InsurancePlan.
     *
     * @param policy The Policy entity to convert.
     * @return A populated PolicyResponseDTO.
     */
    private PolicyResponseDTO convertToDto(Policy policy) {
        PolicyResponseDTO dto = new PolicyResponseDTO();
        dto.setPolicyId(policy.getPolicyId());
//        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setPolicyEndDate(policy.getPolicyEndDate());
        dto.setPolicyStartDate(policy.getPolicyStartDate());
        dto.setPolicyStatus(policy.getPolicyStatus());
        dto.setPremiumAmount(policy.getPremiumAmount());
        dto.setUserId(policy.getUserId());
        if (policy.getVehicle() != null) { // This is the crucial change
            dto.setVehicleId(policy.getVehicle().getVehicleId());
            dto.setRegistrationNumber(policy.getVehicle().getRegistrationNumber()); // Line 178 or nearby
        } else {
            dto.setVehicleId(null); // Explicitly set to null if no vehicle
            dto.setRegistrationNumber(null); // Explicitly set to null if no vehicle
        }
        // Populate details from associated InsurancePlan
        if (policy.getInsurancePlan() != null) {
            dto.setPlanId(policy.getInsurancePlan().getPlanId());
            dto.setPlanName(policy.getInsurancePlan().getPlanName());
            // Ensure planType is converted to String correctly from Enum
            dto.setPlanType(policy.getInsurancePlan().getPlanType().name());
            dto.setBasePremium(policy.getInsurancePlan().getBasePremium());
            dto.setCoverage(policy.getInsurancePlan().getCoverage());
            dto.setDescriptionAboutPolicy(policy.getInsurancePlan().getDescriptionAboutPolicy());
        }
        return dto;
    }
}