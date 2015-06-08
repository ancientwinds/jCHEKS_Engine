package com.archosResearch.jCHEKS.engine.model.exception;

import com.archosResearch.jCHEKS.engine.exception.EngineException;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class AddOutgoingMessageException extends EngineException {
    
    public AddOutgoingMessageException(String message) {
        super(message);
    }
    
    public AddOutgoingMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}