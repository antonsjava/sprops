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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.SecureRandom;
import sk.antons.sprops.model.SpropsData;
import sk.antons.sprops.security.Aes;
import sk.antons.sprops.security.Blowfish;
import sk.antons.sprops.security.Sha256;

/**
 * Implementation uses Aes algorithm for encode and decode provided bytes.
 * used IV part is encoded by Blowfish algorithm
 * @author antons
 */
public class SimpleAesSpropsAlg implements SpropsAlg {
    
    private static final int IV = 1;
    
    private byte[] rawPassword; 
    
    @Override
    public void password(byte[] password) {
        if(password == null) throw new IllegalArgumentException("Bad password");
        if(password.length == 0) throw new IllegalArgumentException("Bad password");
        rawPassword = password;
    }

    @Override
    public void password(String password) {
        if(password == null) throw new IllegalArgumentException("Bad password");
        try {
            password(password.getBytes("UTF-8"));
        } catch(Exception e) {
            throw new IllegalArgumentException("Bad password - encoding", e);
        }
    }

    @Override
    public SpropsData prepare(byte[] data) {
        SpropsData sdata = new SpropsData();
        sdata.setData(data);
        SpropsData.Header header = new SpropsData.Header();
        header.addProperty(IV, generateIV());
        sdata.setHeader(header);
        sdata.setData(data);
        return sdata;
    }

    @Override
    public byte[] encode(SpropsData data) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bos);
            byte[] hashPass = Sha256.hash(rawPassword);
            byte[] header = data.getHeader().toByteArray();
            byte[] iv = data.getHeader().first(IV).getValue();
            byte[] encodedData =  Aes.encode(hashPass, iv, data.getData());
            byte[] encodedHeader =  Blowfish.encode(hashPass, header);
            
            //System.out.println(" ---> header origin " + header.length);            
            //System.out.println(" ---> data origin" + data.getData().length);            
            //System.out.println(" ---> header " + encodedHeader.length);            
            //System.out.println(" ---> data " + encodedData.length);            

            
            out.writeInt(encodedHeader.length);
            out.write(encodedHeader);
            out.write(encodedData);
            out.flush();
            out.close();
            byte[] rv = bos.toByteArray();
            bos.close();
            //System.out.println(" ---> result " + rv.length);            
            return rv;

        } catch(Exception e) {
            throw new IllegalArgumentException("Unable to encode", e);
        }
    }

    @Override
    public SpropsData decode(byte[] data, int offset, int length) {
        try {
            SpropsData sdata = new SpropsData();
            ByteArrayInputStream bis = new ByteArrayInputStream(data, offset, length);
            DataInputStream in = new DataInputStream(bis);
            byte[] hashPass = Sha256.hash(rawPassword);
            int headerLength = in.readInt();
            //System.out.println(" ---< data " + data.length);            
            //System.out.println(" ---< offset " + offset);            
            //System.out.println(" ---< length " + length);            
            //System.out.println(" ---< header len " + headerLength);            
            byte[] encodedHeader = in.readNBytes(headerLength);
            byte[] encodedData = in.readAllBytes();
            //System.out.println(" ---< header " + encodedHeader.length);            
            //System.out.println(" ---< data " + encodedData.length);            
            byte[] decodedHeader = Blowfish.decode(hashPass, encodedHeader);
            SpropsData.Header header = SpropsData.Header.fromByteArray(decodedHeader, 0, decodedHeader.length);
            sdata.setHeader(header);;
            byte[] iv = sdata.getHeader().first(IV).getValue();
            byte[] decodedData =  Aes.decode(hashPass, iv,encodedData);
            sdata.setData(decodedData);
            return sdata;

        } catch(Exception e) {
            throw new IllegalArgumentException("Unable to decode", e);
        }
    }
    

    private static byte[] generateIV() {
        try {
            int ivSize = 16;
            byte[] iv = new byte[ivSize];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            return iv;
        } catch(Exception e) {
            throw new IllegalStateException("Unable to generate IV", e);
        }
    }
}
