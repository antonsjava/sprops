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
import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import sk.antons.jaul.Is;
import sk.antons.jaul.Split;
import sk.antons.sprops.SimpleEncoder;

/**
 *
 * @author antons
 */
public class FileDecodeCommand implements Command {
    private static Logger log = Logger.getLogger("cmd.fdec");
    
    private PropFile propfile = null;
    
    @Override
    public boolean requirePassword() {
        return true;
    }

    @Override
    public boolean checkInput(Args args) {
        String file = args.first("-fdecode", "--fdecode");
        if(Is.empty(file)) {
            log.warning("No properties file specified");
            return false;
        }
        File f = new File(file);
        if(!f.exists()) {
            log.warning("Properties file "+file+" not exists");
            return false;
        }
        if(!f.isFile()) {
            log.warning("Properties file "+file+" is not file");
            return false;
        }
        propfile = PropFile.instance(file);
        
        if(!args.isSet("")) {
            log.warning("No properties to decode are provided");
            List<String> list = propfile.propsToDecode();
            log.warning("You can decode properties");
            for(String string : list) {
                log.warning(" - " + string);
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean realize(Args args) {
        List<String> strings = args.all("");
        
        SimpleEncoder encoder = SimpleEncoder.instance(Resolved.password());
        encoder.algorithm(Resolved.algorithm());
        StringBuilder result = new StringBuilder();
        for(String string : strings) {
            try {
                log.info("Property: '"+string+"'");
                String value = propfile.getProperty(string);
                String decoded = encoder.decode(value);
                propfile.replace(string, decoded);
                log.info("Encoding string from: '"+value+"'");
                log.info("                  to: '"+decoded+"'");
            } catch(Exception e) {
                log.warning("  Unable to encode string because of " + e);
            }
        }

        return true;
    }
    
}
