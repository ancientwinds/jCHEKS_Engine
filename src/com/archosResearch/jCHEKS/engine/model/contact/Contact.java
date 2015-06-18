package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.encrypter.AbstractEncrypter;
import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Contact {
    private ContactInfo contactInfo;
    private final AbstractCommunicator communicator;
    private final AbstractEncrypter encrypter;
    private final AbstractChaoticSystem chaoticSystem;
    private final AbstractChaoticSystem chaoticSystem2;
    
    public Contact(ContactInfo contactInfo, AbstractCommunicator communicator, AbstractEncrypter encrypter, AbstractChaoticSystem chaoticSystem, AbstractChaoticSystem chaoticSystem2) {
        this.contactInfo = contactInfo;
        this.communicator = communicator;
        this.encrypter = encrypter;
        this.chaoticSystem = chaoticSystem;
        this.chaoticSystem2 = chaoticSystem2;
    }

    public ContactInfo getContactInfo() {
        return this.contactInfo;
    }

    public AbstractCommunicator getCommunicator() {
        return this.communicator;
    }
    
    public AbstractEncrypter getEncrypter() {
        return this.encrypter;
    }
    
    public AbstractChaoticSystem getSendingChaoticSystem() {
        return this.chaoticSystem;
    }
    
    public AbstractChaoticSystem getReceivingChaoticSystem() {
        return this.chaoticSystem2;
    }
}
