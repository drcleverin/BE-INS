package com.insurance.InsuranceApp.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime; // For time_of_incident

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDTO {

    private Long claimId; // Will be null for creation, populated for response

    @NotBlank(message = "Claim reason is required")
    private String claimReason;

    @NotBlank(message = "Claim status is required")
    // Consider adding @Pattern if you want to validate against specific enum values ('approved','pending','rejected')
    // @Pattern(regexp = "approved|pending|rejected", message = "Claim status must be 'approved', 'pending', or 'rejected'")
    private String claimStatus;

    @NotNull(message = "Date of claim is required")
    @FutureOrPresent(message = "Date of claim must be in the present or future")
    private LocalDate dateOfClaim;

    @NotNull(message = "Date of incident is required")
    @PastOrPresent(message = "Date of incident cannot be in the future") // Incident date is typically in the past or present
    private LocalDate dateOfIncident;

    private String description; // Optional

    @NotBlank(message = "Location is required")
    private String location;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public @NotBlank(message = "Claim reason is required") String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(@NotBlank(message = "Claim reason is required") String claimReason) {
        this.claimReason = claimReason;
    }

    public @NotBlank(message = "Claim status is required") String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(@NotBlank(message = "Claim status is required") String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public @NotNull(message = "Date of claim is required") @FutureOrPresent(message = "Date of claim must be in the present or future") LocalDate getDateOfClaim() {
        return dateOfClaim;
    }

    public void setDateOfClaim(@NotNull(message = "Date of claim is required") @FutureOrPresent(message = "Date of claim must be in the present or future") LocalDate dateOfClaim) {
        this.dateOfClaim = dateOfClaim;
    }

    public @NotNull(message = "Date of incident is required") @PastOrPresent(message = "Date of incident cannot be in the future") LocalDate getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(@NotNull(message = "Date of incident is required") @PastOrPresent(message = "Date of incident cannot be in the future") LocalDate dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotBlank(message = "Location is required") String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank(message = "Location is required") String location) {
        this.location = location;
    }

    public @NotNull(message = "Sum insured is required") @DecimalMin(value = "0.01", message = "Sum insured must be greater than zero") BigDecimal getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(@NotNull(message = "Sum insured is required") @DecimalMin(value = "0.01", message = "Sum insured must be greater than zero") BigDecimal sumInsured) {
        this.sumInsured = sumInsured;
    }

    public @NotNull(message = "Time of incident is required") LocalTime getTimeOfIncident() {
        return timeOfIncident;
    }

    public void setTimeOfIncident(@NotNull(message = "Time of incident is required") LocalTime timeOfIncident) {
        this.timeOfIncident = timeOfIncident;
    }

    public @NotNull(message = "Policy ID is required for a claim") Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(@NotNull(message = "Policy ID is required for a claim") Long policyId) {
        this.policyId = policyId;
    }

    @NotNull(message = "Sum insured is required")
    @DecimalMin(value = "0.01", message = "Sum insured must be greater than zero")
    private BigDecimal sumInsured;

    @NotNull(message = "Time of incident is required")
    private LocalTime timeOfIncident;

    @NotNull(message = "Policy ID is required for a claim")
    private Long policyId; // This is how you link it to a policy from the frontend
    
    @NotNull(message = "Policy ID is required for a claim")
    private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
}
