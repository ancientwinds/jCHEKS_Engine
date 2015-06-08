package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.engine.mock.StubIoManager;
import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.engine.mock.StubModel;
import com.archosResearch.jCHEKS.engine.model.contact.Contact;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class EngineTest {
    
    @Test
    public void constructor_should_construct_the_engine() {
        Engine engine = null;
        StubCommunicator communicator = new StubCommunicator();
        StubModel model = new StubModel();
        StubIoManager ioManager = new StubIoManager();
        engine = new Engine(communicator, model, ioManager, new Contact("Alice", communicator));
    }
    /*
    @Test
    public void testAckReceived() {
        Engine instance = null;
        instance.ackReceived();
    }

    @Test
    public void testCommunicationReceived() {
        AbstractCommunication communication = null;
        Engine instance = null;
        instance.communicationReceived(communication);
    }

    @Test
    public void testMain() throws Exception {
        String[] args = null;
        Engine.main(args);
    }

    @Test
    public void testHandleOutgoingMessage() {
        String messageContent = "";
        String contactName = "";
        Engine instance = null;
        instance.handleOutgoingMessage(messageContent, contactName);
    }*/
    
}
