package com.archosResearch.jCHEKS.engine.model;

import com.archosResearch.jCHEKS.engine.model.exception.AddOutgoingMessageException;
import com.archosResearch.jCHEKS.engine.model.exception.AddIncomingMessageException;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.concept.engine.ModelObservable;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public abstract class AbstractModel extends ModelObservable {

    public abstract void addContact(Contact contact) throws ContactAlreadyExistException;

    public abstract void addIncomingMessage(String messageContent, Contact contact) throws AddIncomingMessageException;

    public abstract void addOutgoingMessage(String messageContent, String contactName) throws AddOutgoingMessageException;
}
