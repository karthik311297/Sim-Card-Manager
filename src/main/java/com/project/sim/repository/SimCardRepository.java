package com.project.sim.repository;

import static com.project.sim.common.Constants.KYC;
import static com.project.sim.common.Constants.RegistrationStatus;
import static com.project.sim.common.Constants.ServiceProvider;
import static com.project.sim.common.Constants.SimStatus;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.sim.model.SimCard;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard,Long>
{
    List<SimCard> findBySimCardNumber(String simCardNumber);
    
    Long deleteBySimCardNumber(String simCardNumber);
    
    @Query("select a from SimCard a where TIMESTAMPDIFF(DAY, :currentTime ,a.expiryDate) <= 30")
    List<SimCard> findAllSimsWhichExpireWithinNext30days(
            @Param("currentTime") Date currentTime);
    
    @Modifying
    @Query("UPDATE SimCard a SET a.mobileNumber = :mobileNumber, a.status = :status, a.expiryDate = :expiryDate," +
            " a.registrationState = :registrationState, a.kyc = :kyc, a.serviceProvider = :serviceProvider, a.fullName = :fullName WHERE a.simCardNumber = :existingSimCardNumber")
    int updateSimCard(@Param("mobileNumber") String mobileNumber, @Param("status") SimStatus status, @Param("expiryDate") Date expiryDate,
                      @Param("registrationState") RegistrationStatus registrationState, @Param("kyc") KYC kyc, @Param("serviceProvider") ServiceProvider serviceProvider,
                      @Param("fullName") String fullName, @Param("existingSimCardNumber") String existingSimCardNumber);
}
