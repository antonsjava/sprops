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
public class NoCommand implements Command {
    private static Logger log = Logger.getLogger("cmd.none");

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
        log.info("To print help start: java -jar sprops-tool.jar -h");
        log.info("");
        return true;
    }
    
}
