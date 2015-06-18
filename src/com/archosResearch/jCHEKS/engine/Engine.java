package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystemMock;
import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.communicator.tcp.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.engine.model.*;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.gui.chat.view.JavaFxViewController;
import com.archosResearch.jCHEKS.concept.communicator.*;
import com.archosResearch.jCHEKS.concept.engine.message.*;
import com.archosResearch.jCHEKS.concept.exception.CommunicatorException;
import com.archosResearch.jCHEKS.concept.exception.EncrypterException;
import com.archosResearch.jCHEKS.concept.ioManager.*;
import com.archosResearch.jCHEKS.encrypter.RijndaelEncrypter;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.*;
import javax.crypto.NoSuchPaddingException;


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
            Contact contact = this.model.findContactByReceiverSystemId(communication.getSystemId());
            contact.getSendingChaoticSystem().Evolve();
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String communicationReceived(AbstractCommunication communication) {
        try {
            Contact contact = this.model.findContactByReceiverSystemId(communication.getSystemId());
            
            String decryptedMessage = contact.getEncrypter().decrypt(communication.getCipher(), contact.getReceivingChaoticSystem());
           
            this.model.addIncomingMessage(decryptedMessage, contact); 
            //TODO return something else.
            contact.getReceivingChaoticSystem().Evolve();
            return "Testing secure ACK";
        } catch (ContactNotFoundException | EncrypterException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void failToReceiveAck(AbstractCommunication communication) {
        try {
            OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(communication.getSystemId());
            message.updateState(AbstractMessage.State.FAILED);
            this.ioManager.refresh();
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void failToReceiveSecureAck(AbstractCommunication communication) {
        try {
            OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(communication.getSystemId());
            message.updateState(AbstractMessage.State.FAILED);
            this.ioManager.refresh();
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void timeOutReached(AbstractCommunication communication) {
        try {
            OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(communication.getSystemId());
            message.updateState(AbstractMessage.State.FAILED);
            this.ioManager.refresh();
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void createContact(ContactInfo contactInfo){
        AbstractCommunicator communicator = new TCPCommunicator(new TCPSender(contactInfo.getIp(), contactInfo.getPort()), TCPReceiver.getInstance(), contactInfo.getUniqueId()/*Maybe system id or something else, used as unique id.*/);          
        communicator.addObserver(this);
        
        Contact contact;
        try {
            AbstractChaoticSystem sendingSystem = new ChaoticSystemMock();
            //TODO Change the type of system for the real one not the mock.
            //TODO Maybe change the key lenght
            sendingSystem.Generate(128);
            
            AbstractChaoticSystem receivingSystem = new ChaoticSystemMock();
            //TODO Change the type of system for the real one not the mock.
            //TODO Maybe change the key lenght
            receivingSystem.Generate(128);
            contact = new Contact(contactInfo, communicator, new RijndaelEncrypter(), sendingSystem, receivingSystem);
            try {
                model.addContact(contact);
            } catch (ContactAlreadyExistException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void handleOutgoingMessage(String messageContent, String contactName) {
        try {
            this.model.addOutgoingMessage(messageContent, contactName);
            
            Contact contact = this.model.findContactByName(contactName);
            
            String encryptedMessage = contact.getEncrypter().encrypt(messageContent, contact.getSendingChaoticSystem());
            contact.getCommunicator().sendCommunication(new Communication(encryptedMessage, "chipherCheck", contact.getContactInfo().getUniqueId()));
        } catch ( CommunicatorException | ContactNotFoundException | EncrypterException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setReceivingPort(int port) {
        TCPReceiver.getInstance(port);
    }
    
    public static void main(String args[]) throws ContactAlreadyExistException{
        AbstractModel model = new Model();
        InputOutputManager ioManager = JavaFxViewController.getInstance();
        new Engine(model, ioManager);
    }
}
