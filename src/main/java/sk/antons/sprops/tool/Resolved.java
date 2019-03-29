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
package sk.antons.sprops.tool;

import sk.antons.jaul.Is;
import sk.antons.sprops.alg.SpropsAlgFactory;

/**
 *
 * @author antons
 */
public class Resolved {
    private static String password;
    public static String password() {
        if(password == null) throw new IllegalStateException("No password provided.");
        return password;
    }
    protected static void password(String password) {
        Resolved.password = password;
    }
    
    private static int algorithm = SpropsAlgFactory.DEFAULT;
    public static int algorithm() {
        return algorithm;
    }
    protected static void algorithm(String algorithm) {
        if(Is.empty(algorithm)) return;
        else if("simple-aes".equals(algorithm)) Resolved.algorithm = SpropsAlgFactory.SIMPLE_AES;
        else throw new IllegalArgumentException("Unknown algorithme '" + algorithm + "'");
    }
}
