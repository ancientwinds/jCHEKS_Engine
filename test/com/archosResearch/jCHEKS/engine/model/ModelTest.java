package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ModelTest {
    
    @Test
    public void constructor_should_create_the_model() throws Exception {
        
        AbstractModel model = null;
        model = new Model();
        assertNotNull(model);
    }
    /*
    @Test
    public void testAddContact() throws Exception {
        
        Contact contact = null;
        Model instance = null;
        instance.addContact(contact);
    }

    @Test
    public void testAddOutgoingMessage() {
        String messageContent = "";
        String contactName = "";
        Model instance = null;
        instance.addOutgoingMessage(messageContent, contactName);
    }

    @Test
    public void testAddIncomingMessage() {
        String messageContent = "";
        Contact contact = null;
        Model instance = null;
        instance.addIncomingMessage(messageContent, contact);
    }*/
    
}
