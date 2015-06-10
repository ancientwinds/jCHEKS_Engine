package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.engine.model.AbstractModel;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;
import com.archosResearch.jCHEKS.engine.model.exception.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class StubModel extends AbstractModel{

    @Override
    public void addContact(Contact contact) throws ContactAlreadyExistException {}

    @Override
    public void addIncomingMessage(String messageContent, Contact contact) throws AddIncomingMessageException {}

    @Override
    public void addOutgoingMessage(String messageContent, String contactName) throws AddOutgoingMessageException {}

    @Override
    public Contact findContactByReceiverSystemId(String receiverSystemId) throws ContactNotFoundException {
        return null;
    }

    @Override
    public Contact findContactByName(String name) throws ContactNotFoundException {
        return null;
    }
    
}
