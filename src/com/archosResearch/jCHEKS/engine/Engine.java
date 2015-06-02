
package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.communicator.tcp.TCPCommunicator;
import com.archosResearch.jCHEKS.communicator.tcp.TCPReceiver;
import com.archosResearch.jCHEKS.communicator.tcp.TCPSender;
import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.gui.chat.AppController;
import com.archosResearch.jCHEKS.gui.chat.AppControllerDefault;
import com.archosResearch.jCHEKS.gui.chat.model.Contact;
import com.archosResearch.jCHEKS.gui.chat.model.ContactCollectionDefault;
import com.archosResearch.jCHEKS.gui.chat.model.IncomingMessage;
import com.archosResearch.jCHEKS.gui.chat.model.Model;
import com.archosResearch.jCHEKS.gui.chat.model.ModelDefault;
import com.archosResearch.jCHEKS.gui.chat.model.ModelObserver;
import com.archosResearch.jCHEKS.gui.chat.model.NameOfContactAlreadyExistInContactsException;
import com.archosResearch.jCHEKS.gui.chat.model.OutgoingMessage;
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
 * @author Thomas Lepage
 */
public class Engine extends AbstractEngine  implements CommunicatorObserver, ModelObserver{

    private AppController appController = null;
    private Contact contact;
        
    public Engine(String args[])
    {
        try {
            String remoteIp = args[0];
            String remotePort = args[1];
            String remoteContactName = args[2];
            System.out.println(args[2]);
            
            AbstractCommunicator communicator = new TCPCommunicator(new TCPSender(remoteIp, Integer.parseInt(remotePort)), TCPReceiver.getInstance());
            communicator.addObserver(this);
            
            Model model = new ModelDefault(new ContactCollectionDefault());
            this.contact = new Contact(remoteContactName, communicator);            
            model.addContact(contact);            
            model.addObserver(this);
            
            //TODO TEMP
            ViewController viewController = JavaFxViewController.getInstance();
            viewController.setSelectedContact(contact);
            
            this.appController = new AppControllerDefault(model, JavaFxViewController.getInstance());
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
        this.appController.handleIncomingMessage(communication.getChipher(), this.contact);
    }

    @Override
    public void messageSent(OutgoingMessage message, Contact contact) {
        try {
            Communication communication = new Communication(message.getContent(), "chipherCheck", "systemId");
            contact.getCommunicator().sendCommunication(communication);
        } catch (CommunicatorException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void messageReceived(IncomingMessage  message, Contact contact) {
        System.out.println("Message received: " + message.getContent());
    }
    
    @Override
    public void contactAdded(Contact contact) {
        System.out.println("Contact added");
    }
    
    public static void main(String args[]) throws NameOfContactAlreadyExistInContactsException{
        new Engine(args);
    }



}
