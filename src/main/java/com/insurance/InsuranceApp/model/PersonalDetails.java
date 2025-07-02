
package com.insurance.InsuranceApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder; // Don't forget to import Builder

import java.time.LocalDate;

@Entity
@Table(name = "personal_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Add @Builder annotation
public class PersonalDetails {

    public Long getPersonalDetailsId() {
		return personalDetailsId;
	}

	public void setPersonalDetailsId(Long personalDetailsId) {
		this.personalDetailsId = personalDetailsId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalDetailsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",unique=true)
	@JsonIgnore
    private User user;

    @Column(name = "first_name", nullable = false, length = 100)
    @Builder.Default // Default value for firstName
    private String firstName = "DefaultFirstName"; // Example default

    @Column(name = "last_name", nullable = false, length = 100)
    @Builder.Default // Default value for lastName
    private String lastName = "DefaultLastName"; // Example default

    @Column(length = 100)
    @Builder.Default // Default value for email
    private String email = "default@example.com"; // Example default

    @Column(name = "phone_number", length = 20)
    @Builder.Default // Default value for phoneNumber
    private String phoneNumber = "N/A"; // Example default

    @Column(name = "date_of_birth")
    @Builder.Default // Default value for dateOfBirth
    private LocalDate dateOfBirth = LocalDate.of(1900, 1, 1); // Example default

    @Column(length = 10)
    @Builder.Default // Default value for gender
    private String gender = "Unknown"; // Example default

    @Column(length = 255)
    @Builder.Default // Default value for address
    private String address = "Not Provided"; // Example default

    @Column(length = 100)
    @Builder.Default // Default value for city
    private String city = "Unknown City"; // Example default

    @Column(name = "pin_code", length = 10)
    @Builder.Default // Default value for pinCode
    private String pinCode = "000000"; // Example default
}