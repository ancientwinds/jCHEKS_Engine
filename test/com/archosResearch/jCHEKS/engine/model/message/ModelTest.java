package com.archosResearch.jCHEKS.engine.model.message;

import static org.junit.Assert.fail;
import org.junit.Test;



/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ModelTest {
    
    @Test
    public void voidTest() throws Exception {
        fail("Not implemented.");
    }
    /*
    @Test
    public void constructor_should_construct_the_object() {
        AbstractModel model = null;
        model = new Model(new SimpleContactCollectionToTestModel());
        assertNotNull(model);
    }

    @Test
    public void addContact_should_call_addContact_in_contactCollection() throws Exception {
        Contact contact = new Contact("", new StubCommunicator());
        SimpleContactCollectionToTestModel contactCollection = new SimpleContactCollectionToTestModel();
        AbstractModel model = new Model(contactCollection);
        model.addContact(contact);
        assertEquals(contactCollection.contacts.get(0).getName(), contact.getName());
    }

    @Test
    public void addOutgoingMessage_should_broadcast_message_sent() throws NameOfContactAlreadyExistInContactsException {
        AbstractModel model = new Model(new ContactCollectionDefault());
        ObserverMock observer = new ObserverMock();
        Contact contact = new Contact("Bob", new StubCommunicator());
        model.addContact(contact);
        model.addObserver(observer);
        model.addOutgoingMessage("Test message", contact);
        assertEquals("Test message", observer.lastMessageSent.getContent());
    }

    @Test
    public void addIncomingMessage_should_broadcast_message_received() throws NameOfContactAlreadyExistInContactsException {
        AbstractModel model = new Model(new ContactCollectionDefault());
        ObserverMock observer = new ObserverMock();
        Contact contact = new Contact("Bob", new StubCommunicator());
        model.addContact(contact);
        model.addObserver(observer);
        model.addIncomingMessage("Test message", contact);
        assertEquals("Test message", observer.lastMessageReceived.getContent());
    }

    @Test
    public void findMessagesByContact_should_return_message_sent() throws Exception {
        AbstractModel model = new Model(new ContactCollectionDefault());
        Contact contact = new Contact("Bob", new StubCommunicator());
        model.addContact(contact);
        model.addOutgoingMessage("This is a test message", contact);
        ArrayList<AbstractMessage> result = model.findMessagesByContact(contact.getName());
        assertEquals(result.get(0).getContent(), "This is a test message");
    }

    @Test
    public void addIncomingMessage_should_not_broadcast_message_received_when_observer_has_been_removed() throws NameOfContactAlreadyExistInContactsException {
        AbstractModel model = new Model(new ContactCollectionDefault());
        ObserverMock observer = new ObserverMock();
        Contact contact = new Contact("Bob", new StubCommunicator());
        model.addContact(contact);
        model.addObserver(observer);
        model.removeObserver(observer);
        model.addIncomingMessage("Test message", contact);
        assertNull(observer.lastMessageReceived);
    }
*/
}
