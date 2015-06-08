package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactCollection{

    private final ArrayList<Contact> contacts;

    public ContactCollection() {
        this.contacts = new ArrayList();
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
