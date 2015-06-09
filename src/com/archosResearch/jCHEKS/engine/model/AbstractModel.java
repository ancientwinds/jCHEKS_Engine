package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.model.exception.*;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.concept.engine.ModelObservable;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractModel extends ModelObservable {

    public abstract void addContact(Contact contact) throws ContactAlreadyExistException;

    public abstract void addIncomingMessage(String messageContent, Contact contact) throws AddIncomingMessageException;

    public abstract void addOutgoingMessage(String messageContent, String contactName) throws AddOutgoingMessageException;

    public abstract Contact findContactByReceiverSystemId(String receiverSystemId)  throws ContactNotFoundException;

    public abstract Contact findContactByName(String name)  throws ContactNotFoundException;

}
