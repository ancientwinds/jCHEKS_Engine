package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.model.contact.ContactNotFoundException;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.ContactCollection;
import com.archosResearch.jCHEKS.concept.engine.message.AbstractMessage;
import com.archosResearch.jCHEKS.concept.engine.message.IncomingMessage;
import com.archosResearch.jCHEKS.concept.engine.message.OutgoingMessage;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Model extends AbstractModel {

    private final ContactCollection contactCollection;
    private final HashMap<Contact, HashSet<AbstractMessage>> messages;

    public Model(ContactCollection contacts) {
        this.contactCollection = contacts;
        this.messages = new HashMap();
    }

    @Override
    public void addContact(Contact contact) throws NameOfContactAlreadyExistInContactsException {
        this.contactCollection.add(contact);
        this.messages.put(contact, new HashSet());
    }

    @Override
    public void addOutgoingMessage(String messageContent, String contactName) {
        try {
            Contact contact = contactCollection.findByName(contactName);
            HashSet messages = this.messages.get(contact);
            OutgoingMessage message = new OutgoingMessage(messageContent);
            messages.add(message);
            this.notifyMessageSent(message, contactName);
        } catch (ContactNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addIncomingMessage(String messageContent, Contact contact) {
        try {
            contactCollection.findByName(contact.getName());
            HashSet messages = this.messages.get(contact);
            IncomingMessage message = new IncomingMessage(messageContent);
            messages.add(message);
            this.notifyMessageReceived(message, contact.getName());
        } catch (ContactNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    ///TODO AddMessage(AbstractMessage)
    
}
