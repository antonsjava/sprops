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

import sk.antons.jaul.Is;
import sk.antons.jaul.binary.Base64;
import sk.antons.sprops.alg.SpropsAlg;
import sk.antons.sprops.alg.SpropsAlgFactory;
import sk.antons.sprops.model.SpropsData;

/**
 * Simple String encoder/decoder
 * @author antons
 */
public class SimpleEncoder extends AbstractEncoder {

    public static SimpleEncoder instance(byte[] password) {
        SimpleEncoder encoder = new SimpleEncoder();
        encoder.password(password);
        return encoder;
    }
    
    public static SimpleEncoder instance(String password) {
        SimpleEncoder encoder = new SimpleEncoder();
        encoder.password(password);
        return encoder;
    }
    
    /**
     * Encodes provided string, Uses provided password and algorithm.
     * @param value String to me encoded
     * @return encoded string
     */
    public String encode(String value) {
        StringBuilder result = new StringBuilder("sprops:");
        if(!Is.empty(value)) {
            try {
                byte[] rawData = value.getBytes("UTF-8");
                SpropsAlg alg = SpropsAlgFactory.algorithm(algorithm);
                alg.password(rawPassword);
                SpropsData sdata = alg.prepare(rawData);
                byte[] encodeddata = alg.encode(sdata);
                result.append(Base64.encode(encodeddata, false));
                //System.out.println(" --- " + result);
            } catch(Exception e) {
                throw new IllegalArgumentException("Unable to encode string " + value, e);
            }
        }
        return result.toString();
    }
    
    /**
     * Decodes provided encoded string, Uses provided password and algorithm.
     * @param value Encoded string to be decoded
     * @return decoded string
     */
    public String decode(String value) {
        if(Is.empty(value)) throw new IllegalArgumentException("Unable to decode - no sprops format");
        if(!value.startsWith("sprops:")) throw new IllegalArgumentException("Unable to decode - no sprops format");
        StringBuilder result = new StringBuilder();
        try {
            value = value.substring(7);
            byte[] encodedData = Base64.decode(value);
            SpropsAlg alg = SpropsAlgFactory.algorithm(algorithm);
            alg.password(rawPassword);
            SpropsData sdata = alg.decode(encodedData, 0, encodedData.length);
            String resultString = new String(sdata.getData(), "UTF-8");
            result.append(resultString);
        } catch(Exception e) {
            throw new IllegalArgumentException("Unable to decode input data ", e);
        }
        return result.toString();
    }
}
