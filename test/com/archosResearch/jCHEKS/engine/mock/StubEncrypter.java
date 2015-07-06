/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.encrypter.AbstractEncrypter;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class StubEncrypter extends AbstractEncrypter{

    @Override
    public String encrypt(String text, byte[] keyByte) {return "encrypted";}

    @Override
    public String decrypt(String text, byte[] keyByte) {return "decrypted";}

    @Override
    public int bytesNeeded() { return 144; }
    
}
