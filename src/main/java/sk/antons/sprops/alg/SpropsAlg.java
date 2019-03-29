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

import sk.antons.sprops.model.SpropsData;

/**
 * Algorithm implementation used for encoding and decoding.
 * @author antons
 */
public interface SpropsAlg {
    
    /**
     * Sets password used by algorithm.
     * @param password 
     */
    void password(byte[] password);
    
    /**
     * Sets password used by algorithm.
     * @param password 
     */
    void password(String password);

    /**
     * Prepare algorithm for encoding.
     * @param data Data to be encoded.
     * @return Data used for encoding given data
     */
    SpropsData prepare(byte[] data);

    /**
     * Encode given data.
     * @param data data for encoding
     * @return encoded data
     */
    byte[] encode(SpropsData data);


    /**
     * Decodes given data.
     * @param data data to be decoded
     * @param offset offset of data
     * @param length length of data
     * @return decoded data.
     */
    SpropsData decode(byte[] data, int offset, int length);
}
