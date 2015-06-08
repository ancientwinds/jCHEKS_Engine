package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import java.util.HashSet;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactCollection{

    private final HashSet<Contact> contacts;

    public ContactCollection() {
        this.contacts = new HashSet();
    }

    public Contact findByName(String name) throws ContactNotFoundException {
        for (Contact contact : this.contacts) {
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        throw new ContactNotFoundException("Contact doesn't exist in this contact collection.");
    }

    public void add(Contact newContact) throws ContactAlreadyExistException {
        for (Contact contact : this.contacts) {
            if (contact.getName().equals(newContact.getName())) {
                throw new ContactAlreadyExistException("Contact already exist in this contact collection.");
            }
        }
        this.contacts.add(newContact);
    }

}
