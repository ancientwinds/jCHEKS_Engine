/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archosResearch.jCHEKS.engine.exception;

import com.archosResearch.jCHEKS.concept.exception.AbstractEngineException;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class EngineException extends AbstractEngineException {

    public EngineException(String message) {
        super(message);
    }
    
    public EngineException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
