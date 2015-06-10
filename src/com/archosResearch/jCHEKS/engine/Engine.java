package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.communicator.tcp.*;
import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.engine.model.*;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.engine.model.exception.*;
import com.archosResearch.jCHEKS.gui.chat.view.JavaFxViewController;
import com.archosResearch.jCHEKS.concept.ioManager.InputOutputManager;
import com.archosResearch.jCHEKS.concept.communicator.*;
import com.archosResearch.jCHEKS.concept.exception.AbstractCommunicatorException;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;
import java.util.UUID;
import java.util.logging.*;


/**
 *
 * @author Thomas Lepage  & Michael Roussel <rousselm4@gmail.com>
 */
public class Engine extends AbstractEngine  implements CommunicatorObserver{
    private final AbstractModel model;
        
    public Engine(AbstractModel model, InputOutputManager ioManager) throws ContactAlreadyExistException{
            this.model = model;
            this.model.addObserver(ioManager);
            ioManager.setEngine(this);
    }
    
    @Override
    public void ackReceived() {
        //TODO ... Update state of message.
        System.out.println("Ack received!!!");
    }
    
    @Override
    public void secureAckReceived() {
        //TODO ... Update state of message.
        System.out.println("Secure Ack received!!!");
    }
    
    @Override
    public String communicationReceived(AbstractCommunication communication) {
        try {
            Contact contact = this.model.findContactByReceiverSystemId(communication.getSystemId());
            this.model.addIncomingMessage(communication.getCipher(), contact); 
            //TODO return something else.
            return "Testing secure ACK";
        } catch (AddIncomingMessageException | ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            //TODO Better handling of exceptions.
        }
        return null;

    }
    
    public void createContact(String contactName, String remoteIp, String sendingPort, String receivingPort){
        AbstractCommunicator communicator = new TCPCommunicator(new TCPSender(remoteIp, Integer.parseInt(sendingPort)), TCPReceiver.getInstance(Integer.parseInt(receivingPort)));          
        communicator.addObserver(this);
        Contact contact = new Contact(contactName, communicator, UUID.randomUUID().toString());
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
            
            //TODO: Do not respect Law of Demeter or find a better name for contact.
            this.model.findContactByName(contactName).getCommunicator().sendCommunication(new Communication(messageContent, "chipherCheck", "systemId"));
        } catch (AddOutgoingMessageException | AbstractCommunicatorException | ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}