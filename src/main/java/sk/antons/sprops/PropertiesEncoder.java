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

import java.io.InputStream;
import java.util.Properties;

/**
 * Encoder wraps properties and provide them decoded. Provided password 
 * and algorithm are used for decoding property values.
 * @author antons
 */
public class PropertiesEncoder extends AbstractEncoder {
    Properties props = new Properties();

    public static PropertiesEncoder instance(byte[] password) {
        PropertiesEncoder encoder = new PropertiesEncoder();
        encoder.password(password);
        return encoder;
    }
    
    public static PropertiesEncoder instance(String password) {
        PropertiesEncoder encoder = new PropertiesEncoder();
        encoder.password(password);
        return encoder;
    }

    /**
     * Add another properties. 
     * @param props properties to add
     */
    public void addProperties(Properties props) {
        if(props == null) return;
        for(Object object : props.keySet()) {
            this.props.put(object, props.get(object));
        }
    }
    
    /**
     * Add another properties. 
     * @param propsis properties to add
     */
    public void addProperties(InputStream propsis) {
        if(props == null) return;
        Properties p = new Properties();
        try {
            p.load(propsis);
        } catch(Exception e) {
            throw new IllegalArgumentException("Unable to load properties", e);
        }
        addProperties(p);
    }

    /**
     * Reads propery and decode it if necessary
     * @param name name of the properties
     * @return decoded value for given property name
     */
    public String getProperty(String name) {
        String value = props.getProperty(name);
        if(value == null) return null;
        if(value.startsWith("sprops:")) {
            SimpleEncoder se = SimpleEncoder.instance(rawPassword);
            value = se.decode(value);
        }
        return value;
    }
    
    /**
     * Reads property and decode it if necessary
     * @param name name of the properties
     * @param defaultValue default value is provided property is not set
     * @return decoded value for given property name
     */
    public String getProperty(String name, String defaultValue) {
        String value = getProperty(name);
        if(value == null) return defaultValue;
        return value;
    }

    /**
     * Decodes all given properties and returns them decoded.
     * @return Decoded properties
     */
    public Properties decode() {
        Properties p = new Properties();
        SimpleEncoder se = SimpleEncoder.instance(rawPassword);
        for(Object object : props.keySet()) {
            Object value = props.get(object);
            if(value instanceof String) {
                String s = (String)value;
                if(s.startsWith("sprops:")) {
                    value = se.decode(s);
                }
            }
            p.put(object, value);
        }
        return p;
    }
    
}
