package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Contact {

    private final String name;
    //private final int senderChaoticSystemId;
    private final String receiverChaoticSystemId;
    private final AbstractCommunicator communicator;
    //private final AbstractEncrypter encrypter;

    public Contact(String name, AbstractCommunicator communicator/*, int aSenderChaoticSystemId*/, String receiverChaoticSystemId) {
        this.name = name;
        this.communicator = communicator;
        //this.senderChaoticSystemId = aSenderChaoticSystemId;
        this.receiverChaoticSystemId = receiverChaoticSystemId;
    }

    public String getName() {
        return this.name;
    }

    public AbstractCommunicator getCommunicator() {
        return this.communicator;
    }
    
    public String getReceiverChaoticSystemId(){
        return receiverChaoticSystemId;
    }
}
