package com.insurance.InsuranceApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

//This DTO defines the structure of data expected when updating a claim.
//It includes fields that are typically editable by an admin.
public class ClaimUpdateDto {
 private String claimStatus; // Status of the claim (e.g., PENDING, APPROVED, REJECTED)
 private String claimReason; // The reason for the claim, or claim type
 private BigDecimal claimAmount; // The amount of the claim
 private String description; // Description of the incident
 private String location;    // Location where the incident occurred
 private LocalDate dateOfIncident; // Date of the incident in YYYY-MM-DD format
 private LocalTime timeOfIncident; // Time of the incident in HH:MM or HH:MM:SS format
 


 // Note: policyId and dateOfClaim are typically not updated via this endpoint
 // as they are usually set at claim creation and referential.

 public ClaimUpdateDto() {
     // Default constructor
 }

 public ClaimUpdateDto(String claimStatus, String claimReason, BigDecimal claimAmount, String description, String location, LocalDate dateOfIncident, LocalTime timeOfIncident) {
     this.claimStatus = claimStatus;
     this.claimReason = claimReason;
     this.claimAmount = claimAmount;
     this.description = description;
     this.location = location;
     this.dateOfIncident = dateOfIncident;
     this.timeOfIncident = timeOfIncident;
 }

 // --- Getters ---
 public String getClaimStatus() {
     return claimStatus;
 }

 public String getClaimReason() {
     return claimReason;
 }

 public BigDecimal getClaimAmount() {
     return claimAmount;
 }

 public String getDescription() {
     return description;
 }

 public String getLocation() {
     return location;
 }

 public LocalDate getDateOfIncident() {
     return dateOfIncident;
 }

 public LocalTime getTimeOfIncident() {
     return timeOfIncident;
 }

 // --- Setters ---
 public void setClaimStatus(String claimStatus) {
     this.claimStatus = claimStatus;
 }

 public void setClaimReason(String claimReason) {
     this.claimReason = claimReason;
 }

 public void setClaimAmount(BigDecimal claimAmount) {
     this.claimAmount = claimAmount;
 }

 public void setDescription(String description) {
     this.description = description;
 }

 public void setLocation(String location) {
     this.location = location;
 }

 public void setDateOfIncident(LocalDate dateOfIncident) {
     this.dateOfIncident = dateOfIncident;
 }

 public void setTimeOfIncident(LocalTime timeOfIncident) {
     this.timeOfIncident = timeOfIncident;
 }
}