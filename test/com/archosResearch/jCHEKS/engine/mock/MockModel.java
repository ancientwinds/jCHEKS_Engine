package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.engine.message.OutgoingMessage;
import com.archosResearch.jCHEKS.concept.ioManager.ContactInfo;
import com.archosResearch.jCHEKS.engine.model.AbstractModel;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class MockModel extends AbstractModel{

    @Override
    public void addContact(Contact contact) throws ContactAlreadyExistException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addIncomingMessage(String messageContent, Contact contact) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addOutgoingMessage(String messageContent, String contactName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Contact findContactByReceiverSystemId(String receiverSystemId) throws ContactNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Contact findContactBySendingSystemId(String sendingSystemId) throws ContactNotFoundException {
        try {
            ContactInfo info = new ContactInfo("1.1.1.1", 9000, "name", "receivingSystem", sendingSystemId);
            return new Contact(info, new StubCommunicator(), new StubEncrypter(), new StubChaoticSystem(), new StubChaoticSystem());
        } catch (Exception ex) {
                throw new ContactNotFoundException("error");  
        }
    }

    @Override
    public Contact findContactByName(String name) throws ContactNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OutgoingMessage getLastOutgoingMessageBySystemId(String contactName) throws ContactNotFoundException {
        return new OutgoingMessage("outgoing message");
    }
    
}
