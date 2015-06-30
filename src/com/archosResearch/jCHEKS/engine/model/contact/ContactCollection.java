package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import java.util.HashMap;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactCollection {

    private final HashMap<String, Contact> systemIds;
    private final HashMap<String, Contact> names;

    public ContactCollection() {
        this.systemIds = new HashMap();
        this.names = new HashMap();
    }

    public Contact findByUniqueId(String uniqueId) throws ContactNotFoundException {
        Contact contactFound = systemIds.get(uniqueId);
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
        String sysId = newContact.getReceivingChaoticSystem().getSystemId();
        if (this.names.get(name) != null) {
            throw new ContactAlreadyExistException("Contact with this name already exist");
        }
        if (this.systemIds.get(sysId) != null) {
            throw new ContactAlreadyExistException("Contact with this uniqueId already exist");
        }
        this.names.put(name, newContact);
        this.systemIds.put(sysId, newContact);
    }

}
