import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    application
    id("com.github.seanrl.jaxb") version "2.5.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    idea
}

group = "org.example"
version = "1.0"

repositories{
    mavenCentral()
}

application {
    mainClass.set("Main")
}

dependencies {
    implementation(project(":core"))

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.6.0")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.12.0")

    // https://mvnrepository.com/artifact/commons-codec/commons-codec
    implementation("commons-codec:commons-codec:1.15")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<ShadowJar> {
    archiveVersion.set("1.0")
    archiveClassifier.set("final")
}