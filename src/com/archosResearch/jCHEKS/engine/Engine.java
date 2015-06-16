package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.communicator.tcp.*;
import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.engine.model.*;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.gui.chat.view.JavaFxViewController;
import com.archosResearch.jCHEKS.concept.communicator.*;
import com.archosResearch.jCHEKS.concept.engine.message.*;
import com.archosResearch.jCHEKS.concept.exception.CommunicatorException;
import com.archosResearch.jCHEKS.concept.ioManager.*;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;
import java.util.logging.*;

/**
 *
 * @author Thomas Lepage  & Michael Roussel <rousselm4@gmail.com>
 */
public class Engine extends AbstractEngine  implements CommunicatorObserver{
    private final AbstractModel model;
    private final InputOutputManager ioManager;
        
    public Engine(AbstractModel model, InputOutputManager ioManager) throws ContactAlreadyExistException{
            this.model = model;
            this.model.addObserver(ioManager);
            this.ioManager = ioManager;
            ioManager.setEngine(this);
    }
    
    @Override
    public void ackReceived(AbstractCommunication communication) {
        try {
            OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(communication.getSystemId());
            message.updateState(AbstractMessage.State.WAITING_FOR_SECURE_ACK);
            this.ioManager.refresh();
            System.out.println("Ack received!!!");
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void secureAckReceived(AbstractCommunication communication) {
        try {
            OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(communication.getSystemId());
            message.updateState(AbstractMessage.State.OK);
            this.ioManager.refresh();
            System.out.println("Secure Ack received!!!");
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String communicationReceived(AbstractCommunication communication) {
        try {
            Contact contact = this.model.findContactByReceiverSystemId(communication.getSystemId());
            this.model.addIncomingMessage(communication.getCipher(), contact); 
            //TODO return something else.
            return "Testing secure ACK";
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    @Override
    public void createContact(ContactInfo contactInfo){
        try {
            AbstractCommunicator communicator = new TCPCommunicator(new TCPSender(contactInfo.getIp(), contactInfo.getPort()), TCPReceiver.getInstance(), contactInfo.getUniqueId()/*Maybe system id or something else, used as unique id.*/);          
            communicator.addObserver(this);
            Contact contact = new Contact(contactInfo, communicator);
            model.addContact(contact);
        } catch (ContactAlreadyExistException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String args[]) throws ContactAlreadyExistException{
        AbstractModel model = new Model();
        InputOutputManager ioManager = JavaFxViewController.getInstance();
        new Engine(model, ioManager);
    }
    
    @Override
    public void handleOutgoingMessage(String messageContent, String contactName) {
        try {
            this.model.addOutgoingMessage(messageContent, contactName);
            
            Contact contact = this.model.findContactByName(contactName);
            contact.getCommunicator().sendCommunication(new Communication(messageContent, "chipherCheck", contact.getContactInfo().getUniqueId()));
        } catch (CommunicatorException | ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setReceivingPort(int port) {
        TCPReceiver.getInstance(port);
    }

}