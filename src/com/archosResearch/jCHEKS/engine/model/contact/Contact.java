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
    private final ContactInfo contactInfo;
    private final AbstractCommunicator communicator;
    private final AbstractEncrypter encrypter;
    private final AbstractChaoticSystem sendingChaoticSystem;
    private final AbstractChaoticSystem receivingChaoticSystem;
    
    public Contact(ContactInfo contactInfo, AbstractCommunicator communicator, AbstractEncrypter encrypter, AbstractChaoticSystem sendingChaoticSystem, AbstractChaoticSystem receivingChaoticSystem) {
        this.contactInfo = contactInfo;
        this.communicator = communicator;
        this.encrypter = encrypter;
        this.sendingChaoticSystem = sendingChaoticSystem;
        this.receivingChaoticSystem = receivingChaoticSystem;
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
        return this.sendingChaoticSystem;
    }
    
    public AbstractChaoticSystem getReceivingChaoticSystem() {
        return this.receivingChaoticSystem;
    }
}
