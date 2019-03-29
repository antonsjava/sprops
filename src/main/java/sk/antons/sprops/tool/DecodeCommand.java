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
import java.util.List;
import java.util.logging.Logger;
import sk.antons.sprops.SimpleEncoder;

/**
 *
 * @author antons
 */
public class DecodeCommand implements Command {
    private static Logger log = Logger.getLogger("cmd.dec");

    @Override
    public boolean requirePassword() {
        return true;
    }

    @Override
    public boolean checkInput(Args args) {
        if(!args.isSet("")) {
            log.warning("No strings to decode are provided");
            return false;
        }
        return true;
    }

    @Override
    public boolean realize(Args args) {
        List<String> strings = args.all("");
        
        SimpleEncoder encoder = SimpleEncoder.instance(Resolved.password());
        encoder.algorithm(Resolved.algorithm());
        
        for(String string : strings) {
            try {
                log.info("Encoding string from: '"+string+"'");
                log.info("                  to: '"+encoder.decode(string)+"'");
            } catch(Exception e) {
                log.warning("  Unable to decode string because of " + e);
            }
        }

        return true;
    }
    
}
