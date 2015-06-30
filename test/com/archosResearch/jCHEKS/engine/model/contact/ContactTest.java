package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.encrypter.AbstractEncrypter;
import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;
import com.archosResearch.jCHEKS.engine.mock.StubChaoticSystem;
import com.archosResearch.jCHEKS.engine.mock.StubEncrypter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactTest {
    private final String contactName = "Alice";
    private final ContactInfo aliceContactInfo = new ContactInfo("10.10.10.10", 9000, contactName, "path1", "path2");

    @Test
    public void constructor_should_create_the_contact() throws Exception {
        Contact contact = null;
        contact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        assertNotNull(contact);
    }
    
    @Test
    public void getName_should_return_the_name_of_the_contact() throws Exception {
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        String result = contact.getContactInfo().getName();
        assertEquals(contactName, result);
    }

    @Test
    public void getCommunicator_should_return_the_communicator_of_the_contact() throws Exception {
        AbstractCommunicator givenCommunicator = new StubCommunicator();
        Contact contact = new Contact(aliceContactInfo, givenCommunicator, new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        AbstractCommunicator receivedCommunicator = contact.getCommunicator();
        assertEquals(givenCommunicator, receivedCommunicator);
    }
    
    @Test
    public void getEncrypter_should_return_the_encrypter_of_the_contact() throws Exception {
        AbstractEncrypter givenEncrypter = new StubEncrypter();
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator(), givenEncrypter, new StubChaoticSystem(), new StubChaoticSystem());
        AbstractEncrypter receivedEncrypter = contact.getEncrypter();
        assertEquals(givenEncrypter, receivedEncrypter);
    }
    
    @Test
    public void getSendingChaoticSystem_should_return_the_chaoticSystem_of_the_contact() throws Exception {
        AbstractChaoticSystem givenChaoticSystem = new StubChaoticSystem();
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), givenChaoticSystem, new StubChaoticSystem());
        AbstractChaoticSystem receivedChaoticSystem = contact.getSendingChaoticSystem();
        assertEquals(givenChaoticSystem, receivedChaoticSystem);
    }
    
    @Test
    public void getReceivingChaoticSystem_should_return_the_chaoticSystem_of_the_contact() throws Exception {
        AbstractChaoticSystem givenChaoticSystem = new StubChaoticSystem();
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), givenChaoticSystem);
        AbstractChaoticSystem receivedChaoticSystem = contact.getReceivingChaoticSystem();
        assertEquals(givenChaoticSystem, receivedChaoticSystem);
    }
    
}
