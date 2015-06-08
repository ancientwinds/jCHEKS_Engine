package com.archosResearch.jCHEKS.engine.model.exception;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class AddOutgoingMessageException extends AddMessageException {
    
    public AddOutgoingMessageException(String message) {
        super(message);
    }
    
    public AddOutgoingMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}