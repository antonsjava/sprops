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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sk.antons.jaul.Is;

/**
 *
 * @author antons
 */
public class Args {
    private String[] args;
    private Map<String, List<String>> argMap = new HashMap<String, List<String>>(); 
    private Set<String> singles = new HashSet<String>();
    private Set<String> pairs = new HashSet<String>();

    public Args() {
    }

    public static Args instance() { return new Args(); }

    public Args single(String value) { singles.add(value); return this; }
    public Args pair(String value) { pairs.add(value); return this; }
    
    public Args parse(String[] args) {
        this.args = args;
        
        if(args == null) return this;
        String previous = "";
        for(int i = 0; i < args.length; i++) {
            String param = args[i];
            if(singles.contains(param)) {
                addArg(param);
                previous = "";
            } else if(pairs.contains(param)) {
                previous = param;
            } else {
                addArg(previous, param);
                previous = "";
            }
            
        }
        
        return this;
    
    }

    private void addArg(String name) {
        addArg(name, "");
    }

    private void addArg(String name, String value) {
        if(name == null) return;
        if(value == null) value = "";
        List<String> list = argMap.get(name);
        if(list == null) {
            list = new ArrayList<String>();
            argMap.put(name, list);
        }
        list.add(value);
    }

    public List<String> all(String... args) {
        List<String> list = new ArrayList<String>();
        if(args == null) return list;

        for(String arg : args) {
            List<String> l = argMap.get(arg);
            if(l == null) continue;
            list.addAll(l);
        }
        
        return list;
    }
    
    public String first(String... args) {
        if(args == null) return null;

        for(String arg : args) {
            List<String> l = argMap.get(arg);
            if(Is.empty(l)) continue;
            return l.get(0);
        }
        
        return null;
    }
    
    public boolean isSet(String... args) {
        if(args == null) return false;

        for(String arg : args) {
            List<String> l = argMap.get(arg);
            if(Is.empty(l)) continue;
            return true;
        }
        
        return false;
    }
}
