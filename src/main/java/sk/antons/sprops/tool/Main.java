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

import java.io.Console;
import java.io.File;
import java.util.logging.Logger;
import sk.antons.jaul.util.TextFile;
import sk.antons.util.logging.conf.SLConf;

/**
 *
 * @author antons
 */
public class Main {
    private static Logger log = Logger.getLogger("main");
    
    private static Command resolve(Args args) {
        if(args.isSet("-h", "-help", "--help")) return new HelpCommand();
        else if(args.isSet("-encode", "--encode")) return new EncodeCommand();
        else if(args.isSet("-decode", "--decode")) return new DecodeCommand();
        else if(args.isSet("-fencode", "--fencode")) return new FileEncodeCommand();
        else if(args.isSet("-fencodeall", "--fencodeall")) return new FileEncodeAllCommand();
        else if(args.isSet("-fdecode", "--fdecode")) return new FileDecodeCommand();
        else if(args.isSet("-fdecodeall", "--fdecodeall")) return new FileDecodeAllCommand();
        else if(args.isSet("-fshow", "--fshow")) return new FileShowCommand();
        else return null;
    }

    private static void process(Args args) {
        Command command = resolve(args);
        if(command == null) {
            log.warning("Unable to resolve concrete command from arguments");
            command = new NoCommand();
        } else {
            log.info("command to execute " + command.getClass().getSimpleName());
        }
        String file = args.first("-fp", "-fpassword");
        if(file != null) {
            File f = new File(file);
            if((!f.exists()) || (!f.isFile())) {
                log.warning("Unable to resolve password file '"+file+"'");
                command = new HelpCommand();
            }
        } 
        file = args.first("-f", "-file");
        if(file != null) {
            File f = new File(file);
            if((!f.exists()) || (!f.isFile())) {
                log.warning("Unable to resolve properties file '"+file+"'");
                command = new HelpCommand();
            }
        } 
        
        if(!command.checkInput(args)) {
            log.warning("Input check failed for resolved command");
            command = new NoCommand();
        }
        
        Resolved.algorithm(args.first("-alg", "--alg"));
        
        if(command.requirePassword()) {
            if(args.isSet("-p", "-password")) {
                Resolved.password(args.first("-p", "--password"));
            } else if(args.isSet("-fp", "--fpassword")) {
                Resolved.password(TextFile.read(args.first("-fp", "--fpassword"), "utf-8"));
            } else {
                Console console = System.console();
                if (console == null) {
                    log.warning("Couldn't get Console instance");
                    System.exit(0);
                }
                char passwordArray[] = console.readPassword("Enter password: ");
                Resolved.password(new String(passwordArray));
            }
        }

        boolean result = command.realize(args);
        if(result) {
            log.info("command " + command.getClass().getSimpleName() + " execution done.");
        } else {
            log.info("command " + command.getClass().getSimpleName() + " execution failed.");
        }  
    }

    public static void main(String[] params) {
        SLConf.reset();
        SLConf.rootLogger().console().filterAll().pattern()
            .text(" ").level(3, -3)
            .text(" ").simpleName(-6, -6)
            .text(" ").message()
            .patternEnd().handler();
        SLConf.rootLogger().info();
        //SLConf.logger("sk.antons").all();
            
            
        Args args = Args.instance()
            .single("-h").single("-help").single("--help")
            .pair("-p").pair("--password")
            .pair("-fp").pair("--fpassword")
            .pair("-alg").pair("--alg")
            .single("-encode").pair("--encode")
            .single("-decode").pair("--decode")
            .pair("-fencode").pair("--fencode")
            .pair("-fencodeall").pair("--fencodeall")
            .pair("-fdecode").pair("--fdecode")
            .pair("-fdecodeall").pair("--fdecodeall")
            .pair("-fshow").pair("--fshow")
            .parse(params);

        process(args);
    }
    
}
