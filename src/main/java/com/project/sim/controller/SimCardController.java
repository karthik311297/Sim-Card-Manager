package com.project.sim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.sim.common.Constants;
import com.project.sim.exceptions.SimCardAlreadyExistsException;
import com.project.sim.exceptions.SimCardNotFoundException;
import com.project.sim.model.SimCard;
import com.project.sim.service.SimCardService;

@RestController
public class SimCardController
{
    @Autowired
    private SimCardService simCardService;
    
    @GetMapping("/")
    public ResponseEntity<String> welcome()
    {
        return new ResponseEntity<>("Welcome", HttpStatus.OK);
    }
    
    @GetMapping("/listall")
    public List<SimCard> getAllSimCards()
    {
        return simCardService.getAllSimCards();
    }
    
    @GetMapping("/to-renew")
    public List<SimCard> getAllSimsWhichExpireWithinNext30days()
    {
        return simCardService.getAllSimsWhichExpireWithinNext30days();
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> saveSimCard(@RequestBody SimCard simCard)
    {
        try
        {
            simCardService.saveSimCard(simCard);
        }
        catch(SimCardAlreadyExistsException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Sim Card successfully saved", HttpStatus.OK);
    }
    
    @PostMapping("/{simCardNumber}")
    public ResponseEntity<String> updateSimCard(@PathVariable String simCardNumber, @RequestBody SimCard simCard)
    {
        Constants.UPDATE_OPERATION_TYPE operationDone;
        try
        {
            operationDone = simCardService.updateSimCard(simCardNumber, simCard);
        }
        catch(SimCardAlreadyExistsException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        catch(SimCardNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(operationDone == Constants.UPDATE_OPERATION_TYPE.UPDATED_EXISTING_ROW
                ? "Sim Card successfully Updated" : "Sim Card successfully saved", HttpStatus.OK);
    }
    
    @DeleteMapping("/{simCardNumber}")
    public ResponseEntity<String> deleteSimCard(@PathVariable String simCardNumber)
    {
        try
        {
            simCardService.deleteSimCardBySimCardNumber(simCardNumber);
        }
        catch(SimCardNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Sim Card successfully deleted", HttpStatus.OK);
    }
}
