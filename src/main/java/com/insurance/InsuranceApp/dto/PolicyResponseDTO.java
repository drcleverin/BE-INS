package com.insurance.InsuranceApp.dto;

// src/main/java/com/example/policyapp/dto/PolicyResponseDTO.java

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for sending comprehensive policy details to the frontend.
 * Combines fields from Policy entity and its associated InsurancePlan.
 */
public class PolicyResponseDTO {

    private Long policyId;
//    private String policyNumber; // Assuming Policy entity has policyNumber now
    private LocalDate policyEndDate;
    private LocalDate policyStartDate;
    private String policyStatus;
    private BigDecimal premiumAmount; // From Policy entity
    private Long userId;
    private Long vehicleId; // Can be null for health/product policies

    // Fields from associated InsurancePlan
    private Long planId; // The ID of the associated plan
    private String planName;
    private String planType; // Enum value as String (e.g., "Motor", "Health")
    private BigDecimal basePremium; // From InsurancePlan (might be different from policy premiumAmount)
    private BigDecimal coverage; // Sum Insured from InsurancePlan

    private String descriptionAboutPolicy;
    private String registrationNumber;

    public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}


    // Constructors
    public PolicyResponseDTO() {
    }

    public PolicyResponseDTO(Long policyId, LocalDate policyEndDate, LocalDate policyStartDate,
                             String policyStatus, BigDecimal premiumAmount, Long userId, Long vehicleId,
                             Long planId, String planName, String planType, BigDecimal basePremium,
                             BigDecimal coverage, String descriptionAboutPolicy) {
        this.policyId = policyId;
//        this.policyNumber = policyNumber;
        this.policyEndDate = policyEndDate;
        this.policyStartDate = policyStartDate;
        this.policyStatus = policyStatus;
        this.premiumAmount = premiumAmount;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.planId = planId;
        this.planName = planName;
        this.planType = planType;
        this.basePremium = basePremium;
        this.coverage = coverage;
        this.descriptionAboutPolicy = descriptionAboutPolicy;
    }

    // Getters and Setters
    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

//    public String getPolicyNumber() {
//        return policyNumber;
//    }
//
//    public void setPolicyNumber(String policyNumber) {
//        this.policyNumber = policyNumber;
//    }

    public LocalDate getPolicyEndDate() {
        return policyEndDate;
    }

    public void setPolicyEndDate(LocalDate policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public LocalDate getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(LocalDate policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public BigDecimal getBasePremium() {
        return basePremium;
    }

    public void setBasePremium(BigDecimal basePremium) {
        this.basePremium = basePremium;
    }

    public BigDecimal getCoverage() {
        return coverage;
    }

    public void setCoverage(BigDecimal coverage) {
        this.coverage = coverage;
    }

    public String getDescriptionAboutPolicy() {
        return descriptionAboutPolicy;
    }

    public void setDescriptionAboutPolicy(String descriptionAboutPolicy) {
        this.descriptionAboutPolicy = descriptionAboutPolicy;
    }

    @Override
    public String toString() {
        return "PolicyResponseDTO{" +
                "policyId=" + policyId +
//                ", policyNumber='" + policyNumber + '\'' +
                ", policyEndDate=" + policyEndDate +
                ", policyStartDate=" + policyStartDate +
                ", policyStatus='" + policyStatus + '\'' +
                ", premiumAmount=" + premiumAmount +
                ", userId=" + userId +
                ", vehicleId=" + vehicleId +
                ", planId=" + planId +
                ", planName='" + planName + '\'' +
                ", planType='" + planType + '\'' +
                ", basePremium=" + basePremium +
                ", coverage=" + coverage +
                ", descriptionAboutPolicy='" + descriptionAboutPolicy + '\'' +
                '}';
    }
}
