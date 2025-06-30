package com.insurance.InsuranceApp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO to represent the aggregated claim details as requested, with validation annotations.
 * This combines information from Claim, Policy, PersonalDetails, and InsurancePlan.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimList {

    @NotNull(message = "Claim ID cannot be null")
    @Positive(message = "Claim ID must be positive")
    private Long claimId;

    @NotBlank(message = "Claim type cannot be empty")
    private String claimType; // Corresponds to plan_name from InsurancePlan

    @NotNull(message = "Policy number cannot be null")
    @Positive(message = "Policy number must be positive")
    private Long policyNumber; // Corresponds to policy_id from Policy

    @NotBlank(message = "Customer name cannot be empty")
    private String customerName; // Corresponds to first_name + last_name from PersonalDetails

    @NotNull(message = "Date of claim cannot be null")
    @PastOrPresent(message = "Date of claim cannot be in the future")
    private LocalDate dateOfClaim;

    @NotBlank(message = "Status cannot be empty")
    // Consider adding @Pattern if you have specific allowed status values (e.g., "PENDING|APPROVED|REJECTED")
    private String status; // Corresponds to claim_status from Claim

    @NotNull(message = "Claim amount cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Claim amount must be greater than 0")
    private BigDecimal claimAmount; // Corresponds to sum_insured from Claim
    
    private LocalDate dateOfIncident;
    
    private String description;
    
    private String location;
    
    private LocalTime timeOfIncident;
    
    private BigDecimal sumInsured;
    
    private BigDecimal premium;

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public BigDecimal getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(BigDecimal sumInsured) {
		this.sumInsured = sumInsured;
	}

	public LocalDate getDateOfIncident() {
		return dateOfIncident;
	}

	public void setDateOfIncident(LocalDate dateOfIncident) {
		this.dateOfIncident = dateOfIncident;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalTime getTimeOfIncident() {
		return timeOfIncident;
	}

	public void setTimeOfIncident(LocalTime timeOfIncident) {
		this.timeOfIncident = timeOfIncident;
	}

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public Long getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(Long policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDate getDateOfClaim() {
		return dateOfClaim;
	}

	public void setDateOfClaim(LocalDate dateOfClaim) {
		this.dateOfClaim = dateOfClaim;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(BigDecimal claimAmount) {
		this.claimAmount = claimAmount;
	}
}
