package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.engine.message.OutgoingMessage;
import com.archosResearch.jCHEKS.engine.model.AbstractModel;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;


/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ModelContactNotFoundExceptionThrower extends AbstractModel {

    public ModelContactNotFoundExceptionThrower() {}

    @Override
    public void addContact(Contact contact) throws ContactAlreadyExistException {}

    @Override
    public void addIncomingMessage(String messageContent, Contact contact){}

    @Override
    public void addOutgoingMessage(String messageContent, String contactName){}

    @Override
    public Contact findContactByReceiverSystemId(String receiverSystemId) throws ContactNotFoundException {return null;}

    @Override
    public Contact findContactByName(String name) throws ContactNotFoundException {return null;}

    @Override
    public Contact findContactBySendingSystemId(String sendingSystemId) throws ContactNotFoundException {return null;}

    @Override
    public OutgoingMessage getLastOutgoingMessageBySystemId(String contactName) throws ContactNotFoundException {return null;}
    
}
