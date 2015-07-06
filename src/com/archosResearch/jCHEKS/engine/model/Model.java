package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import com.archosResearch.jCHEKS.engine.model.contact.*;
import com.archosResearch.jCHEKS.concept.engine.message.*;
import java.util.HashMap;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Model extends AbstractModel {

    private final ContactCollection contactCollection;
    private final HashMap<String, OutgoingMessage> lastMessageSent;
    
    public Model() {
        this.contactCollection = new ContactCollection();
        this.lastMessageSent = new HashMap();
    }

    @Override
    public void addContact(Contact contact) throws ContactAlreadyExistException {
        this.contactCollection.add(contact);
        this.notifyContactAdded(contact.getContactInfo().getName());
    }

    @Override
    public void addOutgoingMessage(String messageContent, String contactName){
        OutgoingMessage message = new OutgoingMessage(messageContent);
        lastMessageSent.put(contactName, message);
        this.notifyMessageSent(message, contactName);
    }

    @Override
    public void addIncomingMessage(String messageContent, Contact contact){
        IncomingMessage message = new IncomingMessage(messageContent);
        this.notifyMessageReceived(message, contact.getContactInfo().getName());
    }

    @Override
    public Contact findContactBySendingSystemId(String sendingSystemId) throws ContactNotFoundException {
        return this.contactCollection.findBySendingSystemId(sendingSystemId);
    }

    
    @Override
    public OutgoingMessage getLastOutgoingMessageBySystemId(String systemId) throws ContactNotFoundException{
        Contact contact = this.findContactBySendingSystemId(systemId);
        return lastMessageSent.get(contact.getContactInfo().getName());
    }
    
    @Override
    public Contact findContactByReceiverSystemId(String systemId) throws ContactNotFoundException{
        return this.contactCollection.findByReceivingSystemId(systemId);
    }
    
    @Override
    public Contact findContactByName(String name) throws ContactNotFoundException{
        return this.contactCollection.findByName(name);
    }
}
