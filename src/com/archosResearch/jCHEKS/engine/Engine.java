package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.chaoticSystem.*;
import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.communicator.tcp.*;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.engine.model.*;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactAlreadyExistException;
import com.archosResearch.jCHEKS.gui.chat.view.JavaFxViewController;
import com.archosResearch.jCHEKS.concept.communicator.*;
import com.archosResearch.jCHEKS.concept.encrypter.AbstractEncrypter;
import com.archosResearch.jCHEKS.concept.engine.message.*;
import com.archosResearch.jCHEKS.concept.exception.*;
import com.archosResearch.jCHEKS.concept.ioManager.*;
import com.archosResearch.jCHEKS.encrypter.RijndaelEncrypter;
import com.archosResearch.jCHEKS.engine.model.contact.exception.ContactNotFoundException;
import com.archosResearch.jCHEKS.messageChecker.*;
import com.archosResearch.jCHEKS.messageChecker.exception.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
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
            String systemId = communication.getSystemId();
            OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(systemId);
            message.updateState(AbstractMessage.State.WAITING_FOR_SECURE_ACK);
            this.ioManager.refresh();
            this.ioManager.log("Acknowledge received.", this.model.findContactBySendingSystemId(systemId).getContactInfo().getName());

        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void secureAckReceived(AbstractCommunication communication, String secureAck) {
        try {
            String systemId = communication.getSystemId();
            Contact contact = this.model.findContactBySendingSystemId(systemId);
            AbstractChaoticSystem chaoticSystem = contact.getSendingChaoticSystem();
            String contactName = contact.getContactInfo().getName();
            
            try {
                byte[] secureAckKey = chaoticSystem.getKey(SecureAckGenerator.getKeyLength());            
            
                if(SecureAckGenerator.validateSecureAck(secureAckKey, communication.getCipher(), secureAck)) {
                    OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(systemId);
                    message.updateState(AbstractMessage.State.OK);
                    this.ioManager.refresh();
                    this.ioManager.log("Secure acknowledge received.", contactName);
                    chaoticSystem.evolveSystem();
                } else {
                    this.ioManager.log("Wrong secure ack received", contactName);     
                }
            } catch (SecureAckGeneratorException ex) {
                this.ioManager.log("Error while validating the secure ack.", contactName);     
            } catch (Exception ex) {
                this.ioManager.log("Error getting secureAck key.", contactName);     
            }
        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String communicationReceived(AbstractCommunication communication) {
        try {
            Contact contact = this.model.findContactByReceiverSystemId(communication.getSystemId());
            String contactName = contact.getContactInfo().getName();
            AbstractChaoticSystem chaoticSystem = contact.getReceivingChaoticSystem();
            AbstractEncrypter encrypter = contact.getEncrypter();
            
            try {
                byte[] key = chaoticSystem.getKey(encrypter.getByteNeeded());
                String decryptedMessage = encrypter.decrypt(communication.getCipher(), key);
                this.ioManager.log("Communication received: \n      Key: " + Arrays.toString(key), contactName);
                         
                byte[] checkKey = chaoticSystem.getKey(MessageChecker.getKeyLength());
                if(MessageChecker.validateMessage(checkKey, decryptedMessage, communication.getCipherCheck())) {
                    this.model.addIncomingMessage(decryptedMessage, contact);
                    byte[] secureAckKey = chaoticSystem.getKey(SecureAckGenerator.getKeyLength());
                    try {
                        String secureAck = SecureAckGenerator.generateSecureAck(secureAckKey, communication.getCipher());
                        chaoticSystem.evolveSystem();
                        return secureAck;
                    } catch (SecureAckGeneratorException ex) {
                        this.ioManager.log("Error: " + ex.getMessage(), contactName);
                    }                    
                }
            } catch (MessageCheckerException ex) {
                this.ioManager.log("Error while checking the cipher check: " + ex.getMessage(), contactName);
            }
            
            //TODO return something else.
            return "No secure ACK";
        } catch (ContactNotFoundException | EncrypterException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void failToReceiveAck(AbstractCommunication communication) {
        try {
            String systemId = communication.getSystemId();
            this.lastMessageHasFailed(systemId);
            
            this.ioManager.refresh();
            this.ioManager.log("Failed to receive acknowledge.", this.model.findContactBySendingSystemId(systemId).getContactInfo().getName());

        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void failToReceiveSecureAck(AbstractCommunication communication) {
        try {
            String systemId = communication.getSystemId();
            this.lastMessageHasFailed(systemId);
            
            this.ioManager.refresh();
            this.ioManager.log("Failed to receive secure acknowledge.", this.model.findContactBySendingSystemId(systemId).getContactInfo().getName());

        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void timeOutReached(AbstractCommunication communication) {
        try {
            String systemId = communication.getSystemId();
            this.lastMessageHasFailed(systemId);
            
            this.ioManager.refresh();
            this.ioManager.log("Timeout reached.", this.model.findContactByReceiverSystemId(systemId).getContactInfo().getName());

        } catch (ContactNotFoundException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void exceptionThrown(CommunicatorException exception, AbstractCommunication communication) {
        try {
            String systemId = communication.getSystemId();
            this.lastMessageHasFailed(systemId);
            
            this.ioManager.refresh();
            this.ioManager.log(exception.getMessage(), this.model.findContactByReceiverSystemId(systemId).getContactInfo().getName());

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
            AbstractChaoticSystem sendingSystem = FileReader.readChaoticSystem(contactInfo.getSendingChaoticSystem());
            AbstractChaoticSystem receivingSystem = FileReader.readChaoticSystem(contactInfo.getReceivingChaoticSystem());
            
            contact = new Contact(contactInfo, communicator, new RijndaelEncrypter(), sendingSystem, receivingSystem);
            model.addContact(contact);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | ContactAlreadyExistException ex) {
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
            AbstractChaoticSystem chaoticSystem = contact.getSendingChaoticSystem();
            AbstractEncrypter encrypter = contact.getEncrypter();
            
            byte[] key = chaoticSystem.getKey(encrypter.getByteNeeded());   
            
            String encryptedMessage = encrypter.encrypt(messageContent, key);
            try {
                byte[] checkKey = chaoticSystem.getKey(MessageChecker.getKeyLength());
                String cipherCheck = MessageChecker.encodeMessage(checkKey, messageContent);
                contact.getCommunicator().sendCommunication(new Communication(encryptedMessage, cipherCheck, contact.getContactInfo().getUniqueId()));

            } catch (MessageCheckerException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch ( CommunicatorException | ContactNotFoundException | EncrypterException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setReceivingPort(int port) {
        TCPReceiver.getInstance(port);
    }
    
    private void lastMessageHasFailed(String systemId) throws ContactNotFoundException {
        OutgoingMessage message = this.model.getLastOutgoingMessageBySystemId(systemId);
        message.updateState(AbstractMessage.State.FAILED);
    }
    
    public static void main(String args[]) throws ContactAlreadyExistException{
        AbstractModel model = new Model();
        InputOutputManager ioManager = JavaFxViewController.getInstance();
        new Engine(model, ioManager);
    }

}
