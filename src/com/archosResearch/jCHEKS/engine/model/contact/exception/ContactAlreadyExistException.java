package com.archosResearch.jCHEKS.engine.model.contact.exception;

import com.archosResearch.jCHEKS.engine.exception.EngineException;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactAlreadyExistException extends EngineException {

    public ContactAlreadyExistException(String message) {
        super(message);
    }
    
    public ContactAlreadyExistException(String message, Throwable throwable) {
        super(message, throwable);
    }

}