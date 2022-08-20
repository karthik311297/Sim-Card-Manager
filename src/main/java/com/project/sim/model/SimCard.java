package com.project.sim.model;

import static com.project.sim.common.Constants.KYC;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.project.sim.common.Constants.RegistrationStatus;
import com.project.sim.common.Constants.ServiceProvider;
import com.project.sim.common.Constants.SimStatus;

@Entity
public class SimCard
{
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String simCardNumber;
    
    private String mobileNumber;
    
    private SimStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;
    
    private RegistrationStatus registrationState;
    
    private KYC kyc;
    
    private ServiceProvider serviceProvider;
    
    private String fullName;
    
    public SimCard()
    {
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    public String getSimCardNumber()
    {
        return simCardNumber;
    }
    
    public void setSimCardNumber(String simCardNumber)
    {
        this.simCardNumber = simCardNumber;
    }
    
    public String getMobileNumber()
    {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }
    
    public SimStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(SimStatus status)
    {
        this.status = status;
    }
    
    public Date getExpiryDate()
    {
        return expiryDate;
    }
    
    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    
    public RegistrationStatus getRegistrationState()
    {
        return registrationState;
    }
    
    public void setRegistrationState(RegistrationStatus registrationState)
    {
        this.registrationState = registrationState;
    }
    
    public KYC getKyc()
    {
        return kyc;
    }
    
    public void setKyc(KYC kyc)
    {
        this.kyc = kyc;
    }
    
    public ServiceProvider getServiceProvider()
    {
        return serviceProvider;
    }
    
    public void setServiceProvider(ServiceProvider serviceProvider)
    {
        this.serviceProvider = serviceProvider;
    }
    
    public String getFullName()
    {
        return fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
}
