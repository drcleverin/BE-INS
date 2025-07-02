package com.insurance.InsuranceApp.services;


import com.insurance.InsuranceApp.dto.ClaimDTO;
import com.insurance.InsuranceApp.model.Claim;
import com.insurance.InsuranceApp.model.Policy;
import com.insurance.InsuranceApp.repository.ClaimRepository;
import com.insurance.InsuranceApp.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;

    @Autowired
    public ClaimService(ClaimRepository claimRepository, PolicyRepository policyRepository) {
        this.claimRepository = claimRepository;
        this.policyRepository = policyRepository;
    }

    @Transactional
    public ClaimDTO createClaim(ClaimDTO claimDTO) {
        Policy policy = policyRepository.findById(claimDTO.getPolicyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Policy ID: " + claimDTO.getPolicyId()));

        Claim claim = new Claim();
        // Map DTO fields to Entity fields
        claim.setClaimReason(claimDTO.getClaimReason());
        claim.setClaimStatus(claimDTO.getClaimStatus());
        claim.setDateOfClaim(claimDTO.getDateOfClaim());
        claim.setDateOfIncident(claimDTO.getDateOfIncident());
        claim.setDescription(claimDTO.getDescription());
        claim.setLocation(claimDTO.getLocation());
        claim.setSumInsured(claimDTO.getSumInsured());
        claim.setTimeOfIncident(claimDTO.getTimeOfIncident());
        claim.setPolicy(policy);

        Claim savedClaim = claimRepository.save(claim);

        return mapToDTO(savedClaim);
    }

    @Transactional(readOnly = true)
    public ClaimDTO getClaimById(Long claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new IllegalArgumentException("Claim not found with ID: " + claimId));
        return mapToDTO(claim);
    }

    @Transactional(readOnly = true)
    public List<ClaimDTO> getClaimsByPolicyId(Long policyId) {
        List<Claim> claims = claimRepository.findByPolicy_PolicyId(policyId);
        return claims.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to map Claim entity to ClaimDTO
    private ClaimDTO mapToDTO(Claim claim) {
        ClaimDTO dto = new ClaimDTO();
        dto.setClaimId(claim.getClaimId());
        dto.setClaimReason(claim.getClaimReason());
        dto.setClaimStatus(claim.getClaimStatus());
        dto.setDateOfClaim(claim.getDateOfClaim());
        dto.setDateOfIncident(claim.getDateOfIncident());
        dto.setDescription(claim.getDescription());
        dto.setLocation(claim.getLocation());
        dto.setSumInsured(claim.getSumInsured());
        dto.setTimeOfIncident(claim.getTimeOfIncident());
        dto.setPolicyId(claim.getPolicy().getPolicyId());
        dto.setUserId(claim.getPolicy().getUserId());
        return dto;
    }
}
