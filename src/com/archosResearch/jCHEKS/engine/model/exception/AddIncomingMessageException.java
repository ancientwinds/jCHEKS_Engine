package com.archosResearch.jCHEKS.engine.model.exception;

import com.archosResearch.jCHEKS.engine.exception.EngineException;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class AddIncomingMessageException extends EngineException {

    public AddIncomingMessageException(String message) {
        super(message);
    }
    
    public AddIncomingMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
