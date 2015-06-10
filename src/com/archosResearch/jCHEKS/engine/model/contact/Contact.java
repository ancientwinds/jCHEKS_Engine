package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Contact {
    private ContactInfo contactInfo;
    private final AbstractCommunicator communicator;
    //private final AbstractEncrypter encrypter;

    public Contact(ContactInfo contactInfo, AbstractCommunicator communicator/*, AbstractEncrypter encrypter*/) {
        this.contactInfo = contactInfo;
        this.communicator = communicator;
        //this.encrypter = encrypter;
    }

    public ContactInfo getContactInfo() {
        return this.contactInfo;
    }

    public AbstractCommunicator getCommunicator() {
        return this.communicator;
    }
}
