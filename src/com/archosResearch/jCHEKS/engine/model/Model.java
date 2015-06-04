package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.model.exception.AddIncomingMessageException;
import com.archosResearch.jCHEKS.engine.model.exception.AddMessageException;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.ContactCollection;
import com.archosResearch.jCHEKS.concept.engine.message.AbstractMessage;
import com.archosResearch.jCHEKS.concept.engine.message.IncomingMessage;
import com.archosResearch.jCHEKS.concept.engine.message.OutgoingMessage;
import com.archosResearch.jCHEKS.engine.model.exception.AddOutgoingMessageException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Model extends AbstractModel {

    private final ContactCollection contactCollection;
    private final HashMap<Contact, HashSet<AbstractMessage>> messages;

    public Model() {
        this.contactCollection = new ContactCollection();
        this.messages = new HashMap();
    }

    @Override
    public void addContact(Contact contact) throws ContactAlreadyExistException {
        this.contactCollection.add(contact);
        this.messages.put(contact, new HashSet());
    }

    @Override
    public void addOutgoingMessage(String messageContent, String contactName) throws AddOutgoingMessageException {
        OutgoingMessage message= new OutgoingMessage(messageContent);
        try {
            addMessageToContact(message, contactName);
        } catch (AddMessageException ex) {
            throw new AddOutgoingMessageException("Unable to add outgoing message.", ex);
        }
        this.notifyMessageSent(message, contactName);
    }

    @Override
    public void addIncomingMessage(String messageContent, Contact contact) throws AddIncomingMessageException {
        IncomingMessage message = new IncomingMessage(messageContent);
        try {
            addMessageToContact(message, contact.getName());
        } catch (AddMessageException ex) {
            throw new AddIncomingMessageException("Unable to add incoming message.", ex);
        }
        this.notifyMessageReceived(message, contact.getName());
    }
    
    private void addMessageToContact(AbstractMessage message, String contactName) throws AddMessageException{
        try {
            addMessage(message, contactCollection.findByName(contactName));
        } catch (ContactNotFoundException ex){
            throw new AddMessageException("Unable to add message.", ex);
        }
    }
     private void addMessage(AbstractMessage message, Contact contact){
            HashSet contactMessages = this.messages.get(contact);
            contactMessages.add(message);
    }
    
}
