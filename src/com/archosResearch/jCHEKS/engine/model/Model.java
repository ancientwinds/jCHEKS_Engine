package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.model.exception.*;
import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import com.archosResearch.jCHEKS.engine.model.contact.*;
import com.archosResearch.jCHEKS.concept.engine.message.*;
import com.archosResearch.jCHEKS.engine.model.exception.AddOutgoingMessageException;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Model extends AbstractModel {

    private final ContactCollection contactCollection;
    private final HashMap<Contact, HashSet<AbstractMessage>> messages;
    //TODO Do we need to keep the messages^
    public Model() {
        this.contactCollection = new ContactCollection();
        this.messages = new HashMap();
    }

    @Override
    public void addContact(Contact contact) throws ContactAlreadyExistException {
        this.contactCollection.add(contact);
        this.messages.put(contact, new HashSet());
        this.notifyContactAdded(contact.getContactInfo().getName());
    }

    @Override
    public void addOutgoingMessage(String messageContent, String contactName) throws AddOutgoingMessageException {
        OutgoingMessage message = new OutgoingMessage(messageContent);
        try {
            addMessageToContact(message, contactName);
            this.notifyMessageSent(message, contactName);
        } catch (AddMessageException ex) {
            throw new AddOutgoingMessageException("Unable to add outgoing message.", ex);
        }
            
    }

    @Override
    public void addIncomingMessage(String messageContent, Contact contact) throws AddIncomingMessageException {
        IncomingMessage message = new IncomingMessage(messageContent);
        try {
            addMessageToContact(message, contact.getContactInfo().getName());
            this.notifyMessageReceived(message, contact.getContactInfo().getName());
        } catch (AddMessageException ex) {
            throw new AddIncomingMessageException("Unable to add incoming message.", ex);
        }
        
    }

    private void addMessageToContact(AbstractMessage message, String contactName) throws AddMessageException {
        try {
            addMessage(message, contactCollection.findByName(contactName));
        } catch (ContactNotFoundException ex) {
            throw new AddMessageException("Unable to add message.", ex);
        }
    }

    private void addMessage(AbstractMessage message, Contact contact) {
        HashSet contactMessages = this.messages.get(contact);
        contactMessages.add(message);
    }
    
    @Override
    public Contact findContactByReceiverSystemId(String systemId) throws ContactNotFoundException{
        return this.contactCollection.findByUniqueId(systemId);
    }
    
    @Override
    public Contact findContactByName(String name) throws ContactNotFoundException{
        return this.contactCollection.findByName(name);
    }
}
