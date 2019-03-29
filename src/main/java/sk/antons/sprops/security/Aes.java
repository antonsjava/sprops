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

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Aes helper class
 * @author antons
 */
public class Aes {

    public static byte[] decode(byte[] key, byte[] iv, byte[] data) {
        return compute(key, iv, data, Cipher.DECRYPT_MODE);
    }

    public static byte[] encode(byte[] key, byte[] iv, byte[] data) {
        return compute(key, iv, data, Cipher.ENCRYPT_MODE);
    }

    private static byte[] compute(byte[] key, byte[] iv, byte[] data, int mode) {
        String operation = "encode";
        if(mode == Cipher.DECRYPT_MODE) operation = "decode";
        if (key == null) throw new IllegalArgumentException("Unable to "+operation+" Aes - null key");
        if (iv == null) throw new IllegalArgumentException("Unable to "+operation+" Aes - null iv");
        if (data == null) throw new IllegalArgumentException("Unable to "+operation+" Aes - null data");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Key computedKey = new SecretKeySpec(key, "AES");
            IvParameterSpec computedIV = new IvParameterSpec(iv);
            cipher.init(mode, computedKey, computedIV);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to " + operation + " Aes", e);
        }
    }

}
