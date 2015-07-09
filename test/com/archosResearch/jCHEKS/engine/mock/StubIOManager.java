package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.concept.engine.message.*;
import com.archosResearch.jCHEKS.concept.ioManager.InputOutputManager;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class StubIOManager implements InputOutputManager{

    public boolean ackLogged = false;
    
    @Override
    public void setEngine(AbstractEngine engine) {}

    @Override
    public void forwardOutgoingMessage(String messageContent, String contactName) {}

    @Override
    public void log(String logMessage, String id) {
        if(logMessage.equals("Acknowledge received.")) {
            this.ackLogged = true;
        }
    }

    @Override
    public void messageSent(OutgoingMessage message, String contactName) {}

    @Override
    public void messageReceived(IncomingMessage message, String contactName) {}

    @Override
    public void contactAdded(String contactName) {}

    @Override
    public void refresh() {}
    
}
