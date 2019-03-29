/*
 * Copyright 2019 Anton Straka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sk.antons.sprops.alg;

/**
 * Factory implementation for providing algorithm implementations.
 * @author antons
 */
public class SpropsAlgFactory {
    
    /**
     * SimpleAesSpropsAlg 
     */
    public static final int SIMPLE_AES = 1;

    /**
     * Default algorithm used by implementation (Currently SIMPLE_AES)
     */
    public static final int DEFAULT = SIMPLE_AES;
   
    /**
     * Maps integer value to real implementation
     * @param type integer constant defined in this class
     * @return Implementation for given constant
     */
    public static SpropsAlg algorithm(int type) {
        if(SIMPLE_AES == type) return new SimpleAesSpropsAlg();
        throw new IllegalArgumentException("Unknown SpropsAlg type " + type);
    }
    
    /**
     * Provides default algorithm
     * @return default algorithm
     */
    public static SpropsAlg algorithm() {
        return algorithm(DEFAULT);
    }
}
