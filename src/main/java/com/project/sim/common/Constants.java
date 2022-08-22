package com.project.sim.common;

public class Constants
{
    public enum SimStatus
    {
        ACTIVE,
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
    
    public enum UPDATE_OPERATION_TYPE
    {
        NEW_ROW_SAVED,
        UPDATED_EXISTING_ROW
    }
}
