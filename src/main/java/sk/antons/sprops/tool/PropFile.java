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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import sk.antons.jaul.Is;
import sk.antons.jaul.Split;
import sk.antons.jaul.util.TextFile;

/**
 *
 * @author antons
 */
public class PropFile {
    private Properties props;
    private String text;
    private String file;

    public PropFile(String file) {
        this.file = file;
        load();
    }

    private void load() {
        try {
            this.text = TextFile.read(file, "utf-8");
            this.props = new Properties();
            FileInputStream fi = new FileInputStream(file);
            this.props.load(fi);
            fi.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("PropFile " + file, e);
        }
    }

    public String text() { return text; }
    public String getProperty(String property) { return props.getProperty(property); }
    
    public static PropFile instance(String file) { return new PropFile(file); }

    public List<String> propsToEncode() {
        Set<String> names = props.stringPropertyNames();
        List<String> list = new ArrayList<String>();
        for(String name : names) {
            String value = props.getProperty(name);
            if(Is.empty(value)) continue;
            if(value.startsWith("sprops:")) continue;
            list.add(name);
        }
        Collections.sort(list);
        return list;
    }
    
    public List<String> propsToDecode() {
        Set<String> names = props.stringPropertyNames();
        List<String> list = new ArrayList<String>();
        for(String name : names) {
            String value = props.getProperty(name);
            if(Is.empty(value)) continue;
            if(!value.startsWith("sprops:")) continue;
            list.add(name);
        }
        Collections.sort(list);
        return list;
    }

    public void replace(String property, String value) {
        if(Is.empty(property)) return;

        StringBuilder sb = new StringBuilder();
        List<String> lines = Split.string(text).bySubstringToList("\n");
        for(String line : lines) {
            if(line.startsWith(property + "=")
                || line.startsWith(property + " =")) {
                line = property + "=" + value;
            }
            sb.append(line).append('\n');
        }

        TextFile.save(file, "utf-8", sb.toString());
       
        load();
    }
}
