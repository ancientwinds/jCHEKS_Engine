package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class StubChaoticSystem extends AbstractChaoticSystem{

    @Override
    public void Evolve(int factor) {}

    @Override
    public byte[] Key(int requiredLength) { return null;}

    @Override
    public void Reset() {}

    @Override
    public AbstractChaoticSystem Clone() { return null;}

    @Override
    public String Serialize() { return null;}

    @Override
    public void Deserialize(String serialization) {}

    @Override
    public void Generate(int keyLength) throws Exception {}
    
}
