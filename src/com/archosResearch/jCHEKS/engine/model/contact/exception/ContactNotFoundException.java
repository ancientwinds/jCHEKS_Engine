package com.archosResearch.jCHEKS.engine.model.contact.exception;

import com.archosResearch.jCHEKS.engine.exception.EngineException;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class ContactNotFoundException extends EngineException {

    public ContactNotFoundException(String message) {
        super(message);
    }

}