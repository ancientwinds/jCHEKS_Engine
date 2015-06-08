
package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.communicator.tcp.TCPCommunicator;
import com.archosResearch.jCHEKS.communicator.tcp.TCPReceiver;
import com.archosResearch.jCHEKS.communicator.tcp.TCPSender;
import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.engine.model.AbstractModel;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.Model;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.engine.model.exception.AddIncomingMessageException;
import com.archosResearch.jCHEKS.engine.model.exception.AddOutgoingMessageException;
import com.archosResearch.jCHEKS.gui.chat.view.JavaFxViewController;
import com.archosResearch.jCHEKS.concept.ioManager.InputOutputManager;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunication;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.communicator.CommunicatorObserver;
import com.archosResearch.jCHEKS.concept.exception.AbstractCommunicatorException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Thomas Lepage  & Michael Roussel <rousselm4@gmail.com>
 */
public class Engine extends AbstractEngine  implements CommunicatorObserver{

    private final Contact contact;
    private final AbstractModel model;
        
    public Engine(AbstractCommunicator communicator, AbstractModel model, InputOutputManager ioManager, /*TEMP*/Contact contact){
            this.model = model;
            this.model.addObserver(ioManager);
            this.contact = contact;
            communicator.addObserver(this);
            ioManager.setEngine(this);
    }
    
    @Override
    public void ackReceived() {
        //TODO ...
        System.out.println("Ack received!!!");
    }
    
    @Override
    public void communicationReceived(AbstractCommunication communication) {
        try {
            this.model.addIncomingMessage(communication.getCipher(), this.contact);
        } catch (AddIncomingMessageException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            //TODO Better handling of exceptions.
        }

    }
    
    public static void main(String args[]) throws ContactAlreadyExistException{
        String remoteIp = args[0];
        String sendingPort = args[1];
        String remoteContactName = args[2];
        String receivingPort = args[3];
        AbstractModel model = new Model();
        AbstractCommunicator communicator = new TCPCommunicator(new TCPSender(remoteIp, Integer.parseInt(sendingPort)), TCPReceiver.getInstance(Integer.parseInt(receivingPort)));          
        Contact contact = new Contact(remoteContactName, communicator);
        model.addContact(contact);
        InputOutputManager ioManager = JavaFxViewController.getInstance();
        ioManager.setSelectedContactName(remoteContactName);
        new Engine(communicator, model, ioManager, contact);
    }
    
    @Override
    public void handleOutgoingMessage(String messageContent, String contactName) {
        try {
            this.model.addOutgoingMessage(messageContent, contactName);
            
            //TODO: Do not respect Law of Demeter 
            this.contact.getCommunicator().sendCommunication(new Communication(messageContent, "chipherCheck", "systemId"));
        } catch (AddOutgoingMessageException | AbstractCommunicatorException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}