package com.insurance.InsuranceApp.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.insurance.InsuranceApp.model.PersonalDetails;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Long> {
	@Query("SELECT pd FROM PersonalDetails pd WHERE pd.user.id = :userId")
    PersonalDetails findByUserId(@Param("userId") Long userId);
	@Query("SELECT pd FROM PersonalDetails pd WHERE pd.user.id = :userId ORDER BY pd.personalDetailsId DESC LIMIT 1")
    PersonalDetails findTopByUserIdOrderByPersonalDetailsIdDesc(@Param("userId") Long userId);
//	Optional<PersonalDetails> findByUserId(Long userId);
}



