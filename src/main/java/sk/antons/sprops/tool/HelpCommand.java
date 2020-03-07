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
import java.util.logging.Logger;

/**
 *
 * @author antons
 */
public class HelpCommand implements Command {
    private static Logger log = Logger.getLogger("cmd.help");

    @Override
    public boolean requirePassword() {
        return false;
    }

    @Override
    public boolean checkInput(Args args) {
        return true;
    }

    @Override
    public boolean realize(Args args) {
        log.info("Use form: java -jar sprops-tool.jar <parameters>");
        log.info("");
        log.info("  -h, -help, --help - For printing this text.");
        log.info("");
        log.info("common properties");
        log.info("  -p <password>, --password <password> - For direct password.");
        log.info("      Do not use this form if you don't want to store password");
        log.info("      in command history.");
        log.info("  -fp <file>, --fpassword <file> - for password stored in text");
        log.info("      file.");
        log.info("");
        log.info("      If password is necessary and is not specified program");
        log.info("      will ask for one.");
        log.info("");
        log.info("  -alg <algorithm>, --alg <algorithm> - For specifying encoding");
        log.info("      algorithm. Values are ('simple-aes') default value is");
        log.info("      simple-aes");
        log.info("");
        log.info("commands");
        log.info("");
        log.info("  -fshow <file>, --fshow <file>");
        log.info("      For listin encoded properties.");
        log.info("");
        log.info("  -fdecode <file>, --fdecode <file>");
        log.info("      For decoding property file.");
        log.info("");
        log.info("  -fdecodeall <file>, --decodeall <file>");
        log.info("      For decoding whole property file.");
        log.info("");
        log.info("  -fencode <file> <property list>, --fdecode <file> <property list> - For encoding property in property file");
        log.info("      For encoding property in property file");
        log.info("");
        log.info("  -fencodeall <file>, --fdecode <file> - For encoding property in property file");
        log.info("      For encoding property in property file");
        log.info("");
        log.info("  -decode <encoded string list>, --decode <encoded string lisst>");
        log.info("      For decoding encoded strings");
        log.info("");
        log.info("  -encode <string list>, --encode <string list>");
        log.info("      For encoding given strings ");
        log.info("");
        log.info("");

        return true;
    }
    
}
