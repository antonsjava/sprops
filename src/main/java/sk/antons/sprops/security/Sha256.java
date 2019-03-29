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
package sk.antons.sprops.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Sha256 helper class
 * @author antons
 */
public class Sha256 {
    
    public static byte[] hash(byte[] bytes, int offset, int length) {
        if (bytes == null) throw new IllegalArgumentException("Unable to compute hash from null bytes");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes, offset, length);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Unable to access SHA-256 algoritm", e);
        }
    }
    
    public static byte[] hash(byte[] bytes) {
        if (bytes == null) throw new IllegalArgumentException("Unable to compute hash from null bytes");
        return hash(bytes, 0, bytes.length);
    }

    public static byte[] hash(String text) {
        if (text == null) throw new IllegalArgumentException("Unable to compute hash from null bytes");
        try {
            return hash(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to get UTF-8 bytes from string", e);
        }
    }


}
