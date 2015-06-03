package com.archosResearch.jCHEKS.engine.model.contact;

import com.archosResearch.jCheks.concept.communicator.AbstractCommunicator;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Contact {

    private final String name;
    //private final int senderChaoticSystemId;
    //private final int receiverChaoticSystemId;
    private final AbstractCommunicator communicator;
    //private final AbstractEncrypter encrypter;

    public Contact(String aName, AbstractCommunicator communicator/*, int aSenderChaoticSystemId, int aReceiverChaoticSystemId*/) {
        this.name = aName;
        this.communicator = communicator;
        //this.senderChaoticSystemId = aSenderChaoticSystemId;
        //this.receiverChaoticSystemId = aReceiverChaoticSystemId;
    }

    public String getName() {
        return this.name;
    }

    public AbstractCommunicator getCommunicator() {
        return this.communicator;
    }
}
