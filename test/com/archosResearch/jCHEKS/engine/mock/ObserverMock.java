package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.engine.ModelObserver;
import com.archosResearch.jCHEKS.concept.engine.message.IncomingMessage;
import com.archosResearch.jCHEKS.concept.engine.message.OutgoingMessage;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ObserverMock implements ModelObserver {

    public OutgoingMessage lastMessageSent;
    public IncomingMessage lastMessageReceived;
    public String destinationContactOflastMessageSent;
    public String senderOflastMessageReceived;
    public String lastContactAdded;

    @Override
    public void messageSent(OutgoingMessage message, String contactName) {
        lastMessageSent = message;
        destinationContactOflastMessageSent = contactName;
    }

    @Override
    public void messageReceived(IncomingMessage message, String contactName) {
        lastMessageReceived = message;
        senderOflastMessageReceived = contactName;
    }

    @Override
    public void contactAdded(String contactName) {
        lastContactAdded = contactName;
    }

}
