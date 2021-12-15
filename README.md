
# sprops

sprops is small library for encoding and decoding property files or simple 
string data.

Motivation for this project was to have possibility to store secret data like passwords 
together with sources or have it stored somewhere else publicly.

Property file with encoded properties are protected with one password. This must be 
provided 'secretly' but this one only.

## sprops-tool

This is command line tool for manipulating the property files. Tool can be build by 
building this project.

Than you can use it for encoding and decoding properties in property file 

Lets have simple property file like 

```
  main.db.password=xxxx
  email.server.password=yyyy
```

than you can call 

```
  /tmp/aaa> java -jar sprops-tool.jar -fencode passwords.properties  main.db.password
  INF   main command to execute FileEncodeCommand
  Enter password: 
  INF c.fenc Property: 'main.db.password'
  INF c.fenc Encoding string from: 'xxxx'
  INF c.fenc                   to: 'sprops:AAAAIBnzWe9H8rJtXVsOtBApsVC74BldooOGHOu4h1GJWuMpMpWXnD3b/vYurPjmHYM1Ww=='
  INF   main command FileEncodeCommand execution done.
```

and property looks like 

```
  main.db.password=sprops:AAAAIBnzWe9H8rJtXVsOtBApsVC74BldooOGHOu4h1GJWuMpMpWXnD3b/vYurPjmHYM1Ww==
  email.server.password=yyyy
```

you can than call 

```
  /tmp/aaa> java -jar sprops-tool.jar -fdecode passwords.properties  main.db.password
  INF   main command to execute FileDecodeCommand
  Enter password: 
  INF c.fdec Property: 'main.db.password'
  INF c.fdec Encoding string from: 'sprops:AAAAIBnzWe9H8rJtXVsOtBApsVC74BldooOGHOu4h1GJWuMpMpWXnD3b/vYurPjmHYM1Ww=='
  INF c.fdec                   to: 'xxxx'
  INF   main command FileDecodeCommand execution done.
```

and property looks like 

```
  main.db.password=xxxx
  email.server.password=yyyy
```

There are also other options you can see it with --help option.

## Property file usage

Encoded file can be used in application 

```java
  String password = .....
  Properties encodedProps = .....
  PropertiesEncoder encoder = PropertiesEncoder.instance(password);
  encoder.add(encodedProps);
  String decodedProperty = encoder.getProperty("property.name");
  Properties decodedProps = encoder.decode();
```

## Simple encoding/decoding

You can use 

```java
  String password = .....
  String text = .....
  SimpleEncoder encoder = SimpleEncoder.instance(password);
  String encodedString = encoder.encode(text);
  String decodedString = encoder.decode(encodedString);
```

## System properties utility

If you are using spring boot and you want to have encoded properties provide as 
SB property. An easy way is copy the properties to System properties at wery 
first moment of starting application

```java
  Properties props = .....
  SystemPropertiesUpdater.instance(props)
    .map("main.db.password", "spring.datasource.password")
    .map("email.server.password", "myapp.notifier.password");
```

## Maven usage

```
   <dependency>
      <groupId>io.github.antonsjava</groupId>
      <artifactId>sprops</artifactId>
      <version>LATESTVERSION</version>
   </dependency>
```
