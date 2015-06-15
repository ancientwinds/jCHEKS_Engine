package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactTest {
    private final String contactName = "Alice";
    private final ContactInfo aliceContactInfo = new ContactInfo("10.10.10.10", 9000, contactName, "sysId");

    @Test
    public void constructor_should_create_the_contact() {
        Contact contact = null;
        contact = new Contact(aliceContactInfo, new StubCommunicator());
        assertNotNull(contact);
    }
    
    @Test
    public void getName_should_return_the_name_of_the_contact() {
        Contact contact = new Contact(aliceContactInfo, new StubCommunicator());
        String result = contact.getContactInfo().getName();
        assertEquals(contactName, result);
    }

    @Test
    public void getCommunicator_should_return_the_communicator_of_the_contact() {
        AbstractCommunicator givenCommunicator = new StubCommunicator();
        Contact contact = new Contact(aliceContactInfo, givenCommunicator);
        AbstractCommunicator receivedCommunicator = contact.getCommunicator();
        assertEquals(givenCommunicator, receivedCommunicator);
    }
    
}
