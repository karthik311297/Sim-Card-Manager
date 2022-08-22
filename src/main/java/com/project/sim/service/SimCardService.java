package com.project.sim.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sim.common.Constants;
import com.project.sim.common.Constants.UPDATE_OPERATION_TYPE;
import com.project.sim.exceptions.SimCardAlreadyExistsException;
import com.project.sim.exceptions.SimCardNotFoundException;
import com.project.sim.model.SimCard;
import com.project.sim.repository.SimCardRepository;

@Service
public class SimCardService
{
    @Autowired
    private SimCardRepository simCardRepository;
    
    public List<SimCard> getAllSimCards()
    {
        return simCardRepository.findAll();
    }
    
    public SimCard getSimCardBySimCardNumber(String simCardNumber) throws SimCardNotFoundException
    {
        List<SimCard> sims = simCardRepository.findBySimCardNumber(simCardNumber);
        if(sims.isEmpty())
        {
            throw new SimCardNotFoundException("SimCard with the specified simCardNumber  not found");
        }
        return sims.get(0);
    }
    
    @Transactional
    public void deleteSimCardBySimCardNumber(String simCardNumber) throws SimCardNotFoundException
    {
        long deleted = simCardRepository.deleteBySimCardNumber(simCardNumber);
        if(deleted == 0)
        {
            throw new SimCardNotFoundException("SimCard with the specified simCardNumber  not found");
        }
    }
    
    public void saveSimCard(SimCard simCard) throws SimCardAlreadyExistsException
    {
        try
        {
            simCardRepository.save(simCard);
        }
        catch(DataIntegrityViolationException e)
        {
            throw new SimCardAlreadyExistsException("A SimCard with this simCardNumber already exists");
        }
    }
    
    public UPDATE_OPERATION_TYPE updateSimCard(String simCardNumber, SimCard simCard) throws SimCardNotFoundException, SimCardAlreadyExistsException
    {
        SimCard existingSimCard = getSimCardBySimCardNumber(simCardNumber);
        if(existingSimCard.getSimCardNumber().equals(simCard.getSimCardNumber()))
        {
            simCardRepository.updateSimCard(simCard.getMobileNumber(), simCard.getStatus(), simCard.getExpiryDate(),
                    simCard.getRegistrationState(), simCard.getKyc(), simCard.getServiceProvider(), simCard.getFullName(), existingSimCard.getSimCardNumber());
            return UPDATE_OPERATION_TYPE.UPDATED_EXISTING_ROW;
        }
        else
        {
            saveSimCard(simCard);
            return UPDATE_OPERATION_TYPE.NEW_ROW_SAVED;
        }
    }
    
    public List<SimCard> getAllSimsWhichExpireWithinNext30days()
    {
        return simCardRepository.findAllSimsWhichExpireWithinNext30days(Calendar.getInstance().getTime());
    }
}
