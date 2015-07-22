package com.archosResearch.jCHEKS.engine.mock;

import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import java.util.Random;

/**
 *
 * @author Thomas Lepage thomas.lepage@hotmail.ca
 */
public class StubChaoticSystem extends AbstractChaoticSystem{

    public StubChaoticSystem() throws Exception {
        super(128);
    }

    @Override
    public void evolveSystem(int factor) {}

    @Override
    public byte[] getKey(int requiredLength) { return null;}

    @Override
    public void resetSystem() {}

    @Override
    public AbstractChaoticSystem cloneSystem() { return null;}

    @Override
    public String serialize() { return null;}

    @Override
    public void deserialize(String serialization) {}

    @Override
    public void generateSystem(int keyLength, Random random) {}
    
    @Override
    public int getAgentsCount() { return 0;}
    
}
