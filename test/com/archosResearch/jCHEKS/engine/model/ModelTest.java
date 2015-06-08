package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.mock.ObserverMock;
import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.engine.model.exception.AddIncomingMessageException;
import com.archosResearch.jCHEKS.engine.model.exception.AddOutgoingMessageException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ModelTest {
    
    @Test
    public void constructor_should_create_the_model(){
        
        AbstractModel model = null;
        model = new Model();
        assertNotNull(model);
    }

    @Test
    public void addContact_should_notify_observer_that_a_contact_has_been_added() throws ContactAlreadyExistException{
        Contact contact = new Contact("Alice", new StubCommunicator());
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addObserver(observer);
        model.addContact(contact);
        assertEquals(contact.getName(), observer.lastContactAdded);
    }

    @Test (expected = ContactAlreadyExistException.class)
    public void addContact_should_throw_an_exception_if_contact_already_exist() throws ContactAlreadyExistException{
        Contact contact = new Contact("Alice", new StubCommunicator());
        Model model = new Model();
        model.addContact(contact);
        model.addContact(contact);
    }
    
    @Test
    public void addOutgoingMessage_should_notify_observer_that_a_message_has_been_sent() throws AddOutgoingMessageException, ContactAlreadyExistException {
        String messageContent = "Hello";
        String contactName = "Alice";
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addContact(new Contact(contactName, new StubCommunicator()));
        model.addObserver(observer);
        model.addOutgoingMessage(messageContent, contactName);
        assertEquals(messageContent, observer.lastMessageSent.getContent());
    }

    @Test (expected = AddOutgoingMessageException.class)
    public void addOutgoingMessage_should_throw_AddOutgoingMessageException_if_contact_related_to_message_not_exist() throws AddOutgoingMessageException, ContactAlreadyExistException {
        Model model = new Model();
        model.addOutgoingMessage("Hello", "Alice");
    }
    
    @Test
    public void addIncomingMessage_should_notify_observer_that_a_message_has_been_received() throws ContactAlreadyExistException, AddIncomingMessageException {
        String messageContent = "Hello";
        String contactName = "Alice";
        Contact contact = new Contact(contactName, new StubCommunicator());
        Model model = new Model();
        ObserverMock observer = new ObserverMock();
        model.addContact(contact);
        model.addObserver(observer);
        model.addIncomingMessage(messageContent, contact);
        assertEquals(messageContent, observer.lastMessageSent.getContent());
    }

    @Test (expected = AddOutgoingMessageException.class)
    public void addIncomingMessage_should_throw_AddIncomingMessageException_if_contact_related_to_message_not_exist() throws ContactAlreadyExistException, AddIncomingMessageException {
        Model model = new Model();
        model.addIncomingMessage("Hello", new Contact("Alice", new StubCommunicator()));
    }
    
}
