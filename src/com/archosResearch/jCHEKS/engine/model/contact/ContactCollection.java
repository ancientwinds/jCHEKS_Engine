package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.model.NameOfContactAlreadyExistInContactsException;
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
        throw new ContactNotFoundException();
    }

    public void add(Contact newContact) throws NameOfContactAlreadyExistInContactsException {
        for (Contact contact : this.contacts) {
            if (contact.getName().equals(newContact.getName())) {
                throw new NameOfContactAlreadyExistInContactsException();
            }
        }
        this.contacts.add(newContact);
    }

}
