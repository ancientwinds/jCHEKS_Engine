package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactCollection{
    
    private final HashMap<String, Contact> uniqueIds;
    private final HashMap<String, Contact> names;
    
    public ContactCollection() {
        this.uniqueIds = new HashMap();
        this.names = new HashMap();
    }

    public Contact findByUniqueId(String uniqueId) throws ContactNotFoundException{
        Contact contactFound = uniqueIds.get(uniqueId);
        if(contactFound != null) return contactFound;
        throw new ContactNotFoundException("Contact with this receiver system id doesn't exist in this contact collection.");
    }
    
    public Contact findByName(String name) throws ContactNotFoundException {
        Contact contactFound = names.get(name);
        if(contactFound != null)return contactFound;
        
        throw new ContactNotFoundException("Contact with this name doesn't exist in this contact collection.");
    }

    public void add(Contact newContact) throws ContactAlreadyExistException {
        this.names.get(newContact.getContactInfo().getName());
        this.names.put(newContact.getContactInfo().getName(), newContact);
        this.uniqueIds.put(newContact.getContactInfo().getUniqueId(), newContact);
    }


}
