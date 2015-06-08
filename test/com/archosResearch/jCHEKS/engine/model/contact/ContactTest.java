package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactTest {
    
    @Test
    public void constructor_should_create_the_contact() {
        Contact contact = null;
        contact = new Contact("", new StubCommunicator());
        assertNotNull(contact);
    }
    
    @Test
    public void getName_should_return_the_name_of_the_contact() {
        String expName = "Alice";
        Contact contact = new Contact(expName, new StubCommunicator());
        String result = contact.getName();
        assertEquals(expName, result);
    }

    @Test
    public void getCommunicator_should_return_the_communicator_of_the_contact() {
        AbstractCommunicator givenCommunicator = new StubCommunicator();
        Contact contact = new Contact("Alice", givenCommunicator);
        AbstractCommunicator receivedCommunicator = contact.getCommunicator();
        assertEquals(givenCommunicator, receivedCommunicator);
    }
    
}
