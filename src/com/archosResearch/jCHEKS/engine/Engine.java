
package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.communicator.tcp.TCPCommunicator;
import com.archosResearch.jCHEKS.communicator.tcp.TCPReceiver;
import com.archosResearch.jCHEKS.communicator.tcp.TCPSender;
import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.gui.chat.model.AbstractModel;
import com.archosResearch.jCHEKS.gui.chat.model.Contact;
import com.archosResearch.jCHEKS.gui.chat.model.ContactCollectionDefault;
import com.archosResearch.jCHEKS.gui.chat.model.Model;
import com.archosResearch.jCHEKS.gui.chat.model.NameOfContactAlreadyExistInContactsException;
import com.archosResearch.jCHEKS.gui.chat.view.JavaFxViewController;
import com.archosResearch.jCHEKS.gui.chat.view.ViewController;
import com.archosResearch.jCheks.concept.communicator.AbstractCommunication;
import com.archosResearch.jCheks.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCheks.concept.communicator.CommunicatorException;
import com.archosResearch.jCheks.concept.communicator.CommunicatorObserver;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Thomas Lepage  & Michael Roussel <rousselm4@gmail.com>
 */
public class Engine extends AbstractEngine  implements CommunicatorObserver{

    private Contact contact;
    private AbstractModel model;
        
    public Engine(String args[])
    {
        try {
            String remoteIp = args[0];
            String remotePort = args[1];
            String remoteContactName = args[2];
            System.out.println(args[2]);
            
            AbstractCommunicator communicator = new TCPCommunicator(new TCPSender(remoteIp, Integer.parseInt(remotePort)), TCPReceiver.getInstance());
            communicator.addObserver(this);
            
            AbstractModel model = new Model(new ContactCollectionDefault());
            this.contact = new Contact(remoteContactName, communicator);            
            model.addContact(contact);
            
            //TODO TEMP
            ViewController viewController = JavaFxViewController.getInstance();
            viewController.setSelectedContactName(contact.getName());
            model.addObserver(viewController);
            this.model = model;
        } catch (NameOfContactAlreadyExistInContactsException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void ackReceived() {
        //TODO ...
        System.out.println("Ack received!!!");
    }
    
    @Override
    public void communicationReceived(AbstractCommunication communication) {
        this.model.addIncomingMessage(communication.getCipher(), this.contact);
        try {
            communication = new Communication(communication.getCipher(), "chipherCheck", "systemId");
            this.contact.getCommunicator().sendCommunication(communication);
        } catch (CommunicatorException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
        //TODO Get contact
        //Decrypt message
    }
    
    public static void main(String args[]) throws NameOfContactAlreadyExistInContactsException{
        new Engine(args);
    }
    
    public void handleOutgoingMessage(String messageContent, String contactName) {
        this.model.addOutgoingMessage(messageContent, contactName);
    }

}
