package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import java.util.HashMap;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactCollection {

    private final HashMap<String, Contact> sendingSystemIds;
    private final HashMap<String, Contact> receivingSystemIds;
    private final HashMap<String, Contact> names;

    public ContactCollection() {
        this.sendingSystemIds = new HashMap();
        this.receivingSystemIds = new HashMap();
        this.names = new HashMap();
    }

    public Contact findBySendingSystemId(String id) throws ContactNotFoundException {
        Contact contactFound = sendingSystemIds.get(id);
        if (contactFound != null) {
            return contactFound;
        }
        throw new ContactNotFoundException("Contact with this receiver system id doesn't exist in this contact collection.");
    }
    
    public Contact findByReceivingSystemId(String id) throws ContactNotFoundException {
        Contact contactFound = receivingSystemIds.get(id);
        if (contactFound != null) {
            return contactFound;
        }
        throw new ContactNotFoundException("Contact with this receiver system id doesn't exist in this contact collection.");
    }

    public Contact findByName(String name) throws ContactNotFoundException {
        Contact contactFound = names.get(name);
        if (contactFound != null) {
            return contactFound;
        }
        throw new ContactNotFoundException("Contact with this name doesn't exist in this contact collection.");
    }

    public void add(Contact newContact) throws ContactAlreadyExistException {
        String name = newContact.getContactInfo().getName();
        String receivingSystemId = newContact.getReceivingChaoticSystem().getSystemId();
        String sendingSystemId = newContact.getSendingChaoticSystem().getSystemId();
        if (this.names.get(name) != null) {
            throw new ContactAlreadyExistException("Contact with this name already exist");
        }
        if (this.sendingSystemIds.get(sendingSystemId) != null) {
            throw new ContactAlreadyExistException("Contact with this uniqueId already exist");
        }
        if (this.receivingSystemIds.get(receivingSystemId) != null) {
            throw new ContactAlreadyExistException("Contact with this uniqueId already exist");
        }
        this.names.put(name, newContact);
        this.sendingSystemIds.put(sendingSystemId, newContact);
        this.receivingSystemIds.put(receivingSystemId, newContact);
    }

}
