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
package sk.antons.sprops.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper data class for encoded/decoded data.
 * @author antons
 */
public class SpropsData {
    private int version;
    private Header header;
    private byte[] data;


    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public Header getHeader() { return header; }
    public void setHeader(Header header) { this.header = header; }
    
    
    public static class Header {
        private List<Property> properties = new ArrayList<Property>();

        public List<Property> getProperties() { return properties; }
        
        public void addProperty(int type, byte[] value) {
            properties.add(Property.instance(type, value));
        }

        public List<Property> all(int type) {
            List<Property> list = new ArrayList<Property>();
            for(Property property : properties) {
                if(property.getType() == type) list.add(property);
            }
            return list;
        }
        public Property first(int type) {
            for(Property property : properties) {
                if(property.getType() == type) return property;
            }
            return null;
        }

        public byte[] toByteArray() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(bos);
                out.writeInt(properties.size());
                for(Property property : properties) {
                    out.writeInt(property.type);
                    out.writeInt(property.value.length);
                    out.write(property.value);
                }
                out.close();
                byte[] bytes = bos.toByteArray();
                bos.close();
                return bytes;
            } catch(Exception e) {
                throw new IllegalArgumentException("Unable to write header to bytes");
            }
        }
        
        public static Header fromByteArray(byte[] bytes, int offset, int length) {
            try {
                Header h = new Header();
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes, offset, length);
                DataInputStream in = new DataInputStream(bis);
                int size = in.readInt();
                for(int i = 0; i < size; i++) {
                    int type = in.readInt();
                    int len = in.readInt();
                    byte[] b = in.readNBytes(len);
                    h.addProperty(type, b);
                }
                return h;
            } catch(Exception e) {
                throw new IllegalArgumentException("Unable to parse header from bytes");
            }
        }
    }
    
    public static class Property {
        private int type;
        private byte[] value;

        public Property(int type, byte[] value) {
            if(value == null) value = new byte[0];
            this.type = type;
            this.value = value;
        }

        public int getType() { return type; }
        public void setType(int type) { this.type = type; }
        public byte[] getValue() { return value; }
        public void setValue(byte[] value) { this.value = value; }

        public static Property instance(int type, byte[] value) {
            Property p = new Property(type, value);
            return p;
        }

    }
    
}
