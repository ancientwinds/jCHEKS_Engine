package com.archosResearch.jCHEKS.engine.model.exception;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class AddIncomingMessageException extends AddMessageException {

    public AddIncomingMessageException(String message) {
        super(message);
    }
    
    public AddIncomingMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
