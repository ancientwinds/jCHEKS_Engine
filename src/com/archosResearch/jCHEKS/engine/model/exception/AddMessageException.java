package com.archosResearch.jCHEKS.engine.model.exception;

import com.archosResearch.jCHEKS.engine.exception.EngineException;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class AddMessageException extends EngineException {

    public AddMessageException(String message) {
        super(message);
    }
        
    public AddMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
