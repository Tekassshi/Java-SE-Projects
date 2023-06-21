plugins {
    java
    `java-library`
    application
    id("com.github.seanrl.jaxb") version "2.5.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.openjfx.javafxplugin") version "0.0.14"
    idea
}

group = "org.example"
version = "0.2"

application {
    mainClass.set("Core")
}

repositories {
    mavenCentral()
}

dependencies{
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")


    // https://mvnrepository.com/artifact/com.auth0/java-jwt
    api("com.auth0:java-jwt:4.4.0")

    api("ch.qos.logback:logback-core:1.2.3");
    api("ch.qos.logback:logback-classic:1.2.3")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    api("com.fasterxml.jackson.core:jackson-databind:2.15.0")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}