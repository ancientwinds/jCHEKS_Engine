package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.engine.AbstractEngine;
import com.archosResearch.jCHEKS.concept.engine.message.IncomingMessage;
import com.archosResearch.jCHEKS.concept.engine.message.OutgoingMessage;
import com.archosResearch.jCHEKS.concept.ioManager.InputOutputManager;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class StubIoManager implements InputOutputManager{

    @Override
    public void setEngine(AbstractEngine engine) {}

    @Override
    public void forwardOutgoingMessage(String messageContent) {}

    @Override
    public void setSelectedContactName(String name) {}

    @Override
    public void messageSent(OutgoingMessage message, String contactName) {}

    @Override
    public void messageReceived(IncomingMessage message, String contactName) {}

    @Override
    public void contactAdded(String contactName) {}
    
}