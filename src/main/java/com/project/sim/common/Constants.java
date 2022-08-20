package com.project.sim.common;

public class Constants
{
    public enum SimStatus
    {
        ACTIVATE,
        DEACTIVATED
    }
    
    public enum RegistrationStatus
    {
        REGISTRATION_PENDING,
        REGISTERED
    }
    
    public enum ServiceProvider
    {
        AIRTEL,
        JIO,
        BSNL,
        IDEA
    }
    
    public enum KYC
    {
        PENDING,
        DONE
    }
    
    public enum Error
    {
        SIM_CARD_ALREADY_EXISTS,
        SIM_CARD_NOT_FOUND
    }
}
