package com.project.sim.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
