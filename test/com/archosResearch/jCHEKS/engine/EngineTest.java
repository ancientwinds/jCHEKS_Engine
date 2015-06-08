package com.archosResearch.jCHEKS.engine;

import com.archosResearch.jCHEKS.engine.mock.ModelExceptionThrower;
import com.archosResearch.jCHEKS.communicator.Communication;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunication;
import com.archosResearch.jCHEKS.concept.communicator.AbstractCommunicator;
import com.archosResearch.jCHEKS.concept.ioManager.InputOutputManager;
import com.archosResearch.jCHEKS.engine.mock.StubIoManager;
import com.archosResearch.jCHEKS.engine.mock.StubCommunicator;
import com.archosResearch.jCHEKS.engine.mock.StubModel;
import com.archosResearch.jCHEKS.engine.model.AbstractModel;
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
    
    @Test
    public void testAckReceived() {
        StubCommunicator communicator = new StubCommunicator();
        StubModel model = new StubModel();
        StubIoManager ioManager = new StubIoManager();
        Engine engine = new Engine(communicator, model, ioManager, new Contact("Alice", communicator));
        engine.ackReceived();
    }

    @Test
    public void communicationReceived_should_not_cause_any_error_with_stub() {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new StubModel();
        InputOutputManager ioManager = new StubIoManager();
        Engine engine = new Engine(communicator, model, ioManager, new Contact("Alice", communicator));
        AbstractCommunication communication = new Communication("cipher","cipherCheck","sysId");
        engine.communicationReceived(communication);
    }
    
    @Test
    public void communicationReceived_should_catch_AddIncomingMessageException_if_thrown() {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new ModelExceptionThrower();
        InputOutputManager ioManager = new StubIoManager();
        Engine engine = new Engine(communicator, model, ioManager, new Contact("Alice", communicator));
        AbstractCommunication communication = new Communication("cipher","cipherCheck","sysId");
        engine.communicationReceived(communication);
    }

    @Test
    public void handleOutgoingMessage_should_not_cause_any_error_with_stub() {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new StubModel();
        InputOutputManager ioManager = new StubIoManager();
        Engine engine = new Engine(communicator, model, ioManager, new Contact("Alice", communicator));
        engine.handleOutgoingMessage("Message content", "Alice");
    }
    
    @Test
    public void handleOutgoingMessage_should_catch_Exceptions() {
        AbstractCommunicator communicator = new StubCommunicator();
        AbstractModel model = new ModelExceptionThrower();
        InputOutputManager ioManager = new StubIoManager();
        Engine engine = new Engine(communicator, model, ioManager, new Contact("Alice", communicator));
        engine.handleOutgoingMessage("Message content", "Alice");
    }
    
}
