plugins {
    java
    `java-library`
    application
    id("com.github.seanrl.jaxb") version "2.5.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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

    api("ch.qos.logback:logback-core:1.2.3");
    api("ch.qos.logback:logback-classic:1.2.3")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-parameter-names
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.14.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jdk8
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.14.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-jaxb-annotations
    implementation("com.fasterxml.jackson.module:jackson-module-jaxb-annotations:2.14.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}