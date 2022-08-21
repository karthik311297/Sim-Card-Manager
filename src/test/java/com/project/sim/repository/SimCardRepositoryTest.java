package com.project.sim.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.sim.common.Constants.KYC;
import com.project.sim.common.Constants.RegistrationStatus;
import com.project.sim.common.Constants.ServiceProvider;
import com.project.sim.common.Constants.SimStatus;
import com.project.sim.exceptions.SimCardNotFoundException;
import com.project.sim.model.SimCard;
import com.project.sim.service.SimCardService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SimCardRepositoryTest
{
    @Autowired
    SimCardRepository simCardRepository;
    
    @Autowired
    SimCardService simCardService;
    
    @Test
    public void shouldSaveSimCardInDBAnBeAbleToFetchIt()
    {
        Date date = new Date();
        date.setTime(16754883939L);
        SimCard simCard = getSimCardForTest("abc4-1268-1167", "909099695", SimStatus.ACTIVE,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik Iyer");
        
        simCardRepository.save(simCard);
        
        List<SimCard> simCardInDB = simCardRepository.findBySimCardNumber("abc4-1268-1167");
        Assert.assertEquals(1, simCardInDB.size());
    }
    
    @Test
    public void shouldUpdateExistingSimCardInDB() throws SimCardNotFoundException
    {
        Date date = new Date();
        date.setTime(16754883939L);
        SimCard simCard = getSimCardForTest("abc4-1268-1167", "909099695", SimStatus.ACTIVE,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik Iyer");
        simCardRepository.save(simCard);
        SimCard updatedSim = getSimCardForTest("abc4-1268-1167", "909099697", SimStatus.DEACTIVATED,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik");
        
        simCardService.updateSimCard(simCard.getSimCardNumber(), updatedSim);
        
        List<SimCard> simCardsInDB = simCardRepository.findAll();
        Assert.assertEquals(1, simCardsInDB.size());
        SimCard theSimCard = simCardsInDB.get(0);
        Assert.assertEquals(simCard.getSimCardNumber(), theSimCard.getSimCardNumber());
        Assert.assertEquals(updatedSim.getMobileNumber(), theSimCard.getMobileNumber());
        Assert.assertEquals(updatedSim.getStatus(), theSimCard.getStatus());
        Assert.assertEquals(updatedSim.getFullName(), theSimCard.getFullName());
    }
    
    @Test
    public void shouldCreateNewSimCardWhenUpdateMethodIsCalledWithNewSimNumber() throws SimCardNotFoundException
    {
        Date date = new Date();
        date.setTime(16754883939L);
        SimCard simCard = getSimCardForTest("abc4-1268-1167", "909099695", SimStatus.ACTIVE,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik Iyer");
        simCardRepository.save(simCard);
        SimCard updatedSim = getSimCardForTest("abd4-2269-4387", "909099697", SimStatus.DEACTIVATED,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik");
        
        simCardService.updateSimCard(simCard.getSimCardNumber(), updatedSim);
        
        List<SimCard> simCardsInDB = simCardRepository.findAll();
        Assert.assertEquals(2, simCardsInDB.size());
    }
    
    @Test
    public void shouldDeleteSimCardBySimCardNumber()
    {
        Date date = new Date();
        date.setTime(16754883939L);
        SimCard simCard = getSimCardForTest("abc4-1268-1167", "909099695", SimStatus.ACTIVE,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik Iyer");
        
        simCardRepository.deleteBySimCardNumber("abc4-1268-1167");
        
        
        List<SimCard> simCardsInDB = simCardRepository.findAll();
        Assert.assertEquals(0, simCardsInDB.size());
    }
    
    
    @Test(expected = DataIntegrityViolationException.class)
    public void shouldThrowExceptionWhenDuplicateSimCardNumberIsTriedToBeSaved()
    {
        Date date = new Date();
        date.setTime(16754883939L);
        SimCard simCard = getSimCardForTest("abc4-1268-1167", "909099695", SimStatus.ACTIVE,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik Iyer");
        SimCard simCard2 = getSimCardForTest("abc4-1268-1167", "909099697", SimStatus.DEACTIVATED,
                date, RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.AIRTEL, "Vaishak Iyer");
        
        simCardRepository.save(simCard);
        simCardRepository.save(simCard2);
    }
    
    @Test
    public void shouldFetchTheSimCardsWhichAreAboutToExpireWithinNext30Days()
    {
        Date currentDate = new Date();
        currentDate.setTime(1661066512855L);
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, 40);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currentDate);
        cal2.add(Calendar.DATE, 22);
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(currentDate);
        cal3.add(Calendar.DATE, 1);
        Calendar cal4 = Calendar.getInstance();
        cal4.setTime(currentDate);
        SimCard simCard = getSimCardForTest("abc4-1268-1167", "909099695", SimStatus.ACTIVE,
                cal.getTime(), RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.IDEA, "Karthik Iyer");
        SimCard simCard2 = getSimCardForTest("abc4-4566-hyb6", "909099543", SimStatus.DEACTIVATED,
                cal2.getTime(), RegistrationStatus.REGISTERED, KYC.DONE, ServiceProvider.AIRTEL, "Gabriel");
        SimCard simCard3 = getSimCardForTest("vdcf-4566-7654", "909099875", SimStatus.DEACTIVATED,
                cal3.getTime(), RegistrationStatus.REGISTERED, KYC.PENDING, ServiceProvider.JIO, "Messi");
        SimCard simCard4 = getSimCardForTest("7654-4566-hyb6", "969079523", SimStatus.ACTIVE,
                cal4.getTime(), RegistrationStatus.REGISTRATION_PENDING, KYC.PENDING, ServiceProvider.BSNL, "Ronaldo");
        simCardRepository.save(simCard);
        simCardRepository.save(simCard2);
        simCardRepository.save(simCard3);
        simCardRepository.save(simCard4);
        
        List<SimCard> simCardsWhichExpireWithinNext30Days = simCardRepository.findAllSimsWhichExpireWithinNext30days(currentDate);
        
        Assert.assertEquals(3, simCardsWhichExpireWithinNext30Days.size());
    }
    
    @After
    public void cleanup()
    {
        simCardRepository.deleteAll();
    }
    
    private SimCard getSimCardForTest(String simCardNumber, String mobileNumber, SimStatus status, Date expiryDate,
                                      RegistrationStatus registrationState, KYC kyc, ServiceProvider serviceProvider, String fullName)
    {
        SimCard simCard = new SimCard();
        simCard.setSimCardNumber(simCardNumber);
        simCard.setMobileNumber(mobileNumber);
        simCard.setStatus(status);
        simCard.setExpiryDate(expiryDate);
        simCard.setRegistrationState(registrationState);
        simCard.setKyc(kyc);
        simCard.setServiceProvider(serviceProvider);
        simCard.setFullName(fullName);
        return simCard;
    }
}