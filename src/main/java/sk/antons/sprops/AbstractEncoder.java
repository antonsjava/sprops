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
package sk.antons.sprops;

import sk.antons.sprops.alg.SpropsAlgFactory;

/**
 * Helper class for Encoder classes. Provides common functionality.
 * @author antons
 */
public abstract class AbstractEncoder {

    
    protected byte[] rawPassword; 
    protected int algorithm = SpropsAlgFactory.DEFAULT; 
    
    /**
     * Stores password used by encoder. 
     * @param password - provided password 
     */
    public void password(byte[] password) {
        if(password == null) throw new IllegalArgumentException("Bad password");
        if(password.length == 0) throw new IllegalArgumentException("Bad password");
        rawPassword = password;
    }

    /**
     * Stores password used by encoder (UTF-8 encoded). 
     * @param password - provided password 
     */
    public void password(String password) {
        if(password == null) throw new IllegalArgumentException("Bad password");
        try {
            password(password.getBytes("UTF-8"));
        } catch(Exception e) {
            throw new IllegalArgumentException("Bad password - encoding", e);
        }
    }
    
    /**
     * Sets algorithm type used by encoder.
     * @param alg (@see SpropsAlgFactory)
     */
    public void algorithm(int alg) {
        this.algorithm = alg;
    }

}
